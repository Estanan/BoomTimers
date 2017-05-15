package com.example.administrator.boomtimer.Fragment;

import android.util.Log;
import android.view.View;

import com.example.administrator.boomtimer.Adapter.HistoryListAdapter;
import com.example.administrator.boomtimer.R;

import de.halfbit.pinnedsection.PinnedSectionListView;

/**
 * Created by Administrator on 2016/8/22.
 */
public class HistoryFragment extends BaseFragment {

    private PinnedSectionListView psListView;
    public static HistoryListAdapter adapter;
    @Override
    public void initViews(View view) {
        psListView = (PinnedSectionListView) view.findViewById(R.id.pinned_section_list_view);
        psListView.setEmptyView(view.findViewById(R.id.empty_view));
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_history;
    }

    @Override
    public void loadData() {
        adapter = new HistoryListAdapter(getActivity());
        psListView.setAdapter(adapter);
        Log.e("AddActivitiesFragment", "loadData");
//        MainActivity.setAdapter(adapter);
//        getActivity().setTitle("History");
    }

    @Override
    public void onDestroyView() {
        adapter = null;
        super.onDestroyView();
    }

}
