package com.qizhi.qilaiqiqu.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.adapter.DiscussListAdapter;
import com.qizhi.qilaiqiqu.model.CommentPaginationListModel;
import com.qizhi.qilaiqiqu.model.Riding_NewCommentModel;
import com.qizhi.qilaiqiqu.utils.RefreshLayout;
import com.qizhi.qilaiqiqu.utils.RefreshLayout.OnLoadListener;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.qizhi.qilaiqiqu.utils.XUtilsUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 骑游记评论列表
 */
public class Riding_NewCommentListActivity extends MyBaseActivity implements
		OnClickListener, OnItemClickListener, OnTouchListener,
		OnRefreshListener, OnLoadListener {

	private LinearLayout backLayout;

	private ListView discussList;

	private EditText contentEdit;

	private Button discussBtn;

	private TextView titleTxt;

	private DiscussListAdapter adapter;

	private int articleId;
	private XUtilsUtil xUtilsUtil;
	private SharedPreferences preferences;

	private CommentPaginationListModel commentPaginationListModel;
	private List<Riding_NewCommentModel> list;

	private boolean flag = true;

	private int commentId = -1;
	private int replayId = -1;
	private InputMethodManager imm;

	private int pageIndex = 1;

	private RefreshLayout swipeLayout;
	private View header;

	int pageFlag;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discuss);

		initView();
		initEvent();
	}

	@SuppressLint("InlinedApi")
	@SuppressWarnings("deprecation")
	private void initView() {
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		articleId = getIntent().getIntExtra("articleId", -1);
		pageFlag = getIntent().getIntExtra("flag", -1);
		xUtilsUtil = new XUtilsUtil();
		commentPaginationListModel = new CommentPaginationListModel();
		list = new ArrayList<Riding_NewCommentModel>();
		preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);

		backLayout = (LinearLayout) findViewById(R.id.layout_discussactivity_back);

		discussBtn = (Button) findViewById(R.id.btn_discussactivity_discuss);

		contentEdit = (EditText) findViewById(R.id.edt__discussactivity_content);

		discussList = (ListView) findViewById(R.id.list_discussactivity_discuss);
		titleTxt = (TextView) findViewById(R.id.txt_discussactivity_title);
		titleTxt.setText("游记评论");

		commentId = getIntent().getIntExtra("commentId", -1);
		if (commentId != -1) {
			// contentEdit.setHint("回复" + list.get(position).getUserName() +
			// ":");
			contentEdit.setFocusable(true);
			imm.showSoftInputFromInputMethod(contentEdit.getWindowToken(), 0);
			imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
		}
		header = View.inflate(this, R.layout.header, null);
		swipeLayout = (RefreshLayout) findViewById(R.id.swipe_container);
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		discussList.addHeaderView(header);

		if (pageFlag == 1){
			findViewById(R.id.layout__discussactivity_comment).setVisibility(View.GONE);
		}
	}

	private void initEvent() {

		backLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_discussactivity_discuss:
			// if (!"".equals(contentEdit.getText().toString().trim())) {
			commentRiding();
			/*
			 * }else{
			 * 
			 * }
			 */
			break;
		case R.id.layout_discussactivity_back:
			finish();
			break;
		}
	}

	private void  commentRiding() {
		RequestParams params = new RequestParams("UTF-8");
		String url;
		if(flag){
			params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
			params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null));
			params.addBodyParameter("artId", articleId + "");
			params.addBodyParameter("commentMemo", contentEdit.getText().toString());
			url = "article/releaseComment.html";
		}else{
			params.addBodyParameter("artId", articleId + "");
			params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
			params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null));
			params.addBodyParameter("commentMemo", contentEdit.getText().toString());
			params.addBodyParameter("commentId", commentId+"");
			params.addBodyParameter("replyId", commentId+"");

			url = "article/replyComment.html";
		}

		new XUtilsNew().httpPost(url, params, false, this, new XUtilsNew.XUtilsCallBackPost() {

			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				contentEdit.setText("");
				contentEdit.setHint("说几句吧");
				if (flag) {

					// 提交游记评论
					MobclickAgent.onEvent(Riding_NewCommentListActivity.this, new UmengEventUtil().click63);

					Toasts.show(Riding_NewCommentListActivity.this, "评论成功", 0);
				} else {
					Toasts.show(Riding_NewCommentListActivity.this, "回复成功", 0);
				}
				discussBtn.setText("评论");
				imm.hideSoftInputFromWindow(
						contentEdit.getWindowToken(), 0);

				flag = true;
				pageIndex = 1;
				queryCommentPaginationList();
			}

			@Override
			public void onMyFailure(HttpException error, String msg) {

			}
		});

	}

	Riding_NewCommentModel model;
	List<Riding_NewCommentModel> listComment;

	private void queryCommentPaginationList() {
		RequestParams params = new RequestParams("UTF-8");
		params.addBodyParameter("artId", articleId + "");
		params.addBodyParameter("pageIndex", pageIndex + "");
		params.addBodyParameter("pageSize", "10");

		new XUtilsNew().httpPost("article/queryArticleComment.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				try {
					JSONObject jsonObject = new JSONObject(responseInfo.result);

					Gson gson = new Gson();
					Type type = new TypeToken<List<Riding_NewCommentModel>>() {
					}.getType();

					list = gson.fromJson(jsonObject.getJSONArray("dataList").toString(), type);

					adapter = new DiscussListAdapter(
							Riding_NewCommentListActivity.this, list);
					discussList.setAdapter(adapter);
					discussList
							.setOnTouchListener(Riding_NewCommentListActivity.this);
					discussList
							.setOnItemClickListener(Riding_NewCommentListActivity.this);
					swipeLayout.setOnRefreshListener(Riding_NewCommentListActivity.this);
					swipeLayout.setOnLoadListener(Riding_NewCommentListActivity.this);

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onMyFailure(HttpException error, String msg) {
				Toasts.show(Riding_NewCommentListActivity.this, "无法连接服务器，请检查网络", 0);
			}
		});

	}

	@Override
	protected void onResume() {
		discussBtn.setOnClickListener(this);
		queryCommentPaginationList();
		super.onResume();
		MobclickAgent.onResume(this);
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		JPushInterface.onPause(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		flag = false;
		System.out.println(position);
		commentId = list.get(position - 1).getCommentId();
		System.out.println(commentId);
		if(list.get(position-1).getAttendRiderComments().size() == 0){
			replayId = 0;
		}else{
			replayId = list.get(position-1).getAttendRiderComments().get(list.get(position-1).getAttendRiderComments().size()-1).getCommentId();
		}

		contentEdit.setHint("回复" + list.get(position - 1).getUserName() + ":");
		discussBtn.setText("回复");
		contentEdit.setFocusable(true);
		contentEdit.setFocusableInTouchMode(true);
		contentEdit.requestFocus();
		imm.showSoftInputFromInputMethod(contentEdit.getWindowToken(), 0);
		imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * ACTION_DOWN: 表示用户开始触摸 ACTION_MOVE: 表示用户在移动(手指或者其他) ACTION_UP:表示用户抬起了手指
	 * ACTION_CANCEL:表示手势被取消了
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			System.out.println("ACTION_DOWN");
			imm.hideSoftInputFromWindow(contentEdit.getWindowToken(), 0);
			if ("".equals(contentEdit.getText().toString().trim())) {
				commentId = -1;
				replayId = -1;
				contentEdit.setHint("说几句吧");
				discussBtn.setText("评论");
				flag = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			System.out.println(commentId);
			break;
		case MotionEvent.ACTION_UP:
			System.out.println("ACTION_UP");
			break;
		case MotionEvent.ACTION_CANCEL:
			System.out.println("ACTION_CANCEL");
			break;
		default:
			break;
		}

		return super.onTouchEvent(event);
	}

	private void dataJ() {
		RequestParams params = new RequestParams("UTF-8");
		params.addBodyParameter("artId", articleId + "");
		params.addBodyParameter("pageIndex", pageIndex + "");
		params.addBodyParameter("pageSize", "10");

		new XUtilsNew().httpPost("article/queryArticleComment.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				try {
					JSONObject jsonObject = new JSONObject(responseInfo.result);

					Gson gson = new Gson();
					Type type = new TypeToken<List<Riding_NewCommentModel>>() {
					}.getType();
					List<Riding_NewCommentModel> lists = gson.fromJson(jsonObject.getJSONArray("dataList").toString(), type);
					pageIndex = jsonObject.optInt("pageIndex");

					if (pageIndex == 1) {
						list.clear();
						list.addAll(lists);
					} else {
						list.addAll(lists);
					}
					adapter.notifyDataSetChanged();


				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onMyFailure(HttpException error, String msg) {
				Toasts.show(Riding_NewCommentListActivity.this, "无法连接服务器，请检查网络", 0);
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

}
