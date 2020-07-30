package com.minilook.minilook.ui.preorder.view.open.viewholder;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import butterknife.BindString;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.util.SpannableUtil;

public class PreorderOpenItemVH extends BaseViewHolder<PreorderDataModel> {

    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_start_date) TextView endDateTextView;
    @BindView(R.id.txt_brand) TextView brandTextView;
    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_desc) TextView descTextView;

    @BindString(R.string.preorder_end_date) String format_end_date;

    public PreorderOpenItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_preorder_open, (ViewGroup) itemView, false));
    }

    @Override public void bind(PreorderDataModel $data) {
        super.bind($data);

        if (data.getId() == 0) {
            Glide.with(context)
                .load(ContextCompat.getDrawable(context, R.drawable.test_img1))
                .into(thumbImageView);
        } else {
            Glide.with(context)
                .load(ContextCompat.getDrawable(context, R.drawable.test_img2))
                .into(thumbImageView);
        }

        endDateTextView.setText(getEndDate(data.getDate_end()));
        brandTextView.setText(data.getBrand());
        titleTextView.setText(data.getTitle());
        descTextView.setText(data.getDesc());
    }

    private SpannableString getEndDate(String date) {
        String total = String.format(format_end_date, date);
        return SpannableUtil.styleSpan(total, date, Typeface.BOLD);
    }
}
