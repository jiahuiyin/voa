package Acitivity;

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

import Service.MyApplication;
import Service.MyStringRequest;
import Widget.EditInputView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class NewProjectActivity extends AppCompatActivity {

    private MyApplication application;
    private EditInputView editInputView_name;
    private Button btn_confirm;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
        application = (MyApplication) this.getApplicationContext();
        mQueue= Volley.newRequestQueue(NewProjectActivity.this);
        editInputView_name=findViewById(R.id.edi_project_name);
        btn_confirm=findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProject(new VolleyCallback() {
                    @Override
                    public void onSuccess(String string) {
                        int code = Integer.valueOf(string).intValue();
                        SweetAlertDialog sweetAlertDialog;
                        if (code == 200) {
                            sweetAlertDialog = new SweetAlertDialog(NewProjectActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                            sweetAlertDialog.setTitle("成功");
                            sweetAlertDialog.setContentText("创建成功").setConfirmText("确定").setCancelable(false);
                            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    if(sweetAlertDialog!=null){
                                        sweetAlertDialog.dismiss();
                                    }
                                    NewProjectActivity.this.finish();
                                }
                            });
                        } else {
                            sweetAlertDialog = new SweetAlertDialog(NewProjectActivity.this, SweetAlertDialog.ERROR_TYPE);
                            sweetAlertDialog.setTitle("错误");
                            sweetAlertDialog.setContentText("保存失败，请重新检查配置").setConfirmText("确定").setCancelable(false);
                        }
                        sweetAlertDialog.show();
                    }
                });
            }
        });
    }

    private void addProject(final VolleyCallback callback){
        String url="https://voa.yinjiahui.cn/api/team/"+application.getCurrentTeam().getId()+"/project";
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
                System.out.println("3");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("projectName",editInputView_name.getInputEditView().getText().toString() );
                map.put("teamId",application.getCurrentTeam().getId() );
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