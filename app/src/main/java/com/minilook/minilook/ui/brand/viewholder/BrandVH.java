package com.minilook.minilook.ui.brand.viewholder;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.util.DimenUtil;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;
import timber.log.Timber;

public class BrandVH extends BaseViewHolder<BrandDataModel> {

    @BindView(R.id.img_logo) ImageView logoImageView;
    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.txt_tag) TextView tagTextView;
    @BindView(R.id.img_scrap) ImageView scrapImageView;

    @BindColor(R.color.color_FFEEEFF5) int color_FFEEEFF5;
    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;

    public BrandVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_brand, (ViewGroup) itemView, false));
    }

    @Override public void bind(BrandDataModel $data) {
        super.bind($data);
        Timber.e(data.toString());
        Glide.with(context)
            .load(data.getBrand_logo())
            .placeholder(new ColorDrawable(color_FFEEEFF5))
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(context, 1), color_FFDBDBDB)))
            .into(logoImageView);

        nameTextView.setText(data.getBrand_name());
        tagTextView.setText(data.getBrand_tag());


        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        BrandDetailActivity.start(context, data.getId());
    }
}
