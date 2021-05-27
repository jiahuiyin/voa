package Fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Service.MyApplication;
import Service.MyProject;
import Service.MyStringRequest;
import Service.Tasks;

public class MyProjectFragment extends Fragment {
    private TextView tv_byTime,tv_byProgram;
    private FloatingActionButton floatingActionButton;
    private RequestQueue mQueue;
    private ViewGroup container;
    private View view;
    private MyApplication application;
    LinearLayout linearLayoutContainer;
    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){
        view=inflater.inflate(R.layout.activity_my_project ,container,false) ;
        this.container=container;
        application = (MyApplication) getActivity().getApplicationContext();
        linearLayoutContainer=view.findViewById(R.id.ll_container);
        //tv_byTime=view.findViewById(R.id.tv_group_by_create);
        //tv_byProgram=view.findViewById(R.id.tv_group_by_program);
        mQueue= Volley.newRequestQueue(getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        linearLayoutContainer.removeAllViews();
        getMyProject();
    }

    private void getMyProject(){
        String url="https://voa.yinjiahui.cn/api/team/"+application.getCurrentTeam().getId()+"/myproject";
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
                            JsonNode tasksNode;

                            LinkedList<MyProject> l_MyProject=new LinkedList<>();
                            //data链表
                            for(int i=0;i<node.get("data").size();i++){
                                dataJson = node.get("data").get(i).toString();
                                tasksNode = mapper.readTree(dataJson);
                                List<Tasks> l_tasks = mapper.readValue(tasksNode.get("tasks").toString(),new TypeReference<List<Tasks>>(){});
                                l_MyProject.add(new MyProject(node.get("data").get(i).get("id").toString(),node.get("data").get(i).get("name").toString(),l_tasks));
                            }
                            CreateView(l_MyProject);
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


    private void CreateView(LinkedList<MyProject>list){
        View.OnClickListener itemClick=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(view.getTag());
            }
        };
        for(int i=0;i<list.size();i++)
          for(int j=0;j<list.get(i).getTasks().size();j++){
            View item = getLayoutInflater().inflate(R.layout.layout_my_project_item, null);
            item.setTag(list.get(i).getTasks().get(j));
            CheckBox checkBox=item.findViewById(R.id.cb_task_item);
            TextView textView=item.findViewById(R.id.tv_task_note);
            checkBox.setText(list.get(i).getTasks().get(j).getTitle());
            textView.setText(list.get(i).getTasks().get(j).getDeadline());
            switch (list.get(i).getTasks().get(j).getPriority()){
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
            linearLayoutContainer.addView(item);
            item.setOnClickListener(itemClick);
        }
    }

}