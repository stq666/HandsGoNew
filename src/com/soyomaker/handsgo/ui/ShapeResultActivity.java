package com.soyomaker.handsgo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.soyomaker.handsgo.R;
import com.soyomaker.handsgo.adapter.SearchResultListViewAdapter;
import com.soyomaker.handsgo.model.SearchResultList;

public class ShapeResultActivity extends BaseActivity {

    public static final String EXTRA_SEARCH_RESULT = "extra_search_result";

    private SearchResultList mSearchResultList;
    private ListView mSearchResultListView;
    private SearchResultListViewAdapter mSearchResultListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_result);

        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        mSearchResultList = (SearchResultList) intent.getSerializableExtra(EXTRA_SEARCH_RESULT);
    }

    private void initView() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.title_search_result);

        mSearchResultListViewAdapter = new SearchResultListViewAdapter(this);
        if (mSearchResultList != null) {
            mSearchResultListViewAdapter.setSearchResults(mSearchResultList.getSearchResults());
        }
        mSearchResultListView = (ListView) findViewById(R.id.listview_search_result);
        mSearchResultListView.setAdapter(mSearchResultListViewAdapter);
    }

    @Override
    public String getPageName() {
        return "棋型搜索结果界面";
    }
}
