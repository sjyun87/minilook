package com.minilook.minilook.ui.order.viewholder;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.user.CouponDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.order.OrderPresenterImpl;
import com.minilook.minilook.util.SpannableUtil;
import com.minilook.minilook.util.StringUtil;

public class CouponItemVH extends BaseViewHolder<CouponDataModel> {

    @BindView(R.id.txt_coupon) TextView couponTextView;

    @BindString(R.string.order_coupon_cancel) String str_cancel;
    @BindString(R.string.order_coupon) String format_coupon;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;

    @BindColor(R.color.color_FF424242) int color_FF424242;
    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;

    public CouponItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_order_coupon, (ViewGroup) itemView, false));
    }

    @Override public void bind(CouponDataModel $data) {
        super.bind($data);

        if (data.getCoupon_id() != 0) {
            String couponPrice = StringUtil.toDigit(data.getCoupon());
            String name = data.getName();
            String formatText = String.format(format_coupon, couponPrice, name);
            couponTextView.setText(SpannableUtil.fontSpan(formatText, couponPrice, font_bold));
        } else {
            couponTextView.setText(str_cancel);
        }

        if (data.isAvailable()) {
            couponTextView.setTextColor(color_FF424242);
            couponTextView.setEnabled(true);
        } else {
            couponTextView.setTextColor(color_FFDBDBDB);
            couponTextView.setEnabled(false);
        }

        itemView.setOnClickListener(this::onItemClick);
    }

    private void onItemClick(View view) {
        RxBus.send(new OrderPresenterImpl.RxEventCouponSelected(data));
    }
}
