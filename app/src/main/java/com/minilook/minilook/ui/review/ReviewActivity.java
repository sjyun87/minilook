package com.minilook.minilook.ui.review;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.code.ReviewSatisfactions;
import com.minilook.minilook.data.code.ReviewSizeRatings;
import com.minilook.minilook.data.model.review.RatingDataModel;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.databinding.ActivityReviewBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.review.adapter.ReviewAdapter;
import com.minilook.minilook.ui.review.di.ReviewArguments;
import com.minilook.minilook.util.SpannableUtil;
import java.util.List;

public class ReviewActivity extends BaseActivity implements ReviewPresenter.View {

    public static void start(Context context, int productNo) {
        Intent intent = new Intent(context, ReviewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("productNo", productNo);
        context.startActivity(intent);
    }

    @DrawableRes int img_review_good = R.drawable.ic_review_good_s;
    @DrawableRes int img_review_normal = R.drawable.ic_review_normal_s;
    @DrawableRes int img_review_bad = R.drawable.ic_review_bad_s;

    @StringRes int format_percent = R.string.base_percent;
    @StringRes int format_size_rating = R.string.product_detail_review_rating_size;
    @StringRes int format_size_rating_bold = R.string.product_detail_review_rating_size_bold;

    @FontRes int font_bold = R.font.nanum_square_b;

    @DimenRes int dp_1 = R.dimen.dp_1;

    private ActivityReviewBinding binding;
    private ReviewPresenter presenter;
    private final ReviewAdapter adapter = new ReviewAdapter();
    private final BaseAdapterDataView<ReviewDataModel> adapterView = adapter;

    @Override protected View getBindingView() {
        binding = ActivityReviewBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new ReviewPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ReviewArguments provideArguments() {
        return ReviewArguments.builder()
            .view(this)
            .productNo(getIntent().getIntExtra("productNo", -1))
            .adapter(adapter)
            .build();
    }

    @Override public void setupRecyclerView() {
        binding.rcvReview.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvReview.setAdapter(adapter);
        DividerDecoration.builder(this)
            .size(resources.getDimen(dp_1))
            .asSpace()
            .build()
            .addTo(binding.rcvReview);
        EndlessOnScrollListener scrollListener =
            EndlessOnScrollListener.builder()
                .layoutManager(binding.rcvReview.getLayoutManager())
                .onLoadMoreListener(presenter::onLoadMore)
                .visibleThreshold(10)
                .build();
        binding.rcvReview.addOnScrollListener(scrollListener);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void refresh(int start, int rows) {
        adapterView.refresh(start, rows);
    }

    @Override public void showReviewRatingPanel() {
        binding.layoutReviewRatingPanel.setVisibility(View.VISIBLE);
    }

    @Override public void setSatisfaction(String satisfactionCode) {
        ReviewSatisfactions satisfactions = ReviewSatisfactions.toType(satisfactionCode);
        switch (satisfactions) {
            case GOOD:
                binding.imgSatisfaction.setImageDrawable(resources.getDrawable(img_review_good));
                break;
            case NORMAL:
                binding.imgSatisfaction.setImageDrawable(resources.getDrawable(img_review_normal));
                break;
            case BAD:
                binding.imgSatisfaction.setImageDrawable(resources.getDrawable(img_review_bad));
                break;
        }
        binding.txtSatisfaction.setText(satisfactions.getValue());
    }

    @Override public void setSizeRating(RatingDataModel sizeRating) {
        String rating_bold = String.format(resources.getString(format_size_rating_bold),
            ReviewSizeRatings.toType(sizeRating.getCode()).getValue(),
            String.format(resources.getString(format_percent), sizeRating.getValue()));
        String rating = String.format(resources.getString(format_size_rating), rating_bold);
        binding.txtSizeRating.setText(SpannableUtil.fontSpan(rating, rating_bold, resources.getFont(font_bold)));
    }

    @Override public void setSizeRatingDetail(List<RatingDataModel> sizeRatings) {
        binding.txtVeryBigPercent.setText(findPercent(sizeRatings, ReviewSizeRatings.VERY_BIG.getCode()));
        binding.txtLittleBigPercent.setText(findPercent(sizeRatings, ReviewSizeRatings.LITTLE_BIG.getCode()));
        binding.txtGoodPercent.setText(findPercent(sizeRatings, ReviewSizeRatings.PERFECTLY.getCode()));
        binding.txtLittleSmallPercent.setText(findPercent(sizeRatings, ReviewSizeRatings.LITTLE_SMALL.getCode()));
        binding.txtVerySmallPercent.setText(findPercent(sizeRatings, ReviewSizeRatings.VERY_SMALL.getCode()));
    }

    private String findPercent(List<RatingDataModel> sizeRatingList, String sizeCode) {
        for (RatingDataModel rating : sizeRatingList) {
            if (sizeCode.equals(rating.getCode())) {
                return String.format(resources.getString(format_percent), rating.getValue());
            }
        }
        return "-";
    }

    @Override public void showEmptyPanel() {
        binding.txtEmpty.setVisibility(View.VISIBLE);
    }
}
