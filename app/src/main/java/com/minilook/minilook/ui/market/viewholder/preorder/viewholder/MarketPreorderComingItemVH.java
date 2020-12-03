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
import com.minilook.minilook.databinding.ViewMarketPreorderComingItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.util.SpannableUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MarketPreorderComingItemVH extends BaseViewHolder<PreorderDataModel> {

    @StringRes int str_format_d_day = R.string.preorder_d_day;
    @StringRes int str_format_date = R.string.preorder_coming_date;

    @DrawableRes int ph_square = R.drawable.ph_square;

    @DimenRes int dp_150 = R.dimen.dp_150;

    private final ViewMarketPreorderComingItemBinding binding;

    public MarketPreorderComingItemVH(@NonNull View parent, boolean isOnlyOne) {
        super(ViewMarketPreorderComingItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewMarketPreorderComingItemBinding.bind(itemView);

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

        binding.txtStartDate.setText(getStartDate(data.getStartDate()));
        binding.txtBrandName.setText(data.getBrandName());
        binding.txtTitle.setText(data.getTitle());
        binding.txtDesc.setText(data.getDesc());
    }

    private SpannableString getStartDate(long date) {
        Date startDate = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd(E)", Locale.KOREA);
        String strStartData = format.format(startDate);

        long todayDay = Calendar.getInstance().getTimeInMillis() / (24 * 60 * 60 * 1000);
        long targetDay = startDate.getTime() / (24 * 60 * 60 * 1000);
        long count = (targetDay - todayDay) + 1;

        String dday = String.format(resources.getString(str_format_d_day), count);
        String totalStartData = String.format(resources.getString(str_format_date), strStartData, dday);
        return SpannableUtil.styleSpan(totalStartData, dday, Typeface.BOLD);
    }
}
