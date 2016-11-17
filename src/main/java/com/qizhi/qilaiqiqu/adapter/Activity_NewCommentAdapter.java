package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.Activity_NewCommentModel;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell1 on 2016/9/9.
 */
public class Activity_NewCommentAdapter extends BaseAdapter {

    private List<Activity_NewCommentModel> list;
    private Context context;

    // HolderPart
    TipHolder tipHolder;
    CommentHolder commentHolder;

    private static final int TIP = 0;
    private static final int COMMENT = 1;

    public Activity_NewCommentAdapter(List<Activity_NewCommentModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        int itemSize;
        if(list.size() == 0){
            itemSize = 1;
        }else{
            itemSize = list.size();
        }
        return itemSize;
    }

    //返回样式的数量
    @Override
    public int getViewTypeCount() {

        return 2;
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
    public int getItemViewType(int position) {
        System.out.println("getItemViewType(position) list.size()=="+list.size());
        if(list.size() == 0){
            return TIP;
        }
        return COMMENT;
//        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null || convertView.getTag() == null){
            switch (getItemViewType(position)){
                case TIP:
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_list_personal_none, null);
                    tipHolder = new TipHolder(convertView);
                    convertView.setTag(tipHolder);
                    break;

                case COMMENT:
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_rider_detail_comment, null);
                    commentHolder = new CommentHolder(convertView);
                    convertView.setTag(commentHolder);
                    break;

            }

        }else{
            switch (getItemViewType(position)){
                case TIP:
                    tipHolder = (TipHolder) convertView.getTag();
                    break;

                case COMMENT:
                    commentHolder = (CommentHolder) convertView.getTag();
                    break;
            }

        }

        switch (getItemViewType(position)){
            case TIP:
                tipHolder.textTip.setText("暂时没有点评哦！");
                break;

            case COMMENT:
                commentHolder.txtComment.setText(list.get(position).getComment());
                commentHolder.txtCommenter.setText(list.get(position).getUserName());
                commentHolder.txtReward.setText("打赏了"+list.get(position).getReward()+"个计分");
                commentHolder.txtCommentTime.setText(list.get(position).getCommentDate().split(" ")[0]);
                break;
        }

        return convertView;
    }

    static class CommentHolder {
        @InjectView(R.id.txt_comment)
        TextView txtComment;
        @InjectView(R.id.txt_reward)
        TextView txtReward;
        @InjectView(R.id.txt_commenter)
        TextView txtCommenter;
        @InjectView(R.id.txt_commentTime)
        TextView txtCommentTime;

        CommentHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class TipHolder {
        @InjectView(R.id.txt_tip)
        TextView textTip;

        TipHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
