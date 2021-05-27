package Login;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Acitivity.TaskActivity;
import Service.MyStringRequest;
import Widget.EditInputView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegisterActivity extends AppCompatActivity {

    private TextView tv_tip;
    private EditText editText_phoneNum,editText_validateCode,editText_input_pwd,editText_confirm_pwd;
    private Button btn_getValidateCode,btn_gotoNext;
    private TimeCount time;
    private String phoneNum1,pwd;
    private EditInputView editInputView_name,editInputView_birthday,editInputView_gender;
    private RequestQueue mQueue;
    private String reg = "(?=.*([a-zA-Z].*))(?=.*[0-9].*)[a-zA-Z0-9-*/+.~!@#$%^&*()]{6,18}$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("");
        setContentView(R.layout.activity_register_);
        mQueue = Volley.newRequestQueue(RegisterActivity.this);
        editText_phoneNum=findViewById(R.id.register_phonenum);
        editText_validateCode=findViewById(R.id.register_validateCode);
        editInputView_birthday=findViewById(R.id.edi_birthday);
        editInputView_name=findViewById(R.id.edi_name);
        editInputView_gender=findViewById(R.id.edi_gender);
        editText_input_pwd=findViewById(R.id.input_pwd);
        editText_confirm_pwd=findViewById(R.id.confirm_pwd);
        tv_tip=findViewById(R.id.input_tip);
        btn_getValidateCode=findViewById(R.id.register_btn_get_validateCode);
        btn_gotoNext=findViewById(R.id.register_btn_gonext);
        editInputView_birthday.getInputEditView().setInputType(InputType.TYPE_NULL);
        editInputView_gender.getInputEditView().setInputType(InputType.TYPE_NULL);
        editInputView_gender.setText("男");
        editInputView_birthday.setText("2000-0-0");
        initVerification();
        time = new TimeCount(60000, 1000);

        editInputView_gender.getInputEditView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 启动带列表的对话框
                 * @param view
                 */
                final String[] items = {"男","女"};
                new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("请选择性别")
                        .setCancelable(false)
                        .setSingleChoiceItems(items,0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    editInputView_gender.setText("男");
                                }
                                else editInputView_gender.setText("女");
                            }
                        })
                        .setPositiveButton("OK",null)
                        .show();
            }
        });
        editInputView_birthday.getInputEditView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str= editInputView_birthday.getText().toString();
                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = sdf.parse(str);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            editInputView_birthday.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                        }
                    },year,month,day).show();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        btn_getValidateCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum1=editText_phoneNum.getText().toString();
                if(phoneNum1.length()!=11){
                    Toast.makeText(RegisterActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    getCode(new VolleyCallback() {
                        @Override
                        public void onSuccess(String string) {
                            int code = Integer.valueOf(string).intValue();
                            if (code == 200) {
                                Toast.makeText(RegisterActivity.this, "验证码已通过短信形式发送", Toast.LENGTH_SHORT).show();
                                time.start();
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        btn_gotoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editInputView_name.getText().toString().isEmpty()||editInputView_birthday.getText().toString().isEmpty()||editInputView_gender.getText().toString().isEmpty()||editText_phoneNum.getText().toString().isEmpty()||editText_validateCode.getText().toString().isEmpty()||editText_input_pwd.getText().toString().isEmpty()||editText_confirm_pwd.getText().toString().isEmpty()){
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitle("注册失败");
                    sweetAlertDialog.setContentText("请补全信息").setConfirmText("确定").setCancelable(false);
                    sweetAlertDialog.show();}
                else if(!editText_input_pwd.getText().toString().matches(reg)){
                    Toast.makeText(RegisterActivity.this,"密码格式不正确",Toast.LENGTH_SHORT).show();
                    tv_tip.setTextColor(Color.RED);
                }
                else if(!editText_input_pwd.getText().toString().equals(editText_confirm_pwd.getText().toString())){
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitle("注册失败");
                    sweetAlertDialog.setContentText("两次密码不一致").setConfirmText("确定").setCancelable(false);
                    sweetAlertDialog.show();
                }
                else {
                    pwd=editText_confirm_pwd.getText().toString();
                    Register(new VolleyCallback() {
                        @Override
                        public void onSuccess(String string) {
                            int code = Integer.valueOf(string).intValue();
                            SweetAlertDialog sweetAlertDialog;
                            if (code == 200) {
                                sweetAlertDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                sweetAlertDialog.setTitle("成功");
                                sweetAlertDialog.setContentText("注册成功，快去登录吧").setConfirmText("确定").setCancelable(false);
                                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        if(sweetAlertDialog!=null){
                                            sweetAlertDialog.dismiss();
                                        }
                                        RegisterActivity.this.finish();
                                    }
                                });
                            } else {
                                sweetAlertDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE);
                                sweetAlertDialog.setTitle("错误");
                                sweetAlertDialog.setContentText("注册失败，请检查验证码是否正确").setConfirmText("确定").setCancelable(false);
                            }
                            sweetAlertDialog.show();
                        }
                    });
                }
                }

        });
    }



//短信验证码1min仅能发送一次
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_getValidateCode.setBackgroundColor(Color.parseColor("#B6B6D8"));
            btn_getValidateCode.setClickable(false);
            btn_getValidateCode.setText("("+millisUntilFinished / 1000 +") 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            btn_getValidateCode.setText("重新获取验证码");
            btn_getValidateCode.setClickable(true);
            btn_getValidateCode.setBackgroundColor(Color.parseColor("#4EB84A"));

        }
    }
    public void initVerification() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    for (int i = 0; i < s.length(); i++) {
                        char c = s.charAt(i);
                        if (c >= 0x4e00 && c <= 0X9fff) {
                            // 根据字节码判断
                            // 如果是中文，则清除输入的字符，否则保留
                            s.delete(i, i + 1);
                        }
                    }
                }
            }
        };
        editText_input_pwd.addTextChangedListener(textWatcher);
        editText_confirm_pwd.addTextChangedListener(textWatcher);
    }
    private void getCode(final VolleyCallback callback){
        String url = "https://voa.yinjiahui.cn/api/sso/code";
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
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitle("错误");
                sweetAlertDialog.setContentText("通信错误").setConfirmText("确定").setCancelable(false);
                sweetAlertDialog.show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone",editText_phoneNum.getText().toString());
                return map;
            }

            @Override
            protected String getParamsEncoding() {
                return "GBK";
            }
        };
        mQueue.add(request);
    }

    private void Register(final VolleyCallback callback){
        String url = "https://voa.yinjiahui.cn/api/sso/register";
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

                            String message = node.get("code").toString();
                            callback.onSuccess(message);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitle("错误");
                sweetAlertDialog.setContentText("通信错误").setConfirmText("确定").setCancelable(false);
                sweetAlertDialog.show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("birthday", editInputView_birthday.getText().toString());
                map.put("username",editInputView_name.getText().toString());
                map.put("phone",editText_phoneNum.getText().toString());
                map.put("password",pwd);
                if(editInputView_gender.getText().toString().equals("男")){
                    map.put("gender", "0");
                }
                else{
                    map.put("gender", "1");
                }
                return map;
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