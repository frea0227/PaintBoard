package com.swarchi.study.paintboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_main);

        Button step01Button = (Button) findViewById(R.id.step01Button);
        step01Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PaintBoardActivity.class);
                startActivity(intent);
            }
        });

        Button step02Button = (Button) findViewById(R.id.step02Button);
        step02Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GoodPaintBoardActivity.class);
                startActivity(intent);
            }
        });

        Button step03Button = (Button) findViewById(R.id.step03Button);
        step03Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BestPaintBoardActivity.class);
                startActivity(intent);
            }
        });
    }
}
