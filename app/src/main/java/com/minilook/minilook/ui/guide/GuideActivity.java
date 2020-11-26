package com.minilook.minilook.ui.guide;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.guide.adapter.GuideAdapter;
import com.minilook.minilook.ui.guide.di.GuideArguments;
import com.minilook.minilook.ui.main.MainActivity;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class GuideActivity extends _BaseActivity implements GuidePresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.vp_guide) ViewPager2 viewPager;
    @BindView(R.id.txt_skip) TextView skipTextView;
    @BindView(R.id.indicator) DotsIndicator indicator;
    @BindView(R.id.txt_start) TextView startTextView;

    private GuidePresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_guide;
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
        viewPager.setAdapter(new GuideAdapter());
        indicator.setViewPager2(viewPager);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override public void onPageSelected(int position) {
                presenter.onPageSelected(position);
            }
        });
    }

    @Override public void showStartButton() {
        startTextView.setVisibility(View.VISIBLE);
        skipTextView.setVisibility(View.GONE);
        indicator.setVisibility(View.GONE);
    }

    @Override public void hideStartButton() {
        startTextView.setVisibility(View.GONE);
        skipTextView.setVisibility(View.VISIBLE);
        indicator.setVisibility(View.VISIBLE);
    }

    @Override public void navigateToMain() {
        MainActivity.start(this);
    }

    @OnClick(R.id.txt_skip)
    void onSkipClick() {
        presenter.onGuideEnd();
    }

    @OnClick(R.id.txt_start)
    void onStartClick() {
        presenter.onGuideEnd();
    }

    @Override public void onBackPressed() {
    }
}
