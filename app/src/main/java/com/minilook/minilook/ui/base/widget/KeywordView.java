package com.minilook.minilook.ui.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.minilook.minilook.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Setter;

@SuppressLint("ViewConstructor")
public class KeywordView extends FrameLayout {
    public static final int TYPE_SEARCH_RECENT = 0;
    public static final int TYPE_SEARCH_POPULAR = 1;

    @BindView(R.id.txt_keyword) TextView keywordTextView;

    private String keyword;
    @Setter private OnClickListener onClickListener;

    public KeywordView(@NonNull Context context, int type) {
        super(context);
        setupLayout(type);
    }

    private void setupLayout(int type) {
        int layout;
        switch (type) {
            case TYPE_SEARCH_RECENT:
                layout = R.layout.layout_keywordview_search_recent;
                break;
            default:
            case TYPE_SEARCH_POPULAR:
                layout = R.layout.layout_keywordview_search_popular;
                break;
        }
        ButterKnife.bind(this, inflate(getContext(), layout, this));
    }

    public void setKeyword(String $keyword) {
        this.keyword = $keyword;
        keywordTextView.setText(keyword);
    }

    @OnClick(R.id.txt_keyword)
    void onKeywordClick() {
        if (onClickListener != null) onClickListener.onKeywordClick(keyword);
    }

    @OnClick(R.id.img_delete)
    void onDeleteClick() {
        if (onClickListener != null) onClickListener.onDeleteClick(this, keyword);
    }

    public interface OnClickListener {
        void onKeywordClick(String keyword);

        void onDeleteClick(View view, String keyword);
    }
}
