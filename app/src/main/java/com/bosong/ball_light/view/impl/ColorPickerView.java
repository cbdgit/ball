package com.bosong.ball_light.view.impl;

import com.bosong.ball_light.R;
import com.bosong.ball_light.presenter.fragment.UIColorFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mike on 1/28/16.
 */

public class ColorPickerView extends View   {

    private Context mContext;
    private Paint mRightPaint;            //画笔
    private int mHeight;                  //view高
    private int mWidth;                   //view宽
    private int[] mRightColors;
    private int LEFT_WIDTH;
    private Bitmap mLeftBitmap;
    private Paint mBitmapPaint;
    private Paint mColorPaint;
    private PointF mLeftSelectPoint;
    private OnColorChangedListener mChangedListener;
    private boolean mLeftMove = false;
    private float mLeftBitmapRadius;
    private Bitmap mGradualChangeBitmap;
    private Bitmap bitmapTemp;
    private int mCallBackColor = Integer.MAX_VALUE;
    int newWidgth;
    int newHeigh;
    public static String hexColor="";
    public static int ColorText=0;

    public ColorPickerView(Context context) {
        this(context, null);
    }

    public ColorPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }


    public void setOnColorChangedListenner(OnColorChangedListener listener) {

        mChangedListener = listener;
        mChangedListener.onColorChanged(ColorText);
    }

    //初始化资源与画笔
    private void init() {
        bitmapTemp = BitmapFactory.decodeResource(getResources(), R.drawable.color_circle);
        mBitmapPaint = new Paint();


        mLeftBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.pick_color);
        mLeftBitmapRadius = mLeftBitmap.getWidth() / 2;
        mLeftSelectPoint = new PointF(200, 200);
        newWidgth = BitmapFactory.decodeResource(getResources(), R.mipmap.pick_color).getWidth();
        newHeigh = BitmapFactory.decodeResource(getResources(), R.mipmap.pick_color).getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(getGradual(), null, new
                Rect(0, 0, LEFT_WIDTH, mHeight), mBitmapPaint);
        canvas.drawBitmap(mLeftBitmap, mLeftSelectPoint.x - mLeftBitmapRadius,
                mLeftSelectPoint.y - mLeftBitmapRadius, mBitmapPaint);
        mColorPaint = new Paint();
        mColorPaint.setColor(ColorText);
        canvas.drawCircle(300, 300, 80, mColorPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = width;
        } else {
            mWidth = newHeigh;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = height;
        } else {
            mHeight = newHeigh;
        }
        LEFT_WIDTH = mWidth;
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mLeftMove = true;
                proofLeft(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                ColorText=getLeftColor(x, y);
                mLeftMove = false;
                invalidate();
                UIColorFragment.flagOfColorChange=true;
        }
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mGradualChangeBitmap != null && mGradualChangeBitmap.isRecycled() == false) {
            mGradualChangeBitmap.recycle();
        }
        if (mLeftBitmap != null && mLeftBitmap.isRecycled() == false) {
            mLeftBitmap.recycle();
        }
        super.onDetachedFromWindow();
    }

    private Bitmap getGradual() {
        if (mGradualChangeBitmap == null) {
            Paint leftPaint = new Paint();
            leftPaint.setStrokeWidth(1);
            mGradualChangeBitmap = Bitmap.createBitmap(LEFT_WIDTH, mHeight, Config.RGB_565);
            mGradualChangeBitmap.eraseColor(Color.WHITE);
            Canvas canvas = new Canvas(mGradualChangeBitmap);
            canvas.drawBitmap( bitmapTemp, null , new Rect(0, 0, LEFT_WIDTH , mHeight ), mBitmapPaint);
        }
        return mGradualChangeBitmap;
    }
/*
    private void proofLeft(float x, float y) {
        if (x < 0) {
            mLeftSelectPoint.x = 0;
        } else if (x > (LEFT_WIDTH)) {
            mLeftSelectPoint.x = LEFT_WIDTH;
        } else {
            mLeftSelectPoint.x = x;
        }
        if (y < 0) {
            mLeftSelectPoint.y = 0;
        } else if (y > (mHeight - 0)) {
            mLeftSelectPoint.y = mHeight - 0;
        } else {
            mLeftSelectPoint.y = y;
        }
    }
*/

    private void proofLeft(float x, float y) {
        if (x < 0) {
            mLeftSelectPoint.x = 0;
        } else if (x > (LEFT_WIDTH)) {
            mLeftSelectPoint.x = LEFT_WIDTH;
        } else {
            mLeftSelectPoint.x = x;
        }
        if (y < 0) {
            mLeftSelectPoint.y = 0;
        } else if (y > (mHeight - 0)) {
            mLeftSelectPoint.y = mHeight - 0;
        } else {
            mLeftSelectPoint.y = y;
        }
    }

    private int getLeftColor(float x, float y) {
        Bitmap temp = getGradual();

        int intX = (int) x;
        int intY = (int) y;
        if(intX<0)intX=0;
        if(intY<0)intY=0;
        if (intX >= temp.getWidth()) {
            intX = temp.getWidth() - 1;
        }
        if (intY >= temp.getHeight()) {
            intY = temp.getHeight() - 1;
        }
        return temp.getPixel(intX, intY);
    }

    public interface OnColorChangedListener {
        void onColorChanged(int color);
    }

}
