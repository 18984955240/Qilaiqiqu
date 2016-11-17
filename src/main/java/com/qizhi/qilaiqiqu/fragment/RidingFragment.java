package com.qizhi.qilaiqiqu.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.activity.Activity_NewDetailActivity;
import com.qizhi.qilaiqiqu.activity.CharacterListActivity;
import com.qizhi.qilaiqiqu.activity.CityPickerActivity;
import com.qizhi.qilaiqiqu.activity.GuideActivity;
import com.qizhi.qilaiqiqu.activity.LoginActivity;
import com.qizhi.qilaiqiqu.activity.MainActivity_NewActivity;
import com.qizhi.qilaiqiqu.activity.PersonalCenterActivity;
import com.qizhi.qilaiqiqu.activity.RiderGuideActivity;
import com.qizhi.qilaiqiqu.activity.Rider_NewAssessmentActivity;
import com.qizhi.qilaiqiqu.activity.Rider_NewDetailActivity;
import com.qizhi.qilaiqiqu.activity.Riding_NewDetailActivity;
import com.qizhi.qilaiqiqu.activity.SearchActivity;
import com.qizhi.qilaiqiqu.adapter.SlideShowListAdapter;
import com.qizhi.qilaiqiqu.model.CarouselModel;
import com.qizhi.qilaiqiqu.model.Riding_RidingListModel;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.ImageCycleViewUtil;
import com.qizhi.qilaiqiqu.utils.ImageCycleViewUtil.ImageInfo;
import com.qizhi.qilaiqiqu.utils.RefreshLayout;
import com.qizhi.qilaiqiqu.utils.RefreshLayout.OnLoadListener;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.qizhi.qilaiqiqu.utils.XUtilsUtil;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * @author Administrator
 *         主页的骑游记
 */
public class RidingFragment extends Fragment implements OnItemClickListener, OnRefreshListener, OnLoadListener{

    public ListView manageList;
    public View view;
    private List<Riding_RidingListModel> Articlelist;

    private SlideShowListAdapter adapter;
    private Context context;
    private SharedPreferences preferences;
    private XUtilsUtil xUtilsUtil;
    private int pageIndex = 1;
    private List<CarouselModel> cmList;
    List<ImageCycleViewUtil.ImageInfo> IClist;
    public RefreshLayout swipeLayout;
    private View header;

    private ImageCycleViewUtil mImageCycleView;

    public static RidingFragment INSTANCE;

    // 头部导航栏控件
    public TextView txtLocation;
    LinearLayout layoutSearch;
    LinearLayout layoutLocation;
    CircleImageViewUtil imgPhoto;

    @SuppressLint("InlinedApi")
    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        INSTANCE = this;

        view = inflater.inflate(R.layout.fragment_riding, null);
        manageList = (ListView) view.findViewById(R.id.list_mainActivity_slideShow);
        context = getActivity();
        preferences = context.getSharedPreferences("userLogin",
                Context.MODE_PRIVATE);
        xUtilsUtil = new XUtilsUtil();



        Articlelist = new ArrayList<Riding_RidingListModel>();
        adapter = new SlideShowListAdapter(
                context, Articlelist);
        IClist = new ArrayList<ImageCycleViewUtil.ImageInfo>();
        swipeLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
        imageUrl(true);
        return view;

    }

//    private void addRidingImage(String praiseImage, String hotImage) {
//        SystemUtil.Imagexutils(hotImage, imgHot, context);
//        SystemUtil.Imagexutils(praiseImage, imgRecommend, context);
//
//    }

    private void initImage(){
        hotImg.setBackgroundResource(R.color.white);
        newsImg.setBackgroundResource(R.color.white);
        proposeImg.setBackgroundResource(R.color.white);
    }

    /**
     * 轮播图添加
     */

    private TextView hot;
    private TextView news;
    private TextView propose;
    private TextView hotImg;
    private TextView newsImg;
    private TextView proposeImg;

    public ImageView rwz;

    private String atWhere = "zx";

    private void initViewHeader() {
        header = View.inflate(getActivity(),
                R.layout.item_list_mainactivity_header, null);
        manageList.addHeaderView(header);
        manageList.setAdapter(adapter);
        manageList.setOnItemClickListener(RidingFragment.this);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setOnLoadListener(this);

        txtLocation = (TextView) header.findViewById(R.id.txt_mainActivity_location);
        imgPhoto = (CircleImageViewUtil) header.findViewById(R.id.img_mainActivity_photo);
        layoutSearch = (LinearLayout) header.findViewById(R.id.layout_mainActivity_search);
        layoutLocation = (LinearLayout) header.findViewById(R.id.layout_mainActivity_location);

        hot = (TextView) header.findViewById(R.id.txt_riding_hot);
        news = (TextView) header.findViewById(R.id.txt_riding_new);
        propose = (TextView) header.findViewById(R.id.txt_riding_propose);

        hotImg = (TextView) header.findViewById(R.id.img_riding_hot);
        newsImg = (TextView) header.findViewById(R.id.img_riding_new);
        proposeImg = (TextView) header.findViewById(R.id.img_riding_propose);

        rwz = (ImageView) header.findViewById(R.id.img_ridingFragment_rwz);

        hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initImage();
                hotImg.setBackgroundResource(R.color.mainActivity_title);
                atWhere = "rm";
                Articlelist.clear();
                data(atWhere);
                showProgressDialog();
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initImage();
                newsImg.setBackgroundResource(R.color.mainActivity_title);
                atWhere = "zx";
                Articlelist.clear();
                data(atWhere);
                showProgressDialog();
            }
        });
        propose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initImage();
                proposeImg.setBackgroundResource(R.color.mainActivity_title);
                atWhere = "tj";
                Articlelist.clear();
                data(atWhere);
                showProgressDialog();
            }
        });

        rwz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查看人物志
                MobclickAgent.onEvent(getActivity(),"click11");

                startActivity(new Intent(context, CharacterListActivity.class));
            }
        });


        mImageCycleView = (ImageCycleViewUtil) view.findViewById(R.id.icv_topView);
        mImageCycleView.setCycleDelayed(3000);

        mImageCycleView.setOnPageClickListener(new ImageCycleViewUtil.OnPageClickListener() {

                    @Override
                    public void onClick(View imageView, ImageInfo imageInfo) {

                        // 轮播图点击
                        MobclickAgent.onEvent(getActivity(),"click10");

                        if (imageInfo.oppenType.toString().equals("url")) {

                            if(imageInfo.value.toString().contains("weride.com.cn/carouse/app.html")){
                                Intent intent = new Intent(context, GuideActivity.class);
                                context.startActivity(intent);

                            }else{
                                Uri uri = Uri.parse(imageInfo.value.toString());
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                context.startActivity(intent);
                            }

                        } else if (imageInfo.oppenType.toString().equals("app")) {
                            if (imageInfo.type.toString().equals("ARTICLE")) {

                                context.startActivity(new Intent(context, Riding_NewDetailActivity.class)
                                        .putExtra("articleId", Integer.parseInt(imageInfo.value.toString())));

                            }  else if (imageInfo.type.toString().equals("ACTIVITY")) {

                                context.startActivity(new Intent(getActivity(), Activity_NewDetailActivity.class)
                                        .putExtra("activityId",Integer.parseInt(imageInfo.value.toString()))
                                        .putExtra("activityTitle",Integer.parseInt(imageInfo.memo.toString())));

                            } else if (imageInfo.type.toString().equals("RIDER")) {

                                context.startActivity(new Intent(context, Rider_NewDetailActivity.class)
                                        .putExtra("riderId", Integer.parseInt(imageInfo.value.toString())));

                            } else if (imageInfo.type.toString().equals("HDP")) {
                                Intent intent = null;
                                if(imageInfo.value.toString().equals("qlqqssm")){
                                    intent = new Intent(context, GuideActivity.class);
                                }else if(imageInfo.value.toString().equals("qxdrdzwgb")){
                                    intent = new Intent(context, Rider_NewAssessmentActivity.class);
                                }else if(imageInfo.value.toString().equals("pqszmw")){
                                    intent = new Intent(context, RiderGuideActivity.class);
                                }
                                context.startActivity(intent);
                            }
                        }
                    }
                });

        mImageCycleView.loadData(IClist,
                new ImageCycleViewUtil.LoadImageCallBack() {
                    @Override
                    public ImageView loadAndDisplay(
                            ImageCycleViewUtil.ImageInfo imageInfo) {

                        ImageView imageView = new ImageView(context);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                        Picasso.with(context).load(imageInfo.image.toString())
                                .resize(800, 400)
                                .placeholder(R.drawable.bitmap_homepage)
                                .error(R.drawable.bitmap_homepage)
                                .into(imageView);

                        return imageView;

                    }
                });

        mImageCycleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("mImageCycleView.OnTouch()");
                return true;
            }
        });

        SystemUtil.Imagexutils_photo(preferences.getString("userImage", null), imgPhoto, getActivity());
        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("preferences.getInt(\"userId\", -1)="+preferences.getInt("userId", -1));
                if (preferences.getInt("userId", -1) != -1) {
                    startActivity(new Intent(getActivity(),PersonalCenterActivity.class).putExtra("userId",preferences.getInt("userId", -1)));
                } else {
                    Toasts.show(getActivity(), "请登录", 0);
                    Intent intent1 = new Intent(getActivity(),
                            LoginActivity.class);
                    startActivity(intent1);
                }
            }
        });

        layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });

        layoutLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(),CityPickerActivity.class).putExtra("location_city", MainActivity_NewActivity.INSTANCE.locatedCity),0);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //data为B中回传的Intent
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                MainActivity_NewActivity.INSTANCE.locatedCity = data.getExtras().getString("picked_city");
                MainActivity_NewActivity.INSTANCE.locateState = data.getExtras().getInt("locate_state");

                txtLocation.setText(MainActivity_NewActivity.INSTANCE.locatedCity);
                MainActivity_NewActivity.INSTANCE.updateLocateState(MainActivity_NewActivity.INSTANCE.locateState, MainActivity_NewActivity.INSTANCE.locatedCity);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        pageIndex = 1;
        dataJ(atWhere);
        if(null != imgPhoto){
            SystemUtil.Imagexutils_photo(preferences.getString("userImage", null), imgPhoto, getActivity());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }



    /**
     * 轮播数据
     */
    public void imageUrl(final boolean falg) {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, XUtilsNew.URL
                        + "banner/queryPhoto.html",
                new RequestCallBack<String>() {
                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            String data = responseInfo.result;
                            JSONObject jo = new JSONObject(data);
                            JSONArray dataList = jo.getJSONArray("dataList");

                            Gson gson = new Gson();
                            Type type = new TypeToken<List<CarouselModel>>() {
                            }.getType();
                            cmList = new ArrayList<CarouselModel>();
                            cmList = gson.fromJson(dataList.toString(), type);

                            System.out.println("cmList=========="+cmList.size());

                            List<CarouselModel> c = new ArrayList<CarouselModel>();
                            for (int i = 0; i < cmList.size(); i++) {
                                c.add(cmList.get(i));
                            }

                            IClist = new ArrayList<ImageCycleViewUtil.ImageInfo>();
                            for (int i = 0; i < c.size(); i++) {
                                IClist.add(new ImageCycleViewUtil.ImageInfo(
                                        SystemUtil.IMGPHTH_NEW + c.get(i).getImageName(),
                                        c.get(i).getValue(),
                                        c.get(i).getType(),
                                        c.get(i).getOpen_type(),
                                        c.get(i).getMemo()
                                ));
                            }
                            if (IClist.size() == 0) {
                                IClist.add(new ImageCycleViewUtil.ImageInfo(
                                        SystemUtil.IMGPHTH_NEW + "",
                                        "https://www.baidu.com", "URL", 0,""));
                            }
                            if (falg) {
                                initViewHeader();
                            }
                            // 初始化默认查询最新游记
                            data("zx");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                    }
                });
    }


    /**
     * 加载数据
     */

    private String hotImage;
    private String praiseImage;

    private void data(String str) {
        String type = str;

        RequestParams params = new RequestParams("UTF-8");
        pageIndex = 1;
        params.addBodyParameter("pageIndex", pageIndex + "");
        params.addBodyParameter("pageSize", "10");
        params.addBodyParameter("type", type);

        if(preferences.getInt("userId",-1) != -1){
            params.addBodyParameter("userId", preferences.getInt("userId",-1)+"");
            System.out.println("preferences.getInt(userId,-1)="+preferences.getInt("userId",-1));
        }

        System.out.println("type="+type);
        System.out.println("pageIndex="+pageIndex);

        new XUtilsNew().httpPost("article/queryList.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    dissmissProgressDialog();

                    JSONObject jsonObject = new JSONObject(responseInfo.result);

                    pageIndex = jsonObject.optInt("pageIndex");
                    JSONArray jsonArray = jsonObject
                            .optJSONArray("dataList");


                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Riding_RidingListModel>>() {
                    }.getType();
                    List<Riding_RidingListModel> lists = gson.fromJson(jsonArray.toString(),
                            type);

                    Articlelist.clear();
                    Articlelist.addAll(lists);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                dissmissProgressDialog();
                Toasts.show(context, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    /*
     * @Override public void onRefresh() { pageIndex = 1; dataJ(); }
     *
     * @Override public void onLoadingMore() { pageIndex = pageIndex + 1;
     * dataJ(); }
     */
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                            long arg3) {
        if (position != 0) {
            Intent intent = new Intent(context, Riding_NewDetailActivity.class);
            intent.putExtra("isMe", false);
            intent.putExtra("articleId", Articlelist.get(position - 1).getId());
            startActivity(intent);

            System.out.println("Articlelist.get(position - 1).isPraiseState()="+Articlelist.get(position - 1).isPraiseState());
        }
    }

    /**
     * 数据的刷新和加载
     */
    private void dataJ(String str) {

        String type = str;

        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("pageIndex", pageIndex + "");
        params.addBodyParameter("pageSize", "10");
        params.addBodyParameter("type",type);
        if(preferences.getInt("userId",-1) != -1){
            params.addBodyParameter("userId", preferences.getInt("userId",-1)+"");
            System.out.println("preferences.getInt(userId,-1)="+preferences.getInt("userId",-1));
        }

        new XUtilsNew().httpPost("article/queryList.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo.result);

                    JSONArray jsonArray = jsonObject
                            .optJSONArray("dataList");
                    // 数据获取
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Riding_RidingListModel>>() {
                    }.getType();
                    List<Riding_RidingListModel> lists = gson.fromJson(
                            jsonArray.toString(), type);

                    int pageCount = jsonObject.optInt("pageSize");
                    pageIndex = jsonObject.optInt("pageIndex");
                    if (pageIndex == 1) {
                        Articlelist.clear();
                        Articlelist.addAll(lists);
                        //Toasts.show(context, "刷新成功", 0);
                    } else if (1 < pageIndex && pageIndex <= pageCount) {
                        Articlelist.addAll(lists);
                        //Toasts.show(context, "加载成功", 0);
                    } else {
                        pageIndex = jsonObject.optInt("pageIndex");
                        //Toasts.show(context, "已显示全部内容", 0);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {

            }
        });

    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        swipeLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
                pageIndex = 1;
                if (IClist == null) {
                    imageUrl(true);
                } else {
                    dataJ(atWhere);
				}
                // 更新数据
                // 更新完后调用该方法结束刷新

            }
        }, 1500);

    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoad() {
        swipeLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                swipeLayout.setLoading(false);
                pageIndex = pageIndex + 1;
                dataJ(atWhere);
            }
        }, 1500);
    }

    /**
     * 显示进度框
     */
    private ProgressDialog progDialog = null;// 搜索时进度条
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(context);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在加载...");
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
}
