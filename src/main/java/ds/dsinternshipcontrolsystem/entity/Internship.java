package ds.dsinternshipcontrolsystem.entity;

import ds.dsinternshipcontrolsystem.entity.status.InternshipStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

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
    private Integer id;
    @Column(name = "internship_name")
    private String internshipName;
    private String description;
    @Column(name = "start_date")
    private Timestamp startDate;
    @Column(name = "sign_end_date")
    private Timestamp signEndDate;
    @Enumerated(EnumType.STRING)
    private InternshipStatus status;
    @OneToMany(mappedBy = "internship", fetch = FetchType.LAZY)
    private List<UserInternship> userInternships;
    @OneToMany(mappedBy = "internship", fetch = FetchType.LAZY)
    private List<Lesson> lessons;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Internship that = (Internship) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
