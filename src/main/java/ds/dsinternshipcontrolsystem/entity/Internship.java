package ds.dsinternshipcontrolsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "internship")
public class Internship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "internship_id")
    private Integer internshipId;
    @Column(name = "internship_name")
    private String internshipName;
    private String description;
    @Column(name = "start_date")
    private Timestamp start_date;
    @Column(name = "sign_end_date")
    private Timestamp signEndDate;
    private String status;
    @OneToMany(mappedBy = "internship", fetch = FetchType.LAZY)
    private List<UserInternship> userInternships;
    @OneToMany(mappedBy = "internship", fetch = FetchType.LAZY)
    private List<Lesson> lessons;
}
