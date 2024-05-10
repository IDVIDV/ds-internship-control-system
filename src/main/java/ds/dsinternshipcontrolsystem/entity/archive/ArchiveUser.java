package ds.dsinternshipcontrolsystem.entity.archive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"", schema = "archive")
public class ArchiveUser {
    @Id
    @Column(name = "user_id")
    private Integer id;
    private String username;
    private String mail;
    @Column(name = "full_name")
    private String fullName;
    private String phone;
    @Column(name = "telegram_id")
    private String telegramId;
    @Column(name = "study_status")
    private String studyStatus;
    private String university;
    private String faculty;
    private String specialization;
    private Integer course;
    @OneToMany(mappedBy = "user")
    private List<ArchivePerformance> performances;
    @OneToMany(mappedBy = "user")
    private List<ArchiveUserInternship> userInternships;
}
