package Acitivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

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

import Service.MyApplication;
import Service.MyStringRequest;
import Service.Task;
import Widget.EditInputView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class TaskActivity extends AppCompatActivity {
    private EditInputView editInputView_title,editInputView_headName,editInputView_deadline,editInputView_priority;
    private EditText editText_description;
    private CheckBox checkBox_finish;
    private Button button_delete,button_save;
    private RequestQueue mQueue;
    private MyApplication application;
    private Task task;
    private String projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        application = (MyApplication) this.getApplicationContext();
        mQueue= Volley.newRequestQueue(TaskActivity.this);
        projectId=(String)getIntent().getExtras().get("projectId");
        editInputView_title=findViewById(R.id.edi_title);
        editInputView_headName=findViewById(R.id.edi_headid);
        editInputView_deadline=findViewById(R.id.edi_end);
        editInputView_priority=findViewById(R.id.edi_priority);
        editText_description=findViewById(R.id.add_description);
        checkBox_finish=findViewById(R.id.cb_finish);
        button_delete=findViewById(R.id.btn_delete);
        button_save=findViewById(R.id.btn_save);
        editInputView_headName.getInputEditView().setInputType(InputType.TYPE_NULL);
        editInputView_deadline.getInputEditView().setInputType(InputType.TYPE_NULL);
        editInputView_priority.getInputEditView().setInputType(InputType.TYPE_NULL);
        task=(Task)getIntent().getExtras().get("data");
        if(task!=null) {
            editInputView_title.setText(task.getTitle());
            editInputView_headName.setText(task.getHeadName());
            editInputView_deadline.setText(task.getDeadline());
            editInputView_priority.setText(task.getPriority());
            editText_description.setText(task.getDescription());
            if (task.getFinish().equals("1")) {
                checkBox_finish.setChecked(true);
            }
        }
        //负责人选择
//        editInputView_headName.getInputEditView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(TaskActivity.this,MemberActivity.class);
//                intent.putExtra("instruction","choose");
//                Bundle bundle = new Bundle();
//                intent.putExtras(bundle);//将Bundle添加到Intent,也可以在Bundle中添加相应数据传递给下个页面,例如：bundle.putString("abc", "bbb");
//                startActivityForResult(intent, 0);// 跳转并要求返回值，0代表请求值(可以随便写)
//                startActivity(intent);
//            }
//        });
        //优先级选择
        editInputView_priority.getInputEditView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 启动带列表的对话框
                 * @param view
                 */
                    final String[] items = {"1级","2级","3级"};
                    new AlertDialog.Builder(TaskActivity.this)
                            .setTitle("请选择优先级")
                            .setCancelable(false)
                            .setSingleChoiceItems(items, Integer.valueOf(editInputView_priority.getText().toString()).intValue(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    editInputView_priority.setText(Integer.toString(which+1));
                                }
                            })
                            .setPositiveButton("OK",null)
                            .show();
            }
        });
        //结束日期选择
        editInputView_deadline.getInputEditView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=editInputView_deadline.getText().toString();
                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = sdf.parse(str);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    new DatePickerDialog(TaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            editInputView_deadline.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                        }
                    },year,month,day).show();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editInputView_title.getText().toString().isEmpty()||editInputView_headName.getText().toString().isEmpty()){
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(TaskActivity.this, SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitle("错误");
                    sweetAlertDialog.setContentText("任务名称或负责人不能为空").setConfirmText("确定").setCancelable(false);
                sweetAlertDialog.show();}
                else {
                    UpdateTask(new VolleyCallback() {
                        @Override
                        public void onSuccess(String string) {
                            int code = Integer.valueOf(string).intValue();
                            SweetAlertDialog sweetAlertDialog;
                            if (code == 200) {
                                sweetAlertDialog = new SweetAlertDialog(TaskActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                sweetAlertDialog.setTitle("成功");
                                sweetAlertDialog.setContentText("保存成功").setConfirmText("确定").setCancelable(false);
                                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        if(sweetAlertDialog!=null){
                                            sweetAlertDialog.dismiss();
                                        }
                                        TaskActivity.this.finish();
                                    }
                                });
                            } else {
                                sweetAlertDialog = new SweetAlertDialog(TaskActivity.this, SweetAlertDialog.ERROR_TYPE);
                                sweetAlertDialog.setTitle("错误");
                                sweetAlertDialog.setContentText("保存失败，请重新检查配置").setConfirmText("确定").setCancelable(false);
                            }
                            sweetAlertDialog.show();
                        }
                    });
                }
            }
        });
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteTask(new VolleyCallback() {
                    @Override
                    public void onSuccess(String string) {
                        int code=Integer.valueOf(string).intValue();
                        SweetAlertDialog sweetAlertDialog;
                        if(code==200){
                            sweetAlertDialog = new SweetAlertDialog(TaskActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                            sweetAlertDialog.setTitle("成功");
                            sweetAlertDialog.setContentText("删除成功,任务已删除").setConfirmText("确定").setCancelable(false);
                            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    if(sweetAlertDialog!=null){
                                        sweetAlertDialog.dismiss();
                                    }
                                    TaskActivity.this.finish();
                                }
                            });
                        }
                        else{
                            sweetAlertDialog = new SweetAlertDialog(TaskActivity.this, SweetAlertDialog.ERROR_TYPE);
                            sweetAlertDialog.setTitle("错误");
                            sweetAlertDialog.setContentText("删除失败").setConfirmText("确定").setCancelable(false);
                        }
                        sweetAlertDialog.show();
                    }
                });
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 0 && resultCode == MemberActivity.RESULT_OK) {
//            Bundle bundle = data.getExtras();
//            head=(TeamMember)bundle.getSerializable("receiver");
//            editInputView_headName.setText(head.getUsername());
//        }

    private void UpdateTask(final VolleyCallback callback){
        String url="https://voa.yinjiahui.cn/api/project/"+projectId+"/task";
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
//                map.put("projectName",editInputView_name.getInputEditView().getText().toString() );
//                map.put("teamId",application.getCurrentTeam().getId() );
                map.put("title",editInputView_title.getText().toString());
                map.put("priority",editInputView_priority.getText().toString());
                map.put("headName",editInputView_headName.getText().toString());
                map.put("deadline",editInputView_deadline.getText().toString());
                map.put("description",editText_description.getText().toString());
                if(checkBox_finish.isChecked()) {
                    map.put("finish","1");
                }
                else{map.put("finish","0");}
                map.put("id",task.getId());
                map.put("projectId",task.getProjectId());
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
    private void DeleteTask(final VolleyCallback callback){
        String url="https://voa.yinjiahui.cn/api/project/"+projectId+"/task";
        url=url+"?taskId="+task.getId();
        MyStringRequest request = new MyStringRequest(
                JsonRequest.Method.DELETE,
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