package com.minilook.minilook.ui.scrapbook.view.brand.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.databinding.ViewScrapbookBrandBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import com.minilook.minilook.util.DimenUtil;
import java.util.List;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class ScrapbookBrandItemVH extends BaseViewHolder<BrandDataModel> {

    @ColorRes int color_FFDBDBDB = R.color.color_FFDBDBDB;

    @DrawableRes int ph_square = R.drawable.ph_square;
    @DrawableRes int ph_circle = R.drawable.ph_circle;

    private final ViewScrapbookBrandBinding binding;
    private final ImageView[] styleImageViews;

    public ScrapbookBrandItemVH(@NonNull View parent) {
        super(ViewScrapbookBrandBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewScrapbookBrandBinding.bind(itemView);
        styleImageViews = new ImageView[] { binding.imgStyle1, binding.imgStyle2, binding.imgStyle3 };
        binding.imgScrap.setOnClickListener(view -> onScrapClick());
    }

    @Override public void bind(BrandDataModel $data) {
        super.bind($data);

        List<String> styleImages = data.getStyleImages();
        for (int i = 0; i < styleImages.size(); i++) {
            Glide.with(context)
                .load(styleImages.get(i))
                .placeholder(ph_square)
                .error(ph_square)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(styleImageViews[i]);
        }

        Glide.with(context)
            .load(data.getBrandLogo())
            .placeholder(ph_circle)
            .error(ph_circle)
            .transition(new DrawableTransitionOptions().crossFade())
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(context, 1),
                    resources.getColor(color_FFDBDBDB))))
            .into(binding.imgLogo);

        binding.txtName.setText(data.getBrandName());
        binding.txtTag.setText(data.getBrandTag());

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        BrandDetailActivity.start(context, data.getBrandNo());
    }

    void onScrapClick() {
        if (App.getInstance().isLogin()) {
            data.setScrap(false);
            data.setScrapCount(data.getScrapCount() - 1);
            RxBus.send(new MainPresenterImpl.RxBusEventUpdateBrandScrap(data));
        } else {
            LoginActivity.start(context);
        }
    }
}
