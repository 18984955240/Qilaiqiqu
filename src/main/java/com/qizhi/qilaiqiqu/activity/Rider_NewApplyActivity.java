package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 *
 */
public class Rider_NewApplyActivity extends Activity {


    @InjectView(R.id.applyRider_back)
    LinearLayout applyRiderBack;
    @InjectView(R.id.applyRider_userName)
    TextView applyRiderUserName;
    @InjectView(R.id.applyRider_date)
    LinearLayout applyRiderDate;
    @InjectView(R.id.txt_time)
    TextView txtTime;
    @InjectView(R.id.layout_time)
    RelativeLayout layoutTime;
    @InjectView(R.id.txt_prices)
    TextView txtPrices;
    @InjectView(R.id.txt_totalPrices)
    TextView txtTotalPrices;
    @InjectView(R.id.edt_phone)
    EditText edtPhone;
    @InjectView(R.id.edt_text)
    EditText edtText;
    @InjectView(R.id.txt_explain)
    TextView txtExplain;
    @InjectView(R.id.applyRider_totalPrice)
    TextView applyRiderTotalPrice;
    @InjectView(R.id.applyRider_commit)
    TextView applyRiderCommit;

    private float prices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_rider);
        ButterKnife.inject(this);
        init();
    }

    private int riderId;
    private String userName;
    private String attendTime;
    private SharedPreferences preferences;

    private void init() {
        prices = getIntent().getFloatExtra("prices",-1);
        riderId = getIntent().getIntExtra("riderId", -1);
        userName = getIntent().getStringExtra("userName");
        attendTime = getIntent().getStringExtra("attendTime");

        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);

        txtPrices.setText(hours+"*"+prices+"元/h");
        txtTotalPrices.setText((hours*prices)+"元");
        applyRiderTotalPrice.setText("订单总额:"+(hours*prices)+"元");

        applyRiderUserName.setText(Html.fromHtml("您约<font color=\"#6dbfed\">" + userName + "</font>骑行，时间为:" + attendTime));
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

    @OnClick({R.id.applyRider_back, R.id.applyRider_commit, R.id.txt_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.applyRider_back:
                finish();
                break;

            case R.id.applyRider_commit:
                if (edtPhone.getText().toString().equals("")) {
                    Toasts.show(Rider_NewApplyActivity.this, "联系方式不能为空", 0);
                } else if (edtText.getText().toString().equals("")) {
                    Toasts.show(Rider_NewApplyActivity.this, "说几句话吧", 0);
                } else if(hours == 0){
                    Toasts.show(Rider_NewApplyActivity.this, "请选择骑行时长", 0);
                } else {
                    appointRider();
                }
                break;

            case R.id.txt_time:
                selectRiderTime(view);
                break;
        }
    }

    public static int hours = 0;
    private String hour;

    private void selectRiderTime(View parentView) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_popup_rider_time, null);

        NumberPicker hourPicker = (NumberPicker) view
                .findViewById(R.id.timePicker);
        TextView mBtnConfirm = (TextView) view
                .findViewById(R.id.ok);

        hourPicker.setMaxValue(24);
        hourPicker.setMinValue(0);
        hourPicker.setValue(0);

        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker arg0, int oldVal, int newVal) {
                hours = newVal;
                hour = newVal + "小时";
            }
        });


        final PopupWindow popupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });


        mBtnConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (hour == null) {
                    hours = 0;
                    hour = "0小时";
                }

                txtTime.setText(hour);
                txtPrices.setText(hours+"*"+prices+"元/h");
                txtTotalPrices.setText((hours*prices)+"元");
                applyRiderTotalPrice.setText("订单总额:"+(hours*prices)+"元");
                popupWindow.dismiss();
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.color.f8f8));
        // 设置好参数之后再show
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    }

    private void appointRider() {
        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("riderId", riderId + "");

        params.addQueryStringParameter("date", attendTime);
        params.addQueryStringParameter("time", hours+"");

        params.addQueryStringParameter("phone", edtPhone.getText().toString());
        params.addQueryStringParameter("message", edtText.getText().toString());
        params.addQueryStringParameter("userId", preferences.getInt("userId", -1) + "");
        params.addQueryStringParameter("uniqueKey", preferences.getString("uniqueKey", null));

        new XUtilsNew().httpPost("rider/applyRider.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {

                    // 提交订单价格
                    int p  =  (new Double(hours*prices)).intValue();
                    Map<String, String> map_value = new HashMap<String, String>();
                    map_value.put("type" , "activity" );
                    MobclickAgent.onEventValue(Rider_NewApplyActivity.this, new UmengEventUtil().click44 , map_value, p);


                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    Toasts.show(Rider_NewApplyActivity.this, "申请约骑成功，请等待陪骑士处理", 0);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Rider_NewApplyActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
        hideKeyBoard();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
    }

    private void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


}
