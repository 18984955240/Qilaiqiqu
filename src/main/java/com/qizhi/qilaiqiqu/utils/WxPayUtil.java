package com.qizhi.qilaiqiqu.utils;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.sina.weibo.sdk.net.SSLSocketFactoryEx;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by dell1 on 2016/6/16.
 */
public class WxPayUtil {
    /**
     * 获取随机字符串
     * @return
     */
    public static String genNonceStr() {
        Random random = new Random();
        return com.qizhi.qilaiqiqu.utils.MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /**
     * 时间戳
     * @return
     */
    public static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 转化为XML格式
     * @param params
     * @return
     */
    public static String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<"+params.get(i).getName()+">");


            sb.append(params.get(i).getValue());
            sb.append("</"+params.get(i).getName()+">");
        }
        sb.append("</xml>");

        Log.e("", sb.toString());
        return sb.toString();
    }

    /**
     * 解析xml并转化为Map<String,String>值
     * @param content
     * @return
     */
    public static Map<String,String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName=parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if("xml".equals(nodeName)==false){
                            //实例化student对象
                            xml.put(nodeName,parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }
            return xml;
        } catch (Exception e) {
            Log.e("orion",e.toString());
        }
        return null;

    }

    /**
     * 预支付交易会话Id获取  prepay_id
     * @param describe
     * @param money 商品金额，以分为单位
     * @return
     */
    public static String genProductArgs(String describe,String money) {
        StringBuffer xml = new StringBuffer();

        try {
            String	nonceStr = genNonceStr();
            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", ConstantsUtil.APP_ID_TX));//开放平台appid
            packageParams.add(new BasicNameValuePair("body", describe));      //商品描述
            packageParams.add(new BasicNameValuePair("mch_id", ConstantsUtil.MCH_ID));//商户号
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));//随机字符串
            packageParams.add(new BasicNameValuePair("notify_url",ConstantsUtil.NOTIFY_URL));//支付结果异步通知
            packageParams.add(new BasicNameValuePair("out_trade_no",genNonceStr()));//商家订单号
            packageParams.add(new BasicNameValuePair("total_fee", money));//商品金额，以分为单位
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));//交易类型
            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));
            String xmlstring = toXml(packageParams);
            return xmlstring;
        } catch (Exception e) {
            Log.e("TestActivity", "genProductArgs fail, ex = " + e.getMessage());
            return null;
        }
    }



    /**
     * 二次签名
     * @param iwxapi
     * @param req
     * @param resultunifiedorder
     */
    public static void genPayReq(IWXAPI iwxapi,PayReq req,
                                 Map<String,String> resultunifiedorder) {
        req.appId = ConstantsUtil.APP_ID_WX;
        req.partnerId = ConstantsUtil.MCH_ID;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue="Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());
        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
        req.sign = genAppSign(signParams);
        Log.e("orion3", signParams.toString());
        Log.e("orion3.1",req.sign.toString()+"");
        sendPayReq(iwxapi,req);
    }

    /**
     *  检测是否有微信与是否支持微信支付
     * @return
     */
    public static boolean check(Context context, IWXAPI api) {
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if(!api.isWXAppInstalled())
        {
            Toast.makeText(context,"没有安装微信",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!api.isWXAppSupportAPI())
        {
            Toast.makeText(context,"你使用的微信版本不支持微信支付！",Toast.LENGTH_SHORT).show();
            return false;
        }
        return isPaySupported;
    }

    /**
     * 生成签名
     * @param params
     * @return
     */
    public static String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(ConstantsUtil.API_KEY);
        String packageSign = com.qizhi.qilaiqiqu.utils.MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion4",packageSign);
        return packageSign;
    }

    /**
     *  得到app签名
     * @param params
     * @return
     */
    public static String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
//        ,StringBuilder stringBuilder
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(ConstantsUtil.API_KEY);
//        stringBuilder.append("sign str\n"+sb.toString()+"\n\n");
        String appSign = com.qizhi.qilaiqiqu.utils.MD5.getMessageDigest(sb.toString().getBytes());
        Log.e("orion5",appSign);
        return appSign;
    }

    /**
     * 微信注册app
     * @param iwxapi
     * @param req
     */
    public static void sendPayReq(IWXAPI iwxapi, PayReq req) {
        if (iwxapi != null) {
            iwxapi.registerApp(ConstantsUtil.APP_ID_WX);
            iwxapi.sendReq(req);
        }
    }

    public static byte[] httpPost(String url, String entity) {
        if (url == null || url.length() == 0) {
            return null;
        }

        HttpClient httpClient = getNewHttpClient();

        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new StringEntity(entity));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse resp = httpClient.execute(httpPost);
            if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return null;
            }

            return EntityUtils.toByteArray(resp.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }



}
