package com.tribodys.ph7_android.event_handle;

import android.graphics.Matrix;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.tribodys.ph7_android.EventMainActivity;

/**
 * Created by qiyanpeng on 18-5-30.
 */

public class MyScaleGestureDetector extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    private float mPreScale = 1.0f;
    private float mCurScale = 1.0f;
    private Matrix mMatrix;
    private View mImage;
    private EventMainActivity mContainer;

    public MyScaleGestureDetector(EventMainActivity a, View img, float scale) {
        mMatrix = new Matrix();
        mImage = img;
        mContainer = a;
        mPreScale = scale;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        mCurScale = detector.getScaleFactor() * mPreScale;
        if (mCurScale > 5 || mCurScale < 0.1) {
            mPreScale = mCurScale;
            return true;
        }
        mMatrix.setScale(mCurScale, mCurScale, mImage.getWidth() /2, mImage.getHeight() /2);
        mContainer.setMatrix(mMatrix);
        mPreScale = mCurScale;
        return false;
    }
}
