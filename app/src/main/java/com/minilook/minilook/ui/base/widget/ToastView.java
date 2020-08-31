package com.minilook.minilook.ui.base.widget;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.minilook.minilook.R;
import lombok.Builder;
import lombok.NonNull;
import timber.log.Timber;

public class ToastView extends LinearLayout {

    @BindView(R.id.txt_message) TextView messageTextView;
    @BindView(R.id.txt_button) TextView buttonTextView;

    private Context context;
    private String message;
    private String buttonName;
    private OnButtonClickListener listener;

    @Builder
    public ToastView(@NonNull Context context, String message, String buttonName, OnButtonClickListener listener) {
        this(context);
        this.context = context;
        this.message = message;
        this.buttonName = buttonName;
        this.listener = listener;

        initView();
        setupMessage();
        if (buttonName != null) setupButton();
    }

    public ToastView(@NonNull Context context) {
        super(context);
    }

    private void initView() {
        ButterKnife.bind(this, inflate(getContext(), R.layout.layout_toast, this));
    }

    public void setupMessage() {
        messageTextView.setText(message);
    }

    private void setupButton() {
        buttonTextView.setText(buttonName);
        buttonTextView.setVisibility(VISIBLE);
        buttonTextView.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                Timber.e("onClick");
                if (listener != null) listener.onButtonClick();
            }
        });
    }

    public interface OnButtonClickListener {
        void onButtonClick();
    }
}
