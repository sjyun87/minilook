package com.minilook.minilook.ui.point.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.user.PointDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class PointHistoryItemVH extends BaseViewHolder<PointDataModel> {

    @BindView(R.id.txt_type) TextView typeTextView;
    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_date) TextView dateTextView;
    @BindView(R.id.txt_point) TextView pointTextView;
    @BindView(R.id.txt_end_date) TextView endDateTextView;

    public PointHistoryItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_point_history, (ViewGroup) itemView, false));
    }

    @Override public void bind(PointDataModel $data) {
        super.bind($data);
    }
}
