package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;


/**
 *  活动报名处理
 */
public class Activity_NewApplyResultActivity extends Activity {


    @InjectView(R.id.txt_activeTitle)
    TextView txtActiveTitle;
    @InjectView(R.id.txt_priceExplain)
    TextView txtPriceExplain;
    @InjectView(R.id.txt_price)
    TextView txtPrice;
    @InjectView(R.id.txt_total)
    TextView txtTotal;
    @InjectView(R.id.txt_userName)
    TextView txtUserName;
    @InjectView(R.id.txt_userPhone)
    TextView txtUserPhone;
    @InjectView(R.id.layout_back)
    LinearLayout layoutBack;


    private int number;
    private int activityId;
    private Double price;

    private String name;
    private String title;
    private String String;
    private String mobile;
    private String priceDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__new_apply_result);
        ButterKnife.inject(this);

        ini();
//        getData();
    }

    private void ini() {
        number = getIntent().getIntExtra("number", 0);
        price = getIntent().getDoubleExtra("price", -1);

        name = getIntent().getStringExtra("name");
        title = getIntent().getStringExtra("title");
        String = getIntent().getStringExtra("String");
        mobile = getIntent().getStringExtra("mobile");
        priceDesc = getIntent().getStringExtra("priceDesc");

        activityId = getIntent().getIntExtra("activityId", -1);

        setView();
    }

    private void setView() {
        txtActiveTitle.setText(title);
        txtPrice.setText(price+"元*"+number);
        txtTotal.setText((price*number)+"元");
        txtPriceExplain.setText(priceDesc+"(费用名称):"+price+"元");

        txtUserName.setText(name);
        txtUserPhone.setText(mobile);
    }

    @OnClick(R.id.layout_back)
    public void onClick() {
        finish();
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
