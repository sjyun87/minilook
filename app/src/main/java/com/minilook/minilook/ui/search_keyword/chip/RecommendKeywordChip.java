package com.minilook.minilook.ui.search_keyword.chip;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.minilook.minilook.R;
import lombok.Builder;

public class RecommendKeywordChip extends FrameLayout {

    @BindView(R.id.txt_keyword) TextView keywordTextView;

    private String keyword;
    private OnChipClickListener onChipClickListener;

    @Builder
    public RecommendKeywordChip(@NonNull Context context, @NonNull String keyword, OnChipClickListener listener) {
        this(context);
        this.keyword = keyword;
        this.onChipClickListener = listener;

        initView();
        setupKeyword(keyword);
    }

    public RecommendKeywordChip(@NonNull Context context) {
        super(context);
    }

    private void initView() {
        ButterKnife.bind(this, inflate(getContext(), R.layout.view_recommend_keyword, this));
        setOnClickListener(this::onChipClick);
    }

    public void setupKeyword(String keyword) {
        keywordTextView.setText(keyword);
    }

    private void onChipClick(View parent) {
        if (onChipClickListener != null) onChipClickListener.onChipClick(keyword);
    }

    public interface OnChipClickListener {
        void onChipClick(String keyword);
    }
}
