package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.RecommendActivityModel;
import com.qizhi.qilaiqiqu.utils.LoadingDialog;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 *	陪骑认证第三页
 */
public class RiderAuthenticationThirdActivity extends Activity {

    @InjectView(R.id.layout_back)
    LinearLayout layoutBack;
    @InjectView(R.id.edt_name)
    TextView edtName;
    @InjectView(R.id.edt_phone)
    TextView edtPhone;
    @InjectView(R.id.edt_weChat)
    TextView edtWeChat;
    @InjectView(R.id.edt_zhifubao)
    TextView edtZhifubao;
    @InjectView(R.id.btn_commit)
    Button btnCommit;

    private SharedPreferences preferences;

    private RecommendActivityModel activityModel;

    int pictureFlag = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            pictureFlag++;

            System.out.println("pictureFlag="+pictureFlag);
            System.out.println("imageList.size()="+RiderAuthenticationFirstActivity.imageList.size());
            System.out.println("imagePath="+imagePath);

            // 上传的数量是否与本地选择的数量相同
            if(pictureFlag == RiderAuthenticationFirstActivity.imageList.size()){
                insertRider(imagePath);
            }

            super.handleMessage(msg);
        }
    };

    // gif图
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_authentication_thirds);
        ButterKnife.inject(this);

        // 认证达人第三步
        MobclickAgent.onEvent(RiderAuthenticationThirdActivity.this, new UmengEventUtil().click40);

        init();
    }

    private void init() {
        uploadManager = new UploadManager();
        loadingDialog = new LoadingDialog(this);
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
    }

    @OnClick({R.id.btn_commit, R.id.layout_back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_commit:
                System.out.println("check()="+check());
                if(check()){
                    loadingDialog.show();
                    pictureFlag = 0;
                    upLoadImage();
                }
                break;

            case R.id.layout_back:
                finish();
                break;
        }
    }

    private boolean check() {
        if ("".equals(edtName.getText().toString())){
            Toasts.show(RiderAuthenticationThirdActivity.this,"请输入您的真实姓名", 0);
            return false;
        }else if("".equals(edtPhone.getText().toString())){
            Toasts.show(RiderAuthenticationThirdActivity.this,"请输入您的手机号码", 0);
            return false;
        }else if("".equals(edtWeChat.getText().toString())){
            Toasts.show(RiderAuthenticationThirdActivity.this,"请输入您的微信号码", 0);
            return false;
        } else if("".equals(edtZhifubao.getText().toString())){
            Toasts.show(RiderAuthenticationThirdActivity.this,"请输入您的支付宝账号", 0);
            return false;
        }else if(!isMobileNO(edtPhone.getText().toString())){
            Toasts.show(RiderAuthenticationThirdActivity.this,"手机号码格式不正确", 0);
            return false;
        }

        return true;
    }

    // 七牛
    private UploadManager uploadManager;
    public static String uptoken = "";
    private String imagePath = "";
    private void upLoadImage() {
        if("".equals(uptoken)){
            getQiniuToken();
            return;
        }

        for (int i = 0; i < RiderAuthenticationFirstActivity.imageList.size(); i++){
            String s = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            String upkey = "Android_" + s +Integer.toString(i) + Exif(RiderAuthenticationFirstActivity.imageList.get(i)) + ".jpg";


            //设置上传后文件的key
            uploadManager.put(RiderAuthenticationFirstActivity.imageList.get(i), upkey, uptoken, new UpCompletionHandler() {
                public void complete(String key, ResponseInfo rinfo, JSONObject response) {

                    String s = key + ", "+ rinfo + ", " + response;

                    if(rinfo.isOK()){
                        imagePath = imagePath + key +",";

                        System.out.println("upLoadImage() imagePath="+imagePath);
                        System.out.println("upLoadImage() s="+s);

                        Message message = handler.obtainMessage();
                        message.obj = key;
                        message.what = 1;
                        handler.sendMessage(message);
                    }else {
                        getQiniuToken();
                    }
                }
            }, new UploadOptions(null, "test-type", true, null, null));

        }


    }

    /**
     * 浏览照片信息
     */

    String imageWidth;
    String imageHeight;
    private String Exif(String imagePath){

        try {
            ExifInterface exifInterface = new ExifInterface(imagePath);

            imageWidth = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
            imageHeight = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "_"+imageWidth+"_"+imageHeight;
    }


    /**
     * @parms
     * 获取七牛云token
     */
    private void getQiniuToken(){
        RequestParams params = new RequestParams("UTF-8");
        new XUtilsNew().httpPost("common/getAuthToken.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(com.lidroid.xutils.http.ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    uptoken = object.getString("data");

                    upLoadImage();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(RiderAuthenticationThirdActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }


    private void insertRider(String imgPath){

        System.out.println("userId="+preferences.getInt("userId", -1));
        System.out.println("uniqueKey="+preferences.getString("uniqueKey", null));

        System.out.println("label="+RiderAuthenticationFirstActivity.label);
        System.out.println("image="+imgPath.substring(0,imgPath.length()-1));
        System.out.println("personalDesc="+RiderAuthenticationFirstActivity.riderMemos);
        System.out.println("riderArea="+RiderAuthenticationSecondActivity.attendArea);
        System.out.println("riderPrice="+RiderAuthenticationFirstActivity.attendPrice);
        System.out.println("riderTime="+RiderAuthenticationSecondActivity.attendDate.substring(0,RiderAuthenticationSecondActivity.attendDate.length()-1));
        System.out.println("riderDesc="+RiderAuthenticationSecondActivity.attendDesc);

        System.out.println("name="+edtName.getText().toString());
        System.out.println("phone="+edtPhone.getText().toString());
        System.out.println("wxno="+edtWeChat.getText().toString());
        System.out.println("alipayno="+edtZhifubao.getText().toString());

        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("userId", preferences.getInt("userId", -1) + "");
        params.addQueryStringParameter("uniqueKey", preferences.getString("uniqueKey", null));

        params.addQueryStringParameter("label", RiderAuthenticationFirstActivity.label);
        params.addQueryStringParameter("image", imgPath.substring(0,imgPath.length()-1));
        params.addQueryStringParameter("personalDesc", RiderAuthenticationFirstActivity.riderMemos);
        params.addQueryStringParameter("riderArea", RiderAuthenticationSecondActivity.attendArea);
        params.addQueryStringParameter("riderPrice", RiderAuthenticationFirstActivity.attendPrice);
        params.addQueryStringParameter("riderTime", RiderAuthenticationSecondActivity.attendDate.substring(0,RiderAuthenticationSecondActivity.attendDate.length()-1));
        params.addQueryStringParameter("riderDesc", RiderAuthenticationSecondActivity.attendDesc);

        params.addQueryStringParameter("name", edtName.getText().toString());
        params.addQueryStringParameter("phone", edtPhone.getText().toString());
        params.addQueryStringParameter("wxno", edtWeChat.getText().toString());
        params.addQueryStringParameter("alipayno", edtZhifubao.getText().toString());

        new XUtilsNew().httpPost("rider/insertRider.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(com.lidroid.xutils.http.ResponseInfo<String> responseInfo) {
                try {
                    loadingDialog.dismiss();
                    JSONObject object = new JSONObject(responseInfo.result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<RecommendActivityModel>() {}.getType();

                    activityModel = gson.fromJson(object.toString(),type);

                    RiderAuthenticationFirstActivity.instanceFirst.finish();
                    RiderAuthenticationSecondActivity.instanceSecond.finish();
                    RiderAuthenticationThirdActivity.this.finish();

                    // 友盟计数统计事件
                    MobclickAgent.onEvent(RiderAuthenticationThirdActivity.this,"click40");

                    startActivity(new Intent(
                            RiderAuthenticationThirdActivity.this,
                            RiderAuthenticationSuccessActivity.class).putExtra("activityModel",activityModel));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                loadingDialog.hide();
                Toasts.show(RiderAuthenticationThirdActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    /**
     * 验证手机号码
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("(^1(3[0-9]|5[0-35-9]|8[025-9])\\d{8}$)"
                + "|(^1(34[0-8]|(3[5-9]|5[017-9]|8[278])\\d)\\d{7}$)"
                + "|(^1(3[0-2]|5[256]|8[56])\\d{8}$)"
                + "|(^1((33|53|8[09])[0-9]|349)\\d{7}$)"
                + "|(^18[09]\\d{8}$)|(^(180|189|133|134|153)\\d{8}$)"
                + "|(^1(33|53|77|8[019])\\d{8}$)|(^1700\\d{7}$)");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    @Override
    protected void onResume() {
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


}
