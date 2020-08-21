package com.minilook.minilook.ui.coupon.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.SortDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class CouponItemVH extends BaseViewHolder<SortDataModel> {

    public CouponItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_point_history, (ViewGroup) itemView, false));
    }

    @Override public void bind(SortDataModel $data) {
        super.bind($data);


    }
}
