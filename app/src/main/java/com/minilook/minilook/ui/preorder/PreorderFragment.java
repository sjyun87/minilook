package com.minilook.minilook.ui.preorder;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindArray;
import butterknife.BindView;
import com.google.android.material.tabs.TabLayout;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.preorder.adapter.PreorderPagerAdapter;
import com.minilook.minilook.ui.preorder.di.PreorderArguments;
import com.minilook.minilook.ui.base.widget.TabView;
import java.util.Objects;
import me.didik.component.StickyNestedScrollView;

public class PreorderFragment extends BaseFragment implements PreorderPresenter.View {

    public static PreorderFragment newInstance() {
        return new PreorderFragment();
    }

    @BindView(R.id.nsv_content) StickyNestedScrollView scrollView;
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
        viewPager.setOffscreenPageLimit(1);
        viewPager.setUserInputEnabled(false);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override public void onPageSelected(int position) {
                updateHeight(position);
            }
        });
        viewPager.setCurrentItem(0);
    }

    private void updateHeight(int position) {
        RecyclerView.LayoutManager layoutManager = ((RecyclerView) viewPager.getChildAt(0)).getLayoutManager();
        if (layoutManager != null) {
            View view = layoutManager.findViewByPosition(position);
            if (view != null) {
                view.post((Runnable) (Runnable) () -> {
                    int wMeasureSpec =
                        View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY);
                    int hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    view.measure(wMeasureSpec, hMeasureSpec);

                    if (viewPager.getLayoutParams().height != view.getMeasuredHeight()) {
                        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) viewPager.getLayoutParams();
                        params.height = view.getMeasuredHeight();
                        viewPager.setLayoutParams(params);
                    }
                });
            }
        }
    }

    @Override public void setupCurrentPage(int position) {
        viewPager.setCurrentItem(position);
        adapter.notifyDataSetChanged();
    }

    @Override public void scrollToTop() {
        scrollView.scrollTo(0, (int) tabLayout.getY());
    }
}
