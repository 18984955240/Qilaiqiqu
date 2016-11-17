package com.qizhi.qilaiqiqu.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.OnMessageNotifyListener;
import com.easemob.chat.OnNotificationClickListener;
import com.qizhi.qilaiqiqu.activity.ChatActivity;
import com.qizhi.qilaiqiqu.utils.ConstantsUtil;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class WeRideApplication extends Application {

	private static final String TAG = null;
    public static Context applicationContext;
	private static WeRideApplication instance;
	// login user name
	public final String PREF_USERNAME = "username";

	/**
	 * 当前用户nickname,为了苹果推送不是userid而是昵称
	 */
	public static String currentUserNick = "";

    // 第三方app于微信通信的API开放接口
    private IWXAPI api;

	@Override
	public void onCreate() {
        MultiDex.install(this);
		super.onCreate();
		applicationContext = this;
        instance = this;

		initEMChat();
        initxUtils();
		initJpush();
//		initLeakCanary();
        regToWx();
	}

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    /**
     * 初始化xUtils
     */
    private void initxUtils() {
//        x.Ext.init(this);
        // 开启debug会影响性能
//        x.Ext.setDebug(BuildConfig.DEBUG);
    }


    /**
     * 初始化极光
     */
	private void initJpush(){
		JPushInterface.init(applicationContext);
//		JPushInterface.setDebugMode(true);
		JPushInterface.setDebugMode(false);
		JPushInterface.setLatestNotificationNumber(this, 3);
	}

    /**
     * 向微信终端注册APP_ID_WX
     */
    private void regToWx() {
        // 通过WXAPIFactory工厂,获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(getApplicationContext(), null);
        // 将应用的APP_ID_WX注册到微信
        api.registerApp(ConstantsUtil.APP_ID_WX);
    }

    /**
     * 初始化LeakCanary 监控内存泄漏
     */
	private void initLeakCanary(){
		if (LeakCanary.isInAnalyzerProcess(this)) {
			// This process is dedicated to LeakCanary for heap analysis.
			// You should not init your app in this process.
			return;
		}
		LeakCanary.install(this);
	}

    /**
     * 初始化环信
     */
	private void initEMChat(){

		int pid = android.os.Process.myPid();
		String processAppName = getAppName(pid);
		// 如果APP启用了远程的service，此application:onCreate会被调用2次
		// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
		// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

		if (processAppName == null ||!processAppName.equalsIgnoreCase(instance.getPackageName())) {
			Log.e(TAG, "enter the service process!");

			// 则此application::onCreate 是被service 调用的，直接返回
			return;
		}

        EMChat.getInstance().init(getBaseContext());
        EMChat.getInstance().setAutoLogin(true);
//		EMChat.getInstance().setDebugMode(true);
		EMChat.getInstance().setDebugMode(false);
		setNotify();

	}


    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }


    public void setNotify() {
        // 获取到配置options对象
        EMChatOptions options = EMChatManager.getInstance().getChatOptions();
        // 设置自定义的文字提示
        options.setNotifyText(new OnMessageNotifyListener() {

            @Override
            public String onNewMessageNotify(EMMessage message) {
                // 可以根据message的类型提示不同文字，这里为一个简单的示例
                return "你有新消息！";
            }

            @Override
            public String onLatestMessageNotify(EMMessage message,
                                                int fromUsersNum, int messageNum) {
                return fromUsersNum + "个骑友，发来了" + messageNum + "条消息";
            }

            @Override
            public String onSetNotificationTitle(EMMessage arg0) {
                return null;
            }

            @Override
            public int onSetSmallIcon(EMMessage arg0) {
                return 0;
            }
        });

        //设置notification点击listener
        options.setOnNotificationClickListener(new OnNotificationClickListener() {

            @Override
            public Intent onNotificationClick(EMMessage message) {
                Intent intent = new Intent(applicationContext, ChatActivity.class);
                ChatType chatType = message.getChatType();
                if(chatType == ChatType.Chat){ //单聊信息
                    intent.putExtra("userId", message.getFrom());
                }else{ //群聊信息
                    //message.getTo()为群聊id
                    intent.putExtra("groupId", message.getTo());
                }
                startActivity(intent);

                return intent;
            }
        });

    }

}
