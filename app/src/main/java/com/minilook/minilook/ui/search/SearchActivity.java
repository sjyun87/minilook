package com.minilook.minilook.ui.search;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.minilook.minilook.R;
import com.minilook.minilook.data.room.recent_keyword.RecentKeywordDB;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.manager.DialogManager;
import com.minilook.minilook.ui.base.widget.KeywordView;
import com.minilook.minilook.ui.bridge.BridgeActivity;
import com.minilook.minilook.ui.search.di.SearchArguments;
import com.nex3z.flowlayout.FlowLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements SearchPresenter.View,
    KeywordView.OnClickListener, DialogManager.OnButtonClickListener {

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.edit_search) EditText searchEditText;
    @BindView(R.id.layout_recent_panel) ConstraintLayout recentPanel;
    @BindView(R.id.layout_recent_item_panel) FlowLayout recentItemPanel;
    @BindView(R.id.txt_popular_title) TextView popularTitleTextView;
    @BindView(R.id.layout_popular_item_panel) FlowLayout popularItemPanel;
    @BindView(R.id.txt_brand_title) TextView brandTitleTextView;
    @BindView(R.id.rcv_brand) RecyclerView brandRecyclerView;

    private SearchPresenter presenter;
    private RecentKeywordDB recentKeywordDB;

    @Override protected int getLayoutID() {
        return R.layout.activity_search;
    }

    @Override protected void createPresenter() {
        setupRecentKeywordDB();
        presenter = new SearchPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private SearchArguments provideArguments() {
        return SearchArguments.builder()
            .view(this)
            .recentKeywordDB(recentKeywordDB)
            .build();
    }

    private void setupRecentKeywordDB() {
        recentKeywordDB = Room.databaseBuilder(this, RecentKeywordDB.class, getString(R.string.app_name))
            .allowMainThreadQueries()
            .build();
    }

    @Override
    public void setupEditText() {
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if ((actionId & EditorInfo.IME_MASK_ACTION) == EditorInfo.IME_ACTION_SEARCH) {
                presenter.onSearchEnterClick(searchEditText.getText().toString());
                return true;
            }
            return false;
        });
    }

    @Override public void showRecentPanel() {
        recentPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideRecentPanel() {
        recentPanel.setVisibility(View.GONE);
    }

    @Override public void setupPopularTitle(String text) {
        popularTitleTextView.setText(text);
    }

    @Override public void setupBrandTitle(String text) {
        brandTitleTextView.setText(text);
    }

    @Override public void navigateToBridge(String keyword) {
        BridgeActivity.start(this);
    }

    @Override public void addKeywordView(String keyword) {
        KeywordView keywordView = new KeywordView(this, KeywordView.TYPE_SEARCH_RECENT);
        keywordView.setKeyword(keyword);
        keywordView.setOnClickListener(this);
        recentItemPanel.addView(keywordView);
    }

    @Override public void removeAllKeywordView() {
        recentItemPanel.removeAllViews();
    }

    @Override public void removeOldKeywordView() {
        recentItemPanel.removeViewAt(0);
    }

    @OnClick(R.id.txt_recent_clear)
    void onRemoveAllClick() {
        DialogManager.showRemoveAllDialog(this, this);
    }

    // KeywordView OnClickListener
    @Override public void onKeywordClick(String keyword) {
        presenter.onKeywordClick(keyword);
    }

    @Override public void onDeleteClick(View view, String keyword) {
        presenter.onDeleteClick(keyword);
        recentItemPanel.removeView(view);
        if (recentItemPanel.getChildCount() == 0) hideRecentPanel();
    }

    // Dialog OnButtonClickListener
    @Override public void onPositiveClick() {
        presenter.removeAllClick();
    }

    @Override public void onNegativeClick() {

    }
}
