package com.minilook.minilook.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.StringRes;
import androidx.viewpager2.widget.ViewPager2;
import com.minilook.minilook.R;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.databinding.ActivityMainBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.widget.BottomBar;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.event_detail.EventDetailActivity;
import com.minilook.minilook.ui.lookbook.LookBookPresenterImpl;
import com.minilook.minilook.ui.lookbook.view.preview.LookBookPreviewPresenterImpl;
import com.minilook.minilook.ui.main.adapter.MainPagerAdapter;
import com.minilook.minilook.ui.main.di.MainArguments;
import com.minilook.minilook.ui.preorder_detail.PreorderDetailActivity;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.ui.promotion_detail.PromotionDetailActivity;

public class MainActivity extends BaseActivity implements MainPresenter.View {

    public static void start(Context context) {
        start(context, 0);
    }

    public static void start(Context context, int position) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    @StringRes int str_marketing_agree = R.string.toast_marketing_info_agree;
    @StringRes int str_marketing_disagree = R.string.toast_marketing_info_disagree;
    @StringRes int str_app_finish = R.string.toast_app_finish;

    private ActivityMainBinding binding;
    private MainPresenter presenter;

    @Override protected View getBindingView() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new MainPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    @Override protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int position = intent.getIntExtra("position", 0);
        binding.bottombar.setCurrentPage(position);
    }

    private MainArguments provideArguments() {
        return MainArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupViewPager() {
        binding.viewpager.setAdapter(new MainPagerAdapter(this));
        binding.viewpager.setUserInputEnabled(false);
        binding.viewpager.setOffscreenPageLimit(5);
        binding.viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override public void onPageSelected(int position) {
                binding.bottombar.setCurrentPage(position);
            }
        });
    }

    @Override public void setCurrentPage(int position) {
        binding.viewpager.setCurrentItem(position, false);
    }

    @Override public void setupBottomBar() {
        binding.bottombar.setOnBottomBarListener(presenter::onBottomBarClick);
    }

    @Override public void setBottomBarTheme(boolean flag) {
        if (binding.bottombar.getCurrentPage() == BottomBar.POSITION_LOOKBOOK) binding.bottombar.setWhiteTheme(flag);
    }

    @Override public void showMarketingDialog() {
        DialogManager.showMarketingDialog(this, presenter::onMarketingAgree, presenter::onMarketingDisagree);
    }

    @Override public void updateMarketingAgreeToast(boolean enable) {
        Toast.makeText(MainActivity.this, enable ? str_marketing_agree : str_marketing_disagree, Toast.LENGTH_SHORT)
            .show();
    }

    @Override public void showLookBookCoachMark() {
        binding.coachLookbook.getRoot().setVisibility(View.VISIBLE);
        binding.coachLookbook.getRoot().setOnClickListener(v -> {
            if (binding.coachLookbook.coach1.isShown()) {
                binding.coachLookbook.coach1.performClick();
            } else if (binding.coachLookbook.coach2.isShown()) {
                binding.coachLookbook.coach2.performClick();
            } else if (binding.coachLookbook.coach3.isShown()) {
                binding.coachLookbook.coach3.performClick();
            }
        });
        binding.coachLookbook.coach1.setOnClickListener(v -> {
            RxBus.send(new LookBookPreviewPresenterImpl.RxEventLookBookScrollToNextModule());
            binding.coachLookbook.coach1.setVisibility(View.GONE);
            binding.coachLookbook.coach2.setVisibility(View.VISIBLE);
        });
        binding.coachLookbook.coach2.setOnClickListener(v -> {
            RxBus.send(new LookBookPresenterImpl.RxEventScrollToDetail(true));
            binding.coachLookbook.coach2.setVisibility(View.GONE);
            binding.coachLookbook.coach3.setVisibility(View.VISIBLE);
        });
        binding.coachLookbook.coach3.setOnClickListener(v -> {
            RxBus.send(new LookBookPresenterImpl.RxEventScrollToPreview(true));
            binding.coachLookbook.getRoot().setVisibility(View.GONE);
            presenter.onCoachMarkEnd();
        });
    }

    @Override public void showLoadingView() {
        binding.loadingView.show();
    }

    @Override public void hideLoadingView() {
        binding.loadingView.hide();
    }

    @Override public void navigateToPromotionDetail(int promotionNo) {
        PromotionDetailActivity.start(this, promotionNo);
    }

    @Override public void navigateToEventDetail(int eventNo) {
        EventDetailActivity.start(this, eventNo);
    }

    @Override public void navigateToProductDetail(int productNo) {
        ProductDetailActivity.start(this, productNo);
    }

    @Override public void navigateToBrandDetail(int brandNo) {
        BrandDetailActivity.start(this, brandNo);
    }

    @Override public void navigateToPreorderDetail(int preorderNo) {
        PreorderDetailActivity.start(this, preorderNo);
    }

    private long backPressedTime;

    @Override public void onBackPressed() {
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis();
            Toast.makeText(this, str_app_finish, Toast.LENGTH_SHORT).show();
        } else {
            moveTaskToBack(true);
            finishAndRemoveTask();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
