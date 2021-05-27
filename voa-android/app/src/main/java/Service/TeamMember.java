package Service;

import java.io.Serializable;

public class TeamMember implements Serializable {
    private String id;
    private String username;
    private String avatar;
    private String online;

    @Override
    public String toString() {
        return "TeamMember{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", online='" + online + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}
