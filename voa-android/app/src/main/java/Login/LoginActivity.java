package Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.voa.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import Fragment.MainActivity;
import Service.LoginResponse;
import Service.MyApplication;
import Service.MyStringRequest;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class LoginActivity extends AppCompatActivity {

    private EditText editText_userName,editText_userPsw;
    private TextView tv_forgetPwd,tv_register;
    private Button btn_login;
    private RequestQueue mQueue;
    private MyApplication application;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        application = (MyApplication) this.getApplicationContext();
        editText_userName=findViewById(R.id.login_user_name);
        editText_userPsw=findViewById(R.id.login_user_password);
        btn_login=findViewById(R.id.login_lg);
        tv_forgetPwd=findViewById(R.id.login_forget_pwd);
        tv_register=findViewById(R.id.login_register);
        setListeners();
        mQueue = Volley.newRequestQueue(LoginActivity.this);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editText_userName.getText().toString();
                password = editText_userPsw.getText().toString();
                if(username.trim().equals("")|| password.trim().equals("")){
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setContentText("请输入账号和密码").setConfirmText("确定").setCancelable(false);
                    sweetAlertDialog.show();
                }
                else {
                    Login();
                }
            }

        });
    }

    private void Login(){
        String url = "https://voa.yinjiahui.cn/api/sso/login";
        MyStringRequest request = new MyStringRequest(
                JsonRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            LoginResponse result = mapper.readValue(response, LoginResponse.class);

                            if(result.getMessage().equals("操作成功")){
                                application.setToken(result.getToken());
                                application.setId(result.getData().getId());
                                System.out.println(application.getToken());
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else{
                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                                sweetAlertDialog.setTitle("错误");
                                sweetAlertDialog.setContentText("用户名或密码错误").setConfirmText("确定").setCancelable(false);
                                sweetAlertDialog.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitle("错误");
                sweetAlertDialog.setContentText("通信错误").setConfirmText("确定").setCancelable(false);
                sweetAlertDialog.show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("username", username);
                map.put("password", password);
                return map;
            }

            @Override
            protected String getParamsEncoding() {
                return "GBK";
            }
        };
        mQueue.add(request);
    }

    private void setListeners(){
        OnClick onClick=new OnClick();
        tv_register.setOnClickListener(onClick);
        tv_forgetPwd.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent =null;
            switch (v.getId()){
                case R.id.login_register:
                    intent=new Intent(LoginActivity.this,RegisterActivity.class);
                    intent.putExtra("request",true);
                    break;
                case R.id.login_forget_pwd:
                    intent=new Intent(LoginActivity.this,RegisterActivity.class);
                    intent.putExtra("request",false);
                    break;
            }
            startActivity(intent);
        }
    }

}