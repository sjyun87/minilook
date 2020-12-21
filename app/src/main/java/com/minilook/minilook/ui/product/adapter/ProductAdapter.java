package com.minilook.minilook.ui.product.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.product.ProductFeedVH;
import com.minilook.minilook.ui.product.ProductFullVH;
import com.minilook.minilook.ui.product.ProductGridVH;
import com.minilook.minilook.ui.product.ProductImageVH;
import com.minilook.minilook.ui.product.ProductSize84VH;
import com.minilook.minilook.ui.product.ProductWide32VH;
import com.minilook.minilook.ui.product.ProductWideVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import timber.log.Timber;

public class ProductAdapter extends RecyclerView.Adapter<_BaseViewHolder<ProductDataModel>> implements
    BaseAdapterDataModel<ProductDataModel>, BaseAdapterDataView<ProductDataModel> {

    public static final int VIEW_TYPE_GRID = 1;
    public static final int VIEW_TYPE_SIZE_84 = 3;
    public static final int VIEW_TYPE_WIDE = 5;
    public static final int VIEW_TYPE_WIDE_32 = 6;

    @Setter private int viewType = 0;
    @Setter private boolean isShowBrand = true;
    @Setter private ProductWideVH.OnDeleteClickListener onDeleteClickListener;

    private final List<ProductDataModel> items = new ArrayList<>();

    @NonNull @Override
    public _BaseViewHolder<ProductDataModel> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_GRID:
                ProductGridVH productGridVH = new ProductGridVH(parent);
                productGridVH.setShowBrand(isShowBrand);
                return productGridVH;
            case VIEW_TYPE_SIZE_84:
                return new ProductSize84VH(parent);
            case VIEW_TYPE_WIDE:
                ProductWideVH productWideVH = new ProductWideVH(parent);
                productWideVH.setListener(onDeleteClickListener);
                return productWideVH;
            case VIEW_TYPE_WIDE_32:
                return new ProductWide32VH(parent);
            default:
                Timber.e("Product view type is null..");
                return new _BaseViewHolder<>(parent);
        }
    }

    @Override public void onBindViewHolder(@NonNull _BaseViewHolder<ProductDataModel> holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemViewType(int position) {
        return viewType;
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(ProductDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, ProductDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<ProductDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, ProductDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<ProductDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public ProductDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<ProductDataModel> get() {
        return items;
    }

    @Override public int get(ProductDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(ProductDataModel $item) {
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
        notifyItemRangeChanged($start, $row);
    }
}
