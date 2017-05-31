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
import java.util.Stack;

/**
 * Created by ksk on 2017-05-28.
 */

public class GoodPaintBoard extends View {

    Stack<Bitmap> undos = new Stack<>();
    public static int maxUndos = 10;
    public boolean changed = false;

    Canvas mCanvas;
    Bitmap mBitmap;
    final Paint mPaint;

    int lastX;
    int lastY;

    public GoodPaintBoard(Context context) {
        super(context);

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(2);

        lastX = -1;
        lastY = -1;

        Log.i("GoodPaintBoard", "initialized.");
    }

    public void clearUndo() {
        while (true) {
            Bitmap prev = undos.pop();
            if (prev == null) {
                return;
            }

            prev.recycle();
        }
    }

    public void saveUndo() {
        if (mBitmap == null) return;

        while (undos.size() >= maxUndos) {
            Bitmap bitmap = undos.get(undos.size() - 1);
            bitmap.recycle();
            undos.remove(bitmap);
        }

        Bitmap img = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(img);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        undos.push(img);

        Log.i("GoodPaintBoard", "saveUndo() called.");
    }

    public void undo() {
        Bitmap bitmap = null;

        try {
            bitmap = undos.pop();
        } catch(Exception e) {
            Log.e("GoodPaintBoard", "Exception : " + e.getMessage());
        }

        if (bitmap != null) {
            drawBackground(mCanvas);
            mCanvas.drawBitmap(bitmap, 0, 0, mPaint);
            invalidate();

            bitmap.recycle();
        }

        Log.i("GoodPaintBoard", "undo() called.");
    }

    public void drawBackground(Canvas canvas) {
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
        }
    }

    public void updatePaintProperty(int color, int size) {
        mPaint.setColor(color);
        mPaint.setStrokeWidth(size);
    }

    public void newImage(int width, int height) {
        Bitmap img = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(img);

        mBitmap = img;
        mCanvas = canvas;

        drawBackground(mCanvas);

        changed = false;
        invalidate();
    }

    public void setImage(Bitmap newImage) {
        changed = false;

        setImageSize(newImage.getWidth(), newImage.getHeight(), newImage);
        invalidate();
    }

    public void setImageSize(int width, int height, Bitmap newImage) {
        if (mBitmap != null) {
            if (width < mBitmap.getWidth()) width = mBitmap.getWidth();
            if (height < mBitmap.getHeight()) height = mBitmap.getHeight();
        }

        if (width < 1 || height < 1) return;

        Bitmap img = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        drawBackground(canvas);

        if (newImage != null) {
            canvas.setBitmap(newImage);
        }

        if (mBitmap != null) {
            mBitmap.recycle();
            mCanvas.restore();
        }

        mBitmap = img;
        mCanvas = canvas;

        clearUndo();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w > 0 && h > 0) {
            newImage(w, h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int X = (int) event.getX();
        int Y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_UP:
                changed = true;

                lastX = -1;
                lastY = -1;

                break;

            case MotionEvent.ACTION_DOWN:
                saveUndo();

                if (lastX != -1) {
                    if (X != -1 || Y != -1) {
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
