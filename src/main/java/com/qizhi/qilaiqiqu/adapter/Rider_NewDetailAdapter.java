package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.Rider_NewDetailModel;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell1 on 2016/8/29.
 */
public class Rider_NewDetailAdapter extends BaseAdapter {


    private Rider_NewDetailModel model;
    private Context context;

    // HolderPart
    TipHolder tipHolder;
    CommentHolder commentHolder;


    public Rider_NewDetailAdapter(Rider_NewDetailModel model, Context context) {
        this.model = model;
        this.context = context;
    }

    @Override
    public int getCount() {

        return model.getList().size();
    }

    @Override
    public Object getItem(int position) {
        return model.getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null || convertView.getTag() == null){
            if(model.getComments().size() != 0){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_rider_detail_comment, null);
                commentHolder = new CommentHolder(convertView);
                convertView.setTag(commentHolder);
            }else{
                convertView = LayoutInflater.from(context).inflate(R.layout.item_list_personal_none, null);
                tipHolder = new TipHolder(convertView);
                convertView.setTag(tipHolder);
            }

        }else{
            if(model.getComments().size() != 0){
                commentHolder = (CommentHolder) convertView.getTag();
            }else{
                tipHolder = (TipHolder) convertView.getTag();

            }

        }

        setView(position);
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

    private void setView(int position) {
        if(model.getComments().size() != 0){
            commentHolder.txtComment.setText(model.getComments().get(position).getCommentMemo());
            commentHolder.txtCommenter.setText(model.getComments().get(position).getUserName());
            commentHolder.txtReward.setText("打赏了"+model.getComments().get(position).getGrade()+"个计分");
            commentHolder.txtCommentTime.setText(model.getComments().get(position).getCommentDate().split(" ")[0]);
        }else{
            tipHolder.textTip.setText("暂时没有人评论哦！");
        }
    }

}
