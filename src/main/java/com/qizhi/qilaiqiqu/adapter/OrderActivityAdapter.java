package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.FragmentOrderActivityModel;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell1 on 2016/9/1.
 */
public class OrderActivityAdapter extends BaseAdapter {

    private Context context;
    private List<FragmentOrderActivityModel> list;

    private ViewHolder holder;

    public OrderActivityAdapter(Context context, List<FragmentOrderActivityModel> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_order_activity, null);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(Html.fromHtml("你报名参与的活动<font color=\"#6dbfed\">"+list.get(position).getActivityTitle()+"</font>"));
        if(list.get(position).getState() == 1){
            holder.txtState.setText("未支付");
        }else if(list.get(position).getState() == 2){
            holder.txtState.setText("支付完成");
        }else if(list.get(position).getState() == 3){
            holder.txtState.setText("已取消");
        }else if(list.get(position).getState() == 4){
            holder.txtState.setText("待评价");
        }else{
            holder.txtState.setText("已完成");
        }


        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.txt_activityName)
        TextView txtName;
        @InjectView(R.id.txt_activityState)
        TextView txtState;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
