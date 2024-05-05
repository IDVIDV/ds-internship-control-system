package ds.dsinternshipcontrolsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"commit\"")
public class Commit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commit_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "task_fork_id", nullable = false)
    private TaskFork taskFork;
    private String author;
    @Column(name = "commit_date")
    private Timestamp commitDate;
    private String url;
    @OneToMany(mappedBy = "commit")
    private List<Comment> comments;
}
