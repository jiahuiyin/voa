package Service;

import java.io.Serializable;

public class Task implements Serializable {
      private String id;
      private String projectId;
      private String headId;
      private String title;
      private String deadline;
      private String description;
      private String priority;
      private String finish;
      private String headName;

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", projectId='" + projectId + '\'' +
                ", headId='" + headId + '\'' +
                ", title='" + title + '\'' +
                ", deadline='" + deadline + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", finish='" + finish + '\'' +
                ", headName='" + headName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getHeadId() {
        return headId;
    }

    public void setHeadId(String headId) {
        this.headId = headId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }
}
