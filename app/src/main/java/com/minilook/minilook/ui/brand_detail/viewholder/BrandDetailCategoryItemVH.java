package com.minilook.minilook.ui.brand_detail.viewholder;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CategoryDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

import butterknife.BindColor;
import butterknife.BindFont;
import butterknife.BindView;
import lombok.Setter;

public class BrandDetailCategoryItemVH extends BaseViewHolder<CategoryDataModel> {

    @BindView(R.id.txt_category) TextView categoryTextView;
    @BindView(R.id.txt_count) TextView countTextView;

    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;
    @BindColor(R.color.color_FF232323) int color_FF232323;
    @BindColor(R.color.color_FF424242) int color_FF424242;
    @BindColor(R.color.color_FFA9A9A9) int color_FFA9A9A9;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;
    @BindFont(R.font.nanum_square_r) Typeface font_regular;

    @Setter private OnItemClickListener onItemClickListener;

    public BrandDetailCategoryItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_brand_detail_category, (ViewGroup) itemView, false));
    }

    @Override public void bind(CategoryDataModel $data) {
        super.bind($data);

        // TODO : 브랜드 상품 카테고리

        //if (data.isSelect()) {
        //    categoryTextView.setTextColor(color_FF8140E5);
        //    categoryTextView.setTypeface(font_bold);
        //    countTextView.setTextColor(color_FF424242);
        //    countTextView.setTypeface(font_bold);
        //} else {
        //    categoryTextView.setTextColor(color_FF232323);
        //    categoryTextView.setTypeface(font_regular);
        //    countTextView.setTextColor(color_FFA9A9A9);
        //    countTextView.setTypeface(font_regular);
        //}
        //
        //categoryTextView.setText(data.getName());
        //countTextView.setText(StringUtil.toDigit(data.getCount()));
        //
        //itemView.setOnClickListener(v -> {
        //    if (onItemClickListener != null) onItemClickListener.onItemClick(data.getPosition());
        //});
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
