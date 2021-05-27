package Acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.voa.R;

import java.util.List;

import Adapter.TeamListAdapter;
import Service.MyApplication;
import Service.Team;

public class TeamListActivity extends AppCompatActivity {
    private LinearLayout ll_container;
    private ListView mlv;
    private TextView tv_title;
    private List<Team> l_team;
    private Button btn_addTeam;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);
        MyApplication application = (MyApplication) this.getApplicationContext();
        mQueue= Volley.newRequestQueue(TeamListActivity.this);
        l_team=application.getL_team();
        mlv=findViewById(R.id.lv_team);
        ll_container=findViewById(R.id.ll_team);
        tv_title=findViewById(R.id.tv_title);
        tv_title.setText("团队列表");
        btn_addTeam=new Button(TeamListActivity.this);
        btn_addTeam.setBackgroundResource(R.drawable.bg_btn_register_2);
        btn_addTeam.setText("创建团队");
        btn_addTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TeamListActivity.this,NewTeamActivity.class);
                startActivity(intent);
            }
        });
        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                application.setCurrentTeam(l_team.get(position));
                TeamListActivity.this.finish();
            }
        });
        ll_container.addView(btn_addTeam);
        mlv.setAdapter(new TeamListAdapter(TeamListActivity.this,l_team));
    }
}
