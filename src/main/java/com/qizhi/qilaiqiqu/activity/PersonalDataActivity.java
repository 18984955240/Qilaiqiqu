package com.qizhi.qilaiqiqu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.adapter.ArrayWheelAdapter;
import com.qizhi.qilaiqiqu.model.UserLoginModel;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.qizhi.qilaiqiqu.utils.XUtilsUtil;
import com.qizhi.qilaiqiqu.widget.OnWheelChangedListener;
import com.qizhi.qilaiqiqu.widget.WheelView;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.jpush.android.api.JPushInterface;

import static com.qizhi.qilaiqiqu.utils.SystemUtil.checkSDcard;

/**
 *  约骑申请
 */

public class PersonalDataActivity extends BaseActivity implements
		OnClickListener, OnWheelChangedListener, TextWatcher {

	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;

	private Button mBtnConfirm;

	private TextView confirmTxt;
	private TextView provinceTxt;
	private TextView cityTxt;
	private TextView DistrictTxt;
	private TextView signatureTxt;
	private TextView usernameTxt;
	private TextView fansTxt;
	private TextView careTxt;

	private EditText nickEdt;
	private EditText signatureEdt;

	private ImageView maleImg;
	private ImageView famaleImg;
	private ImageView photoImg;

	private RelativeLayout addressLayout;
	private RelativeLayout bindPhoneLayout;
	private LinearLayout backLayout;
	private LinearLayout photoLayout;
	private LinearLayout fansLayout;
	private LinearLayout careLayout;
	private RelativeLayout riderLayout;
	private TextView attendRiderTxt;
	private ImageView RiderImage;

	private AssetManager asset;

	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int IMAGE_REQUEST_CODE = 2;
	private static final int RESULT_REQUEST_CODE = 3;
	private static final int BING_PHONE = 4;

	private XUtilsUtil xUtilsUtil;

	private String sexString = null;

	private String img_path = null;

	private SharedPreferences preferences;

	private UserLoginModel userModel;

	private boolean path = true;

	private boolean falg = true;
	private ProgressDialog pDialog;

	// 七牛
	private UploadManager uploadManager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_data);
		asset = this.getAssets();
		initView();
		initEvnet();
		hide();
		data();
		checkUser();
	}

	private void checkUser() {
		RequestParams params = new RequestParams("UTF-8");
		params.addBodyParameter("userId",userModel.getUserId()+"");
		new XUtilsNew().httpPost("rider/queryRiderState.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				try {
					JSONObject object = new JSONObject(responseInfo.result);
					if(object.getString("data").equals("2")){
						attendRiderTxt.setText("已认证陪骑士");
						RiderImage.setImageResource(R.drawable.set_sucessfully);

					}else {
						attendRiderTxt.setText("未认证陪骑士");
						RiderImage.setImageResource(R.drawable.activity_finish);

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onMyFailure(HttpException error, String msg) {

			}
		});

	}

	private void hide() {
		// 将输入法隐藏
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(signatureEdt.getWindowToken(), 0);
	}

	private void initView() {
		xUtilsUtil = new XUtilsUtil();
		uploadManager = new UploadManager();
		preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
//		certainUserModel = new CertainUserModel();

		confirmTxt = (TextView) findViewById(R.id.txt_personalDataActivity_confirm);
		provinceTxt = (TextView) findViewById(R.id.txt_personalDataActivity_province);
		cityTxt = (TextView) findViewById(R.id.txt_personalDataActivity_city);
		DistrictTxt = (TextView) findViewById(R.id.txt_personalDataActivity_district);
		signatureTxt = (TextView) findViewById(R.id.txt_personalDataActivity_signature);
		careTxt = (TextView) findViewById(R.id.txt_personalDataActivity_care);
		fansTxt = (TextView) findViewById(R.id.txt_personalDataActivity_fans);

		nickEdt = (EditText) findViewById(R.id.edt_personalDataActivity_nick);
		usernameTxt = (TextView) findViewById(R.id.txt_personalDataActivity_usernametxt);
		signatureEdt = (EditText) findViewById(R.id.edt_personalDataActivity_signature);

		maleImg = (ImageView) findViewById(R.id.img_personalDataActivity_male);
		famaleImg = (ImageView) findViewById(R.id.img_personalDataActivity_famale);
		photoImg = (ImageView) findViewById(R.id.img_personalDataActivity_photo);

		fansLayout = (LinearLayout) findViewById(R.id.layout_personalDataActivity_fans);
		careLayout = (LinearLayout) findViewById(R.id.layout_personalDataActivity_care);
		backLayout = (LinearLayout) findViewById(R.id.layout_personalDataActivity_back);
		photoLayout = (LinearLayout) findViewById(R.id.layout_personalDataActivity_photo);
		addressLayout = (RelativeLayout) findViewById(R.id.layout_personalDataActivity_address);
		bindPhoneLayout = (RelativeLayout) findViewById(R.id.layout_personalDataActivity_bindphone);
		riderLayout = (RelativeLayout) findViewById(R.id.layout_rideractivity_activity_becomerider);
		attendRiderTxt = (TextView) findViewById(R.id.txt_personActivity_attendRider);
		RiderImage = (ImageView) findViewById(R.id.img_personActivity_rider);
	}

	private void initEvnet() {
		backLayout.setOnClickListener(this);
		maleImg.setOnClickListener(this);
		famaleImg.setOnClickListener(this);
		confirmTxt.setOnClickListener(this);
		addressLayout.setOnClickListener(this);
		signatureEdt.addTextChangedListener(this);
		photoLayout.setOnClickListener(this);
		careLayout.setOnClickListener(this);
		fansLayout.setOnClickListener(this);
		bindPhoneLayout.setOnClickListener(this);
		riderLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_personalDataActivity_male:
			sexString = "M";
			maleImg.setImageResource(R.drawable.male_chosen);
			famaleImg.setImageResource(R.drawable.famale_unchosen);
			break;

		case R.id.img_personalDataActivity_famale:
			sexString = "F";
			famaleImg.setImageResource(R.drawable.famale_chosen);
			maleImg.setImageResource(R.drawable.male_unchosen);
			break;

		case R.id.txt_personalDataActivity_confirm:
			setViewData();
			break;

		case R.id.layout_personalDataActivity_address:
			showPopupWindow(v);
			break;

		case R.id.layout_personalDataActivity_back:
			finish();
			break;

		case R.id.layout_personalDataActivity_photo:
//			initGalleryFinal();
            choosePhoto();
			break;

		case R.id.layout_personalDataActivity_fans:
			startActivity(new Intent(PersonalDataActivity.this,
					FriendActivity.class).putExtra("friendFlag", 0));
			break;

		case R.id.layout_personalDataActivity_care:
			startActivity(new Intent(PersonalDataActivity.this,
					FriendActivity.class).putExtra("friendFlag", 1));
			break;

		case R.id.layout_personalDataActivity_bindphone:
			if (preferences.getInt("userId", -1) != -1
					&& "".equals(usernameTxt.getText().toString().trim())) {
				Intent intent = new Intent(PersonalDataActivity.this,
						BindPhoneActivity.class);
				intent.putExtra("userId", preferences.getInt("userId", -1));
				intent.putExtra("uniqueKey",
						preferences.getString("uniqueKey", null));
				startActivityForResult(intent, BING_PHONE);
			}
			break;
		case R.id.layout_rideractivity_activity_becomerider:
			if (!userModel.getUserType().equals("NORMAL")) {
				startActivity(new Intent(this, Rider_NewDetailActivity.class).putExtra(
						"riderId", userModel.getRiderId()));
			}else{
				startActivity(new Intent(this,
						RiderAuthenticationActivity.class));
			}
			break;

		default:
			break;
		}
	}



    private void choosePhoto() {
		// 调用android的图库
		Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		i.setType("image/*");
		startActivityForResult(i, Crop.REQUEST_PICK);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());

        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    /**
     * 创建文件夹
     */
    private static String createFile(){
        String filePath = null;
        if(checkSDcard()){
            filePath = "/sdcard/Qilaiqiqu/Crop";
        } else {
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Qilaiqiqu/Crop";
        }

        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            Log.i("error:", e+"");
        }

        return filePath;
    }

    private void beginCrop(Uri source) {
        System.out.println("source="+source);
        String s = new SimpleDateFormat("yyyyMMddhhmmss")
                .format(new Date());

        Uri destination = Uri.fromFile(new File(createFile()+ "/" + s +"_crop.jpg"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) { // 裁剪成功
            File file = null;
            try {
                file = new File(new URI(Crop.getOutput(result).toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            img_path = file.getPath();
            Glide.with(PersonalDataActivity.this).load(img_path).into(photoImg);
            Exif(img_path);

        } else if (resultCode == Crop.RESULT_ERROR) { // 裁剪失败
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


	/**
	 * 浏览照片信息并获取图片宽高
	 */
	String imageWidth;
	String imageHeight;
	private String Exif(String imagePath){
		try {
			ExifInterface exifInterface = new ExifInterface(imagePath);

			imageWidth = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
			imageHeight = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);

//			// 上传头像
//			upLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imagePath;
	}


	private void setViewData() {
		pDialog = ProgressDialog.show(this, "请稍等", "正在修改");
		if (!falg) {
			pDialog.dismiss();
			return;
		}
		falg = false;
		if (img_path != null) {
			upLoadImage();
		} else {
			informationUpdate();
		}
	}

	/**
	 *
	 */
	public static String uptoken = "";
	private void upLoadImage() {

		System.out.println("=============================");
		System.out.println(img_path);
		System.out.println("=============================");

		if("".equals(uptoken)){
			getQiniuToken();
			return;
		}

		String s = new SimpleDateFormat("yyyyMMddhhmmss")
				.format(new Date());
		String upkey = "Android_" + s + "_"+imageWidth+"_"+imageHeight+".jpg";
		uploadManager.put(img_path, upkey, uptoken, new UpCompletionHandler() {
			public void complete(String key, com.qiniu.android.http.ResponseInfo rinfo, JSONObject response) {

				if(rinfo.isOK()){
					System.out.println("PersonalDataActivity upLoadCover——————key:"+key);
					userModel.setUserImage(key);
					// 上传资料
					informationUpdate();
				}else {
					getQiniuToken();
				}
			}
		}, new UploadOptions(null, "test-type", true, null, null));
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
				Toasts.show(PersonalDataActivity.this, "无法连接服务器，请检查网络", 0);
			}
		});
	}


	private void showPopupWindow(View view) {

		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.item_popup_address, null);

		mViewProvince = (WheelView) contentView.findViewById(R.id.id_province);
		mViewCity = (WheelView) contentView.findViewById(R.id.id_city);
		mViewDistrict = (WheelView) contentView.findViewById(R.id.id_district);
		mBtnConfirm = (Button) contentView
				.findViewById(R.id.btn_addressPopup_confirm);

		mViewProvince.addChangingListener(this);
		mViewCity.addChangingListener(this);
		mViewDistrict.addChangingListener(this);

		setUpData();

		final PopupWindow popupWindow = new PopupWindow(contentView,
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);

		popupWindow.setTouchable(true);

		popupWindow.setAnimationStyle(R.style.PopupAnimation);

		popupWindow.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return false;
				// 这里如果返回true的话，touch事件将被拦截
				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
			}
		});

		mBtnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showSelectedResult();
				popupWindow.dismiss();
			}
		});

		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		// 我觉得这里是API的一个bug
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.f8f8));
		// 设置好参数之后再show
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

	}

	private void setUpData() {
		initProvinceDatas(asset);
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(
				PersonalDataActivity.this, mProvinceDatas));
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == mViewProvince) {
			updateCities();
		} else if (wheel == mViewCity) {
			updateAreas();
		} else if (wheel == mViewDistrict) {
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}
	}

	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict
				.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mViewDistrict.setCurrentItem(0);
	}

	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	private void showSelectedResult() {
		provinceTxt.setText(mCurrentProviceName);
		cityTxt.setText(mCurrentCityName);
		DistrictTxt.setText(mCurrentDistrictName);

	}

	@Override
	public void afterTextChanged(Editable e) {
		signatureTxt.setText(Html.fromHtml(e.length()
				+ "<font color='#9d9d9e'>/50</font>"));

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

	}

	private void informationUpdate() {
		if (!"".equals(userModel.getUserImage())) {
			RequestParams params = new RequestParams("UTF-8");
			params.addBodyParameter("sex", sexString);
			params.addBodyParameter("userImage", userModel.getUserImage());
			params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
			params.addBodyParameter("userName", nickEdt.getText().toString().trim());
			params.addBodyParameter("userMemo", signatureEdt.getText().toString().trim());
			params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null));
			params.addBodyParameter("address", provinceTxt.getText().toString().trim() + "," + cityTxt.getText().toString().trim() + "," + DistrictTxt.getText().toString().trim());

			new XUtilsNew().httpPost("personal/updateInfo.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
				@Override
				public void onMySuccess(ResponseInfo<String> responseInfo) {

					final Editor editor = preferences.edit();// 获取编辑器
					editor.putString("userImage", userModel.getUserImage());
					editor.commit();

					// 更新环信用户昵称
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							if (path) {
								Toasts.show(PersonalDataActivity.this,
										"修改成功", 0);
							} else {
								Toasts.show(PersonalDataActivity.this,
										"用户头像修改失败", 0);
								path = true;
							}
							pDialog.dismiss();
						}
					}, 500);
					PersonalDataActivity.this.finish();
//					PersonalCenterActivity.instances.finish();
					startActivity(new Intent(PersonalDataActivity.this,PersonalCenterActivity.class).putExtra("userId",userModel.getUserId()));
				}

				@Override
				public void onMyFailure(HttpException error, String msg) {
					pDialog.dismiss();
					Toasts.show(PersonalDataActivity.this, "无法连接服务器，请检查网络", 0);
					falg = true;
				}
			});
		}
	}

	private void data() {
		userModel = (UserLoginModel)getIntent().getSerializableExtra("userModel");


		nickEdt.setText(userModel.getUserName());
		usernameTxt.setText(userModel.getMobilePhone());

		fansTxt.setText(userModel.getUserFans() + "");
		careTxt.setText(userModel.getConcern() + "");
		if ("null".equals(userModel.getUserMemo())
				|| "".equals(userModel.getUserMemo())) {

		} else {
			signatureEdt.setText(userModel.getUserMemo());
			int n = 0;
			if (userModel.getUserMemo() != null) {
				n = userModel.getUserMemo().length();
			}
			signatureTxt.setText(Html.fromHtml(n
					+ "<font color='#9d9d9e'>/50</font>"));
		}
		if ("M".equals(userModel.getSex())) {
			sexString = "M";
			maleImg.setImageResource(R.drawable.male_chosen);
			famaleImg.setImageResource(R.drawable.famale_unchosen);
		} else if ("F".equals(userModel.getSex())) {
			sexString = "F";
			famaleImg.setImageResource(R.drawable.famale_chosen);
			maleImg.setImageResource(R.drawable.male_unchosen);
		} else {
			maleImg.setImageResource(R.drawable.male_unchosen);
			famaleImg.setImageResource(R.drawable.famale_unchosen);
			sexString = "Q";
		}
		if ("null".equals(userModel.getAddress())
				|| "".equals(userModel.getAddress())
				|| userModel.getAddress() == null) {
			provinceTxt.setText("");
			cityTxt.setText("");
			DistrictTxt.setText("");
		} else {
			String[] ss = userModel.getAddress().split(",|，|\\|");
			if (ss.length > 2) {
				provinceTxt.setText(ss[0]);
				cityTxt.setText(ss[1]);
				DistrictTxt.setText(ss[2]);
			}
		}

		Picasso.with(this).load(SystemUtil.IMGPHTH_NEW + userModel.getUserImage()).into(photoImg);





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
		if(pDialog != null){
			pDialog.dismiss();
		}
		super.onDestroy();
	}
}
