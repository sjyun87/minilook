package com.minilook.minilook.ui.question_history.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.PhotoDetailDataModel;
import com.minilook.minilook.data.model.question.QuestionDataModel;
import com.minilook.minilook.databinding.ViewQuestionHistoryItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.photo_detail.PhotoDetailActivity;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.ui.question_edit.QuestionEditActivity;
import com.minilook.minilook.ui.question_history.adapter.QuestionHistoryPhotoAdapter;

public class QuestionHistoryItemVH extends BaseViewHolder<QuestionDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;
    @DrawableRes int dot_purple = R.drawable.dot_purple;
    @DrawableRes int dot_gray = R.drawable.dot_gray;

    @StringRes int format_type = R.string.question_type;
    @StringRes int str_unanswered = R.string.question_unanswered;
    @StringRes int str_answer_completed = R.string.question_answer_completed;
    @StringRes int str_answering = R.string.question_answering;

    private final ViewQuestionHistoryItemBinding binding;
    private final QuestionHistoryPhotoAdapter adapter = new QuestionHistoryPhotoAdapter();

    public QuestionHistoryItemVH(@NonNull View parent) {
        super(ViewQuestionHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewQuestionHistoryItemBinding.bind(itemView);
        binding.layoutProductPanel.setOnClickListener(view -> onProductClick());
        adapter.setOnPhotoClickListener(this::onPhotoClick);
        binding.txtEdit.setOnClickListener(this::onEditClick);
        binding.txtDelete.setOnClickListener(this::onDeleteClick);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        binding.rcvPhoto.setHasFixedSize(true);
        binding.rcvPhoto.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        binding.rcvPhoto.setAdapter(adapter);
    }

    @Override public void bind(QuestionDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getThumbUrl())
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgProductThumb);

        binding.txtBrandName.setText(data.getBrandName());
        binding.txtProductName.setText(data.getProductName());

        binding.txtRegistDate.setText(data.getRegistDate());
        binding.txtType.setText(String.format(resources.getString(format_type), data.getType()));
        setAnswerState();

        if (data.isSecret()) binding.imgSecret.setVisibility(View.VISIBLE);

        binding.txtQuestion.setText(data.getQuestion());

        if (data.getPhotos() != null && data.getPhotos().size() > 0) {
            adapter.set(data.getPhotos());
            adapter.refresh();
            binding.rcvPhoto.setVisibility(View.VISIBLE);
        } else {
            binding.rcvPhoto.setVisibility(View.GONE);
        }

        if (data.isAnswer()) {
            binding.txtAnswer.setText(data.getAnswer());
            binding.txtAnswerDate.setText(data.getAnswerDate());
            binding.txtAnswerDate.setVisibility(View.VISIBLE);
        } else {
            binding.txtAnswer.setText(resources.getString(str_answering));
            binding.txtAnswerDate.setVisibility(View.GONE);
        }

        if (!data.isAnswer()) {
            binding.txtDelete.setVisibility(View.VISIBLE);
        } else {
            binding.txtDelete.setVisibility(View.GONE);
        }
    }

    private void setAnswerState() {
        if (data.isAnswer()) {
            binding.imgState.setImageDrawable(resources.getDrawable(dot_purple));
            binding.txtState.setText(resources.getString(str_answer_completed));
        } else {
            binding.imgState.setImageDrawable(resources.getDrawable(dot_gray));
            binding.txtState.setText(resources.getString(str_unanswered));
        }
    }

    private void handleEditButton(boolean isMyQuestion) {
        if (isMyQuestion && !data.isAnswer()) {
            binding.txtEdit.setVisibility(View.VISIBLE);
        } else {
            binding.txtEdit.setVisibility(View.GONE);
        }
    }

    private void onProductClick() {
        ProductDetailActivity.start(context, data.getProductNo());
    }

    private void onPhotoClick(int position) {
        PhotoDetailDataModel model = new PhotoDetailDataModel();
        model.setPhotos(data.getPhotos());
        model.setPosition(position);

        PhotoDetailActivity.start(context, model);
    }

    private void onEditClick(View view) {
        QuestionEditActivity.start(context, data);
    }

    private void onDeleteClick(View view) {

    }
}
