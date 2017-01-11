package com.example.administrator.boomtimer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.boomtimer.R;
import com.example.administrator.boomtimer.db.DB;
import com.example.administrator.boomtimer.model.Tag;
import com.example.administrator.boomtimer.util.SmallUtil;

/**
 * 添加活动
 */
public class EditAddTagActivity extends AppCompatActivity {

    private EditText tagName;
    private Button selectColor;
    private AppCompatImageView selectIcon;
    //    private boolean newOne = false;//为true时，不必比较tag和newTag
//    private boolean change = false;//当newOne为false时，需比较是否改变了�?
    private Tag tag;
    private Tag newTag;
    private DB mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add_tag);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tagName = (EditText) findViewById(R.id.tag_name_edit);
        selectColor = (Button) findViewById(R.id.tag_color_select);
        selectIcon = (AppCompatImageView) findViewById(R.id.tag_icon_select);
        mDB = DB.getInstance(this);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ok:
//                        newTag = new Tag(tagName.getText(),
//                                ,
//                                );
                        if (!tag.equals(newTag)) {
                            mDB.addAboutTag(newTag);
                            backIntent(newTag);
                        }
//                        //MainActivity.updateAdapter(1);
                        finish();
                        break;
                    case R.id.cancel:
                        finish();
                        break;
                }
                return true;
            }
        });
        Intent intent = getIntent();
        tag = (Tag) intent.getSerializableExtra("tag");
        if (tag.getName().equals("")) {
        } else {
            tagName.setText(tag.getName());
        }
        SmallUtil.changeColor(selectIcon, tag);
        selectColor.setBackgroundResource(tag.getColor());

        selectColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        selectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAddTagActivity.this, SelectIconActivity.class);
                startActivity(intent);
            }
        });
    }

    private void backIntent(Tag tag) {
        Intent intent = new Intent();
        intent.putExtra("new tag", tag);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_tag, menu);
        return true;
    }
}
