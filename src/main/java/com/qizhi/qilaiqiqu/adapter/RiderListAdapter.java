package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.RiderRecommendModel;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;


/**
 * 
 * @author Administrator
 *		陪骑士列表
 */
public class RiderListAdapter extends RecyclerView.Adapter<RiderListAdapter.MasonryView> implements OnClickListener{
	 private List<RiderRecommendModel> products;
	    private Context context;


	    public RiderListAdapter(List<RiderRecommendModel> list,Context context) {
	        products=list;
	        this.context = context;
	    }

	    @Override
	    public MasonryView onCreateViewHolder(ViewGroup viewGroup, int i) {
	        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_riderlist, viewGroup, false);
	       
	        view.setOnClickListener(this);
	        return new MasonryView(view);
	    }

	    @Override
	    public void onBindViewHolder(final MasonryView masonryView, int position) {
	    			
			SystemUtil.Imagexutils(products.get(position).getRiderImage().split(",")[0], masonryView.imageView, context);
			SystemUtil.loadImagexutils(products.get(position).getUserImage(),masonryView.roundImageView,context);
			masonryView.titleTxt.setText(products.get(position).getUserName());
			if(products.get(position).getRiderMemo().length() > 50){
				String text = products.get(position).getRiderMemo().substring(0,47);
	    		masonryView.describeTxt.setText(text+"...");
			}else{
				masonryView.describeTxt.setText(products.get(position).getRiderMemo());
			}
			masonryView.commendTxt.setText("("+products.get(position).getCommentCount()+")");
			masonryView.itemView.setTag(position);
			String[] s = products.get(position).getLabel().split(",");
			masonryView.activeTag.setAdapter(new TagAdapter(s) {
				@Override
				public View getView(FlowLayout parent, int position, Object o) {
					TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.tag_item_activive3,null);
					textView.setText(o.toString());
					return textView;
				}

			});

	    }

	    @Override
	    public int getItemCount() {
	        return products.size();
	    }

	    public static class MasonryView extends  RecyclerView.ViewHolder{

	        ImageView imageView;
	        TextView titleTxt;
	        TextView describeTxt;
			TextView commendTxt;
			TagFlowLayout activeTag;
			TagFlowLayout tagFlowLayout;
			CircleImageViewUtil roundImageView;

	       public MasonryView(View itemView){
	           super(itemView);
	           imageView= (ImageView) itemView.findViewById(R.id.masonry_item_img );
	           titleTxt= (TextView) itemView.findViewById(R.id.masonry_item_title);
	           describeTxt = (TextView) itemView.findViewById(R.id.masonry_item_describe);
			   commendTxt = (TextView) itemView.findViewById(R.id.masonry_item_comment);
			   roundImageView = (CircleImageViewUtil) itemView.findViewById(R.id.masonry_item_userImg);
			   activeTag = (TagFlowLayout) itemView.findViewById(R.id.masonry_item_flowlayout);
	       }

	    }
	    
	    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
	    public static interface OnRecyclerViewItemClickListener {
	        void onItemClick(View view , Integer position);
	    }
	    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
	        this.mOnItemClickListener = listener;
	    }

		@Override
		public void onClick(View v) {
			if (mOnItemClickListener != null) {
	            //注意这里使用getTag方法获取数据
	            mOnItemClickListener.onItemClick(v,(Integer)v.getTag());
	        }
		}
	    
}
