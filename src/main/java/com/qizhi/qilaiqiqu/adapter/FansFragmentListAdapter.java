package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.FansModel;
import com.qizhi.qilaiqiqu.utils.SystemUtil;

import java.util.List;


/**
 * 
 * @author Administrator
 *				粉丝
 */
public class FansFragmentListAdapter extends BaseAdapter {

	private List<FansModel> list;
	private Context context;
	private ViewHolder holder;
	
	public FansFragmentListAdapter(Context context, List<FansModel> list){
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
	public View getView(int position, View view, ViewGroup arg2) {
		holder = new ViewHolder();
		if (view == null || view.getTag() == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.item_list_fansfragment, null);
			holder.photoImg = (ImageView) view
					.findViewById(R.id.img_fansFragmentList_photo);
			holder.nickTxt = (TextView) view
					.findViewById(R.id.txt_fansFragmentList_nick);
			holder.personalityTxt = (TextView) view
					.findViewById(R.id.txt_fansFragmentList_personality);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		SystemUtil.Imagexutils(list.get(position).getUserImage(), holder.photoImg, context);
		holder.nickTxt.setText(list.get(position).getUserName());
		holder.personalityTxt.setText(list.get(position).getUserMemo());
		
		return view;
	}

	public class ViewHolder{
		private ImageView photoImg;
		private TextView nickTxt;
		private TextView personalityTxt;
	}
	
	
}
