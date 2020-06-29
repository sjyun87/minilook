package com.minilook.minilook.ui.main.fragment.lookbook.view.detail;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.main.fragment.lookbook.view.detail.adapter.LookBookImageAdapter;
import com.minilook.minilook.ui.main.fragment.lookbook.view.detail.adapter.LookBookProductAdapter;
import com.minilook.minilook.ui.main.fragment.lookbook.view.detail.di.LookBookDetailArguments;
import com.minilook.minilook.ui.main.fragment.market.viewholder.promotion.adapter.MarketPromotionAdapter;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator3;
import timber.log.Timber;

public class LookBookDetailFragment extends BaseFragment implements LookBookDetailPresenter.View {

    public static LookBookDetailFragment newInstance() {
        return new LookBookDetailFragment();
    }

    @BindView(R.id.vp_thumb) ViewPager2 thumbViewPager;
    @BindView(R.id.indicator) CircleIndicator3 indicator;
    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_desc) TextView descTextView;
    @BindView(R.id.rcv_product) RecyclerView productRecyclerView;

    private LookBookDetailPresenter presenter;
    private LookBookImageAdapter imageAdapter = new LookBookImageAdapter();
    private BaseAdapterDataView<String> imageAdapterDataView = imageAdapter;
    private LookBookProductAdapter productAdapter = new LookBookProductAdapter();
    private BaseAdapterDataView<ProductDataModel> productAdapterDataView = productAdapter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_lookbook_detail;
    }

    @Override protected void createPresenter() {
        presenter = new LookBookDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private LookBookDetailArguments provideArguments() {
        return LookBookDetailArguments.builder()
            .view(this)
            .imageAdapter(imageAdapter)
            .productAdapter(productAdapter)
            .build();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override public void setupViewPager() {
        thumbViewPager.setAdapter(imageAdapter);
        thumbViewPager.setPageTransformer(
            new MarginPageTransformer(getResources().getDimensionPixelOffset(R.dimen.dp_3)));
        ((RecyclerView) thumbViewPager.getChildAt(0)).setClipToPadding(false);
        thumbViewPager.getChildAt(0)
            .setPadding(0, 0, getResources().getDimensionPixelSize(R.dimen.dp_8), 0);
        indicator.setViewPager(thumbViewPager);
        imageAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
        thumbViewPager.getChildAt(0).setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });
    }

    @Override public void setupRecyclerView() {
        productRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productRecyclerView.setAdapter(productAdapter);
        DividerDecoration.builder(requireContext())
            .size(getResources().getDimensionPixelSize(R.dimen.dp_4))
            .asSpace()
            .build()
            .addTo(productRecyclerView);
    }

    @Override public void setTitle(String text) {
        titleTextView.setText(text);
    }

    @Override public void setDescription(String text) {
        descTextView.setText(text);
    }

    @Override public void imageRefresh() {
        imageAdapterDataView.refresh();
    }

    @Override public void productRefresh() {
        productAdapterDataView.refresh();
    }
}
