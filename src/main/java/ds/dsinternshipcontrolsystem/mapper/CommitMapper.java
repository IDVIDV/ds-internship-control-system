package ds.dsinternshipcontrolsystem.mapper;

import ds.dsinternshipcontrolsystem.entity.Commit;
import org.gitlab4j.api.webhook.PushEvent;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface CommitMapper {
    default List<Commit> toCommitList(PushEvent pushEvent) {
        List<Commit> commits = new ArrayList<>();

        //pushEvent.
        return commits;
    }
}
