package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.repository.CommitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommitService {
    private final CommitRepository commitRepository;

}
