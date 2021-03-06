package com.example.administrator.boomtimer.Adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v13.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.example.administrator.boomtimer.Fragment.AddActivitiesFragment;
import com.example.administrator.boomtimer.Fragment.HistoryFragment;
import com.example.administrator.boomtimer.Fragment.MoreFragment;
import com.example.administrator.boomtimer.Fragment.AddTagFragment;
import com.example.administrator.boomtimer.Fragment.NoteTimeFragment;
import com.example.administrator.boomtimer.R;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    int nNumOfTabs;
    public MyFragmentPagerAdapter(FragmentManager fm, int nNumOfTabs, Context context)
    {
        super(fm);
        this.nNumOfTabs=nNumOfTabs;
        this.context = context;
    }
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                Fragment tab1 = new AddActivitiesFragment();
                return tab1;
            case 1:
                Fragment tab2 = new NoteTimeFragment();
                return tab2;
            case 2:
                Fragment tab3 = new HistoryFragment();
                return tab3;
            case 3:
                Fragment tab4 = new AddTagFragment();
                return tab4;
            case 4:
                Fragment tab5 = new MoreFragment();
                return tab5;
        }
        return null;
    }

    private int[] imageResId = {
            R.drawable.ic_timer_white_24dp,
            R.drawable.ic_import_contacts_white_24dp,
            R.drawable.ic_import_contacts_white_24dp,
            R.drawable.ic_storage_white_24dp,
            R.drawable.ic_more_horiz_white_24dp
    };
    @Override
    public CharSequence getPageTitle(int position) {
        Drawable image = context.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    @Override
    public int getCount() {
        return nNumOfTabs;
    }

}
