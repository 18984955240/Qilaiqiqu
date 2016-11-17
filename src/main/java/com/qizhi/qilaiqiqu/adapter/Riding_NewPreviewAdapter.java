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
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.RidingContentModel;
import com.qizhi.qilaiqiqu.utils.AMapUtil;
import com.qizhi.qilaiqiqu.utils.SoundPlayer;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell1 on 2016/9/28.
 */

public class Riding_NewPreviewAdapter extends BaseAdapter {

    private ViewHolder holder;
    public static final int TYPE_TIME = 0;  // 时间View
    public static final int TYPE_NORMAL = 1;  // 游记详情View
    public static final int TYPE_DEFAULT = 2;  // 默认显示View

    // 高德地图
    private AMap aMap;
    private List<Marker> markerList;
    private List<Double> latitudeList;
    private List<Double> longitudeList;
    private List<LatLonPoint> latLonPointList;


    private List<RidingContentModel> list;
    private Context context;
    public Riding_NewPreviewAdapter(Context context, List<RidingContentModel> list, AMap aMap){
        this.list = list;
        this.aMap = aMap;
        this.context = context;

        markerList = new ArrayList<Marker>();
        latitudeList = new ArrayList<Double>();
        longitudeList = new ArrayList<Double>();
        latLonPointList = new ArrayList<LatLonPoint>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context).load(new File(list.get(position).getPicture()))
                .resize(1200, 600)
                .centerInside()
                .placeholder(R.drawable.bitmap_homepage)
                .error(R.drawable.bitmap_homepage)
                .into(holder.imgImage);

        if (isShowTimeLayout(position) == TYPE_NORMAL) {
            // 不显示
            holder.layoutTime.setVisibility(View.GONE);
        } else if(isShowTimeLayout(position) == TYPE_TIME){
            // 显示日期
            holder.layoutTime.setVisibility(View.VISIBLE);
            holder.txtTime.setText(list.get(position).getTime().split(" ")[0]);
        }else{
            holder.txtTime.setText(list.get(position).getTime().split(" ")[0]);
        }

        if(!"".equals(list.get(position).getRidingTitle())){
            holder.txtContent.setText(list.get(position).getRidingTitle());
        }else{
            holder.txtContent.setVisibility(View.GONE);
        }

        if(!"".equals(list.get(position).getPosition())){
            holder.txtPosition.setText(list.get(position).getPosition());
        }else{
            holder.layoutPosition.setVisibility(View.GONE);
            holder.imgPosition.setVisibility(View.GONE);
        }
        if(!"".equals(list.get(position).getLegend())){
            holder.txtLegend.setText(list.get(position).getLegend());
        }else{
            holder.txtLegend.setVisibility(View.GONE);
        }

        if ("".equals(list.get(position).getVoice())) {
            holder.layoutVoice.setVisibility(View.GONE);
        }else{
            if("none".equals(list.get(position).getPalyState())){

            }else if("ing".equals(list.get(position).getPalyState())){
                holder.txtVoice.setText("正在播放");
                holder.imgVoice.setBackgroundResource(R.drawable.stop);
            }else if("end".equals(list.get(position).getPalyState())){
                holder.txtVoice.setText("播放完毕");
                holder.imgVoice.setBackgroundResource(R.drawable.voice_start);
            }
        }

        holder.imgVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list.get(position).setPalyState("ing");
                notifyDataSetChanged();

                SoundPlayer.playSound(SystemUtil.IMGPHTH_NEW + list.get(position).getVoice(), new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        list.get(position).setPalyState("end");
                        notifyDataSetChanged();
                    }

                });
            }
        });


        return convertView;
    }

    private int isShowTimeLayout(int position) {
        String timePrior;
        String timeAfter;

        if (position != 0) {
            timeAfter = list.get(position).getTime();
            timePrior = list.get(position - 1).getTime();

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
        for(int i=0;i<list.size();i++){
            if(list.get(i).getLatitude() != null && list.get(i).getLongitude() != null){
                if(list.get(i).getLatitude() != 0.0 && list.get(i).getLongitude() != 0.0){
                    latitudeList.add(list.get(i).getLatitude());
                    longitudeList.add(list.get(i).getLongitude());
                }
            }
        }


        // 添加所有的经纬度点
        for(int i=0;i<longitudeList.size();i++){

            System.out.println("for longitudeList.size() = "+longitudeList.size());
            System.out.println("for latLonPointList.size() = "+latLonPointList.size());

            if(latitudeList.get(i) != null && longitudeList.get(i) != null){

                if(!latitudeList.get(i).equals("null") && !longitudeList.get(i).equals("null")){
                    LatLonPoint mlatLonPoint = new LatLonPoint(latitudeList.get(i),longitudeList.get(i));
                    latLonPointList.add(mlatLonPoint);
                }
            }
        }

        System.out.println("latLonPointList.size() = "+latLonPointList.size());

        // 添加所有的marker
        markerList.clear();
        for(int i = 0; i < latLonPointList.size();i++){

            if(null != latitudeList.get(i) && null != longitudeList.get(i)){
                markerList.add(aMap.addMarker(new MarkerOptions().anchor(0.5f,1.0f)
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.strat_map))));

                markerList.get(markerList.size() - 1).setPosition(
                        AMapUtil.convertToLatLng(latLonPointList.get(i)));
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
