package com.qizhi.qilaiqiqu.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.BitmapUtils;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.RidingContentModel;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;


/**
 * 本地图片选择
 */
public class NativeImagesActivity extends MyBaseActivity implements OnClickListener {

	// protected ImageLoader imageLoader = ImageLoader.getInstance();

	private GridView photoGrid;

	private LinearLayout backLayout;

	private TextView confirmTxt;
	private TextView optionTxt;

	private ArrayList<String> imageUrls;
	// private DisplayImageOptions options;
	private ImageAdapter imageAdapter;

	private ViewHolder holder;

	private boolean falg = false;
	private boolean articleFalg = false;
	@SuppressWarnings("unused")
	private BitmapUtils bitmapUtils;


    // 游记信息
    public List<RidingContentModel> list;
    private Map<Integer,RidingContentModel> map;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				Object ob = msg.obj;
				if (!ob.toString().equals("0")) {
					optionTxt.setText("已选择" + ob.toString() + "张");
				} else {
					optionTxt.setText("发布骑游记");
				}
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_native_image);

		initData();
		initView();

	}

	private void initView() {
		photoGrid = (GridView) findViewById(R.id.grid_photo);

		backLayout = (LinearLayout) findViewById(R.id.layout_back);

		confirmTxt = (TextView) findViewById(R.id.txt_confirm);
		optionTxt = (TextView) findViewById(R.id.txt_nativeimageactivity_option);

		falg = getIntent().getBooleanExtra("falg", false);
		articleFalg = getIntent().getBooleanExtra("articleFalg", false);

        list = new ArrayList<RidingContentModel>();
        map = new HashMap<Integer,RidingContentModel>();

		bitmapUtils = new BitmapUtils(NativeImagesActivity.this);

		backLayout.setOnClickListener(this);
		confirmTxt.setOnClickListener(this);
		imageAdapter = new ImageAdapter(this, imageUrls);
		photoGrid.setAdapter(imageAdapter);

	}

	/**
	 * 读取本地图片
	 */
	private void initData() {
		this.imageUrls = new ArrayList<String>();

		final String[] columns = { MediaStore.Images.Media.DATA,
				MediaStore.Images.Media._ID };
		final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
		Cursor imagecursor = this
				.getApplicationContext()
				.getContentResolver()
				.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
						null, null, orderBy + " DESC");
		for (int i = 0; i < imagecursor.getCount(); i++) {
			imagecursor.moveToPosition(i);
			int dataColumnIndex = imagecursor
					.getColumnIndex(MediaStore.Images.Media.DATA);
			if (imagecursor.getString(dataColumnIndex).contains(".jpg")
					|| imagecursor.getString(dataColumnIndex).contains(".JPG")
					|| imagecursor.getString(dataColumnIndex).contains(".png")
					|| imagecursor.getString(dataColumnIndex).contains(".PNG")
					&& fileIsExists(imagecursor.getString(dataColumnIndex))) {
				imageUrls.add(imagecursor.getString(dataColumnIndex));
			}
		}
	}
	public boolean fileIsExists(String img){
        try{
                File f=new File(img);
                if(!f.exists()){
                        return false;
                }
                
        }catch (Exception e) {
                return false;
        }
        return true;
}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_back:
			finish();
			break;
		case R.id.txt_confirm:

            Intent intentConfirm = new Intent();
			if(articleFalg){
                Bundle mBundle = new Bundle();
                mBundle.putParcelableArrayList("latLonPointList", latLonPointList);
                mBundle.putSerializable("list", (Serializable) list);

                System.out.println("list.size()="+list.size());
                System.out.println("latLonPointList.size()="+latLonPointList.size());

                intentConfirm.putExtras(mBundle);
                setResult(3, intentConfirm);
			}else{
				ArrayList<String> selectedItems = imageAdapter
						.getCheckedItems();
				intentConfirm.putStringArrayListExtra("photoList",
						selectedItems);

				setResult(1, intentConfirm);
			}
            finish();


			break;
		}
	}


    @Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * Description GridView Adapter
	 */
	boolean isCheck = false;

	int position;
	String FDateTime;
	String FPosition;
	Double FLatitude;
	Double FLongitude;
	String deviceName;
	String deviceModel;
	String imageWidth;
	String imageHeight;

	RidingContentModel ridingContentModel;
	private LatLonPoint mlatLonPoint;
	private GeocodeSearch mgeocoderSearch;
	private List<Integer> keyList = new ArrayList<Integer>();
	private ArrayList<LatLonPoint> latLonPointList = new ArrayList<LatLonPoint>();

    public class ImageAdapter extends BaseAdapter implements GeocodeSearch.OnGeocodeSearchListener {

		Object tag;
		boolean isChecked;

		ArrayList<String> mList;
		LayoutInflater mInflater;
		Context mContext;
		SparseBooleanArray mSparseBooleanArray;

		public ImageAdapter(Context context, ArrayList<String> imageList) {
			mContext = context;
			mInflater = LayoutInflater.from(mContext);
			mSparseBooleanArray = new SparseBooleanArray();
			mList = new ArrayList<String>();
			this.mList = imageList;
		}

		public ArrayList<String> getCheckedItems() {
			ArrayList<String> mTempArry = new ArrayList<String>();

			for (int i = 0; i < mList.size(); i++) {
				if (mSparseBooleanArray.get(i)) {
					mTempArry.add(mList.get(i));
				}
			}

			return mTempArry;
		}

		@Override
		public int getCount() {
			return imageUrls.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(
						R.layout.item_grid_nativeimageactivity_photo, null);
				holder.photoChk = (CheckBox) convertView
						.findViewById(R.id.chk_photo);
				holder.photoImg = (ImageView) convertView
						.findViewById(R.id.img_photo);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

            Glide.with(mContext).load("file://" +
					 imageUrls.get(position)).override(480, 480)
					 	.centerCrop().into(holder.photoImg);


			holder.photoChk.setTag(position);
			holder.photoChk.setChecked(mSparseBooleanArray.get(position));
			holder.photoChk.setOnCheckedChangeListener(mCheckedChangeListener);

			return convertView;
		}

		OnCheckedChangeListener mCheckedChangeListener = new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean is) {
				isChecked = is;
				tag = buttonView.getTag();
                position = Integer.parseInt(String.valueOf(tag));
                if(articleFalg){
					// 添加图片数量
					MobclickAgent.onEvent(NativeImagesActivity.this,"click20");
                    exif();
                }
			}
		};

		private void exif() {

			File file = new File(imageUrls.get(position));
			if(file != null){
				System.out.println("file.getPath()="+file.getPath());
			}

			if(isChecked){
				try {

					ExifInterface exifInterface = new ExifInterface(imageUrls.get(position));

					//拍摄日期
					FDateTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
					// 设备品牌
					deviceName = exifInterface.getAttribute(ExifInterface.TAG_MAKE);
					// 设备型号
					deviceModel = exifInterface.getAttribute(ExifInterface.TAG_MODEL);

					int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
					System.out.println("orientation="+orientation);
					switch (orientation) {
						case ExifInterface.ORIENTATION_ROTATE_90:

						case ExifInterface.ORIENTATION_ROTATE_270:
							// 照片被旋转 宽高相反
							imageWidth = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
							imageHeight = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
							break;

						default:
							imageWidth = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
							imageHeight = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
							break;

					}

					System.out.println("imageWidth="+imageWidth);
					System.out.println("imageHeight="+imageHeight);

					// 拍摄地点经纬度
					String latValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
					String lngValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
					String latRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
					String lngRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);

					ridingContentModel = new RidingContentModel();
					// 设置图片宽度
					ridingContentModel.setWidth(imageWidth);
					// 设置图片高度
					ridingContentModel.setHeight(imageHeight);
					// 设置图片发布状态
					ridingContentModel.setState("publish");
					// 设置图片地址
					ridingContentModel.setPicture(imageUrls.get(position));
					// 设置图片时间
					if(FDateTime == null){
						ridingContentModel.setTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
					}else{
						ridingContentModel.setTime(FDateTime);
					}

					// 转换经纬度格式
					if (latValue != null && latRef != null && lngValue != null && lngRef != null) {

						FLatitude = convertRationalLatLonToFloat(latValue, latRef);
						FLongitude = convertRationalLatLonToFloat(lngValue, lngRef);

						mgeocoderSearch = new GeocodeSearch(NativeImagesActivity.this);
						mgeocoderSearch.setOnGeocodeSearchListener(this);
						mlatLonPoint = new LatLonPoint(FLatitude,FLongitude);
						latLonPointList.add(mlatLonPoint);

						// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
						RegeocodeQuery query = new RegeocodeQuery(mlatLonPoint, 200, GeocodeSearch.AMAP);
						mgeocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求

					}else{

						list.add(ridingContentModel);
						keyList.add(position);

						mSparseBooleanArray.put((Integer) tag, isChecked);
						Message msg = new Message();
						msg.what = 1;
						msg.obj = getCheckedItems().size();
						handler.sendMessage(msg);

					}


				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				// 遍历keyList,得到Position在keyList中的下标，根据此下标删除list中对应下标的model
				for(int i = 0; i < keyList.size(); i++){
					if(keyList.get(i) == position){
						list.remove(i);
						keyList.remove(i);
					}
				}

                mSparseBooleanArray.put((Integer) tag, isChecked);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = getCheckedItems().size();
                handler.sendMessage(msg);

			}

		}

		@Override
		public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
			if (i == 1000) {
				if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
						&& regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {

					FPosition = regeocodeResult.getRegeocodeAddress().getFormatAddress();
					System.out.println("FPosition="+FPosition);
//                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                        AMapUtil.convertToLatLng(new LatLonPoint(ridingContentModel.getLatitude(),ridingContentModel.getLongitude())), 13f));
				} else {
					FPosition = "";
					new SystemUtil().makeToast(NativeImagesActivity.this,
							R.string.no_result + "no_result");
				}

				ridingContentModel.setLatitude(FLatitude);
				ridingContentModel.setLongitude(FLongitude);

				if(FPosition != null){
					ridingContentModel.setPosition(FPosition);
				}

				list.add(ridingContentModel);
				keyList.add(position);

				mSparseBooleanArray.put((Integer) tag, isChecked);
				Message msg = new Message();
				msg.what = 1;
				msg.obj = getCheckedItems().size();
				handler.sendMessage(msg);

			} else {

				list.add(ridingContentModel);
				keyList.add(position);

				mSparseBooleanArray.put((Integer) tag, isChecked);
				Message msg = new Message();
				msg.what = 1;
				msg.obj = getCheckedItems().size();
				handler.sendMessage(msg);

				new SystemUtil().makeToast(NativeImagesActivity.this, getString(R.string.error_other) + " rCode:"+i);
				// 高德错误码对照表:http://lbs.amap.com/api/android-sdk/errorcode/
				// 新版高德SDK rCode = 1000为正确，旧版高德SDK rCode = 0 为正确
			}
		}

		@Override
		public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

		}
	}




    private class ViewHolder {
		private ImageView photoImg;
		private CheckBox photoChk;
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
	}

    /**
     * 经纬度格式转换
     * @param rationalString
     * @param ref
     * @return
     */
    private static Double convertRationalLatLonToFloat(
            String rationalString, String ref) {

        String[] parts = rationalString.split(",");

        String[] pair;
        pair = parts[0].split("/");
        double degrees = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[1].split("/");
        double minutes = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[2].split("/");
        double seconds = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        double result = degrees + (minutes / 60.0) + (seconds / 3600.0);
        if ((ref.equals("S") || ref.equals("W"))) {
            return -result;
        }
        return result;
    }

}
