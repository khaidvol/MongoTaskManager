package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Subtask {

  @Id
  @GeneratedValue()
  private long subtaskId;

  private String name;
  private String description;

  public Subtask() {}

  public Subtask(String name, String description) {
    this.name = name;
    this.description = description;
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

  @Override
  public String toString() {
    return "Subtask{"
        + "subtaskId="
        + subtaskId
        + ", name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + '}';
  }
}
