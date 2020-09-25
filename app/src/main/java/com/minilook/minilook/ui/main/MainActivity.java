package com.minilook.minilook.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.common.PrefsKey;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.widget.BottomBar;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.lookbook.LookBookPresenterImpl;
import com.minilook.minilook.ui.lookbook.view.preview.LookBookPreviewPresenterImpl;
import com.minilook.minilook.ui.main.adapter.MainPagerAdapter;
import com.minilook.minilook.ui.main.di.MainArguments;
import com.pixplicity.easyprefs.library.Prefs;

public class MainActivity extends BaseActivity implements MainPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    public static void start(Context context, int position) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    @BindView(R.id.viewpager) ViewPager2 viewPager;
    @BindView(R.id.bottombar) BottomBar bottomBar;

    @BindView(R.id.layout_coach_lookbook1) ConstraintLayout coachLookbook1;
    @BindView(R.id.layout_coach_lookbook2) ConstraintLayout coachLookbook2;
    @BindView(R.id.layout_coach_lookbook3) ConstraintLayout coachLookbook3;

    @BindString(R.string.toast_app_finish) String toast_app_finish;
    @BindString(R.string.toast_marketing_info_agree) String toast_marketing_agree;

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
    }

    @Override public void onLogout() {
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
            Toast.makeText(MainActivity.this, toast_marketing_agree, Toast.LENGTH_SHORT).show();
        }, dialogInterface -> presenter.onMarketingDismiss());
    }

    @Override public void showLookBookCoachMark() {
        coachLookbook1.setVisibility(View.VISIBLE);
    }

    @Override public void onBackPressed() {
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis();
            Toast.makeText(this, toast_app_finish, Toast.LENGTH_SHORT).show();
        } else {
            finishAffinity();
        }
    }

    @OnClick(R.id.layout_coach_lookbook1)
    void onCoachLookBook1Click() {
        RxBus.send(new LookBookPreviewPresenterImpl.RxEventLookBookCoachMark1());
        coachLookbook1.setVisibility(View.GONE);
        coachLookbook2.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.layout_coach_lookbook2)
    void onCoachLookBook2Click() {
        RxBus.send(new LookBookPresenterImpl.RxEventScrollToDetail(true));
        coachLookbook2.setVisibility(View.GONE);
        coachLookbook3.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.layout_coach_lookbook3)
    void onCoachLookBook3Click() {
        RxBus.send(new LookBookPresenterImpl.RxEventScrollToPreview(true));
        coachLookbook3.setVisibility(View.GONE);
        Prefs.putBoolean(PrefsKey.KEY_LOOKBOOK_COACH_VISIBLE, true);
    }
}
