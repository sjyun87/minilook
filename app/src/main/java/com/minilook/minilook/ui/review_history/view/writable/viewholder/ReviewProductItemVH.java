package com.minilook.minilook.ui.review_history.view.writable.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.databinding.ViewReviewProductItemBinding;
import com.minilook.minilook.databinding.ViewWritableReviewItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class ReviewProductItemVH extends BaseViewHolder<ReviewDataModel> {

    private final ViewReviewProductItemBinding binding;
    //private final ReviewPhotoAdapter adapter = new ReviewPhotoAdapter();

    public ReviewProductItemVH(@NonNull View parent) {
        super(ViewWritableReviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewReviewProductItemBinding.bind(itemView);
    }

    @Override public void bind(ReviewDataModel $data) {
        super.bind($data);
    }
}
