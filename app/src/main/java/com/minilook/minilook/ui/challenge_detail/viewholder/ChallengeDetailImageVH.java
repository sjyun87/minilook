package com.minilook.minilook.ui.challenge_detail.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.databinding.ViewChallengeDetailImageBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class ChallengeDetailImageVH extends BaseViewHolder<String> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    private final ViewChallengeDetailImageBinding binding;

    public ChallengeDetailImageVH(@NonNull View parent) {
        super(ViewChallengeDetailImageBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewChallengeDetailImageBinding.bind(parent);
    }

    @Override public void bind(String $data) {
        super.bind($data);

        Glide.with(context)
            .load(data)
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgStyle);
    }
}
