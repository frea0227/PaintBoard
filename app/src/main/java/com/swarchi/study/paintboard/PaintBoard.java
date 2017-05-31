package com.swarchi.study.paintboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.OutputStream;

/**
 * Created by ksk on 2017-05-27.
 */

public class PaintBoard extends View {
    Canvas mCanvas;
    Bitmap mBitmap;
    final Paint mPaint;

    int lastX;
    int lastY;

    public PaintBoard(Context context) {
        super(context);

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);

        lastX = -1;
        lastY = -1;

        Log.i("PaintBoard", "initialized.");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d("DEBUG", "onSizeChanged() called.");

        Bitmap img = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(img);
        canvas.drawColor(Color.WHITE);

        mBitmap = img;
        mCanvas = canvas;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("DEBUG", "onDraw() called.");

        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("DEBUG", "onTouchEvent() called.");

        int action = event.getAction();
        int X = (int) event.getX();
        int Y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (lastX != -1) {
                    if (X != lastX || Y != lastY) {
                        mCanvas.drawLine(lastX, lastY, X, Y, mPaint);
                    }
                }

                lastX = X;
                lastY = Y;

                break;

            case MotionEvent.ACTION_MOVE:
                if (lastX != -1) {
                    mCanvas.drawLine(lastX, lastY, X, Y, mPaint);
                }

                lastX = X;
                lastY = Y;

                break;

            case MotionEvent.ACTION_UP:
                lastX = -1;
                lastY = -1;

                break;
        }

        invalidate();

        return true;
    }

    public boolean Save(OutputStream outstream) {
        try {
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
            invalidate();

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
