package com.minilook.minilook.ui.market.viewholder.brand.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.util.DimenUtil;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;
import lombok.Setter;

public class MarketBrandMenuVH extends BaseViewHolder<BrandDataModel> {

    @BindView(R.id.img_brand_logo) ImageView logoImageView;
    @BindView(R.id.img_dot) ImageView dotImageView;

    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;

    @Setter private OnMenuClickListener onMenuClickListener;

    public MarketBrandMenuVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_brand_menu, (ViewGroup) itemView, false));
    }

    @Override public void bind(BrandDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getBrand_logo())
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(context, 1), color_FFDBDBDB)))
            .into(logoImageView);

        if (data.isSelect()) {
            dotImageView.setVisibility(View.VISIBLE);
        } else {
            dotImageView.setVisibility(View.GONE);
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
