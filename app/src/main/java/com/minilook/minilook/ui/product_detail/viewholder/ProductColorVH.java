package com.minilook.minilook.ui.product_detail.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.base.ColorDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class ProductColorVH extends BaseViewHolder<ColorDataModel> {

    @BindView(R.id.img_color) ImageView imageView;

    public ProductColorVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_product_color, (ViewGroup) itemView, false));
    }

    @Override public void bind(ColorDataModel $data) {
        super.bind($data);

    }
}
