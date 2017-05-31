package com.swarchi.study.paintboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

/**
 * Created by ksk on 2017-05-28.
 */

public class ColorPaletteDialog extends Activity {

    GridView grid;
    Button closeBtn;
    ColorDataAdapter adapter;
    public static OnColorSelectedListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);

        setTitle("색상 선택");

        grid = (GridView) findViewById(R.id.grid);
        closeBtn = (Button) findViewById(R.id.closeBtn);

        grid.setColumnWidth(14);
        grid.setBackgroundColor(Color.GRAY);
        grid.setVerticalSpacing(4);
        grid.setHorizontalSpacing(4);

        adapter = new ColorDataAdapter(this);
        grid.setAdapter(adapter);
        grid.setNumColumns(adapter.getNumColumns());

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}

class ColorDataAdapter extends BaseAdapter {

    Context mContext;
    public static final int[] colors = new int[] {
        0xff000000,0xff00007f,0xff0000ff,0xff007f00,0xff007f7f,0xff00ff00,0xff00ff7f,
        0xff00ffff,0xff7f007f,0xff7f00ff,0xff7f7f00,0xff7f7f7f,0xffff0000,0xffff007f,
        0xffff00ff,0xffff7f00,0xffff7f7f,0xffff7fff,0xffffff00,0xffffff7f,0xffffffff
    };

    int rowCount;
    int columnCount;

    public ColorDataAdapter(Context context) {
        super();

        mContext = context;

        rowCount = 3;
        columnCount = 7;
    }

    public int getNumColumns() {
        return columnCount;
    }

    @Override
    public int getCount() {
        return rowCount * columnCount;
    }

    @Override
    public Object getItem(int i) {
        return colors[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.d("ColorDataAdapter", "getView(" + i + ") called.");

        int rowIndex = i / columnCount;
        int columnIndex = i % columnCount;
        Log.d("ColorDataAdapter", "Index: " + rowIndex + ", " + columnIndex);

        GridView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        Button aItem = new Button(mContext);
        aItem.setText(" ");
        aItem.setLayoutParams(params);
        aItem.setPadding(4, 4, 4, 4);
        aItem.setBackgroundColor(colors[i]);
        aItem.setHeight(120);
        aItem.setTag(colors[i]);

        aItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ColorPaletteDialog.listener != null) {
                    ColorPaletteDialog.listener.onColorSelected(  (  (Integer) view.getTag()  )  );
                }

                (  (ColorPaletteDialog) mContext  ).finish();
            }
        });

        return aItem;
    }

}