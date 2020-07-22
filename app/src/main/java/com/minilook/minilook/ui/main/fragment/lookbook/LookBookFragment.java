package com.minilook.minilook.ui.main.fragment.lookbook;

import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.main.fragment.lookbook.adapter.LookBookPagerAdapter;
import com.minilook.minilook.ui.main.fragment.lookbook.di.LookBookArguments;
import timber.log.Timber;

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

    @Override public void setupViewPager() {
        adapter = new LookBookPagerAdapter(getChildFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Timber.e("positionOffset :: "  + positionOffset);
                //if (positionOffset < 0.5) {
                //    presenter.onPageSelected(position);
                //} else {
                //    presenter.onPageSelected(position + 1);
                //}
            }

            @Override public void onPageSelected(int position) {
                presenter.onPageSelected(position);
            }
        });
    }

    @Override public void navigateToDetailPage() {
        viewPager.setCurrentItem(1);
    }
}
