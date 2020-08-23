package com.minilook.minilook.ui.brand_detail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.common.SortDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.network.brand.BrandRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.brand_detail.di.BrandDetailArguments;
import com.minilook.minilook.util.StringUtil;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class BrandDetailPresenterImpl extends BasePresenterImpl implements BrandDetailPresenter {

    private final View view;
    private final int id;
    private final BaseAdapterDataModel<String> styleAdapter;
    private final BaseAdapterDataModel<SortDataModel> sortAdapter;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;
    private final BrandRequest brandRequest;

    private Gson gson = new Gson();
    private boolean isSortVisible = false;

    public BrandDetailPresenterImpl(BrandDetailArguments args) {
        view = args.getView();
        id = args.getId();
        styleAdapter = args.getStyleAdapter();
        sortAdapter = args.getSortAdapter();
        productAdapter = args.getProductAdapter();
        brandRequest = new BrandRequest();
    }

    @Override public void onCreate() {
        view.setupScrollView();
        view.setupStyleRecyclerView();
        view.setupSortRecyclerView();
        view.setupProductRecyclerView();
        reqBrand();

        //List<SortDataModel> sortList = new ArrayList<>();
        //SortDataModel model1 = new SortDataModel();
        //model1.setName("최신순");
        //sortList.add(model1);
        //
        //SortDataModel model2 = new SortDataModel();
        //model2.setName("가격 낮은순");
        //sortList.add(model2);
        //
        //SortDataModel model3 = new SortDataModel();
        //model3.setName("가격 높은순");
        //sortList.add(model3);
        //
        //sortAdapter.set(sortList);
        //view.sortRefresh();
        //
        //
        //addProductData();

    }

    private void addProductData() {
        List<ProductDataModel> productList = new ArrayList<>();
        ProductDataModel productDataModel1 = new ProductDataModel();
        productDataModel1.setBrand_name("가가가가가");
        productDataModel1.setProduct_name("가가가가가가가각");
        productDataModel1.setDiscount(true);
        productDataModel1.setPrice_discount_percent(10);
        productDataModel1.setPrice(30000);
        productList.add(productDataModel1);

        ProductDataModel productDataModel2 = new ProductDataModel();
        productDataModel2.setBrand_name("가가가가가");
        productDataModel2.setProduct_name("가가가가가가가각");
        productDataModel2.setDiscount(true);
        productDataModel2.setPrice_discount_percent(10);
        productDataModel2.setPrice(30000);
        productList.add(productDataModel2);

        ProductDataModel productDataModel3 = new ProductDataModel();
        productDataModel3.setBrand_name("가가가가가");
        productDataModel3.setProduct_name("가가가가가가가각");
        productDataModel3.setDiscount(true);
        productDataModel3.setPrice_discount_percent(10);
        productDataModel3.setPrice(30000);
        productList.add(productDataModel3);

        ProductDataModel productDataModel4 = new ProductDataModel();
        productDataModel4.setBrand_name("가가가가가");
        productDataModel4.setProduct_name("가가가가가가가각");
        productDataModel4.setDiscount(true);
        productDataModel4.setPrice_discount_percent(10);
        productDataModel4.setPrice(30000);
        productList.add(productDataModel4);

        ProductDataModel productDataModel5 = new ProductDataModel();
        productDataModel5.setBrand_name("가가가가가");
        productDataModel5.setProduct_name("가가가가가가가각");
        productDataModel5.setDiscount(true);
        productDataModel5.setPrice_discount_percent(10);
        productDataModel5.setPrice(30000);
        productList.add(productDataModel5);

        ProductDataModel productDataModel6 = new ProductDataModel();
        productDataModel6.setBrand_name("가가가가가");
        productDataModel6.setProduct_name("가가가가가가가각");
        productDataModel6.setDiscount(true);
        productDataModel6.setPrice_discount_percent(10);
        productDataModel6.setPrice(30000);
        productList.add(productDataModel6);

        ProductDataModel productDataModel7 = new ProductDataModel();
        productDataModel7.setBrand_name("가가가가가");
        productDataModel7.setProduct_name("가가가가가가가각");
        productDataModel7.setDiscount(true);
        productDataModel7.setPrice_discount_percent(10);
        productDataModel7.setPrice(30000);
        productList.add(productDataModel7);

        ProductDataModel productDataModel8 = new ProductDataModel();
        productDataModel8.setBrand_name("가가가가가");
        productDataModel8.setProduct_name("가가가가가가가각");
        productDataModel8.setDiscount(true);
        productDataModel8.setPrice_discount_percent(10);
        productDataModel8.setPrice(30000);
        productList.add(productDataModel8);

        ProductDataModel productDataModel9 = new ProductDataModel();
        productDataModel9.setBrand_name("가가가가가");
        productDataModel9.setProduct_name("가가가가가가가각");
        productDataModel9.setDiscount(true);
        productDataModel9.setPrice_discount_percent(10);
        productDataModel9.setPrice(30000);
        productList.add(productDataModel9);

        ProductDataModel productDataModel10 = new ProductDataModel();
        productDataModel10.setBrand_name("가가가가가");
        productDataModel10.setProduct_name("가가가가가가가각");
        productDataModel10.setDiscount(true);
        productDataModel10.setPrice_discount_percent(10);
        productDataModel10.setPrice(30000);
        productList.add(productDataModel10);

        productAdapter.addAll(productList);
        view.productRefresh();
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

    @Override public void onLoadMore() {
        //addProductData();
    }

    private void reqBrand() {
        addDisposable(
            brandRequest.getBrand(id)
                .map(data -> gson.fromJson(data.getData(), BrandDataModel.class))
                .compose(Transformer.applySchedulers())
                .subscribe(this::resBrand, Timber::e)
        );
    }

    private void resBrand(BrandDataModel data) {
        view.setupThumb(data.getImage_url());
        view.setupLogo(data.getBrand_logo());
        view.setupScrapCount(StringUtil.toDigit(data.getScrap_cnt()));
        view.setupName(data.getBrand_name());
        //view.setupTag(data.getTag());
        view.setupDesc(data.getBrand_desc());
        styleAdapter.set(data.getStyle_images());
        view.styleRefresh();

        sortAdapter.set(data.getSorts());
        view.sortRefresh();
        view.setupSortText(sortAdapter.get(0).getName());
    }
}