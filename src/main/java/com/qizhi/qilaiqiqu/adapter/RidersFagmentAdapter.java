package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.Rider_NewModel;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by dell1 on 2016/8/26.
 */
public class RidersFagmentAdapter extends RecyclerView.Adapter<RidersFagmentAdapter.RiderHolder> {

    private List<Rider_NewModel> list;
    private final Context context;

    MyItemClickListener mItemClickListener;

    public interface MyItemClickListener {
        void onItemClick(View view,int postion);
    }
    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }


    public RidersFagmentAdapter(List<Rider_NewModel> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public RiderHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_riderlist, viewGroup, false);
        return new RiderHolder(view,mItemClickListener);
    }

    @Override
    public void onBindViewHolder(RiderHolder riderHolder, int i) {
        SystemUtil.Imagexutils_new(list.get(i).getPersonalImage().toString().split(",")[0], riderHolder.imageView, context);
        SystemUtil.Imagexutils_photo(list.get(i).getUserImage(), riderHolder.roundImageView, context);

        riderHolder.titleTxt.setText(list.get(i).getUserName());
        if(list.get(i).getPersonalDesc().length() > 50){
            String text = list.get(i).getPersonalDesc().substring(0,47);
            riderHolder.describeTxt.setText(text+"...");
        }else{
            riderHolder.describeTxt.setText(list.get(i).getPersonalDesc());
        }

        riderHolder.commendTxt.setText("("+list.get(i).getCommentNum()+")");
        riderHolder.itemView.setTag(i);
        String[] s = list.get(i).getLabel().split(",");
        riderHolder.activeTag.setAdapter(new TagAdapter(s) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.tag_item_activive3,null);
                textView.setText(o.toString());
                return textView;
            }

        });

        if(i == (list.size()-1)){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,0,dp2px(context,40f));
            riderHolder.linearLayout.setLayoutParams(params);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    // 这里去掉static..
    public class RiderHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView titleTxt;
        TextView describeTxt;
        TextView commendTxt;
        TagFlowLayout activeTag;
        LinearLayout linearLayout;
        TagFlowLayout tagFlowLayout;
        CircleImageViewUtil roundImageView;

        private MyItemClickListener mListener;

        public RiderHolder(View itemView,MyItemClickListener listener){
            super(itemView);
            mListener = listener;

            imageView= (ImageView) itemView.findViewById(R.id.masonry_item_img );
            titleTxt= (TextView) itemView.findViewById(R.id.masonry_item_title);
            describeTxt = (TextView) itemView.findViewById(R.id.masonry_item_describe);
            commendTxt = (TextView) itemView.findViewById(R.id.masonry_item_comment);
            roundImageView = (CircleImageViewUtil) itemView.findViewById(R.id.masonry_item_userImg);
            activeTag = (TagFlowLayout) itemView.findViewById(R.id.masonry_item_flowlayout);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.masonry_item_layout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getPosition());
            }
        }
    }

    /**
     * dp转px
     *
     * @param context
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }


}
