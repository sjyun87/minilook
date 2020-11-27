package com.minilook.minilook.ui.review_write;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.review_write.di.ReviewWriteArguments;

public class ReviewWriteActivity extends _BaseActivity implements ReviewWritePresenter.View {

    public static void start(Context context, String orderNo, OrderProductDataModel data) {
        Intent intent = new Intent(context, ReviewWriteActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("orderNo", orderNo);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;
    @BindView(R.id.txt_option) TextView optionTextView;
    @BindView(R.id.edit_review) EditText reviewEditText;
    @BindView(R.id.txt_apply) TextView applyTextView;

    @BindString(R.string.review_write_option) String format_option;
    @BindString(R.string.toast_review_write) String str_review_write;

    @BindColor(R.color.color_FFF5F5F5) int color_FFF5F5F5;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;

    @BindDrawable(R.drawable.ph_square) Drawable img_placeholder;

    private ReviewWritePresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_review_write;
    }

    @Override protected void createPresenter() {
        presenter = new ReviewWritePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ReviewWriteArguments provideArguments() {
        return ReviewWriteArguments.builder()
            .view(this)
            .orderNo(getIntent().getStringExtra("orderNo"))
            .data((OrderProductDataModel) getIntent().getSerializableExtra("data"))
            .build();
    }

    @Override public void setThumb(String url) {
        Glide.with(this)
            .load(url)
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);
    }

    @Override public void setBrandName(String name) {
        brandNameTextView.setText(name);
    }

    @Override public void setProductName(String name) {
        productNameTextView.setText(name);
    }

    @Override public void setOption(String color, String size) {
        optionTextView.setText(String.format(format_option, color, size));
    }

    @Override public void setupReviewEditText() {
        reviewEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onTextChanged(s.toString());
            }

            @Override public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override public void enableApplyButton() {
        applyTextView.setEnabled(true);
        applyTextView.setBackgroundColor(color_FF8140E5);
    }

    @Override public void disableApplyButton() {
        applyTextView.setEnabled(false);
        applyTextView.setBackgroundColor(color_FFF5F5F5);
    }

    @Override public void showReviewWriteToast() {
        Toast.makeText(this, str_review_write, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.txt_apply)
    void onApplyClick() {
        presenter.onApplyClick();
    }
}
