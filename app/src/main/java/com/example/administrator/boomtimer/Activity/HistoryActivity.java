package com.example.administrator.boomtimer.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.administrator.boomtimer.Adapter.HistoryListAdapter;
import com.example.administrator.boomtimer.Fragment.BaseFragment;
import com.example.administrator.boomtimer.R;

import de.halfbit.pinnedsection.PinnedSectionListView;

/**
 * Created by Administrator on 2016/8/22.
 */
public class HistoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_history);
        initViews();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_history;
    }

    private PinnedSectionListView psListView;
    public static HistoryListAdapter adapter;

    public void initViews() {
        psListView = (PinnedSectionListView) findViewById(R.id.pinned_section_list_view);
        psListView.setEmptyView(findViewById(R.id.empty_view));
        loadData();
    }

    public void loadData() {
        adapter = new HistoryListAdapter(this);
        psListView.setAdapter(adapter);
        Log.e("AddActivitiesFragment", "loadData");
//        MainActivity.setAdapter(adapter);
//        getActivity().setTitle("History");
    }

    @Override
    protected void onDestroy() {
        adapter = null;
        super.onDestroy();
    }

}
