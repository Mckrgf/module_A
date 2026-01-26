package com.yaobing.framemvpproject.mylibrary.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.yaobing.framemvpproject.mylibrary.R;
import com.yaobing.framemvpproject.mylibrary.ui.CameraPreview;
import com.yaobing.module_middleware.Utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Created by yaobing on 2021/5/18 14:55
 * <p>
 * Describe：拍照界面，无返回结果，只是验证在小米15上拍照速度很慢，且需要远离证件时才能拍照成功（可能因为小米15的聚焦onFocus功能有问题）
 */
public class TakePictureActivity extends Activity {

    private boolean isFront;//是否是正面
    private String name;
    private CameraPreview mCamera;
    private LinearLayout loadingLayout;
    private FrameLayout frameLayout, camera_preview;

    private boolean captureAfterFocus = false;//聚焦成功后拍照
    private Handler handler = new Handler();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_take_picture);

        frameLayout = findViewById(R.id.viewfinder_content);
        loadingLayout = findViewById(R.id.loadingLayout_takePicture);
        camera_preview = findViewById(R.id.frameContent_takePicture);

//        mCamera = Camera.open();//初始化 Camera对象
        mCamera = new CameraPreview(this,null,0,0);
        camera_preview.addView(mCamera);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isFront = bundle.getBoolean("isFront");
            name = "TEST";
//            ALog.i("TakePictureActivity","isFront:"+isFront);

            TextView title = findViewById(R.id.title_takePicture);
            title.setText("TEST");
        }
        mCamera.setOnSurfaceCreatedListener(new CameraPreview.OnSurfaceCreatedListener() {
            @Override
            public void onSurfaceCreated() {
                focusCamera();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {

//                ALog.i("TakePictureActivity","自动对焦结果，success："+success);
            if (!captureAfterFocus) return;
            if (success) {
                //获取照片
                mCamera.takePicture(mPictureCallback);
            } else {
                Toast.makeText(TakePictureActivity.this,"对焦失败",Toast.LENGTH_SHORT).show();
                loadingLayout.setVisibility(View.GONE);
            }
        }
    };

    private void focusCamera() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCamera.autoFocus(autoFocusCallback);
                focusCamera();
            }
        }, 2000);
    }

    public void takePicture(View view) {

//        //得到照相机的参数
//        Camera.Parameters parameters = mCamera.getParameters();
//        //图片的格式
//        parameters.setPictureFormat(ImageFormat.JPEG);
//        parameters.setPreviewSize(frameLayout.getWidth() / 2, frameLayout.getHeight() / 2);
//        //设置对焦模式，自动对焦
//        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        loadingLayout.setVisibility(View.VISIBLE);
//        mCamera.startPreview();//开始预览
        //对焦成功后，自动拍照  自动对焦。要放在startPreview后面，否则可能报自动对焦失败异常，还没预览就进行对焦是不行的。
        captureAfterFocus = true;
        handler.removeCallbacksAndMessages(null);
        mCamera.autoFocus(autoFocusCallback);
    }

    private void handleResult(boolean isSuccess, String path) {
        Intent intent = new Intent();
        intent.putExtra("isFront", isFront);
        intent.putExtra("isSuccess", isSuccess);
        intent.putExtra("path", path);
        setResult(RESULT_OK, intent);

        finish();
    }

    //获取照片中的接口回调
    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Log.d("zxcv","take picture success");
            ToastUtil.showToast(TakePictureActivity.this,"拍照获取焦点成功",0);
            finish();

        }
    };

    public void cancel(View view) {
        handleResult(false, "");
    }
}
