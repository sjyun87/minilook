package com.minilook.minilook.ui.market.viewholder.preorder.viewholder;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.databinding.ViewMarketPreorderOpenItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.preorder_detail.PreorderDetailActivity;
import com.minilook.minilook.util.SpannableUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MarketPreorderOpenItemVH extends BaseViewHolder<PreorderDataModel> {

    @StringRes int str_format_d_day = R.string.preorder_d_day;
    @StringRes int str_format_date = R.string.preorder_open_date;

    @DrawableRes int ph_square = R.drawable.ph_square;

    @DimenRes int dp_150 = R.dimen.dp_150;

    private final ViewMarketPreorderOpenItemBinding binding;

    public MarketPreorderOpenItemVH(@NonNull View parent, boolean isOnlyOne) {
        super(ViewMarketPreorderOpenItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewMarketPreorderOpenItemBinding.bind(itemView);

        setupItemSize(isOnlyOne);
    }

    private void setupItemSize(boolean isOnlyOne) {
        ViewGroup.LayoutParams params = binding.getRoot().getLayoutParams();
        params.width = isOnlyOne ? ViewGroup.LayoutParams.MATCH_PARENT : resources.getDimensionPixelSize(dp_150);
        binding.getRoot().setLayoutParams(params);
    }

    @Override public void bind(PreorderDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getThumbUrl())
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgThumb);

        binding.txtEndDate.setText(getEndDate(data.getEndDate()));
        binding.txtBrandName.setText(data.getBrandName());
        binding.txtTitle.setText(data.getTitle());
        binding.txtDesc.setText(data.getDesc());

        itemView.setOnClickListener(this::onItemClick);
    }

    private SpannableString getEndDate(long date) {
        Date endDate = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd(E)", Locale.KOREA);
        String strEndData = format.format(endDate);

        long todayDay = Calendar.getInstance().getTimeInMillis() / (24 * 60 * 60 * 1000);
        long targetDay = endDate.getTime() / (24 * 60 * 60 * 1000);
        long count = (targetDay - todayDay) + 1;

        String dday = String.format(resources.getString(str_format_d_day), count);
        String totalEndData = String.format(resources.getString(str_format_date), strEndData, dday);
        return SpannableUtil.styleSpan(totalEndData, dday, Typeface.BOLD);
    }

    void onItemClick(View view) {
        PreorderDetailActivity.start(context, data.getPreorderNo());
    }
}
