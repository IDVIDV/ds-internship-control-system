package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.dto.AddLesson;
import ds.dsinternshipcontrolsystem.dto.LessonDto;
import ds.dsinternshipcontrolsystem.entity.Internship;
import ds.dsinternshipcontrolsystem.entity.Lesson;
import ds.dsinternshipcontrolsystem.mapper.LessonMapper;
import ds.dsinternshipcontrolsystem.repository.InternshipRepository;
import ds.dsinternshipcontrolsystem.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final InternshipRepository internshipRepository;
    private final LessonMapper lessonMapper;
    private final LessonRepository lessonRepository;

    public List<LessonDto> getAllLessonsByInternshipId(Integer internshipId) {
        return lessonMapper.toLessonDtoList(lessonRepository.findAllByInternshipId(internshipId));
    }

    public LessonDto getLessonById(Integer lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElse(null);

        if (lesson == null) {
            throw new EntityNotFoundException(String.format("Lesson with id %d does not exist", lessonId));
        }

        return lessonMapper.toLessonDto(lesson);
    }

    public void addLesson(AddLesson addLesson) {
        Internship internship = internshipRepository.findById(addLesson.getInternshipId()).orElse(null);

        if (internship == null) {
            throw new EntityNotFoundException(String.format("Internship with id %d does not exist",
                    addLesson.getInternshipId()));
        }

        Lesson lesson = lessonMapper.toLesson(addLesson);
        lessonRepository.save(lesson);
    }
}
