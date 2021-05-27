package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.voa.R;

import java.util.List;

import Service.TeamMember;
import de.hdodenhof.circleimageview.CircleImageView;

public class MemberListAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater mLayoutInlafter;
    private List<TeamMember>l_member;

    public MemberListAdapter(Context context,List<TeamMember>l_member){
        this.mContext=context;
        this.l_member=l_member;
        mLayoutInlafter=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return l_member.size();
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
        public CircleImageView imageView_head;
        public ImageView imageView_status;
        public TextView tv_username;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=mLayoutInlafter.inflate(R.layout.layout_member_item,null);
            holder=new ViewHolder();
            holder.imageView_head=convertView.findViewById(R.id.civ_head);
            holder.tv_username=convertView.findViewById(R.id.tv_username);
            holder.imageView_status=convertView.findViewById(R.id.iv_status);
            //holder.imageView_head.("标题");
            holder.tv_username.setText(l_member.get(position).getUsername());
            //holder.tvContent.setText("内容");
            if(!l_member.get(position).getAvatar().isEmpty()) {
                Glide.with(mContext).load("https://voa.yinjiahui.cn/files/avatar/" + l_member.get(position).getAvatar()).into(holder.imageView_head);
            }
            else {holder.imageView_head.setImageResource(R.mipmap.ic_launcher);}
            if(l_member.get(position).getOnline().equals("1")){
                holder.imageView_status.setImageResource(R.mipmap.ic_online);
            }
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        //给控件赋值
//        Glide.with(mContext).load("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png").into(holder.imageView);

        return convertView;
    }
}
