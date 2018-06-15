package com.tribodys.ph7_android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.tribodys.ph7_android.R;
import com.tribodys.ph7_android.event_handle.MyScaleGestureDetector;

public class EventMainActivity extends Activity {

    private ScaleGestureDetector mDetector;
    private ImageView mImgView;
    private int mTranslateX = 0;
    private int mTranslateY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);
        mImgView = (ImageView) findViewById(R.id.iv);
        mDetector = new ScaleGestureDetector(this, new MyScaleGestureDetector(this, mImgView));

        Bitmap sourceBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.test);

        DisplayMetrics displayMetrics=new DisplayMetrics();
        WindowManager windowManager= (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int[] screenSize=new int[2];
        screenSize[0]=displayMetrics.widthPixels;
        screenSize[1]=displayMetrics.heightPixels;
        mTranslateX = screenSize[0]/2-sourceBitmap.getWidth()/2;//使图片显示在中心
        mTranslateY = sourceBitmap.getHeight()/2-screenSize[1]/2;

        mImgView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mImgView.setImageBitmap(sourceBitmap);

        mImgView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mDetector.onTouchEvent(motionEvent);
            }
        });
    }

    public void setMatrix(Matrix matrix) {
        matrix.preTranslate(mTranslateX, mTranslateY);
        mImgView.setImageMatrix(matrix);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
