package com.minilook.minilook.ui.scrapbook;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.annotation.ArrayRes;
import androidx.annotation.DimenRes;
import com.google.android.material.tabs.TabLayout;
import com.minilook.minilook.R;
import com.minilook.minilook.databinding.ActivityScrapbookBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.widget.TabView;
import com.minilook.minilook.ui.scrapbook.adapter.ScrapbookPagerAdapter;
import com.minilook.minilook.ui.scrapbook.di.ScrapbookArguments;
import java.util.Objects;

public class ScrapbookActivity extends BaseActivity implements ScrapbookPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, ScrapbookActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @ArrayRes int tab_scrapbook = R.array.tab_scrapbook;

    @DimenRes int dp_48 = R.dimen.dp_48;

    private ActivityScrapbookBinding binding;
    private ScrapbookPresenter presenter;
    private ScrapbookPagerAdapter adapter;

    @Override protected View getBindingView() {
        binding = ActivityScrapbookBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new ScrapbookPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ScrapbookArguments provideArguments() {
        return ScrapbookArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupTabLayout() {
        for (String tabName : resources.getStringArray(tab_scrapbook)) {
            TabView tabView = TabView.builder()
                .context(this)
                .name(tabName)
                .width(dp_48)
                .build();

            TabLayout.Tab tab = binding.layoutTabPanel.newTab();
            tab.setCustomView(tabView);
            binding.layoutTabPanel.addTab(tab);
        }

        binding.layoutTabPanel.addOnTabSelectedListener(tabSelectedListener);
        getTabView(0).setupSelected();
    }

    @Override public void setupViewPager() {
        adapter = new ScrapbookPagerAdapter(getSupportFragmentManager(), getLifecycle());
        binding.vpScrapbook.setAdapter(adapter);
        binding.vpScrapbook.setOffscreenPageLimit(1);
        binding.vpScrapbook.setUserInputEnabled(false);
        binding.vpScrapbook.setCurrentItem(0);
    }

    @Override public void setupCurrentPage(int position) {
        binding.vpScrapbook.setCurrentItem(position);
    }

    @Override public void clear() {
        binding.layoutTabPanel.removeAllTabs();
        binding.layoutTabPanel.removeOnTabSelectedListener(tabSelectedListener);
        binding.vpScrapbook.setAdapter(null);
    }

    private final TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override public void onTabSelected(TabLayout.Tab tab) {
            presenter.onTabClick(tab.getPosition());
            getTabView(tab).setupSelected();
        }

        @Override public void onTabUnselected(TabLayout.Tab tab) {
            getTabView(tab).setupUnselected();
        }

        @Override public void onTabReselected(TabLayout.Tab tab) {
        }
    };

    public TabView getTabView(TabLayout.Tab tab) {
        return (TabView) tab.getCustomView();
    }

    public TabView getTabView(int position) {
        return (TabView) Objects.requireNonNull(binding.layoutTabPanel.getTabAt(position)).getCustomView();
    }
}
