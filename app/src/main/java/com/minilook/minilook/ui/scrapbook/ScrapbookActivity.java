package com.minilook.minilook.ui.scrapbook;

import android.content.Context;
import android.content.Intent;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindArray;
import butterknife.BindDimen;
import butterknife.BindView;
import com.google.android.material.tabs.TabLayout;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.base.widget.TabView;
import com.minilook.minilook.ui.scrapbook.adapter.ScrapbookPagerAdapter;
import com.minilook.minilook.ui.scrapbook.di.ScrapbookArguments;
import java.util.Objects;

public class ScrapbookActivity extends _BaseActivity implements ScrapbookPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, ScrapbookActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.layout_tab_panel) TabLayout tabLayout;
    @BindView(R.id.vp_scrapbook) ViewPager2 viewPager;

    @BindArray(R.array.tab_scrapbook) String[] tabNames;
    @BindDimen(R.dimen.dp_48) int dp_48;

    private ScrapbookPresenter presenter;
    private ScrapbookPagerAdapter adapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_scrapbook;
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
        for (String tabName : tabNames) {
            TabView tabView = TabView.builder()
                .context(this)
                .name(tabName)
                .width(dp_48)
                .build();

            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(tabView);
            tabLayout.addTab(tab);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                presenter.onTabClick(tab.getPosition());
                getTabView(tab).setupSelected();
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {
                getTabView(tab).setupUnselected();
            }

            @Override public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        getTabView(0).setupSelected();
    }

    public TabView getTabView(TabLayout.Tab tab) {
        return (TabView) tab.getCustomView();
    }

    public TabView getTabView(int position) {
        return (TabView) Objects.requireNonNull(tabLayout.getTabAt(position)).getCustomView();
    }

    @Override public void setupViewPager() {
        adapter = new ScrapbookPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setUserInputEnabled(false);
        viewPager.setCurrentItem(0);
    }


    @Override public void setupCurrentPage(int position) {
        viewPager.setCurrentItem(position);
    }
}
