package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.Active_NewModel;
import com.qizhi.qilaiqiqu.utils.SystemUtil;

import java.util.List;

/**
 * @author Administrator
 *         活动管理
 */
public class ManageAdapter extends BaseAdapter {

    private Context context;
    private List<Active_NewModel> list;
    private ViewHolder holder;
    private LayoutInflater inflater;
    private int userId;

    public static int height;

    private String from;

    public ManageAdapter(Context context, List<Active_NewModel> list, int userId, String from) {
        inflater = LayoutInflater.from(context);

        this.list = list;
        this.from = from;
        this.userId = userId;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = new ViewHolder();
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_list_manage_fragment2, null);

            holder.titleTxt = (TextView) convertView.findViewById(R.id.txt_managefragment_title);
            holder.applyTxt = (TextView) convertView.findViewById(R.id.txt_managefragment_apply);
            holder.scanNum = (TextView) convertView.findViewById(R.id.txt_managefragment_browse);
            holder.endDate = (TextView) convertView.findViewById(R.id.txt_managefragment_endDate);
            holder.username = (TextView) convertView.findViewById(R.id.txt_managefragment_userName);
            holder.userIamge = (ImageView) convertView.findViewById(R.id.img_managefragment_userImage);
            holder.photoImg = (ImageView) convertView.findViewById(R.id.img_managefragment_photo);
            holder.want = (TextView) convertView.findViewById(R.id.txt_managefragment_want);
            holder.wantImg = (ImageView) convertView.findViewById(R.id.img_managefragment_want);

            holder.info = (TextView) convertView.findViewById(R.id.txt_managefragment_activityInfo);
            holder.moneyTxt = (TextView) convertView.findViewById(R.id.txt_managefragment_money);

            holder.layout = (RelativeLayout) convertView.findViewById(R.id.layout_managefragment_info);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (from.equals("news")) {
            holder.layout.setVisibility(View.GONE);
        }

        if(list.get(position).getTitle().length()>16){
            holder.titleTxt.setText(list.get(position).getTitle().substring(0,16)+"...");
        }else{
            holder.titleTxt.setText(list.get(position).getTitle());

        }
        if(list.get(position).getOrginName().length()>5){
            holder.username.setText(list.get(position).getOrginName()+"...");
        }else{
            holder.username.setText(list.get(position).getOrginName());
        }
        holder.endDate.setText(list.get(position).getYdjzsj());
        holder.scanNum.setText(list.get(position).getScanNum() + "次浏览");
        holder.info.setText("发布于:" + list.get(position).getCreate_date().split(" ")[0]);
        holder.want.setText(list.get(position).getPraiseNum() + "人想去");


        if (list.get(position).isPraiseState()) {
            holder.wantImg.setImageResource(R.drawable.want);
        } else {
            holder.wantImg.setImageResource(R.drawable.want_unchosen);
        }

        if (userId == list.get(position).getUserId()) {
            holder.applyTxt.setText("我发起");
        } else {
            if (list.get(position).getSignCount() != 0) {
                holder.applyTxt.setText(list.get(position).getSignCount() + "人已报名");
            } else {
                holder.applyTxt.setBackgroundResource(R.drawable.corners_apply_text);
                holder.applyTxt.setText("立即报名");
            }
        }

        SystemUtil.Imagexutils_new(list.get(position).getUserImage(), holder.userIamge, context);
        SystemUtil.Imagexutils_new(list.get(position).getPhoto().split(",")[0], holder.photoImg, context);

        if (list.get(position).getPrice() == 0) {
//            holder.moneyTxt.setVisibility(View.GONE);
            holder.moneyTxt.setText("免费");
        } else {
//            holder.moneyTxt.setVisibility(View.VISIBLE);
            holder.moneyTxt.setText("收费");
        }


        return convertView;
    }

    private class ViewHolder {
        private TextView titleTxt;
        private TextView applyTxt;
        private TextView moneyTxt;
        private ImageView photoImg;
        private TextView scanNum;
        private TextView endDate;
        private TextView username;
        private ImageView userIamge;
        private TextView info;
        private TextView want;
        private ImageView wantImg;
        private RelativeLayout layout;

    }

    public void update() {
        notifyDataSetChanged();
    }
}
