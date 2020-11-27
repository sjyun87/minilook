package com.minilook.minilook.ui.lookbook;

import android.view.View;
import androidx.viewpager2.widget.ViewPager2;
import com.minilook.minilook.databinding.FragmentLookbookBinding;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.lookbook.adapter.LookBookPagerAdapter;
import com.minilook.minilook.ui.lookbook.di.LookBookArguments;

public class LookBookFragment extends BaseFragment implements LookBookPresenter.View {

    public static LookBookFragment newInstance() {
        return new LookBookFragment();
    }

    private FragmentLookbookBinding binding;
    private LookBookPresenter presenter;

    @Override protected View getBindingView() {
        binding = FragmentLookbookBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new LookBookPresenterImpl(provideArguments());
        getViewLifecycleOwner().getLifecycle().addObserver(presenter);
    }

    private LookBookArguments provideArguments() {
        return LookBookArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupViewPager() {
        binding.viewpager.setAdapter(
            new LookBookPagerAdapter(getChildFragmentManager(), getViewLifecycleOwner().getLifecycle()));
        binding.viewpager.setOffscreenPageLimit(1);
        binding.viewpager.registerOnPageChangeCallback(OnPageChangeCallback);
    }

    @Override public void scrollToPreviewPage(boolean smoothScroll) {
        binding.viewpager.setCurrentItem(0, smoothScroll);
    }

    @Override public void scrollToDetailPage(boolean smoothScroll) {
        binding.viewpager.setCurrentItem(1, smoothScroll);
    }

    @Override public void clear() {
        binding.viewpager.unregisterOnPageChangeCallback(OnPageChangeCallback);
    }

    private final ViewPager2.OnPageChangeCallback OnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (positionOffset > 0.75) {
                presenter.onPageSelected(position + 1);
            } else {
                presenter.onPageSelected(position);
            }
        }
    };
}
