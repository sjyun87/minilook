package com.minilook.minilook.ui.coupon.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindString;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.member.CouponDataModel;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.util.StringUtil;

public class CouponItemVH extends _BaseViewHolder<CouponDataModel> {

    @BindView(R.id.txt_coupon) TextView couponTextView;
    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.txt_use_condition) TextView useConditionTextView;
    @BindView(R.id.txt_date) TextView dateTextView;
    @BindView(R.id.curtain) View curtainView;

    @BindString(R.string.coupon_use_condition) String format_use_condition;
    @BindString(R.string.coupon_date) String format_date;
    @BindString(R.string.coupon_end) String str_end;

    public CouponItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_coupon, (ViewGroup) itemView, false));
    }

    @Override public void bind(CouponDataModel $data) {
        super.bind($data);

        couponTextView.setText(StringUtil.toDigit(data.getValue()));
        nameTextView.setText(data.getName());
        useConditionTextView.setText(String.format(format_use_condition, data.getCondition()));
        if (data.isExpired()) {
            curtainView.setVisibility(View.VISIBLE);
            dateTextView.setText(str_end);
        } else {
            String endDate = data.getExpireDate();
            if (data.getExpireDate() == null) {
                endDate = "";
            }
            dateTextView.setText(String.format(format_date, data.getStartDate(), endDate));
            curtainView.setVisibility(View.GONE);
        }
    }
}
