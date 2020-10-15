package com.minilook.minilook.ui.preorder;

import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindArray;
import butterknife.BindView;
import com.google.android.material.tabs.TabLayout;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.base.widget.TabView;
import com.minilook.minilook.ui.preorder.adapter.PreorderPagerAdapter;
import com.minilook.minilook.ui.preorder.di.PreorderArguments;
import com.minilook.minilook.ui.preorder_info.PreorderInfoActivity;
import java.util.Objects;

public class PreorderFragment extends BaseFragment implements PreorderPresenter.View {

    public static PreorderFragment newInstance() {
        return new PreorderFragment();
    }

    @BindView(R.id.layout_tab_panel) TabLayout tabLayout;
    @BindView(R.id.vp_preorder) ViewPager2 viewPager;

    @BindArray(R.array.tab_preorder) String[] tabNames;

    private PreorderPresenter presenter;
    private PreorderPagerAdapter adapter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_preorder;
    }

    @Override protected void createPresenter() {
        presenter = new PreorderPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PreorderArguments provideArguments() {
        return PreorderArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupTabLayout() {
        for (String tabName : tabNames) {
            TabView tabView = TabView.builder()
                .context(getContext())
                .name(tabName)
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
        adapter = new PreorderPagerAdapter(getChildFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setUserInputEnabled(false);
        viewPager.setCurrentItem(0);
    }

    @Override public void setupCurrentPage(int position) {
        viewPager.setCurrentItem(position);
        adapter.notifyDataSetChanged();
    }

    @Override public void navigateToPreorderInfo() {
        PreorderInfoActivity.start(getContext());
    }

    @Override public void hideClosePreorderTab() {
        tabLayout.removeTabAt(tabLayout.getTabCount() - 1);
    }
}
