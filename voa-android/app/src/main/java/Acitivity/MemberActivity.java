package Acitivity;

import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.voa.BuildConfig;
import com.example.voa.R;
import com.farsunset.cim.sdk.android.CIMEventListener;
import com.farsunset.cim.sdk.android.CIMListenerManager;
import com.farsunset.cim.sdk.android.CIMPushManager;
import com.farsunset.cim.sdk.android.constant.CIMConstant;
import com.farsunset.cim.sdk.android.model.Message;
import com.farsunset.cim.sdk.android.model.ReplyBody;
import com.farsunset.cim.sdk.android.model.SentBody;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.MemberListAdapter;
import Service.MyApplication;
import Service.MyStringRequest;
import Service.TeamMember;

public class MemberActivity extends AppCompatActivity implements CIMEventListener {
    private LinearLayout ll_mem;
    private ListView mlv;
    private TextView tv_title;
    private RequestQueue mQueue;
    private List<TeamMember> l_member;
    private MyApplication application;
    private Button btn_addMember;
    private TeamMember receiver;
    private Boolean flag=false;
    private String instruction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        application = (MyApplication) this.getApplicationContext();
        ll_mem=findViewById(R.id.ll_mem);
//        btn_addMember=new Button(MemberActivity.this);
//        btn_addMember.setBackgroundResource(R.drawable.bg_btn_register_2);
//        btn_addMember.setText("添加成员");
//        ll_mem.addView(btn_addMember);
        mlv=findViewById(R.id.lv_member);
        tv_title=findViewById(R.id.tv_title);
        tv_title.setText("成员列表");
        instruction=(String)getIntent().getExtras().get("instruction");
        mQueue= Volley.newRequestQueue(MemberActivity.this);
        if(instruction.equals("chat")) {
            CIMListenerManager.registerMessageListener(this);
            btn_addMember=new Button(MemberActivity.this);
            btn_addMember.setBackgroundResource(R.drawable.bg_btn_register_2);
            btn_addMember.setText("添加成员");
            ll_mem.addView(btn_addMember);
            btn_addMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MemberActivity.this, FindMemberActivity.class);
                    intent.putExtra("instruction","1");
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMember();
    }

    private void getMember(){
        String url="https://voa.yinjiahui.cn/api/team/"+application.getCurrentTeam().getId()+"/member";
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
                            JsonNode datanode=mapper.readTree(dataJson);
                            String teamMemberJson=datanode.get("teamMember").toString();
                            l_member=mapper.readValue(teamMemberJson,new TypeReference<List<TeamMember>>(){});
                            mlv.setAdapter(new MemberListAdapter(MemberActivity.this,l_member));
                            mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    receiver=l_member.get(position);
                                    if(instruction.equals("chat")) {
                                        CIMPushManager.connect(MemberActivity.this, BuildConfig.CIM_SERVER_HOST, BuildConfig.CIM_SERVER_PORT);
                                        Intent intent = new Intent(MemberActivity.this, MessageActivity.class);
                                        intent.putExtra("receiver", receiver);
                                        startActivity(intent);
                                    }
                                    else{
                                        Intent intent = getIntent();
                                        Bundle bundle = intent.getExtras();
                                        bundle.putSerializable("receiver", receiver);//添加要返回给页面1的数据
                                        intent.putExtras(bundle);
                                        MemberActivity.this.setResult(MemberActivity.RESULT_OK, intent);//返回页面1
                                        MemberActivity.this.finish();
                                    }
                                }
                            });
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        CIMListenerManager.removeMessageListener(this);
    }
    @Override
    public void onMessageReceived(Message message) {

    }

    @Override
    public void onReplyReceived(ReplyBody replyBody) {
        /*
         *第三步 用户id绑定成功，可以接收消息了
         */
        if (replyBody.getKey().equals(CIMConstant.RequestKey.CLIENT_BIND)) {
            flag=true;
        }
    }

    @Override
    public void onSendFinished(SentBody sentBody) {
    }

    @Override
    public void onNetworkChanged(NetworkInfo networkInfo) {
    }

    /**
     * 连接cim推送服务端成功回调
     * @param b
     */
    @Override
    public void onConnectFinished(boolean b) {
        /*
         * 第二步 绑定用户id到长连接
         * 这里业务方可以设置token等由后端实现转换成用户id
         */
        CIMPushManager.bind(this,application.getId());
    }

    @Override
    public void onConnectionClosed() {
    }

    /**
     * 连接cim推送服务端失败回调
     * @param
     */
    @Override
    public void onConnectFailed() {
        Toast.makeText(this,"连接失败! 请检查Host和Port是否正确，", Toast.LENGTH_LONG).show();
    }

    @Override
    public int getEventDispatchOrder() {
        return 0;
    }
    public interface VolleyCallback {
        void onSuccess(String string);
    }
}