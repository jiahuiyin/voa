package Service;

import java.io.Serializable;

public class Team implements Serializable {
    private String id;
    private String teamName;

    @Override
    public String toString() {
        return "Team{" +
                "id='" + id + '\'' +
                ", teamName='" + teamName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
