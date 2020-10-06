package com.minilook.minilook.ui.order_history;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.order.OrderHistoryDataModel;
import com.minilook.minilook.data.network.order.OrderRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.order_history.di.OrderHistoryArguments;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class OrderHistoryPresenterImpl extends BasePresenterImpl implements OrderHistoryPresenter {

    private static final int ROWS = 10;

    private final View view;
    private final BaseAdapterDataModel<OrderHistoryDataModel> adapter;
    private final OrderRequest orderRequest;

    private Gson gson = new Gson();

    private long lastOderTime;

    public OrderHistoryPresenterImpl(OrderHistoryArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        orderRequest = new OrderRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupRecyclerView();

        reqOrderHistory();
    }

    private void reqOrderHistory() {
        addDisposable(orderRequest.getOrderHistory(lastOderTime, ROWS)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.showEmptyPanel();
                }
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<OrderHistoryDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<OrderHistoryDataModel>>() {
                }.getType()))
            .subscribe(this::resOrderHistory, Timber::e));
    }

    private void resOrderHistory(List<OrderHistoryDataModel> data) {
        lastOderTime = data.get(data.size() - 1).getRegistDate();

        adapter.set(data);
        view.refresh();
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxBusEventOrderClick) {
                OrderHistoryDataModel data = ((RxBusEventOrderClick) o).getData();
                String orderNo = data.getOrderNo();
                String receiptNo = data.getReceiptNo();
                view.navigateToOrderDetail(orderNo, receiptNo);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxBusEventOrderClick {
        private OrderHistoryDataModel data;
    }
}