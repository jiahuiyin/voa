package Acitivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import Service.Project;
import Service.Task;

public class TaskListActivity extends AppCompatActivity {
    private Project project;
    private RequestQueue mQueue;
    private MyApplication application;
    private Button btn_add;
    private String projectId;
    private LinearLayout linearLayoutContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        application = (MyApplication) TaskListActivity.this.getApplicationContext();
        btn_add=findViewById(R.id.add_task);
        linearLayoutContainer = findViewById(R.id.ll_container);
        mQueue= Volley.newRequestQueue(TaskListActivity.this);
        projectId=(String)getIntent().getExtras().get("projectId");
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TaskListActivity.this,NewTaskActivity.class);
                intent.putExtra("projectId",projectId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        linearLayoutContainer.removeAllViews();
        getTask();
    }

    private void getTask(){
        String url="https://voa.yinjiahui.cn/api/project/"+projectId+"/task";
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
                            List<Task> l_Task = mapper.readValue(dataJson, new TypeReference<List<Task>>() {
                            });
                            CreateView(l_Task);
                            //System.out.println(l_Task);
                            //System.out.println(l_MyProject);

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


    private void CreateView(List<Task>list){
        View.OnClickListener itemClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TaskListActivity.this, TaskActivity.class);
                intent.putExtra("data",(Task)view.getTag());
                intent.putExtra("projectId",projectId);
                startActivity(intent);
                System.out.println(view.getTag().toString());
            }
        };
        for (int i = 0; i < list.size(); i++) {
            View item = getLayoutInflater().inflate(R.layout.layout_my_project_item, null);
            item.setTag(list.get(i));
            CheckBox checkBox=item.findViewById(R.id.cb_task_item);
            TextView textView=item.findViewById(R.id.tv_task_note);
            checkBox.setText(list.get(i).getTitle());
            textView.setText(list.get(i).getDeadline());
            switch (list.get(i).getPriority()){
                case "0":
                case "1":
                    checkBox.setTextColor(Color.RED);
                    break;
                case "2":
                    checkBox.setTextColor(Color.BLACK);
                    break;
                default:
                    checkBox.setTextColor(Color.GREEN);
                    break;
            }
            if(list.get(i).getFinish().equals("1")){
                checkBox.setChecked(true);;
            }
            linearLayoutContainer.addView(item);
            item.setOnClickListener(itemClick);
        }
    }
}