package com.qizhi.qilaiqiqu.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.RelativeLayout;

import com.qizhi.qilaiqiqu.R;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by dell1 on 2016/6/30.
 */
public class LoadingDialog extends ProgressDialog  {


    private GifImageView mInnerImg;
    private Context context;

    public LoadingDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);  //设置宽高
        getWindow().setGravity(Gravity.CENTER); //设置居中
        setCancelable(false);  // 设置当返回键按下是否关闭对话框
        setCanceledOnTouchOutside(false);  // 设置当点击对话框以外区域是否关闭对话框
        initView();
    }

    GifDrawable gifDrawable = null;
    private void initView() {
        mInnerImg = (GifImageView) findViewById(R.id.GifImageView);

        try {
            gifDrawable = new GifDrawable(context.getResources(), R.drawable.loading_gif);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void show() {
        super.show();
        if(gifDrawable!=null){
            mInnerImg.setImageDrawable(gifDrawable);
        }

    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(gifDrawable!=null){
            gifDrawable.recycle();
        }
    }


}
