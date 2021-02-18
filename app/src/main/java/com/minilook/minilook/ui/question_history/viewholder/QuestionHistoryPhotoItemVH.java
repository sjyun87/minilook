package com.minilook.minilook.ui.question_history.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.ImageDataModel;
import com.minilook.minilook.databinding.ViewReviewPhotoItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.util.DeviceUtil;
import lombok.Setter;

public class QuestionHistoryPhotoItemVH extends BaseViewHolder<ImageDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    @DimenRes int dp_8 = R.dimen.dp_8;
    @DimenRes int dp_4 = R.dimen.dp_4;

    private final ViewReviewPhotoItemBinding binding;

    @Setter private OnPhotoClickListener onPhotoClickListener;

    public QuestionHistoryPhotoItemVH(@NonNull View parent) {
        super(ViewReviewPhotoItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewReviewPhotoItemBinding.bind(itemView);
        setItemSize();
    }

    private void setItemSize() {
        int width = DeviceUtil.getDeviceWidth(context);
        int panelWidth = width - (resources.getDimen(dp_8) * 2);
        int itemSize = panelWidth / 4;
        int imageSize = itemSize - resources.getDimen(dp_4);
        ViewGroup.LayoutParams params = binding.getRoot().getLayoutParams();
        params.width = itemSize;
        params.height = imageSize;
        binding.getRoot().setLayoutParams(params);

        ConstraintLayout.LayoutParams imageParams = (ConstraintLayout.LayoutParams) binding.imgThumb.getLayoutParams();
        imageParams.width = imageSize;
        imageParams.height = imageSize;
        binding.imgThumb.setLayoutParams(imageParams);
    }

    @Override public void bind(ImageDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getThumbUrl())
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgThumb);

        itemView.setOnClickListener(this::onItemClick);
    }

    private void onItemClick(View view) {
        if (onPhotoClickListener != null) onPhotoClickListener.onPhotoClick(position);
    }

    public interface OnPhotoClickListener {
        void onPhotoClick(int position);
    }
}
