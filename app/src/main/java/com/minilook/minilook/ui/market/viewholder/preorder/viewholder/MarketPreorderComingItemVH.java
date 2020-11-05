package com.minilook.minilook.ui.market.viewholder.preorder.viewholder;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
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

public class MarketPreorderComingItemVH extends BaseViewHolder<PreorderDataModel> {

    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_start_date) TextView startDateTextView;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_desc) TextView descTextView;

    @BindString(R.string.preorder_d_day) String format_d_day;
    @BindString(R.string.preorder_coming_date) String format_date;

    @BindDrawable(R.drawable.placeholder_image) Drawable img_placeholder;

    public MarketPreorderComingItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_preorder_coming_item, (ViewGroup) itemView, false));
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
        brandNameTextView.setText(data.getBrandName());
        titleTextView.setText(data.getTitle());
        descTextView.setText(data.getDesc());
    }

    private SpannableString getStartDate(long date) {
        Date startDate = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd(E)", Locale.KOREA);
        String strStartData = format.format(startDate);

        long todayDay = Calendar.getInstance().getTimeInMillis() / (24 * 60 * 60 * 1000);
        long targetDay = startDate.getTime() / (24 * 60 * 60 * 1000);
        long count = Math.abs(targetDay - todayDay) + 1;

        String dday = String.format(format_d_day, count);
        String totalStartData = String.format(format_date, strStartData, dday);
        return SpannableUtil.styleSpan(totalStartData, dday, Typeface.BOLD);
    }
}
