package com.minilook.minilook.ui.preorder.view.coming.viewholder;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.util.SpannableUtil;

public class PreorderComingItemVH extends BaseViewHolder<PreorderDataModel> {

    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_start_date) TextView startDateTextView;
    @BindView(R.id.txt_brand) TextView brandTextView;
    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_desc) TextView descTextView;
    @BindView(R.id.txt_notification) TextView notificationTextView;

    @BindString(R.string.preorder_start_date) String format_start_date;
    @BindString(R.string.preorder_notification_off) String str_noti_off;
    @BindString(R.string.preorder_notification_on) String str_noti_on;

    @BindColor(R.color.color_FF232323) int color_FF232323;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;

    @BindDrawable(R.drawable.bg_preorder_notification_off) Drawable bg_noti_off;
    @BindDrawable(R.drawable.bg_preorder_notification_on) Drawable bg_noti_on;

    public PreorderComingItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_preorder_coming, (ViewGroup) itemView, false));
    }

    @Override public void bind(PreorderDataModel $data) {
        super.bind($data);

        if (data.getId() == 0) {
            Glide.with(context)
                .load(ContextCompat.getDrawable(context, R.drawable.test_img3))
                .into(thumbImageView);
            notificationTextView.setTextColor(color_FF232323);
            notificationTextView.setBackground(bg_noti_off);
            notificationTextView.setText(str_noti_off);
        } else {
            Glide.with(context)
                .load(ContextCompat.getDrawable(context, R.drawable.test_img4))
                .into(thumbImageView);
            notificationTextView.setTextColor(color_FF8140E5);
            notificationTextView.setBackground(bg_noti_on);
            notificationTextView.setText(str_noti_on);
        }

        startDateTextView.setText(getStartDate(data.getDate_start()));
        brandTextView.setText(data.getBrand());
        titleTextView.setText(data.getTitle());
        descTextView.setText(data.getDesc());
    }

    private SpannableString getStartDate(String date) {
        String total = String.format(format_start_date, date);
        return SpannableUtil.styleSpan(total, date, Typeface.BOLD);
    }
}
