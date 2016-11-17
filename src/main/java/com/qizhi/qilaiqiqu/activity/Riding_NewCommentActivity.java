package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.adapter.RidingCommentAdapter;
import com.qizhi.qilaiqiqu.model.RidingCommentsModel;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 *	游记评论
 */
public class Riding_NewCommentActivity extends Activity {

    @InjectView(R.id.layout_ridingComment_back)
    LinearLayout layoutRidingCommentBack;
    @InjectView(R.id.img_ridingComment_photo)
    CircleImageViewUtil imgRidingCommentPhoto;
    @InjectView(R.id.text_commentmessageactivity_message)
    TextView textCommentmessageactivityMessage;
    @InjectView(R.id.txt_ridingComment_content)
    TextView txtRidingCommentContent;
    @InjectView(R.id.list_ridingComment)
    ListView listRidingComment;
    @InjectView(R.id.edt_ridingComment_content)
    EditText edtRidingCommentContent;
    @InjectView(R.id.btn_ridingComment_comment)
    Button btnRidingCommentComment;

    private RidingCommentAdapter adapter;

    private SharedPreferences preferences;

    private List<RidingCommentsModel.CommentsBean> list = new ArrayList<RidingCommentsModel.CommentsBean>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_riding_comment);
        ButterKnife.inject(this);

        // 游记评论查看
        MobclickAgent.onEvent(Riding_NewCommentActivity.this, new UmengEventUtil().click62);

        initView();
        getData();
    }

    private ImageView ridingPhoto;
    private TextView ridingLike;
    private TextView ridingTitle;
    private TextView ridingScanNum;
    private TextView ridingComment;
    private TextView ridingCheckIt;
    private TextView ridingCheckAll;


    int userId;
    int superId;
    int articleId;
    int commentId;
    String type;
    private void initView() {


        articleId = getIntent().getIntExtra("articleId",-1);
        commentId = getIntent().getIntExtra("commentId",-1);

        type = getIntent().getStringExtra("type");


        View view = LayoutInflater.from(this).inflate(R.layout.riding_comment_footerview,null);
        listRidingComment.addFooterView(view);
        adapter = new RidingCommentAdapter(this,list);
        listRidingComment.setAdapter(adapter);

        ridingPhoto = (ImageView) view.findViewById(R.id.footerView_ridingPhoto);
        ridingLike = (TextView) view.findViewById(R.id.txt_footerView_ridingLike);
        ridingTitle = (TextView) view.findViewById(R.id.txt_footerView_ridingTitle);
        ridingCheckIt = (TextView) view.findViewById(R.id.txt_footerView_checkIt);
        ridingCheckAll = (TextView) view.findViewById(R.id.txt_footerView_checkAll);
        ridingScanNum = (TextView) view.findViewById(R.id.txt_footerView_ridingScanNum);
        ridingComment = (TextView) view.findViewById(R.id.txt_footerView_ridingComment);

        ridingCheckIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Riding_NewCommentActivity.this,Riding_NewDetailActivity.class).putExtra("articleId",articleId));
            }
        });

        ridingCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Riding_NewCommentActivity.this,Riding_NewCommentListActivity.class).putExtra("articleId",articleId).putExtra("flag", 1));
            }
        });




    }

    @OnClick({R.id.layout_ridingComment_back, R.id.btn_ridingComment_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_ridingComment_back:
                finish();
                break;

            case R.id.btn_ridingComment_comment:
                comment();
                break;

        }
    }

    private void getData(){
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("commentId",commentId+"");
        params.addQueryStringParameter("userId",preferences.getInt("userId",0)+"");
        params.addQueryStringParameter("uniqueKey",preferences.getString("uniqueKey",null));

        String url = "";
        if("HF".equals(type)){
            url = "article/queryArticelReplyComment.html";
        }else{
            url = "article/queryArticelComment.html";
        }

        new XUtilsNew().httpPost(url, params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    List<RidingCommentsModel.CommentsBean> lists = null;
                    RidingCommentsModel.CommentsBean commentsBean = null;

                    if("HF".equals(type)){
                        ridingLike.setText(object.getInt("praiseNum")+"");
                        ridingScanNum.setText(object.getInt("scanNum")+"");
                        ridingComment.setText(object.getInt("articelComNum")+"");

                        commentsBean = new Gson().fromJson(object.getJSONObject("comments").toString(), new TypeToken<RidingCommentsModel.CommentsBean>() {}.getType());
                        list.clear();
                        list.add(commentsBean);
                    }else{
                        ridingLike.setText(object.getInt("articleprise")+"");
                        ridingScanNum.setText(object.getInt("articleSceen")+"");
                        ridingComment.setText(object.getInt("articelComNum")+"");

                        lists = new Gson().fromJson(object.getJSONArray("comments").toString(),  new TypeToken<List<RidingCommentsModel.CommentsBean>>() {
                        }.getType());
                        list.clear();
                        list.addAll(lists);
                    }

                    txtRidingCommentContent.setText(object.getString("comment"));
                    if(list.size() == 0){
                        textCommentmessageactivityMessage.setText(Html.fromHtml("<font color=\"#6dbfed\">"+object.getString("userName")+"</font>评论了你的游记-<font color=\"#6dbfed\">"+object.getString("title")+"</font>"));
                    }else{
                        textCommentmessageactivityMessage.setText(Html.fromHtml("<font color=\"#6dbfed\">"+object.getString("userName")+"</font>回复了你的游记评论"));
                    }
                    SystemUtil.Imagexutils_photo(object.getString("userImage"),imgRidingCommentPhoto,Riding_NewCommentActivity.this);

                    ridingTitle.setText(object.getString("title"));
                    SystemUtil.Imagexutils_new(object.getString("articleImage"),ridingPhoto,Riding_NewCommentActivity.this);



                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Riding_NewCommentActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }


    private void comment() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("artId",articleId + "");
        params.addBodyParameter("replyId",commentId+ "");
        params.addBodyParameter("commentId",commentId + "");
        params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
        params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null));
        params.addBodyParameter("commentMemo",edtRidingCommentContent.getText().toString().trim());
        new XUtilsNew().httpPost("article/replyComment.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {

            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(responseInfo.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new SystemUtil().makeToast(Riding_NewCommentActivity.this, "回复成功");
                getData();

                // 收起键盘
                InputMethodManager imm =  (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    edtRidingCommentContent.setText("");
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Riding_NewCommentActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
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
