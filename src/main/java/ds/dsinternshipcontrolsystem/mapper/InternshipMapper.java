package ds.dsinternshipcontrolsystem.mapper;

import ds.dsinternshipcontrolsystem.dto.AddInternship;
import ds.dsinternshipcontrolsystem.dto.InternshipDto;
import ds.dsinternshipcontrolsystem.dto.InternshipItem;
import ds.dsinternshipcontrolsystem.entity.Internship;
import ds.dsinternshipcontrolsystem.entity.archive.ArchiveInternship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = LessonMapper.class)
public interface InternshipMapper {
    @Mapping(target = "lessonItemList", source = "lessons")
    InternshipDto toInternshipDto(Internship internship);

    InternshipItem toInternshipItem(Internship internship);

    List<InternshipItem> toInternshipItemList(List<Internship> internshipList);

    Internship toInternship(AddInternship addInternship);

    @Mapping(target = "userInternships", ignore = true)
    @Mapping(target = "performances", ignore = true)
    ArchiveInternship toArchiveInternship(Internship internship);
}
