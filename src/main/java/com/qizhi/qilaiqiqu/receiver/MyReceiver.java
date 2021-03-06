package com.qizhi.qilaiqiqu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.qizhi.qilaiqiqu.activity.Activity_NewApplyResultActivity;
import com.qizhi.qilaiqiqu.activity.Activity_NewCommentActivity;
import com.qizhi.qilaiqiqu.activity.Activity_NewCommentInformActivity;
import com.qizhi.qilaiqiqu.activity.Activity_NewConsultInformActivity;
import com.qizhi.qilaiqiqu.activity.RiderCommentActivity;
import com.qizhi.qilaiqiqu.activity.Rider_NewComemntResultActivity;
import com.qizhi.qilaiqiqu.activity.Riding_NewCommentActivity;
import com.qizhi.qilaiqiqu.activity.Riding_NewDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	private String key;
	private String value;

	private String openType;
	private String pushType;

	Intent startIntent = null;
	JSONObject pushMessage = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
			// send the Registration Id to your server...
			System.out.println("[MyReceiver] 接收Registration Id : " + regId);

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
			// processCustomMessage(context, bundle);
			System.out.println("[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
			int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			System.out.println("[MyReceiver] 用户点击打开了通知");
			String EXTRA = bundle.getString(JPushInterface.EXTRA_EXTRA);
			System.out.println("EXTRA:" + EXTRA);

			try {
				JSONObject object = new JSONObject(EXTRA);

				openType = object.getString("openType");
				pushType = object.getString("pushType");
                pushMessage = object.getJSONObject("pushValue").getJSONObject("pushMessage");

				if(openType.equals("APP")){

                    // 骑游点赞;
                    if(pushType.equals("QYJDZ")){
                        // 陪骑详情，弹窗提示
                        startIntent = new Intent(context, Riding_NewDetailActivity.class);
						startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
						startIntent.putExtra("inType","QYJDZ");
                        startIntent.putExtra("articleId", pushMessage.getInt("articleId"));
                        startIntent.putExtra("title",pushMessage.getString("title"));
                        startIntent.putExtra("userName",pushMessage.getString("userName"));
                        startIntent.putExtra("praiseNum",pushMessage.getInt("totalCount"));

                        context.startActivity(startIntent);

                    }else if(pushType.equals("QYJPL")){
                        // 用户间互评页面
                        startIntent = new Intent(context, Riding_NewCommentActivity.class);
						startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startIntent.putExtra("type", "PL");
                        startIntent.putExtra("articleId", pushMessage.getInt("articleId"));
                        startIntent.putExtra("commentId", pushMessage.getInt("commentId"));

                        context.startActivity(startIntent);

                    } else if(pushType.equals("QYJHF")){
                        // 骑游回复;
						startIntent = new Intent(context, Riding_NewCommentActivity.class);

						startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
						startIntent.putExtra("type", "HF");
						startIntent.putExtra("articleId", pushMessage.getInt("articleId"));
						startIntent.putExtra("commentId", pushMessage.getInt("commentId"));

						context.startActivity(startIntent);

					}else if(pushType.equals("QYJDS")){
                        // 骑游打赏;
                        // 陪骑详情，弹窗提示
                        startIntent = new Intent(context, Riding_NewDetailActivity.class);
						startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
						startIntent.putExtra("inType","QYJDS");
                        startIntent.putExtra("articleId", pushMessage.getInt("articleId"));
                        startIntent.putExtra("title",pushMessage.getString("title"));
                        startIntent.putExtra("userName",pushMessage.getString("userName"));
                        startIntent.putExtra("integral",pushMessage.getInt("grade"));
                        startIntent.putExtra("sumIntegral",pushMessage.getInt("totalGrade"));
						context.startActivity(startIntent);

                    }else if(pushType.equals("HDZX")){
                        // 7,活动咨询;
                        // 咨询消息通知页
                        startIntent = new Intent(context, Activity_NewConsultInformActivity.class);
						startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
						startIntent.putExtra("intoType","HDZX");
						startIntent.putExtra("commentId", pushMessage.getInt("commentId"));
						startIntent.putExtra("activityId", pushMessage.getInt("activityId"));

						startIntent.putExtra("title",pushMessage.getString("title"));
						startIntent.putExtra("photo",pushMessage.getString("photo"));
						startIntent.putExtra("reply",pushMessage.getString("reply"));
						startIntent.putExtra("userName",pushMessage.getString("userName"));
						startIntent.putExtra("userImage", pushMessage.getString("userImage"));

                        context.startActivity(startIntent);

                    }else if(pushType.equals("HDZXHF")){
                        // 资讯回复通知
                        startIntent = new Intent(context, Activity_NewConsultInformActivity.class);
						startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
						startIntent.putExtra("intoType","ZXHF");
						startIntent.putExtra("commentId", pushMessage.getInt("commentId"));
						startIntent.putExtra("activityId", pushMessage.getInt("activityId"));

						startIntent.putExtra("title",pushMessage.getString("title"));
						startIntent.putExtra("photo",pushMessage.getString("photo"));
						startIntent.putExtra("reply",pushMessage.getString("reply"));
						startIntent.putExtra("userName",pushMessage.getString("userName"));
						startIntent.putExtra("userImage", pushMessage.getString("userImage"));

                        context.startActivity(startIntent);

                    }else if(pushType.equals("HDYQDS")){
                        // 打赏、评论活动页面
                        startIntent = new Intent(context, Activity_NewCommentActivity.class);
						startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
						startIntent.putExtra("activityId", pushMessage.getInt("activityId"));
						startIntent.putExtra("orderId", pushMessage.getString("orderId"));
                        context.startActivity(startIntent);

                    }else if(pushType.equals("HDBM")){
                        // 报名信息结果页
                        startIntent = new Intent(context, Activity_NewApplyResultActivity.class);
						startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
						startIntent.putExtra("activityId", pushMessage.getInt("activityId"));
                        context.startActivity(startIntent);

                    }else if(pushType.equals("HDPL")){
						// 活动点评结果通知
						startIntent = new Intent(context, Activity_NewCommentInformActivity.class);
						startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
						startIntent.putExtra("title", pushMessage.getString("title"));
						startIntent.putExtra("comment", pushMessage.getString("comment"));
						startIntent.putExtra("userName", pushMessage.getString("userName"));
						startIntent.putExtra("userImage", pushMessage.getString("userImage"));
						startIntent.putExtra("integral", pushMessage.getInt("integral"));
						startIntent.putExtra("activityId", pushMessage.getInt("activityId"));

						startIntent.putExtra("activityImage", pushMessage.getString("photo"));
						context.startActivity(startIntent);

					}else if(pushType.equals("PQSYQDS")){
                        // 打赏、评论陪骑士页面
                        startIntent = new Intent(context, RiderCommentActivity.class);
						startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
						startIntent.putExtra("riderId", pushMessage.getInt("riderId"));
                        startIntent.putExtra("applyId", pushMessage.getInt("applyId"));

                        startIntent.putExtra("time", pushMessage.getString("time"));
                        startIntent.putExtra("date", pushMessage.getString("date"));
                        startIntent.putExtra("name", pushMessage.getString("riderName"));
                        startIntent.putExtra("orderId", pushMessage.getString("orderId"));

                        context.startActivity(startIntent);

                    }else if(pushType.equals("PQSDSJG")){
                    	// 陪骑点评结果通知
                        startIntent = new Intent(context, Rider_NewComemntResultActivity.class);
						startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
						startIntent.putExtra("integral", pushMessage.getInt("integral"));
                        startIntent.putExtra("userName", pushMessage.getString("userName"));
                        startIntent.putExtra("commmet", pushMessage.getString("commmet"));
                        startIntent.putExtra("userImage", pushMessage.getString("userImage"));
                        context.startActivity(startIntent);

                    }

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}


		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));

			// 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
			// 打开一个网页等..
			System.out.println("[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));

		} else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
			boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
			System.out.println("[MyReceiver]" + intent.getAction() + " connected state change to " + connected);

		} else {
			Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			System.out.println("[MyReceiver] Unhandled intent - " + intent.getAction());

		}
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				try {
					JSONObject json = new JSONObject(
							bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it = json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" + myKey + " - "
								+ json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

}
