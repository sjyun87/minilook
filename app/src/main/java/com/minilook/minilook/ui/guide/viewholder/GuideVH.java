package com.minilook.minilook.ui.guide.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class GuideVH extends BaseViewHolder<String> {

    public GuideVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_guide, (ViewGroup) itemView, false));
    }

    @Override public void bind(String $data) {
        super.bind($data);

    }
}
