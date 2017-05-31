package com.swarchi.study.paintboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.io.OutputStream;

/**
 * Created by ksk on 2017-05-30.
 */

public class PaintBoardSurface extends SurfaceView implements SurfaceHolder.Callback {

    Canvas mCanvas;
    Bitmap mBitmap;
    final Paint mPaint;

    int lastX;
    int lastY;
    SurfaceHolder mHolder;

    public PaintBoardSurface(Context context) {
        super(context);

        mHolder = getHolder();
        mHolder.addCallback(this);

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(30);

        lastX = -1;
        lastY = -1;

        Log.i("PaintBoardSurface", "initialized.");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int X = (int) event.getX();
        int Y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_UP:
                lastX = -1;
                lastY = -1;

                break;

            case MotionEvent.ACTION_MOVE:
                if (lastX != -1) {
                    mCanvas.drawLine(lastX, lastY, X, Y, mPaint);
                }

                lastX = X;
                lastY = Y;

                break;

            case MotionEvent.ACTION_DOWN:
                if (lastX != -1) {
                    if (X != lastX || Y != lastY) {
                        mCanvas.drawLine(lastX, lastY, X, Y, mPaint);
                    }
                }

                lastX = X;
                lastY = Y;

                break;
        }

        Log.d("PaintBoard-Surface", "repaintCanvas()");

        draw();

        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        int w = getWidth();
        int h = getHeight();

        Bitmap img = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(img);
        canvas.drawColor(Color.YELLOW);

        mBitmap = img;
        mCanvas = canvas;

        draw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    private void draw() {
        Canvas _canvas = null;
        try {
            _canvas = mHolder.lockCanvas(null);
            super.draw(_canvas);
            _canvas.drawBitmap(mBitmap, 0, 0, null);
        } finally {
            if (_canvas != null) {
                mHolder.unlockCanvasAndPost(_canvas);
            }
        }
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
