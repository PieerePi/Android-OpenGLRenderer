package com.phuket.tour.opengl.renderer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class PngPreviewActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private ConstraintLayout preview_parent_layout;

    private String picPath = "1.png";

    private PngPreviewController pngPreviewController;
    private Callback previewCallback = new Callback() {

        public void surfaceCreated(SurfaceHolder holder) {
            pngPreviewController = new PngPreviewController();
            String lpicPath = getApplicationContext().getFilesDir().getAbsolutePath() + File.separator + picPath;
            pngPreviewController.init(lpicPath);
            pngPreviewController.setSurface(holder.getSurface());
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            pngPreviewController.resetSize(width, height);
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_png_preview);
        findView();
        CopyAssets(getApplicationContext(), picPath,
                getApplicationContext().getFilesDir().getAbsolutePath(), picPath);
    }

    private void findView() {
        preview_parent_layout = (ConstraintLayout) findViewById(R.id.preview_parent_layout);
        surfaceView = new SurfaceView(PngPreviewActivity.this);
        SurfaceHolder mSurfaceHolder = surfaceView.getHolder();
        mSurfaceHolder.addCallback(previewCallback);
//        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        preview_parent_layout.addView(surfaceView, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            surfaceView.getLayoutParams().width = getWindowManager().getCurrentWindowMetrics().getBounds().width();
            surfaceView.getLayoutParams().height = getWindowManager().getCurrentWindowMetrics().getBounds().height();
        } else {
            surfaceView.getLayoutParams().width = getWindowManager().getDefaultDisplay().getWidth();
            surfaceView.getLayoutParams().height = getWindowManager().getDefaultDisplay().getWidth();
        }
    }

    /**
     * On stop.
     */
    protected void onStop() {
        super.onStop();
        // Free the native renderer
        stopPlay();
    }

    protected void stopPlay() {
        Log.i("problem", "playerController.stop()...");
        if (null != pngPreviewController) {
            pngPreviewController.stop();
            pngPreviewController = null;
        }
    }

    /**
     　　*
     　　* @param myContext
     　　* @param ASSETS_NAME 要复制的文件名
     　　* @param savePath 要保存的路径
     　　* @param saveName 复制后的文件名
     　　*/
    public static void CopyAssets(Context myContext, String ASSETS_NAME,
                                  String savePath, String saveName) {
        String filename = savePath + File.separator + saveName;
        File dir = new File(savePath);
        // 如果目录不中存在，创建这个目录
        if (!dir.exists())
            dir.mkdir();
        try {
            if (!(new File(filename)).exists()) {
                InputStream is = myContext.getResources().getAssets()
                        .open(ASSETS_NAME);
                FileOutputStream fos = new FileOutputStream(filename);
                byte[] buffer = new byte[7168];
                int count;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}