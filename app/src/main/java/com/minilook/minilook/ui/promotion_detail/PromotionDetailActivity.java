package com.minilook.minilook.ui.promotion_detail;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.promotion.PromotionDataModel;
import com.minilook.minilook.databinding.ActivityPromotionDetailBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.promotion_detail.adapter.PromotionDetailOtherAdapter;
import com.minilook.minilook.ui.promotion_detail.adapter.PromotionDetailProductAdapter;
import com.minilook.minilook.ui.promotion_detail.di.PromotionDetailArguments;
import com.minilook.minilook.util.SpannableUtil;

public class PromotionDetailActivity extends BaseActivity implements PromotionDetailPresenter.View {

    public static void start(Context context, int promotionNo) {
        Intent intent = new Intent(context, PromotionDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("promotionNo", promotionNo);
        context.startActivity(intent);
    }

    @DrawableRes int ph_square = R.drawable.ph_square;

    @StringRes int str_format_total = R.string.promotion_total;
    @StringRes int str_format_total_bold = R.string.promotion_total_b;

    @DimenRes int dp_2 = R.dimen.dp_2;

    @FontRes int font_bold = R.font.nanum_square_b;

    private ActivityPromotionDetailBinding binding;
    private PromotionDetailPresenter presenter;

    private final PromotionDetailProductAdapter productAdapter = new PromotionDetailProductAdapter();
    private final BaseAdapterDataView<ProductDataModel> productAdapterView = productAdapter;
    private final PromotionDetailOtherAdapter promotionDetailOtherAdapter = new PromotionDetailOtherAdapter();
    private final BaseAdapterDataView<PromotionDataModel> promotionAdapterView = promotionDetailOtherAdapter;

    @Override protected View getBindingView() {
        binding = ActivityPromotionDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new PromotionDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PromotionDetailArguments provideArguments() {
        return PromotionDetailArguments.builder()
            .view(this)
            .promotionId(getIntent().getIntExtra("promotionNo", -1))
            .productAdapter(productAdapter)
            .promotionAdapter(promotionDetailOtherAdapter)
            .build();
    }

    @Override public void onProductScrap(ProductDataModel data) {
        presenter.onProductScrap(data);
    }

    @Override public void setupClickAction() {
        binding.titlebar.getBinding().imgTitlebarShare.setOnClickListener(view -> presenter.onShareClick());
    }

    @Override public void setupProductRecyclerView() {
        binding.rcvProduct.setHasFixedSize(true);
        binding.rcvProduct.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rcvProduct.setAdapter(productAdapter);
    }

    @Override public void productRefresh() {
        productAdapterView.refresh();
    }

    @Override public void setupPromotionRecyclerView() {
        binding.rcvPromotion.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rcvPromotion.setAdapter(promotionDetailOtherAdapter);
        DividerDecoration.builder(this)
            .size(resources.getDimen(dp_2))
            .asSpace()
            .build()
            .addTo(binding.rcvPromotion);
        EndlessOnScrollListener scrollListener =
            EndlessOnScrollListener.builder()
                .layoutManager(binding.rcvPromotion.getLayoutManager())
                .onLoadMoreListener(presenter::onLoadMore)
                .visibleThreshold(4)
                .build();
        binding.rcvPromotion.addOnScrollListener(scrollListener);
    }

    @Override public void promotionRefresh() {
        promotionAdapterView.refresh();
    }

    @Override public void promotionRefresh(int start, int rows) {
        promotionAdapterView.refresh(start, rows);
    }

    @Override public void setThumb(String url) {
        Glide.with(this)
            .load(url)
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgThumb);
    }

    @Override public void setEventImage(String url) {
        Glide.with(this)
            .load(url)
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgEvent);
    }

    @Override public void setTotal(int count) {
        String total = String.format(getString(str_format_total), count);
        String bold = String.format(getString(str_format_total_bold), count);
        binding.txtTotal.setText(SpannableUtil.fontSpan(total, bold, resources.getFont(font_bold)));
    }

    @Override public void hideOtherPromotions() {
        binding.layoutOtherPanel.setVisibility(View.GONE);
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(this);
    }

    @Override public void sendDynamicLink(String link) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, link);
        startActivity(Intent.createChooser(intent, "친구에게 공유하기"));
    }

    @Override public void clear() {
        binding.rcvProduct.setAdapter(null);
        binding.rcvPromotion.setAdapter(null);
    }
}
