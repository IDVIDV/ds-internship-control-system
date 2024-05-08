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
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;
    @Column(name = "task_name")
    private String taskName;
    private String description;
    private String url;
    private String path;
    @OneToMany(mappedBy = "task")
    private List<TaskFork> taskForks;
}
