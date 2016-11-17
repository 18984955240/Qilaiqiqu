package com.qizhi.qilaiqiqu.fragment;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.activity.Activity_NewApplyResultActivity;
import com.qizhi.qilaiqiqu.activity.Activity_NewCommentActivity;
import com.qizhi.qilaiqiqu.activity.Activity_NewCommentInformActivity;
import com.qizhi.qilaiqiqu.activity.Activity_NewConsultInformActivity;
import com.qizhi.qilaiqiqu.activity.RiderCommentActivity;
import com.qizhi.qilaiqiqu.activity.Rider_NewComemntResultActivity;
import com.qizhi.qilaiqiqu.activity.Rider_NewRefundActivity;
import com.qizhi.qilaiqiqu.activity.Riding_NewCommentActivity;
import com.qizhi.qilaiqiqu.activity.Riding_NewDetailActivity;
import com.qizhi.qilaiqiqu.adapter.MyMessageAdapter;
import com.qizhi.qilaiqiqu.model.SystemMessageModel;
import com.qizhi.qilaiqiqu.utils.RefreshLayout;
import com.qizhi.qilaiqiqu.utils.RefreshLayout.OnLoadListener;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.qizhi.qilaiqiqu.utils.XUtilsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Administrator
 *			系统消息中心
 */
public class MessageFragment extends Fragment implements OnItemClickListener,OnRefreshListener, OnLoadListener,OnItemLongClickListener{
	private View view;
	private Context context;
	
	private ListView myMessageList;		//系统消息的集合
	private MyMessageAdapter adapter;
	private XUtilsUtil xUtilsUtil;
	private Integer pageIndex = 1;
	private SharedPreferences preferences;
	private List<SystemMessageModel> list;
	private RefreshLayout swipeLayout;
	private View header;
	
	
	@SuppressLint("InlinedApi")
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_message,null);
		myMessageList = (ListView) view.findViewById(R.id.list_fragment_message);
		context = getActivity();
		preferences = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
		list = new ArrayList<SystemMessageModel>();
		xUtilsUtil = new XUtilsUtil();
		header = View.inflate(getActivity(),R.layout.header, null);
		swipeLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		myMessageList.addHeaderView(header);
		adapter = new MyMessageAdapter(context, list);
		myMessageList.setAdapter(adapter);
		myMessageList.setOnItemClickListener(this);
		myMessageList.setOnItemLongClickListener(this);
		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setOnLoadListener(this);
		return view;
	}


	@Override
	public void onResume() {
        if(null != preferences.getString("uniqueKey", null)){
		    data();
        }
		super.onResume();
	}
	private void data() {
		pageIndex = 1;
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
		params.addBodyParameter("pageIndex", pageIndex + "");
		params.addBodyParameter("pageSize", "20");
		params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null));

		new XUtilsNew().httpPost("message/queryMessageList.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {
			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				try {
					JSONObject jsonObject = new JSONObject(responseInfo.result);
					list.clear();
					Gson gson = new Gson();
					Type type = new TypeToken<List<SystemMessageModel>>(){}.getType();
					List<SystemMessageModel> lists = gson.fromJson(jsonObject.optJSONArray("dataList").toString(), type);
					list.addAll(lists);
					adapter.notifyDataSetChanged();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onMyFailure(HttpException error, String msg) {

			}
		});
	}
	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
		
		int systemMessageId = list.get(position - 1).getId();
		RequestParams params = new RequestParams("UTF_8");
		params.addBodyParameter("id",systemMessageId+"");

		new XUtilsNew().httpPost("message/readMessage.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {
			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
                JSONObject object = null;
                Intent intent = null;
                try {

                    object = new JSONObject(responseInfo.result);
					System.out.println("list.get(position - 1).getType()="+list.get(position - 1).getType());

                    switch (Integer.parseInt(list.get(position - 1).getType())){
                        // 0,自定义消息;
                        case 0:

                            break;

                        // 1,骑游点赞;
                        case 1:
                            // 陪骑详情，弹窗提示
                            intent = new Intent(getActivity(), Riding_NewDetailActivity.class);
                            intent.putExtra("inType","QYJDZ");
							intent.putExtra("articleId", object.getInt("articleId"));
                            intent.putExtra("title",object.getString("title"));
                            intent.putExtra("userName",object.getString("userName"));
                            intent.putExtra("praiseNum",object.getInt("totalCount"));

                            startActivity(intent);
                            break;

                        // 2,骑游评论;
                        case 2:
                            // 用户间互评页面
                            intent = new Intent(getActivity(), Riding_NewCommentActivity.class);
							intent.putExtra("type", "PL");
                            intent.putExtra("articleId", object.getInt("articleId"));
                            intent.putExtra("commentId", object.getInt("commentId"));
                            startActivity(intent);
                            break;

                        // 3,骑游打赏;
                        case 3:
                            // 陪骑详情，弹窗提示
                            intent = new Intent(getActivity(), Riding_NewDetailActivity.class);
                            intent.putExtra("inType","QYJDS");
							intent.putExtra("articleId", object.getInt("articleId"));
                            intent.putExtra("title",object.getString("title"));
                            intent.putExtra("userName",object.getString("userName"));
                            intent.putExtra("integral",object.getInt("grade"));
                            intent.putExtra("sumIntegral",object.getInt("totalGrade"));
                            startActivity(intent);

                            break;

                        // 4,骑游回复;
                        case 4:
                            // 用户间互评页面
                            intent = new Intent(getActivity(), Riding_NewCommentActivity.class);
							intent.putExtra("type", "HF");
                            intent.putExtra("articleId", object.getInt("articleId"));
                            intent.putExtra("commentId", object.getInt("commentId"));
                            startActivity(intent);

                            break;

                        // 5,活动点赞;
                        case 5:

                            break;

                        // 6,活动报名;
                        case 6:
                            // 报名信息结果页
                            intent = new Intent(getActivity(), Activity_NewApplyResultActivity.class);
                            intent.putExtra("name", object.getString("name"));
                            intent.putExtra("number", object.getInt("number"));
                            intent.putExtra("price", object.getDouble("price"));
                            intent.putExtra("title", object.getString("title"));
                            intent.putExtra("mobile", object.getString("mobile"));
                            intent.putExtra("activityId", object.getInt("activityId"));
                            intent.putExtra("priceDesc", object.getString("priceDesc"));
                            startActivity(intent);

                            break;

                        // 7,活动咨询;
                        case 7:
                            // 咨询消息通知页
                            intent = new Intent(getActivity(), Activity_NewConsultInformActivity.class);
                            intent.putExtra("intoType","HDZX");
                            intent.putExtra("commentId", object.getInt("commentId"));
                            intent.putExtra("activityId", object.getInt("activityId"));

							intent.putExtra("photo",object.getString("photo"));
							intent.putExtra("title",object.getString("title"));
                            intent.putExtra("reply",object.getString("reply"));
							intent.putExtra("userName",object.getString("userName"));
                            intent.putExtra("userImage", object.getString("userImage"));
                            startActivity(intent);

                            break;

                        // 8,咨询回复;
                        case 8:
                            // 资讯回复通知
                            intent = new Intent(getActivity(), Activity_NewConsultInformActivity.class);
                            intent.putExtra("intoType","ZXHF");
                            intent.putExtra("commentId", object.getInt("commentId"));
                            intent.putExtra("activityId", object.getInt("activityId"));

                            intent.putExtra("photo",object.getString("photo"));
							intent.putExtra("title",object.getString("title"));
							intent.putExtra("reply",object.getString("reply"));
							intent.putExtra("userName",object.getString("userName"));
							intent.putExtra("userImage", object.getString("userImage"));
                            startActivity(intent);

                            break;

                        // 9,活动点评;
                        case 9:
                            // 活动点评结果通知
                            intent = new Intent(getActivity(), Activity_NewCommentInformActivity.class);
                            intent.putExtra("title", object.getString("title"));
                            intent.putExtra("comment", object.getString("comment"));
                            intent.putExtra("userName", object.getString("userName"));
                            intent.putExtra("userImage", object.getString("userImage"));
                            intent.putExtra("integral", object.getInt("integral"));
                            intent.putExtra("activityId", object.getInt("activityId"));

							intent.putExtra("activityImage", object.getString("photo"));
                            startActivity(intent);

                            break;

                        // 10,陪骑点评;
                        case 10:
                            // 陪骑点评结果通知
                            intent = new Intent(getActivity(), Rider_NewComemntResultActivity.class);
                            intent.putExtra("integral", object.getInt("integral"));
                            intent.putExtra("userName", object.getString("userName"));
                            intent.putExtra("commmet", object.getString("commmet"));
                            intent.putExtra("userImage", object.getString("userImage"));
                            startActivity(intent);

                            break;

                        // 11,陪骑退款;
                        case 11:
                            intent = new Intent(getActivity(), Rider_NewRefundActivity.class);
                            intent.putExtra("orderId", object.getString("orderId"));
                            startActivity(intent);

                            break;

                        // 12,活动邀请打赏;
                        case 12:
                            // 打赏、评论活动页面
                            intent = new Intent(getActivity(), Activity_NewCommentActivity.class);
                            intent.putExtra("activityId", object.getInt("activityId"));
                            intent.putExtra("orderId", object.getString("orderId"));
                            startActivity(intent);

                            break;

                        // 13，配骑士邀请打赏
                        case 13:
                            // 打赏、评论陪骑士页面
                            intent = new Intent(getActivity(), RiderCommentActivity.class);
                            intent.putExtra("riderId", object.getInt("riderId"));
                            intent.putExtra("applyId", object.getInt("applyId"));

                            intent.putExtra("time", object.getString("time"));
                            intent.putExtra("date", object.getString("date"));
                            intent.putExtra("name", object.getString("riderName"));
                            intent.putExtra("orderId", object.getString("orderId"));

                            startActivity(intent);

                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
			}

			@Override
			public void onMyFailure(HttpException error, String msg) {

			}
		});




	}

	private void dataJ() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
		params.addBodyParameter("pageIndex", pageIndex + "");
		params.addBodyParameter("pageSize", "20");
		params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null));

		new XUtilsNew().httpPost("message/queryMessageList.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {
			
			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				String s = responseInfo.result;
				JSONObject jsonObject = null;
				try {
					jsonObject = new JSONObject(s);
				} catch (JSONException e) {
					e.printStackTrace();
				}

                Gson gson = new Gson();
                Type type = new TypeToken<List<SystemMessageModel>>(){}.getType();
                List<SystemMessageModel> lists = gson.fromJson(jsonObject.optJSONArray("dataList").toString(), type);

                if(pageIndex == 1){
                    list.clear();
                    list.addAll(lists);
                    Toasts.show(getActivity(), "刷新成功", 0);
                }else {
                    list.addAll(lists);
					Toasts.show(getActivity(), "加载成功", 0);
                }
                // 更新UI
                adapter.notifyDataSetChanged();
					
			}
			
			@Override
			public void onMyFailure(HttpException error, String msg) {
			}
		});
	}


	@Override
	public void onRefresh() {
		swipeLayout.postDelayed(new Runnable() {

			@Override
			public void run() {
				swipeLayout.setRefreshing(false);
				pageIndex = 1;
					dataJ();
				
			}
		}, 1500);

	}

	@Override
	public void onLoad() {
		swipeLayout.postDelayed(new Runnable() {

			@Override
			public void run() {
				swipeLayout.setLoading(false);
				pageIndex = pageIndex + 1;
				dataJ();
			}
		}, 1500);
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
			showPopupWindow(view,list.get(position - 1).getId(), position - 1);
		return true;
	}
	
	  private void showPopupWindow(View view,final Integer systemMessageId, final int position) {
	  // 一个自定义的布局，作为显示的内容 
		  View mview = LayoutInflater.from(getActivity()).
				  inflate(R.layout.popup_messagefragment, null);
	  
		  Button qubj = (Button) mview.findViewById(R.id.btn_personaldataactivity_phone); 
		  Button scxt =(Button) mview .findViewById(R.id.btn_personaldataactivity_photograph);
		  LinearLayout quxiao = (LinearLayout) mview.findViewById(R.id.quxiao);
	  
	  final PopupWindow popupWindow = new PopupWindow(mview,
	  LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
	  
	  popupWindow.setTouchable(true);
	  
	  popupWindow.setAnimationStyle(R.style.Popupfade_in_out);
	  
	  popupWindow.setTouchInterceptor(new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			return false;
		}
	});
	  qubj.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			popupWindow.dismiss();
			biaoji(systemMessageId, position);
			
		}
	});
	  scxt.setOnClickListener(new OnClickListener() {
			
		@Override
		public void onClick(View v) {
			popupWindow.dismiss();
			delete(systemMessageId,position);
		}
	});
	  quxiao.setOnClickListener(new OnClickListener() {
			
		@Override
		public void onClick(View v) {
			popupWindow.dismiss();
		}
	});
	  
	  
	  
	  // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
	  popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.corners_layout)); // 设置好参数之后再show
	  popupWindow.showAtLocation(view, Gravity.CENTER, 0, Gravity.CENTER);
	  
	  }
	
	private void delete(Integer systemMessageId, final int position) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", systemMessageId + "");
		new XUtilsNew().httpPost("message/deleteMessage.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {
			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
                list.remove(position);
                adapter.notifyDataSetChanged();
			}
			
			@Override
			public void onMyFailure(HttpException error, String msg) {
				
			}
		});
	}
	
	private void biaoji(Integer systemMessageId, final int position){
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", systemMessageId + "");
        new XUtilsNew().httpPost("message/readMessage.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {

			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
                SystemMessageModel messageModel = list.get(position);
                messageModel.setState("YESVIEW");
                list.set(position, messageModel);
                adapter.notifyDataSetChanged();
			}

			@Override
			public void onMyFailure(HttpException error, String msg) {

			}
		});
	}
}
	
