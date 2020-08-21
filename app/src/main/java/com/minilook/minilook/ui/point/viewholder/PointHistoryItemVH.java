package com.minilook.minilook.ui.point.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.SortDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class PointHistoryItemVH extends BaseViewHolder<SortDataModel> {

    public PointHistoryItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_point_history, (ViewGroup) itemView, false));
    }

    @Override public void bind(SortDataModel $data) {
        super.bind($data);


    }
}
