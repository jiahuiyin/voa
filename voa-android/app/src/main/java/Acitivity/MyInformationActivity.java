package Acitivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

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
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Service.MyApplication;
import Service.MyStringRequest;
import Service.User;
import Widget.EditInputView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyInformationActivity extends AppCompatActivity {

    private EditInputView editInputView_name,editInputView_birthday,editInputView_gender;
    private RoundedImageView roundedImageView_avatar;
    private Button btn_save;
    private MyApplication application;
    private RequestQueue mQueue;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        application = (MyApplication) this.getApplicationContext();
        mQueue = Volley.newRequestQueue(MyInformationActivity.this);
        editInputView_birthday=findViewById(R.id.edi_birthday);
        editInputView_name=findViewById(R.id.edi_name);
        editInputView_gender=findViewById(R.id.edi_gender);
        roundedImageView_avatar=findViewById(R.id.imv_avatar);
        btn_save=findViewById(R.id.btn_save);
        editInputView_birthday.getInputEditView().setInputType(InputType.TYPE_NULL);
        editInputView_gender.getInputEditView().setInputType(InputType.TYPE_NULL);
        user=application.getUser();
        //getInformation();
        editInputView_name.setText(user.getUsername());
        editInputView_birthday.setText(user.getBirthday());
        if(user.getGender().equals("0")){
             editInputView_gender.setText("男");
              }
        else editInputView_gender.setText("女");
        if(!user.getAvatar().isEmpty()) {
            String imageUrl = "https://voa.yinjiahui.cn/api/files/avatar/" + user.getAvatar();
            Glide.with(MyInformationActivity.this).load(imageUrl).into(roundedImageView_avatar);
        }
        else {
            roundedImageView_avatar.setImageResource(R.mipmap.ic_launcher);
        }
        editInputView_gender.getInputEditView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 启动带列表的对话框
                 * @param view
                 */
                final String[] items = {"男","女"};
                new AlertDialog.Builder(MyInformationActivity.this)
                        .setTitle("请选择性别")
                        .setCancelable(false)
                        .setSingleChoiceItems(items, GenderDetermine(editInputView_gender.getText().toString()), new DialogInterface.OnClickListener() {
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
                    new DatePickerDialog(MyInformationActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editInputView_name.getText().toString().isEmpty()){
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyInformationActivity.this, SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitle("错误");
                    sweetAlertDialog.setContentText("昵称不能为空").setConfirmText("确定").setCancelable(false);
                sweetAlertDialog.show();}
                else {
                    UpdateInformation(new VolleyCallback() {
                        @Override
                        public void onSuccess(String string) {
                            int code = Integer.valueOf(string).intValue();
                            SweetAlertDialog sweetAlertDialog;
                            if (code == 200) {
                                sweetAlertDialog = new SweetAlertDialog(MyInformationActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                sweetAlertDialog.setTitle("成功");
                                sweetAlertDialog.setContentText("保存成功").setConfirmText("确定").setCancelable(false);
                            } else {
                                sweetAlertDialog = new SweetAlertDialog(MyInformationActivity.this, SweetAlertDialog.ERROR_TYPE);
                                sweetAlertDialog.setTitle("错误");
                                sweetAlertDialog.setContentText("保存失败，请重新检查配置").setConfirmText("确定").setCancelable(false);
                            }
                            sweetAlertDialog.show();
                        }
                    });
                }
            }
        });
    }

    private int GenderDetermine(String string){
        if(string.equals("男")){
            return 0;
        }
        return 1;
    }

    private void  UpdateInformation(final VolleyCallback callback){
        String url="https://voa.yinjiahui.cn/api/user";
        MyStringRequest request = new MyStringRequest(
                JsonRequest.Method.PUT,
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
                            User user2=mapper.readValue(node.get("data").toString(),User.class);
                            application.setUser(user2);
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
                map.put("username",editInputView_name.getText().toString());
                map.put("birthday",editInputView_birthday.getText().toString());
                map.put("gender",Integer.toString(GenderDetermine(editInputView_gender.getText().toString())));
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