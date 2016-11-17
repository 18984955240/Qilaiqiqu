package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.RidingLabelModel;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.soundcloud.android.crop.Crop;
import com.umeng.analytics.MobclickAgent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.jpush.android.api.JPushInterface;

import static com.qizhi.qilaiqiqu.utils.SystemUtil.checkSDcard;

/**
 *	骑游记封面选择
 */
public class Riding_NewCoverSelect extends Activity {

    @InjectView(R.id.img_coverActivity_headerImg)
    ImageView imgCover;
    @InjectView(R.id.img_coverActivity_back)
    ImageView imgBack;
    @InjectView(R.id.Img_coverActivity_reChoose)
    ImageView ImgReChoose;
    @InjectView(R.id.img_coverActivity_photo)
    CircleImageViewUtil imgPhoto;
    @InjectView(R.id.edt_coverActivity_content)
    EditText edtContent;
    @InjectView(R.id.tag_coverActivity_bike)
    TagFlowLayout tagBike;
    @InjectView(R.id.tag_coverActivity_line)
    TagFlowLayout tagLine;
    @InjectView(R.id.tag_coverActivity_author)
    TagFlowLayout tagAuthor;
    @InjectView(R.id.btn_coverActivity_addImg)
    Button btnAddImg;


    // 图片信息集合
    private String coverPath;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__cover_select);
        ButterKnife.inject(this);

        getLabel();
        initView();
        initEvent();

    }

    private void initView() {
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);


        coverPath = getIntent().getStringExtra("photoPath");

        Glide.with(this).load(coverPath).into(imgCover);

        SystemUtil.Imagexutils_photo(preferences.getString("userImage", null), imgPhoto, this);

        // 添加封面图
        MobclickAgent.onEvent(this,"click18");
    }

    List<RidingLabelModel> list;

    private void getLabel() {
        RequestParams params = new RequestParams("UTF-8");
        new XUtilsNew().httpPost("article/queryLabelList.html", params, false, this,  new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                System.out.println("ResponseInfo<String> responseInfo="+responseInfo.result);
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    Gson gson = new Gson();
                    list = gson
                            .fromJson(
                                    object.getJSONArray("dataList").toString(),
                                    new TypeToken<ArrayList<RidingLabelModel>>(){}.getType());

                    for(int i= 0;i<3;i++){
                        for (int j= 0;j<list.get(i).getLabels().size();j++){
                            switch (i){
                                case 0:
                                    bikeList.add(list.get(i).getLabels().get(j).getLabelName());
                                    break;
                                case 1:
                                    lineList.add(list.get(i).getLabels().get(j).getLabelName());
                                    break;
                                case 2:
                                    authorList.add(list.get(i).getLabels().get(j).getLabelName());
                                    break;
                            }
                        }
                    }

                    setLabel();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error,String msg) {
                Toasts.show(Riding_NewCoverSelect.this, "无法连接服务器，请检查网络", 0);
            }
        });

    }

    List<String> bikeList = new ArrayList<String>();
    List<String> lineList = new ArrayList<String>();
    List<String> authorList = new ArrayList<String>();

    String bikeStr = null;
    String lineStr = null;
    String authorStr = null;

    private void setLabel() {
        tagBike.setAdapter(new TagAdapter(bikeList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                View v = LayoutInflater.from(Riding_NewCoverSelect.this).inflate(R.layout.tag_item_activive, null);
                TextView textView = (TextView) v.findViewById(R.id.tag_item);
                textView.setText(o.toString());
                return v;
            }
        });
        tagLine.setAdapter(new TagAdapter(lineList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                View v = LayoutInflater.from(Riding_NewCoverSelect.this).inflate(R.layout.tag_item_activive, null);
                TextView textView = (TextView) v.findViewById(R.id.tag_item);
                textView.setText(o.toString());
                return v;
            }
        });
        tagAuthor.setAdapter(new TagAdapter(authorList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                View v = LayoutInflater.from(Riding_NewCoverSelect.this).inflate(R.layout.tag_item_activive, null);
                TextView textView = (TextView) v.findViewById(R.id.tag_item);
                textView.setText(o.toString());
                return v;
            }
        });
    }

    //标签选中状态，-1为初始未选择
    int bikeId = -1;
    int lineId = -1;
    int authorId = -1;

    private void initEvent() {
        tagBike.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (bikeId == position) {
                    bikeId = -1;
                    bikeStr = null;
                } else {
                    bikeId = position;
                    bikeStr = bikeList.get(position);
                }

                return true;
            }
        });

        tagLine.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (lineId == position) {
                    lineId = -1;
                    lineStr = null;
                } else {
                    lineId = position;
                    lineStr = lineList.get(position);
                }

                return true;
            }
        });

        tagAuthor.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (authorId == position) {
                    authorId = -1;
                    authorStr = null;
                } else {
                    authorId = position;
                    authorStr = authorList.get(position);
                }

                return true;
            }
        });

    }

    @butterknife.OnClick({R.id.img_coverActivity_back, R.id.Img_coverActivity_reChoose, R.id.btn_coverActivity_addImg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_coverActivity_back:
                finish();
                break;

            case R.id.btn_coverActivity_addImg:

                if (edtContent.getText().toString().trim().equals("")) {
                    Toasts.show(Riding_NewCoverSelect.this, "请输入游记标题", 0);
                    break;
                }
                if (authorStr == null || bikeStr == null || lineStr == null) {
                    Toasts.show(Riding_NewCoverSelect.this, "您还有未选择标签", 0);
                    break;
                }

//                initGalleryFinal();

                startActivity(new Intent(Riding_NewCoverSelect.this, Riding_NewReleaseActivity.class)
                        .putExtra("coverPath", coverPath)
                        .putExtra("title", edtContent.getText().toString().trim())
                        .putExtra("label", authorStr+","+bikeStr+","+lineStr));
                finish();
                break;

            case R.id.Img_coverActivity_reChoose:
                rechooseCover();
//                rechooseCoverRx();
                break;
        }
    }

    private void rechooseCover() {
//        Crop.pickImage(this);

        // 调用android的图库
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(i, Crop.REQUEST_PICK);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());

        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    /**
     * 创建文件夹
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

            coverPath = file.getPath();
            imgCover.setImageURI(Crop.getOutput(result));

        } else if (resultCode == Crop.RESULT_ERROR) { // 裁剪失败
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
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

