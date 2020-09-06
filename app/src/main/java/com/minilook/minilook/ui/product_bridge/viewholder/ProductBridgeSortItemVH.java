package com.minilook.minilook.ui.product_bridge.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.SortDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import lombok.Setter;

public class ProductBridgeSortItemVH extends BaseViewHolder<SortDataModel> {

    @BindView(R.id.txt_sort) TextView sortTextView;

    @Setter private OnSortSelectListener onSortSelectListener;

    public ProductBridgeSortItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_brand_detail_sort, (ViewGroup) itemView, false));
    }

    @Override public void bind(SortDataModel $data) {
        super.bind($data);

        sortTextView.setText(data.getName());

        itemView.setOnClickListener(this::onItemClick);
    }

    private void onItemClick(View view) {
        if (onSortSelectListener != null) onSortSelectListener.onSortSelected(data);
    }

    public interface OnSortSelectListener {
        void onSortSelected(SortDataModel data);
    }
}
