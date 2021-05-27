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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Service.MyApplication;
import Service.MyStringRequest;
import Service.Team;
import Widget.EditInputView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class NewTeamActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private MyApplication application;
    private EditInputView editInputView_team;
    private Button btn_confirm;
    private List<Team> l_team;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_team);
        application = (MyApplication) this.getApplicationContext();
        editInputView_team=findViewById(R.id.edi_team_name);
        btn_confirm=findViewById(R.id.btn_confirm);
        mQueue= Volley.newRequestQueue(NewTeamActivity.this);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            SweetAlertDialog sweetAlertDialog;
            @Override
            public void onClick(View v) {
                if(editInputView_team.getInputEditView()==null){
                    sweetAlertDialog = new SweetAlertDialog(NewTeamActivity.this, SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitle("错误");
                    sweetAlertDialog.setContentText("请输入ID").setConfirmText("确定").setCancelable(false);
                }
                else{
                    AddTeam(new VolleyCallback() {
                        @Override
                        public void onSuccess(String string) {
                            int code=Integer.valueOf(string).intValue();
                            if(code==200){
                                sweetAlertDialog = new SweetAlertDialog(NewTeamActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                sweetAlertDialog.setTitle("成功");
                                sweetAlertDialog.setContentText("添加成功").setConfirmText("确定").setCancelable(false);
                                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        if(sweetAlertDialog!=null){
                                            sweetAlertDialog.dismiss();
                                        }
                                        NewTeamActivity.this.finish();
                                    }
                                });
                            }
                            else{
                                sweetAlertDialog = new SweetAlertDialog(NewTeamActivity.this, SweetAlertDialog.ERROR_TYPE);
                                sweetAlertDialog.setTitle("错误");
                                sweetAlertDialog.setContentText("添加失败，请检查重试").setConfirmText("确定").setCancelable(false);
                            }
                            sweetAlertDialog.show();
                        }
                    });
                }
            }
        });
    }

    private void AddTeam(final VolleyCallback callback){
        String url="https://voa.yinjiahui.cn/api/team";
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
                            dataJson = node.get("data").toString();
                            l_team=mapper.readValue(dataJson,new TypeReference<List<Team>>(){});
                            application.setL_team(l_team);
                            System.out.println(l_team);
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
//                map.put("projectName",editInputView_name.getInputEditView().getText().toString() );
//                map.put("teamId",application.getCurrentTeam().getId() );
                map.put("teamName",editInputView_team.getText().toString());
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