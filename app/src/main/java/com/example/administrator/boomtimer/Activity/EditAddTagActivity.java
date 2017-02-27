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
import com.example.administrator.boomtimer.widgets.ColorPickerDialog;
import com.example.administrator.boomtimer.widgets.IconPickerDialog;
import com.larswerkman.holocolorpicker.ColorPicker;

/**
 * 添加活动
 */
public class EditAddTagActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText tagName;
    private Button selectColor;
    private AppCompatImageView selectIcon;
    //    private boolean newOne = false;//为true时，不必比较tag和newTag
//    private boolean change = false;//当newOne为false时，需比较是否改变了�?
    private Tag tag;
    private Tag newTag;
    private DB mDB;
    private Intent intent;
    private Toolbar toolbar;
    ColorPickerDialog colorPicker;
    IconPickerDialog iconPicker;
    private int colorId, iconId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add_tag);
        initView();
        initData();
    }

    private void initData() {
        mDB = DB.getInstance(this);
        newTag = new Tag();
        intent = getIntent();
        tag = (Tag) intent.getSerializableExtra("tag");
        //修改还是添加
        if (tag.getName().equals("")) {
            //添加新的
        } else {
            //修改已有
            tagName.setText(tag.getName());
        }
        SmallUtil.changeIcon(selectIcon, tag);
        selectColor.setBackgroundResource(tag.getColor());
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tagName = (EditText) findViewById(R.id.tag_name_edit);
        selectColor = (Button) findViewById(R.id.tag_color_select);
        selectIcon = (AppCompatImageView) findViewById(R.id.tag_icon_select);
        selectColor.setOnClickListener(this);
        selectIcon.setOnClickListener(this);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ok:
                        newTag.setName(tagName.getText().toString());
                        newTag.setColor(colorId);
                        newTag.setIcon(iconId);
                        if (!tag.equals(newTag)) {
                            mDB.addAboutTag(newTag);
                            backIntent(newTag);
                        }
                        finish();
                        break;
                    case R.id.cancel:
                        finish();
                        break;
                }
                return true;
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tag_icon_select:
                iconPicker = IconPickerDialog.getInstance(this);
                iconPicker.setListener(this, new IconPickerDialog.ICustomDialogEventListener() {
                    @Override
                    public void customDialogEvent(int icon) {
                        selectIcon.setImageResource(icon);
                        iconId = icon;
                    }
                });
                iconPicker.show();
                break;
            case R.id.tag_color_select:
                colorPicker = ColorPickerDialog.getInstance(this);
                colorPicker.setListener(this, new ColorPickerDialog.ICustomDialogEventListener() {
                    @Override
                    public void customDialogEvent(int color) {
                        selectColor.setBackgroundColor(color);
                        colorId = color;
                    }
                });
                colorPicker.show();
                break;
        }
    }
}
