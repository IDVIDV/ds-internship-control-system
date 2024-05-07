package ds.dsinternshipcontrolsystem.mapper;

import ds.dsinternshipcontrolsystem.dto.AddInternship;
import ds.dsinternshipcontrolsystem.dto.InternshipDto;
import ds.dsinternshipcontrolsystem.entity.Internship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = LessonMapper.class)
public interface InternshipMapper {
    @Mapping(target = "lessonDtoList", source = "lessons")
    InternshipDto toInternshipDto(Internship internship);
    List<InternshipDto> toInternshipDtoList(List<Internship> internshipList);
    Internship toInternship(AddInternship addInternship);
}
