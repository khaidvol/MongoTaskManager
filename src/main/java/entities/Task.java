package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Task {
  @Id @GeneratedValue() private long taskId;

  private String name;
  private String description;
  private String category;
  private Date dateOfCreating;
  private Date deadline;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Subtask> subtask;

  public Task() {
    subtask = new ArrayList<>();
  }

  public Task(
          String name,
          String description,
          String category,
          Date dateOfCreating,
          Date deadline) {
    this.name = name;
    this.description = description;
    this.category = category;
    this.dateOfCreating = dateOfCreating;
    this.deadline = deadline;
    subtask = new ArrayList<>();
  }

  public Task(
      String name,
      String description,
      String category,
      Date dateOfCreating,
      Date deadline,
      List<Subtask> subtask) {
    this.name = name;
    this.description = description;
    this.category = category;
    this.dateOfCreating = dateOfCreating;
    this.deadline = deadline;
    this.subtask = subtask;
  }

  public long getTaskId() {
    return taskId;
  }

  public void setTaskId(long taskId) {
    this.taskId = taskId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public List<Subtask> getSubtask() {
    return subtask;
  }

  public void setSubtask(List<Subtask> subtask) {
    this.subtask = subtask;
  }

  public Date getDateOfCreating() {
    return dateOfCreating;
  }

  public void setDateOfCreating(Date dateOfCreating) {
    this.dateOfCreating = dateOfCreating;
  }

  public Date getDeadline() {
    return deadline;
  }

  public void setDeadline(Date deadline) {
    this.deadline = deadline;
  }

  @Override
  public String toString() {
    return "Task{"
        + "taskId="
        + taskId
        + ", name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + ", category='"
        + category
        + '\''
        + ", subtask="
        + subtask
        + ", dateOfCreating="
        + dateOfCreating
        + ", deadline="
        + deadline
        + '}';
  }
}
