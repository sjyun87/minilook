package com.minilook.minilook.ui.challenge.view.open.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.challenge.ChallengeDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.databinding.ViewChallengeOpenItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.challenge.ChallengePresenterImpl;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.util.StringUtil;
import java.util.Calendar;
import java.util.Date;

public class ChallengeOpenItemVH extends BaseViewHolder<ChallengeDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    @StringRes int str_unit = R.string.challenge_unit;
    @StringRes int str_enter = R.string.challenge_enter;
    @StringRes int str_end_date = R.string.challenge_end_date;
    @StringRes int str_end_date_today = R.string.challenge_end_date_today;

    @ColorRes int color_FF424242 = R.color.color_FF424242;
    @ColorRes int color_FF8140E5 = R.color.color_FF8140E5;

    private final ViewChallengeOpenItemBinding binding;

    public ChallengeOpenItemVH(@NonNull View parent) {
        super(ViewChallengeOpenItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewChallengeOpenItemBinding.bind(itemView);
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
            binding.txtLabel.setText(getEndDate(data.getEndDate()));
        }

        binding.txtBrand.setText(data.getBrand());
        binding.txtProduct.setText(data.getProduct());

        binding.txtEnter.setText(
            String.format(resources.getString(str_unit), StringUtil.toDigit(data.getEnterCount())));
        binding.txtWinner.setText(String.valueOf(data.getWinnerCount()));

        itemView.setOnClickListener(this::onItemClick);
    }

    private String getEndDate(long date) {
        Date endDate = new Date(date);
        long todayDay = Calendar.getInstance().getTimeInMillis() / (24 * 60 * 60 * 1000);
        long targetDay = endDate.getTime() / (24 * 60 * 60 * 1000);
        long count = (targetDay - todayDay) + 1;

        if (count > 1) {
            return String.format(resources.getString(str_end_date), count);
        } else {
            return resources.getString(str_end_date_today);
        }
    }

    private void onItemClick(View view) {
        if (App.getInstance().isLogin()) {
            RxBus.send(new ChallengePresenterImpl.RxEventNavigateToChallengeDetail(data.getChallengeNo()));
        } else {
            LoginActivity.start(context);
        }
    }
}
