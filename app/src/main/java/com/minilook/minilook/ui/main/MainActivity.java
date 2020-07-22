package com.minilook.minilook.ui.main;

import android.content.Context;
import android.content.Intent;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.widget.BottomBar;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.ui.main.adapter.MainPagerAdapter;
import com.minilook.minilook.ui.main.di.MainArguments;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;

public class MainActivity extends BaseActivity implements MainPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.viewpager) ViewPager2 viewPager;
    @BindView(R.id.bottombar) BottomBar bottomBar;

    private MainPresenter presenter;
    private MainPagerAdapter adapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override protected void createPresenter() {
        presenter = new MainPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private MainArguments provideArguments() {
        return MainArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupViewPager() {
        adapter = new MainPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
    }

    @Override public void setupBottomBar() {
        bottomBar.setOnTabChangeListener(position -> {
            viewPager.setCurrentItem(position, false);
            presenter.onTabChanged(position);
        });
    }

    @Override public void setupBottombarTheme(boolean flag) {
        bottomBar.setupWhiteTheme(flag);
    }

    @Override public void navigateToProductDetail(String url) {
        ProductDetailActivity.start(this, url);
    }

    @Override public void navigateToBrandDetail(int brandId) {
        BrandDetailActivity.start(this, brandId);
    }
}
