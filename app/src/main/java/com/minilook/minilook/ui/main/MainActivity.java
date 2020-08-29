package com.minilook.minilook.ui.main;

import android.content.Context;
import android.content.Intent;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindString;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.manager.ToastManager;
import com.minilook.minilook.ui.base.widget.BottomBar;
import com.minilook.minilook.ui.main.adapter.MainPagerAdapter;
import com.minilook.minilook.ui.main.di.MainArguments;

public class MainActivity extends BaseActivity implements MainPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    public static void clearStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.viewpager) ViewPager2 viewPager;
    @BindView(R.id.bottombar) BottomBar bottomBar;

    @BindString(R.string.base_app_finish) String str_app_finish;

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
        bottomBar.setCurrentPage(BottomBar.POSITION_MARKET);
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
        viewPager.setOffscreenPageLimit(5);
    }

    @Override public void setupBottomBar() {
        bottomBar.setOnTabChangeListener(position -> presenter.onTabChanged(position));
    }

    @Override public void setupBottomBarTheme(boolean flag) {
        bottomBar.setupWhiteTheme(flag);
    }

    @Override public void setupCurrentPage(int position) {
        viewPager.setCurrentItem(position, false);
    }

    @Override public void onBackPressed() {
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis();
            ToastManager.showToast(this, str_app_finish);
        } else {
            finishAffinity();
        }
    }
}
