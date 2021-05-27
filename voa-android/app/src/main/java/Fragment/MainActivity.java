package Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

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
import com.yinglan.alphatabs.AlphaTabView;
import com.yinglan.alphatabs.AlphaTabsIndicator;
import com.yinglan.alphatabs.OnTabChangedListner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.TabFragmentPagerAdapter;
import Service.MyApplication;
import Service.MyStringRequest;
import Service.Team;
import Service.User;

public class MainActivity extends AppCompatActivity {
    private AlphaTabsIndicator alphaTabsIndicator;
    private AlphaTabView alphaTabView_Task,alphaTabView_Team,alphaTabView_Program,alphaTabView_My;
    private TabFragmentPagerAdapter adapter;
    private ViewPager mViewPager;
    private List<Fragment> list;
    private RequestQueue mQueue;
    private List<Team> l_team;
    private MyApplication application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        application = (MyApplication) this.getApplicationContext();
        alphaTabView_Task=findViewById(R.id.atv_task);
        alphaTabView_Team=findViewById(R.id.atv_team);
        alphaTabView_Program=findViewById(R.id.atv_program);
        alphaTabView_My=findViewById(R.id.atv_my);
        alphaTabsIndicator=findViewById(R.id.alphaIndicator);
        mViewPager = (ViewPager) findViewById(R.id.vp_viewpager);
        mQueue= Volley.newRequestQueue(MainActivity.this);
        getTeam(new VolleyCallback() {
            @Override
            public void onSuccess(List<Team> l_team) {
                application.setL_team(l_team);
                if(!l_team.isEmpty()&&application.getCurrentTeam()==null){
                    application.setCurrentTeam(l_team.get(0));
                    initView();
                }
            }
        });
//        getTeam(new VolleyCallback() {
//            @Override
//            public void onSuccess(List<Team> l_team) {
//                application.setL_team(l_team);
//                if(l_team.size()!=0){
//                    application.setCurrentTeam(l_team.get(0));
//                }
//                initView();
//            }
//            });
//        getUser();
        //linearLayout_task=findViewById(R.id.ll_task);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTeam(new VolleyCallback() {
            @Override
            public void onSuccess(List<Team> l_team) {
                application.setL_team(l_team);
                if(l_team.size()!=0&&application.getCurrentTeam()==null){
                    application.setCurrentTeam(l_team.get(0));
                }
            }
        });
        getUser();
    }

    private void initView(){

        //alphaTabsIndicator.setOnTabChangedListener(new MyOnTabChangedListener());
        mViewPager.addOnPageChangeListener(new MyPagerChangeListener());
        list =new ArrayList<>();
        list.add(new MyProjectFragment());
        list.add(new ProjectFragment());
        list.add(new TeamFragment());
        list.add(new MyFragment());
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);  //初始化显示第一个页面
        alphaTabsIndicator.setViewPager(mViewPager);
    }
    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            alphaTabsIndicator.setTabCurrenItem(arg0);
        }
    }
    public class MyOnTabChangedListener implements OnTabChangedListner{

        @Override
        public void onTabSelected(int tabNum) {
            alphaTabsIndicator.setTabCurrenItem(tabNum);
        }
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
    private void getUser(){
        String url="https://voa.yinjiahui.cn/api/user";
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
                            User user=mapper.readValue(node.get("data").toString(), User.class);
                            application.setUser(user);
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