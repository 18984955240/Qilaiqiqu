package com.qizhi.qilaiqiqu.utils;

public class ConstantsUtil {
	// APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID_WX = "wx3eb3d4d796093674";

	// 腾讯APP_ID
    public static final String APP_ID_TX = "1104904678";

	// 微信商户号
	public static final String MCH_ID = "1300851701";

	// 微信API密钥，在商户平台设置
	public static final  String API_KEY="7fx5r0n7j8k1tvnzpn55c3ef0zo9a7be";

	// 微信支付结果异步通知链接
	public static final  String NOTIFY_URL="http://dev.weride.com.cn/mobile/payment/wxPayNotify.html";

	//订单生成的机器IP，指用户浏览器端IP，也可以写成固定值
	public static final  String SPBILL_CREATE_IP="196.168.1.1";

    public static class ShowMsgActivity {
		public static final String STitle = "showmsg_title";
		public static final String SMessage = "showmsg_message";
		public static final String BAThumbData = "showmsg_thumb_data";
	}
}
