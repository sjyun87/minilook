package com.minilook.minilook.ui.question_write.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseViewHolder;
import lombok.Setter;

public class QuestionTypeItemVH extends BaseViewHolder<String> {

    @BindView(R.id.txt_type) TextView sizeTextView;

    @Setter private OnTypeSelectedListener onTypeSelectedListener;

    public QuestionTypeItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_question_type, (ViewGroup) itemView, false));
    }

    @Override public void bind(String $data) {
        super.bind($data);

        sizeTextView.setText(data);
        itemView.setOnClickListener(v -> {
            if (onTypeSelectedListener != null) onTypeSelectedListener.onTypeSelected(data);
        });
    }

    public interface OnTypeSelectedListener {
        void onTypeSelected(String data);
    }
}
