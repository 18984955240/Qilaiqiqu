package com.qizhi.qilaiqiqu.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.qizhi.qilaiqiqu.utils.XUtilsUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 *	陪骑评价
 */
public class RiderCommentActivity extends MyBaseActivity {


    @InjectView(R.id.layout_riderCommentActivity_back)
    LinearLayout layoutRiderCommentActivityBack;
    @InjectView(R.id.btn_riderCommentActivity_comfirm)
    TextView btnRiderCommentActivityComfirm;
    @InjectView(R.id.txt_riderCommentActivity_return_name)
    TextView txtRiderCommentActivityReturnName;
    @InjectView(R.id.txt_riderCommentActivity_return_time)
    TextView txtRiderCommentActivityReturnTime;
    @InjectView(R.id.txt_riderCommentActivity_return_award)
    TextView txtRiderCommentActivityReturnAward;
    @InjectView(R.id.img_riderCommentActivity_return_award)
    ImageView imgRiderCommentActivityReturnAward;
    @InjectView(R.id.riderCommentActivity_frameLayout)
    FrameLayout riderCommentActivityFrameLayout;
    @InjectView(R.id.edt_riderCommentActivity_my_return)
    EditText edtRiderCommentActivityMyReturn;
    @InjectView(R.id.txt_riderCommentActivity_return_num)
    TextView txtRiderCommentActivityReturnNum;
    private int riderId;
    private int applyId;
    private String orderId;

    private String name;
    private String time;
    private String date;

    private XUtilsUtil xUtilsUtil;
    private SharedPreferences preferences;

    private InputMethodManager imm;

    private int markPointInt = 0;
    private ImageView popup_mark0;
    private ImageView popup_mark1;
    private ImageView popup_mark2;
    private ImageView popup_mark3;
    private ImageView popup_mark4;
    private ImageView popup_mark5;
    private ImageView popup_mark6;
    private ImageView popup_mark7;
    private ImageView popup_mark8;
    private ImageView popup_mark9;
    private TextView popup_ok;
    private TextView markPointTxt;
    private TextView popup_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_comment);

        // 陪骑评价
        MobclickAgent.onEvent(RiderCommentActivity.this, new UmengEventUtil().click49);

        ButterKnife.inject(this);
        initView();
        setView();
        initEvent();
    }

    private void initView() {
        xUtilsUtil = new XUtilsUtil();
        riderId = getIntent().getIntExtra("riderId", -1);
        applyId = getIntent().getIntExtra("applyId", -1);

        name = getIntent().getStringExtra("name");
        time = getIntent().getStringExtra("time");
        date = getIntent().getStringExtra("date");
        orderId = getIntent().getStringExtra("orderId");

        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


    }

    private void setView() {

        txtRiderCommentActivityReturnName.setText(name);
        txtRiderCommentActivityReturnTime.setText(date+" 时长"+time+"小时");
    }

    private void initEvent() {

        edtRiderCommentActivityMyReturn.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtRiderCommentActivityReturnNum.setText(Html.fromHtml(s.length()
                        + "<font color='#9d9d9e'>/50</font>"));
            }
        });
    }


    private void commentRiding() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("orderId", orderId + "");
        params.addBodyParameter("grade", markPointInt + "");
        params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
        params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null));
        params.addBodyParameter("comment", edtRiderCommentActivityMyReturn.getText().toString());

        new XUtilsNew().httpPost("rider/riderComment.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                Toasts.show(RiderCommentActivity.this, "评价成功", 0);

                // 提交评价
                MobclickAgent.onEvent(RiderCommentActivity.this, new UmengEventUtil().click50);

                imm.hideSoftInputFromWindow(
                        edtRiderCommentActivityMyReturn.getWindowToken(),  0);
                RiderCommentActivity.this.finish();
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(RiderCommentActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });

    }


    /**
     * 打赏弹窗
     *
     * @param view popup所依附的布局
     */
    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.item_popup_grade, null);

        popup_mark0 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark0);
        popup_mark1 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark1);
        popup_mark2 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark2);
        popup_mark3 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark3);
        popup_mark4 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark4);
        popup_mark5 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark5);
        popup_mark6 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark6);
        popup_mark7 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark7);
        popup_mark8 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark8);
        popup_mark9 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark9);
        popup_ok = (TextView) contentView.findViewById(R.id.txt_gradePopup_ok);
        markPointTxt = (TextView) contentView
                .findViewById(R.id.txt_gradePopup_markPoint);
        popup_cancel = (TextView) contentView
                .findViewById(R.id.txt_gradePopup_cancel);
        selectMark();

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setAnimationStyle(R.style.PopupAnimation);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        popup_mark0.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                markPointInt = 1;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                markPointInt = 2;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                popup_mark2.setImageResource(R.drawable.award_yellow);
                markPointInt = 3;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                popup_mark2.setImageResource(R.drawable.award_yellow);
                popup_mark3.setImageResource(R.drawable.award_yellow);
                markPointInt = 4;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                popup_mark2.setImageResource(R.drawable.award_yellow);
                popup_mark3.setImageResource(R.drawable.award_yellow);
                popup_mark4.setImageResource(R.drawable.award_yellow);
                markPointInt = 5;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                popup_mark2.setImageResource(R.drawable.award_yellow);
                popup_mark3.setImageResource(R.drawable.award_yellow);
                popup_mark4.setImageResource(R.drawable.award_yellow);
                popup_mark5.setImageResource(R.drawable.award_yellow);
                markPointInt = 6;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark6.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                popup_mark2.setImageResource(R.drawable.award_yellow);
                popup_mark3.setImageResource(R.drawable.award_yellow);
                popup_mark4.setImageResource(R.drawable.award_yellow);
                popup_mark5.setImageResource(R.drawable.award_yellow);
                popup_mark6.setImageResource(R.drawable.award_yellow);
                markPointInt = 7;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark7.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                popup_mark2.setImageResource(R.drawable.award_yellow);
                popup_mark3.setImageResource(R.drawable.award_yellow);
                popup_mark4.setImageResource(R.drawable.award_yellow);
                popup_mark5.setImageResource(R.drawable.award_yellow);
                popup_mark6.setImageResource(R.drawable.award_yellow);
                popup_mark7.setImageResource(R.drawable.award_yellow);
                markPointInt = 8;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark8.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                popup_mark2.setImageResource(R.drawable.award_yellow);
                popup_mark3.setImageResource(R.drawable.award_yellow);
                popup_mark4.setImageResource(R.drawable.award_yellow);
                popup_mark5.setImageResource(R.drawable.award_yellow);
                popup_mark6.setImageResource(R.drawable.award_yellow);
                popup_mark7.setImageResource(R.drawable.award_yellow);
                popup_mark8.setImageResource(R.drawable.award_yellow);
                markPointInt = 9;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark9.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                popup_mark2.setImageResource(R.drawable.award_yellow);
                popup_mark3.setImageResource(R.drawable.award_yellow);
                popup_mark4.setImageResource(R.drawable.award_yellow);
                popup_mark5.setImageResource(R.drawable.award_yellow);
                popup_mark6.setImageResource(R.drawable.award_yellow);
                popup_mark7.setImageResource(R.drawable.award_yellow);
                popup_mark8.setImageResource(R.drawable.award_yellow);
                popup_mark9.setImageResource(R.drawable.award_yellow);
                markPointInt = 10;
                markPointTxt.setText(markPointInt + "分");
            }
        });

        popup_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                imgRiderCommentActivityReturnAward.setVisibility(View.GONE);
                txtRiderCommentActivityReturnAward.setText(markPointInt+"");

                popupWindow.dismiss();
            }
        });

        popup_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.corners_layout));
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 50);

    }

    /**
     * 设置所有评分图片暗色
     */
    public void selectMark() {
        popup_mark0.setImageResource(R.drawable.award_gray);
        popup_mark1.setImageResource(R.drawable.award_gray);
        popup_mark2.setImageResource(R.drawable.award_gray);
        popup_mark3.setImageResource(R.drawable.award_gray);
        popup_mark4.setImageResource(R.drawable.award_gray);
        popup_mark5.setImageResource(R.drawable.award_gray);
        popup_mark6.setImageResource(R.drawable.award_gray);
        popup_mark7.setImageResource(R.drawable.award_gray);
        popup_mark8.setImageResource(R.drawable.award_gray);
        popup_mark9.setImageResource(R.drawable.award_gray);
        markPointInt = 0;
        markPointTxt.setText(markPointInt + "分");
    }

    @OnClick({R.id.layout_riderCommentActivity_back, R.id.btn_riderCommentActivity_comfirm, R.id.riderCommentActivity_frameLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_riderCommentActivity_back:
                finish();
                break;

            case R.id.btn_riderCommentActivity_comfirm:
                if(markPointInt == 0){
                    Toasts.show(RiderCommentActivity.this,"请选择打赏分数",0);
                }else if(edtRiderCommentActivityMyReturn.getText().toString().equals("")){
                    Toasts.show(RiderCommentActivity.this,"请填写评价",0);
                } else {
                    commentRiding();
                }
                break;

            case R.id.riderCommentActivity_frameLayout:
                showPopupWindow(view);
                break;
        }
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
