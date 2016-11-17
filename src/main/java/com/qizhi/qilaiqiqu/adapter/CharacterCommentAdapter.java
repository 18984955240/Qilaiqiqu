package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.CharacterCommentModel;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.SystemUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell1 on 2016/5/25.
 */
public class CharacterCommentAdapter extends BaseAdapter {

    List<CharacterCommentModel> list;
    Context context;
    ViewHolder holder;

    public CharacterCommentAdapter(Context context, List<CharacterCommentModel> list) {
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

        SystemUtil.Imagexutils_new(list.get(position).getUserImage(),holder.imgPhoto,context);
        holder.txtDiscusspeo.setText(list.get(position).getUserName()+":");
        holder.txtContent.setText(list.get(position).getCommentMemo());
        if(list.get(position).getCommentList().size()>0){
            holder.layoutAuthorComment.setVisibility(View.VISIBLE);
            for(int i = 0;i<list.get(position).getCommentList().size();i++){
                holder.txtAuthorComment.setText(list.get(position).getCommentList().get(i));
            }
        }else{
            holder.layoutAuthorComment.setVisibility(View.GONE);
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
        @InjectView(R.id.layout_discussactivity_authorComment)
        LinearLayout layoutAuthorComment;
        @InjectView(R.id.txt_discussactivity_authorComment)
        TextView txtAuthorComment;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
