package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.NewsRiderModel;

import java.util.List;

/**
 * 
 * @author Administrator
 *			约骑
 */
public class RiderAppointAdapter extends BaseAdapter {
	
	@SuppressWarnings("unused")
	private Context context;
	private List<NewsRiderModel> list;
	private ViewHolder holder;
	private LayoutInflater inflater;
	
	
	public RiderAppointAdapter(Context context, List<NewsRiderModel> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_rider_appoint, null);
			holder.nameTxt = (TextView) convertView.findViewById(R.id.txt_riderFragment_name);
			holder.timeTxt = (TextView) convertView.findViewById(R.id.txt_riderFragment_time);
			holder.conditionTxt = (TextView) convertView.findViewById(R.id.txt_riderFragment_condition);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.nameTxt.setText(list.get(position).getRiderName());

		String s = list.get(position).getDate();
		holder.timeTxt.setText(s);
		
		if(list.get(position).getIsAgree().equals("0")){
			holder.conditionTxt.setText("待处理");
			holder.conditionTxt.setTextColor(0xFFFFBF86);
		}else if(list.get(position).getIsAgree().equals("1")){
			holder.conditionTxt.setText("已同意");
			holder.conditionTxt.setTextColor(0xFF6DBFED);
		}else if(list.get(position).getIsAgree().equals("2")){
			holder.conditionTxt.setText("被拒绝");
			holder.conditionTxt.setTextColor(0xFFFF3030);
		}
		return convertView;
	}

	public class ViewHolder{
		private TextView nameTxt;
		private TextView timeTxt;
		private TextView conditionTxt;
	}
	
}
