package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.ArticleModel;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.SystemUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by leiqian on 2016/4/20.
 */
public class RidingAdapter extends BaseAdapter {

    private ViewHolder viewHolder;
    private Context context;
    private List<ArticleModel> list;

    public RidingAdapter(Context context, List<ArticleModel> list) {
        this.list = list;
        this.context = context;
        System.out.print("List<ArticleModel> list.size():"+list.size());
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ridinglist, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtRidingListUserName.setText(list.get(position).getUserName());
        viewHolder.txtRidingListRidingInfo.setText(list.get(position).getCreateDate());
        viewHolder.txtRidingListRidingName.setText(list.get(position).getTitle());
        viewHolder.txtRidingListBrowse.setText(list.get(position).getScanNum() + "次浏览");
        viewHolder.txtRidingListComment.setText("(" + list.get(position).getCommentNum() + ")");
        viewHolder.txtRidingListUserlike.setText("(" + list.get(position).getPraiseNum() + ")");

        SystemUtil.Imagexutils(list.get(position).getUserImage(),
                viewHolder.imgRidingListUserImage, context);
        if (list.get(position).getDefaultShowImage() != null) {

            SystemUtil.Imagexutils(
                    list.get(position).getDefaultShowImage(),
                    viewHolder.imgRidingListRidingIamge, context);
        }else{
            viewHolder.imgRidingListRidingIamge.setImageResource(R.drawable.bitmap_homepage);
        }


        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.img_ridingList_ridingIamge)
        ImageView imgRidingListRidingIamge;
        @InjectView(R.id.img_ridingList_userImage)
        CircleImageViewUtil imgRidingListUserImage;
        @InjectView(R.id.txt_ridingList_userName)
        TextView txtRidingListUserName;
        @InjectView(R.id.txt_ridingList_ridingInfo)
        TextView txtRidingListRidingInfo;
        @InjectView(R.id.txt_ridingList_ridingName)
        TextView txtRidingListRidingName;
        @InjectView(R.id.txt_ridingList_browse)
        TextView txtRidingListBrowse;
        @InjectView(R.id.txt_ridingList_userlike)
        TextView txtRidingListUserlike;
        @InjectView(R.id.txt_ridingList_comment)
        TextView txtRidingListComment;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public void updata(){
        notifyDataSetChanged();
    }

}
