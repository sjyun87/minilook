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
import com.minilook.minilook.ui.product.ProductWide32VH;
import com.minilook.minilook.ui.product.ProductWideVH;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Setter;
import timber.log.Timber;

public class ProductAdapter extends RecyclerView.Adapter<BaseViewHolder<ProductDataModel>> implements
    BaseAdapterDataModel<ProductDataModel>, BaseAdapterDataView<ProductDataModel> {

    public static final int VIEW_TYPE_FEED = 0;
    public static final int VIEW_TYPE_GRID = 1;
    public static final int VIEW_TYPE_FULL = 2;
    public static final int VIEW_TYPE_SIZE_84 = 3;
    public static final int VIEW_TYPE_IMAGE = 4;
    public static final int VIEW_TYPE_WIDE = 5;
    public static final int VIEW_TYPE_WIDE_32 = 6;

    @Setter private int viewType = 0;
    @Setter private boolean isShowScrap = true;
    @Setter private boolean isShowBrand = true;
    @Setter private ProductWideVH.OnDeleteClickListener onDeleteClickListener;

    private Map<Integer, BaseViewHolder<ProductDataModel>> viewHolders = new HashMap<>();
    private List<ProductDataModel> items = new ArrayList<>();

    @NonNull @Override
    public BaseViewHolder<ProductDataModel> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_FEED:
                return new ProductFeedVH(parent);
            case VIEW_TYPE_GRID:
                ProductGridVH productGridVH = new ProductGridVH(parent);
                productGridVH.setShowScrap(isShowScrap);
                productGridVH.setShowBrand(isShowBrand);
                return productGridVH;
            case VIEW_TYPE_IMAGE:
                return new ProductImageVH(parent);
            case VIEW_TYPE_FULL:
                return new ProductFullVH(parent);
            case VIEW_TYPE_SIZE_84:
                ProductSize84VH productSize84VH = new ProductSize84VH(parent);
                productSize84VH.setShowScrap(isShowScrap);
                productSize84VH.setShowBrand(isShowBrand);
                return productSize84VH;
            case VIEW_TYPE_WIDE:
                ProductWideVH productWideVH = new ProductWideVH(parent);
                productWideVH.setListener(onDeleteClickListener);
                return productWideVH;
            case VIEW_TYPE_WIDE_32:
                return new ProductWide32VH(parent);
            default:
                Timber.e("Product view type is null..");
                return new BaseViewHolder<>(parent);
        }
    }

    @Override public void onBindViewHolder(@NonNull BaseViewHolder<ProductDataModel> holder, int position) {
        holder.bind(items.get(position));
        viewHolders.put(position, holder);
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

    @Override public int get(ProductDataModel $item) {
        return items.indexOf($item);
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
