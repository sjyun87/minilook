package com.minilook.minilook.ui.promotion_detail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.promotion.PromotionDataModel;
import com.minilook.minilook.data.network.promotion.PromotionRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.promotion_detail.di.PromotionDetailArguments;
import com.minilook.minilook.util.DynamicLinkUtil;
import com.minilook.minilook.util.TrackingUtil;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class PromotionDetailPresenterImpl extends BasePresenterImpl implements PromotionDetailPresenter {

    private static final int ROWS = 10;

    private final View view;
    private final int promotionNo;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;
    private final BaseAdapterDataModel<PromotionDataModel> promotionAdapter;
    private final PromotionRequest promotionRequest;
    private final DynamicLinkUtil dynamicLinkUtil;
    private final Gson gson;

    private PromotionDataModel data;
    private int latestPromotionNo = -1;

    public PromotionDetailPresenterImpl(PromotionDetailArguments args) {
        view = args.getView();
        promotionNo = args.getPromotionId();
        productAdapter = args.getProductAdapter();
        promotionAdapter = args.getPromotionAdapter();
        promotionRequest = new PromotionRequest();
        dynamicLinkUtil = new DynamicLinkUtil();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {
        view.setupClickAction();
        view.setupProductRecyclerView();
        view.setupPromotionRecyclerView();

        getPromotionDetail();
        getOtherPromotion();
    }

    @Override public void onResume() {
        TrackingUtil.pageTracking("기획전 상세페이지", PromotionDetailActivity.class.getSimpleName());
    }

    @Override public void onDestroy() {
        view.clear();
    }

    @Override public void onLoadMore() {
        if (promotionAdapter.getSize() >= ROWS) getMoreOtherPromotion();
    }

    @Override public void onProductScrap(ProductDataModel data) {
        replaceData(data);
    }

    @Override public void onShareClick() {
        dynamicLinkUtil.createLink(DynamicLinkUtil.TYPE_PROMOTION, promotionNo, data.getTitle(), data.getThumbUrl(),
            new DynamicLinkUtil.OnDynamicLinkListener() {
                @Override public void onSuccess(String link) {
                    view.sendDynamicLink(link);
                }

                @Override public void onError() {
                    view.showErrorDialog();
                }
            });
    }

    private void getPromotionDetail() {
        addDisposable(promotionRequest.getPromotionDetail(promotionNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (!code.equals(HttpCode.OK)) {
                    view.showErrorDialog();
                }
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), PromotionDataModel.class))
            .subscribe(this::onResPromotionDetail, Timber::e)
        );
    }

    private void onResPromotionDetail(PromotionDataModel data) {
        this.data = data;

        view.setThumb(data.getThumbUrl());
        view.setEventImage(data.getEventUrl());

        productAdapter.set(data.getProducts());
        view.productRefresh();
        view.setTotal(productAdapter.getSize());
    }

    private void getOtherPromotion() {
        addDisposable(promotionRequest.getPromotions(promotionNo, ROWS)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.hideOtherPromotions();
                } else if (!code.equals(HttpCode.OK)) {
                    view.showErrorDialog();
                }
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<PromotionDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<PromotionDataModel>>() {
                }.getType()))
            .subscribe(this::onResOtherPromotions, Timber::e));
    }

    private void onResOtherPromotions(List<PromotionDataModel> data) {
        latestPromotionNo = data.get(data.size() - 1).getPromotionNo();
        promotionAdapter.set(data);
        view.promotionRefresh();
    }

    private void getMoreOtherPromotion() {
        addDisposable(promotionRequest.getPromotions(promotionNo, ROWS, latestPromotionNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<PromotionDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<PromotionDataModel>>() {
                }.getType()))
            .subscribe(this::onResMoreOtherPromotions, Timber::e));
    }

    private void onResMoreOtherPromotions(List<PromotionDataModel> data) {
        latestPromotionNo = data.get(data.size() - 1).getPromotionNo();
        int start = promotionAdapter.getSize();
        promotionAdapter.addAll(data);
        view.promotionRefresh(start, data.size());
    }

    private void replaceData(ProductDataModel data) {
        for (ProductDataModel product : productAdapter.get()) {
            if (product.getProductNo() == data.getProductNo()) {
                product.setScrap(data.isScrap());
                product.setScrapCount(data.getScrapCount());
            }
        }
        view.productRefresh();
    }
}