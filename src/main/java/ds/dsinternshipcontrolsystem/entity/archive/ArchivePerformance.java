package ds.dsinternshipcontrolsystem.entity.archive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "performance", schema = "archive")
public class ArchivePerformance {
    @Id
    @Column(name = "performance_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private ArchiveUser user;
    @ManyToOne
    @JoinColumn(name = "internship_id", nullable = false)
    private ArchiveInternship internship;
    @Column(name = "fork_url")
    private String forkUrl;
    private Boolean accepted;
}
