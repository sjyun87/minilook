package com.minilook.minilook.ui.preorder_detail;

import android.net.Uri;
import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.code.PreorderType;
import com.minilook.minilook.data.code.ShippingCode;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.data.model.product.OptionDataModel;
import com.minilook.minilook.data.model.product.OptionProductDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingProductDataModel;
import com.minilook.minilook.data.network.preorder.PreorderRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.preorder_detail.di.PreorderDetailArguments;
import com.minilook.minilook.ui.preorder_detail.viewholder.PreorderDetailProductVH;
import com.minilook.minilook.util.DynamicLinkManager;
import com.minilook.minilook.util.TrackingManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import timber.log.Timber;

public class PreorderDetailPresenterImpl extends BasePresenterImpl implements PreorderDetailPresenter {

    private final View view;
    private final int preorderNo;
    private final BaseAdapterDataModel<String> imageAdapter;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;
    private final DynamicLinkManager dynamicLinkManager;

    private final PreorderRequest preorderRequest;

    private Gson gson = new Gson();
    private PreorderDataModel data;

    public PreorderDetailPresenterImpl(PreorderDetailArguments args) {
        view = args.getView();
        preorderNo = args.getPreorderNo();
        imageAdapter = args.getImageAdapter();
        productAdapter = args.getProductAdapter();
        dynamicLinkManager = args.getDynamicLinkManager();
        preorderRequest = new PreorderRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupViewPager();
        view.setupTabLayout();
        view.setupRecyclerView();

        reqPreorder();
    }

    @Override public void onResume() {
        TrackingManager.pageTracking("프리오더 상세페이지", PreorderDetailActivity.class.getSimpleName());
    }

    @Override public void onTabClick(int position) {
        switch (position) {
            case 0:
                view.scrollToPreorderInfo();
                break;
            case 1:
                view.scrollToProduct();
                break;
            case 2:
                view.scrollToShippingNRefund();
                break;
        }
    }

    @Override public void onBuyClick() {
        if (App.getInstance().isLogin()) {
            view.showOptionSelector();
            reqPreorderOptions();
        } else {
            view.navigateToLogin();
        }
    }

    @Override public void onOptionSelectorBuyClick(List<ShoppingProductDataModel> shoppingProductData) {
        List<ShoppingBrandDataModel> brandData = parseToData(shoppingProductData);
        view.navigateToOrder(brandData);
    }

    @Override public void onShareClick() {
        String title = data.getTitle() + " (" + parseToDate(data.getStartDate()) + "~" + parseToDate(data.getEndDate()) + ")";
        dynamicLinkManager.createShareLink(DynamicLinkManager.TYPE_PREORDER, preorderNo, data.getTitle(),
            data.getImages().get(0),
            new DynamicLinkManager.OnCompletedListener() {
                @Override public void onSuccess(Uri uri) {
                    view.sendLink(uri.toString());
                }

                @Override public void onFail() {
                    view.showErrorMessage();
                }
            });
    }

    private String parseToDate(long date) {
        Date endDate = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd", Locale.KOREA);
        return format.format(endDate);
    }

    private List<ShoppingBrandDataModel> parseToData(List<ShoppingProductDataModel> shoppingProductData) {
        List<ShoppingBrandDataModel> brandData = new ArrayList<>();
        ShoppingBrandDataModel brandModel = new ShoppingBrandDataModel();
        brandModel.setBrandNo(data.getBrandNo());
        brandModel.setBrandName(data.getBrandName());
        brandModel.setShippingType(ShippingCode.FREE.getValue());
        brandModel.setProducts(shoppingProductData);
        brandData.add(brandModel);
        return brandData;
    }

    private void reqPreorder() {
        addDisposable(preorderRequest.getPreorder(preorderNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), PreorderDataModel.class))
            .subscribe(this::resPreorder, Timber::e)
        );
    }

    private void resPreorder(PreorderDataModel data) {
        this.data = data;
        int status = data.getStatus();

        if (status == PreorderType.ING.getValue()) {
            view.setEnableLabelBackground();
            view.enableBuyButton();
        } else {
            view.setDisableLabelBackground();
            view.disableBuyButton(data.getStatusName());
            view.showCloseTextView();
        }
        view.setLabel(data.getStatusName());

        imageAdapter.set(checkValid(data.getImages()));
        view.imageRefresh();

        view.setBrandName(data.getBrandName());
        view.setTitle(data.getTitle());

        view.setRemainDate(getRemainDate(data.getEndDate()));
        view.setTermDate(getTermDate(data.getStartDate(), data.getEndDate()));
        view.setDeliveryDate(getDeliveryDate(data.getDeliveryDate()));

        view.setDetailImage(data.getDetailUrl());

        productAdapter.set(data.getProducts());
        view.productRefresh();
    }

    private List<String> checkValid(List<String> images) {
        List<String> items = new ArrayList<>();
        for (String url : images) {
            if (url != null && !url.equals("")) items.add(url);
        }
        return items;
    }

    private int getRemainDate(long endDate) {
        long todayDay = Calendar.getInstance().getTimeInMillis() / (24 * 60 * 60 * 1000);
        long targetDay = endDate / (24 * 60 * 60 * 1000);
        return (int) Math.abs(targetDay - todayDay) + 1;
    }

    private String getTermDate(long startDate, long endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
        String formatStartData = format.format(new Date(startDate));
        String formatEndData = format.format(new Date(endDate));
        return formatStartData + " ~ " + formatEndData;
    }

    private String getDeliveryDate(long deliveryDate) {
        SimpleDateFormat format = new SimpleDateFormat("MM월 dd일 E요일", Locale.KOREA);
        return format.format(new Date(deliveryDate));
    }

    private void reqPreorderOptions() {
        addDisposable(preorderRequest.getPreorderOptions(preorderNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map(data -> gson.fromJson(data.getData(), OptionDataModel.class))
            .subscribe(this::resPreorderOptions, Timber::e));
    }

    private void resPreorderOptions(OptionDataModel options) {
        List<OptionProductDataModel> productOptions = options.getProducts();
        for (int i = 0; i < productOptions.size(); i++) {
            productOptions.get(i).setPosition(i);
            productOptions.get(i).setBonus(false);
        }
        for (OptionProductDataModel bonusOptionData : options.getBonusProducts()) {
            bonusOptionData.setPosition(-1);
            bonusOptionData.setBonus(true);
        }
        view.setupOptionSelector(options);
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof PreorderDetailProductVH.RxEventPreorderProductClick) {
                String title = ((PreorderDetailProductVH.RxEventPreorderProductClick) o).getTitle();
                int productNo = ((PreorderDetailProductVH.RxEventPreorderProductClick) o).getProductNo();
                view.navigateToPreorderProductDetail(title, preorderNo, productNo);
            }
        }, Timber::e));
    }
}