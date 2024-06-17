package ds.dsinternshipcontrolsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ds.dsinternshipcontrolsystem.dto.AddInternship;
import ds.dsinternshipcontrolsystem.dto.InternshipDto;
import ds.dsinternshipcontrolsystem.dto.InternshipItem;
import ds.dsinternshipcontrolsystem.entity.status.InternshipStatus;
import ds.dsinternshipcontrolsystem.service.InternshipService;
import ds.dsinternshipcontrolsystem.service.ReportService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class InternshipControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private InternshipService internshipService;
    @Mock
    private ReportService reportService;
    @InjectMocks
    private InternshipController internshipController;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(internshipController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void getAllInternshipsTest() throws Exception {
        InternshipItem internshipItem1 = getInternshipItem(InternshipStatus.IN_PROGRESS);
        InternshipItem internshipItem2 = getInternshipItem(InternshipStatus.IN_PROGRESS);

        List<InternshipItem> internshipItemList = new ArrayList<>();

        internshipItemList.add(internshipItem1);
        internshipItemList.add(internshipItem2);

        when(internshipService.getAllInternships()).thenReturn(internshipItemList);

        mockMvc.perform(get("/internships"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(internshipItemList.size())));

        verify(internshipService, times(1)).getAllInternships();
    }

    @Test
    void getInternshipByIdTest() throws Exception {
        InternshipDto internshipDto = getInternshipDto(InternshipStatus.REGISTRY);

        when(internshipService.getInternshipById(internshipDto.getId())).thenReturn(internshipDto);

        mockMvc.perform(get("/internships/{id}", internshipDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(internshipDto.getId()))
                .andExpect(jsonPath("$.internshipName").value(internshipDto.getInternshipName()))
                .andExpect(jsonPath("$.description").value(internshipDto.getDescription()))
                .andExpect(jsonPath("$.startDate").value(internshipDto.getStartDate().getTime()))
                .andExpect(jsonPath("$.signEndDate").value(internshipDto.getSignEndDate().getTime()))
                .andExpect(jsonPath("$.status").value(internshipDto.getStatus().toString()))
                .andExpect(jsonPath("$.lessonItemList",
                        Matchers.hasSize(internshipDto.getLessonItemList().size())));

        verify(internshipService, times(1)).getInternshipById(internshipDto.getId());
    }

    @Test
    void getUnexistingInternshipByIdTest() throws Exception {
        when(internshipService.getInternshipById(any())).thenThrow(new EntityNotFoundException("Not found"));

        mockMvc.perform(get("/internships/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(EntityNotFoundException.class));

        verify(internshipService, times(1)).getInternshipById(any());
    }

    @Test
    void addInternshipTest() throws Exception {
        AddInternship addInternship = getAddInternship();
        InternshipDto internshipDto = getInternshipDto(InternshipStatus.IN_PROGRESS);

        when(internshipService.addInternship(addInternship)).thenReturn(internshipDto);

        mockMvc.perform(post("/internships")
                        .content(objectMapper.writeValueAsString(addInternship))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(internshipService, times(1)).addInternship(addInternship);
    }

    @Test
    void endRegistryTest() throws Exception {
        int id = 1;

        doNothing().when(internshipService).endRegistry(id);

        mockMvc.perform(post("/internships/{id}/end-registry", id))
                .andExpect(status().isOk());

        verify(internshipService, times(1)).endRegistry(id);
    }

    @Test
    void endRegistryOfUnexistingInternshipTest() throws Exception {
        doThrow(EntityNotFoundException.class).when(internshipService).endRegistry(any());

        mockMvc.perform(post("/internships/{id}/end-registry", 1))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(EntityNotFoundException.class));

        verify(internshipService, times(1)).endRegistry(any());
    }

    @Test
    void startInternshipTest() throws Exception {
        int id = 1;

        doNothing().when(internshipService).startInternship(id);

        mockMvc.perform(post("/internships/{id}/start", id))
                .andExpect(status().isOk());

        verify(internshipService, times(1)).startInternship(id);
    }

    @Test
    void startUnexistingInternshipTest() throws Exception {
        doThrow(EntityNotFoundException.class).when(internshipService).startInternship(any());

        mockMvc.perform(post("/internships/{id}/start", 1))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(EntityNotFoundException.class));

        verify(internshipService, times(1)).startInternship(any());
    }

    @Test
    void endInternshipTest() throws Exception {
        int id = 1;

        doNothing().when(internshipService).endInternship(id);

        mockMvc.perform(post("/internships/{id}/end", id))
                .andExpect(status().isOk());

        verify(internshipService, times(1)).endInternship(id);
    }

    @Test
    void endUnexistingInternshipTest() throws Exception {
        doThrow(EntityNotFoundException.class).when(internshipService).endInternship(any());

        mockMvc.perform(post("/internships/{id}/end", 1))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(EntityNotFoundException.class));

        verify(internshipService, times(1)).endInternship(any());
    }

    private static InternshipDto getInternshipDto(InternshipStatus internshipStatus) {
        return new InternshipDto(1, "name", "desc",
                Timestamp.valueOf("2024-06-15 00:00:00"),
                Timestamp.valueOf("2024-06-20 00:00:00"),
                internshipStatus,
                new ArrayList<>());
    }

    private static InternshipItem getInternshipItem(InternshipStatus internshipStatus) {
        return new InternshipItem(1, "name", "desc",
                Timestamp.valueOf("2024-06-15 00:00:00"),
                Timestamp.valueOf("2024-06-20 00:00:00"),
                internshipStatus);
    }

    private static AddInternship getAddInternship() {
        return new AddInternship("name", "desc",
                Timestamp.valueOf("2024-06-15 00:00:00"),
                Timestamp.valueOf("2024-06-20 00:00:00"));
    }
}
