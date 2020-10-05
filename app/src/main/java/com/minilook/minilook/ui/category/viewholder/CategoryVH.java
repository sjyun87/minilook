package com.minilook.minilook.ui.category.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.product_bridge.ProductBridgeActivity;

public class CategoryVH extends BaseViewHolder<CodeDataModel> {

    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.img_icon) ImageView iconImageView;

    public CategoryVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_category, (ViewGroup) itemView, false));
    }

    @Override public void bind(CodeDataModel $data) {
        super.bind($data);

        nameTextView.setText(data.getName());

        Glide.with(context)
            .load(data.getIconUrl())
            .into(iconImageView);

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        ProductBridgeActivity.start(context, getOptionData());
    }

    private SearchOptionDataModel getOptionData() {
        SearchOptionDataModel model = new SearchOptionDataModel();
        model.setCategory_code(data.getCode());
        model.setCategory_name(data.getName());
        return model;
    }
}
