package com.minilook.minilook.ui.order_detail.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.order.OrderGoodsDataModel;
import com.minilook.minilook.data.type.OrderStatus;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.order_detail.viewholder.status.CancelCompletedVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.CancelVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.CannotExchangeVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.CannotReturnVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.DeliveryCompletedVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.DeliveryVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.ExchangeCompletedVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.ExchangeVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.OrderCompletedVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.PrepareDeliveryVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.PurchaseConfirmVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.ReturnCompletedVH;
import com.minilook.minilook.ui.order_detail.viewholder.status.ReturnVH;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailGoodsAdapter extends RecyclerView.Adapter<BaseViewHolder<OrderGoodsDataModel>>
    implements BaseAdapterDataModel<OrderGoodsDataModel>, BaseAdapterDataView<OrderGoodsDataModel> {

    private List<OrderGoodsDataModel> items = new ArrayList<>();

    @NonNull @Override
    public BaseViewHolder<OrderGoodsDataModel> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
            default:
                return new BaseViewHolder<>(parent);
        }
    }

    @Override public void onBindViewHolder(@NonNull BaseViewHolder<OrderGoodsDataModel> holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemViewType(int position) {
        return items.get(position).getStatusCode();
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(OrderGoodsDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, OrderGoodsDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<OrderGoodsDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, OrderGoodsDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<OrderGoodsDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public OrderGoodsDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<OrderGoodsDataModel> get() {
        return this.items;
    }

    @Override public int get(OrderGoodsDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(OrderGoodsDataModel $item) {
        this.items.remove($item);
    }

    @Override public void removeAll() {
        this.items.clear();
    }

    @Override public void clear() {
        this.items.clear();
    }

    @Override public int getSize() {
        return this.items.size();
    }

    @Override public void refresh() {
        notifyDataSetChanged();
    }

    @Override public void refresh(int $position) {
        notifyItemChanged($position);
    }

    @Override public void refresh(int $start, int $row) {
        notifyItemRangeChanged($start, $row);
    }
}
