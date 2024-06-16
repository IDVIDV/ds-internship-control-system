package ds.dsinternshipcontrolsystem.mapper;

import ds.dsinternshipcontrolsystem.dto.AddLesson;
import ds.dsinternshipcontrolsystem.dto.LessonDto;
import ds.dsinternshipcontrolsystem.dto.LessonItem;
import ds.dsinternshipcontrolsystem.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface LessonMapper {
    @Mapping(target = "internshipId", source = "internship.id")
    @Mapping(target = "taskItemList", source = "tasks")
    LessonDto toLessonDto(Lesson lesson);

    @Mapping(target = "internshipId", source = "internship.id")
    LessonItem toLessonItem(Lesson lesson);

    List<LessonItem> toLessonItemList(List<Lesson> lessonList);

    @Mapping(target = "internship",
            expression = "java(new ds.dsinternshipcontrolsystem.entity." +
                    "Internship(addLesson.getInternshipId(), null, null, null, null, null, null, null))")
    Lesson toLesson(AddLesson addLesson);
}
