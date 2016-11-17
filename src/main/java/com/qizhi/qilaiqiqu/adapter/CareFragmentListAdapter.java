package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.FansModel;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;

import java.util.ArrayList;

/**
 * 
 * @author Administrator
 *		我的关注
 */

public class CareFragmentListAdapter extends BaseAdapter {


	private SharedPreferences preferences;
	private ArrayList<FansModel> list;
	private Context context;
	private ViewHolder holder;

	public CareFragmentListAdapter(Context context, ArrayList<FansModel> list) {
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

	public void removeItem(int position) {
		list.remove(position);
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		holder = new ViewHolder();
		if (view == null || view.getTag() == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.item_list_carefragment, null);
			holder.photoImg = (ImageView) view
					.findViewById(R.id.img_careFragmentList_photo);
			holder.nickTxt = (TextView) view
					.findViewById(R.id.txt_careFragmentList_nick);
			holder.careTxt = (TextView) view
					.findViewById(R.id.txt_careFragmentList_care);
			holder.personalityTxt = (TextView) view
					.findViewById(R.id.txt_careFragmentList_personality);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		SystemUtil.Imagexutils(list.get(position).getUserImage(), holder.photoImg, context);
		/*Picasso.with(context)
				.load("http://weride.oss-cn-hangzhou.aliyuncs.com/"
						+ list.get(position).getUserImage())
				.into(holder.photoImg);*/
		holder.nickTxt.setText(list.get(position).getUserName());
		holder.personalityTxt.setText(list.get(position).getUserMemo());
		holder.careTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				deleteCare(position);
			}
		});
		return view;
	}

	public class ViewHolder {
		private ImageView photoImg;
		private TextView nickTxt;
		private TextView careTxt;
		private TextView personalityTxt;
	}

	public void deleteCare(final int position) {
		preferences = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);

		RequestParams params = new RequestParams("UTF-8");
		params.addBodyParameter("userId", list.get(position).getUserId() + "");
		params.addBodyParameter("followId", preferences.getInt("userId", -1) + "");
		params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null) + "");
		params.addBodyParameter("tag", "2");
		new XUtilsNew().httpPost("rider/followRider.html", params, false, context, new XUtilsNew.XUtilsCallBackPost() {
			
			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
                removeItem(position);
			}
			
			@Override
			public void onMyFailure(HttpException error, String msg) {
                Toasts.show(context, "无法连接服务器，请检查网络", 0);
			}
		});
	}

}
