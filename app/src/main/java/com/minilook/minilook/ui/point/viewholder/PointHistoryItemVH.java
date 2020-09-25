package com.minilook.minilook.ui.point.viewholder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.member.PointHistoryDataModel;
import com.minilook.minilook.data.code.PointStatus;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class PointHistoryItemVH extends BaseViewHolder<PointHistoryDataModel> {

    @BindView(R.id.txt_type) TextView typeTextView;
    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_point) TextView pointTextView;
    @BindView(R.id.txt_regist_date) TextView registDateTextView;
    @BindView(R.id.txt_expire_date) TextView expireDateTextView;

    @BindDrawable(R.drawable.bg_round_border_purple) Drawable bg_plus;
    @BindDrawable(R.drawable.bg_round_border_gray) Drawable bg_minus;

    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;
    @BindColor(R.color.color_FFA9A9A9) int color_FFA9A9A9;
    @BindColor(R.color.color_FF424242) int color_FF424242;

    @BindString(R.string.point_plus) String format_plus;
    @BindString(R.string.point_minus) String format_minus;
    @BindString(R.string.point_expire) String format_expire;

    public PointHistoryItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_point_history, (ViewGroup) itemView, false));
    }

    @Override public void bind(PointHistoryDataModel $data) {
        super.bind($data);
        if (data.getCode() == PointStatus.PLUS.getValue()) {
            typeTextView.setBackground(bg_plus);
            typeTextView.setTextColor(color_FF8140E5);
            pointTextView.setTextColor(color_FF8140E5);
            pointTextView.setText(String.format(format_plus, data.getPoint()));
            expireDateTextView.setText(String.format(format_expire, data.getExpireDate()));
            expireDateTextView.setVisibility(View.VISIBLE);
        } else {
            typeTextView.setBackground(bg_minus);
            typeTextView.setTextColor(color_FFA9A9A9);
            pointTextView.setTextColor(color_FF424242);
            pointTextView.setText(String.format(format_minus, data.getPoint()));
            expireDateTextView.setVisibility(View.GONE);
        }
        typeTextView.setText(data.getType());
        titleTextView.setText(data.getTitle());
        registDateTextView.setText(data.getRegistDate());
    }
}
