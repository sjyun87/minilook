package com.minilook.minilook.ui.review_write.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.databinding.ViewPhotoFooterItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.util.DeviceUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class PhotoFooterItemVH extends BaseViewHolder<PhotoDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    @DimenRes int dp_10 = R.dimen.dp_10;
    @DimenRes int dp_4 = R.dimen.dp_4;

    private final ViewPhotoFooterItemBinding binding;

    public PhotoFooterItemVH(@NonNull View parent) {
        super(ViewPhotoFooterItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewPhotoFooterItemBinding.bind(itemView);
        setItemSize();
    }

    private void setItemSize() {
        int width = DeviceUtil.getDeviceWidth(context);
        int panelWidth = width - (resources.getDimen(dp_4) * 2);
        int itemSize = panelWidth / 4;
        ViewGroup.LayoutParams params = binding.getRoot().getLayoutParams();
        params.width = itemSize;
        params.height = itemSize;
        binding.getRoot().setLayoutParams(params);

        int imageSize = itemSize - resources.getDimen(dp_10);
        ConstraintLayout.LayoutParams imageParams = (ConstraintLayout.LayoutParams) binding.viewBg.getLayoutParams();
        imageParams.width = imageSize;
        imageParams.height = imageSize;
        binding.viewBg.setLayoutParams(imageParams);
    }

    @Override public void bind(PhotoDataModel $data) {
        super.bind($data);
        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        RxBus.send(new RxEventReviewPhotoFooterClick());
    }

    @AllArgsConstructor @Getter public final static class RxEventReviewPhotoFooterClick {
    }
}
