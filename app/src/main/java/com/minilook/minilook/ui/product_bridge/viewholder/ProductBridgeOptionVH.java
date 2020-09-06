package com.minilook.minilook.ui.product_bridge.viewholder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.search.OptionMenuDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import lombok.Setter;

public class ProductBridgeOptionVH extends BaseViewHolder<OptionMenuDataModel> {

    @BindView(R.id.layout_option_panel) ConstraintLayout optionPanel;
    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.img_delete) ImageView deleteImageView;

    @BindColor(R.color.color_FF232323) int color_FF232323;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;

    @BindDrawable(R.drawable.bg_button_border_lightgray) Drawable bg_button_off;
    @BindDrawable(R.drawable.bg_button_border_purple) Drawable bg_button_on;

    @Setter private OnMenuClickListener onMenuClickListener;

    public ProductBridgeOptionVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_option_menu, (ViewGroup) itemView, false));
    }

    @Override public void bind(OptionMenuDataModel $data) {
        super.bind($data);

        nameTextView.setText(data.getName());
        if (data.isSelected()) {
            optionPanel.setBackground(bg_button_on);
            nameTextView.setTextColor(color_FF8140E5);
            deleteImageView.setVisibility(View.VISIBLE);
        } else {
            optionPanel.setBackground(bg_button_off);
            nameTextView.setTextColor(color_FF232323);
            deleteImageView.setVisibility(View.GONE);
        }

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        if (onMenuClickListener != null) onMenuClickListener.onMenuClick(data.getPosition());
    }

    public interface OnMenuClickListener {
        void onMenuClick(int position);
    }
}
