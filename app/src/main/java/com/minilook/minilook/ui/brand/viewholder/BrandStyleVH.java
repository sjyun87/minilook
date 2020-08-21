package com.minilook.minilook.ui.brand.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandMenuDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import lombok.Setter;

public class BrandStyleVH extends BaseViewHolder<BrandMenuDataModel> {

    @Setter private OnStyleClickListener onStyleClickListener;

    public BrandStyleVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_brand_style, (ViewGroup) itemView, false));
    }

    @Override public void bind(BrandMenuDataModel $data) {
        super.bind($data);

        // select 분기
        // bg_brand_style_on / offß

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        if (onStyleClickListener != null) onStyleClickListener.onStyleClick(data.getPosition());
    }

    public interface OnStyleClickListener {
        void onStyleClick(int position);
    }
}
