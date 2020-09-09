package com.minilook.minilook.ui.main;

import android.content.Context;
import android.content.Intent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.widget.BottomBar;
import com.minilook.minilook.ui.base.widget.CustomToast;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.main.adapter.MainPagerAdapter;
import com.minilook.minilook.ui.main.di.MainArguments;

public class MainActivity extends BaseActivity implements MainPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    public static void start(Context context, int position) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    @BindView(R.id.root) ConstraintLayout root;
    @BindView(R.id.viewpager) ViewPager2 viewPager;
    @BindView(R.id.bottombar) BottomBar bottomBar;

    @BindString(R.string.base_toast_app_finish) String str_toast_app_finish;
    @BindString(R.string.base_toast_marketing_info_agree) String str_toast_marketing_agree;
    @BindString(R.string.base_toast_login) String str_toast_login;
    @BindString(R.string.base_toast_logout) String str_toast_logout;

    @BindColor(R.color.color_FF616161) int color_FF616161;

    private MainPresenter presenter;
    private MainPagerAdapter adapter;

    private long backPressedTime;

    @Override protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override protected void createPresenter() {
        presenter = new MainPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    @Override protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int position = intent.getIntExtra("position", -1);
        if (position != -1) bottomBar.setCurrentPage(position);
    }

    private MainArguments provideArguments() {
        return MainArguments.builder()
            .view(this)
            .build();
    }

    @Override public void onLogin() {
        CustomToast.make(this, str_toast_login).show();
    }

    @Override public void onLogout() {
        CustomToast.make(this, str_toast_logout).show();
    }

    @Override public void onProductScrap(boolean isScrap, ProductDataModel product) {
        presenter.onProductScrap(isScrap, product);
    }

    @Override public void onBrandScrap(boolean isScrap, BrandDataModel brand) {
        presenter.onBrandScrap(isScrap, brand);
    }

    @Override public void setupViewPager() {
        adapter = new MainPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
        viewPager.setOffscreenPageLimit(5);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override public void onPageSelected(int position) {
                bottomBar.setCurrentPage(position);
            }
        });
    }

    @Override public void setupCurrentPage(int position) {
        viewPager.setCurrentItem(position, false);
    }

    @Override public void setupBottomBar() {
        bottomBar.setOnTabChangeListener(position -> presenter.onTabChanged(position));
    }

    @Override public void setupBottomBarTheme(boolean flag) {
        bottomBar.setupWhiteTheme(flag);
    }

    @Override public void showMarketingDialog() {
        DialogManager.showMarketingDialog(this, () -> {
            presenter.onMarketingAgree();
            CustomToast.make(this, str_toast_marketing_agree).show();
        });
    }

    @Override public void navigateToLogin() {
        LoginActivity.start(this);
    }

    @Override public void onBackPressed() {
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis();
            CustomToast.make(this, str_toast_app_finish).show();
        } else {
            finishAffinity();
        }
    }
}
