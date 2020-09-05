package com.minilook.minilook.ui.ipage;

import android.view.View;
import android.widget.TextView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.coupon.CouponActivity;
import com.minilook.minilook.ui.ipage.di.IpageArguments;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.order_history.OrderHistoryActivity;
import com.minilook.minilook.ui.point.PointActivity;
import com.minilook.minilook.ui.profile.ProfileActivity;
import com.minilook.minilook.ui.question.QuestionActivity;
import com.minilook.minilook.ui.recent.RecentActivity;
import com.minilook.minilook.ui.scrapbook.ScrapbookActivity;
import com.minilook.minilook.ui.shoppingbag.ShoppingBagActivity;
import com.minilook.minilook.ui.webview.WebViewActivity;
import com.minilook.minilook.util.StringUtil;

public class IpageFragment extends BaseFragment implements IpagePresenter.View {

    public static IpageFragment newInstance() {
        return new IpageFragment();
    }

    @BindView(R.id.txt_nick) TextView nickTextView;
    @BindView(R.id.txt_point) TextView pointTextView;
    @BindView(R.id.txt_coupon) TextView couponTextView;
    @BindView(R.id.txt_order_complete) TextView orderCompleteTextView;
    @BindView(R.id.txt_packing) TextView packingTextView;
    @BindView(R.id.txt_delivery) TextView deliveryTextView;
    @BindView(R.id.txt_delivery_complete) TextView deliveryCompleteTextView;
    @BindView(R.id.curtain) View curtainView;

    @BindString(R.string.ipage_profile_login) String str_login;
    @BindString(R.string.ipage_profile_point) String format_point;
    @BindString(R.string.ipage_profile_coupon) String format_coupon;

    private IpagePresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_ipage;
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

    @Override public void setupLogin() {
        nickTextView.setText(str_login);
    }

    @Override public void setupNick(String text) {
        nickTextView.setText(text);
    }

    @Override public void setupPoint(int point) {
        pointTextView.setText(String.format(format_point, StringUtil.toDigit(point)));
    }

    @Override public void setupCoupon(int coupon) {
        couponTextView.setText(String.format(format_coupon, StringUtil.toDigit(coupon)));
    }

    @Override public void setupOrderComplete(int count) {
        orderCompleteTextView.setText(StringUtil.toDigit(count));
    }

    @Override public void setupPacking(int count) {
        packingTextView.setText(StringUtil.toDigit(count));
    }

    @Override public void setupDelivery(int count) {
        deliveryTextView.setText(StringUtil.toDigit(count));
    }

    @Override public void setupDeliveryComplete(int count) {
        deliveryCompleteTextView.setText(StringUtil.toDigit(count));
    }

    @Override public void showCurtain() {
        curtainView.setVisibility(View.VISIBLE);
    }

    @Override public void hideCurtain() {
        curtainView.setVisibility(View.GONE);
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

    @Override public void navigateToQuestion() {
        QuestionActivity.start(getContext());
    }

    @Override public void navigateToWebView(String url) {
        WebViewActivity.start(getContext(), url);
    }

    @OnClick(R.id.curtain)
    void onCurtainClick() {
        presenter.onCurtainClick();
    }

    @OnClick(R.id.layout_profile_panel)
    void onProfileClick() {
        presenter.onProfileClick();
    }

    @OnClick(R.id.layout_point_panel)
    void onPointClick() {
        presenter.onPointClick();
    }

    @OnClick(R.id.layout_coupon_panel)
    void onCouponClick() {
        presenter.onCouponClick();
    }

    @OnClick(R.id.layout_scrap_panel)
    void onScrapBookClick() {
        presenter.onScrapBookClick();
    }

    @OnClick(R.id.layout_shoppingbag_panel)
    void onShoppingBagClick() {
        presenter.onShoppingBagClick();
    }

    @OnClick(R.id.layout_recent_panel)
    void onRecentClick() {
        presenter.onRecentClick();
    }

    @OnClick(R.id.txt_order_more)
    void onOrderMoreClick() {
        presenter.onOrderMoreClick();
    }

    @OnClick(R.id.layout_order_complete_panel)
    void onOrderCompleteClick() {
        presenter.onOrderCompleteClick();
    }

    @OnClick(R.id.layout_packing_panel)
    void onPackingClick() {
        presenter.onPackingClick();
    }

    @OnClick(R.id.layout_delivery_panel)
    void onDeliveryClick() {
        presenter.onDeliveryClick();
    }

    @OnClick(R.id.layout_delivery_complete_panel)
    void onDeliveryCompleteClick() {
        presenter.onDeliveryCompleteClick();
    }

    @OnClick(R.id.layout_question_panel)
    void onQuestionClick() {
        presenter.onQuestionClick();
    }

    @OnClick(R.id.txt_service_notice)
    void onNoticeClick() {
        presenter.onNoticeClick();
    }

    @OnClick(R.id.txt_service_faq)
    void onFAQClick() {
        presenter.onFAQClick();
    }
}
