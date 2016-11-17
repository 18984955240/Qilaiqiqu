package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.SystemMessageModel;

import java.util.List;


/**
 * 
 * @author Administrator
 *		系统消息
 */
public class MyMessageAdapter extends BaseAdapter {

	private ViewHolder holder;
	private LayoutInflater inflater;
	
	private List<SystemMessageModel> list;
	private Context context;
	
	public MyMessageAdapter(Context context, List<SystemMessageModel> list){
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.list = list;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_list_mymessageactivity, null);
			holder.contentTxt = (TextView) convertView.findViewById(R.id.txt_mymessageactivity_content);
			holder.systemMessageTxt = (TextView) convertView.findViewById(R.id.txt_mymessageactivity_system_message);
			holder.portraitImg = (ImageView) convertView.findViewById(R.id.img_mymessageactivity_portrait);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.contentTxt.setText(list.get(position).getContent());

		switch (Integer.parseInt(list.get(position).getType())) {
			case 0:
				break;

			case 1:
			case 2:
			case 3:
			case 4:
				holder.portraitImg.setImageResource(R.drawable.commert_news);
				holder.systemMessageTxt.setText("游记消息");
				break;

			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 12:
				holder.portraitImg.setImageResource(R.drawable.activity_news);
				holder.systemMessageTxt.setText("活动消息");
				break;

			case 10:
			case 11:
			case 13:
				holder.portraitImg.setImageResource(R.drawable.attend_news);
				holder.systemMessageTxt.setText("陪骑士消息");
				break;
		}


		
		if(list.get(position).getState().equals("0")){
			holder.contentTxt.setTextColor(0xff6dbfed);
		}else{
			holder.contentTxt.setTextColor(0xffB3B3B3);
			holder.systemMessageTxt.setTextColor(0xffB3B3B3);
		}
		
		
		return convertView;
	}

	public class ViewHolder{
		private TextView contentTxt;
		private TextView systemMessageTxt;
		private ImageView portraitImg;
	}
}
