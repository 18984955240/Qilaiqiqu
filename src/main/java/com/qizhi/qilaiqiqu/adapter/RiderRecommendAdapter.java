package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.Search_RiderModel;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;


/**
 * 
 * @author Administrator
 *			陪骑士推荐
 */
public class RiderRecommendAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater inflater;
	private List<Search_RiderModel> list;
	private ViewHolder holder;
	
	public RiderRecommendAdapter(Context context, List<Search_RiderModel> list){
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_rider_recommend, null);
			holder.photoImg = (ImageView) convertView.findViewById(R.id.img_riderrecommend_photo);
			holder.pTxt = (TextView) convertView.findViewById(R.id.txt_riderrecommend_p);
			holder.cTxt = (TextView) convertView.findViewById(R.id.txt_riderrecommend_c);
			holder.dTxt = (TextView) convertView.findViewById(R.id.txt_riderrecommend_d);
			holder.describeTxt = (TextView) convertView.findViewById(R.id.txt_riderrecommend_describe);

			holder.tagFlowLayout = (TagFlowLayout) convertView.findViewById(R.id.rider_tag);
			holder.riderName = (TextView) convertView.findViewById(R.id.txt_riderrecommend_name);
			holder.attendPrice = (TextView) convertView.findViewById(R.id.txt_riderrecommend_price);
            holder.riderImage = (ImageView) convertView.findViewById(R.id.img_riderrecommend_pic);

			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.pTxt.setText(list.get(position).getRiderArea());
		holder.cTxt.setText("");
		holder.dTxt.setText("");

		holder.riderName.setText(list.get(position).getUserName());
		if(list.get(position).getRiderPrice() == 0){
			holder.attendPrice.setText("免费");
		}else{
			holder.attendPrice.setText(list.get(position).getRiderPrice()+"元/H");
		}

		if(list.get(position).getLabel() == null ){

		}else{
            String str = list.get(position).getLabel().toString();
            String[] label =  str.split(",");
            holder.tagFlowLayout.setAdapter(new TagAdapter(label) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {

                    TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.tag_item_activive2,null);
                    textView.setText(o.toString());
                    return textView;
                }
            });
        }

		SystemUtil.Imagexutils_photo(list.get(position).getUserImage(), holder.photoImg, context);
        SystemUtil.Imagexutils_new(list.get(position).getPersonalImage().split(",")[0],  holder.riderImage, context);

		holder.describeTxt.setText(list.get(position).getPersonalDesc());
		
		return convertView;
	}
	private class ViewHolder{
		private ImageView photoImg;
		private TextView pTxt;
		private TextView cTxt;
		private TextView dTxt;
		private TextView describeTxt;

		private TextView riderName;
		private TextView attendPrice;
        private ImageView riderImage;
		private TagFlowLayout tagFlowLayout;
	}
}
