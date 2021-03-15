package com.minilook.minilook.ui.search_keyword.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.databinding.ViewRecentKeywordBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class RecentKeywordVH extends BaseViewHolder<String> {

    private final ViewRecentKeywordBinding binding;

    public RecentKeywordVH(@NonNull View parent) {
        super(ViewRecentKeywordBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewRecentKeywordBinding.bind(itemView);

        binding.imgDelete.setOnClickListener(this::onDeleteClick);
        itemView.setOnClickListener(this::onItemClick);
    }

    @Override public void bind(String $data) {
        super.bind($data);

        binding.txtKeyword.setText(data);
    }

    private void onItemClick(View view) {
        RxBus.send(new RxEventRecentKeywordClick(data));
    }

    private void onDeleteClick(View parent) {
        RxBus.send(new RxEventRecentKeywordDeleteClick(data));
    }

    @AllArgsConstructor @Getter public final static class RxEventRecentKeywordClick {
        private final String keyword;
    }

    @AllArgsConstructor @Getter public final static class RxEventRecentKeywordDeleteClick {
        private final String keyword;
    }
}
