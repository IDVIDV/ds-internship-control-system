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
@Table(name = "user_internship", schema = "archive")
public class ArchiveUserInternship {
    @Id
    @Column(name = "user_internship_id")
    private Integer userInternshipId;
    @ManyToOne
    @JoinColumn(name = "internship_id", nullable = false)
    private ArchiveInternship internship;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private ArchiveUser user;
}
