package com.minilook.minilook.ui.product_detail.viewholder;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.product_detail.ProductDetailPresenterImpl;
import com.minilook.minilook.util.SpannableUtil;

public class ProductDetailReviewItemVH extends _BaseViewHolder<ReviewDataModel> {

    @BindView(R.id.txt_nick) TextView nickTextView;
    @BindView(R.id.txt_regist_date) TextView registDateTextView;
    @BindView(R.id.txt_contents) TextView contentsTextView;
    @BindView(R.id.layout_help_panel) LinearLayout helpPanel;
    @BindView(R.id.img_smile) ImageView smileImageView;
    @BindView(R.id.txt_help) TextView helpTextView;
    @BindView(R.id.txt_help_count) TextView helpCountTextView;

    @BindDrawable(R.drawable.bg_button_border_lightgray) Drawable bg_button_off;
    @BindDrawable(R.drawable.bg_button_border_purple) Drawable bg_button_on;
    @BindDrawable(R.drawable.ic_smile_gray) Drawable bg_smile_off;
    @BindDrawable(R.drawable.ic_smile_purple) Drawable bg_smile_on;

    @BindColor(R.color.color_FF616161) int color_FF616161;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;
    @BindColor(R.color.color_FF424242) int color_FF424242;

    @BindString(R.string.review_help_count) String format_help_count;
    @BindString(R.string.review_help_count_b) String format_help_count_bold;

    @BindFont(R.font.nanum_square_r) Typeface font_regular;
    @BindFont(R.font.nanum_square_b) Typeface font_bold;

    public ProductDetailReviewItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_review, (ViewGroup) itemView, false));
    }

    @Override public void bind(ReviewDataModel $data) {
        super.bind($data);

        nickTextView.setText(data.getNickname());
        registDateTextView.setText(data.getRegistDate());
        contentsTextView.setText(data.getReview());

        handleHelpData();
    }

    private void handleHelpData() {
        if (data.isHelp()) {
            helpPanel.setBackground(bg_button_on);
            smileImageView.setImageDrawable(bg_smile_on);
            helpTextView.setTextColor(color_FF8140E5);
        } else {
            helpPanel.setBackground(bg_button_off);
            smileImageView.setImageDrawable(bg_smile_off);
            helpTextView.setTextColor(color_FF616161);
        }
        helpCountTextView.setText(getSpanText());
    }

    private SpannableString getSpanText() {
        int helpCount = data.getHelpCount();
        String totalText = String.format(format_help_count, helpCount);
        String boldText = String.format(format_help_count_bold, helpCount);

        SpannableString fontSpan = SpannableUtil.fontSpan(totalText, boldText, font_bold);
        SpannableString colorSpan = SpannableUtil.foregroundColorSpan(fontSpan, boldText, color_FF424242);
        return colorSpan;
    }

    @OnClick(R.id.layout_help_panel)
    void onHelpClick() {
        if (!App.getInstance().isLogin()) {
            LoginActivity.start(context);
            return;
        }

        if (data.isHelp()) {
            data.setHelpCount(data.getHelpCount() - 1);
        } else {
            data.setHelpCount(data.getHelpCount() + 1);
        }
        data.setHelp(!data.isHelp());
        handleHelpData();
        RxBus.send(
            new ProductDetailPresenterImpl.RxEventProductDetailReviewHelpClick(data.isHelp(), data.getReviewNo()));
    }
}
