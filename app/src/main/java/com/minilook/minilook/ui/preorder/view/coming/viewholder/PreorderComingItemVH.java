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
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.util.SpannableUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import timber.log.Timber;

public class PreorderComingItemVH extends BaseViewHolder<PreorderDataModel> {

    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_start_date) TextView startDateTextView;
    @BindView(R.id.txt_brand) TextView brandTextView;
    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_desc) TextView descTextView;

    @BindString(R.string.preorder_d_day) String format_d_day;
    @BindString(R.string.preorder_coming_date) String format_date;

    @BindString(R.string.preorder_notification_off) String str_noti_off;
    @BindString(R.string.preorder_notification_on) String str_noti_on;

    @BindColor(R.color.color_FF232323) int color_FF232323;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;

    @BindDrawable(R.drawable.placeholder_image) Drawable img_placeholder;
    @BindDrawable(R.drawable.bg_preorder_notification_off) Drawable bg_noti_off;
    @BindDrawable(R.drawable.bg_preorder_notification_on) Drawable bg_noti_on;

    public PreorderComingItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_preorder_coming, (ViewGroup) itemView, false));
    }

    @Override public void bind(PreorderDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getThumbUrl())
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);

        startDateTextView.setText(getStartDate(data.getStartDate()));
        brandTextView.setText(data.getBrandName());
        titleTextView.setText(data.getTitle());
        descTextView.setText(data.getDesc());
    }

    private SpannableString getStartDate(long date) {
        Date startDate = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd(E)", Locale.KOREA);
        String strStartData = format.format(startDate);

        long todayDay = Calendar.getInstance().getTimeInMillis() / (24 * 60 * 60 * 1000);
        long targetDay = startDate.getTime() / (24 * 60 * 60 * 1000);
        long count = targetDay - todayDay;

        String dday = String.format(format_d_day, count);
        String totalStartData = String.format(format_date, strStartData, dday);
        return SpannableUtil.styleSpan(totalStartData, dday, Typeface.BOLD);
    }
}
