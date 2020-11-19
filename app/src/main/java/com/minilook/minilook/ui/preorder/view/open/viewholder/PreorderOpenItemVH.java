package com.minilook.minilook.ui.preorder.view.open.viewholder;

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
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.preorder.PreorderPresenterImpl;
import com.minilook.minilook.util.SpannableUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PreorderOpenItemVH extends BaseViewHolder<PreorderDataModel> {

    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_end_date) TextView endDateTextView;
    @BindView(R.id.txt_brand) TextView brandTextView;
    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_desc) TextView descTextView;

    @BindString(R.string.preorder_d_day) String format_d_day;
    @BindString(R.string.preorder_open_date) String format_date;

    @BindDrawable(R.drawable.placeholder_image) Drawable img_placeholder;

    public PreorderOpenItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_preorder_open, (ViewGroup) itemView, false));
    }

    @Override public void bind(PreorderDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getThumbUrl())
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);

        endDateTextView.setText(getEndDate(data.getEndDate()));
        brandTextView.setText(data.getBrandName());
        titleTextView.setText(data.getTitle());
        descTextView.setText(data.getDesc());

        itemView.setOnClickListener(this::onItemClick);
    }

    private SpannableString getEndDate(long date) {
        Date endDate = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd(E)", Locale.KOREA);
        String strEndData = format.format(endDate);

        long todayDay = Calendar.getInstance().getTimeInMillis() / (24 * 60 * 60 * 1000);
        long targetDay = endDate.getTime() / (24 * 60 * 60 * 1000);
        long count = Math.abs(targetDay - todayDay) + 1;

        String dday = String.format(format_d_day, count);
        String totalEndData = String.format(format_date, strEndData, dday);
        return SpannableUtil.styleSpan(totalEndData, dday, Typeface.BOLD);
    }

    private void onItemClick(View view) {
        RxBus.send(new PreorderPresenterImpl.RxBusEventPreorderClick(data.getPreorderNo()));
    }

    @OnClick(R.id.img_share)
    void onShareClick() {
        RxBus.send(new PreorderPresenterImpl.RxEventPreorderShareClick(data));
    }
}
