package Service;

import java.util.Date;

public class Tasks {
    private String id;
    private String title;
    private String deadline;
    private String priority;

    @Override
    public String toString() {
        return "Tasks{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", deadline='" + deadline + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
