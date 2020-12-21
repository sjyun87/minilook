package com.minilook.minilook.ui.preorder.view.coming.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.ui.base._BaseViewHolder;

public class PreorderComingHeaderVH extends _BaseViewHolder<PreorderDataModel> {

    public PreorderComingHeaderVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_preorder_coming_header, (ViewGroup) itemView, false));
    }
}
