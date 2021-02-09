package com.minilook.minilook.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseDialog;
import lombok.Setter;

public class ReviewCompletedDialog extends BaseDialog {

    @BindView(R.id.txt_contents) TextView contentsTextView;

    @BindString(R.string.dialog_write_review_normal) String str_normal_review;
    @BindString(R.string.dialog_write_review_photo) String str_photo_review;

    @Setter private boolean isPhotoReview;
    @Setter private int point;

    public ReviewCompletedDialog(@NonNull Context context) {
        super(context);
    }

    @Override protected int getLayoutID() {
        return R.layout.dialog_write_review;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String contents;
        if (isPhotoReview) {
            contents = String.format(str_photo_review, point);
        } else {
            contents = String.format(str_normal_review, point);
        }
        contentsTextView.setText(contents);
    }

    @OnClick(R.id.txt_ok)
    void onOkClick() {
        this.dismiss();
        onPositiveClickListener.onPositiveClick();
    }
}
