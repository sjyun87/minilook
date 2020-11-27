package com.minilook.minilook.ui.promotion_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.promotion.PromotionDataModel;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.promotion_detail.adapter.PromotionAdapter;
import com.minilook.minilook.ui.promotion_detail.di.PromotionDetailArguments;
import com.minilook.minilook.data.firebase.DynamicLinkManager;
import com.minilook.minilook.util.SpannableUtil;

public class PromotionDetailActivity extends _BaseActivity implements PromotionDetailPresenter.View {

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, PromotionDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("promotion_id", id);
        context.startActivity(intent);
    }

    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.img_event) ImageView eventImageView;
    @BindView(R.id.txt_total) TextView totalTextView;
    @BindView(R.id.rcv_product) RecyclerView productRecyclerView;
    @BindView(R.id.rcv_promotion) RecyclerView promotionRecyclerView;

    @BindDrawable(R.drawable.ph_square) Drawable img_placeholder;
    @BindDrawable(R.drawable.placeholder_image_wide) Drawable img_placeholder_wide;

    @BindString(R.string.promotion_total) String format_total;
    @BindString(R.string.promotion_total_b) String format_total_bold;
    @BindString(R.string.dialog_error_title) String str_error_msg;

    @BindDimen(R.dimen.dp_2) int dp_2;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;

    private PromotionDetailPresenter presenter;
    private ProductAdapter productAdapter = new ProductAdapter();
    private BaseAdapterDataView<ProductDataModel> productAdapterView = productAdapter;
    private PromotionAdapter promotionAdapter = new PromotionAdapter();
    private BaseAdapterDataView<PromotionDataModel> promotionAdapterView = promotionAdapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_promotion_detail;
    }

    @Override protected void createPresenter() {
        presenter = new PromotionDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PromotionDetailArguments provideArguments() {
        return PromotionDetailArguments.builder()
            .view(this)
            .promotionId(getIntent().getIntExtra("promotion_id", -1))
            .productAdapter(productAdapter)
            .promotionAdapter(promotionAdapter)
            .dynamicLinkManager(new DynamicLinkManager(this))
            .build();
    }

    @Override public void setupProductRecyclerView() {
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productAdapter.setViewType(ProductAdapter.VIEW_TYPE_GRID);
        productRecyclerView.setAdapter(productAdapter);
    }

    @Override public void productRefresh() {
        productAdapterView.refresh();
    }

    @Override public void setupPromotionRecyclerView() {
        promotionRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        promotionRecyclerView.setAdapter(promotionAdapter);
        DividerDecoration.builder(this)
            .size(dp_2)
            .asSpace()
            .build()
            .addTo(promotionRecyclerView);
        EndlessOnScrollListener scrollListener =
            EndlessOnScrollListener.builder()
                .layoutManager(productRecyclerView.getLayoutManager())
                .onLoadMoreListener(presenter::onLoadMore)
                .visibleThreshold(4)
                .build();
        promotionRecyclerView.addOnScrollListener(scrollListener);
    }

    @Override public void promotionRefresh(int start, int rows) {
        promotionAdapterView.refresh(start, rows);
    }

    @Override public void sendLink(String shareLink) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareLink);
        startActivity(Intent.createChooser(intent, "친구에게 공유하기"));
    }

    @Override public void showErrorMessage() {
        Toast.makeText(this, str_error_msg, Toast.LENGTH_SHORT).show();
    }

    @Override public void setupThumb(String url) {
        Glide.with(this)
            .load(url)
            .placeholder(img_placeholder_wide)
            .error(img_placeholder_wide)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);
    }

    @Override public void setupEventImage(String url) {
        Glide.with(this)
            .load(url)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(eventImageView);
    }

    @Override public void setupTotal(int count) {
        String total = String.format(format_total, count);
        String bold = String.format(format_total_bold, count);
        totalTextView.setText(SpannableUtil.fontSpan(total, bold, font_bold));
    }

    @OnClick(R.id.img_titlebar_share)
    void onShareClick() {
        presenter.onShareClick();
    }
}
