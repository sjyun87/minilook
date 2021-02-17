package com.minilook.minilook.ui.review_history.view.written.viewholder;

import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.code.GenderCode;
import com.minilook.minilook.data.code.ReviewSatisfactions;
import com.minilook.minilook.data.code.ReviewSizeRatings;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.databinding.ViewWrittenReviewItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.photo.PhotoActivity;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.ui.review_history.view.written.adapter.ReviewPhotoAdapter;
import com.minilook.minilook.util.SpannableUtil;

public class WrittenReviewItemVH extends BaseViewHolder<ReviewDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;
    @DrawableRes int ic_review_good = R.drawable.ic_review_good_s;
    @DrawableRes int ic_review_normal = R.drawable.ic_review_normal_s;
    @DrawableRes int ic_review_bad = R.drawable.ic_review_bad_s;

    @StringRes int str_format_option = R.string.review_history_written_option;
    @StringRes int str_age_unit = R.string.review_history_written_age_unit;
    @StringRes int str_height_unit = R.string.review_history_written_height_unit;
    @StringRes int str_weight_unit = R.string.review_history_written_weight_unit;

    @StringRes int format_help_count = R.string.review_help_count;
    @StringRes int format_help_count_bold = R.string.review_help_count_b;

    @FontRes int font_bold = R.font.nanum_square_b;

    @ColorRes int color_FF424242 = R.color.color_FF424242;

    private final ViewWrittenReviewItemBinding binding;
    private final ReviewPhotoAdapter adapter = new ReviewPhotoAdapter();

    public WrittenReviewItemVH(@NonNull View parent) {
        super(ViewWrittenReviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewWrittenReviewItemBinding.bind(itemView);
        binding.layoutMorePanel.setOnClickListener(view -> onReviewMoreClick());
        binding.layoutProductPanel.setOnClickListener(view -> onProductClick());
        adapter.setOnPhotoClickListener(this::onPhotoClick);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        binding.rcvPhoto.setHasFixedSize(true);
        binding.rcvPhoto.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        binding.rcvPhoto.setAdapter(adapter);
    }

    @Override public void bind(ReviewDataModel $data) {
        super.bind($data);

        binding.txtRegistDate.setText(data.getRegistDate());
        binding.txtOrderDate.setText(data.getOrderDate());

        Glide.with(context)
            .load(data.getImageUrl())
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgProductThumb);

        binding.txtBrandName.setText(data.getBrandName());
        binding.txtProductName.setText(data.getProductName());
        binding.txtOption.setText(
            String.format(resources.getString(str_format_option), data.getColorName(), data.getSizeName()));

        binding.txtColor.setText(data.getColorName());
        binding.txtSize.setText(data.getSizeName());

        if (!TextUtils.isEmpty(data.getGenderCode())) {
            binding.txtGender.setText(GenderCode.toType(data.getGenderCode()).getValue());
            binding.txtGender.setVisibility(View.VISIBLE);
            binding.txtGenderCaption.setVisibility(View.VISIBLE);
        } else {
            binding.txtGender.setVisibility(View.GONE);
            binding.txtGenderCaption.setVisibility(View.GONE);
        }

        if (data.getAge() > 0) {
            binding.txtAge.setText(String.format(resources.getString(str_age_unit), data.getAge()));
            binding.txtAge.setVisibility(View.VISIBLE);
            binding.txtAgeCaption.setVisibility(View.VISIBLE);
        } else {
            binding.txtAge.setVisibility(View.GONE);
            binding.txtAgeCaption.setVisibility(View.GONE);
        }

        if (data.getHeight() > 0) {
            binding.txtHeight.setText(String.format(resources.getString(str_height_unit), data.getHeight()));
            binding.txtHeight.setVisibility(View.VISIBLE);
            binding.txtHeightCaption.setVisibility(View.VISIBLE);
        } else {
            binding.txtHeight.setVisibility(View.GONE);
            binding.txtHeightCaption.setVisibility(View.GONE);
        }

        if (data.getWeight() > 0) {
            binding.txtWeight.setText(String.format(resources.getString(str_weight_unit), data.getWeight()));
            binding.txtWeight.setVisibility(View.VISIBLE);
            binding.txtWeightCaption.setVisibility(View.VISIBLE);
        } else {
            binding.txtWeight.setVisibility(View.GONE);
            binding.txtWeightCaption.setVisibility(View.GONE);
        }

        ReviewSatisfactions satisfactions = ReviewSatisfactions.toType(data.getSatisfactionCode());
        switch (satisfactions) {
            case GOOD:
                binding.imgSatisfaction.setImageResource(ic_review_good);
                break;
            case NORMAL:
                binding.imgSatisfaction.setImageResource(ic_review_normal);
                break;
            case BAD:
                binding.imgSatisfaction.setImageResource(ic_review_bad);
                break;
        }
        binding.txtSatisfaction.setText(satisfactions.getValue());
        ReviewSizeRatings sizeRatings = ReviewSizeRatings.toType(data.getSizeRatingCode());
        binding.txtSizeRating.setText(sizeRatings.getValue());

        if (data.getPhotos() != null && data.getPhotos().size() > 0) {
            adapter.set(data.getPhotos());
            adapter.refresh();
            binding.rcvPhoto.setVisibility(View.VISIBLE);
        } else {
            binding.rcvPhoto.setVisibility(View.GONE);
        }

        binding.txtReview.setText(data.getReview());
        binding.txtReview.post(() -> {
            int lineCount = binding.txtReview.getLineCount();
            if (lineCount > binding.txtReview.getMaxLines()) {
                binding.layoutMorePanel.setVisibility(View.VISIBLE);
            }
        });

        binding.txtHelpCount.setText(getHelpCountText());
    }

    private SpannableString getHelpCountText() {
        int helpCount = data.getHelpCount();
        String totalText = String.format(resources.getString(format_help_count), helpCount);
        String boldText = String.format(resources.getString(format_help_count_bold), helpCount);

        SpannableString fontSpan = SpannableUtil.fontSpan(totalText, boldText, resources.getFont(font_bold));
        SpannableString colorSpan =
            SpannableUtil.foregroundColorSpan(fontSpan, boldText, resources.getColor(color_FF424242));
        return colorSpan;
    }

    private void onReviewMoreClick() {
        binding.txtReview.setMaxLines(Integer.MAX_VALUE);
        binding.layoutMorePanel.setVisibility(View.GONE);
    }

    private void onProductClick() {
        ProductDetailActivity.start(context, data.getProductNo());
    }

    private void onPhotoClick(int position) {
        PhotoActivity.start(context, data.getPhotos(), position);
    }
}
