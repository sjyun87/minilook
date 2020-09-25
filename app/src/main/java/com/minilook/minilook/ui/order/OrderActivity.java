package com.minilook.minilook.ui.order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindArray;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.BuildConfig;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.bootpay.BootPayDataModel;
import com.minilook.minilook.data.model.bootpay.BootPayItemDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import com.minilook.minilook.data.model.member.CouponDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.order.adapter.CouponAdapter;
import com.minilook.minilook.ui.order.adapter.MemoAdapter;
import com.minilook.minilook.ui.order.adapter.OrderAdapter;
import com.minilook.minilook.ui.order.di.OrderArguments;
import com.minilook.minilook.ui.order_complete.OrderCompleteActivity;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.ui.shipping.ShippingActivity;
import com.minilook.minilook.util.SpannableUtil;
import com.minilook.minilook.util.StringUtil;
import java.util.Arrays;
import kr.co.bootpay.Bootpay;
import kr.co.bootpay.BootpayBuilder;
import kr.co.bootpay.enums.PG;
import kr.co.bootpay.enums.UX;
import timber.log.Timber;

public class OrderActivity extends BaseActivity implements OrderPresenter.View {

    public static void start(Context context, String route) {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("route", route);
        context.startActivity(intent);
    }

    @BindView(R.id.layout_shipping_panel) ConstraintLayout shippingPanel;
    @BindView(R.id.txt_default) TextView defaultTextView;
    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.txt_phone) TextView phoneTextView;
    @BindView(R.id.txt_address) TextView addressTextView;
    @BindView(R.id.layout_shipping_add_panel) ConstraintLayout shippingAddPanel;
    @BindView(R.id.layout_memo_box) LinearLayout memoBox;
    @BindView(R.id.txt_memo_box) TextView memoBoxTextView;
    @BindView(R.id.img_memo_box) ImageView memoBoxArrowImageView;
    @BindView(R.id.edit_memo) EditText memoEditText;
    @BindView(R.id.rcv_memo) RecyclerView memoRecyclerView;

    @BindView(R.id.rcv_product) RecyclerView productRecyclerView;

    @BindView(R.id.layout_coupon_box) LinearLayout couponBox;
    @BindView(R.id.txt_coupon_box) TextView couponBoxTextView;
    @BindView(R.id.img_coupon_box) ImageView couponBoxArrowImageView;
    @BindView(R.id.rcv_coupon) RecyclerView couponRecyclerView;

    @BindView(R.id.txt_have_point) TextView havePointTextView;
    @BindView(R.id.edit_point) EditText pointEditText;
    @BindView(R.id.txt_point_all) TextView pointAllUseTextView;

    @BindView(R.id.txt_total_price) TextView totalPriceTextView;
    @BindView(R.id.txt_total_product_price) TextView totalProductPriceTextView;
    @BindView(R.id.txt_total_shipping_price) TextView totalShippingPriceTextView;
    @BindView(R.id.txt_total_coupon) TextView totalCouponTextView;
    @BindView(R.id.txt_total_point) TextView totalPointTextView;

    @BindView(R.id.txt_payment_card) TextView cardTextView;
    @BindView(R.id.txt_payment_bank) TextView bankTextView;

    @BindView(R.id.txt_point_earned) TextView pointEarnedTextView;
    @BindView(R.id.txt_point_confirm) TextView pointConfirmTextView;
    @BindView(R.id.txt_point_review) TextView pointReviewTextView;

    @BindView(R.id.img_check_order_info) ImageView orderInfoCheckImageView;
    @BindView(R.id.txt_order_confirm) TextView orderConfirmTextView;

    @BindString(R.string.shipping_address) String format_address;
    @BindString(R.string.order_total_shipping_price) String format_shipping_price;
    @BindString(R.string.order_shipping_memo_direct_input) String str_memo_direct_input;
    @BindString(R.string.order_shipping_memo_select) String str_memo_select;
    @BindString(R.string.order_coupon_empty) String str_coupon_empty;
    @BindString(R.string.order_coupon_no_available) String str_coupon_no_available;
    @BindString(R.string.order_coupon_select) String str_coupon_select;
    @BindString(R.string.order_coupon_available) String format_coupon_available;
    @BindString(R.string.order_coupon) String format_coupon;
    @BindString(R.string.order_total_coupon) String format_total_coupon;
    @BindString(R.string.order_have_point) String format_have_point;
    @BindString(R.string.order_total_point) String format_total_point;
    @BindString(R.string.order_point_over_use_toast) String str_point_over_use_toast;
    @BindString(R.string.order_point_over_total_price_toast) String str_point_over_total_price_toast;
    @BindString(R.string.order_point_use_condition_toast) String str_point_use_condition_toast;

    @BindDrawable(R.drawable.bg_button_border_lightgray) Drawable bg_border_lightgray;
    @BindDrawable(R.drawable.bg_button_border_gray) Drawable bg_border_gray;
    @BindDrawable(R.drawable.bg_button_border_purple) Drawable bg_border_purple;
    @BindDrawable(R.drawable.ic_arrow_down_gray) Drawable img_arrow_down_gray;
    @BindDrawable(R.drawable.ic_arrow_down_black) Drawable img_arrow_down_black;
    @BindDrawable(R.drawable.ic_arrow_up_black) Drawable img_arrow_up_black;
    @BindDrawable(R.drawable.ic_checkbox2_off) Drawable img_check_off;
    @BindDrawable(R.drawable.ic_checkbox2_on) Drawable img_check_on;

    @BindColor(R.color.color_FFA9A9A9) int color_FFA9A9A9;
    @BindColor(R.color.color_FF232323) int color_FF232323;
    @BindColor(R.color.color_FF424242) int color_FF424242;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;

    @BindArray(R.array.shipping_memo) String[] shippingMemos;

    private OrderPresenter presenter;
    private MemoAdapter memoAdapter = new MemoAdapter();
    private OrderAdapter orderAdapter = new OrderAdapter();
    private BaseAdapterDataView<ShoppingBrandDataModel> orderAdapterView = orderAdapter;
    private CouponAdapter couponAdapter = new CouponAdapter();
    private BaseAdapterDataView<CouponDataModel> couponAdapterView = couponAdapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_order;
    }

    @Override protected void createPresenter() {
        presenter = new OrderPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private OrderArguments provideArguments() {
        String route = getIntent().getStringExtra("route");
        boolean isDirect = route.equals(ProductDetailActivity.class.getSimpleName());
        return OrderArguments.builder()
            .view(this)
            .isDirect(isDirect)
            .orderAdapter(orderAdapter)
            .couponAdapter(couponAdapter)
            .build();
    }

    @Override public void setupMemoRecyclerView() {
        memoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        memoRecyclerView.setAdapter(memoAdapter);
        memoAdapter.set(Arrays.asList(shippingMemos));
        memoAdapter.refresh();
    }

    @Override public void showShippingPanel() {
        shippingPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideShippingPanel() {
        shippingPanel.setVisibility(View.GONE);
    }

    @Override public void showShippingAddPanel() {
        shippingAddPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideShippingAddPanel() {
        shippingAddPanel.setVisibility(View.INVISIBLE);
    }

    @Override public void setupName(String name) {
        nameTextView.setText(name);
    }

    @Override public void setupPhone(String phone) {
        phoneTextView.setText(phone);
    }

    @Override public void setupAddress(String zipcode, String address, String address_detail) {
        addressTextView.setText(String.format(format_address, zipcode, address, address_detail));
    }

    @Override public void showDefaultLabel() {
        defaultTextView.setVisibility(View.VISIBLE);
    }

    @Override public void hideDefaultLabel() {
        defaultTextView.setVisibility(View.GONE);
    }

    @Override public void showMemoBox() {
        memoBox.setVisibility(View.VISIBLE);
        memoEditText.setVisibility(View.VISIBLE);
    }

    @Override public void openMemoBox() {
        memoRecyclerView.setVisibility(View.VISIBLE);
        memoBoxArrowImageView.setImageDrawable(img_arrow_up_black);
    }

    @Override public void closeMemoBox() {
        memoRecyclerView.setVisibility(View.GONE);
        memoBoxArrowImageView.setImageDrawable(img_arrow_down_black);
    }

    @Override public void setupOpenMemoBoxText() {
        memoBoxTextView.setText(str_memo_select);
        memoBoxTextView.setTextColor(color_FFA9A9A9);
    }

    @Override public void setupDirectInputMemoBoxText() {
        memoBoxTextView.setText(str_memo_direct_input);
        memoBoxTextView.setTextColor(color_FF232323);
    }

    @Override public void setupMemoBoxText(String memo) {
        memoBoxTextView.setText(memo);
        memoBoxTextView.setTextColor(color_FF232323);
    }

    @Override public void showDirectMemoEditText() {
        memoEditText.setVisibility(View.VISIBLE);
    }

    @Override public void hideDirectMemoEditText() {
        memoEditText.setText("");
        memoEditText.setVisibility(View.GONE);
    }

    @Override public void setupProductRecyclerView() {
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productRecyclerView.setAdapter(orderAdapter);
    }

    @Override public void productRefresh() {
        orderAdapterView.refresh();
    }

    @Override public void setupTotalPrice(int price) {
        totalPriceTextView.setText(StringUtil.toDigit(price));
    }

    @Override public void setupTotalProductPrice(int price) {
        totalProductPriceTextView.setText(StringUtil.toDigit(price));
    }

    @Override public void setupCouponBoxText(int count) {
        couponBoxTextView.setText(String.format(format_coupon_available, count));
        couponBoxTextView.setTextColor(color_FFA9A9A9);
    }

    @Override public void setupSelectedCouponBoxText(int coupon, String name) {
        String couponPrice = StringUtil.toDigit(coupon);
        String formatText = String.format(format_coupon, couponPrice, name);
        couponBoxTextView.setText(SpannableUtil.fontSpan(formatText, couponPrice, font_bold));
        couponBoxTextView.setTextColor(color_FF424242);
    }

    @Override public void setupEmptyCouponBoxText() {
        couponBoxTextView.setText(str_coupon_empty);
        couponBoxTextView.setTextColor(color_FFA9A9A9);
    }

    @Override public void setupNoAvailableCouponBoxText() {
        couponBoxTextView.setText(str_coupon_no_available);
        couponBoxTextView.setTextColor(color_FFA9A9A9);
    }

    @Override public void setupOpenCouponBoxText() {
        couponBoxTextView.setText(str_coupon_select);
        couponBoxTextView.setTextColor(color_FFA9A9A9);
    }

    @Override public void enableCouponBox() {
        couponBox.setBackground(bg_border_gray);
        couponBoxArrowImageView.setImageDrawable(img_arrow_down_black);
        couponBox.setEnabled(true);
    }

    @Override public void disableCouponBox() {
        couponBox.setBackground(bg_border_lightgray);
        couponBoxArrowImageView.setImageDrawable(img_arrow_down_gray);
        couponBox.setEnabled(false);
    }

    @Override public void setupCouponRecyclerView() {
        couponRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        couponRecyclerView.setAdapter(couponAdapter);
    }

    @Override public void couponRefresh() {
        couponAdapterView.refresh();
    }

    @Override public void openCouponBox() {
        couponRecyclerView.setVisibility(View.VISIBLE);
        couponBoxArrowImageView.setImageDrawable(img_arrow_up_black);
    }

    @Override public void closeCouponBox() {
        couponRecyclerView.setVisibility(View.GONE);
        couponBoxArrowImageView.setImageDrawable(img_arrow_down_black);
    }

    @Override public void setupTotalCoupon(int coupon) {
        totalCouponTextView.setText(String.format(format_total_coupon, StringUtil.toDigit(coupon)));
    }

    @Override public void setupPointEditText() {
        pointEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    presenter.onPointEditTextChanged(Integer.parseInt(s.toString()));
                }
            }

            @Override public void afterTextChanged(Editable s) {
            }
        });
        pointEditText.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) presenter.onPointEditTextEnter();
            }
            return false;
        });
    }

    @Override public void setupHavePoint(int point) {
        havePointTextView.setText(String.format(format_have_point, StringUtil.toDigit(point)));
    }

    @Override public void disablePointBox() {
        pointEditText.setEnabled(false);
        pointAllUseTextView.setEnabled(false);
    }

    @Override public void setupPoint(int point) {
        pointEditText.setText(String.valueOf(point));
        pointEditText.setSelection(pointEditText.getText().length());
        totalPointTextView.setText(String.format(format_total_point, StringUtil.toDigit(point)));
    }

    @Override public void showOverPointToast() {
        Toast.makeText(this, str_point_over_use_toast, Toast.LENGTH_SHORT).show();
    }

    @Override public void showOverTotalPriceToast() {
        Toast.makeText(this, str_point_over_total_price_toast, Toast.LENGTH_SHORT).show();
    }

    @Override public void showUseMinPointToast() {
        Toast.makeText(this, str_point_use_condition_toast, Toast.LENGTH_SHORT).show();
    }

    @Override public void selectedCard() {
        cardTextView.setBackground(bg_border_purple);
        cardTextView.setTextColor(color_FF8140E5);
        bankTextView.setBackground(bg_border_lightgray);
        bankTextView.setTextColor(color_FFA9A9A9);
    }

    @Override public void selectedBank() {
        cardTextView.setBackground(bg_border_lightgray);
        cardTextView.setTextColor(color_FFA9A9A9);
        bankTextView.setBackground(bg_border_purple);
        bankTextView.setTextColor(color_FF8140E5);
    }

    @Override public void setupConfirmPointEarned(int point) {
        pointConfirmTextView.setText(String.format(format_have_point, StringUtil.toDigit(point)));
    }

    @Override public void setupReviewPointEarned(int point) {
        pointReviewTextView.setText(String.format(format_have_point, StringUtil.toDigit(point)));
    }

    @Override public void setupTotalPointEarned(int point) {
        pointEarnedTextView.setText(String.format(format_have_point, StringUtil.toDigit(point)));
    }

    @Override public void checkOrderInfo() {
        orderInfoCheckImageView.setImageDrawable(img_check_on);
    }

    @Override public void uncheckOrderInfo() {
        orderInfoCheckImageView.setImageDrawable(img_check_off);
    }

    @Override public void enableOrderConfirmButton() {
        orderConfirmTextView.setEnabled(true);
        orderConfirmTextView.setBackgroundColor(color_FF8140E5);
    }

    @Override public void disableOrderConfirmButton() {
        orderConfirmTextView.setEnabled(false);
        orderConfirmTextView.setBackgroundColor(color_FFA9A9A9);
    }

    @Override public void setupTotalShippingPrice(int price) {
        totalShippingPriceTextView.setText(String.format(format_shipping_price, StringUtil.toDigit(price)));
    }

    @Override public void navigateToShipping() {
        ShippingActivity.start(this, OrderActivity.class.getSimpleName());
    }

    @Override public void showBootPay(BootPayDataModel bootPayData) {
        BootpayBuilder bootpayBuilder = Bootpay.init(getFragmentManager())
            .setApplicationId(
                BuildConfig.DEBUG ? getString(R.string.bootpay_key_debug) : getString(R.string.bootpay_key_release))
            .setPG(PG.INICIS)
            .setMethod(bootPayData.getMethod())
            .setContext(this)
            .setBootUser(bootPayData.getBootUser())
            .setBootExtra(bootPayData.getBootExtra())
            .setUX(UX.PG_DIALOG)
            .setName(bootPayData.getName())
            .setOrderId(bootPayData.getOrderId())
            .setPrice(bootPayData.getPrice());
        for (BootPayItemDataModel bootpayModel : bootPayData.getItems()) {
            bootpayBuilder.addItem(
                bootpayModel.getName(),
                bootpayModel.getQuantity(),
                bootpayModel.getId(),
                bootPayData.getPrice());
        }
        bootpayBuilder.onConfirm(message -> {
            Timber.e("onConfirm :: %s", message);
            Bootpay.confirm(message);
        });
        bootpayBuilder.onDone(message -> {
            Timber.e("onDone :: %s", message);
            presenter.onBootPayDone(bootPayData, message);
        });
        bootpayBuilder.onCancel(message -> Timber.e("onCancel :: %s", message));
        bootpayBuilder.onError(message -> {
            Timber.e("onError :: %s", message);
            presenter.onBootPayError(message);
        });
        bootpayBuilder.onClose(message -> Timber.e("onClose :: %s", message));
        bootpayBuilder.request();
    }

    @Override public void setBootPayCancel() {
        Bootpay.removePaymentWindow();
    }

    @Override public void showOutOfStockDialog() {
        DialogManager.showOutOfStockDialog(this);
    }

    @Override public void showErrorToast() {
        Toast.makeText(this, "주문요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override public void showBootPayErrorToast() {
        Toast.makeText(this, "결제 요청에 실패하였습니다", Toast.LENGTH_SHORT).show();
    }

    @Override public void navigateToOrderComplete() {
        OrderCompleteActivity.start(this);
    }

    @OnClick({ R.id.layout_shipping_panel, R.id.layout_shipping_add_panel })
    void onShippingClick() {
        presenter.onShippingClick();
    }

    @OnClick(R.id.layout_memo_box)
    void onMemoBoxClick() {
        presenter.onMemoBoxClick();
    }

    @OnClick(R.id.layout_coupon_box)
    void onCouponBoxClick() {
        presenter.onCouponBoxClick();
    }

    @OnClick(R.id.txt_point_all)
    void onPointAllUseClick() {
        presenter.onPointAllUseClick();
    }

    @OnClick(R.id.txt_payment_card)
    void onPaymentCardClick() {
        presenter.onPaymentCardClick();
    }

    @OnClick(R.id.txt_payment_bank)
    void onPaymentBankClick() {
        presenter.onPaymentBankClick();
    }

    @OnClick(R.id.layout_order_info_check)
    void onOrderInfoCheck() {
        presenter.onOrderInfoCheck();
    }

    @OnClick(R.id.txt_order_confirm)
    void onOrderConfirmClick() {
        presenter.onOrderConfirmClick();
    }
}
