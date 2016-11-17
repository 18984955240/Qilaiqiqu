package com.qizhi.qilaiqiqu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.bumptech.glide.Glide;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.activity.MapActivity;
import com.qizhi.qilaiqiqu.activity.Riding_NewReleaseActivity;
import com.qizhi.qilaiqiqu.model.RidingContentModel;
import com.qizhi.qilaiqiqu.utils.AMapUtil;
import com.qizhi.qilaiqiqu.utils.SoundPlayer;
import com.qizhi.qilaiqiqu.utils.SoundRecoder;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell1 on 2016/7/22.
 */
public class RidingReleaseAdapter extends BaseAdapter {


    boolean isRecorder = false;

    private Context context;
    private ViewHolder holder;
    public List<Marker> markerList;
    private List<RidingContentModel> list;

    private PopupWindow popupWindow;

    private AMap aMap;
    private List<LatLonPoint> latLonPointList;
    int nowItem = -1;
//    SoundRecoder soundRecorder;


    public RidingReleaseAdapter(Context context, List<RidingContentModel> list, AMap aMap,List<LatLonPoint> latLonPointList,PopupWindow popupWindow) {
        this.aMap = aMap;
        this.list = list;
        this.context = context;
        this.popupWindow = popupWindow;
        this.latLonPointList = latLonPointList;
        markerList = new ArrayList<Marker>();

//        soundRecorder = new SoundRecoder();
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
//        if (convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_releaseactivity, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }

        if(list.get(position).getVoice().equals("")){
            if("none".equals(list.get(position).getRecorderState())){
                holder.imgVoice.setBackgroundResource(R.drawable.voice);
                holder.txtVoice.setText("");
                holder.layoutCancel.setVisibility(View.GONE);
            }else if("recording".equals(list.get(position).getRecorderState())){
                holder.txtVoice.setText("录制中...");
                holder.imgVoice.setBackgroundResource(R.drawable.stop);
                holder.layoutCancel.setVisibility(View.GONE);
            }else if("recordOver".equals(list.get(position).getRecorderState())){
                holder.txtVoice.setText("录制完成");
                holder.imgVoice.setBackgroundResource(R.drawable.voice_start);
                holder.layoutCancel.setVisibility(View.VISIBLE);
            }
        }else{
            if("none".equals(list.get(position).getPalyState())){
                holder.imgVoice.setBackgroundResource(R.drawable.voice_start);
                holder.txtVoice.setText("录制完成");
                holder.layoutCancel.setVisibility(View.VISIBLE);
            }else if("playing".equals(list.get(position).getPalyState())){
                holder.imgVoice.setBackgroundResource(R.drawable.stop);
                holder.txtVoice.setText("正在播放");
                holder.layoutCancel.setVisibility(View.GONE);
            }else if("pause".equals(list.get(position).getPalyState())){
                holder.imgVoice.setBackgroundResource(R.drawable.voice_start);
                holder.txtVoice.setText("播放完毕");
                holder.layoutCancel.setVisibility(View.VISIBLE);
            }

        }

        holder.txtLocation.setText(list.get(position).getPosition());

        holder.edtLegend.setText(list.get(position).getLegend());
        holder.edtContent.setText(list.get(position).getRidingTitle());

        if(list.get(position).getPicture().indexOf("storage") != -1){
            // 包含
            Glide.with(context).load(new File(list.get(position).getPicture()))
                    .override(1200, 600)
                    .centerCrop()
                    .placeholder(R.drawable.bitmap_homepage)
                    .error(R.drawable.bitmap_homepage)
                    .into(holder.imgPhoto);
        }else if(list.get(position).getPicture().indexOf("system") != -1){
            Glide.with(context).load(new File(list.get(position).getPicture()))
                    .override(1200, 600)
                    .centerCrop()
                    .placeholder(R.drawable.bitmap_homepage)
                    .error(R.drawable.bitmap_homepage)
                    .into(holder.imgPhoto);
        }else{
            // 不包含
            SystemUtil.Imagexutils_new(list.get(position).getPicture(),holder.imgPhoto,context);
        }

//        Glide.with(context).load(new File(list.get(position).getPicture())).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imgPhoto);

        holder.txtLocation.setText(list.get(position).getPosition());


        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                upDate();
            }
        });

        holder.imgVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(list.get(position).getVoice().equals("")){
                    if("none".equals(list.get(position).getRecorderState())){

                        // 标注语音
                        MobclickAgent.onEvent(context,"click19");

                        // 录音
                        Riding_NewReleaseActivity.voiceNumber++;
                        System.out.println("recording_Riding_NewReleaseActivity.voiceNumber="+Riding_NewReleaseActivity.voiceNumber);
                        list.get(position).setRecorderState("recording");
                        SoundRecoder.startRecording();
                        recodePopup(position);
                        upDate();
                        return;
                    }

                }else{
                    if("none".equals(list.get(position).getPalyState())){

                        // 播放
                        SoundPlayer.playSound(list.get(position).getVoice(), new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {// 播放结束回调
                                list.get(position).setPalyState("pause");
                                upDate();

                            }
                        });
                        list.get(position).setPalyState("playing");
                        upDate();
                        return;
                    }
                    if("playing".equals(list.get(position).getPalyState())){
                        // 暂停
                        SoundPlayer.pause();
                        list.get(position).setPalyState("pause");
                        upDate();
                        return;
                    }
                    if("pause".equals(list.get(position).getPalyState())){
                        // 播放
                        SoundPlayer.playSound(list.get(position).getVoice(), new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {// 播放结束回调
                                list.get(position).setPalyState("pause");
                                upDate();

                            }
                        });
                        list.get(position).setPalyState("playing");
                        upDate();
                        // 继续播放
                    }

                }


            }
        });
        holder.layoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Riding_NewReleaseActivity.voiceNumber--;
                list.get(position).setVoice("");
                list.get(position).setRecorderState("none");
                list.get(position).setPalyState("none");
                upDate();
            }
        });


        holder.layoutLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("isFrom", "travels");
                ((Activity) context).startActivityForResult(intent, 2);
            }
        });


        // 文本框监听，即时存入输入文字

        holder.edtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                System.out.println("edtContent  position="+position);
                System.out.println("edtContent  s.toString().trim()="+s.toString().trim());

                list.get(position).setRidingTitle(s.toString().trim());
            }
        });

        holder.edtLegend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                System.out.println("edtLegend  position="+position);
                System.out.println("edtLegend  s.toString().trim()="+s.toString().trim());

                list.get(position).setLegend(s.toString().trim());
            }
        });

        holder.txtLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                list.get(position).setPosition(s.toString().trim());
            }
        });


        if(position == (list.size()-1)){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,0,dp2px(context,65f));
            holder.edtLegend.setLayoutParams(params);
        }


        return convertView;

    }



    static class ViewHolder {
        @InjectView(R.id.edt_releaseactivity_content)
        EditText edtContent;
        @InjectView(R.id.img_releaseactivity_photo)
        ImageView imgPhoto;
        @InjectView(R.id.img_releaseactivity_delete)
        ImageView imgDelete;
        @InjectView(R.id.layout_releaseactivity_delete)
        LinearLayout layoutDelete;
        @InjectView(R.id.img_releaseactivity_voice)
        TextView imgVoice;
        @InjectView(R.id.txt_releaseactivity_voice)
        TextView txtVoice;
        @InjectView(R.id.txt_releaseactivity_location)
        TextView txtLocation;
        @InjectView(R.id.layout_releaseactivity_location)
        LinearLayout layoutLocation;
        @InjectView(R.id.edt_releaseactivity_legend)
        EditText edtLegend;
        @InjectView(R.id.layout_releaseactivity_cancel)
        LinearLayout layoutCancel;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


    public void upDate() {
        notifyDataSetChanged();
    }

    private void setMarkerAndPolyLine() {
        markerList.clear();
        for(int i = 0; i < list.size();i++){
            if(null != list.get(i).getLatitude() && null != list.get(i).getLongitude()){
                if(list.get(i).getLatitude() != 0.0 && list.get(i).getLongitude() != 0.0){
                    markerList.add(aMap.addMarker(new MarkerOptions().anchor(0.5f,1.0f)
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.strat_map))));

                    markerList.get(markerList.size() - 1).setPosition(AMapUtil.convertToLatLng(new LatLonPoint(list.get(i).getLatitude(),list.get(i).getLongitude())));
                }
            }
        }
        System.out.print("markerList.size()"+markerList.size());

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
            System.out.print("latLonPointList.size()"+latLonPointList.size());

            LatLngBounds bounds = builder.build();
            if(bounds != null){
//                aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            }

        }
    }


    /**
     * 录音弹窗
     */
    private AnimationDrawable animationDrawable;
    private void recodePopup(final int position){
        // 一个自定义的布局，作为显示的内容
        View view = LayoutInflater.from(context).inflate(R.layout.item_popup_record, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.recode_animation);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.recode_layout);

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);

        popupWindow.setTouchable(true);

        popupWindow.setAnimationStyle(R.style.PopupAnimation);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                System.out.println("popupWindow.setTouchInterceptor");
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

                System.out.println("linearLayout.setOnClickListener");

                if("recording".equals(list.get(position).getRecorderState())){
                    // 停止录音 保存 录音文件
                    list.get(position).setRecorderState("recordOver");
                    SoundRecoder.stopRecording();
                    list.get(position).setVoice(SoundRecoder.url);
                    upDate();
                }
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置好参数之后再show
        popupWindow.showAtLocation(Riding_NewReleaseActivity.INSTANCE.findViewById(R.id.layout_newRelease), Gravity.CENTER, 0, 50);

    }


    /**
     * dp转px
     *
     * @param context
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }


}
