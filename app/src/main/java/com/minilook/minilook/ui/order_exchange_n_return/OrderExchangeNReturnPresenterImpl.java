package com.minilook.minilook.ui.order_exchange_n_return;

import com.google.gson.Gson;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.order.ExchangeNReturnDataModel;
import com.minilook.minilook.data.model.order.OrderGoodsDataModel;
import com.minilook.minilook.data.network.order.OrderRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.order_detail.OrderDetailPresenterImpl;
import com.minilook.minilook.ui.order_exchange_n_return.di.OrderExchangeNReturnArguments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class OrderExchangeNReturnPresenterImpl extends BasePresenterImpl implements OrderExchangeNReturnPresenter {

    private final View view;
    private final OrderGoodsDataModel data;
    private final BaseAdapterDataModel<CodeDataModel> typeAdapter;
    private final BaseAdapterDataModel<CodeDataModel> reasonAdapter;
    private final OrderRequest orderRequest;

    private final Gson gson = new Gson();

    private boolean isTypeBoxOpened = false;
    private boolean isReasonBoxOpened = false;

    private CodeDataModel selectedType;
    private CodeDataModel selectedReason;
    private String reasonDetail;

    public OrderExchangeNReturnPresenterImpl(OrderExchangeNReturnArguments args) {
        view = args.getView();
        data = args.getData();
        typeAdapter = args.getTypeAdapter();
        reasonAdapter = args.getReasonAdapter();
        orderRequest = new OrderRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupTypeRecyclerView();
        view.setupReasonRecyclerView();
        view.setupReasonDetailEditText();

        view.setThumbImage(data.getThumbUrl());
        view.setProductName(data.getName());
        view.setOption(data.getColorName(), data.getSizeName());
        view.setProductPrice(data.getProductPrice());

        reqExchangeNReturnCode();
    }

    @Override public void onTypeBoxClick() {
        handleTypeBox();
    }

    @Override public void onReasonBoxClick() {
        handleReasonBox();
    }

    @Override public void onReasonDetailTextChanged(String text) {
        reasonDetail = text;
    }

    @Override public void onApplyClick() {
        reqExchangeNReturn();
    }

    private void reqExchangeNReturnCode() {
        addDisposable(orderRequest.getExchangeNReturnCode()
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map(data -> gson.fromJson(data.getData(), ExchangeNReturnDataModel.class))
            .subscribe(this::resExchangeNReturnCode, Timber::e));
    }

    private void resExchangeNReturnCode(ExchangeNReturnDataModel data) {
        typeAdapter.set(data.getTypes());
        view.typeRefresh();
        reasonAdapter.set(data.getReasons());
        view.reasonRefresh();
    }

    private void handleTypeBox() {
        isTypeBoxOpened = !isTypeBoxOpened;
        if (isTypeBoxOpened) {
            view.showTypeBox();
        } else {
            view.hideTypeBox();
            if (selectedType != null) {
                view.setSelectedType(selectedType.getCodeName());
            } else {
                view.setTypeHint();
            }
        }
    }

    private void handleReasonBox() {
        isReasonBoxOpened = !isReasonBoxOpened;
        if (isReasonBoxOpened) {
            view.showReasonBox();
        } else {
            view.hideReasonBox();
            if (selectedReason != null) {
                view.selectedReason(selectedReason.getCodeName());
            } else {
                view.setReasonHint();
            }
        }
    }

    private void checkEnableAppyButton() {
        if (selectedType != null && selectedReason != null) {
            view.enableApplyButton();
        }
    }

    private void reqExchangeNReturn() {
        addDisposable(
            orderRequest.exchangeNReturn(data.getOrderOptionNo(), selectedType.getCode(), selectedReason.getCode(),
                reasonDetail)
                .compose(Transformer.applySchedulers())
                .filter(data -> data.getCode().equals(HttpCode.OK))
                .subscribe(this::resExchangeNReturn, Timber::e));
    }

    private void resExchangeNReturn(BaseDataModel dataModel) {
        RxBus.send(new OrderDetailPresenterImpl.RxBusEventStatusRefresh());
        view.showReceiptCompleteToast();
        view.finish();
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxEventSelectedType) {
                selectedType = ((RxEventSelectedType) o).getTypeData();
                handleTypeBox();
                checkEnableAppyButton();
            } else if (o instanceof RxEventSelectedReason) {
                selectedReason = ((RxEventSelectedReason) o).getReasonData();
                handleReasonBox();
                checkEnableAppyButton();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventSelectedType {
        CodeDataModel typeData;
    }

    @AllArgsConstructor @Getter public final static class RxEventSelectedReason {
        CodeDataModel reasonData;
    }
}