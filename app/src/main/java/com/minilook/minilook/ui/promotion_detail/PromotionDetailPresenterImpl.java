package com.minilook.minilook.ui.promotion_detail;

import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.promotion.PromotionDataModel;
import com.minilook.minilook.data.network.promotion.PromotionRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.promotion_detail.di.PromotionDetailArguments;

import timber.log.Timber;

public class PromotionDetailPresenterImpl extends BasePresenterImpl implements PromotionDetailPresenter {

    private final View view;
    private final int promotionId;
    private final BaseAdapterDataModel<ProductDataModel> adapter;
    private final PromotionRequest promotionRequest;

    public PromotionDetailPresenterImpl(PromotionDetailArguments args) {
        view = args.getView();
        promotionId = args.getPromotionId();
        adapter = args.getAdapter();
        promotionRequest = new PromotionRequest();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
        //reqPromotion();
    }

    private void reqPromotion() {
        addDisposable(
            promotionRequest.getPromotion(promotionId)
                .compose(Transformer.applySchedulers())
                .subscribe(this::resPromotion, Timber::e)
        );
    }

    private void resPromotion(PromotionDataModel data) {
        view.setupBgImage(data.getUrl_image());
        view.setupDesc(data.getDesc());
        view.setupTitle(data.getTitle());

        //adapter.set(data.getProducts());
        //view.refresh();
    }
}