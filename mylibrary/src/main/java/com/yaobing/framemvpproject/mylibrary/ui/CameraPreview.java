package com.yaobing.framemvpproject.mylibrary.ui;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private int mW, mH;

    private OnSurfaceCreatedListener onSurfaceCreatedListener;

    public CameraPreview(Context context, Camera camera, int w, int h) {
        super(context);
        //初始化Camera对象
//        mCamera = camera;
        //得到SurfaceHolder对象
        mHolder = getHolder();
        //添加回调，得到Surface的三个声明周期方法
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
//        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mW = w;
        mH = h;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            this.mCamera = Camera.open();
        } catch (Exception var3) {
            var3.printStackTrace();
            Log.i("FUCK ERROR", "Camera.open: ");
        }

        if (this.mCamera != null) {
            try {
                this.mCamera.enableShutterSound(false);
            } catch (Exception e) {
                Log.i("FUCK ERROR", "enableShutterSound: ");
            }
        }
        try {
            //得到照相机的参数
            Camera.Parameters parameters = mCamera.getParameters();
            //图片的格式
            parameters.setPictureFormat(ImageFormat.JPEG);
//            parameters.setPreviewSize(frameLayout.getWidth() / 2, frameLayout.getHeight() / 2);
            //设置对焦模式，自动对焦
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            mCamera.setPreviewDisplay(holder);  //SurfaceView

            List<Camera.Size> sizeList = parameters.getSupportedPictureSizes();
            Camera.Size selectedSize = sizeList.get(0);
            parameters.setPictureSize(Math.max(selectedSize.width, 1920), Math.max(selectedSize.height, 1080));
            mCamera.setParameters(parameters);
            mCamera.startPreview();//开始预览
            if (onSurfaceCreatedListener != null) {
                onSurfaceCreatedListener.onSurfaceCreated();
            }
        } catch (Exception e) {
//            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
            Log.i("FUCK ERROR", "getParameters " + e.getMessage());
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            try {
                mCamera.stopPreview();
                mCamera.setPreviewCallback(null);
                mCamera.setPreviewDisplay(null);
                mCamera.release();
                mCamera = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void autoFocus(Camera.AutoFocusCallback focusCallback) {
        if (null != mCamera) {
            mCamera.autoFocus(focusCallback);
        }
    }

    public void takePicture(Camera.PictureCallback callback) {
        if (null != mCamera) {
            mCamera.takePicture(null, null, callback);
        }
    }

    public void setOnSurfaceCreatedListener(OnSurfaceCreatedListener onSurfaceCreatedListener) {
        this.onSurfaceCreatedListener = onSurfaceCreatedListener;
    }

    public interface OnSurfaceCreatedListener {
        void onSurfaceCreated();
    }
}
