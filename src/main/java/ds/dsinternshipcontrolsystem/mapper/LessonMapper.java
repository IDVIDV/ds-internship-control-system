package ds.dsinternshipcontrolsystem.mapper;

import ds.dsinternshipcontrolsystem.dto.LessonDto;
import ds.dsinternshipcontrolsystem.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = InternshipMapper.class)
public interface LessonMapper {
    @Mapping(target = "internshipId", source = "internship.id")
    LessonDto toLessonDto(Lesson lesson);
    List<LessonDto> toLessonDtoList(List<Lesson> lessonList);
}
