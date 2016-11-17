package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.RidingCommentsModel;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.SystemUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell1 on 2016/6/29.
 */
public class RidingCommentAdapter extends BaseAdapter {

    private List<RidingCommentsModel.CommentsBean> list;
    private Context context;
    private ViewHolder holder;

    public RidingCommentAdapter(Context context, List<RidingCommentsModel.CommentsBean> list) {
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
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SystemUtil.Imagexutils_photo(list.get(position).getUserImage(), holder.portraitImg, context);

        String s = "<font color='#6dbfed'>"+list.get(position).getUserName()+"</font>:";
        holder.discusspeoTxt.setText(Html.fromHtml(s));
        holder.contentTxt.setText(list.get(position).getCommentMemo());

        holder.discussReturnLayout.removeAllViews();
        if(list.get(position).getAttendRiderComments().size() != 0){
            List<RidingCommentsModel.CommentsBean.AttendRiderCommentsBean> lists = list.get(position).getAttendRiderComments();
            for (RidingCommentsModel.CommentsBean.AttendRiderCommentsBean attendRiderCommentsBean : lists) {
                TextView discussReturnTxt = (TextView) View.inflate(context, R.layout.text_discussactivity_discuss_return, null);

                String str1 = "<font color='#6dbfed'>"+attendRiderCommentsBean.getUserName()+"</font>回复:"+attendRiderCommentsBean.getCommentMemo();
                discussReturnTxt.setText(Html.fromHtml(str1));

                holder.discussReturnLayout.addView(discussReturnTxt);
            }
        }


//        holder.commenter.setText(list.get(position).getUserName());
//        holder.byCommenter.setText(list.get(position).getParentName());
//        holder.content.setText(list.get(position).getCommentMemo());

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.img_discussactivity_photo)
        CircleImageViewUtil portraitImg;
        @InjectView(R.id.txt_discussactivity_discusspeo)
        TextView discusspeoTxt;
        @InjectView(R.id.txt_discussactivity_content)
        TextView contentTxt;
        @InjectView(R.id.layout_discussactivity_discuss_return)
        LinearLayout discussReturnLayout;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


}
