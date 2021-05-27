package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.voa.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Acitivity.FileActivity;
import Acitivity.FindMemberActivity;
import Acitivity.MemberActivity;
import Service.MyApplication;
import Service.MyStringRequest;
import Service.Team;

public class TeamFragment extends Fragment {
    private TextView tv_member,tv_team,tv_search,tv_file;
    private RequestQueue mQueue;
    private List<Team> l_team;
    private MyApplication application;
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){
        View view=inflater .inflate(R.layout.activity_team ,container,false) ;
        application = (MyApplication) getActivity().getApplicationContext();
        mQueue= Volley.newRequestQueue(getActivity());
        tv_member=view.findViewById(R.id.tv_member);
        tv_team=view.findViewById(R.id.tv_team);
        tv_search=view.findViewById(R.id.tv_search);
        tv_file=view.findViewById(R.id.tv_file);
//        tv_member.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(), MemberActivity.class);
//                startActivity(intent);
//            }
//        });
//        if(application.getCurrentTeam()!=null){
//            tv_team.setText(application.getCurrentTeam().getTeamName());
//        }
//        tv_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(), FindMemberActivity.class);
//                intent.putExtra("instruction","0");
//                startActivity(intent);
//            }
//        });
//        tv_file.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(), FileActivity.class);
//                startActivity(intent);
//            }
//        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        tv_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MemberActivity.class);
                intent.putExtra("instruction","chat");
                startActivity(intent);
            }
        });
        if(application.getCurrentTeam()!=null){
            tv_team.setText(application.getCurrentTeam().getTeamName());
        }
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FindMemberActivity.class);
                intent.putExtra("instruction","0");
                startActivity(intent);
            }
        });
        tv_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getTeam(final VolleyCallback callback){
        String url="https://voa.yinjiahui.cn/api/team";
        MyStringRequest request = new MyStringRequest(
                JsonRequest.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            String jsonStr = response;
                            ObjectMapper mapper = new ObjectMapper();

                            JsonNode node = mapper.readTree(jsonStr);
                            String dataJson;

                            //LinkedList<Project> l_Project=new LinkedList<>();
                            //data链表
                            dataJson = node.get("data").toString();
                            l_team=mapper.readValue(dataJson,new TypeReference<List<Team>>(){});
                            callback.onSuccess(l_team);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("3");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mHeader = new HashMap<String, String>();
                mHeader.put("Authorization", application.getToken());
                return mHeader;
            }

            @Override
            protected String getParamsEncoding() {
                return "GBK";
            }
        };
        mQueue.add(request);
    }

    public interface VolleyCallback {
        void onSuccess(List<Team> l_team);
    }

}