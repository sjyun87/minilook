package com.minilook.minilook.ui.product_detail.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.base.SizeDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class ProductSizeVH extends BaseViewHolder<SizeDataModel> {

    //@BindView(R.id.img_style) ImageView imageView;

    public ProductSizeVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_product_size, (ViewGroup) itemView, false));
    }

    @Override public void bind(SizeDataModel $data) {
        super.bind($data);

        //Glide.with(context)
        //    .load(data)
        //    .into(imageView);
    }
}
