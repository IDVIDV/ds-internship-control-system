package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.dto.AddLesson;
import ds.dsinternshipcontrolsystem.dto.LessonDto;
import ds.dsinternshipcontrolsystem.entity.Lesson;
import ds.dsinternshipcontrolsystem.mapper.LessonMapper;
import ds.dsinternshipcontrolsystem.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonMapper lessonMapper;
    private final LessonRepository lessonRepository;

    public List<LessonDto> getAllLessonsByInternshipId(Integer internshipId) {
        return lessonMapper.toLessonDtoList(lessonRepository.findAllByInternshipId(internshipId));
    }

    public void addLesson(AddLesson addLesson) {
        Lesson lesson = lessonMapper.toLesson(addLesson);
        lessonRepository.save(lesson);
    }
}
