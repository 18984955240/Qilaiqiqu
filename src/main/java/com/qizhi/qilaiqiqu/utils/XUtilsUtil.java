package com.qizhi.qilaiqiqu.utils;

import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class XUtilsUtil {

	public void post(RequestParams params){
		x.http().post(params, new Callback.CacheCallback<String>() {
			@Override
			public boolean onCache(String result) {
				return false;
			}

			@Override
			public void onSuccess(String result) {

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				if (ex instanceof HttpException) { // 网络错误
					HttpException httpEx = (HttpException) ex;
					int responseCode = httpEx.getCode();
					String responseMsg = httpEx.getMessage();
					String errorResult = httpEx.getResult();
				} else { // 其他错误

				}
				Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
			}

			@Override
			public void onCancelled(CancelledException cex) {

			}

			@Override
			public void onFinished() {

			}
		});
	}

	public interface PostCallBack{
		void onSuccess(String result);
	}

}
