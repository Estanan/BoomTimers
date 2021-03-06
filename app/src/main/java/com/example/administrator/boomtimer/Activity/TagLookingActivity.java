package com.example.administrator.boomtimer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.boomtimer.R;
import com.example.administrator.boomtimer.db.DB;
import com.example.administrator.boomtimer.model.Tag;
import com.example.administrator.boomtimer.util.SmallUtil;

/**
 * 选项活动查看页面
 */
public class TagLookingActivity extends AppCompatActivity {

    private TextView tagName;
    private AppCompatImageView tagIcon;
    private LinearLayout linearLayout;
    private Tag mTag;
    public DB mDB = DB.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_looking);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tagName = (TextView) findViewById(R.id.tag_name);
        tagIcon = (AppCompatImageView) findViewById(R.id.tag_icon);
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);

        mTag = (Tag) getIntent().getSerializableExtra("tag");
        init(mTag);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TagLookingActivity.this, TrendeActivity.class);
                intent.putExtra("tagId", mTag.getId());
                startActivity(intent);
//                查看条形图
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        //删除标签为？的activity和tag以及set,update setorder and tagorder
                        mDB.deleteAboutTag(mTag.getId());
                        finish();
                        break;
                    case R.id.update:
                        Intent intent = new Intent(TagLookingActivity.this, EditAddTagActivity.class);
                        intent.putExtra("tag", mTag);
                        startActivityForResult(intent, 1);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Tag newTag = (Tag) data.getSerializableExtra("new tag");
                init(newTag);
                MainActivity.updateTagList();
            }
        }
    }

    private void init(Tag tag) {
        tagName.setText(tag.getName());
        SmallUtil.changeIcon(tagIcon, tag);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_look_tag, menu);
        return true;
    }
}
