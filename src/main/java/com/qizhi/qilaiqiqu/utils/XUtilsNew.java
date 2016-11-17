package com.qizhi.qilaiqiqu.utils;

import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dell1 on 2016/7/30.
 */
public class XUtilsNew {

    public static String URL = "http://weride.com.cn/weride/mobile/";


    private LoadingDialog loadingDialog;
    private HttpUtils httpUtils;

    public void httpPost(final String url, RequestParams params, final boolean isAnimate, final Context context, final XUtilsCallBackPost back){

        loadingDialog = new LoadingDialog(context);
        httpUtils = new HttpUtils();
        httpUtils.getHttpClient().getParams().setParameter("http.protocol.content-charset", "UTF-8");
        httpUtils.send(HttpRequest.HttpMethod.POST, URL + url, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                        System.out.println(url+",请求开始");
                        if(isAnimate){
                            // 加载动画
                            loadingDialog.show();
                        }
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        HttpException error = new HttpException();
                        if(isAnimate){
                            // 结束加载动画
                            loadingDialog.dismiss();
                        }

                        try {
                            JSONObject object = new JSONObject(responseInfo.result);

                            if(object.getInt("code") == 0){
                                back.onMySuccess(responseInfo);
                                System.out.println(url+",请求成功");
                                System.out.println(url+":"+responseInfo.result);

                            }else if(object.getInt("code") == 1){
                                Toasts.show(context, "暂无内容", 0);
                                System.out.println(url+",code= 1返回数据为空");

                            }else if(object.getInt("code") == 2){
                                Toasts.show(context, object.getString("message"), 0);
                                System.out.println(url+",code= 2,"+object.getString("message"));

                            }else if(object.getInt("code") == 3){
                                Toasts.show(context, object.getString("message"), 0);
                                System.out.println(url+",code= 3"+object.getString("message"));

                            }else if(object.getInt("code") == -4){
                                System.out.println(url+"code:"+object.getInt("code")+","+object.getString("message"));
                                back.onMyFailure(error,"url+\",UniqueKey不存在\"");

                            }else if(object.getInt("code") == -3){
                                System.out.println(url+"code:"+object.getInt("code")+","+object.getString("message"));
                                back.onMyFailure(error,"url+\",参数异常\"");

                            }else if(object.getInt("code") == -2){
                                System.out.println(url+"code:"+object.getInt("code")+","+object.getString("message"));
                                back.onMyFailure(error,"url+\",系统异常\"");

                            }else if(object.getInt("code") == -1){
                                System.out.println(url+"code:"+object.getInt("code")+","+object.getString("message"));
                                back.onMyFailure(error,"url+\",加解码异常\"");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println(url+",请求失败");
                        System.out.println("error="+error);
                        System.out.println("msg="+msg);

                        if(isAnimate){
                            // 结束加载动画
                            loadingDialog.dismiss();
                        }
                        back.onMyFailure(error,msg);
                    }
                });
    }


    public void httpGet(final String url, final boolean isAnimate, final Context context, final XUtilsCallBackGet back) {
        loadingDialog = new LoadingDialog(context);
        httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, URL + url , new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        if(isAnimate){
                            // 加载动画
                            loadingDialog.show();
                        }
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {

                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        HttpException error = new HttpException();
                        if(isAnimate){
                            // 结束加载动画
                            loadingDialog.dismiss();
                        }

                        try {
                            JSONObject object = new JSONObject(responseInfo.result);

                            if(object.getInt("code") == 0){
                                back.onMySuccess(responseInfo);
                                System.out.println(url+",请求成功");
                                System.out.println(url+":"+responseInfo.result);

                            }else if(object.getInt("code") == 1){
                                Toasts.show(context, "暂无内容", 0);
                                System.out.println(url+",code= 1返回数据为空");

                            }else if(object.getInt("code") == 2){
                                Toasts.show(context, object.getString("message"), 0);
                                System.out.println(url+",code= 2,"+object.getString("message"));

                            }else if(object.getInt("code") == 3){
                                Toasts.show(context, object.getString("message"), 0);
                                System.out.println(url+",code= 3"+object.getString("message"));

                            }else if(object.getInt("code") == -4){
                                System.out.println(url+"code:"+object.getInt("code")+","+object.getString("message"));
                                back.onMyFailure(error,"url+\",UniqueKey不存在\"");

                            }else if(object.getInt("code") == -3){
                                System.out.println(url+"code:"+object.getInt("code")+","+object.getString("message"));
                                back.onMyFailure(error,"url+\",参数异常\"");

                            }else if(object.getInt("code") == -2){
                                System.out.println(url+"code:"+object.getInt("code")+","+object.getString("message"));
                                back.onMyFailure(error,"url+\",系统异常\"");

                            }else if(object.getInt("code") == -1){
                                System.out.println(url+"code:"+object.getInt("code")+","+object.getString("message"));
                                back.onMyFailure(error,"url+\",加解码异常\"");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        back.onMySuccess(responseInfo);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        if(isAnimate){
                            // 结束加载动画
                            loadingDialog.dismiss();
                        }
                        back.onMyFailure(error,msg);
                    }
                });
    }

    public interface XUtilsCallBackPost {

        void onMySuccess(ResponseInfo<String> responseInfo);

        void onMyFailure(HttpException error,String msg);
    }

    public interface XUtilsCallBackGet {

        void onMySuccess(ResponseInfo<String> responseInfo);

        void onMyFailure(HttpException error,String msg);
    }

}
