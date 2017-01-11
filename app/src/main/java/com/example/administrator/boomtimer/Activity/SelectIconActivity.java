package com.example.administrator.boomtimer.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.boomtimer.Adapter.IconListAdapter;
import com.example.administrator.boomtimer.R;
import com.tonicartos.superslim.LayoutManager;

/**
 * 选择图标界面
 */
public class SelectIconActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_icon);


        RecyclerView recyclerView =
                (RecyclerView) findViewById(R.id.recycler_select_icon);
        recyclerView.setLayoutManager(new LayoutManager(this));
        recyclerView.setAdapter(new IconListAdapter(this));
    }
}
