package com.swarchi.study.paintboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ksk on 2017-05-30.
 */

public class PaintBoardSurfaceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PaintBoardSurface board = new PaintBoardSurface(this);
        setContentView(board);
    }
}
