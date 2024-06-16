package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.dto.AddInternship;
import ds.dsinternshipcontrolsystem.dto.InternshipDto;
import ds.dsinternshipcontrolsystem.dto.InternshipItem;
import ds.dsinternshipcontrolsystem.entity.Internship;
import ds.dsinternshipcontrolsystem.entity.status.InternshipStatus;
import ds.dsinternshipcontrolsystem.entity.status.UserInternshipStatus;
import ds.dsinternshipcontrolsystem.exception.WrongInternshipStatusException;
import ds.dsinternshipcontrolsystem.mapper.InternshipMapper;
import ds.dsinternshipcontrolsystem.repository.InternshipRepository;
import ds.dsinternshipcontrolsystem.repository.UserInternshipRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InternshipServiceTest {
    @Mock
    private InternshipRepository internshipRepository;
    @Mock
    private UserInternshipRepository userInternshipRepository;
    @Mock
    private InternshipMapper internshipMapper;
    @Mock
    private TaskForkService taskForkService;
    @Mock
    private ArchiveService archiveService;
    @InjectMocks
    private InternshipService internshipService;

    private static Stream<Arguments> getInternshipAndInternshipItemLists() {
        List<Internship> emptyInternshipList = new ArrayList<>();
        List<InternshipItem> emptyInternshipItemList = new ArrayList<>();
        List<Internship> internshipList = new ArrayList<>();
        List<InternshipItem> internshipItemsList = new ArrayList<>();

        internshipList.add(getInternship(InternshipStatus.REGISTRY));
        internshipItemsList.add(getInternshipItem(InternshipStatus.REGISTRY));

        return Stream.of(
                Arguments.of(
                        emptyInternshipList, emptyInternshipItemList
                ),
                Arguments.of(
                        internshipList, internshipItemsList
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getInternshipAndInternshipItemLists")
    void getAllInternshipsTest(List<Internship> internshipList, List<InternshipItem> internshipItemList) {
        when(internshipRepository.findAll()).thenReturn(internshipList);
        when(internshipMapper.toInternshipItemList(internshipList)).thenReturn(internshipItemList);

        assertThat(internshipItemList).isEqualTo(internshipService.getAllInternships());
    }

    @Test
    void getInternshipByIdTest() {
        Internship internship = getInternship(InternshipStatus.REGISTRY);
        InternshipDto internshipDto = getInternshipDto(InternshipStatus.REGISTRY);

        when(internshipRepository.findById(internship.getId())).thenReturn(Optional.of(internship));
        when(internshipMapper.toInternshipDto(internship)).thenReturn(internshipDto);

        assertThat(internshipDto).isEqualTo(internshipService.getInternshipById(internship.getId()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1, 1})
    void getUnexistingInternshipByIdTest(Integer id) {
        when(internshipRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> internshipService.getInternshipById(id)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void addInternshipTest() {
        Internship internship = getInternship(InternshipStatus.REGISTRY);
        AddInternship addInternship = getAddIntenrship();
        InternshipDto internshipDto = getInternshipDto(InternshipStatus.REGISTRY);
        internship.setStatus(null);

        when(internshipMapper.toInternship(addInternship)).thenReturn(internship);
        when(internshipRepository.save(internship)).thenReturn(internship);
        when(internshipMapper.toInternshipDto(internship)).thenReturn(internshipDto);

        assertThat(internshipDto).isEqualTo(internshipService.addInternship(addInternship));
        assertThat(internship.getStatus()).isEqualTo(InternshipStatus.REGISTRY);
    }

    @Test
    void endRegistryTest() {
        Internship internship = getInternship(InternshipStatus.REGISTRY);

        when(internshipRepository.findById(internship.getId())).thenReturn(Optional.of(internship));
        when(internshipRepository.save(internship)).thenReturn(internship);

        internshipService.endRegistry(internship.getId());

        assertThat(internship.getStatus()).isEqualTo(InternshipStatus.AWAITING_START);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1, 1})
    void endUnexistingInternshipRegistryTest(Integer id) {
        when(internshipRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> internshipService.endRegistry(id)).isInstanceOf(EntityNotFoundException.class);
    }


    @ParameterizedTest
    @EnumSource(value = InternshipStatus.class, names = {"AWAITING_START", "IN_PROGRESS", "CLOSED"})
    void endRegistryWithWrongStatusTest(InternshipStatus internshipStatus) {
        Internship internship = getInternship(internshipStatus);

        when(internshipRepository.findById(internship.getId())).thenReturn(Optional.of(internship));

        assertThatThrownBy(() -> internshipService.endRegistry(internship.getId()))
                .isInstanceOf(WrongInternshipStatusException.class);
    }

    @Test
    void startInternshipTest() {
        Internship internship = getInternship(InternshipStatus.AWAITING_START);

        when(internshipRepository.findById(internship.getId())).thenReturn(Optional.of(internship));
        doNothing().when(taskForkService).createForksOnInternshipStart(internship);
        when(internshipRepository.save(internship)).thenReturn(internship);

        internshipService.startInternship(internship.getId());

        assertThat(internship.getStatus()).isEqualTo(InternshipStatus.IN_PROGRESS);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1, 1})
    void startUnexistingInternshipTest(Integer id) {
        when(internshipRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> internshipService.startInternship(id)).isInstanceOf(EntityNotFoundException.class);
    }

    @ParameterizedTest
    @EnumSource(value = InternshipStatus.class, names = {"REGISTRY", "IN_PROGRESS", "CLOSED"})
    void startInternshipWithWrongStatusTest(InternshipStatus internshipStatus) {
        Internship internship = getInternship(internshipStatus);

        when(internshipRepository.findById(internship.getId())).thenReturn(Optional.of(internship));

        assertThatThrownBy(() -> internshipService.startInternship(internship.getId()))
                .isInstanceOf(WrongInternshipStatusException.class);
    }

    @Test
    void endInternshipTest() {
        Internship internship = getInternship(InternshipStatus.IN_PROGRESS);

        when(internshipRepository.findById(internship.getId())).thenReturn(Optional.of(internship));
        when(userInternshipRepository.findAllByInternshipIdAndStatus(internship.getId(),
                UserInternshipStatus.JOINED)).thenReturn(new ArrayList<>());
        doNothing().when(archiveService).archiveUsersInInternship(anyList(), eq(internship));
        when(internshipRepository.save(internship)).thenReturn(internship);

        internshipService.endInternship(internship.getId());

        assertThat(internship.getStatus()).isEqualTo(InternshipStatus.CLOSED);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1, 1})
    void endUnexistingInternshipTest(Integer id) {
        when(internshipRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> internshipService.endInternship(id)).isInstanceOf(EntityNotFoundException.class);
    }

    @ParameterizedTest
    @EnumSource(value = InternshipStatus.class, names = {"REGISTRY", "AWAITING_START", "CLOSED"})
    void endInternshipWithWrongStatusTest(InternshipStatus internshipStatus) {
        Internship internship = getInternship(internshipStatus);

        when(internshipRepository.findById(internship.getId())).thenReturn(Optional.of(internship));

        assertThatThrownBy( () -> internshipService.endInternship(internship.getId()))
                .isInstanceOf(WrongInternshipStatusException.class);
    }

    private static Internship getInternship(InternshipStatus internshipStatus) {
        return new Internship(1, "name", "desc",
                Timestamp.valueOf("2024-06-15 00:00:00"),
                Timestamp.valueOf("2024-06-20 00:00:00"),
                internshipStatus,
                null,
                null);
    }

    private static InternshipDto getInternshipDto(InternshipStatus internshipStatus) {
        return new InternshipDto(1, "name", "desc",
                Timestamp.valueOf("2024-06-15 00:00:00"),
                Timestamp.valueOf("2024-06-20 00:00:00"),
                internshipStatus,
                null);
    }

    private static InternshipItem getInternshipItem(InternshipStatus internshipStatus) {
        return new InternshipItem(1, "name", "desc",
                Timestamp.valueOf("2024-06-15 00:00:00"),
                Timestamp.valueOf("2024-06-20 00:00:00"),
                internshipStatus);
    }

    private static AddInternship getAddIntenrship() {
        return new AddInternship("name", "desc",
                Timestamp.valueOf("2024-06-15 00:00:00"),
                Timestamp.valueOf("2024-06-20 00:00:00"));
    }
}
