package com.minilook.minilook.ui.search_keyword;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.minilook.minilook.databinding.ActivitySearchKeywordBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.search_keyword.di.SearchKeywordArguments;

public class SearchKeywordActivity extends BaseActivity implements SearchKeywordPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchKeywordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private ActivitySearchKeywordBinding binding;
    private SearchKeywordPresenter presenter;

    @Override protected View getBindingView() {
        binding = ActivitySearchKeywordBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        setupRecentKeywordDB();
        presenter = new SearchKeywordPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private SearchKeywordArguments provideArguments() {
        return SearchKeywordArguments.builder()
            .view(this)
            .build();
    }

    private void setupRecentKeywordDB() {
    }

    @Override
    public void setupEditText() {
        //searchEditText.setOnEditorActionListener((v, actionId, event) -> {
        //    if ((actionId & EditorInfo.IME_MASK_ACTION) == EditorInfo.IME_ACTION_SEARCH) {
        //        presenter.onSearchEnterClick(searchEditText.getText().toString());
        //        return true;
        //    }
        //    return false;
        //});
    }

    @Override public void showRecentPanel() {
        //recentPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideRecentPanel() {
        //recentPanel.setVisibility(View.GONE);
    }

    @Override public void setupPopularTitle(String text) {
        //popularTitleTextView.setText(text);
    }

    @Override public void setupBrandTitle(String text) {
        //brandTitleTextView.setText(text);
    }

    @Override public void navigateToBridge(String keyword) {
        //ProductBridgeActivity.start(this);
    }

    @Override public void addKeywordView(String keyword) {
        //KeywordView keywordView = new KeywordView(this, KeywordView.TYPE_SEARCH_RECENT);
        //keywordView.setKeyword(keyword);
        //keywordView.setOnClickListener(this);
        //recentItemPanel.addView(keywordView);
    }

    @Override public void removeAllKeywordView() {
        //recentItemPanel.removeAllViews();
    }

    @Override public void removeOldKeywordView() {
        //recentItemPanel.removeViewAt(0);
    }

    //@OnClick(R.id.txt_recent_clear)
    //void onRemoveAllClick() {
    //}

    //// Dialog OnButtonClickListener
    //@Override public void onPositiveClick() {
    //    presenter.removeAllClick();
    //}
    //
    //@Override public void onNegativeClick() {
    //
    //}
}
