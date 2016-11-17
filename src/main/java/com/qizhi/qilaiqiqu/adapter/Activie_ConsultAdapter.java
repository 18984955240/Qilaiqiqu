package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.Active_ConsultModel;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell1 on 2016/8/18.
 */
public class Activie_ConsultAdapter extends BaseAdapter {
    private Context context;
    private ViewHolder holder;
    private List<Active_ConsultModel> list;

    public Activie_ConsultAdapter(Context context, List<Active_ConsultModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_discussactivity, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context).load(SystemUtil.IMGPHTH_NEW +list.get(position).getUserImage()).into(holder.imgPhoto);

        holder.txtDiscusspeo.setText(list.get(position).getUserName()+":");
        holder.txtContent.setText(list.get(position).getComment());
        if(list.get(position).getList().size()>0){
            holder.layoutRepaly.setVisibility(View.VISIBLE);
            holder.txtRepaly.setText(list.get(position).getListComment());
            holder.txtRepalyer.setText(list.get(position).getListCommenter());
        }

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.img_discussactivity_photo)
        CircleImageViewUtil imgPhoto;
        @InjectView(R.id.txt_discussactivity_discusspeo)
        TextView txtDiscusspeo;
        @InjectView(R.id.txt_discussactivity_content)
        TextView txtContent;
        @InjectView(R.id.txt_discussactivity_authorComment)
        TextView txtAuthorComment;
        @InjectView(R.id.layout_discussactivity_authorComment)
        LinearLayout layoutAuthorComment;
        @InjectView(R.id.txt_discussactivity_reward)
        TextView txtReward;
        @InjectView(R.id.layout_discussactivity_discuss_return)
        LinearLayout layoutDiscussReturn;
        @InjectView(R.id.layout_discussactivity_repaly)
        LinearLayout layoutRepaly;
        @InjectView(R.id.txt_discussactivity_replyer)
        TextView txtRepalyer;
        @InjectView(R.id.txt_discussactivity_reply)
        TextView txtRepaly;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
