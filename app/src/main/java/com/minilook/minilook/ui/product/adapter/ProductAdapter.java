package com.minilook.minilook.ui.product.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.product.ProductFeedVH;
import com.minilook.minilook.ui.product.ProductFullVH;
import com.minilook.minilook.ui.product.ProductGridVH;
import com.minilook.minilook.ui.product.ProductImageVH;
import com.minilook.minilook.ui.product.ProductSize84VH;
import com.minilook.minilook.ui.product.ProductNoBrandVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import timber.log.Timber;

public class ProductAdapter extends RecyclerView.Adapter<BaseViewHolder<ProductDataModel>> implements
    BaseAdapterDataModel<ProductDataModel>, BaseAdapterDataView<ProductDataModel> {

    public static final int VIEW_TYPE_FEED = 0;
    public static final int VIEW_TYPE_GRID = 1;
    public static final int VIEW_TYPE_FULL = 2;
    public static final int VIEW_TYPE_SIZE_84 = 3;
    public static final int VIEW_TYPE_IMAGE = 4;

    @Setter private int viewType = 0;
    @Setter private boolean isShowScrap = true;
    @Setter private boolean isShowBrand = true;

    private List<ProductDataModel> items = new ArrayList<>();

    @NonNull @Override
    public BaseViewHolder<ProductDataModel> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_FEED:
                return new ProductFeedVH(parent);
            case VIEW_TYPE_GRID:
                return new ProductGridVH(parent);
            case VIEW_TYPE_IMAGE:
                return new ProductImageVH(parent);
            case VIEW_TYPE_FULL:
                return new ProductFullVH(parent);
            case VIEW_TYPE_SIZE_84:
                return new ProductSize84VH(parent, isShowScrap);
            default:
                Timber.e("Product view type is null..");
                return new BaseViewHolder<>(parent);
        }
    }

    @Override public void onBindViewHolder(@NonNull BaseViewHolder<ProductDataModel> holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemViewType(int position) {
        return viewType;
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(ProductDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, ProductDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<ProductDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, ProductDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<ProductDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public ProductDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<ProductDataModel> get() {
        return this.items;
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(ProductDataModel $item) {
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
