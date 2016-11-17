package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.FragmentOrderRiderModel;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by leqian on 2016/6/7.
 */
public class OrderFragmentAdapter extends BaseAdapter {

    ViewHolder holder;
    private Context context;
    private List<FragmentOrderRiderModel> list;


    String type;// 订单类型
    String[] str = {"未支付", "已支付", "已取消", "退款处理中",  "待评价", "已完成", "已退款"};

    public OrderFragmentAdapter(Context context, List<FragmentOrderRiderModel> list, String type) {
        this.list = list;
        this.type = type;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_order_user, null);
            holder = new ViewHolder(convertView);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtOrderTime.setText(list.get(position).getRiderTime()+" "+list.get(position).getTotal_time()+"小时");

        holder.txtOrderName.setText(Html.fromHtml("您约<font color=\"#6dbfed\">"+list.get(position).getRiderName()+"</font>骑行,骑行时间为"));
        if(!"".equals(list.get(position).getState())){
            holder.txtOrderCondition.setText(str[Integer.parseInt(list.get(position).getState())-1]);
        }
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.txt_order_name)
        TextView txtOrderName;
        @InjectView(R.id.txt_order_time)
        TextView txtOrderTime;
        @InjectView(R.id.txt_order_condition)
        TextView txtOrderCondition;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
