package Service;

import android.app.Application;

import java.util.List;

public class MyApplication extends Application {
    private List<Team> l_team;
    private String token;
    private Team CurrentTeam;
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Team getCurrentTeam() {
        return CurrentTeam;
    }

    public void setCurrentTeam(Team currentTeam) {
        CurrentTeam = currentTeam;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    public List<Team> getL_team() {
        return l_team;
    }

    public void setL_team(List<Team> l_team) {
        this.l_team = l_team;
    }
}
