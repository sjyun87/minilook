package com.minilook.minilook.ui.order_detail.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.data.code.OrderStatus;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.order_detail.viewholder.status.CancelCompletedVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.CancelVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.CannotExchangeVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.CannotReturnVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.DeliveryCompletedVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.DeliveryVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.ExchangeCompletedVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.ExchangeVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.OrderCompletedVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.PreorderVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.PrepareDeliveryVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.PurchaseConfirmVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.ReturnCompletedVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.ReturnVH;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailGoodsAdapter extends RecyclerView.Adapter<_BaseViewHolder<OrderProductDataModel>>
    implements BaseAdapterDataModel<OrderProductDataModel>, BaseAdapterDataView<OrderProductDataModel> {

    private List<OrderProductDataModel> items = new ArrayList<>();

    @NonNull @Override
    public _BaseViewHolder<OrderProductDataModel> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (OrderStatus.toStatus(viewType)) {
            case ORDER_COMPLETED:
                return new OrderCompletedVH(parent);
            case PREPARE_DELIVERY:
                return new PrepareDeliveryVH(parent);
            case DELIVERY:
                return new DeliveryVH(parent);
            case DELIVERY_COMPLETED:
                return new DeliveryCompletedVH(parent);
            case RETURN_COMPLETED:
                return new ReturnCompletedVH(parent);
            case EXCHANGE_COMPLETED:
                return new ExchangeCompletedVH(parent);
            case CANCEL_COMPLETED:
                return new CancelCompletedVH(parent);
            case RETURN:
                return new ReturnVH(parent);
            case EXCHANGE:
                return new ExchangeVH(parent);
            case CANNOT_RETURN:
                return new CannotReturnVH(parent);
            case CANNOT_EXCHANGE:
                return new CannotExchangeVH(parent);
            case PURCHASE_CONFIRM:
                return new PurchaseConfirmVH(parent);
            case CANCEL:
                return new CancelVH(parent);
            case PREORDER:
                return new PreorderVH(parent);
            default:
                return new _BaseViewHolder<>(parent);
        }
    }

    @Override public void onBindViewHolder(@NonNull _BaseViewHolder<OrderProductDataModel> holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemViewType(int position) {
        return items.get(position).getStatusCode();
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(OrderProductDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, OrderProductDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<OrderProductDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, OrderProductDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<OrderProductDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public OrderProductDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<OrderProductDataModel> get() {
        return items;
    }

    @Override public int get(OrderProductDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(OrderProductDataModel $item) {
        items.remove($item);
    }

    @Override public void removeAll() {
        items.clear();
    }

    @Override public void clear() {
        items.clear();
    }

    @Override public int getSize() {
        return items.size();
    }

    @Override public void refresh() {
        notifyDataSetChanged();
    }

    @Override public void refresh(int $position) {
        notifyItemChanged($position);
    }

    @Override public void refresh(int $start, int $row) {
        notifyItemRangeInserted($start, $row);
    }
}
