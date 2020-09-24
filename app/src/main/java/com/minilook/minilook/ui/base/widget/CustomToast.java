package com.minilook.minilook.ui.base.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.minilook.minilook.R;
import lombok.Builder;

public class CustomToast {

    private Activity activity;
    private PopupWindow popup;
    private View root;
    private View layout;

    @Builder
    public CustomToast(Activity activity, String message, String button, View.OnClickListener listener) {
        this.activity = activity;
        root = activity.getWindow().getDecorView();

        setupView();
        setMessage(message);
        if (listener != null) {
            setButton(button, listener);
        }
        setupPopup(layout);
    }

    public static CustomToast make(Activity activity, String message) {
        return CustomToast.builder()
            .activity(activity)
            .message(message)
            .build();
    }

    public static CustomToast make(Activity activity, String message, String button, View.OnClickListener listener) {
        return CustomToast.builder()
            .activity(activity)
            .message(message)
            .button(button)
            .listener(listener)
            .build();
    }

    public void show() {
        // TODO Bad Token Exception..
        //popup.showAtLocation(root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //root.postDelayed(this::hide, 3000);
    }

    public void hide() {
        if (popup.isShowing()) popup.dismiss();
    }

    @SuppressLint("InflateParams")
    private void setupView() {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.item_toast, null, false);
    }

    private void setMessage(String message) {
        TextView messageTextView = layout.findViewById(R.id.txt_message);
        messageTextView.setText(message);
    }

    private void setButton(String button, View.OnClickListener listener) {
        TextView buttonTextView = layout.findViewById(R.id.txt_button);
        buttonTextView.setVisibility(View.VISIBLE);
        buttonTextView.setText(button);
        buttonTextView.setOnClickListener(listener);
    }

    private void setupPopup(View layout) {
        int startMargin = activity.getResources().getDimensionPixelSize(R.dimen.dp_10);
        int endMargin = activity.getResources().getDimensionPixelSize(R.dimen.dp_10);
        int height = activity.getResources().getDimensionPixelSize(R.dimen.dp_44);

        popup = new PopupWindow(layout);
        popup.setWidth(root.getWidth() - (startMargin + endMargin));
        popup.setHeight(height);
        popup.setAnimationStyle(R.style.ToastStyle);
        popup.setTouchable(false);
        popup.setFocusable(false);
    }
}
