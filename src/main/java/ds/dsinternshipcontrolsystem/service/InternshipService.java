package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.dto.AddInternship;
import ds.dsinternshipcontrolsystem.dto.InternshipDto;
import ds.dsinternshipcontrolsystem.entity.Internship;
import ds.dsinternshipcontrolsystem.entity.User;
import ds.dsinternshipcontrolsystem.entity.status.InternshipStatus;
import ds.dsinternshipcontrolsystem.entity.status.UserInternshipStatus;
import ds.dsinternshipcontrolsystem.exception.WrongInternshipStatusException;
import ds.dsinternshipcontrolsystem.mapper.InternshipMapper;
import ds.dsinternshipcontrolsystem.repository.InternshipRepository;
import ds.dsinternshipcontrolsystem.repository.UserInternshipRepository;
import ds.dsinternshipcontrolsystem.repository.projection.UserOnly;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InternshipService {
    private final InternshipRepository internshipRepository;
    private final UserInternshipRepository userInternshipRepository;
    private final InternshipMapper internshipMapper;
    private final TaskForkService taskForkService;
    private final ArchiveService archiveService;

    public List<InternshipDto> getAllInternships() {
        return internshipMapper.toInternshipDtoList(internshipRepository.findAll());
    }

    public InternshipDto getInternshipById(Integer internshipId) {
        Internship internship = internshipRepository.findById(internshipId).orElse(null);

        if (internship == null) {
            throw new EntityNotFoundException(String.format("Internship with id %d does not exist", internshipId));
        }

        return internshipMapper.toInternshipDto(internship);
    }

    public void addInternship(AddInternship addInternship) {
        Internship internship = internshipMapper.toInternship(addInternship);
        internship.setStatus(InternshipStatus.REGISTRY);
        internshipRepository.save(internship);
    }

    public void endRegistry(Integer internshipId) {
        Internship internship = internshipRepository.findById(internshipId).orElse(null);

        if (internship == null) {
            throw new EntityNotFoundException(String.format("Internship with id %d does not exist", internshipId));
        }

        if (!internship.getStatus().equals(InternshipStatus.REGISTRY)) {
            throw new WrongInternshipStatusException(String.format("Internship should be in %s state",
                    InternshipStatus.REGISTRY));
        }

        internship.setStatus(InternshipStatus.AWAITING_START);
        internshipRepository.save(internship);

        log.info("Internship transferred from {} to {} state",
                InternshipStatus.REGISTRY, InternshipStatus.AWAITING_START);
    }

    public void startInternship(Integer internshipId) {
        Internship internship = internshipRepository.findById(internshipId).orElse(null);

        if (internship == null) {
            throw new EntityNotFoundException(String.format("Internship with id %d does not exist", internshipId));
        }

        if (!internship.getStatus().equals(InternshipStatus.AWAITING_START)) {
            throw new WrongInternshipStatusException(String.format("Internship should be in %s state",
                    InternshipStatus.AWAITING_START));
        }

        taskForkService.createForksOnInternshipStart(internship);

        internship.setStatus(InternshipStatus.IN_PROGRESS);
        internshipRepository.save(internship);

        log.info("Internship transferred from {} to {} state",
                InternshipStatus.AWAITING_START, InternshipStatus.IN_PROGRESS);
    }

    public void endInternship(Integer internshipId) {
        Internship internship = internshipRepository.findById(internshipId).orElse(null);

        if (internship == null) {
            throw new EntityNotFoundException(String.format("Internship with id %d does not exist", internshipId));
        }

        if (!internship.getStatus().equals(InternshipStatus.IN_PROGRESS)) {
            throw new WrongInternshipStatusException(String.format("Internship should be in %s state",
                    InternshipStatus.IN_PROGRESS));
        }

//        internship.setStatus(InternshipStatus.CLOSED);
//        internshipRepository.save(internship);

        List<User> usersInInternship = userInternshipRepository
                .findAllByInternshipIdAndStatus(internship.getId(), UserInternshipStatus.JOINED)
                .stream()
                .map(UserOnly::getUser)
                .collect(Collectors.toList());

        archiveService.archiveUsersInInternship(usersInInternship, internship);

        internship.setStatus(InternshipStatus.CLOSED);
        internshipRepository.save(internship);

        log.info("Internship transferred from {} to {} state",
                InternshipStatus.IN_PROGRESS, InternshipStatus.CLOSED);
    }
}
