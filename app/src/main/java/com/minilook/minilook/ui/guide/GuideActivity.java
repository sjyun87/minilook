package com.minilook.minilook.ui.guide;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.viewpager2.widget.ViewPager2;
import com.minilook.minilook.databinding.ActivityGuideBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.guide.adapter.GuideAdapter;
import com.minilook.minilook.ui.guide.di.GuideArguments;
import com.minilook.minilook.ui.main.MainActivity;

public class GuideActivity extends BaseActivity implements GuidePresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private ActivityGuideBinding binding;
    private GuidePresenter presenter;

    @Override protected View getBindingView() {
        binding = ActivityGuideBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new GuidePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private GuideArguments provideArguments() {
        return GuideArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupViewPager() {
        binding.vpGuide.setAdapter(new GuideAdapter());
        binding.indicator.setViewPager2(binding.vpGuide);
        binding.vpGuide.registerOnPageChangeCallback(onPageChangeCallback);
    }

    @Override public void setupGuideListener() {
        binding.txtStart.setOnClickListener(view -> presenter.onGuideEnd());
        binding.txtSkip.setOnClickListener(view -> presenter.onGuideEnd());
    }

    @Override public void showStartButton() {
        binding.txtStart.setVisibility(View.VISIBLE);
        binding.txtSkip.setVisibility(View.GONE);
        binding.indicator.setVisibility(View.GONE);
    }

    @Override public void hideStartButton() {
        binding.txtStart.setVisibility(View.GONE);
        binding.txtSkip.setVisibility(View.VISIBLE);
        binding.indicator.setVisibility(View.VISIBLE);
    }

    @Override public void navigateToMain() {
        MainActivity.start(this);
    }

    @Override public void clear() {
        binding.txtStart.setOnClickListener(null);
        binding.txtSkip.setOnClickListener(null);
        binding.vpGuide.unregisterOnPageChangeCallback(onPageChangeCallback);
        binding.vpGuide.setAdapter(null);
    }

    @Override public void onBackPressed() {
        presenter.onGuideEnd();
    }

    private final ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override public void onPageSelected(int position) {
            presenter.onPageSelected(position);
        }
    };
}
