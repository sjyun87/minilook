package com.minilook.minilook.ui.order_detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.order.OrderBrandDataModel;
import com.minilook.minilook.data.model.order.OrderCancelDataModel;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.order_cancel.OrderCancelActivity;
import com.minilook.minilook.ui.order_detail.adapter.OrderDetailAdapter;
import com.minilook.minilook.ui.order_detail.di.OrderDetailArguments;
import com.minilook.minilook.ui.order_exchange_n_return.OrderExchangeNReturnActivity;
import com.minilook.minilook.ui.review_write.ReviewWriteActivity;
import com.minilook.minilook.util.StringUtil;

public class OrderDetailActivity extends BaseActivity implements OrderDetailPresenter.View {

    public static void start(Context context, String orderNo, String receiptNo) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("orderNo", orderNo);
        intent.putExtra("receiptNo", receiptNo);
        context.startActivity(intent);
    }

    @BindView(R.id.txt_info_number) TextView orderNoTextView;
    @BindView(R.id.txt_info_date) TextView orderDateTextView;
    @BindView(R.id.rcv_brand_order) RecyclerView recyclerView;
    @BindView(R.id.layout_order_cancel_panel) ConstraintLayout allCancelPanel;
    @BindView(R.id.txt_shipping_name) TextView shippingNameTextView;
    @BindView(R.id.txt_shipping_phone) TextView shippingPhoneTextView;
    @BindView(R.id.txt_shipping_address) TextView shippingAddressTextView;
    @BindView(R.id.txt_payment_method) TextView paymentMethodTextView;
    @BindView(R.id.txt_total_price) TextView paymentPriceTextView;
    @BindView(R.id.txt_total_product_price) TextView productPriceTextView;
    @BindView(R.id.txt_total_shipping_price) TextView shippingPriceTextView;
    @BindView(R.id.txt_total_coupon) TextView couponTextView;
    @BindView(R.id.txt_total_point) TextView pointTextView;

    @BindString(R.string.order_detail_shipping_address) String format_address;
    @BindString(R.string.order_detail_shipping_price) String format_shipping_price;
    @BindString(R.string.order_detail_coupon_price) String format_coupon_price;
    @BindString(R.string.order_detail_point) String format_point;

    private OrderDetailPresenter presenter;
    private OrderDetailAdapter adapter = new OrderDetailAdapter();
    private BaseAdapterDataView<OrderBrandDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_order_detail;
    }

    @Override protected void createPresenter() {
        presenter = new OrderDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private OrderDetailArguments provideArguments() {
        return OrderDetailArguments.builder()
            .view(this)
            .orderNo(getIntent().getStringExtra("orderNo"))
            .receiptNo(getIntent().getStringExtra("receiptNo"))
            .adapter(adapter)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void setOrderNo(String no) {
        orderNoTextView.setText(no);
    }

    @Override public void setOrderDate(String date) {
        orderDateTextView.setText(date);
    }

    @Override public void hideAllCancelButton() {
        allCancelPanel.setVisibility(View.GONE);
    }

    @Override public void setShippingName(String name) {
        shippingNameTextView.setText(name);
    }

    @Override public void setShippingPhone(String phone) {
        shippingPhoneTextView.setText(phone);
    }

    @Override public void setShippingAddress(String zipcode, String address, String address_detail) {
        shippingAddressTextView.setText(String.format(format_address, zipcode, address, address_detail));
    }

    @Override public void setPaymentMethod(String method) {
        paymentMethodTextView.setText(method);
    }

    @Override public void setPaymentPrice(int price) {
        paymentPriceTextView.setText(StringUtil.toDigit(price));
    }

    @Override public void setProductPrice(int price) {
        productPriceTextView.setText(StringUtil.toDigit(price));
    }

    @Override public void setShippingPrice(int price) {
        shippingPriceTextView.setText(String.format(format_shipping_price, StringUtil.toDigit(price)));
    }

    @Override public void setCouponPrice(int price) {
        couponTextView.setText(String.format(format_coupon_price, StringUtil.toDigit(price)));
    }

    @Override public void setPoint(int point) {
        pointTextView.setText(String.format(format_point, StringUtil.toDigit(point)));
    }

    @Override public void showPurchaseConfirmDialog(int orderOptionNo) {
        DialogManager.showPurchaseConfirmDialog(this, () -> presenter.onPurchaseConfirmDialogOkClick(orderOptionNo));
    }

    @Override public void showBrandCallDialog(String name, String logo, String csTime, String csTel) {
        DialogManager.showBrandCallDialog(this, name, logo, csTime, () -> presenter.onBrandCallDialogOkClick(csTel));
    }

    @Override public void navigateToOutlink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override public void navigateToDial(String csPhone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + csPhone));
        startActivity(intent);
    }

    @Override public void navigateToOrderExchangeNReturn(OrderProductDataModel data) {
        OrderExchangeNReturnActivity.start(this, data);
    }

    @Override public void navigateToOrderCancel(OrderCancelDataModel items) {
        OrderCancelActivity.start(this, items);
    }

    @Override public void navigateToMinilookTalk() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://pf.kakao.com/_xmHqdK"));
        startActivity(intent);
    }

    @Override public void navigateToReviewWrite(String orderNo, OrderProductDataModel data) {
        ReviewWriteActivity.start(this, orderNo, data);
    }

    @OnClick(R.id.txt_order_cancel)
    void onOrderAllCancelClick() {
        presenter.onOrderAllCancelClick();
    }
}
