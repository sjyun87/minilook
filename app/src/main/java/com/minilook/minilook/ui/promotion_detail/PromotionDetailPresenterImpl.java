package com.minilook.minilook.ui.promotion_detail;

import android.net.Uri;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.promotion.PromotionDataModel;
import com.minilook.minilook.data.network.promotion.PromotionRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.promotion_detail.di.PromotionDetailArguments;
import com.minilook.minilook.util.DynamicLinkManager;
import com.minilook.minilook.util.TrackingManager;
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
    private final DynamicLinkManager dynamicLinkManager;

    private Gson gson = new Gson();
    private PromotionDataModel data;

    private int latestPromotionId = -1;

    public PromotionDetailPresenterImpl(PromotionDetailArguments args) {
        view = args.getView();
        promotionNo = args.getPromotionId();
        productAdapter = args.getProductAdapter();
        promotionAdapter = args.getPromotionAdapter();
        dynamicLinkManager = args.getDynamicLinkManager();
        promotionRequest = new PromotionRequest();
    }

    @Override public void onCreate() {
        view.setupProductRecyclerView();
        view.setupPromotionRecyclerView();

        reqPromotionDetail();
        reqTogetherPromotion();
    }

    @Override public void onResume() {
        TrackingManager.pageTracking("기획전 상세페이지", PromotionDetailActivity.class.getSimpleName());
    }

    @Override public void onLoadMore() {
        reqTogetherPromotion();
    }

    @Override public void onShareClick() {
        dynamicLinkManager.createShareLink(DynamicLinkManager.TYPE_PROMOTION, promotionNo, data.getTitle(),
            data.getImageUrl(),
            new DynamicLinkManager.OnCompletedListener() {
                @Override public void onSuccess(Uri uri) {
                    view.sendLink(uri.toString());
                }

                @Override public void onFail() {
                    view.showErrorMessage();
                }
            });
    }

    private void reqPromotionDetail() {
        addDisposable(promotionRequest.getPromotionDetail(promotionNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map(data -> gson.fromJson(data.getData(), PromotionDataModel.class))
            .subscribe(this::resPromotion, Timber::e)
        );
    }

    private void resPromotion(PromotionDataModel data) {
        this.data = data;

        view.setupThumb(data.getThumbUrl());
        view.setupEventImage(data.getEventUrl());
        if (data.getProducts() != null && data.getProducts().size() > 0) {
            productAdapter.set(data.getProducts());
            view.productRefresh();
            view.setupTotal(productAdapter.getSize());
        }
    }

    private void reqTogetherPromotion() {
        addDisposable(promotionRequest.getPromotions(promotionNo, ROWS, latestPromotionId)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map((Function<BaseDataModel, List<PromotionDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<PromotionDataModel>>() {
                }.getType()))
            .subscribe(this::resTogetherPromotion, Timber::e));
    }

    private void resTogetherPromotion(List<PromotionDataModel> data) {
        latestPromotionId = data.get(data.size() - 1).getPromotionNo();
        int start = promotionAdapter.getSize();
        promotionAdapter.addAll(data);
        view.promotionRefresh(start, data.size());
    }
}