package com.minilook.minilook.ui.product_bridge.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.ui.base._BaseViewHolder;

import butterknife.BindView;
import lombok.Setter;

public class BrandDetailSortItemVH extends _BaseViewHolder<CodeDataModel> {

    @BindView(R.id.txt_sort) TextView sortTextView;

    @Setter private OnSortSelectListener onSortSelectListener;

    public BrandDetailSortItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.view_sort_selector_item, (ViewGroup) itemView, false));
    }

    @Override public void bind(CodeDataModel $data) {
        super.bind($data);

        sortTextView.setText(data.getName());

        itemView.setOnClickListener(this::onItemClick);
    }

    private void onItemClick(View view) {
        if (onSortSelectListener != null) onSortSelectListener.onSortSelected(data);
    }

    public interface OnSortSelectListener {
        void onSortSelected(CodeDataModel data);
    }
}
