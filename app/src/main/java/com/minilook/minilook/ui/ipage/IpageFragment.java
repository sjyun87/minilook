package com.minilook.minilook.ui.ipage;

import android.view.View;
import androidx.annotation.StringRes;
import com.minilook.minilook.R;
import com.minilook.minilook.databinding.FragmentIpageBinding;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.coupon.CouponActivity;
import com.minilook.minilook.ui.ipage.di.IpageArguments;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.order_history.OrderHistoryActivity;
import com.minilook.minilook.ui.point.PointActivity;
import com.minilook.minilook.ui.profile.ProfileActivity;
import com.minilook.minilook.ui.recent.RecentActivity;
import com.minilook.minilook.ui.scrapbook.ScrapbookActivity;
import com.minilook.minilook.ui.shoppingbag.ShoppingBagActivity;
import com.minilook.minilook.ui.webview.WebViewActivity;
import com.minilook.minilook.util.StringUtil;

public class IpageFragment extends BaseFragment implements IpagePresenter.View {

    public static IpageFragment newInstance() {
        return new IpageFragment();
    }

    @StringRes int str_profile_login = R.string.ipage_profile_login;
    @StringRes int str_point_unit = R.string.ipage_profile_point;
    @StringRes int str_coupon_unit = R.string.ipage_profile_coupon;
    @StringRes int str_max_count = R.string.ipage_shopping_max_count;

    private FragmentIpageBinding binding;
    private IpagePresenter presenter;

    @Override protected View getBindingView() {
        binding = FragmentIpageBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new IpagePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private IpageArguments provideArguments() {
        return IpageArguments.builder()
            .view(this)
            .build();
    }

    @Override public void onLogin() {
        presenter.onLogin();
    }

    @Override public void onLogout() {
        presenter.onLogout();
    }

    @Override public void setupClickAction() {
        binding.curtain.setOnClickListener(view -> presenter.onCurtainClick());
        binding.layoutProfilePanel.setOnClickListener(view -> presenter.onProfileClick());
        binding.layoutPointPanel.setOnClickListener(view -> presenter.onPointClick());
        binding.layoutCouponPanel.setOnClickListener(view -> presenter.onCouponClick());
        binding.layoutScrapPanel.setOnClickListener(view -> presenter.onScrapBookClick());
        binding.layoutShoppingbagPanel.setOnClickListener(view -> presenter.onShoppingBagClick());
        binding.layoutRecentPanel.setOnClickListener(view -> presenter.onRecentClick());
        binding.txtOrderMore.setOnClickListener(view -> presenter.onOrderMoreClick());
        binding.layoutOrderCompletePanel.setOnClickListener(view -> presenter.onOrderCompleteClick());
        binding.layoutPackingPanel.setOnClickListener(view -> presenter.onPackingClick());
        binding.layoutDeliveryPanel.setOnClickListener(view -> presenter.onDeliveryClick());
        binding.layoutDeliveryCompletePanel.setOnClickListener(view -> presenter.onDeliveryCompleteClick());
        binding.layoutQuestionPanel.setOnClickListener(view -> presenter.onQuestionClick());
        binding.layoutReviewPanel.setOnClickListener(view -> presenter.onReviewClick());
        binding.txtServiceNotice.setOnClickListener(view -> presenter.onNoticeClick());
        binding.txtServiceFaq.setOnClickListener(view -> presenter.onFAQClick());
        binding.layoutOrderPanel.setOnClickListener(view -> presenter.onOrderMoreClick());
    }

    @Override public void setLoginButton() {
        binding.txtNick.setText(resources.getString(str_profile_login));
    }

    @Override public void setNick(String text) {
        binding.txtNick.setText(text);
    }

    @Override public void setPoint(int point) {
        binding.txtPoint.setText(String.format(resources.getString(str_point_unit), StringUtil.toDigit(point)));
    }

    @Override public void setCoupon(int coupon) {
        binding.txtCoupon.setText(String.format(resources.getString(str_coupon_unit), StringUtil.toDigit(coupon)));
    }

    @Override public void setOrderComplete(int count) {
        binding.txtOrderComplete.setText(StringUtil.toDigit(count));
    }

    @Override public void setPacking(int count) {
        binding.txtPacking.setText(StringUtil.toDigit(count));
    }

    @Override public void setDelivery(int count) {
        binding.txtDelivery.setText(StringUtil.toDigit(count));
    }

    @Override public void setDeliveryComplete(int count) {
        binding.txtDeliveryComplete.setText(StringUtil.toDigit(count));
    }

    @Override public void setShoppingBagCount(int count) {
        if (count > 99) {
            binding.txtShoppingbagCount.setText(resources.getString(str_max_count));
        } else {
            binding.txtShoppingbagCount.setText(String.valueOf(count));
        }
        binding.txtShoppingbagCount.setVisibility(View.VISIBLE);
    }

    @Override public void setReviewCount(int count) {
        if (count > 99) {
            binding.txtReviewCount.setText(resources.getString(str_max_count));
        } else {
            binding.txtReviewCount.setText(String.valueOf(count));
        }
        binding.txtReviewCount.setVisibility(View.VISIBLE);
    }

    @Override public void setQuestionCount(int count) {
        if (count > 99) {
            binding.txtQuestionCount.setText(resources.getString(str_max_count));
        } else {
            binding.txtQuestionCount.setText(String.valueOf(count));
        }
        binding.txtQuestionCount.setVisibility(View.VISIBLE);
    }

    @Override public void showCurtain() {
        binding.curtain.setVisibility(View.VISIBLE);
    }

    @Override public void hideCurtain() {
        binding.curtain.setVisibility(View.GONE);
    }

    @Override public void navigateToLogin() {
        LoginActivity.start(getContext());
    }

    @Override public void navigateToProfile() {
        ProfileActivity.start(getContext());
    }

    @Override public void navigateToPoint() {
        PointActivity.start(getContext());
    }

    @Override public void navigateToCoupon() {
        CouponActivity.start(getContext());
    }

    @Override public void navigateToScrapBook() {
        ScrapbookActivity.start(getContext());
    }

    @Override public void navigateToShoppingBag() {
        ShoppingBagActivity.start(getContext());
    }

    @Override public void navigateToRecent() {
        RecentActivity.start(getContext());
    }

    @Override public void navigateToOrderMore() {
        OrderHistoryActivity.start(getContext());
    }

    @Override public void navigateToQuestionHistory() {
        //QuestionActivity.start(getContext());
    }

    @Override public void navigateToReviewHistory() {

    }

    @Override public void navigateToWebView(String url) {
        WebViewActivity.start(getContext(), url);
    }
}
