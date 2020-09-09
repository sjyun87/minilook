package com.minilook.minilook.ui.coupon.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindString;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.user.CouponDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.util.StringUtil;

public class CouponItemVH extends BaseViewHolder<CouponDataModel> {

    @BindView(R.id.txt_coupon) TextView couponTextView;
    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.txt_use_condition) TextView useConditionTextView;
    @BindView(R.id.txt_date) TextView dateTextView;
    @BindView(R.id.curtain) View curtainView;

    @BindString(R.string.coupon_use_condition) String format_use_condition;
    @BindString(R.string.coupon_expiration_date) String format_expiration_date;
    @BindString(R.string.coupon_expiration) String str_expiration;

    public CouponItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_coupon, (ViewGroup) itemView, false));
    }

    @Override public void bind(CouponDataModel $data) {
        super.bind($data);

        couponTextView.setText(StringUtil.toDigit(data.getCoupon()));
        nameTextView.setText(data.getName());
        useConditionTextView.setText(String.format(format_use_condition, data.getUse_condition()));
        if (data.isEnd()) {
            curtainView.setVisibility(View.VISIBLE);
            dateTextView.setText(str_expiration);
        } else {
            if (data.getDate_end() != null) {
                dateTextView.setText(String.format(format_expiration_date, data.getDate_end()));
            }
            curtainView.setVisibility(View.GONE);
        }
    }
}
