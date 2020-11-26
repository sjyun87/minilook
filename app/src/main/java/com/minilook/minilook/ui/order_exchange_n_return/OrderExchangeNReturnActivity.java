package com.minilook.minilook.ui.order_exchange_n_return;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.order_exchange_n_return.adapter.ExchangeNReturnReasonAdapter;
import com.minilook.minilook.ui.order_exchange_n_return.adapter.ExchangeNReturnTypeAdapter;
import com.minilook.minilook.ui.order_exchange_n_return.di.OrderExchangeNReturnArguments;
import com.minilook.minilook.util.StringUtil;

public class OrderExchangeNReturnActivity extends _BaseActivity implements OrderExchangeNReturnPresenter.View {

    public static void start(Context context, OrderProductDataModel data) {
        Intent intent = new Intent(context, OrderExchangeNReturnActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;
    @BindView(R.id.txt_option) TextView optionTextView;
    @BindView(R.id.txt_price) TextView priceTextView;

    @BindView(R.id.txt_type_box) TextView typeTextView;
    @BindView(R.id.img_type_box) ImageView typeArrowImageView;
    @BindView(R.id.rcv_type) RecyclerView typeRecyclerView;
    @BindView(R.id.txt_reason_box) TextView reasonTextView;
    @BindView(R.id.img_reason_box) ImageView reasonArrowImageView;
    @BindView(R.id.rcv_reason) RecyclerView reasonRecyclerView;
    @BindView(R.id.edit_reason_detail) EditText reasonDetailEditText;

    @BindView(R.id.txt_apply) TextView applyTextView;

    @BindString(R.string.order_exchange_n_return_option) String format_option;
    @BindString(R.string.order_exchange_n_return_type_hint) String str_type_hint;
    @BindString(R.string.order_exchange_n_return_reason_hint) String str_reason_hint;
    @BindString(R.string.toast_receipt_completed) String toast_receipt_complete;

    @BindColor(R.color.color_FF232323) int color_FF232323;
    @BindColor(R.color.color_FFA9A9A9) int color_FFA9A9A9;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;

    @BindDrawable(R.drawable.ic_arrow_down_black) Drawable img_arrow_down;
    @BindDrawable(R.drawable.ic_arrow_up_black) Drawable img_arrow_up;
    @BindDrawable(R.drawable.placeholder_image) Drawable img_placeholder;

    private OrderExchangeNReturnPresenter presenter;
    private ExchangeNReturnTypeAdapter typeAdapter = new ExchangeNReturnTypeAdapter();
    private BaseAdapterDataView<CodeDataModel> typeAdapterView = typeAdapter;
    private ExchangeNReturnReasonAdapter reasonAdapter = new ExchangeNReturnReasonAdapter();
    private BaseAdapterDataView<CodeDataModel> reasonAdapterView = reasonAdapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_order_exchange_n_return;
    }

    @Override protected void createPresenter() {
        presenter = new OrderExchangeNReturnPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private OrderExchangeNReturnArguments provideArguments() {
        return OrderExchangeNReturnArguments.builder()
            .view(this)
            .data((OrderProductDataModel) getIntent().getSerializableExtra("data"))
            .typeAdapter(typeAdapter)
            .reasonAdapter(reasonAdapter)
            .build();
    }

    @Override public void setupTypeRecyclerView() {
        typeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        typeRecyclerView.setAdapter(typeAdapter);
    }

    @Override public void typeRefresh() {
        typeAdapterView.refresh();
    }

    @Override public void setupReasonRecyclerView() {
        reasonRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reasonRecyclerView.setAdapter(reasonAdapter);
    }

    @Override public void reasonRefresh() {
        reasonAdapterView.refresh();
    }

    @Override public void setupReasonDetailEditText() {
        reasonDetailEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onReasonDetailTextChanged(s.toString());
            }

            @Override public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override public void setThumbImage(String url) {
        Glide.with(this)
            .load(url)
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);
    }

    @Override public void setProductName(String name) {
        productNameTextView.setText(name);
    }

    @Override public void setOption(String color, String size) {
        optionTextView.setText(String.format(format_option, color, size));
    }

    @Override public void setProductPrice(int price) {
        priceTextView.setText(StringUtil.toDigit(price));
    }

    @Override public void showTypeBox() {
        typeRecyclerView.setVisibility(View.VISIBLE);
        typeArrowImageView.setImageDrawable(img_arrow_up);
        setTypeHint();
    }

    @Override public void hideTypeBox() {
        typeRecyclerView.setVisibility(View.GONE);
        typeArrowImageView.setImageDrawable(img_arrow_down);
    }

    @Override public void setSelectedType(String type) {
        typeTextView.setText(type);
        typeTextView.setTextColor(color_FF232323);
    }

    @Override public void setTypeHint() {
        typeTextView.setText(str_type_hint);
        typeTextView.setTextColor(color_FFA9A9A9);
    }

    @Override public void showReasonBox() {
        reasonRecyclerView.setVisibility(View.VISIBLE);
        reasonArrowImageView.setImageDrawable(img_arrow_up);
        setReasonHint();
    }

    @Override public void hideReasonBox() {
        reasonRecyclerView.setVisibility(View.GONE);
        reasonArrowImageView.setImageDrawable(img_arrow_down);
    }

    @Override public void selectedReason(String reason) {
        reasonTextView.setText(reason);
        reasonTextView.setTextColor(color_FF232323);
    }

    @Override public void setReasonHint() {
        reasonTextView.setText(str_reason_hint);
        reasonTextView.setTextColor(color_FFA9A9A9);
    }

    @Override public void enableApplyButton() {
        applyTextView.setBackgroundColor(color_FF8140E5);
        applyTextView.setEnabled(true);
    }

    @Override public void showReceiptCompleteToast() {
        Toast.makeText(this, toast_receipt_complete, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.layout_type_box)
    void onTypeBoxClick() {
        presenter.onTypeBoxClick();
    }

    @OnClick(R.id.layout_reason_box)
    void onReasonBoxClick() {
        presenter.onReasonBoxClick();
    }

    @OnClick(R.id.txt_apply)
    void onApplyClick() {
        presenter.onApplyClick();
    }
}
