package com.qizhi.qilaiqiqu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.adapter.SearchAdapter;
import com.qizhi.qilaiqiqu.model.CharacterModel;
import com.qizhi.qilaiqiqu.model.Search_ActivityModel;
import com.qizhi.qilaiqiqu.model.Search_RiderModel;
import com.qizhi.qilaiqiqu.model.Search_RidingModel;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 搜索
 */
public class SearchActivity extends MyBaseActivity {

    @InjectView(R.id.img_search_back)
    ImageView imgBack;
    @InjectView(R.id.edt_search_input)
    EditText edtInput;
    @InjectView(R.id.txt_search_search)
    TextView txtSearch;
    @InjectView(R.id.txt_search_QYJ)
    TextView txtQYJ;
    @InjectView(R.id.txt_search_HD)
    TextView txtHD;
    @InjectView(R.id.txt_search_PQS)
    TextView txtPQS;
    @InjectView(R.id.txt_search_RWZ)
    TextView txtRWZ;
    @InjectView(R.id.list_search_list)
    ListView listList;

    private SearchAdapter searchAdapter;
    private SharedPreferences preferences;


    private List<String> typeList;
    private String searchValue;
    private int pageIndex = 1;
    private int pageSize = 10;

    private List<Search_RidingModel> list_QYJ;
    private List<CharacterModel> list_RWZ;
    private List<Search_RiderModel> list_PQS;
    private List<Search_ActivityModel> list_HD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);

        // 搜索框
        MobclickAgent.onEvent(SearchActivity.this,"click13");

        init();
        initEvnet();
    }

    private void init() {
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);

        typeList = new ArrayList<String>();
        typeList.add("noneType");

        list_RWZ = new ArrayList<CharacterModel>();
        list_PQS = new ArrayList<Search_RiderModel>();
        list_QYJ = new ArrayList<Search_RidingModel>();
        list_HD = new ArrayList<Search_ActivityModel>();

        searchAdapter = new SearchAdapter(SearchActivity.this,typeList, preferences.getInt("userId", -1),
                list_QYJ, list_HD, list_PQS, list_RWZ);

        listList.setAdapter(searchAdapter);

        // 列表初始化完成之后设置默认搜索类型和UI
        findViewById(R.id.img_search_QYJ).setBackgroundResource(R.color.mainActivity_title);

    }


    private void initEvnet() {
        edtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        listList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(typeList.get(0).equals("QYJ")){
                    System.out.println("position="+position);
                    System.out.println("pageIndex="+pageIndex);
                    System.out.println("list_QYJ.size() + 1="+list_QYJ.size() + 1);
                    if ( pageIndex < list_QYJ.size() + 1) {

                        Intent intent = new Intent(SearchActivity.this, Riding_NewDetailActivity.class);
                        intent.putExtra("isMe", false);
                        intent.putExtra("articleId", list_QYJ.get(position)
                                .getId());
                        startActivity(intent);
                    }
                } else if(typeList.get(0).equals("HD")){
                        Intent intent = new Intent(SearchActivity.this, Activity_NewDetailActivity.class);
                        intent.putExtra("activityId", list_HD.get(position).getId());
                        startActivity(intent);

                } else if(typeList.get(0).equals("PQS")){
                        Intent intent = new Intent(SearchActivity.this, Rider_NewDetailActivity.class);
                        intent.putExtra("riderId", list_PQS.get(position).getId());
                        startActivity(intent);

                } else if(typeList.get(0).equals("RWZ")){
                        Intent intent = new Intent(SearchActivity.this,CharacterDetailsActivity.class);
                        intent.putExtra("url",list_RWZ.get(position).getUrl());
                        intent.putExtra("title",list_RWZ.get(position).getTitle());
                        intent.putExtra("scannum",list_RWZ.get(position).getScanNum());
                        intent.putExtra("createdate",list_RWZ.get(position).getCreateDate());
                        intent.putExtra("comment_num",list_RWZ.get(position).getComment_num());
                        intent.putExtra("characterId",list_RWZ.get(position).getCharacterId()+"");
                        startActivity(intent);
                }
            }
        });

    }

    @OnClick({R.id.img_search_back, R.id.txt_search_search, R.id.txt_search_QYJ, R.id.txt_search_HD, R.id.txt_search_PQS, R.id.txt_search_RWZ})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_search_back:
                finish();
                break;

            case R.id.txt_search_search:
                showProgressDialog();
                searchValue = edtInput.getText().toString().trim();
                search();
                break;

            case R.id.txt_search_QYJ:
                initTextView();
                findViewById(R.id.img_search_QYJ).setBackgroundResource(R.color.mainActivity_title);
                typeList.clear();
                typeList.add("QYJ");
                searchAdapter.update();
                break;

            case R.id.txt_search_HD:
                initTextView();
                findViewById(R.id.img_search_HD).setBackgroundResource(R.color.mainActivity_title);
                typeList.clear();
                typeList.add("HD");

                searchAdapter.update();
                break;

            case R.id.txt_search_PQS:
                initTextView();
                findViewById(R.id.img_search_PQS).setBackgroundResource(R.color.mainActivity_title);
                typeList.clear();
                typeList.add("PQS");
                searchAdapter.update();
                break;

            case R.id.txt_search_RWZ:
                initTextView();
                findViewById(R.id.img_search_RWZ).setBackgroundResource(R.color.mainActivity_title);
                typeList.clear();
                typeList.add("RWZ");
                searchAdapter.update();
                break;
        }
    }

    private void initTextView() {
        findViewById(R.id.img_search_QYJ).setBackgroundResource(R.color.white);
        findViewById(R.id.img_search_HD).setBackgroundResource(R.color.white);
        findViewById(R.id.img_search_PQS).setBackgroundResource(R.color.white);
        findViewById(R.id.img_search_RWZ).setBackgroundResource(R.color.white);
    }


    private void search() {

        if("noneType".equals(typeList.get(0))){
            typeList.clear();
            typeList.add("QYJ");
        }

        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("keyword" ,searchValue);
        params.addQueryStringParameter("pageIndex",pageIndex+"");
        params.addQueryStringParameter("pageSize",pageSize+"");

        params.addQueryStringParameter("tag", typeList.get(0));

        System.out.println("tag="+typeList.get(0));
        System.out.println("keyword="+searchValue);

        new XUtilsNew().httpPost("personal/queryByKeyword.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                dissmissProgressDialog();
                String result = responseInfo.result;

                try {
                    JSONObject object = new JSONObject(result);
                    // 数据获取
                    Gson gson = new Gson();

                    if(typeList.get(0).equals("QYJ")){
                        JSONArray jsonArray = object.optJSONArray("dataList");
                        Type t = new TypeToken<List<Search_RidingModel>>(){}.getType();
                        List<Search_RidingModel> list = gson.fromJson(jsonArray.toString(), t);

                        list_QYJ.clear();
                        list_QYJ.addAll(list);
                        searchAdapter.update();
                        System.out.println("list.size()="+list.size());
                        System.out.println("list_QYJ.size()="+list_QYJ.size());

                    } else if(typeList.get(0).equals("HD")) {

                        JSONArray jsonArray = object.optJSONArray("dataList");
                        Type t = new TypeToken<ArrayList<Search_ActivityModel>>(){}.getType();
                        List<Search_ActivityModel> lists = gson.fromJson(jsonArray.toString(),t);

                        list_HD.clear();
                        list_HD.addAll(lists);
                        searchAdapter.update();

                    } else if(typeList.get(0).equals("PQS")) {

                        JSONArray jsonArray = object.optJSONArray("dataList");
                        Type t = new TypeToken<ArrayList<Search_RiderModel>>(){}.getType();
                        List<Search_RiderModel> lists = gson.fromJson(jsonArray.toString(), t);

                        list_PQS.clear();
                        list_PQS.addAll(lists);
                        searchAdapter.update();

                    } else if(typeList.get(0).equals("RWZ")) {
                        JSONArray jsonArray = object.optJSONArray("dataList");
                        Type t = new TypeToken<ArrayList<CharacterModel>>(){}.getType();
                        List<CharacterModel> lists = gson.fromJson(jsonArray.toString(), t);

                        list_RWZ.clear();
                        list_RWZ.addAll(lists);
                        searchAdapter.update();
                    }


                } catch (JSONException e) {
                    dissmissProgressDialog();
                    e.printStackTrace();
                }

            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                dissmissProgressDialog();
                Toasts.show(SearchActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    /**
     * 显示进度框
     */
    private ProgressDialog progDialog = null;// 搜索时进度条
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在搜索...");
        progDialog.show();
    }
    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
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
