package com.minilook.minilook.ui.search_keyword;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.room.keyword.KeywordDB;
import com.minilook.minilook.databinding.ActivitySearchKeywordBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.search_keyword.adapter.RecentKeywordAdapter;
import com.minilook.minilook.ui.search_keyword.chip.RecommendKeywordChip;
import com.minilook.minilook.ui.search_keyword.di.SearchKeywordArguments;

public class SearchKeywordActivity extends BaseActivity implements SearchKeywordPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchKeywordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @DimenRes int dp_8 = R.dimen.dp_8;

    private ActivitySearchKeywordBinding binding;
    private SearchKeywordPresenter presenter;

    private final RecentKeywordAdapter recentKeywordAdapter = new RecentKeywordAdapter();
    private final BaseAdapterDataView<String> recentKeywordAdapterView = recentKeywordAdapter;

    @Override protected View getBindingView() {
        binding = ActivitySearchKeywordBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new SearchKeywordPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private SearchKeywordArguments provideArguments() {
        return SearchKeywordArguments.builder()
            .view(this)
            .recentKeywordAdapter(recentKeywordAdapter)
            .keywordDB(getKeywordDB())
            .build();
    }

    private KeywordDB getKeywordDB() {
        return Room.databaseBuilder(this, KeywordDB.class, getString(R.string.app_name))
            .allowMainThreadQueries()
            .build();
    }

    @Override public void setupClickAction() {
        binding.imgClose.setOnClickListener(view -> finish());
        binding.txtRecentClear.setOnClickListener(view -> presenter.onRecentClearClick());
    }

    @Override public void setupRecentKeywordRecyclerView() {
        binding.rcvRecent.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rcvRecent.setAdapter(recentKeywordAdapter);
        DividerDecoration.builder(this)
            .size(resources.getDimen(dp_8))
            .asSpace()
            .build()
            .addTo(binding.rcvRecent);
    }

    @Override public void recentKeywordRefresh() {
        recentKeywordAdapterView.refresh();
    }

    @Override public void setupEditText() {
        binding.editSearch.setOnEditorActionListener((v, actionId, event) -> {
            if ((actionId & EditorInfo.IME_MASK_ACTION) == EditorInfo.IME_ACTION_SEARCH) {
                presenter.onSearchEnterClick(binding.editSearch.getText().toString().trim());
                return true;
            }
            return false;
        });
    }

    @Override public void setSearchKeyword(String keyword) {
        binding.editSearch.setText(keyword);
        binding.editSearch.setSelection(keyword.length());
    }

    @Override public void showRecentPanel() {
        binding.layoutRecentPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideRecentPanel() {
        binding.layoutRecentPanel.setVisibility(View.GONE);
    }

    @Override public void addRecommendKeyword(String keyword) {
        RecommendKeywordChip chipView = RecommendKeywordChip.builder()
            .context(this)
            .keyword(keyword)
            .listener(presenter::onRecommendKeywordClick)
            .build();
        binding.layoutRecommendKeyword.addView(chipView);
    }

    @Override public void showRecommendPanel() {
        binding.layoutRecommendPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideRecommendPanel() {
        binding.layoutRecommendPanel.setVisibility(View.GONE);
    }

    @Override public void navigateToBridge(String keyword) {
        //ProductBridgeActivity.start(this);
    }
}
