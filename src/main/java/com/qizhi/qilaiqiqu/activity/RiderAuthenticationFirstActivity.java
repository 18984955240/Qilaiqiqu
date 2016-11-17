package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.LabelModel;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;

/**
 *	陪骑认证第一页
 */
public class RiderAuthenticationFirstActivity extends BaseActivity implements
		OnClickListener {

	public static Activity instanceFirst = null;


	private TextView nextTxt;

//	private EditText phoneTxt;




	private LinearLayout layoutBack;
	private LinearLayout pictureLayout;


	private ImageView addImg;

	public static ArrayList<Bitmap> bitList;
	public static ArrayList<String> imageList;

	int num = 0;

//	public static String riderPhone = null;
	public static String attendPrice = null;
    public static String riderMemos = null;
	public static String label = null;

	private String identityStr = null;
	private String equipmentStr = null;
	private String evaluateStr = null;
	private String personalityStr = null;

	private TagFlowLayout identity;
	private TagFlowLayout equipment;
	private TagFlowLayout evaluate;
	private TagFlowLayout personality;

	private EditText riderMemo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authentication_first_rider);

		// 认证达人第一步
		MobclickAgent.onEvent(RiderAuthenticationFirstActivity.this, new UmengEventUtil().click38);

		instanceFirst = this;
		initView();
		initEvent();
		getLabel();
	}

	List<LabelModel> list;
    List<String> identityList = new ArrayList<String>();
    List<String> equipmentList = new ArrayList<String>();
    List<String> evaluateList = new ArrayList<String>();
    List<String> personalityList = new ArrayList<String>();


	public void getLabel() {

		new XUtilsNew().httpGet("rider/queryLabel.html", false, this, new XUtilsNew.XUtilsCallBackGet() {
			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				String str = responseInfo.result;
				JSONObject object;

				identityList.clear();
				equipmentList.clear();
				evaluateList.clear();
				personalityList.clear();

				try {
					object = new JSONObject(str);
						Gson gson = new Gson();
						list = gson
								.fromJson(
										object.getJSONArray("dataList").toString(),
										new TypeToken<ArrayList<LabelModel>>(){}.getType());


                        for(int i= 0;i<4;i++){
							System.out.println("list.get(i).getLabels().size()="+list.get(i).getLabels().size());
                            for (int j= 0;j<list.get(i).getLabels().size();j++){
                                switch (i){
                                    case 0:
                                        identityList.add(list.get(i).getLabels().get(j).getLabelName());
                                        break;
                                    case 1:
                                        equipmentList.add(list.get(i).getLabels().get(j).getLabelName());
                                        break;
                                    case 2:
                                        evaluateList.add(list.get(i).getLabels().get(j).getLabelName());
                                        break;
                                    case 3:
                                        personalityList.add(list.get(i).getLabels().get(j).getLabelName());
                                        break;
                                }
                            }
                        }

					System.out.println("identityList.size()="+identityList.size());
					System.out.println("equipmentList.size()="+equipmentList.size());
					System.out.println("evaluateList.size()="+evaluateList.size());
					System.out.println("personalityList.size()="+personalityList.size());


                        identity.setAdapter(new TagAdapter(identityList) {
                            @Override
                            public View getView(FlowLayout parent, int position, Object o) {
								View v  = LayoutInflater.from(RiderAuthenticationFirstActivity.this).inflate(R.layout.tag_item_activive,null);
								TextView textView = (TextView) v.findViewById(R.id.tag_item);
								textView.setText(o.toString());
								return v;
                            }
                        });
                        equipment.setAdapter(new TagAdapter(equipmentList) {
                            @Override
                            public View getView(FlowLayout parent, int position, Object o) {
								View v  = LayoutInflater.from(RiderAuthenticationFirstActivity.this).inflate(R.layout.tag_item_activive,null);
								TextView textView = (TextView) v.findViewById(R.id.tag_item);
								textView.setText(o.toString());
								return v;
                            }
                        });
                        evaluate.setAdapter(new TagAdapter(evaluateList) {
                            @Override
                            public View getView(FlowLayout parent, int position, Object o) {
								View v  = LayoutInflater.from(RiderAuthenticationFirstActivity.this).inflate(R.layout.tag_item_activive,null);
								TextView textView = (TextView) v.findViewById(R.id.tag_item);
								textView.setText(o.toString());
								return v;
                            }
                        });
                        personality.setAdapter(new TagAdapter(personalityList) {
                            @Override
                            public View getView(FlowLayout parent, int position, Object o) {
								View v  = LayoutInflater.from(RiderAuthenticationFirstActivity.this).inflate(R.layout.tag_item_activive,null);
								TextView textView = (TextView) v.findViewById(R.id.tag_item);
								textView.setText(o.toString());
								return v;
                            }
                        });

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onMyFailure(HttpException error, String msg) {
				Toasts.show(RiderAuthenticationFirstActivity.this, "无法连接服务器，请检查网络", 0);
			}
		});


	}

	private void initView() {
		addImg = (ImageView) findViewById(R.id.img_riderAuthenticationActivity_first_add);

//		phoneTxt = (EditText) findViewById(R.id.edt_riderAuthenticationActivity_first_phoneNum);

		nextTxt = (TextView) findViewById(R.id.txt_riderAuthenticationActivity_first_next);

		layoutBack = (LinearLayout) findViewById(R.id.layout_riderAuthenticationActivity_first_back);
		pictureLayout = (LinearLayout) findViewById(R.id.layout_riderAuthenticationActivity_first_picture);
		bitList = new ArrayList<Bitmap>();
        imageList = new ArrayList<String>();

		identity = (TagFlowLayout) findViewById(R.id.identity);
		equipment = (TagFlowLayout) findViewById(R.id.equipment);
		evaluate = (TagFlowLayout) findViewById(R.id.evaluate);
		personality = (TagFlowLayout) findViewById(R.id.personality);

		riderMemo = (EditText) findViewById(R.id.edt_releaseActiveActivity_riderMemo);

	}

	//标签选中状态，-1为初始未选择
	int identityId = -1;
	int equipmentId = -1;

	private void initEvent() {
		addImg.setOnClickListener(this);
		nextTxt.setOnClickListener(this);
		layoutBack.setOnClickListener(this);


		identity.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
			@Override
			public boolean onTagClick(View view, int position, FlowLayout parent) {
				if(identityId == position){
					identityId = -1;
					identityStr = null;
				}else {
					identityId = position;
					identityStr = identityList.get(position);
				}
                System.out.println(identityStr );
				return true;
			}
		});
        equipment.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
				if(equipmentId == position){
					equipmentId = -1;
					equipmentStr = null;
				}else {
					equipmentId = position;
					equipmentStr = equipmentList.get(position);
				}
                System.out.println(equipmentStr);
                return true;
            }
        });
        evaluate.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                String[] str = selectPosSet.toString().substring(1,selectPosSet.toString().length()-1).split(",");
                if(str.length == 1){
					if(!str[0].trim().equals("")) {
						evaluateStr = evaluateList.get(Integer.parseInt(str[0].trim()));
					}
                }else{
                    evaluateStr = evaluateList.get(Integer.parseInt(str[0].trim())) + "," + evaluateList.get(Integer.parseInt(str[1].trim()));
                }
                System.out.println(evaluateStr);
            }
        });
        personality.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                String[] str = selectPosSet.toString().substring(1,selectPosSet.toString().length()-1).split(",");
                if(str.length == 1){
					if(!str[0].trim().equals("")) {
						personalityStr = personalityList.get(Integer.parseInt(str[0].trim()));
					}
                }else{
                    personalityStr = personalityList.get(Integer.parseInt(str[0].trim())) + "," + personalityList.get(Integer.parseInt(str[1].trim()));
                }
                System.out.println(personalityStr);
            }
        });


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_riderAuthenticationActivity_first_back:
			finish();
			break;

		case R.id.img_riderAuthenticationActivity_first_add:
			Intent i = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
			i.setType("image/*");
			startActivityForResult(i, 2);
			break;

		case R.id.txt_riderAuthenticationActivity_first_next:
			keepData();

			if (bitList.size() == 0 || riderMemos.length() < 1 ) {
				Toasts.show(RiderAuthenticationFirstActivity.this, "你还有未填项哦", 0);
			} else if(identityStr == null ||equipmentStr == null ||evaluateStr == null ||personalityStr == null){
                Toasts.show(RiderAuthenticationFirstActivity.this, "个性标签还未选择哦", 0);
            } else {
//				if (isMobileNO(riderPhone)) {
//
//				} else {
//					Toasts.show(RiderAuthenticationFirstActivity.this,
//							"手机号码格式不正确", 0);
//				}
				for (int j = 0; j < RiderAuthenticationFirstActivity.bitList.size(); j++){
					System.out.println("RiderAuthenticationFirstActivity.bitList="+RiderAuthenticationFirstActivity.bitList.get(j));
				}

				startActivity(new Intent(
						RiderAuthenticationFirstActivity.this,
						RiderAuthenticationSecondActivity.class));

			}
			break;

		default:
			break;
		}
	}

	private void keepData() {

        label = identityStr + "," + equipmentStr + "," + evaluateStr + "," + personalityStr;
        riderMemos = riderMemo.getText().toString().trim();
	}

	/**
	 * 回调
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_CANCELED) {
			switch (requestCode) {
			case 1:
				if (null != data) {
					startPhotoZoom(data.getData());
				}
				break;
			case 2:
				if (data != null) {
					showProgressDialog();
                    String path = null;
					Uri extras = data.getData();

					if (null != extras) {
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(extras, proj, null, null, null);
                        if(cursor.moveToFirst()) {
                            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            path = cursor.getString(column_index);
                        }
                        cursor.close();

						Bitmap photo = null;
						try {
							photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), extras);
						} catch (IOException e) {
							e.printStackTrace();
						}
						setAddView(SystemUtil.comp(photo), num, path);
						num++;
						if (num == 3) {
							addImg.setVisibility(View.GONE);
							break;
						}
					}
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现
	 *
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		if (uri == null) {
			return;
		}
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		// 设置裁剪
		intent.putExtra("crop", true);
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}

	private void setAddView(final Bitmap photo, final int number, final String imagePath) {
		LinearLayout linearLayout = pictureLayout;
		final FrameLayout frameLayout = new FrameLayout(this);
		LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(dp2px(
				this, 100f), LinearLayout.LayoutParams.MATCH_PARENT);
		fp.setMargins(dp2px(this, 9f), 0, 0, 0);
		frameLayout.setLayoutParams(fp);

		ImageView picture = new ImageView(this);
		picture.setScaleType(ScaleType.CENTER_CROP);
		picture.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		picture.setScaleType(ImageView.ScaleType.CENTER_CROP);

		// Toast.makeText(this, uri + "", 0).show();
		picture.setImageBitmap(photo);
		dissmissProgressDialog();
		bitList.add(photo);
        System.out.println("imagePath="+imagePath);
        imageList.add(imagePath);

		ImageView delete = new ImageView(this);
		FrameLayout.LayoutParams deleteParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		deleteParams.gravity = Gravity.RIGHT;
		deleteParams.setMargins(0, dp2px(this, 6f), dp2px(this, 6f), 0);
		delete.setLayoutParams(deleteParams);

		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				bitList.remove(photo);
                imageList.add(imagePath);
				frameLayout.setVisibility(View.GONE);

				num--;
				if (num != 3) {
					addImg.setVisibility(View.VISIBLE);
				} else {
					addImg.setVisibility(View.GONE);
				}
			}
		});
		delete.setImageResource(R.drawable.red_delete);

		frameLayout.addView(picture);
		frameLayout.addView(delete);
		linearLayout.addView(frameLayout);
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



	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("(^1(3[0-9]|5[0-35-9]|8[025-9])\\d{8}$)"
				+ "|(^1(34[0-8]|(3[5-9]|5[017-9]|8[278])\\d)\\d{7}$)"
				+ "|(^1(3[0-2]|5[256]|8[56])\\d{8}$)"
				+ "|(^1((33|53|8[09])[0-9]|349)\\d{7}$)"
				+ "|(^18[09]\\d{8}$)|(^(180|189|133|134|153)\\d{8}$)"
				+ "|(^1(33|53|77|8[019])\\d{8}$)|(^1700\\d{7}$)");
		Matcher m = p.matcher(mobiles);
		return m.matches();
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

	/**
	 * 显示进度框
	 */
	private ProgressDialog progDialog = null;// 进度条
	public void showProgressDialog() {
		if (progDialog == null) {
			progDialog = new ProgressDialog(this);
		}
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(false);
		progDialog.setMessage("图片加载中...");
		progDialog.show();
	}

	/**
	 * 隐藏进度框
	 */
	public void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}



}
