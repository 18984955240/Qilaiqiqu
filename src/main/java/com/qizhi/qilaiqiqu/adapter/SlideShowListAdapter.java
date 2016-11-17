package com.qizhi.qilaiqiqu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.activity.PersonalCenterActivity;
import com.qizhi.qilaiqiqu.model.Riding_RidingListModel;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.qizhi.qilaiqiqu.utils.XUtilsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 
 * @author leiqian
 * 
 */

public class SlideShowListAdapter extends BaseAdapter {

	private ViewHolder holder;
	private LayoutInflater inflater;

	private List<Riding_RidingListModel> list;
	private Context context;
	

	private XUtilsUtil xUtilsUtil;
	private SharedPreferences preferences;


	public SlideShowListAdapter(Context context, List<Riding_RidingListModel> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		preferences = context.getSharedPreferences("userLogin",
				Context.MODE_PRIVATE);
		xUtilsUtil = new XUtilsUtil();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(final int position, View view, ViewGroup arg2) {

		if (view == null || view.getTag() == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.item_list_mainactivity_body, null);
			holder.timeTxt = (TextView) view
					.findViewById(R.id.txt_mainList_time);
			holder.titleTxt = (TextView) view
					.findViewById(R.id.txt_mainList_title);
			holder.byBrowseTxt = (TextView) view
					.findViewById(R.id.txt_mainList_byBrowse);
			holder.likeTxt = (TextView) view
					.findViewById(R.id.txt_ridinglist_like);
			holder.likeImg = (ImageView) view
					.findViewById(R.id.img_ridinglist_like);
			holder.photoImg = (ImageView) view
					.findViewById(R.id.img_mainList_photo);
			holder.backgroundImg = (ImageView) view
					.findViewById(R.id.img_mainList_background);
			holder.likeLayout = (FrameLayout) view.findViewById(R.id.layout_ridinglist_like);

			view.setTag(holder);

		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.timeTxt.setText(list.get(position).getCreateDate()
				.substring(0, 10));
		holder.titleTxt.setText(list.get(position).getTitle());
		holder.byBrowseTxt.setText(list.get(position).getScanNum()
				+ list.get(position).getVirtualScan() + "次浏览");
		holder.likeTxt.setText((list.get(position).getPraiseNum() + list
				.get(position).getVirtualPraise()) + "");

		SystemUtil.Imagexutils_photo(list.get(position).getUserImage(), holder.photoImg, context);
		SystemUtil.Imagexutils_new(list.get(position).getDefaultImage(), holder.backgroundImg, context);


		holder.photoImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println(position);
				Intent intent = new Intent(context, PersonalCenterActivity.class).putExtra("userId", list.get(position).getUserId());
				context.startActivity(intent);

			}
		});
		if (list.get(position).isPraiseState()) {
			holder.likeImg.setImageResource(R.drawable.like_press);
			holder.likeTxt.setTextColor(0xffffffff);

		} else {
			holder.likeImg.setImageResource(R.drawable.like_unpress);
			holder.likeTxt.setTextColor(0xffffffff);

		}
		holder.likeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (list.get(position).isPraiseState()) {
					RequestParams params = new RequestParams();
					params.addBodyParameter("id", list.get(position)
							.getId() + "");
					params.addBodyParameter("userId",
							preferences.getInt("userId", -1) + "");
					params.addBodyParameter("uniqueKey",
							preferences.getString("uniqueKey", null));
					params.addBodyParameter("tag", "2");

						new XUtilsNew().httpPost("article/praiseArticle.html", params, false, context, new XUtilsNew.XUtilsCallBackPost() {
							@Override
							public void onMySuccess(ResponseInfo<String> responseInfo) {
								try {
									JSONObject jsonObject = new JSONObject(responseInfo.result);

									list.get(position)
											.setPraiseState(false);
									list.get(position)
											.setPraiseNum(jsonObject.optInt("data"));
									notifyDataSetChanged();

								} catch (JSONException e) {
									e.printStackTrace();
								}
							}

							@Override
							public void onMyFailure(HttpException error, String msg) {

							}
						});

				} else {
					RequestParams params = new RequestParams();
					params.addBodyParameter("id", list.get(position)
							.getId() + "");
					params.addBodyParameter("userId",
							preferences.getInt("userId", -1) + "");
					params.addBodyParameter("uniqueKey",
							preferences.getString("uniqueKey", null));
					params.addBodyParameter("tag", "1");

					if (preferences.getInt("userId", -1) != -1) {
						new XUtilsNew().httpPost("article/praiseArticle.html", params, false, context, new XUtilsNew.XUtilsCallBackPost() {
							@Override
							public void onMySuccess(ResponseInfo<String> responseInfo) {
								try {
									JSONObject jsonObject = new JSONObject(responseInfo.result);

									list.get(position).setPraiseState(
											true);
									list.get(position).setPraiseNum(
											jsonObject.optInt("data"));
									notifyDataSetChanged();

								} catch (JSONException e) {
									e.printStackTrace();
								}
							}

							@Override
							public void onMyFailure(HttpException error, String msg) {

							}
						});
					}
				}
			}
		});

		return view;
	}

	public class ViewHolder {

		private TextView titleTxt;
		private TextView timeTxt;
		private TextView byBrowseTxt;
		private ImageView photoImg;
		private ImageView backgroundImg;
		private ImageView likeImg;
		private TextView likeTxt;
		private FrameLayout likeLayout;
	}

	/**
	 * 异步任务,获取数据
	 * 
	 */
	class GetListTask extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			try {

				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
		}
	}
}
