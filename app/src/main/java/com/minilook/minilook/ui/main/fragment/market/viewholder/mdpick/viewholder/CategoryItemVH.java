package com.minilook.minilook.ui.main.fragment.market.viewholder.mdpick.viewholder;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindFont;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.category.CategoryDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import lombok.Setter;

public class CategoryItemVH extends BaseViewHolder<CategoryDataModel> {

    @BindView(R.id.txt_category) TextView categoryTextView;

    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;
    @BindColor(R.color.color_FF232323) int color_FF232323;

    @BindFont(R.font.notosans_medium) Typeface font_medium;
    @BindFont(R.font.notosans_regular) Typeface font_regular;

    @Setter private OnItemClickListener onItemClickListener;

    public CategoryItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_category, (ViewGroup) itemView, false));
    }

    @Override public void bind(CategoryDataModel $data) {
        super.bind($data);

        if (data.isSelect()) {
            categoryTextView.setTextColor(color_FF8140E5);
            categoryTextView.setTypeface(font_medium);
        } else {
            categoryTextView.setTextColor(color_FF232323);
            categoryTextView.setTypeface(font_regular);
        }

        categoryTextView.setText(data.getName());

        itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) onItemClickListener.onItemClick(data.getPosition());
        });
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
