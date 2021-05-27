package Acitivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.voa.R;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import Service.Member;
import Service.MyApplication;
import Service.MyStringRequest;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class MemberInformationActivity extends AppCompatActivity {
    private LinearLayout ll_mem,ll_mem2;
    private CircleImageView circleImageView_head;
    private TextView textView_name;
    private MyApplication application;
    private RequestQueue mQueue;
    private Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_member_item);
        application = (MyApplication) this.getApplicationContext();
        mQueue = Volley.newRequestQueue(MemberInformationActivity.this);
        ll_mem = findViewById(R.id.ll_mem);
        ll_mem2 = findViewById(R.id.ll_mem2);
        textView_name = findViewById(R.id.tv_username);
        circleImageView_head = findViewById(R.id.civ_head);
        member = (Member) getIntent().getExtras().get("member");
        textView_name.setText(member.getUsername());
        String imageUrl = "https://voa.yinjiahui.cn/api/files/avatar/" + member.getAvatar();
        Glide.with(MemberInformationActivity.this).load(imageUrl).into(circleImageView_head);
        String instruction = getIntent().getExtras().get("instruction").toString();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 50, 0, 20);//4个参数按顺序分别是左上右下
        ll_mem2.setLayoutParams(layoutParams);
        if (instruction.equals("1")) {
            Button button = new Button(MemberInformationActivity.this);
            button.setBackgroundResource(R.drawable.bg_btn_register_2);
            button.setText("添加成员");
            ll_mem.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addMember(new VolleyCallback() {
                        @Override
                        public void onSuccess(String string) {
                            int code = Integer.valueOf(string).intValue();
                            SweetAlertDialog sweetAlertDialog;
                            if (code == 200) {
                                sweetAlertDialog = new SweetAlertDialog(MemberInformationActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                sweetAlertDialog.setTitle("成功");
                                sweetAlertDialog.setContentText("添加成功").setConfirmText("确定").setCancelable(false);
                            } else {
                                sweetAlertDialog = new SweetAlertDialog(MemberInformationActivity.this, SweetAlertDialog.ERROR_TYPE);
                                sweetAlertDialog.setTitle("错误");
                                sweetAlertDialog.setContentText("操作失败").setConfirmText("确定").setCancelable(false);
                            }
                            sweetAlertDialog.show();
                        }
                    });
                }


            });
        }
    }

    private void  addMember(final VolleyCallback callback){
        String url="https://voa.yinjiahui.cn/api/team/"+application.getCurrentTeam().getId()+"/member";
        MyStringRequest request = new MyStringRequest(
                JsonRequest.Method.POST,
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
                            String message = node.get("code").toString();
                            System.out.println(message);
                            callback.onSuccess(message);
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId",member.getId());
                //headId
                return map;
            }

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
        void onSuccess(String string);
    }
}