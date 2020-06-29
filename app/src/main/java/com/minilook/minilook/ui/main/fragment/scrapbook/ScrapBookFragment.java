package com.minilook.minilook.ui.main.fragment.scrapbook;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.main.fragment.scrapbook.di.ScrapBookArguments;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;

public class ScrapBookFragment extends BaseFragment implements ScrapBookPresenter.View {

    public static ScrapBookFragment newInstance() {
        return new ScrapBookFragment();
    }

    @BindView(R.id.txt_product) TextView productTextView;
    @BindView(R.id.txt_brand) TextView likeTextView;
    @BindView(R.id.txt_recent) TextView recentTextView;
    @BindView(R.id.viewpager) ViewPager2 viewPager;

    @BindDrawable(R.drawable.bg_scrapbook_tab) Drawable tabBgImage;
    @BindColor(R.color.color_FFFFFFFF) int color_FFFFFFFF;
    @BindColor(R.color.color_FFA9A9A9) int color_FFA9A9A9;

    private ScrapBookPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_scrapbook;
    }

    @Override protected void createPresenter() {
        presenter = new ScrapBookPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ScrapBookArguments provideArguments() {
        return ScrapBookArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setCurrentPage(int page) {

    }

    @Override public void setProductButtonUI() {
        productTextView.setBackground(tabBgImage);
        productTextView.setTextColor(color_FFFFFFFF);
        likeTextView.setBackground(null);
        likeTextView.setTextColor(color_FFA9A9A9);
        recentTextView.setBackground(null);
        recentTextView.setTextColor(color_FFA9A9A9);
    }

    @Override public void setBrandButtonUI() {
        productTextView.setBackground(null);
        productTextView.setTextColor(color_FFA9A9A9);
        likeTextView.setBackground(tabBgImage);
        likeTextView.setTextColor(color_FFFFFFFF);
        recentTextView.setBackground(null);
        recentTextView.setTextColor(color_FFA9A9A9);
    }

    @Override public void setRecentButtonUI() {
        productTextView.setBackground(null);
        productTextView.setTextColor(color_FFA9A9A9);
        likeTextView.setBackground(null);
        likeTextView.setTextColor(color_FFA9A9A9);
        recentTextView.setBackground(tabBgImage);
        recentTextView.setTextColor(color_FFFFFFFF);
    }

    @OnClick(R.id.txt_product)
    void onProductClick() {
        presenter.onProductClick();
    }

    @OnClick(R.id.txt_brand)
    void onFavoritesClick() {
        presenter.onBrandClick();
    }

    @OnClick(R.id.txt_recent)
    void onRecentClick() {
        presenter.onRecentClick();
    }
}
