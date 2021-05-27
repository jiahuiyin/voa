package Service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class MyProject {
    private String id;
    private String name;
    private List<Tasks> tasks;

    public MyProject(String id, String name, List<Tasks> tasks) {
        this.id = id;
        this.name = name;
        this.tasks = tasks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "MyProject{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
