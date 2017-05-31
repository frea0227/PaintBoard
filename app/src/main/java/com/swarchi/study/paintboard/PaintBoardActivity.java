package com.swarchi.study.paintboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ksk on 2017-05-27.
 */

public class PaintBoardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PaintBoard board = new PaintBoard(this);
        setContentView(board);
    }
}
