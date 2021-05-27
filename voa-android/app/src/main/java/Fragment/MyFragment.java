package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.voa.R;

import Acitivity.MyInformationActivity;
import Acitivity.TeamListActivity;
import Service.MyApplication;
import Service.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyFragment extends Fragment {
    private MyApplication application;
    private TextView tv_team,tv_name;
    private User user;
    private LinearLayout ll_team,ll_name;
    private CircleImageView circleImageView_avatar;
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){
        View view=inflater .inflate(R.layout.activity_my ,container,false) ;
        application = (MyApplication) getActivity().getApplicationContext();
        user=application.getUser();
        tv_team=view.findViewById(R.id.tv_team_name);
        tv_name=view.findViewById(R.id.tv_name);
        ll_team=view.findViewById(R.id.ll_team);
        ll_name=view.findViewById(R.id.ll_name);
        circleImageView_avatar=view.findViewById(R.id.civ_head);
//        if(application.getCurrentTeam()!=null){
//            tv_team.setText(application.getCurrentTeam().getTeamName());
//        }
//        chooseTeam(ll_team);
//        getMyInformation(ll_name);
//        String imageUrl="https://voa.yinjiahui.cn/api/files/avatar/"+user.getAvatar();
//        tv_name.setText(user.getUsername());
//
//        Glide.with(getActivity()).load(imageUrl).into(circleImageView_avatar);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(application.getCurrentTeam()!=null){
            tv_team.setText(application.getCurrentTeam().getTeamName());
        }
        chooseTeam(ll_team);
        getMyInformation(ll_name);
        if(!user.getAvatar().isEmpty()) {
            String imageUrl = "https://voa.yinjiahui.cn/api/files/avatar/" + user.getAvatar();
            Glide.with(getActivity()).load(imageUrl).into(circleImageView_avatar);
        }
        else {
            circleImageView_avatar.setImageResource(R.mipmap.ic_launcher);
        }
        tv_name.setText(user.getUsername());
    }

    private void chooseTeam(LinearLayout ll_team){
        ll_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), TeamListActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getMyInformation(LinearLayout ll_name){
        ll_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyInformationActivity.class);
                startActivity(intent);
            }
        });
    }
}