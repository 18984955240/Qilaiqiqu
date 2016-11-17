package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 *  活动评价
 */
public class Activity_NewCommentActivity extends Activity {

    @InjectView(R.id.layout_NewActivityComment_back)
    LinearLayout layoutBack;
    @InjectView(R.id.btn_NewActivityComment_comfirm)
    TextView btnComfirm;
    @InjectView(R.id.img_NewActivityComment_activeImg)
    ImageView imgActiveImg;
    @InjectView(R.id.txt_NewActivityComment_activetitle)
    TextView txtActivetitle;
    @InjectView(R.id.txt_NewActivityComment_award)
    TextView txtAward;
    @InjectView(R.id.img_NewActivityComment_award)
    ImageView imgAward;
    @InjectView(R.id.NewActivityComment_frameLayout)
    FrameLayout layoutAward;
    @InjectView(R.id.edt_NewActivityComment_comment)
    EditText edtComment;
    @InjectView(R.id.txt_NewActivityComment_commentSize)
    TextView txtCommentSize;

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
    private TextView popup_cancel;
    private TextView markPointTxt;

    private SharedPreferences preferences;

    private InputMethodManager imm;

    private int activityId;
    private String orderId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__new_comment);
        ButterKnife.inject(this);

        // 活动评价
        MobclickAgent.onEvent(Activity_NewCommentActivity.this, new UmengEventUtil().click34);

        initView();
        getData();
    }

    private void initView() {
        orderId = getIntent().getStringExtra("orderId");
        activityId = getIntent().getIntExtra("activityId", -1);

        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public void getData() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("activityId",activityId+"");
        params.addBodyParameter("userId",preferences.getInt("userId",-1)+"");
        new XUtilsNew().httpPost("activity/querySignState.html", params, false, Activity_NewCommentActivity.this, new XUtilsNew.XUtilsCallBackPost() {

            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                JSONObject object = null;
                try {
                    object = new JSONObject(responseInfo.result);
                    txtActivetitle.setText(Html.fromHtml("您已经体验了活动<font color=\"#6dbfed\">"+object.getString("title")+"</font>，点评一下！"));
//                    if(){
                        SystemUtil.Imagexutils_new(object.getString("photo").split(",")[0],imgActiveImg,Activity_NewCommentActivity.this);
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Activity_NewCommentActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });


    }


    @OnClick({R.id.layout_NewActivityComment_back, R.id.NewActivityComment_frameLayout,R.id.btn_NewActivityComment_comfirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_NewActivityComment_back:
                finish();
                break;

            case R.id.NewActivityComment_frameLayout:
                showPopupWindow(view);
                break;

            case R.id.btn_NewActivityComment_comfirm:

                // 提交点评
                MobclickAgent.onEvent(Activity_NewCommentActivity.this, new UmengEventUtil().click35);

                comment();
                break;
        }
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

        popup_mark0.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                markPointInt = 1;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                markPointInt = 2;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark2.setOnClickListener(new View.OnClickListener() {

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
        popup_mark3.setOnClickListener(new View.OnClickListener() {

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
        popup_mark4.setOnClickListener(new View.OnClickListener() {

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
        popup_mark5.setOnClickListener(new View.OnClickListener() {

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
        popup_mark6.setOnClickListener(new View.OnClickListener() {

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
        popup_mark7.setOnClickListener(new View.OnClickListener() {

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
        popup_mark8.setOnClickListener(new View.OnClickListener() {

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
        popup_mark9.setOnClickListener(new View.OnClickListener() {

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

        popup_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                imgAward.setVisibility(View.GONE);
                txtAward.setText(markPointInt+"");

                popupWindow.dismiss();
            }
        });

        popup_cancel.setOnClickListener(new View.OnClickListener() {

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


    private void comment(){
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("activityId",activityId+"");
        params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
        params.addBodyParameter("comment",edtComment.getText().toString());
        params.addBodyParameter("orderId",orderId);
        params.addBodyParameter("grade",markPointInt+"");

        new XUtilsNew().httpPost("activity/releaseComment.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                Toasts.show(Activity_NewCommentActivity.this,"点评成功",0);
                finish();
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Activity_NewCommentActivity.this, "无法连接服务器，请检查网络", 0);
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
