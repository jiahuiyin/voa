package Acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.voa.R;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import Service.Member;
import Service.MyApplication;
import Service.MyStringRequest;
import Widget.EditInputView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class FindMemberActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private MyApplication application;
    private EditInputView editInputView_numberId;
    private Button btn_confirm;
    private Member member;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        application = (MyApplication) this.getApplicationContext();
        editInputView_numberId=findViewById(R.id.edi_num_phone);
        btn_confirm=findViewById(R.id.btn_confirm);
        mQueue= Volley.newRequestQueue(FindMemberActivity.this);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            SweetAlertDialog sweetAlertDialog;
            @Override
            public void onClick(View v) {
                if(editInputView_numberId.getText().toString().isEmpty()||editInputView_numberId.getText().toString().length()!=11){
                    sweetAlertDialog = new SweetAlertDialog(FindMemberActivity.this, SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitle("错误");
                    sweetAlertDialog.setContentText("请输入正确的用户手机号").setConfirmText("确定").setCancelable(false);
                    sweetAlertDialog.show();
                }
                else{
                  FindMember(new VolleyCallback() {
                      @Override
                      public void onSuccess(String string) {
                          int code=Integer.valueOf(string).intValue();
                          if(code==200){
//                              sweetAlertDialog = new SweetAlertDialog(FindMemberActivity.this, SweetAlertDialog.SUCCESS_TYPE);
//                              sweetAlertDialog.setTitle("成功");
//                              sweetAlertDialog.setContentText("添加成功").setConfirmText("确定").setCancelable(false);
                              Intent intent=new Intent(FindMemberActivity.this,MemberInformationActivity.class);
                              intent.putExtra("instruction",getIntent().getExtras().get("instruction").toString());
                              intent.putExtra("member",member);
                              startActivity(intent);
                          }
                          else{
                              sweetAlertDialog = new SweetAlertDialog(FindMemberActivity.this, SweetAlertDialog.ERROR_TYPE);
                              sweetAlertDialog.setTitle("错误");
                              sweetAlertDialog.setContentText("该用户不存在").setConfirmText("确定").setCancelable(false);
                          }
                          sweetAlertDialog.show();
                      }
                  });
                }
            }
        });
    }


    private void FindMember(final VolleyCallback callback){
        String url="https://voa.yinjiahui.cn/api/user/phone/"+editInputView_numberId.getText().toString();
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
                            String message = node.get("code").toString();
                            member=mapper.readValue(node.get("data").toString(), Member.class);
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