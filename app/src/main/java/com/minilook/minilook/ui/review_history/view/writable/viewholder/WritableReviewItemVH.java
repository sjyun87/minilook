package com.minilook.minilook.ui.review_history.view.writable.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.order.OrderHistoryDataModel;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.databinding.ViewWritableReviewItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.review_history.view.writable.adapter.WritableReviewOptionAdapter;
import com.minilook.minilook.ui.review_write.ReviewWriteActivity;

public class WritableReviewItemVH extends BaseViewHolder<OrderHistoryDataModel>
    implements WritableReviewOptionItemVH.OnReviewWriteClickListener {

    @DimenRes int dp_1 = R.dimen.dp_1;

    @ColorRes int color_FFF5F5F5 = R.color.color_FFF5F5F5;

    private final ViewWritableReviewItemBinding binding;
    private final WritableReviewOptionAdapter adapter = new WritableReviewOptionAdapter();

    public WritableReviewItemVH(@NonNull View parent) {
        super(ViewWritableReviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewWritableReviewItemBinding.bind(itemView);
        adapter.setOnReviewWriteClickListener(this);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        binding.rcvProduct.setHasFixedSize(true);
        binding.rcvProduct.setLayoutManager(new LinearLayoutManager(context));
        binding.rcvProduct.setAdapter(adapter);
        DividerDecoration.builder(context)
            .size(resources.getDimen(dp_1))
            .colorRes(color_FFF5F5F5)
            .build()
            .addTo(binding.rcvProduct);
    }

    @Override public void bind(OrderHistoryDataModel $data) {
        super.bind($data);

        binding.txtOrderNo.setText(data.getOrderNo());
        binding.txtOrderDate.setText(data.getOrderDate());

        adapter.set(data.getOptions());
        adapter.refresh();
    }

    @Override public void onReviewClick(OrderProductDataModel orderData) {
        ReviewWriteActivity.start(context, data.getOrderNo(), data.getOrderDate(), orderData);
    }
}
