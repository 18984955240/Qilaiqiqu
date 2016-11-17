package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.activity.Rider_NewSelectDate;
import com.qizhi.qilaiqiqu.model.DateModel;
import com.qizhi.qilaiqiqu.utils.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell1 on 2016/8/24.
 */
public class DateAdapter extends BaseAdapter {

    private String[] monthList = {"", "一月份", "二月份", "三月份", "四月份", "五月份", "六月份", "七月份", "八月份", "九月份", "十月份", "十一月份", "十二月份"};

    // 前一个已选中日期的信息
    private int lastWeek = -1;
    private int lastPosition = -1;
    private int lastDateViewId = -1;

    Rider_NewSelectDate instance;

    private String intoType;
    private Context context;
    private ViewHolder holder;
    private List<DateModel> list;
    private HashMap<String,String> dateMap;
    private List<String> dateList;

    private List<TextView> dateViewList = new ArrayList<TextView>();
    private HashMap<Integer, List<TextView>> hashMap = new HashMap<Integer, List<TextView>>();

    public DateAdapter(Context context, List<DateModel> list,HashMap<String,String> dateMap,String intoType) {
        this.list = list;
        this.context = context;
        this.dateMap = dateMap;
        this.intoType = intoType;
    }
    public DateAdapter(Context context, List<DateModel> list, List<String> dateList, String intoType, Rider_NewSelectDate instance) {
        this.list = list;
        this.context = context;
        this.intoType = intoType;
        this.dateList = dateList;
        this.instance = instance;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_rider_calendar2, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            addTextView();
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.month.setText(monthList[list.get(position).getMonth()]);
        dateViewList.clear();
        getTextView(holder.time,list.get(position).getMonth(),position);
        setDate(position);

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.month)
        TextView month;
        @InjectView(R.id.txt_day0)
        TextView txtDay0;
        @InjectView(R.id.txt_day1)
        TextView txtDay1;
        @InjectView(R.id.txt_day2)
        TextView txtDay2;
        @InjectView(R.id.txt_day3)
        TextView txtDay3;
        @InjectView(R.id.txt_day4)
        TextView txtDay4;
        @InjectView(R.id.txt_day5)
        TextView txtDay5;
        @InjectView(R.id.txt_day6)
        TextView txtDay6;
        @InjectView(R.id.txt_day7)
        TextView txtDay7;
        @InjectView(R.id.txt_day8)
        TextView txtDay8;
        @InjectView(R.id.txt_day9)
        TextView txtDay9;
        @InjectView(R.id.txt_day10)
        TextView txtDay10;
        @InjectView(R.id.txt_day11)
        TextView txtDay11;
        @InjectView(R.id.txt_day12)
        TextView txtDay12;
        @InjectView(R.id.txt_day13)
        TextView txtDay13;
        @InjectView(R.id.txt_day14)
        TextView txtDay14;
        @InjectView(R.id.txt_day15)
        TextView txtDay15;
        @InjectView(R.id.txt_day16)
        TextView txtDay16;
        @InjectView(R.id.txt_day17)
        TextView txtDay17;
        @InjectView(R.id.txt_day18)
        TextView txtDay18;
        @InjectView(R.id.txt_day19)
        TextView txtDay19;
        @InjectView(R.id.txt_day20)
        TextView txtDay20;
        @InjectView(R.id.txt_day21)
        TextView txtDay21;
        @InjectView(R.id.txt_day22)
        TextView txtDay22;
        @InjectView(R.id.txt_day23)
        TextView txtDay23;
        @InjectView(R.id.txt_day24)
        TextView txtDay24;
        @InjectView(R.id.txt_day25)
        TextView txtDay25;
        @InjectView(R.id.txt_day26)
        TextView txtDay26;
        @InjectView(R.id.txt_day27)
        TextView txtDay27;
        @InjectView(R.id.txt_day28)
        TextView txtDay28;
        @InjectView(R.id.txt_day29)
        TextView txtDay29;
        @InjectView(R.id.txt_day30)
        TextView txtDay30;
        @InjectView(R.id.txt_day31)
        TextView txtDay31;
        @InjectView(R.id.txt_day32)
        TextView txtDay32;
        @InjectView(R.id.txt_day33)
        TextView txtDay33;
        @InjectView(R.id.txt_day34)
        TextView txtDay34;
        @InjectView(R.id.txt_day35)
        TextView txtDay35;
        @InjectView(R.id.txt_day36)
        TextView txtDay36;
        @InjectView(R.id.txt_day37)
        TextView txtDay37;
        @InjectView(R.id.txt_day38)
        TextView txtDay38;
        @InjectView(R.id.txt_day39)
        TextView txtDay39;
        @InjectView(R.id.txt_day40)
        TextView txtDay40;
        @InjectView(R.id.txt_day41)
        TextView txtDay41;
        @InjectView(R.id.is_need)
        LinearLayout isNeed;

        @InjectView(R.id.is_need2)
        LinearLayout isNeed2;

        @InjectView(R.id.time)
        LinearLayout time;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


    private void addTextView(){
        dateViewList.add(holder.txtDay0);
        dateViewList.add(holder.txtDay1);
        dateViewList.add(holder.txtDay2);
        dateViewList.add(holder.txtDay3);
        dateViewList.add(holder.txtDay4);
        dateViewList.add(holder.txtDay5);
        dateViewList.add(holder.txtDay6);
        dateViewList.add(holder.txtDay7);
        dateViewList.add(holder.txtDay8);
        dateViewList.add(holder.txtDay9);
        dateViewList.add(holder.txtDay10);
        dateViewList.add(holder.txtDay11);
        dateViewList.add(holder.txtDay12);
        dateViewList.add(holder.txtDay13);
        dateViewList.add(holder.txtDay14);
        dateViewList.add(holder.txtDay15);
        dateViewList.add(holder.txtDay16);
        dateViewList.add(holder.txtDay17);
        dateViewList.add(holder.txtDay18);
        dateViewList.add(holder.txtDay19);
        dateViewList.add(holder.txtDay20);
        dateViewList.add(holder.txtDay21);
        dateViewList.add(holder.txtDay22);
        dateViewList.add(holder.txtDay23);
        dateViewList.add(holder.txtDay24);
        dateViewList.add(holder.txtDay25);
        dateViewList.add(holder.txtDay26);
        dateViewList.add(holder.txtDay27);
        dateViewList.add(holder.txtDay28);
        dateViewList.add(holder.txtDay29);
        dateViewList.add(holder.txtDay30);
        dateViewList.add(holder.txtDay31);
        dateViewList.add(holder.txtDay32);
        dateViewList.add(holder.txtDay33);
        dateViewList.add(holder.txtDay34);
        dateViewList.add(holder.txtDay35);
        dateViewList.add(holder.txtDay36);
        dateViewList.add(holder.txtDay37);
        dateViewList.add(holder.txtDay38);
        dateViewList.add(holder.txtDay39);
        dateViewList.add(holder.txtDay40);
        dateViewList.add(holder.txtDay41);
    }

    private int dateViewId;
    private TextView dateView;


    private void getTextView(ViewGroup viewGroup, final int month, final int position) {
        if (viewGroup == null) {
            return;
        }
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);

            if (view instanceof TextView) {
                // 若是TextView记录下
                dateView = (TextView) view;
                dateView.setText(" ");
                dateViewList.add(dateView);
                hashMap.put(month,dateViewList);

                final int finalI = i;
                dateView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 将textView实例，获取hint的Id
                        TextView t = (TextView) v;
                        dateViewId = Integer.parseInt(t.getHint().toString());

                        int week = list.get(position).getFirstDayInWeek();
                        int dayTotal = list.get(position).getDayTotal();

                        if(week <= dateViewId && dateViewId < (week+dayTotal) ){

                            if("can".equals(list.get(position).getDayInfo().get((dateViewId-week)).getState())){

                                list.get(position).getDayInfo().get(dateViewId-week).setState("yet");
                                String data = DateUtils.dateToString(list.get(position).getDayInfo().get(dateViewId-week).getData());

                                if("release".equals(intoType)){
                                    dateMap.put(data,data);
                                }else{
                                    // 判断是否有已选择日期
                                    if(lastWeek != -1){
                                        list.get(lastPosition).getDayInfo().get(lastDateViewId-lastWeek).setState("can");
                                    }
                                    dateList.clear();
                                    dateList.add(data);
                                    instance.setTime();
                                }


                                // 选择日期后设置上一次选择日期信息
                                lastWeek = week;
                                lastPosition = position;
                                lastDateViewId = dateViewId;


                            }else if("yet".equals(list.get(position).getDayInfo().get(dateViewId-week).getState())){

                                list.get(position).getDayInfo().get(dateViewId-week).setState("can");
                                String data = DateUtils.dateToString(list.get(position).getDayInfo().get(dateViewId-week).getData());

                                if("release".equals(intoType)){
                                    dateMap.remove(data);
                                }else{
                                    dateList.remove(data);
                                }

                                // 取消日期后设置上一次选择日期为初始值
                                lastWeek = -1;
                                lastPosition = -1;
                                lastDateViewId = -1;

                            }
                            notifyDataSetChanged();
                        }



                    }
                });


            } else if (view instanceof LinearLayout) {
                // 若是布局控件（LinearLayout或RelativeLayout）,继续查询子View
                this.getTextView((ViewGroup) view ,month, position);
            }
        }

    }


    private void setDate(int position) {
        if(list.get(position).getDayTotal() == 31){
            holder.isNeed2.setVisibility(View.VISIBLE);
            if(list.get(position).getFirstDayInWeek() <= 4){
                holder.isNeed.setVisibility(View.GONE);
            }else {
                holder.isNeed.setVisibility(View.VISIBLE);
            }
        }else if(list.get(position).getDayTotal() == 30){
            holder.isNeed2.setVisibility(View.VISIBLE);
            if(list.get(position).getFirstDayInWeek() <= 5){
                holder.isNeed.setVisibility(View.GONE);
            }else {
                holder.isNeed.setVisibility(View.VISIBLE);
            }
        }else if(list.get(position).getDayTotal() == 29){
            holder.isNeed.setVisibility(View.GONE);
            holder.isNeed2.setVisibility(View.VISIBLE);
        } else if(list.get(position).getDayTotal() == 28){
            holder.isNeed.setVisibility(View.GONE);
            holder.isNeed2.setVisibility(View.GONE);
        }




        for (int i = 0; i < list.get(position).getDayTotal(); i++) {
            hashMap.get(list.get(position).getMonth()).get(list.get(position).getFirstDayInWeek() + i).setText(list.get(position).getDayInfo().get(i).getDayNum()+"");

            if("release".equals(intoType)){

            }else{

            }

            if("no".equals(list.get(position).getDayInfo().get(i).getState())){
                hashMap.get(list.get(position).getMonth()).get(list.get(position).getFirstDayInWeek() + i).setTextColor(context.getResources().getColor(R.color.gray));
                hashMap.get(list.get(position).getMonth()).get(list.get(position).getFirstDayInWeek() + i).setBackgroundColor(context.getResources().getColor(R.color.white));
            }else if("can".equals(list.get(position).getDayInfo().get(i).getState())){
                hashMap.get(list.get(position).getMonth()).get(list.get(position).getFirstDayInWeek() + i).setTextColor(context.getResources().getColor(R.color.dark));
                hashMap.get(list.get(position).getMonth()).get(list.get(position).getFirstDayInWeek() + i).setBackgroundColor(context.getResources().getColor(R.color.white));
            } else if("yet".equals(list.get(position).getDayInfo().get(i).getState())) {
                hashMap.get(list.get(position).getMonth()).get(list.get(position).getFirstDayInWeek() + i).setTextColor(context.getResources().getColor(R.color.white));
                hashMap.get(list.get(position).getMonth()).get(list.get(position).getFirstDayInWeek() + i).setBackgroundColor(context.getResources().getColor(R.color.bule));
            }



        }
    }

}
