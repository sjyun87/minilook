package com.minilook.minilook.ui.base.widget;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.minilook.minilook.R;
import lombok.Builder;

public class TagView extends FrameLayout {

    @BindView(R.id.txt_tag) TextView tagTextView;

    private String tag;

    @Builder
    public TagView(@NonNull Context context, @NonNull String tag) {
        this(context);
        this.tag = tag;

        initView();
        setTag(tag);
    }

    public TagView(@NonNull Context context) {
        super(context);
    }

    private void initView() {
        ButterKnife.bind(this, inflate(getContext(), R.layout.layout_tag_view, this));
    }

    public void setTag(String name) {
        tagTextView.setText(tag);
    }
}
