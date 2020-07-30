package com.minilook.minilook.ui.brand_detail;

import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.brand.BrandDetailDataModel;
import com.minilook.minilook.data.model.category.CategoryDataModel;
import com.minilook.minilook.data.model.common.SortDataModel;
import com.minilook.minilook.data.model.market.MarketMDPickDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.network.brand.BrandRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.brand_detail.di.BrandDetailArguments;
import com.minilook.minilook.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class BrandDetailPresenterImpl extends BasePresenterImpl implements BrandDetailPresenter {

    private final View view;
    private final int id;
    private final BaseAdapterDataModel<String> styleAdapter;
    private final BaseAdapterDataModel<ProductDataModel> pickAdapter;
    private final BaseAdapterDataModel<CategoryDataModel> categoryAdapter;
    private final BaseAdapterDataModel<SortDataModel> sortAdapter;
    private final BrandRequest brandRequest;

    private int selectedPosition = 0;
    private boolean isSortVisible = false;

    public BrandDetailPresenterImpl(BrandDetailArguments args) {
        view = args.getView();
        id = args.getId();
        styleAdapter = args.getStyleAdapter();
        pickAdapter = args.getPickAdapter();
        categoryAdapter = args.getCategoryAdapter();
        sortAdapter = args.getSortAdapter();
        brandRequest = new BrandRequest();
    }

    @Override public void onCreate() {
        view.setupStyleRecyclerView();
        view.setupPickRecyclerView();
        view.setupCategoryRecyclerView();
        view.setupSortRecyclerView();

        reqBrand();
    }

    @Override public void onCategoryItemClick(int position) {
        categoryAdapter.get(selectedPosition).setSelect(false);
        selectedPosition = position;
        categoryAdapter.get(selectedPosition).setSelect(true);
        view.categoryRefresh();
        //setupProductList();
    }

    @Override public void onSortClick() {
        if (isSortVisible) {
            view.hideSortPanel();
        } else {
            view.showSortPanel();
        }
        isSortVisible = !isSortVisible;
    }

    @Override public void onSortSelected(SortDataModel data) {
        view.setupSortText(data.getName());
    }

    private void reqBrand() {
        addDisposable(
            brandRequest.getBrand(id)
                .compose(Transformer.applySchedulers())
                .subscribe(this::resBrand, Timber::e)
        );
    }

    private void resBrand(BrandDetailDataModel data) {

        BrandDataModel brandData = data.getBrand();

        view.setupThumb(brandData.getUrl_thumb());
        view.setupLogo(brandData.getUrl_logo());
        view.setupScrapCount(StringUtil.toDigit(brandData.getScrap_cnt()));
        view.setupName(brandData.getName());
        view.setupTag(brandData.getTag());
        view.setupDesc(brandData.getDesc());
        styleAdapter.set(brandData.getImages());
        view.styleRefresh();
        categoryAdapter.set(parseToCategoryList(data.getCate_list()));
        view.categoryRefresh();
        sortAdapter.set(data.getSort_list());
        view.sortRefresh();
        view.setupSortText(sortAdapter.get(0).getName());

    }

    private List<CategoryDataModel> parseToCategoryList(List<CategoryDataModel> items) {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setPosition(i);
            items.get(i).setSelect(i == 0);

            // test
            items.get(i).setCount(i);
        }
        return items;
    }
}