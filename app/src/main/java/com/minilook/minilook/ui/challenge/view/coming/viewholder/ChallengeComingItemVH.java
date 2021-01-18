package com.minilook.minilook.ui.challenge.view.coming.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.challenge.ChallengeDataModel;
import com.minilook.minilook.databinding.ViewChallengeComingItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.util.StringUtil;
import java.util.Calendar;
import java.util.Date;

public class ChallengeComingItemVH extends BaseViewHolder<ChallengeDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    @StringRes int str_unit = R.string.challenge_unit;
    @StringRes int str_enter = R.string.challenge_enter;
    @StringRes int str_start_date = R.string.challenge_start_date;
    @StringRes int str_start_date_tomorrow = R.string.challenge_start_date_tomorrow;

    @ColorRes int color_FF424242 = R.color.color_FF424242;
    @ColorRes int color_FF8140E5 = R.color.color_FF8140E5;

    private final ViewChallengeComingItemBinding binding;

    public ChallengeComingItemVH(@NonNull View parent) {
        super(ViewChallengeComingItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewChallengeComingItemBinding.bind(itemView);
    }

    @Override public void bind(ChallengeDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getThumbUrl())
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgThumb);

        if (data.isEnter()) {
            binding.txtLabel.setTextColor(resources.getColor(color_FF8140E5));
            binding.txtLabel.setText(resources.getString(str_enter));
        } else {
            binding.txtLabel.setTextColor(resources.getColor(color_FF424242));
            binding.txtLabel.setText(getStartDate(data.getStartDate()));
        }

        binding.txtBrandName.setText(data.getBrandName());
        binding.txtProductName.setText(data.getProductName());

        binding.txtWinner.setText(
            String.format(resources.getString(str_unit), StringUtil.toDigit(data.getWinnerCount())));
    }

    private String getStartDate(long date) {
        Date startDate = new Date(date);
        long todayDay = Calendar.getInstance().getTimeInMillis() / (24 * 60 * 60 * 1000);
        long targetDay = startDate.getTime() / (24 * 60 * 60 * 1000);
        long count = targetDay - todayDay;

        if (count > 1) {
            return String.format(resources.getString(str_start_date), (count + 1));
        } else {
            return resources.getString(str_start_date_tomorrow);
        }
    }
}
