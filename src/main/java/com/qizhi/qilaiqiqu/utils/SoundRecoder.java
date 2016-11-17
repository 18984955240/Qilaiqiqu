package com.qizhi.qilaiqiqu.utils;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.qizhi.qilaiqiqu.utils.SystemUtil.checkSDcard;

/**
 * Created by dell1 on 2016/7/25.
 */
public class SoundRecoder {
    public static MediaRecorder mRecorder;

    public static String url;

    public static void startRecording() {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
            // 文件输出目录
            url = newFileName();
            mRecorder.setOutputFile(url);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            try {
                mRecorder.prepare();
                Thread.sleep(1000);
                mRecorder.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    public static void stopRecording() {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
    }

    public static String newFileName() {

        String s = new SimpleDateFormat("yyyyMMddhhmmss")
                .format(new Date());
        System.out.print("SoundRecoder mFileName: "+ createFile() + "/" + s + ".aac");
        return createFile() + "/" + s + ".aac";
    }

    /**
     * 创建文件夹
     */
    private static String createFile(){
        String filePath = null;
        if(checkSDcard()){
            filePath = "/sdcard/Qilaiqiqu/Voice";
        } else {
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Qilaiqiqu/Voice";
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


}
