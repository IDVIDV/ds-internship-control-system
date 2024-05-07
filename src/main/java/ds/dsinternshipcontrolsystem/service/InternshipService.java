package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.dto.AddInternship;
import ds.dsinternshipcontrolsystem.dto.InternshipDto;
import ds.dsinternshipcontrolsystem.entity.Internship;
import ds.dsinternshipcontrolsystem.entity.status.InternshipStatus;
import ds.dsinternshipcontrolsystem.exception.WrongInternshipStatusException;
import ds.dsinternshipcontrolsystem.mapper.InternshipMapper;
import ds.dsinternshipcontrolsystem.repository.InternshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipService {
    private final InternshipRepository internshipRepository;
    private final InternshipMapper internshipMapper;
    private final TaskForkService taskForkService;

    public List<InternshipDto> getAllInternships() {
        return internshipMapper.toInternshipDtoList(internshipRepository.findAll());
    }

    public void addInternship(AddInternship addInternship) {
        Internship internship = internshipMapper.toInternship(addInternship);
        internship.setStatus(InternshipStatus.REGISTRY);
        internshipRepository.save(internship);
    }

    public void endRegistry(Integer internshipId) {
        Internship internship = internshipRepository.findById(internshipId).orElse(null);

        if (internship == null) {
            throw new EntityNotFoundException("Internship with given id does not exist");
        }

        if (!internship.getStatus().equals(InternshipStatus.REGISTRY)) {
            throw new WrongInternshipStatusException(String.format("Internship should be in %s state",
                    InternshipStatus.REGISTRY));
        }

        internship.setStatus(InternshipStatus.AWAITING_START);

        internshipRepository.save(internship);
    }

    @Transactional
    public void startInternship(Integer internshipId) {
        Internship internship = internshipRepository.findById(internshipId).orElse(null);

        if (internship == null) {
            throw new EntityNotFoundException("Internship with given id does not exist");
        }

        if (!internship.getStatus().equals(InternshipStatus.AWAITING_START)) {
            throw new WrongInternshipStatusException(String.format("Internship should be in %s state",
                    InternshipStatus.AWAITING_START));
        }

        internship.setStatus(InternshipStatus.IN_PROGRESS);

        taskForkService.createForksOnInternshipStart(internship);

        internshipRepository.save(internship);
    }

    public void endInternship(Integer internshipId) {
        Internship internship = internshipRepository.findById(internshipId).orElse(null);

        if (internship == null) {
            throw new EntityNotFoundException("Internship with given id does not exist");
        }

        if (!internship.getStatus().equals(InternshipStatus.IN_PROGRESS)) {
            throw new WrongInternshipStatusException(String.format("Internship should be in %s state",
                    InternshipStatus.IN_PROGRESS));
        }

        internship.setStatus(InternshipStatus.CLOSED);
        internshipRepository.save(internship);
    }
}
