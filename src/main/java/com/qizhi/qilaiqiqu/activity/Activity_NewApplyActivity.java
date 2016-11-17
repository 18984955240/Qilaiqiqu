package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.utils.Toasts;
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
 *  活动报名
 */
public class Activity_NewApplyActivity extends Activity {

    @InjectView(R.id.layout_newActiveApply_back)
    LinearLayout layoutBack;
    @InjectView(R.id.edt_newActiveApply_name)
    EditText edtName;
    @InjectView(R.id.edt_newActiveApply_phone)
    EditText edtPhone;
    @InjectView(R.id.edt_newActiveApply_number)
    EditText edtNumber;
    @InjectView(R.id.txt_newActiveApply_explain)
    TextView txtExplain;
    @InjectView(R.id.txt_newActiveApply_prices)
    TextView txtPrices;
    @InjectView(R.id.txt_newActiveApply_commit)
    TextView txtCommit;


    private Double total = 0.0;
    private Double prices = 0.0;
    private int activityId;

    private int slxz;
    private String priceDesc;
    private String activityTitle;

    private String name;
    private String phone;
    private int number;

    // 本地用户数据
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_activity__apply);
        ButterKnife.inject(this);
        init();
        initEvent();
        getData();
    }

    private void init() {
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        slxz = getIntent().getIntExtra("slxz",-1);
        prices = getIntent().getDoubleExtra("price",-1);
        activityId = getIntent().getIntExtra("activityId",-1);
        priceDesc = getIntent().getStringExtra("priceDesc");
        activityTitle = getIntent().getStringExtra("activityTitle");

        if(prices == 0){
            txtPrices.setText("免费");
        }else{
            txtPrices.setText("总额:"+prices+"￥");
        }
    }

    private void initEvent() {
        if(slxz == 0){
            edtNumber.setHint("请填写预订数量");
        }else{
            edtNumber.setHint("你最多预订数量为"+slxz+"位");
        }

        edtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if("".equals(s.toString())){
                    txtPrices.setText("总额:"+prices+"￥");
                }else{
                    if(prices == 0){
                        txtPrices.setText("免费");
                    }else{
                        if(slxz == 0){
                            total = (Integer.parseInt(s.toString())*prices);
                            txtPrices.setText("总额:"+total+"￥");
                        }else{
                            if(Integer.parseInt(s.toString()) > slxz){
                                Toasts.show(Activity_NewApplyActivity.this,"你填写的数量大于了预定限制数量",0);
                            }else{
                                total = (Integer.parseInt(s.toString())*prices);
                                txtPrices.setText("总额:"+total+"￥");
                            }
                        }
                    }

                }
            }
        });

    }

    @OnClick({R.id.layout_newActiveApply_back, R.id.txt_newActiveApply_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_newActiveApply_back:
                finish();
                break;

            case R.id.txt_newActiveApply_commit:

                // 提交订单
                MobclickAgent.onEvent(Activity_NewApplyActivity.this,"click27");

                // 订单金额
                int p  =  (new Double(total)).intValue();
                Map<String, String> map_value = new HashMap<String, String>();
                map_value.put("type" , "activity" );
                MobclickAgent.onEventValue(this, "click28" , map_value, p);

                commit();
                break;
        }
    }

    private void commit() {
        if("".equals(edtName.getText().toString())){
            Toasts.show(this,"姓名不能为空哦",0);
            return;
        }
        if("".equals(edtPhone.getText().toString())){
            Toasts.show(this,"手机号码不能为空哦",0);
            return;
        }
        if("".equals(edtNumber.getText().toString())){
            Toasts.show(this,"请填写预订数量",0);
            return;
        }
        if(slxz == 0){
            if (Integer.parseInt(edtNumber.getText().toString()) <= 0) {
                Toasts.show(this, "预订数量必须大于0哦", 0);
                return;
            }
        }else{
            if (Integer.parseInt(edtNumber.getText().toString()) <= 0){
                Toasts.show(this,"预订数量必须大于0哦",0);
                return;
            }else if(Integer.parseInt(edtNumber.getText().toString()) > slxz){
                Toasts.show(this,"你填写的数量大于了预定限制数量",0);
                return;
            }
        }

        name = edtName.getText().toString();
        phone = edtPhone.getText().toString();
        number = Integer.parseInt(edtNumber.getText().toString());
        activityApply();


    }

    public void activityApply() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("activityId",activityId+"");
        params.addBodyParameter("mobile",phone);
        params.addBodyParameter("name",name);
        params.addBodyParameter("total",number+"");
        params.addBodyParameter("uniqueKey",preferences.getString("uniqueKey",null));

        new XUtilsNew().httpPost("activity/activitySign.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    if(prices == 0){
                        Toasts.show(Activity_NewApplyActivity.this,"报名成功",0);
                        Activity_NewApplyActivity.this.finish();
                    }else{
                        startActivity(new Intent(Activity_NewApplyActivity.this,Activity_NewOrderActivity.class)
                                .putExtra("inWay","tj")
                                .putExtra("orderId",object.getString("orderId")));
                    }

                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onMyFailure(HttpException error, String msg) {

            }
        });
    }

    public void getData() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("name","reserve_desc");

        new XUtilsNew().httpPost("common/queryParams.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    txtExplain.setText(object.getString("data"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        MobclickAgent.onPause(this);
    }

}
