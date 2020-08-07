package com.minilook.minilook.ui.lookbook;

import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.lookbook.adapter.LookBookPagerAdapter;
import com.minilook.minilook.ui.lookbook.di.LookBookArguments;

public class LookBookFragment extends BaseFragment implements LookBookPresenter.View {

    public static LookBookFragment newInstance() {
        return new LookBookFragment();
    }

    @BindView(R.id.viewpager) ViewPager2 viewPager;

    private LookBookPresenter presenter;
    private LookBookPagerAdapter adapter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_lookbook;
    }

    @Override protected void createPresenter() {
        presenter = new LookBookPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private LookBookArguments provideArguments() {
        return LookBookArguments.builder()
            .view(this)
            .build();
    }

    @Override public void onDestroyView() {
        viewPager.unregisterOnPageChangeCallback(OnPageChangeCallback);
        super.onDestroyView();
    }

    @Override public void setupViewPager() {
        adapter = new LookBookPagerAdapter(getChildFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.registerOnPageChangeCallback(OnPageChangeCallback);
    }

    @Override public void navigateToPreviewPage(boolean smoothScroll) {
        viewPager.setCurrentItem(0, smoothScroll);
    }

    @Override public void navigateToDetailPage(boolean smoothScroll) {
        viewPager.setCurrentItem(1, smoothScroll);
    }

    private ViewPager2.OnPageChangeCallback OnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (positionOffset > 0.75) {
                presenter.onPageSelected(position + 1);
            } else {
                presenter.onPageSelected(position);
            }
        }
    };
}
