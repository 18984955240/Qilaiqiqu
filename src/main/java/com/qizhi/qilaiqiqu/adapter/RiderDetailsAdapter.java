package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.RidingCommentModel;
import com.qizhi.qilaiqiqu.utils.SystemUtil;

import java.util.List;

public class RiderDetailsAdapter extends BaseAdapter {

	private List<RidingCommentModel> list;
	private Context context;
	private LayoutInflater inflater;
	private ViewHolder holder;

	int size = 0;

	public RiderDetailsAdapter(Context context, List<RidingCommentModel> list) {
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (list.size() == 0) {
			size = 1;
		} else {
			size = list.size();
		}
		return size;
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
		if (list.size() == 0) {
			convertView = inflater.inflate(R.layout.item_list_nocomment, null);
		} else {
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(
						R.layout.item_list_discussactivity, null);
				holder.contentTxt = (TextView) convertView
						.findViewById(R.id.txt_discussactivity_content);
				holder.discusspeoTxt = (TextView) convertView
						.findViewById(R.id.txt_discussactivity_discusspeo);
				holder.reward = (TextView) convertView
						.findViewById(R.id.txt_discussactivity_reward);
				holder.portraitImg = (ImageView) convertView
						.findViewById(R.id.img_discussactivity_photo);
				holder.discussReturnLayout = (LinearLayout) convertView
						.findViewById(R.id.layout_discussactivity_discuss_return);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			SystemUtil.Imagexutils(list.get(position).getUserImage(),
					holder.portraitImg, context);

			String s = "<font color='#6dbfed'>"
					+ list.get(position).getUserName() + ":</font>";
			holder.discusspeoTxt.setText(Html.fromHtml(s));
			holder.contentTxt.setText(list.get(position).getCommentMemo());
			if(list.get(position).getIntegral() != 0){
				holder.reward.setVisibility(View.VISIBLE);
				holder.reward.setText("打赏了"+list.get(position).getIntegral()+"个积分");
			}
		}
		return convertView;
	}

	private class ViewHolder {
		private TextView contentTxt;
		private TextView discusspeoTxt;
		private ImageView portraitImg;
		private LinearLayout discussReturnLayout;
		private TextView reward;
	}

	public void notifyData() {
		notifyDataSetChanged();
	}
}
