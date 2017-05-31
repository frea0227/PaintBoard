package com.swarchi.study.paintboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by ksk on 2017-05-28.
 */

public class GoodPaintBoardActivity extends AppCompatActivity {

    GoodPaintBoard board;

    Button colorBtn;
    Button penBtn;
    Button eraserBtn;
    Button undoBtn;

    int mColor = 0xff000000;
    int mSize = 2;
    int oldColor;
    int oldSize;

    boolean eraserSelected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout boardLayout = (LinearLayout) findViewById(R.id.boardLayout);
        colorBtn = (Button) findViewById(R.id.colorBtn);
        penBtn = (Button) findViewById(R.id.penBtn);
        eraserBtn = (Button) findViewById(R.id.eraser);
        undoBtn = (Button) findViewById(R.id.undoBtn);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        board = new GoodPaintBoard(this);
        board.setLayoutParams(params);
        board.setPadding(2, 2, 2, 2);

        boardLayout.addView(board);

        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ColorPaletteDialog.listener = new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        mColor = color;
                        board.updatePaintProperty(mColor, mSize);
                    }
                };

                Intent intent = new Intent(getApplicationContext(), ColorPaletteDialog.class);
                startActivity(intent);
            }
        });

        penBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PenPaletteDialog.listener = new OnPenSelectedListener() {
                    @Override
                    public void onPenSelected(int size) {
                        mSize = size;
                        board.updatePaintProperty(mColor, mSize);
                    }
                };

                Intent intent = new Intent(getApplicationContext(), PenPaletteDialog.class);
                startActivity(intent);
            }
        });

        eraserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eraserSelected = !eraserSelected;

                if (eraserSelected) {
                    colorBtn.setEnabled(false);
                    penBtn.setEnabled(false);
                    undoBtn.setEnabled(false);

                    colorBtn.invalidate();
                    penBtn.invalidate();
                    undoBtn.invalidate();

                    oldColor = mColor;
                    oldSize = mSize;

                    mColor = Color.WHITE;
                    mSize = 15;

                    board.updatePaintProperty(mColor, mSize);
                } else {
                    colorBtn.setEnabled(true);
                    penBtn.setEnabled(true);
                    undoBtn.setEnabled(true);

                    colorBtn.invalidate();
                    penBtn.invalidate();
                    undoBtn.invalidate();

                    mColor = oldColor;
                    mSize = oldSize;

                    board.updatePaintProperty(mColor, mSize);
                }

            }
        });

        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.undo();
            }
        });
    }

    public int getChosenColor() {
        return mColor;
    }

    public int getPenThickness() {
        return mSize;
    }
}
