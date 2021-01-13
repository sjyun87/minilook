package com.minilook.minilook.ui.challenge.view.close.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.challenge.ChallengeDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.databinding.ViewChallengeCloseItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.challenge.ChallengePresenterImpl;
import com.minilook.minilook.util.StringUtil;

public class ChallengeCloseItemVH extends BaseViewHolder<ChallengeDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    @StringRes int str_unit = R.string.challenge_unit;
    @StringRes int str_term_date = R.string.challenge_term_date;

    private final ViewChallengeCloseItemBinding binding;

    public ChallengeCloseItemVH(@NonNull View parent) {
        super(ViewChallengeCloseItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewChallengeCloseItemBinding.bind(itemView);
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
            binding.txtLabel.setVisibility(View.VISIBLE);
        } else {
            binding.txtLabel.setVisibility(View.GONE);
        }

        binding.txtBrandName.setText(data.getBrandName());
        binding.txtProductName.setText(data.getProductName());

        binding.txtEnter.setText(
            String.format(resources.getString(str_unit), StringUtil.toDigit(data.getEnterCount())));
        binding.txtWinner.setText(String.valueOf(data.getWinnerCount()));

        binding.txtTerm.setText(
            String.format(resources.getString(str_term_date), data.getStartDateName(), data.getEndDateName()));

        itemView.setOnClickListener(this::onItemClick);
    }

    private void onItemClick(View view) {
        RxBus.send(new ChallengePresenterImpl.RxEventNavigateToChallengeDetail(data.getChallengeNo()));
    }
}
