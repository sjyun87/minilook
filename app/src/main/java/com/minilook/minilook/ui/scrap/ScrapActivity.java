package com.minilook.minilook.ui.scrap;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindArray;
import butterknife.BindDimen;
import butterknife.BindView;
import com.google.android.material.tabs.TabLayout;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.widget.TabView;
import com.minilook.minilook.ui.preorder.adapter.PreorderPagerAdapter;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.scrap.adapter.ScrapPagerAdapter;
import com.minilook.minilook.ui.scrap.di.ScrapArguments;
import java.util.Objects;

public class ScrapActivity extends BaseActivity implements ScrapPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, ScrapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.layout_tab_panel) TabLayout tabLayout;
    @BindView(R.id.vp_scrap) ViewPager2 viewPager;

    @BindArray(R.array.tab_scrap) String[] tabNames;
    @BindDimen(R.dimen.dp_48) int dp_48;

    private ScrapPresenter presenter;
    private ScrapPagerAdapter adapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_scrap;
    }

    @Override protected void createPresenter() {
        presenter = new ScrapPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ScrapArguments provideArguments() {
        return ScrapArguments.builder()
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
        adapter = new ScrapPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setUserInputEnabled(false);
        viewPager.setCurrentItem(0);
    }


    @Override public void setupCurrentPage(int position) {

    }

    @Override public void scrollToTop() {

    }
}
