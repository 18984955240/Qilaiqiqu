package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.activity.PersonalCenterActivity;
import com.qizhi.qilaiqiqu.model.Active_NewModel;
import com.qizhi.qilaiqiqu.model.Rider_NewModel;
import com.qizhi.qilaiqiqu.model.Riding_RidingListModel;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.RotateTextView;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell1 on 2016/8/22.
 */
public class PersonalCenterAdapter extends BaseAdapter {

    private static final int ARTICLE = 0;
    private static final int ACTIVITY = 1;
    private static final int RIDER = 2;
    private static final int COLLECTED = 3;
    private static final int SELECT = 5;
    private static final int TIP = 6;
    private int currentType;//当前item类型

    private List<String> type;

    private List<Rider_NewModel> riderList;
    private List<Active_NewModel> activityList;
    private List<Riding_RidingListModel> articleList;
    private List<Riding_RidingListModel> colloctedList;

    private int userId;
    private int articleTotal;
    private int activityTotal;

    private Context context;
    private PersonalCenterActivity instances;

    private TipHolder tipHolder = null;
    private SelectBarHolder selectBarHolder = null;

    private RiderHolder riderHolder = null;
    private ArticleHolder articleHolder = null;
    private ActivityHolder activityHolder = null;

    private SharedPreferences preferences;

    public PersonalCenterAdapter(List<Riding_RidingListModel> articleList,
                                 List<Active_NewModel> activityList,
                                 List<Rider_NewModel> riderList,
                                 List<Riding_RidingListModel> colloctedList,
                                 List<String> type,
                                 int articleTotal,
                                 int activityTotal,
                                 Context context,
                                 PersonalCenterActivity instances,
                                 int userId) {

        this.riderList = riderList;
        this.articleTotal = articleTotal;
        this.activityTotal = activityTotal;
        this.articleList = articleList;
        this.activityList = activityList;
        this.colloctedList = colloctedList;

        this.type = type;
        this.context = context;
        this.userId = userId;
        this.instances = instances;

        preferences = context.getSharedPreferences("userLogin",Context.MODE_PRIVATE);
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return SELECT;
        }
        if ("article".equals(type.get(0))) {
            if(articleList.size() == 0){
                return TIP;
            }
            return ARTICLE;
        }
        if ("activity".equals(type.get(0))) {
            if(activityList.size() == 0){
                return TIP;
            }
            return ACTIVITY;
        }
        if ("rider".equals(type.get(0))) {
            if(riderList.size() == 0){
                return TIP;
            }
            return RIDER;
        }
        if ("collected".equals(type.get(0))) {
            if(colloctedList.size() == 0){
                return TIP;
            }
            return COLLECTED;
        }
        return super.getItemViewType(position);
    }

    //返回样式的数量
    @Override
    public int getViewTypeCount() {

        return 6;
    }

    @Override
    public int getCount() {
        if ("article".equals(type.get(0))) {
            if (articleList.size() == 0) {
                return 2;
            } else {
                return articleList.size() + 1;
            }
        } else if ("activity".equals(type.get(0))) {
            if (activityList.size() == 0) {
                return 2;
            } else {
                return activityList.size() + 1;
            }

        } else if ("rider".equals(type.get(0))) {
            if (riderList.size() == 0) {
                return 2;
            } else {
                return riderList.size() + 1;
            }

        } else if ("collected".equals(type.get(0))) {
            if (colloctedList.size() == 0) {
                return 2;
            } else {
                return colloctedList.size() + 1;
            }
        } else {
            return 1;
        }
    }

    @Override
    public Object getItem(int position) {
        if ("article".equals(type.get(0))) {
            return articleList.get(position);

        } else if ("activity".equals(type.get(0))) {
            return activityList.get(position);

        } else if ("rider".equals(type.get(0))) {
            return riderList.get(position);

        } else if ("collected".equals(type.get(0))) {
            return colloctedList.get(position);
        } else {
            return null;
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        tipHolder = null;
        selectBarHolder = null;
        riderHolder = null;
        articleHolder = null;
        activityHolder = null;
        currentType = getItemViewType(position);

        if(view == null){
            switch (currentType) {
                case SELECT:
                    view = LayoutInflater.from(context).inflate(R.layout.item_list_personal_center_select, null);
                    selectBarHolder = new SelectBarHolder(view);
                    view.setTag(selectBarHolder);
                    break;

                case ARTICLE:
                    view = LayoutInflater.from(context).inflate(R.layout.item_list_mainactivity_body, null);
                    articleHolder = new ArticleHolder(view);
                    view.setTag(articleHolder);
                    break;

                case ACTIVITY:
                    view = LayoutInflater.from(context).inflate(R.layout.item_list_manage_fragment2, null);
                    activityHolder = new ActivityHolder(view);
                    view.setTag(activityHolder);

                    break;

                case RIDER:
                    view = LayoutInflater.from(context).inflate(R.layout.list_rider_recommend, null);
                    riderHolder = new RiderHolder(view);
                    view.setTag(riderHolder);
                    break;

                case COLLECTED:
                    view = LayoutInflater.from(context).inflate(R.layout.item_list_mainactivity_body, null);
                    articleHolder = new ArticleHolder(view);
                    view.setTag(articleHolder);
                    break;

                case TIP:
                    view = LayoutInflater.from(context).inflate(R.layout.item_list_personal_none, null);
                    tipHolder = new TipHolder(view);
                    view.setTag(tipHolder);
                    break;

            }
        }else{
            switch (currentType) {
                case SELECT:
                    selectBarHolder = (SelectBarHolder) view.getTag();
                    break;

                case ARTICLE:
                    articleHolder = (ArticleHolder) view.getTag();

                    break;

                case ACTIVITY:
                    activityHolder = (ActivityHolder) view.getTag();

                    break;

                case RIDER:
                    riderHolder = (RiderHolder) view.getTag();

                    break;

                case COLLECTED:
                    articleHolder = (ArticleHolder) view.getTag();
                    break;

                case TIP:
                    tipHolder = (TipHolder) view.getTag();
                    break;
            }
        }


        switch (currentType) {
            case SELECT:
                if(userId != preferences.getInt("userId",-1)){
                    selectBarHolder.layoutMyCollected.setVisibility(View.GONE);
//                    selectBarHolder.txtMyArticle.setText("TA的游记(" + articleTotal + ")");
//                    selectBarHolder.txtMyActivity.setText("TA的活动(" + activityTotal + ")");
                    selectBarHolder.txtMyArticle.setText(Html.fromHtml("TA的游记("+"<font color=\"#6dbfed\">"+articleTotal+"</font>"+")"));
                    selectBarHolder.txtMyActivity.setText(Html.fromHtml("TA的活动("+"<font color=\"#6dbfed\">"+activityTotal+"</font>"+")"));
                }else{
                    selectBarHolder.txtMyArticle.setText(Html.fromHtml("我的游记("+"<font color=\"#6dbfed\">"+articleTotal+"</font>"+")"));
                    selectBarHolder.txtMyActivity.setText(Html.fromHtml("我的活动("+"<font color=\"#6dbfed\">"+activityTotal+"</font>"+")"));
                }

                selectBarHolder.layoutMyRider.setOnClickListener(new SelectBarClickListener());
                selectBarHolder.layoutMyArticle.setOnClickListener(new SelectBarClickListener());
                selectBarHolder.layoutMyActivity.setOnClickListener(new SelectBarClickListener());
                selectBarHolder.layoutMyCollected.setOnClickListener(new SelectBarClickListener());


                initSelectBar();
                break;

            case ARTICLE:
                articleHolder.txtTitle.setText(articleList.get(position-1).getTitle());
                articleHolder.txTime.setText(articleList.get(position-1).getCreateDate().substring(0, 10));
                articleHolder.txtMainListByBrowse.setText(articleList.get(position-1).getScanNum() + articleList.get(position-1).getVirtualScan() + "次浏览");
                articleHolder.txtLike.setText((articleList.get(position-1).getPraiseNum() + articleList.get(position-1).getVirtualPraise()) + "");

                SystemUtil.Imagexutils_photo(articleList.get(position-1).getUserImage(),articleHolder.imgPhoto,context);
                SystemUtil.Imagexutils_new(articleList.get(position-1).getDefaultImage(), articleHolder.imgBackground, context);

                if(articleList.get(position-1).getState().equals("publish")){
                    articleHolder.txtDraft.setVisibility(View.GONE);
                }else{
                    articleHolder.txtDraft.setVisibility(View.VISIBLE);
                }

                break;

            case ACTIVITY:
                activityHolder.layoutInfo.setVisibility(View.GONE);
                activityHolder.txtTitle.setText(activityList.get(position-1).getTitle());
                activityHolder.txtBrowse.setText(activityList.get(position-1).getScanNum() + "次浏览");
                SystemUtil.Imagexutils_photo(activityList.get(position-1).getUserImage(),activityHolder.imgUserImage,context);
                SystemUtil.Imagexutils_new(activityList.get(position-1).getPhoto().split(",")[0],activityHolder.imgPhoto,context);

                if (activityList.get(position-1).getPrice() == 0) {
                    activityHolder.txtMoney.setVisibility(View.GONE);
                    activityHolder.txtMoney.setText("免费");
                } else {
                    activityHolder.txtMoney.setVisibility(View.VISIBLE);
                    activityHolder.txtMoney.setText("收费");
                }
                break;

            case RIDER:

                SystemUtil.Imagexutils_new(riderList.get(0).getPersonalImage().split(",")[0],riderHolder.imgRiderrecommendPic,context);

                riderHolder.riderrecommend1.setVisibility(View.GONE);
                riderHolder.txtRiderrecommendPrice2.setVisibility(View.VISIBLE);

                riderHolder.txtRiderrecommendDescribe.setText(riderList.get(0).getRiderDesc());
                riderHolder.txtRiderrecommendPrice2.setText(riderList.get(0).getRiderPrice()+"元/h");

                System.out.print("riderList.get(0).getRiderArea()="+riderList.get(0).getRiderArea());
                if(riderList.get(0).getRiderArea().split(",").length >2){
                    riderHolder.txtRiderrecommendP.setText(riderList.get(0).getRiderArea().split(",")[0]);
                    riderHolder.txtRiderrecommendC.setText(riderList.get(0).getRiderArea().split(",")[1]);
                    riderHolder.txtRiderrecommendD.setText(riderList.get(0).getRiderArea().split(",")[2]);
                }else if(riderList.get(0).getRiderArea().split(",").length  == 1) {
                    riderHolder.txtRiderrecommendP.setText(riderList.get(0).getRiderArea());
                }


                riderHolder.riderTag.setAdapter(new TagAdapter(riderList.get(0).getLabel().split(",")) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {

                        TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.tag_item_activive2,null);
                        textView.setText(o.toString());
                        return textView;
                    }
                });
                break;

            case COLLECTED:
                articleHolder.txtTitle.setText(colloctedList.get(position-1).getTitle());
                articleHolder.txTime.setText(colloctedList.get(position-1).getCreateDate().substring(0, 10));
                articleHolder.txtMainListByBrowse.setText(colloctedList.get(position-1).getScanNum() + colloctedList.get(position-1).getVirtualScan() + "次浏览");
                articleHolder.txtLike.setText((colloctedList.get(position-1).getPraiseNum() + colloctedList.get(position-1).getVirtualPraise()) + "");
                SystemUtil.Imagexutils_photo(colloctedList.get(position-1).getUserImage(),articleHolder.imgPhoto,context);
                SystemUtil.Imagexutils_new(colloctedList.get(position-1).getDefaultImage(), articleHolder.imgBackground, context);

                break;

            case TIP:
                if(userId != preferences.getInt("userId",-1)){
                    if ("article".equals(type.get(0))) {
                        tipHolder.textTip.setText("TA还没有发布游记哦!");
                    } else if ("activity".equals(type.get(0))) {
                        tipHolder.textTip.setText("TA还没有参加活动哦!");
                    } else if ("rider".equals(type.get(0))) {
                        tipHolder.textTip.setText("TA还没有认证成为陪骑士哦!");
                    } else if ("collected".equals(type.get(0))) {
                        tipHolder.textTip.setText("TA还没有任何收藏哦!");
                    }
                }else{
                    if ("article".equals(type.get(0))) {
                        tipHolder.textTip.setText("您还没有发布游记哦!");
                    } else if ("activity".equals(type.get(0))) {
                        tipHolder.textTip.setText("您还没有参加活动哦!");
                    } else if ("rider".equals(type.get(0))) {
                        tipHolder.textTip.setText("您还没有认证成为陪骑士哦!");
                    } else if ("collected".equals(type.get(0))) {
                        tipHolder.textTip.setText("您还没有任何收藏哦!");
                    }
                }

                break;

        }
        return view;
    }

    public class SelectBarClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layout_personalCengter_myArticle:
                    instances.changeType(0);
                    break;

                case R.id.layout_personalCengter_myActivity:
                    instances.changeType(1);
                    break;

                case R.id.layout_personalCengter_myRider:
                    instances.changeType(2);
                    break;

                case R.id.layout_personalCengter_myCollected:
                    instances.changeType(3);
                    break;
            }
        }
    }

    private void initSelectBar() {
        selectBarHolder.imgMyRider.setBackgroundColor(context.getResources().getColor(R.color.white));
        selectBarHolder.imgMyArticle.setBackgroundColor(context.getResources().getColor(R.color.white));
        selectBarHolder.imgMyActivity.setBackgroundColor(context.getResources().getColor(R.color.white));
        selectBarHolder.imgMyCollected.setBackgroundColor(context.getResources().getColor(R.color.white));

        if ("article".equals(type.get(0))) {
            selectBarHolder.imgMyArticle.setBackgroundColor(context.getResources().getColor(R.color.bule));
        } else if ("activity".equals(type.get(0))) {
            selectBarHolder.imgMyActivity.setBackgroundColor(context.getResources().getColor(R.color.bule));
        } else if ("rider".equals(type.get(0))) {
            selectBarHolder.imgMyRider.setBackgroundColor(context.getResources().getColor(R.color.bule));
        } else if ("collected".equals(type.get(0))) {
            selectBarHolder.imgMyCollected.setBackgroundColor(context.getResources().getColor(R.color.bule));
        }
    }

    static class SelectBarHolder {
        @InjectView(R.id.txt_personalCengter_myArticle)
        TextView txtMyArticle;
        @InjectView(R.id.img_personalCengter_myArticle)
        TextView imgMyArticle;
        @InjectView(R.id.layout_personalCengter_myArticle)
        LinearLayout layoutMyArticle;
        @InjectView(R.id.txt_personalCengter_myActivity)
        TextView txtMyActivity;
        @InjectView(R.id.img_personalCengter_myActivity)
        TextView imgMyActivity;
        @InjectView(R.id.layout_personalCengter_myActivity)
        LinearLayout layoutMyActivity;
        @InjectView(R.id.txt_personalCengter_myRider)
        TextView txtMyRider;
        @InjectView(R.id.img_personalCengter_myRider)
        TextView imgMyRider;
        @InjectView(R.id.layout_personalCengter_myRider)
        LinearLayout layoutMyRider;
        @InjectView(R.id.txt_personalCengter_myCollected)
        TextView txtMyCollected;
        @InjectView(R.id.img_personalCengter_myCollected)
        TextView imgMyCollected;
        @InjectView(R.id.layout_personalCengter_myCollected)
        LinearLayout layoutMyCollected;

        SelectBarHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class TipHolder {
        @InjectView(R.id.txt_tip)
        TextView textTip;

        TipHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class ArticleHolder {
        @InjectView(R.id.img_mainList_background)
        ImageView imgBackground;
        @InjectView(R.id.img_mainList_photo)
        CircleImageViewUtil imgPhoto;
        @InjectView(R.id.txt_mainList_title)
        TextView txtTitle;
        @InjectView(R.id.txt_mainList_time)
        TextView txTime;
        @InjectView(R.id.txt_mainList_byBrowse)
        TextView txtMainListByBrowse;
        @InjectView(R.id.img_ridinglist_like)
        ImageView imgRidinglistLike;
        @InjectView(R.id.txt_ridinglist_like)
        TextView txtLike;
        @InjectView(R.id.animation)
        TextView animation;
        @InjectView(R.id.layout_ridinglist_like)
        FrameLayout layoutRidinglistLike;
        @InjectView(R.id.txtt_mainList_draft)
        TextView txtDraft;

        ArticleHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class ActivityHolder {
        @InjectView(R.id.txt_managefragment_title)
        TextView txtTitle;
        @InjectView(R.id.txt_managefragment_browse)
        TextView txtBrowse;
        @InjectView(R.id.img_managefragment_photo)
        ImageView imgPhoto;
        @InjectView(R.id.txt_managefragment_money)
        RotateTextView txtMoney;
        @InjectView(R.id.img_managefragment_userImage)
        CircleImageViewUtil imgUserImage;
        @InjectView(R.id.txt_managefragment_userName)
        TextView txtUserName;
        @InjectView(R.id.txt_managefragment_activityInfo)
        TextView txtActivityInfo;
        @InjectView(R.id.baomingjiezhi)
        TextView baomingjiezhi;
        @InjectView(R.id.txt_managefragment_endDate)
        TextView txtEndDate;
        @InjectView(R.id.img_managefragment_want)
        ImageView imgWant;
        @InjectView(R.id.txt_managefragment_apply)
        TextView txtApply;
        @InjectView(R.id.txt_managefragment_want)
        TextView txtWant;
        @InjectView(R.id.layout_managefragment_info)
        RelativeLayout layoutInfo;

        ActivityHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class RiderHolder {
        @InjectView(R.id.img_riderrecommend_pic)
        ImageView imgRiderrecommendPic;
        @InjectView(R.id.img_riderrecommend_photo)
        CircleImageViewUtil imgRiderrecommendPhoto;
        @InjectView(R.id.txt_riderrecommend_name)
        TextView txtRiderrecommendName;
        @InjectView(R.id.txt_riderrecommend_price)
        TextView txtRiderrecommendPrice;
        @InjectView(R.id.txt_riderrecommend_price2)
        TextView txtRiderrecommendPrice2;
        @InjectView(R.id.riderrecommend_1)
        LinearLayout riderrecommend1;
        @InjectView(R.id.txt_riderrecommend_p)
        TextView txtRiderrecommendP;
        @InjectView(R.id.txt_riderrecommend_c)
        TextView txtRiderrecommendC;
        @InjectView(R.id.txt_riderrecommend_d)
        TextView txtRiderrecommendD;
        @InjectView(R.id.rider_tag)
        TagFlowLayout riderTag;
        @InjectView(R.id.txt_riderrecommend_describe)
        TextView txtRiderrecommendDescribe;
        @InjectView(R.id.imageView3)
        ImageView imageView3;

        RiderHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
