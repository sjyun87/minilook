package com.minilook.minilook.ui.brand_detail;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.databinding.ActivityBrandDetailBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.brand_detail.adapter.BrandDetailProductAdapter;
import com.minilook.minilook.ui.brand_detail.adapter.BrandDetailStyleAdapter;
import com.minilook.minilook.ui.brand_detail.di.BrandDetailArguments;
import com.minilook.minilook.ui.brand_info.BrandInfoActivity;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.StringUtil;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class BrandDetailActivity extends BaseActivity implements BrandDetailPresenter.View {

    public static void start(Context context, int brandNo) {
        Intent intent = new Intent(context, BrandDetailActivity.class);
        intent.putExtra("brandNo", brandNo);
        context.startActivity(intent);
    }

    @ColorRes int color_FFDBDBDB = R.color.color_FFDBDBDB;

    @DrawableRes int img_scrap_off = R.drawable.ic_scrap_off;
    @DrawableRes int img_scrap_on = R.drawable.ic_scrap_on;
    @DrawableRes int ph_square = R.drawable.ph_square;
    @DrawableRes int ph_circle = R.drawable.ph_circle;

    @DimenRes int dp_2 = R.dimen.dp_2;
    @DimenRes int dp_150 = R.dimen.dp_150;

    private ActivityBrandDetailBinding binding;
    private BrandDetailPresenter presenter;

    private final BrandDetailStyleAdapter styleAdapter = new BrandDetailStyleAdapter();
    private final BaseAdapterDataView<String> styleAdapterView = styleAdapter;
    private final BrandDetailProductAdapter productAdapter = new BrandDetailProductAdapter();
    private final BaseAdapterDataView<ProductDataModel> productAdapterView = productAdapter;

    private boolean isLoading = false;

    @Override protected View getBindingView() {
        binding = ActivityBrandDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new BrandDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private BrandDetailArguments provideArguments() {
        return BrandDetailArguments.builder()
            .view(this)
            .brandNo(getIntent().getIntExtra("brandNo", -1))
            .styleAdapter(styleAdapter)
            .productAdapter(productAdapter)
            .build();
    }

    @Override public void setupClickAction() {
        binding.layoutSortPanel.setOnClickListener(view -> presenter.onSortClick());
        binding.layoutBrandInfoPanel.setOnClickListener(view -> presenter.onBrandInfoClick());
        binding.imgShare.setOnClickListener(view -> presenter.onShareClick());
        binding.layoutScrapPanel.setOnClickListener(view -> presenter.onScrapClick());
    }

    @Override public void setupScrollView() {
        binding.nsvContents.setOnScrollChangeListener(
            (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                if (!isLoading && scrollY > ((v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())
                    - (resources.getDimen(dp_150)))) {
                    isLoading = true;
                    presenter.onLoadMore();
                }
            });
    }

    @Override public void setupStyleRecyclerView() {
        binding.rcvStyle.setHasFixedSize(true);
        binding.rcvStyle.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rcvStyle.setAdapter(styleAdapter);
        DividerDecoration.builder(this)
            .size(resources.getDimen(dp_2))
            .asSpace()
            .build()
            .addTo(binding.rcvStyle);
    }

    @Override public void styleRefresh() {
        styleAdapterView.refresh();
    }

    @Override public void setupSortSelector() {
        binding.sortSelector.bind(App.getInstance().getSortCodes(), presenter::onSortSelected);
    }

    @Override public void showSortSelector() {
        binding.sortSelector.show();
    }

    @Override public void hideSortSelector() {
        binding.sortSelector.hide();
    }

    @Override public void setupSortText(String name) {
        binding.txtSort.setText(name);
    }

    @Override public void setupProductRecyclerView() {
        binding.rcvProduct.setHasFixedSize(true);
        binding.rcvProduct.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rcvProduct.setAdapter(productAdapter);
    }

    @Override public void productRefresh() {
        productAdapterView.refresh();
        isLoading = false;
    }

    @Override public void productRefresh(int start, int row) {
        productAdapterView.refresh(start, row);
        isLoading = false;
    }

    @Override public void setThumb(String url) {
        Glide.with(this)
            .load(url)
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgThumb);
    }

    @Override public void setLogo(String url) {
        Glide.with(this)
            .load(url)
            .placeholder(ph_circle)
            .error(ph_circle)
            .transition(new DrawableTransitionOptions().crossFade())
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(this, 1), getColor(color_FFDBDBDB))))
            .into(binding.imgLogo);
    }

    @Override public void setScrapCount(int count) {
        binding.txtScrapCount.setText(StringUtil.toDigit(count));
    }

    @Override public void setName(String name) {
        binding.txtName.setText(name);
    }

    @Override public void setTag(String tag) {
        binding.txtTag.setText(tag);
    }

    @Override public void setDesc(String desc) {
        binding.txtDesc.setText(desc);
    }

    @Override public void scrapOn() {
        binding.imgScrap.setImageResource(img_scrap_on);
    }

    @Override public void scrapOff() {
        binding.imgScrap.setImageResource(img_scrap_off);
    }

    @Override public void scrollToTop() {
        if (binding.nsvContents.getScrollY() > binding.layoutHeaderPanel.getY()) {
            binding.nsvContents.setScrollY((int) binding.layoutHeaderPanel.getY());
        }
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(this);
    }

    @Override public void navigateToBrandInfo(int brandNo) {
        BrandInfoActivity.start(this, brandNo);
    }

    @Override public void navigateToLogin() {
        LoginActivity.start(this);
    }

    @Override public void clear() {
        binding.layoutSortPanel.setOnClickListener(null);
        binding.layoutBrandInfoPanel.setOnClickListener(null);
        binding.imgShare.setOnClickListener(null);
        binding.layoutScrapPanel.setOnClickListener(null);
        binding.rcvStyle.setAdapter(null);
        binding.rcvProduct.setAdapter(null);
    }

    @Override public void onBackPressed() {
        if (binding.sortSelector.isShow()) {
            hideSortSelector();
        } else {
            super.onBackPressed();
        }
    }
}
