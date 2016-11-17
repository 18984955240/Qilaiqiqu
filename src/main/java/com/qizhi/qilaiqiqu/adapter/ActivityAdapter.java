package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.RotateTextView;
import com.qizhi.qilaiqiqu.utils.SystemUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell1 on 2016/4/22.
 */
public class ActivityAdapter extends BaseAdapter {

    private List<?> list;
    private ViewHolder holder;
    private Context context;

    public ActivityAdapter(Context context,List<?> list){
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_manage_fragment2, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtManagefragmentTitle.setText("");
        holder.txtManagefragmentBrowse.setText("次浏览");
        holder.txtManagefragmentUserName.setText("");
        holder.txtManagefragmentEndDate.setText("");
        holder.txtManagefragmentWant.setText("人想去");
        holder.txtmanagefragmentactivityInfo.setText("");

        SystemUtil.loadImagexutils("",holder.imgManagefragmentPhoto,context);
        SystemUtil.loadImagexutils("",holder.imgManagefragmentUserImage,context);

        if(true){
            holder.txtManagefragmentApply.setText("人已报名");
        }else{
            holder.txtManagefragmentApply.setText("立即报名");
        }
        if(true){
            holder.txtManagefragmentMoney.setVisibility(View.VISIBLE);
        } else {
            holder.txtManagefragmentMoney.setVisibility(View.GONE);
        }
        if(true){
            holder.imgManagefragmentWant.setImageResource(R.drawable.want_unchosen);
        } else {
            holder.imgManagefragmentWant.setImageResource(R.drawable.want);
        }

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.txt_managefragment_title)
        TextView txtManagefragmentTitle;
        @InjectView(R.id.img_managefragment_photo)
        ImageView imgManagefragmentPhoto;
        @InjectView(R.id.txt_managefragment_money)
        RotateTextView txtManagefragmentMoney;
        @InjectView(R.id.img_managefragment_userImage)
        CircleImageViewUtil imgManagefragmentUserImage;
        @InjectView(R.id.txt_managefragment_userName)
        TextView txtManagefragmentUserName;
        @InjectView(R.id.txt_managefragment_activityInfo)
        TextView txtmanagefragmentactivityInfo;
        @InjectView(R.id.txt_managefragment_endDate)
        TextView txtManagefragmentEndDate;
        @InjectView(R.id.img_managefragment_want)
        ImageView imgManagefragmentWant;
        @InjectView(R.id.txt_managefragment_apply)
        TextView txtManagefragmentApply;
        @InjectView(R.id.txt_managefragment_want)
        TextView txtManagefragmentWant;
        @InjectView(R.id.txt_managefragment_browse)
        TextView txtManagefragmentBrowse;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public void updata(){
        notifyDataSetChanged();
    }
}
