package com.minilook.minilook.ui.order_detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.order.OrderBrandDataModel;
import com.minilook.minilook.data.model.order.OrderCancelDataModel;
import com.minilook.minilook.data.model.order.OrderGoodsDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.order_cancel.OrderCancelActivity;
import com.minilook.minilook.ui.order_detail.adapter.OrderDetailAdapter;
import com.minilook.minilook.ui.order_detail.di.OrderDetailArguments;
import com.minilook.minilook.ui.order_exchange_n_return.OrderExchangeNReturnActivity;
import com.minilook.minilook.util.StringUtil;

public class OrderDetailActivity extends BaseActivity implements OrderDetailPresenter.View {

    public static void start(Context context, String order_id, String receipt_id) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("order_id", order_id);
        intent.putExtra("receipt_id", receipt_id);
        context.startActivity(intent);
    }

    @BindView(R.id.txt_info_number) TextView orderNoTextView;
    @BindView(R.id.txt_info_date) TextView orderDateTextView;
    @BindView(R.id.rcv_brand_order) RecyclerView recyclerView;
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
            .order_id(getIntent().getStringExtra("order_id"))
            .receipt_id(getIntent().getStringExtra("receipt_id"))
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

    @Override public void showComingSoonToast() {
        Toast.makeText(this, "현재 서비스 준비중입니다.\n빠른 시일내에 업데이트 하겠습니다. 감사합니다.", Toast.LENGTH_SHORT).show();
    }

    @Override public void navigateToOutlink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override public void navigateToDial(String csPhone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + csPhone));
        startActivity(intent);
    }

    @Override public void navigateToOrderExchangeNReturn(OrderGoodsDataModel data) {
        OrderExchangeNReturnActivity.start(this, data);
    }

    @Override public void navigateToOrderCancel(OrderCancelDataModel items) {
        OrderCancelActivity.start(this, items);
    }

    @Override public void navigateToMinilookTalk() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://pf.kakao.com/_xmHqdK"));
        startActivity(intent);
    }

    @OnClick(R.id.txt_order_cancel)
    void onOrderAllCancelClick() {
        presenter.onOrderAllCancelClick();
    }
}
