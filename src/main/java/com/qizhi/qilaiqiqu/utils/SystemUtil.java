package com.qizhi.qilaiqiqu.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.BitmapUtils;
import com.qizhi.qilaiqiqu.R;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author leiqian
 * 
 */
public class SystemUtil {

	public final static String IMGPHTH_NEW = "http://image.weride.com.cn/";
	public final static String IMGPHTH = "http://img.weride.com.cn/";

	public static String STRIP = "strip/quality/50";
	public static String THUMBNAIL = "thumbnail/";
	public static String IMAGEMOGR = "?imageMogr2/auto-orient/";

	public final static String AES_KEY = "key";


	/**
	 * @param context
	 *            上下文
	 * @param msg
	 *            消息内容
	 */
	public void makeToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * @return date 系统时间
	 */

	@SuppressLint("SimpleDateFormat")
	public static String getTime() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		return date;
	}

	/**
	 * 
	 * @param url
	 *            图片路径
	 * @param sellersmallimg
	 *            imageView控件
	 * @param context
	 *            上下文
	 */
	public static void loadImagexutils(String url, ImageView sellersmallimg,
			Context context) {
		BitmapUtils bitmapUtils = new BitmapUtils(context);
		bitmapUtils.display(sellersmallimg, IMGPHTH + url);
	}

	/**
	 * 
	 * @param url
	 *            图片路径
	 * @param sellersmallimg
	 *            imageView控件
	 * @param context
	 *            上下文
	 */
	public static void Imagexutils(String url, ImageView sellersmallimg,
			Context context) {

		if (url == null) {
			return;
		}
		if (url.indexOf("http") != -1) {
			Picasso.with(context).load(url)
					.placeholder(R.drawable.bitmap_homepage)
					.error(R.drawable.bitmap_homepage).into(sellersmallimg);
		} else {
			String uri = UrlSize(url);
			Picasso.with(context).load(IMGPHTH + uri)
					.placeholder(R.drawable.bitmap_homepage)
					.error(R.drawable.bitmap_homepage).into(sellersmallimg);
		}

	}

	/**
	 *
	 * @param url
	 *            图片路径
	 * @param sellersmallimg
	 *            imageView控件
	 * @param context
	 *            上下文
	 */
	public static void Imagexutils_new(String url, ImageView sellersmallimg, Context context) {
		if (url == null) {
			return;
		}
		if (url.indexOf("http") != -1) {
            Glide.with(context).load(url)
					.placeholder(R.drawable.bitmap_homepage)
					.error(R.drawable.bitmap_homepage).into(sellersmallimg);
		} else {
//			String uri = UrlSize(url);
            Glide.with(context)
					.load(IMGPHTH_NEW + url + IMAGEMOGR + STRIP)
					.placeholder(R.drawable.bitmap_homepage)
					.error(R.drawable.bitmap_homepage)
					.into(sellersmallimg);

            System.out.println("SystemUtil ______________Imagexutils_new + uri="+IMGPHTH_NEW + url +IMAGEMOGR+STRIP);

		}

	}


	/**
	 *
	 * @param url
	 *            图片路径
	 * @param sellersmallimg
	 *            imageView控件
	 * @param context
	 *            上下文
	 */
	public static void ImageLoad_article(String url, ImageView sellersmallimg, Context context,int px) {

		if (url == null) {
			return;
		}
		if (url.indexOf("storage") != -1) {
			Picasso.with(context).load(url)
					.placeholder(R.drawable.bitmap_homepage)
					.error(R.drawable.bitmap_homepage).into(sellersmallimg);
		} else {
//			String uri = UrlSize(url);
			Picasso.with(context)
					.load(IMGPHTH_NEW + url + IMAGEMOGR + THUMBNAIL + px + "x/" + STRIP)
					.placeholder(R.drawable.bitmap_homepage)
					.error(R.drawable.bitmap_homepage).into(sellersmallimg);

			System.out.println("SystemUtil ______________ImageLoad_article + uri="+IMGPHTH_NEW + url + IMAGEMOGR + THUMBNAIL + px + "x/" + STRIP);
		}

	}

	/**
	 *
	 * @param url
	 *            图片路径
	 * @param sellersmallimg
	 *            imageView控件
	 * @param context
	 *            上下文
	 */
	public static void Imagexutils_photo(String url, ImageView sellersmallimg, Context context) {


		if (url == null) {
			return;
		}
		if (url.indexOf("http") != -1) {
			Picasso.with(context).load(url)
					.placeholder(R.drawable.bitmap_homepage)
					.error(R.drawable.bitmap_homepage).into(sellersmallimg);
		} else {
//			String uri = UrlSize(url);
			Picasso.with(context)
					.load(IMGPHTH_NEW + url + IMAGEMOGR)
					.placeholder(R.drawable.bitmap_homepage)
					.error(R.drawable.bitmap_homepage)
					.into(sellersmallimg);

			System.out.println("SystemUtil ______________Imagexutils_photo + uri="+IMGPHTH_NEW + url +IMAGEMOGR);

		}

	}


	/**
	 * @param srcPath 图片路径
	 * @return 压缩后的bitmap
	 */
	public static Bitmap compressImageFromFile(String srcPath, float ww) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();

		newOpts.inJustDecodeBounds = true;// 只读边,不读内容
//		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		// 现在主流手机比较多是800*480分辨率
		/*
		 * float hh = 800f;// float ww = 480f;//
		 */int be = 1;
		if (w > ww) {
			be = (int) (newOpts.outWidth / ww);
		}
		if (be <= 0)
			be = 1;

		newOpts.inSampleSize = be;// 设置采样率
		newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;// 该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;// 当系统内存不够时候图片自动被回收


		Bitmap bitmap = BitmapFactory.decodeFile(srcPath.trim(), newOpts);


		System.out.println("srcPath="+srcPath);
		System.out.println("newOpts="+newOpts);
		System.out.println("bitmap="+bitmap);

		return bitmap;
	}

	public static String UrlSize(String url) {
		// USER_201602261033021750_160_160_11.jpg
		String[] ss = url.split("_");
		if (ss.length == 5) {
			int w = Integer.parseInt(ss[2]);
			int h = Integer.parseInt(ss[3]);
			// int size = Integer.parseInt(ss[4].split(".")[0]);
			if (w > 1024 || h > 1024) {
				if (w > h) {
					h = (int) (h * 1.0 * 1024 / w);
					w = 1024;
				} else {
					w = (int) (w * 1.0 * 1024 / h);
					h = 1024;
				}
				return url + "@" + w + "w_" + h + "h";
			}
		}
		return url;

	}

	@SuppressLint("NewApi")
	public String saveMyBitmap(Bitmap mBitmap) throws IOException {
		File outDir = null;
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			// 这个路径，在手机内存下创建一个pictures的文件夹，把图片存在其中。
			outDir = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		} else {
			outDir = new File(state);
			// return null;
		}
		if (!outDir.exists()) {
			outDir.mkdirs();
		}
		outDir = new File(outDir, System.currentTimeMillis() + ".jpg");
		String s = outDir.toString();
		/*
		 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 * mBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
		 * baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		 * if(baos.toByteArray().length / 1024 > 400){ FileOutputStream fos =
		 * new FileOutputStream(outDir);
		 * mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos); }else{
		 * FileOutputStream fos = new FileOutputStream(outDir);
		 * mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos); }
		 */

		FileOutputStream fos = new FileOutputStream(outDir);
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
		fos.flush();
		fos.close();

		if (mBitmap != null && !mBitmap.isRecycled()) {
			// 回收并且置为null
			mBitmap.recycle();
			mBitmap = null;
		}
		System.gc();

		return s;
	}

	/**
	 * 
	 * @param image
	 *            图片
	 * @param imageSize
	 *            压缩大小
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image, Integer imageSize) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 90, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;

		while (baos.toByteArray().length / 1024 > imageSize) {
			baos.reset();// 重置baos即清空baos
			options = options - 10;// 每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}

		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	public void httpClient(final String img_path,
			final SharedPreferences preferences, final Handler handler,
			final String type) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				client.getParams().setParameter(
						CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
				HttpPost post = new HttpPost(
						 "common/uploadImage.html");// 创建 HTTP POST 请求
				MultipartEntityBuilder builder = MultipartEntityBuilder
						.create();
				// builder.setCharset(Charset.forName("uft-8"));//设置请求的编码格式
				builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);// 设置浏览器兼容模式
				System.out.println("=============================");
				System.out.println(img_path);
				System.out.println("=============================");
				File file = new File(img_path);
				FileBody fileBody = new FileBody(file);// 把文件转换成流对象FileBody
				builder.addPart("files", fileBody);
				builder.addTextBody("type", type);// 设置请求参数
				builder.addTextBody("uniqueKey",
						preferences.getString("uniqueKey", null));// 设置请求参数
				HttpEntity entity = builder.build();// 生成 HTTP POST 实体
				post.setEntity(entity);// 设置请求参数
				HttpResponse response;
				try {
					response = client.execute(post);
					// 发起请求 并返回请求的响应
					if (response.getStatusLine().getStatusCode() == 200) {
						String strResult = EntityUtils.toString(response
								.getEntity());
						JSONObject jo = new JSONObject(strResult);
						System.out.println(jo.toString());
						if (jo.optBoolean("result")) {
							JSONArray jsonArray = jo.getJSONArray("dataList");
							String s = jsonArray.getString(0);
							Message msg = new Message();
							msg.what = 1;
							msg.obj = s;
							file.delete();
							handler.handleMessage(msg);
						} else {
							Message msg = new Message();
							msg.what = 2;
							msg.obj = jo.opt("message");
							file.delete();
							handler.handleMessage(msg);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public static byte[] bmpToByteArray(final Bitmap bmp,
			final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}


	/**
	 * 检查系统版本
	 *
	 */
	public static boolean canMakeSmores(){

		return(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1);

	}

	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
			android.Manifest.permission.READ_EXTERNAL_STORAGE,
			android.Manifest.permission.WRITE_EXTERNAL_STORAGE };

	public static void verifyStoragePermissions(Activity activity) {
		// Check if we have write permission
		int permission = ActivityCompat.checkSelfPermission(activity,
				android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

		if (permission != PackageManager.PERMISSION_GRANTED) {
			// We don't have permission so prompt the user
			ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
					REQUEST_EXTERNAL_STORAGE);
		}
	}


	public static Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

		if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出

			baos.reset();//重置baos即清空baos

			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中

		}

		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());

		BitmapFactory.Options newOpts = new BitmapFactory.Options();

		//开始读入图片，此时把options.inJustDecodeBounds 设回true了

		newOpts.inJustDecodeBounds = true;

		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);

		newOpts.inJustDecodeBounds = false;

		int w = newOpts.outWidth;

		int h = newOpts.outHeight;

		//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为

		float hh = 800f;//这里设置高度为800f

		float ww = 480f;//这里设置宽度为480f

		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可

		int be = 1;//be=1表示不缩放

		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放

			be = (int) (newOpts.outWidth / ww);

		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放

			be = (int) (newOpts.outHeight / hh);

		}

		if (be <= 0)

			be = 1;

		newOpts.inSampleSize = be;//设置缩放比例

		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了

		isBm = new ByteArrayInputStream(baos.toByteArray());

		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);

		return compressImage(bitmap);//压缩好比例大小后再进行质量压缩

	}

	public static Bitmap compressImage(Bitmap image){

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		//质量压缩方法，100表示不压缩，把压缩后的数据存放到baos中

		image.compress(Bitmap.CompressFormat.JPEG,100,baos);

		int options = 100;

		//循环判断，压缩后图片是否大于100kb，大于继续压缩

		while(baos.toByteArray().length / 1024>100){

			//重置baos即清空baos

			baos.reset();

			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

			options -= 10;//每次都减少10

		}

		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中

		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片

		return bitmap;

	}

	/**
	 * dp转px
	 *
	 * @param context
	 * @return
	 */
	public static int dp2px(Context context, float dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, context.getResources().getDisplayMetrics());
	}


	/**
	 * 检查内存卡是否存在
	 * @return
     */
	public static boolean checkSDcard(){
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

}



