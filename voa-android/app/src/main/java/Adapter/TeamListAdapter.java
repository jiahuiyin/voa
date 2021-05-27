package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.voa.R;

import java.util.List;

import Service.Team;
import Service.TeamMember;
import de.hdodenhof.circleimageview.CircleImageView;

public class TeamListAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater mLayoutInlafter;
    private List<Team> l_team;

    public TeamListAdapter(Context context,List<Team>l_team){
        this.mContext=context;
        this.l_team=l_team;
        mLayoutInlafter=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return l_team.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder{
        public TextView textView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=mLayoutInlafter.inflate(R.layout.layout_team_item,null);
            holder=new ViewHolder();
            holder.textView=convertView.findViewById(R.id.tv_team_name);
            //holder.imageView_head.("标题");
            holder.textView.setText(l_team.get(position).getTeamName());
            //holder.tvContent.setText("内容");
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        //给控件赋值
//        Glide.with(mContext).load("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png").into(holder.imageView);

        return convertView;
    }
}
