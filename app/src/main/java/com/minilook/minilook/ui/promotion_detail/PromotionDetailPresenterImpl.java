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
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class PromotionDetailPresenterImpl extends BasePresenterImpl implements PromotionDetailPresenter {

    private static final int ROWS = 10;

    private final View view;
    private final int promotionId;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;
    private final BaseAdapterDataModel<PromotionDataModel> promotionAdapter;
    private final PromotionRequest promotionRequest;

    private Gson gson = new Gson();

    private int latestPromotionId = -1;

    public PromotionDetailPresenterImpl(PromotionDetailArguments args) {
        view = args.getView();
        promotionId = args.getPromotionId();
        productAdapter = args.getProductAdapter();
        promotionAdapter = args.getPromotionAdapter();
        promotionRequest = new PromotionRequest();
    }

    @Override public void onCreate() {
        view.setupProductRecyclerView();
        view.setupPromotionRecyclerView();

        reqPromotionDetail();
        reqTogetherPromotion();
    }

    @Override public void onLoadMore() {
        reqTogetherPromotion();
    }

    private void reqPromotionDetail() {
        addDisposable(promotionRequest.getPromotionDetail(promotionId)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map(data -> gson.fromJson(data.getData(), PromotionDataModel.class))
            .subscribe(this::resPromotion, Timber::e)
        );
    }

    private void resPromotion(PromotionDataModel data) {
        view.setupThumb(data.getThumb_url());
        view.setupEventImage(data.getEvent_url());
        productAdapter.set(data.getProducts());
        view.productRefresh();
        view.setupTotal(productAdapter.getSize());
    }

    private void reqTogetherPromotion() {
        addDisposable(promotionRequest.getPromotions(promotionId, ROWS, latestPromotionId)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map((Function<BaseDataModel, List<PromotionDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<PromotionDataModel>>() {
                }.getType()))
            .subscribe(this::resTogetherPromotion, Timber::e));
    }

    private void resTogetherPromotion(List<PromotionDataModel> data) {
        latestPromotionId = data.get(data.size() - 1).getPromotion_id();
        int start = promotionAdapter.getSize();
        promotionAdapter.addAll(data);
        view.promotionRefresh(start, data.size());
    }
}