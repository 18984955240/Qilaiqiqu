package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.CharacterModel;
import com.qizhi.qilaiqiqu.model.Search_ActivityModel;
import com.qizhi.qilaiqiqu.model.Search_RiderModel;
import com.qizhi.qilaiqiqu.model.Search_RidingModel;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell1 on 2016/5/16.
 */
public class SearchAdapter extends BaseAdapter {

    private Context context;
    //    private String searchType;
    private int nowType;

    int userId;

    private int TYPE_QYJ = 1;
    private int TYPE_HD = 2;
    private int TYPE_PQS = 3;
    private int TYPE_RWZ = 4;
    private int TYPE_TIP = 5;

    ViewHolderTip viewHolder = null;
    ViewHolderQYJ viewHolderQYJ = null;
    ViewHolderHD viewHolderHD = null;
    ViewHolderPQS viewHolderPQS = null;
    ViewHolderRWZ viewHolderRWZ = null;

    private List<String> typeList = new ArrayList<String>();

    private List<CharacterModel> list_RWZ = new ArrayList<CharacterModel>();
    private List<Search_RiderModel> list_PQS = new ArrayList<Search_RiderModel>();
    private List<Search_RidingModel> list_QYJ = new ArrayList<Search_RidingModel>();
    private List<Search_ActivityModel> list_HD = new ArrayList<Search_ActivityModel>();

    public SearchAdapter(Context context, List<String> typeList, int userId
            , List<Search_RidingModel> list_QYJ
            , List<Search_ActivityModel> list_HD
            , List<Search_RiderModel> list_PQS
            , List<CharacterModel> list_RWZ) {

        this.userId = userId;
        this.context = context;
//        this.searchType = searchType;

        this.typeList = typeList;

        this.list_RWZ = list_RWZ;
        this.list_PQS = list_PQS;
        this.list_QYJ = list_QYJ;
        this.list_HD = list_HD;

    }

    @Override
    public int getItemViewType(int position) {

        if (typeList.get(0).equals("QYJ")) {
            if (list_QYJ.size() == 0) {
                return TYPE_TIP;
            } else {
                return TYPE_QYJ;
            }
        } else if (typeList.get(0).equals("HD")) {
            if (list_HD.size() == 0) {
                return TYPE_TIP;
            } else {
                return TYPE_HD;
            }
        } else if (typeList.get(0).equals("PQS")) {
            if (list_PQS.size() == 0) {
                return TYPE_TIP;
            } else {
                return TYPE_PQS;
            }
        } else if (typeList.get(0).equals("RWZ")) {
            if (list_RWZ.size() == 0) {
                return TYPE_TIP;
            } else {
                return TYPE_RWZ;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getCount() {

        if (typeList.get(0).equals("QYJ")) {
            if (list_QYJ.size() == 0) {
                return 1;
            } else {
                return list_QYJ.size();
            }

        } else if (typeList.get(0).equals("HD")) {
            if (list_HD.size() == 0) {
                return 1;
            } else {
                return list_HD.size();
            }

        } else if (typeList.get(0).equals("PQS")) {
            if (list_PQS.size() == 0) {
                return 1;
            } else {
                return list_PQS.size();
            }

        } else if (typeList.get(0).equals("RWZ")) {
            if (list_RWZ.size() == 0) {
                return 1;
            } else {
                return list_RWZ.size();
            }

        }
        return 0;
    }

    //返回样式的数量
    @Override
    public int getViewTypeCount() {

        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("getView");

        nowType = getItemViewType(position);
        System.out.println("nowType=" + nowType);
        System.out.println("searchType=" + typeList.get(0));
        System.out.println("getCount()=" + getCount());

        if (convertView == null) {
            switch (nowType) {
                //没有查询类型
                case 5:
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_list_personal_none, null);
                    viewHolder = new ViewHolderTip(convertView);
                    convertView.setTag(viewHolder);
                    break;

                //骑游记
                case 1:
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_list_mainactivity_body, null);
                    viewHolderQYJ = new ViewHolderQYJ(convertView);
                    convertView.setTag(viewHolderQYJ);
                    break;

                //活动
                case 2:
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_list_manage_fragment2, null);
                    viewHolderHD = new ViewHolderHD(convertView);
                    convertView.setTag(viewHolderHD);
                    break;

                //陪骑士
                case 3:
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_rider_recommend, null);
                    viewHolderPQS = new ViewHolderPQS(convertView);
                    convertView.setTag(viewHolderPQS);
                    break;

                //人物志
                case 4:
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_list_character, null);
                    viewHolderRWZ = new ViewHolderRWZ(convertView);
                    convertView.setTag(viewHolderRWZ);
                    break;
            }
        } else {
            switch (nowType) {
                //没有查询类型
                case 5:
                    viewHolder = (ViewHolderTip) convertView.getTag();
                    break;

                //骑游记
                case 1:
                    viewHolderQYJ = (ViewHolderQYJ) convertView.getTag();
                    break;

                //活动
                case 2:
                    viewHolderHD = (ViewHolderHD) convertView.getTag();
                    break;

                //陪骑士
                case 3:
                    viewHolderPQS = (ViewHolderPQS) convertView.getTag();
                    break;

                //人物志
                case 4:
                    viewHolderRWZ = (ViewHolderRWZ) convertView.getTag();
                    break;
            }
        }

        switch (nowType) {
            //没有查询类型
            case 5:
                viewHolder.textTip.setText("没有搜索结果");

                break;

            //骑游记
            case 1:
                viewHolderQYJ.type_QYJ_time.setText(list_QYJ.get(position).getCreateDate().substring(0, 10));
                viewHolderQYJ.type_QYJ_title.setText(list_QYJ.get(position).getTitle());
                viewHolderQYJ.type_QYJ_browse.setText(list_QYJ.get(position).getScanNum() + list_QYJ.get(position).getVirtualScan() + "次浏览");
                viewHolderQYJ.type_QYJ_txtLike.setText((list_QYJ.get(position).getPraiseNum() + list_QYJ.get(position).getVirtualPraise()) + "");

                SystemUtil.Imagexutils_photo(list_QYJ.get(position).getUserImage(), viewHolderQYJ.type_QYJ_photo, context);
                SystemUtil.Imagexutils_new(list_QYJ.get(position).getDefaultImage(), viewHolderQYJ.type_QYJ_image, context);

                viewHolderQYJ.type_QYJ_layoutLike.setVisibility(View.GONE);
                if (list_QYJ.get(position).isPraiseState()) {
                    viewHolderQYJ.type_QYJ_imgLike.setImageResource(R.drawable.like_press);
                    viewHolderQYJ.type_QYJ_txtLike.setTextColor(0xffffffff);

                } else {
                    viewHolderQYJ.type_QYJ_imgLike.setImageResource(R.drawable.like_unpress);
                    viewHolderQYJ.type_QYJ_txtLike.setTextColor(0xffffffff);

                }


                break;

            //活动
            case 2:
                viewHolderHD.type_HD_title.setText(list_HD.get(position).getTitle());
                viewHolderHD.type_HD_name.setText(list_HD.get(position).getUserName());
                viewHolderHD.type_HD_endDate.setText(list_HD.get(position).getYdjzsj());
                viewHolderHD.type_HD_browse.setText(list_HD.get(position).getScanNum() + "次浏览");
                viewHolderHD.type_HD_info.setText("发布于:" + list_HD.get(position).getCreate_date());
                viewHolderHD.type_HD_txtWant.setText(list_HD.get(position).getPraiseNum() + "人想去");
                viewHolderHD.type_HD_apply.setText(list_HD.get(position).getSignCount() + "人已报名");

                SystemUtil.Imagexutils_photo(list_HD.get(position).getUserImage(), viewHolderHD.type_HD_image, context);
                SystemUtil.Imagexutils_new(list_HD.get(position).getPhoto().split(",")[0], viewHolderHD.type_HD_photo, context);
                if (list_HD.get(position).isPraiseState()) {
                    viewHolderHD.type_HD_imgWant.setImageResource(R.drawable.want);
                } else {
                    viewHolderHD.type_HD_imgWant.setImageResource(R.drawable.want_unchosen);
                }


                if (list_HD.get(position).getPrice() == 0) {
//                    viewHolderHD.type_HD_money.setVisibility(View.GONE);
                    viewHolderHD.type_HD_money.setText("免费");
                } else {
//                    viewHolderHD.type_HD_money.setVisibility(View.VISIBLE);
                    viewHolderHD.type_HD_money.setText("收费");
                }
                break;

            //陪骑士
            case 3:
                String[] address = list_PQS.get(position).getRiderArea().split(",");

                if (list_PQS.get(position).getRiderArea().split(",").length > 2) {
                    viewHolderPQS.type_PQS_P.setText(address[0]);
                    viewHolderPQS.type_PQS_C.setText(address[1]);
                    viewHolderPQS.type_PQS_D.setText(address[2]);
                } else if (list_PQS.get(position).getRiderArea().split(",").length == 1) {
                    viewHolderPQS.type_PQS_P.setText(list_PQS.get(position).getRiderArea());
                }

                viewHolderPQS.type_PQS_name.setText(list_PQS.get(position).getUserName());

                if (list_PQS.get(position).getRiderPrice() == 0) {
                    viewHolderPQS.type_PQS_price.setText("免费");
                } else {
                    viewHolderPQS.type_PQS_price.setText(list_PQS.get(position).getRiderPrice() + "元/H");
                }

                if (list_PQS.get(position).getLabel() == null) {

                } else {
                    String str = list_PQS.get(position).getLabel().toString();
                    String[] label = str.split(",");
                    viewHolderPQS.type_PQS_tag.setAdapter(new TagAdapter(label) {
                        @Override
                        public View getView(FlowLayout parent, int position, Object o) {

                            TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.tag_item_activive2, null);
                            textView.setText(o.toString());
                            return textView;
                        }
                    });
                }

                SystemUtil.Imagexutils_photo(list_PQS.get(position).getUserImage(), viewHolderPQS.type_PQS_photo, context);
                SystemUtil.Imagexutils_new(list_PQS.get(position).getPersonalImage(), viewHolderPQS.type_PQS_image, context);

                viewHolderPQS.type_PQS_describe.setText(list_PQS.get(position).getPersonalDesc());
                break;

            //人物志
            case 4:
                SystemUtil.Imagexutils_photo(list_RWZ.get(position).getUserImage(), viewHolderRWZ.txtCharacterPhoto, context);
                SystemUtil.Imagexutils_new(list_RWZ.get(position).getDefaultImage(), viewHolderRWZ.imgCharacterImage, context);
                viewHolderRWZ.imgCharacterTitle.setText(list_RWZ.get(position).getTitle());
                viewHolderRWZ.txtCharacterName.setText(list_RWZ.get(position).getUserName());
                viewHolderRWZ.txtCharacterBrowse.setText(list_RWZ.get(position).getScanNum() + "");
                viewHolderRWZ.txtCharacterRecomment.setText(list_RWZ.get(position).getComment_num() + "");
                break;
        }


        return convertView;
    }


    static class ViewHolderQYJ {
        @InjectView(R.id.img_mainList_background)
        ImageView type_QYJ_image;
        @InjectView(R.id.img_mainList_photo)
        CircleImageViewUtil type_QYJ_photo;
        @InjectView(R.id.txt_mainList_title)
        TextView type_QYJ_title;
        @InjectView(R.id.txt_mainList_time)
        TextView type_QYJ_time;
        @InjectView(R.id.txt_mainList_byBrowse)
        TextView type_QYJ_browse;
        @InjectView(R.id.img_ridinglist_like)
        ImageView type_QYJ_imgLike;
        @InjectView(R.id.txt_ridinglist_like)
        TextView type_QYJ_txtLike;
        @InjectView(R.id.animation)
        TextView type_QYJ_animation;
        @InjectView(R.id.layout_ridinglist_like)
        FrameLayout type_QYJ_layoutLike;

        ViewHolderQYJ(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class ViewHolderPQS {
        @InjectView(R.id.img_riderrecommend_pic)
        ImageView type_PQS_image;
        @InjectView(R.id.img_riderrecommend_photo)
        CircleImageViewUtil type_PQS_photo;
        @InjectView(R.id.txt_riderrecommend_name)
        TextView type_PQS_name;
        @InjectView(R.id.txt_riderrecommend_price)
        TextView type_PQS_price;
        @InjectView(R.id.txt_riderrecommend_p)
        TextView type_PQS_P;
        @InjectView(R.id.txt_riderrecommend_c)
        TextView type_PQS_C;
        @InjectView(R.id.txt_riderrecommend_d)
        TextView type_PQS_D;
        @InjectView(R.id.rider_tag)
        TagFlowLayout type_PQS_tag;
        @InjectView(R.id.txt_riderrecommend_describe)
        TextView type_PQS_describe;

        ViewHolderPQS(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class ViewHolderTip {
        @InjectView(R.id.txt_tip)
        TextView textTip;

        ViewHolderTip(View view) {
            ButterKnife.inject(this, view);
        }
    }

    class ViewHolderRWZ {
        @InjectView(R.id.img_character_image)
        ImageView imgCharacterImage;
        @InjectView(R.id.img_character_title)
        TextView imgCharacterTitle;
        @InjectView(R.id.txt_character_photo)
        CircleImageViewUtil txtCharacterPhoto;
        @InjectView(R.id.txt_character_name)
        TextView txtCharacterName;
        @InjectView(R.id.txt_character_browse)
        TextView txtCharacterBrowse;
        @InjectView(R.id.txt_character_recomment)
        TextView txtCharacterRecomment;

        ViewHolderRWZ(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public void update() {
        notifyDataSetChanged();
    }

    static class ViewHolderHD {
        @InjectView(R.id.txt_managefragment_title)
        TextView type_HD_title;
        @InjectView(R.id.txt_managefragment_browse)
        TextView type_HD_browse;
        @InjectView(R.id.img_managefragment_photo)
        ImageView type_HD_photo;
        @InjectView(R.id.img_managefragment_userImage)
        CircleImageViewUtil type_HD_image;
        @InjectView(R.id.txt_managefragment_activityInfo)
        TextView type_HD_info;
        @InjectView(R.id.txt_managefragment_endDate)
        TextView type_HD_endDate;
        @InjectView(R.id.img_managefragment_want)
        ImageView type_HD_imgWant;
        @InjectView(R.id.txt_managefragment_apply)
        TextView type_HD_apply;
        @InjectView(R.id.txt_managefragment_want)
        TextView type_HD_txtWant;
        @InjectView(R.id.txt_managefragment_money)
        TextView type_HD_money;
        @InjectView(R.id.txt_managefragment_userName)
        TextView type_HD_name;
        @InjectView(R.id.textView9)
        TextView textView9;
        @InjectView(R.id.baomingjiezhi)
        TextView baomingjiezhi;
        @InjectView(R.id.layout_managefragment_info)
        RelativeLayout layoutManagefragmentInfo;

        ViewHolderHD(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
