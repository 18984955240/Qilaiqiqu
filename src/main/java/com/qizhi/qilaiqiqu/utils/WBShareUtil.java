package com.qizhi.qilaiqiqu.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;

/**
 * Created by dell1 on 2016/7/6.
 */
public class WBShareUtil {
    private static final String TAG = WBShareUtil.class.getSimpleName();
    private Activity mActivity;
    public WBShareUtil(Activity activity) {
        this.mActivity = activity;
    }
    public void shareToWB(IWeiboShareAPI mShareAPI, Bitmap bitmap, String text ,String url,String description) {
        Log.i(TAG, "shareToWeibo");
        //2.准备分享的文本和图片
        final TextObject mTO = new TextObject();
        mTO.text = text;
        ImageObject mIO = new ImageObject();

        //Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_calendar_left_arrow_56);
        mIO.setImageObject(bitmap); //图片 参数问题 导致无法调用客户端

        WebpageObject mWO = new WebpageObject();
        mWO.identify = Utility.generateGUID();
        mWO.title = text;
        mWO.description = description;

        mWO.setThumbImage(bitmap);
        mWO.actionUrl = url;
        mWO.defaultText = "骑来骑去";

        //3.初始化微博的分享信息
        WeiboMultiMessage mWeiboMultiMsg = new WeiboMultiMessage();
        mWeiboMultiMsg.mediaObject = mWO;
        mWeiboMultiMsg.imageObject = mIO;
        //3.建立分享请求
        SendMultiMessageToWeiboRequest mRequest = new SendMultiMessageToWeiboRequest();
        mRequest.transaction = String.valueOf(System.currentTimeMillis());
        mRequest.multiMessage = mWeiboMultiMsg;

        //4.分享
        AuthInfo mAuthInfo = new AuthInfo(mActivity, WBConstants.APP_KEY,
                WBConstants.REDIRECT_URL, WBConstants.SCOPE);
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(mActivity);
        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        }

        mShareAPI.sendRequest(mActivity, mRequest, mAuthInfo, token, new WeiboAuthListener() {
            @Override
            public void onComplete(Bundle bundle) {
                Oauth2AccessToken mToken = Oauth2AccessToken.parseAccessToken(bundle);
                if ( mToken.isSessionValid() ) {
                    Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                    AccessTokenKeeper.writeAccessToken(mActivity, newToken);
                    Toast.makeText(mActivity, "onAuthorizeComplete token = " + newToken.getToken(), 0).show();
                } else {
                    // 以下几种情况，您会收到 Code：
                    // 1. 当您未在平台上注册的应用程序的包名与签名时；
                    // 2. 当您注册的应用程序包名与签名不正确时；
                    // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                    String code = bundle.getString("code");
                    String message = ".WEIBO_AUTH_FAILED";
                    if (!TextUtils.isEmpty(code)) {
                        Log.i(TAG, "msg = " + message + code);
                    }
                    Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onWeiboException(WeiboException e) {
                e.printStackTrace();
            }

            @Override
            public void onCancel() {
                Toast.makeText(mActivity, ".WEIBO_SHARE_CANCELED",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
