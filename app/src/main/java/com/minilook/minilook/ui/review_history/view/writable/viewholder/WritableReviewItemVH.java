package com.minilook.minilook.ui.review_history.view.writable.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.databinding.ViewWritableReviewItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class WritableReviewItemVH extends BaseViewHolder<ReviewDataModel> {

    private final ViewWritableReviewItemBinding binding;
    //private final ReviewPhotoAdapter adapter = new ReviewPhotoAdapter();

    public WritableReviewItemVH(@NonNull View parent) {
        super(ViewWritableReviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewWritableReviewItemBinding.bind(itemView);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        binding.rcvProduct.setHasFixedSize(true);
        binding.rcvProduct.setLayoutManager(new LinearLayoutManager(context));
        //binding.rcvProduct.setAdapter(adapter);
    }

    @Override public void bind(ReviewDataModel $data) {
        super.bind($data);




    }
}
