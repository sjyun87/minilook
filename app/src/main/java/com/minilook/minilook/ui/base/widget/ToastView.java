package com.minilook.minilook.ui.base.widget;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.minilook.minilook.R;
import lombok.Builder;
import lombok.NonNull;

public class ToastView extends LinearLayout {

    @BindView(R.id.txt_message) TextView messageTextView;

    @Builder
    public ToastView(@NonNull Context context, String message) {
        this(context);

        initView();
        setupMessage(message);
    }

    public ToastView(@NonNull Context context) {
        super(context);
    }

    private void initView() {
        ButterKnife.bind(this, inflate(getContext(), R.layout.layout_toast, this));
    }

    public void setupMessage(String message) {
        messageTextView.setText(message);
    }
}
