package Acitivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Service.MyApplication;
import Service.MyFile;
import Service.MyStringRequest;

public class FileActivity extends AppCompatActivity {

    private TextView tv_byTime, tv_byProgram;
    private FloatingActionButton floatingActionButton;
    private RequestQueue mQueue;
    private MyApplication application;
    private SwipeRecyclerView swipeRecyclerView_files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        application = (MyApplication) FileActivity.this.getApplicationContext();
        mQueue = Volley.newRequestQueue(FileActivity.this);
        getFile();
    }


    private void getFile() {
        String url = "https://voa.yinjiahui.cn/api/file?teamId=" + application.getCurrentTeam().getId();
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
                            dataJson = node.get("data").toString();
                            List<MyFile> l_Task = mapper.readValue(dataJson, new TypeReference<List<MyFile>>() {
                            });
                            CreateView(l_Task);


                            //System.out.println(l_MyProject);

                        } catch (Exception e) {
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


    private void CreateView(List<MyFile> list) {
        LinearLayout linearLayoutContainer = findViewById(R.id.ll_container);
        View.OnClickListener itemClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setData(Uri.parse("https://voa.yinjiahui.cn/api/files/" + ((MyFile) view.getTag()).getTrueName()));//Url 就是你要打开的网址
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent); //启动浏览器
            }
        };
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                View item = getLayoutInflater().inflate(R.layout.layout_file_item, null);
                item.setTag(list.get(i));
                TextView name = item.findViewById(R.id.tv_file_name);
                TextView size = item.findViewById(R.id.tv_file_size);
                name.setText(list.get(i).getFileName());
                size.setText(list.get(i).getFileSize());
                linearLayoutContainer.addView(item);
                item.setOnClickListener(itemClick);
            }
        }
    }
}