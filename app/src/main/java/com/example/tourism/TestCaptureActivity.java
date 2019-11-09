package com.example.tourism;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.View;

import com.google.zxing.Result;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.camera.CameraManager;
import com.yzq.zxinglibrary.view.ViewfinderView;

public class TestCaptureActivity extends CaptureActivity {

    public TestCaptureActivity() {
        super();
    }

    @Override
    public ViewfinderView getViewfinderView() {
        return super.getViewfinderView();
    }

    @Override
    public Handler getHandler() {
        return super.getHandler();
    }

    @Override
    public CameraManager getCameraManager() {
        return super.getCameraManager();
    }

    @Override
    public void drawViewfinder() {
        super.drawViewfinder();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void switchFlashImg(int flashState) {
        super.switchFlashImg(flashState);
    }

    @Override
    public void handleDecode(Result rawResult) {
        Intent intent = new Intent();
        intent.putExtra("codedContent", rawResult.getText());
        setResult(-1, intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        super.surfaceChanged(holder, format, width, height);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
