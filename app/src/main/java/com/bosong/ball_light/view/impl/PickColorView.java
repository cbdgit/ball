package com.bosong.ball_light.view.impl;

import com.bosong.ball_light.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by mike on 1/28/16.
 */
public class PickColorView extends View {

    //private int mImageViewId;
    private int pixelColor;

    public float currentX = 0;
    public float currentY = 0;


    public PickColorView(Context mContext){
        super(mContext);
        //this.mImageViewId = mImageViewId;
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.color_circle);
        int height = src.getHeight();
        int width = src.getWidth();
        pixelColor = src.getPixel((int)currentX, (int)currentY);
        Paint paint = new Paint();
        paint.setColor(pixelColor);
        //paint.setColor(Color.RED);
        canvas.drawCircle(currentX, currentY, 30, paint);
    }

    /*
    public void getPixColor() {
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.color_circle);
        int A, R, G, B;
        int pixelColor;
        int height = src.getHeight();
        int width = src.getWidth();


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelColor = src.getPixel(x, y);
                A = Color.alpha(pixelColor);
                R = Color.red(pixelColor);
                G = Color.green(pixelColor);
                B = Color.blue(pixelColor);
            }
        }
    }
    */
}
