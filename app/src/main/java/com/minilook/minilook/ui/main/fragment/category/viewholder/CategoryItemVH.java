package com.minilook.minilook.ui.main.fragment.category.viewholder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.category.CategoryItemDataModel;
import com.minilook.minilook.data.type.CategoryType;
import com.minilook.minilook.ui.base.BaseViewHolder;

import butterknife.BindDrawable;
import butterknife.BindView;

public class CategoryItemVH extends BaseViewHolder<CategoryItemDataModel> {

    @BindView(R.id.img_category) ImageView categoryImageView;
    @BindView(R.id.txt_product_name) TextView nameTextView;

    @BindDrawable(R.drawable.img_category_top) Drawable topImage;
    @BindDrawable(R.drawable.img_category_pants) Drawable pantsImage;
    @BindDrawable(R.drawable.img_category_skirt) Drawable skirtImage;
    @BindDrawable(R.drawable.img_category_set) Drawable setImage;
    @BindDrawable(R.drawable.img_category_onepiece) Drawable onepieceImage;
    @BindDrawable(R.drawable.img_category_outer) Drawable outerImage;
    @BindDrawable(R.drawable.img_category_accessories) Drawable accessoriesImage;
    @BindDrawable(R.drawable.img_category_underwear) Drawable underwearImage;
    @BindDrawable(R.drawable.img_category_baby) Drawable babyImage;
    @BindDrawable(R.drawable.img_category_swimsuit) Drawable swimsuitImage;
    @BindDrawable(R.drawable.img_category_bag) Drawable bagImage;
    @BindDrawable(R.drawable.img_category_living) Drawable livingImage;

    public CategoryItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_category, (ViewGroup) itemView, false));
    }

    @Override public void bind(CategoryItemDataModel $data) {
        super.bind($data);

        Drawable categoryImage;
        if (data.getId().equals(CategoryType.TYPE_TOP.getId())) {
            categoryImage = topImage;
        } else if (data.getId().equals(CategoryType.TYPE_PANTS.getId())) {
            categoryImage = pantsImage;
        } else if (data.getId().equals(CategoryType.TYPE_SKIRT.getId())) {
            categoryImage = skirtImage;
        } else if (data.getId().equals(CategoryType.TYPE_SET.getId())) {
            categoryImage = setImage;
        } else if (data.getId().equals(CategoryType.TYPE_ONE_PIECE.getId())) {
            categoryImage = onepieceImage;
        } else if (data.getId().equals(CategoryType.TYPE_OUTER.getId())) {
            categoryImage = outerImage;
        } else if (data.getId().equals(CategoryType.TYPE_ACCESSORIES_BEAUTY.getId())) {
            categoryImage = accessoriesImage;
        } else if (data.getId().equals(CategoryType.TYPE_UNDERWEAR_SOCKS.getId())) {
            categoryImage = underwearImage;
        } else if (data.getId().equals(CategoryType.TYPE_BABY.getId())) {
            categoryImage = babyImage;
        } else if (data.getId().equals(CategoryType.TYPE_SWIMSUIT_RAINCOAT.getId())) {
            categoryImage = swimsuitImage;
        } else if (data.getId().equals(CategoryType.TYPE_BAG_SHOES.getId())) {
            categoryImage = bagImage;
        } else if (data.getId().equals(CategoryType.TYPE_LIVING.getId())) {
            categoryImage = livingImage;
        } else {
            categoryImage = null;
        }

        Glide.with(itemView)
            .load(categoryImage)
            .into(categoryImageView);

        nameTextView.setText(data.getName_ko());
    }
}
