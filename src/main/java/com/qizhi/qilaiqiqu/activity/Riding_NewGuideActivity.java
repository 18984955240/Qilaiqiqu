package com.qizhi.qilaiqiqu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qizhi.qilaiqiqu.R;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.qizhi.qilaiqiqu.utils.SystemUtil.checkSDcard;

/**
 *	游记发布引导
 */
public class Riding_NewGuideActivity extends MyBaseActivity implements View.OnClickListener {

    private TextView txtComfirm;
    private LinearLayout layoutBack;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riding__new_guide);
        initView();
        initEvent();
    }

    private void initView() {
        txtComfirm = (TextView) findViewById(R.id.txt_comfirm);
        layoutBack = (LinearLayout) findViewById(R.id.layout_back);

        preferences = getSharedPreferences("firstInit", Context.MODE_PRIVATE);
    }

    private void initEvent() {
        layoutBack.setOnClickListener(this);
        txtComfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_back:
                finish();
                break;

            case R.id.txt_comfirm:
//                chooseCoverRx();
                // 调用android的图库
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");
                startActivityForResult(i, Crop.REQUEST_PICK);
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //data为B中回传的Intent
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());

        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);

        }
    }

    /**
     * 创建文件夹`
     */
    private static String createFile(){
        String filePath = null;
        if(checkSDcard()){
            filePath = "/sdcard/Qilaiqiqu/Crop";
        } else {
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Qilaiqiqu/Crop";
        }

        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            Log.i("error:", e+"");
        }

        return filePath;
    }

    private void beginCrop(Uri source) {
        System.out.println("source="+source);
        String s = new SimpleDateFormat("yyyyMMddhhmmss")
                .format(new Date());

        Uri destination = Uri.fromFile(new File(createFile()+ "/" + s +"_crop.jpg"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) { // 裁剪成功
            File file = null;
            try {
                file = new File(new URI(Crop.getOutput(result).toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(Riding_NewGuideActivity.this, Riding_NewCoverSelect.class);
            intent.putExtra("photoPath", file.getPath());
            startActivity(intent);

            finish();

        } else if (resultCode == Crop.RESULT_ERROR) { // 裁剪失败
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
