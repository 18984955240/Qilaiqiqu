package com.qizhi.qilaiqiqu.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.adapter.ArrayWheelAdapter;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.widget.OnWheelChangedListener;
import com.qizhi.qilaiqiqu.widget.WheelView;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 *	陪骑认证第二页
 */
public class RiderAuthenticationSecondActivity extends BaseActivity
		implements OnClickListener,OnWheelChangedListener {

	public static Activity instanceSecond = null; 
	
	private LinearLayout timeLayout;
	private LinearLayout backLayout;

	private TextView nextTxt;
	private TextView timeTxt;
	private TextView priceTxt;

	// 地点选择
	private TextView cityTxt;
	private TextView provinceTxt;
	private TextView districtTxt;
	private EditText signatureEdt;
	private RelativeLayout addressLayout;

	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;

	private Button mBtnConfirm;

	// 地址
	public static String attendArea = null;
	public static String attendDate = null;
	public static String attendDesc = null;

	private AssetManager asset;


	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, List<TextView>> hashMap = new HashMap<Integer, List<TextView>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authentication_second_rider);
		instanceSecond = this;

		// 认证达人第二步
		MobclickAgent.onEvent(RiderAuthenticationSecondActivity.this, new UmengEventUtil().click39);

		initView();
		initEvent();
	}

	private void initView() {
		asset = this.getAssets();


		nextTxt = (TextView) findViewById(R.id.txt_riderAuthenticationActivity_second_next);
		priceTxt = (TextView) findViewById(R.id.edt_riderAuthenticationActivity_second_price);
		timeTxt = (TextView) findViewById(R.id.txt_riderAuthenticationActivity_second_time);
		signatureEdt = (EditText) findViewById(R.id.edt_riderAuthenticationActivity_second_signature);
		timeLayout = (LinearLayout) findViewById(R.id.layout_riderAuthenticationActivity_second_time);

		backLayout = (LinearLayout) findViewById(R.id.layout_riderAuthenticationActivity_second_back);

		cityTxt = (TextView) findViewById(R.id.txt_riderAuthenticationActivity_second_city);
		provinceTxt = (TextView) findViewById(R.id.txt_riderAuthenticationActivity_second_province);
		districtTxt = (TextView) findViewById(R.id.txt_riderAuthenticationActivity_second_district);
		addressLayout = (RelativeLayout) findViewById(R.id.layout_riderAuthenticationActivity_second_address);



	}

	private void initEvent() {
		nextTxt.setOnClickListener(this);
		priceTxt.setOnClickListener(this);
		backLayout.setOnClickListener(this);
		timeLayout.setOnClickListener(this);
		addressLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.layout_riderAuthenticationActivity_second_address:
				showPopupWindow(v);
				break;

			case R.id.layout_riderAuthenticationActivity_second_time:
                Intent intent = new Intent(RiderAuthenticationSecondActivity.this,
                        Rider_NewReleaseDate.class);
                startActivityForResult(intent,0);
				break;


			case R.id.txt_riderAuthenticationActivity_second_next:

				attendArea = mCurrentProviceName + "," + mCurrentCityName + "," + mCurrentDistrictName;

                if (priceTxt.getText().toString().trim().equals("") || RiderAuthenticationFirstActivity.attendPrice == null) {
//                    RiderAuthenticationFirstActivity.attendPrice = 0 + "";
					Toasts.show(RiderAuthenticationSecondActivity.this,"请选择陪骑价格", 0);
                }

				if(attendArea.equals("nullnull") || attendArea.equals("")){
					Toasts.show(RiderAuthenticationSecondActivity.this,"请选择陪骑区域", 0);
				}else if(signatureEdt.getText().toString().equals("")){
					Toasts.show(RiderAuthenticationSecondActivity.this,"请填写陪骑说明", 0);
				}else if ("点击设置陪骑时间".equals(timeTxt.getText().toString())){
                    Toasts.show(RiderAuthenticationSecondActivity.this,"请选择陪骑时间", 0);
                }else{

                    attendDesc = signatureEdt.getText().toString();
					startActivity(new Intent(
							RiderAuthenticationSecondActivity.this,
							RiderAuthenticationThirdActivity.class));
				}
				break;

			case R.id.layout_riderAuthenticationActivity_second_back:
				finish();
				break;

			case R.id.edt_riderAuthenticationActivity_second_price:
				showPopupPrice(v);
				break;
		default:
			break;
		}
	}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 0:

                //data为B中回传的Intent
				if(null != data){
					attendDate = data.getExtras().getString("date");//str即为回传的值
					timeTxt.setText(attendDate+"");
				}
                break;
            default:
                break;
        }
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
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

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
				RiderAuthenticationSecondActivity.this, mProvinceDatas));
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

	/**

	 * mCurrentCityName 选择的城市名
	 * mCurrentProviceName 选择的省份名
	 * mCurrentDistrictName 选择的县份名
	 */
	private void showSelectedResult() {
		cityTxt.setText(mCurrentCityName);
		provinceTxt.setText(mCurrentProviceName);
		districtTxt.setText(mCurrentDistrictName);

	}




	String price;
	int position = 0;

	private void showPopupPrice(View v) {
		View view = LayoutInflater.from(this).inflate(
				R.layout.item_riderauthentication_price, null);

		NumberPicker pricePicker = (NumberPicker) view
				.findViewById(R.id.authentication_second_pricepicker);
		TextView mBtnConfirm = (TextView) view
				.findViewById(R.id.authentication_second_pricepicker_ok);

		final String[] p = {"10", "20", "30", "40", "50", "60", "70",
				"80", "90", "100", "110", "120", "130", "140", "150" };
		pricePicker.setDisplayedValues(p);
		pricePicker.setMaxValue(p.length - 1);
		pricePicker.setMinValue(0);
		pricePicker.setValue(position);
		pricePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		pricePicker.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker arg0, int oldVal, int newVal) {
                System.out.print("newVal=" + newVal);

				position = newVal;
			}
		});

		final PopupWindow popupWindow = new PopupWindow(view,
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);

		popupWindow.setTouchable(true);

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
                price = p[position];
				priceTxt.setText(price);
				RiderAuthenticationFirstActivity.attendPrice = price;
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
