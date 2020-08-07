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
import com.minilook.minilook.data.model.category.CategoryDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class CategoryVH extends BaseViewHolder<CategoryDataModel> {

    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.img_icon) ImageView iconImageView;

    public CategoryVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_category, (ViewGroup) itemView, false));
    }

    @Override public void bind(CategoryDataModel $data) {
        super.bind($data);

        nameTextView.setText(data.getName());

        Glide.with(context)
            .load(R.drawable.test_profile)
            .into(iconImageView);

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {

    }
}
