package com.minilook.minilook.ui.brand_detail;

import com.google.gson.Gson;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.common.SortDataModel;
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
    private final int brand_id;
    private final BaseAdapterDataModel<String> styleAdapter;
    private final BaseAdapterDataModel<SortDataModel> sortAdapter;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;
    private final BrandRequest brandRequest;

    private Gson gson = new Gson();
    private boolean isSortVisible = false;

    private List<ProductDataModel> productList = new ArrayList<>();

    public BrandDetailPresenterImpl(BrandDetailArguments args) {
        view = args.getView();
        brand_id = args.getBrand_id();
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

        addProductData();
    }

    private void addProductData() {
        ProductDataModel productDataModel1 = new ProductDataModel();
        productDataModel1.setBrand_name("리미떼두두");
        productDataModel1.setProduct_name("Luxe point long pants");
        productDataModel1.setDiscount(true);
        productDataModel1.setPrice_discount_percent(20);
        productDataModel1.setPrice(36000);
        productDataModel1.setImage_url(
            "http://lookbook.minilook.co.kr/data/goods/45/medium/" + "aEv7qJH2ggxSd3YNRjKw71b9jrRYRe.png");
        productList.add(productDataModel1);

        ProductDataModel productDataModel2 = new ProductDataModel();
        productDataModel2.setBrand_name("로아앤제인");
        productDataModel2.setProduct_name("에디 맨투맨");
        productDataModel2.setDiscount(false);
        productDataModel2.setPrice_discount_percent(0);
        productDataModel2.setPrice(36800);
        productDataModel2.setImage_url(
            "http://lookbook.minilook.co.kr/data/goods/3/medium/" + "njDBsuXmVpcJTh5PwuSGC261xwZmms.jpg");
        productList.add(productDataModel2);

        ProductDataModel productDataModel3 = new ProductDataModel();
        productDataModel3.setBrand_name("베베베베");
        productDataModel3.setProduct_name("Triplets rabbits sweatshirt");
        productDataModel3.setDiscount(true);
        productDataModel3.setPrice_discount_percent(10);
        productDataModel3.setPrice(29700);
        productDataModel3.setImage_url(
            "http://lookbook.minilook.co.kr/data/goods/6/medium/" + "M2NM5mX75fmdhGJdmQBZ1zmvATc9b9.png");
        productList.add(productDataModel3);

        ProductDataModel productDataModel4 = new ProductDataModel();
        productDataModel4.setBrand_name("런래빗");
        productDataModel4.setProduct_name("Pineapple Dress");
        productDataModel4.setDiscount(false);
        productDataModel4.setPrice_discount_percent(0);
        productDataModel4.setPrice(79000);
        productDataModel4.setImage_url(
            "http://lookbook.minilook.co.kr/data/goods/83/medium/" + "FzX4BWDpsJpY6lefrVkPtlep1FDRtm.jpg");
        productList.add(productDataModel4);

        ProductDataModel productDataModel5 = new ProductDataModel();
        productDataModel5.setBrand_name("젤리멜로");
        productDataModel5.setProduct_name("TOUT LETTERING SWEATSHIRT_Blue_Kids");
        productDataModel5.setDiscount(true);
        productDataModel5.setPrice_discount_percent(10);
        productDataModel5.setPrice(49500);
        productDataModel5.setImage_url(
            "http://lookbook.minilook.co.kr/data/goods/26/medium/" + "MXG1aZwspgSHKthrd8xZGlGK9N7G4q.jpg");
        productList.add(productDataModel5);

        ProductDataModel productDataModel6 = new ProductDataModel();
        productDataModel6.setBrand_name("쥬쥬봉");
        productDataModel6.setProduct_name("STRIPES RABBIT SWEAT SHIRT");
        productDataModel6.setDiscount(true);
        productDataModel6.setPrice_discount_percent(10);
        productDataModel6.setPrice(35100);
        productDataModel6.setImage_url(
            "http://lookbook.minilook.co.kr/data/goods/48/medium/" + "Khy9KYtd9UNHW52HETRNnmxLKas7ft.jpg");
        productList.add(productDataModel6);

        ProductDataModel productDataModel7 = new ProductDataModel();
        productDataModel7.setBrand_name("수아비");
        productDataModel7.setProduct_name("Stripe T : Red");
        productDataModel7.setDiscount(false);
        productDataModel7.setPrice_discount_percent(0);
        productDataModel7.setPrice(21000);
        productDataModel7.setImage_url(
            "http://lookbook.minilook.co.kr/data/goods/55/medium/" + "qKd2SW9E13zaDCNRN96t9LteUBtVVK.png");
        productList.add(productDataModel7);

        ProductDataModel productDataModel8 = new ProductDataModel();
        productDataModel8.setBrand_name("슈슈앤크라");
        productDataModel8.setProduct_name("일루지온 원피스 - 크림");
        productDataModel8.setDiscount(true);
        productDataModel8.setPrice_discount_percent(30);
        productDataModel8.setPrice(57400);
        productDataModel8.setImage_url(
            "http://lookbook.minilook.co.kr/data/goods/57/medium/" + "wlAJW1NVGmkUVDT2SujaPGP8nl2j3U.jpg");
        productList.add(productDataModel8);

        ProductDataModel productDataModel9 = new ProductDataModel();
        productDataModel9.setBrand_name("메르시유");
        productDataModel9.setProduct_name("Basic stripe t-shirt - orange");
        productDataModel9.setDiscount(false);
        productDataModel9.setPrice_discount_percent(0);
        productDataModel9.setPrice(27000);
        productDataModel9.setImage_url(
            "http://lookbook.minilook.co.kr/data/goods/89/medium/" + "ejhgxPykfwhDGJKYfHaBM546luCCpc.jpg");
        productList.add(productDataModel9);

        ProductDataModel productDataModel10 = new ProductDataModel();
        productDataModel10.setBrand_name("미니룩");
        productDataModel10.setProduct_name("[20SUMMER_LIKE A SUMMER BREEZE] Hello Dino T-Shirt");
        productDataModel10.setDiscount(true);
        productDataModel10.setPrice_discount_percent(10);
        productDataModel10.setPrice(22500);
        productDataModel10.setImage_url(
            "http://lookbook.minilook.co.kr/data/goods/106/medium/" + "LGvngzhbuZM538hMhlySAK1CeBf7V8.jpg");
        productList.add(productDataModel10);

        productList.add(productDataModel1);
        productList.add(productDataModel2);
        productList.add(productDataModel3);
        productList.add(productDataModel4);
        productList.add(productDataModel5);
        productList.add(productDataModel6);
        productList.add(productDataModel7);
        productList.add(productDataModel8);
        productList.add(productDataModel9);
        productList.add(productDataModel10);

        int start = productAdapter.getSize();
        productAdapter.addAll(productList);
        view.productRefresh(start, 20);
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
        addProductData();
    }

    @Override public void onBrandInfoClick() {
        view.navigateToBrandInfo(brand_id);
    }

    private void reqBrand() {
        addDisposable(
            brandRequest.getBrand(brand_id)
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