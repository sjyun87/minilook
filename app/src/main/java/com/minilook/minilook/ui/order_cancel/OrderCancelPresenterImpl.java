package com.minilook.minilook.ui.order_cancel;

import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.order.OrderCancelDataModel;
import com.minilook.minilook.data.model.order.OrderGoodsDataModel;
import com.minilook.minilook.data.network.order.OrderRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.order_cancel.di.OrderCancelArguments;
import com.minilook.minilook.ui.order_detail.OrderDetailPresenterImpl;
import timber.log.Timber;

public class OrderCancelPresenterImpl extends BasePresenterImpl implements OrderCancelPresenter {

    private final View view;
    private final OrderCancelDataModel orderData;
    private final BaseAdapterDataModel<OrderGoodsDataModel> adapter;
    private final OrderRequest orderRequest;

    public OrderCancelPresenterImpl(OrderCancelArguments args) {
        view = args.getView();
        orderData = args.getOrderData();
        adapter = args.getAdapter();
        orderRequest = new OrderRequest();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();

        view.setOrderNo(orderData.getOrderNo());
        view.setOrderDate(orderData.getOrderDate());

        adapter.set(orderData.getGoods());
        view.refresh();
    }

    @Override public void onApplyClick() {
        reqOrderCancel();
    }

    private void reqOrderCancel() {
        addDisposable(orderRequest.orderCancel(orderData)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_CANCEL)) {
                    view.showErrorToast(data.getMessage());
                }
                return code.equals(HttpCode.OK);
            })
            .subscribe(this::resOrderAllCancel, Timber::e)
        );
    }

    private void resOrderAllCancel(BaseDataModel dataModel) {
        RxBus.send(new OrderDetailPresenterImpl.RxBusEventStatusRefresh());
        view.showReceiptCompletedToast();
        view.finish();
    }
}