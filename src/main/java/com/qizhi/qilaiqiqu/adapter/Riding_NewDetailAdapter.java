package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.bumptech.glide.Glide;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.activity.MainActivity_NewActivity;
import com.qizhi.qilaiqiqu.model.Riding_NewDatailModel;
import com.qizhi.qilaiqiqu.utils.AMapUtil;
import com.qizhi.qilaiqiqu.utils.SoundPlayer;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell1 on 2016/8/3.
 */
public class Riding_NewDetailAdapter extends BaseAdapter {

    public static final int TYPE_TIME = 0;  // 时间View
    public static final int TYPE_NORMAL = 1;  // 游记详情View
    public static final int TYPE_DEFAULT = 2;  // 默认显示View

    public static int height;

    Riding_NewDatailModel model;

    private List<Riding_NewDatailModel.DetailsBean.ListBean> mList;
    private List<Riding_NewDatailModel> list;
    private Context context;
    private ViewHolder holder;


    private AMap aMap;
    private List<Marker> markerList;
    private List<String> latitudeList;
    private List<String> longitudeList;
    private List<LatLonPoint> latLonPointList;

    public Riding_NewDetailAdapter(Riding_NewDatailModel model, Context context, AMap aMap) {
        this.aMap = aMap;
        this.model = model;
        this.context = context;
        mList = new ArrayList<Riding_NewDatailModel.DetailsBean.ListBean>();
        // 遍历然后重组需要的数据

        markerList = new ArrayList<Marker>();
        latitudeList = new ArrayList<String>();
        longitudeList = new ArrayList<String>();
        latLonPointList = new ArrayList<LatLonPoint>();

        if (null != model.getDetails()) {
            for (int i = 0; i < model.getDetails().size(); i++) {
                for (int j = 0; j < model.getDetails().get(i).getList().size(); j++) {
                    mList.add(model.getDetails().get(i).getList().get(j));
                }
            }
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        setMarkerAndPolyLine();
        if (convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_new_riding_detail, null);
            holder = new ViewHolder(convertView);
            holder.layoutTime = (LinearLayout) convertView.findViewById(R.id.layout_newRidingDetail_time);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if (isShowTimeLayout(position) == TYPE_NORMAL) {
            // 不显示
            holder.layoutTime.setVisibility(View.GONE);
        } else if(isShowTimeLayout(position) == TYPE_TIME){
            // 显示日期
            holder.layoutTime.setVisibility(View.VISIBLE);
            holder.txtTime.setText(mList.get(position).getImageTime()+"");
        }else{
            holder.txtTime.setText(mList.get(position).getImageTime()+"");
        }


        if(mList.get(position).getImageName().indexOf("storage") != -1){
            // 包含
            Picasso.with(context).load(new File(mList.get(position).getImageName()))
                    .resize(1200, 600)
                    .centerInside()
                    .placeholder(R.drawable.bitmap_homepage)
                    .error(R.drawable.bitmap_homepage)
                    .into(holder.imgImage);
        }else if(mList.get(position).getImageName().indexOf("system") != -1){
            Glide.with(context).load(new File(mList.get(position).getImageName()))
                    .override(1200, 600)
                    .centerCrop()
                    .placeholder(R.drawable.bitmap_homepage)
                    .error(R.drawable.bitmap_homepage)
                    .into(holder.imgImage);
        }else{
            // 不包含
            int imageWidth = MainActivity_NewActivity.INSTANCE.screenWidth-SystemUtil.dp2px(context,22f);
            SystemUtil.ImageLoad_article(mList.get(position).getImageName(),holder.imgImage,context, imageWidth);
        }

        if(!"".equals(mList.get(position).getContext())){
            holder.txtContent.setText(mList.get(position).getContext());
        }else{
            holder.txtContent.setVisibility(View.GONE);
        }

        if(!"".equals(mList.get(position).getImageAddress())){
            holder.txtPosition.setText(mList.get(position).getImageAddress());
        }else{
            holder.layoutPosition.setVisibility(View.GONE);
            holder.imgPosition.setVisibility(View.GONE);
        }
        if(!"".equals(mList.get(position).getImageDesc())){
            holder.txtLegend.setText(mList.get(position).getImageDesc());
        }else{
            holder.txtLegend.setVisibility(View.GONE);
        }

        if ("".equals(mList.get(position).getVoicePath())) {
            holder.layoutVoice.setVisibility(View.GONE);
        }else{
            if("none".equals(mList.get(position).getPalyState())){
                holder.imgVoice.setBackgroundResource(R.drawable.voice_start);
                holder.txtVoice.setText("点击播放");
            }else if("ing".equals(mList.get(position).getPalyState())){
                holder.imgVoice.setBackgroundResource(R.drawable.stop);
                holder.txtVoice.setText("正在播放");
            }else if("end".equals(mList.get(position).getPalyState())){
                holder.imgVoice.setBackgroundResource(R.drawable.voice_start);
                holder.txtVoice.setText("播放完毕");
            }
        }


        holder.imgVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("none".equals(mList.get(position).getPalyState())){
                    mList.get(position).setPalyState("ing");
                    notifyDataSetChanged();

                    // 图片语音播放
                    MobclickAgent.onEvent(context, new UmengEventUtil().click65);

                    SoundPlayer.playSound(SystemUtil.IMGPHTH_NEW + mList.get(position).getVoicePath(), new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mList.get(position).setPalyState("end");
                            notifyDataSetChanged();
                        }

                    });


                }else if("ing".equals(mList.get(position).getPalyState())){

                    // 暂停
                    SoundPlayer.pause();
                    mList.get(position).setPalyState("end");
                    notifyDataSetChanged();

                }else if("end".equals(mList.get(position).getPalyState())){

                    mList.get(position).setPalyState("ing");
                    notifyDataSetChanged();

                    SoundPlayer.playSound(SystemUtil.IMGPHTH_NEW + mList.get(position).getVoicePath(), new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mList.get(position).setPalyState("end");
                            notifyDataSetChanged();
                        }

                    });

                }




            }
        });

        return convertView;
    }

    private int isShowTimeLayout(int position) {
        String timePrior;
        String timeAfter;

        if (position != 0) {
            timeAfter = mList.get(position).getImageTime();
            timePrior = mList.get(position - 1).getImageTime();

            if (timeAfter.equals(timePrior)) {
                // 不显示
                return TYPE_NORMAL;
            } else {
                // 显示日期
                return TYPE_TIME;
            }
        } else {
            // 显示日期
            return TYPE_DEFAULT;
        }
    }


    static class ViewHolder {
        @InjectView(R.id.txt_newRidingDetail_content)
        TextView txtContent;
        @InjectView(R.id.img_newRidingDetail_image)
        ImageView imgImage;
        @InjectView(R.id.txt_newRidingDetail_voice)
        TextView txtVoice;
        @InjectView(R.id.img_newRidingDetail_voice)
        TextView imgVoice;
        @InjectView(R.id.txt_newRidingDetail_position)
        TextView txtPosition;
        @InjectView(R.id.txt_newRidingDetail_legend)
        TextView txtLegend;
        @InjectView(R.id.txt_newRidingDetail_time)
        TextView txtTime;
        @InjectView(R.id.layout_newRidingDetail_time)
        LinearLayout layoutTime;
        @InjectView(R.id.layout_newRidingDetail_voice)
        RelativeLayout layoutVoice;
        @InjectView(R.id.layout_newRidingDetail_position)
        LinearLayout layoutPosition;
        @InjectView(R.id.img_newRidingDetail_position)
        ImageView imgPosition;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


    private void setMarkerAndPolyLine() {

        latitudeList.clear();
        longitudeList.clear();
        latLonPointList.clear();
        // 遍历是否有经纬度
        for(int i=0;i<mList.size();i++){
            if(!"".equals(mList.get(i).getImageInglat())){
                String[] Inglat =mList.get(i).getImageInglat().split(",");
                longitudeList.add(Inglat[0]);
                latitudeList.add(Inglat[1]);
            }
        }

        System.out.println("mList.size() = "+mList.size());
        System.out.println("latitudeList.size() = "+latitudeList.size());
        System.out.println("longitudeList.size() = "+longitudeList.size());

        // 添加所有的经纬度点
        for(int i=0;i<longitudeList.size();i++){

            System.out.println("for longitudeList.size() = "+longitudeList.size());
            System.out.println("for latLonPointList.size() = "+latLonPointList.size());

            if(latitudeList.get(i) != null && longitudeList.get(i) != null){

                if(!latitudeList.get(i).equals("null") && !longitudeList.get(i).equals("null")){
                    LatLonPoint mlatLonPoint = new LatLonPoint(Double.parseDouble(latitudeList.get(i)),Double.parseDouble(longitudeList.get(i)));
                    latLonPointList.add(mlatLonPoint);
                }
            }
        }

        System.out.println("latLonPointList.size() = "+latLonPointList.size());

        // 添加所有的marker
        markerList.clear();
        for(int i = 0; i < latLonPointList.size();i++){

            if(null != latitudeList.get(i) && null != longitudeList.get(i)){
                if(latitudeList.get(i) != "" && longitudeList.get(i) != ""){
                    markerList.add(aMap.addMarker(new MarkerOptions().anchor(0.5f,1.0f)
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.strat_map))));

                    markerList.get(markerList.size() - 1).setPosition(
                            AMapUtil.convertToLatLng(latLonPointList.get(i)));
                }
            }
        }

        // 给marker连线
        if (markerList.size() > 1) {
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.width(10f);
            polylineOptions.color(Color.RED);
            for(Marker marker : markerList){
                polylineOptions.add(marker.getPosition());
            }
            aMap.addPolyline(polylineOptions);


            // 显示所有Marker
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for(LatLonPoint latLonPoint : latLonPointList){
                builder.include(AMapUtil.convertToLatLng(latLonPoint));
            }
            LatLngBounds bounds = builder.build();
            aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        }
    }

}
