package Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

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

import Acitivity.NewProjectActivity;
import Acitivity.TaskListActivity;
import Service.MyApplication;
import Service.MyStringRequest;
import Service.Project;

public class ProjectFragment extends Fragment {
    private RequestQueue mQueue;
    private ViewGroup container;
    private View view;
    private Button btn_add;
    private MyApplication application;
    private LinearLayout linearLayoutContainer;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_project, container, false);
        this.container = container;
        application = (MyApplication) getActivity().getApplicationContext();
        mQueue = Volley.newRequestQueue(getActivity());
        btn_add = view.findViewById(R.id.add_project);
        linearLayoutContainer = view.findViewById(R.id.ll_container);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewProjectActivity.class);
                startActivity(intent);
            }
        });
//        getProject();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getProject();
    }

    private void getProject() {
        String url = "https://voa.yinjiahui.cn/api/team/"+application.getCurrentTeam().getId()+"/project";
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
                            List<Project> l_Project = mapper.readValue(dataJson, new TypeReference<List<Project>>() {
                            });
                            CreateView(l_Project);
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

    private void CreateView(List<Project> list) {
        View.OnClickListener itemClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), TaskListActivity.class);
                intent.putExtra("projectId",((Project)view.getTag()).getId());
                startActivity(intent);
            }
        };
        for (int i = 0; i < list.size(); i++) {
            View item = getLayoutInflater().inflate(R.layout.layout_project_item, null);
            TextView textView = item.findViewById(R.id.tv_project_name);
            textView.setText(list.get(i).getName());
            textView.setTag(list.get(i));
            linearLayoutContainer.addView(item);
            textView.setOnClickListener(itemClick);
        }
    }
}