package com.minilook.minilook.ui.question.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.question.QuestionDataModel;
import com.minilook.minilook.databinding.ViewQuestionItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.question.adapter.QuestionPhotoAdapter;

public class QuestionItemVH extends BaseViewHolder<QuestionDataModel> {

    @DrawableRes int dot_purple = R.drawable.dot_purple;
    @DrawableRes int dot_gray = R.drawable.dot_gray;

    @StringRes int format_type = R.string.question_type;
    @StringRes int str_my_question = R.string.question_my_question;
    @StringRes int str_unanswered = R.string.question_unanswered;
    @StringRes int str_answer_completed = R.string.question_answer_completed;
    @StringRes int str_answering = R.string.question_answering;

    @ColorRes int color_FF616161 = R.color.color_FF616161;
    @ColorRes int color_FF6200EA = R.color.color_FF6200EA;

    private final ViewQuestionItemBinding binding;
    private final QuestionPhotoAdapter adapter = new QuestionPhotoAdapter();

    public QuestionItemVH(@NonNull View parent) {
        super(ViewQuestionItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewQuestionItemBinding.bind(itemView);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        binding.rcvPhoto.setHasFixedSize(true);
        binding.rcvPhoto.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        binding.rcvPhoto.setAdapter(adapter);
    }

    @Override public void bind(QuestionDataModel $data) {
        super.bind($data);

        boolean isMyQuestion = data.getMemberNo() == App.getInstance().getMemberNo();

        binding.txtRegistDate.setText(data.getRegistDate());
        setNick(isMyQuestion);
        binding.txtType.setText(String.format(resources.getString(format_type), data.getType()));
        setAnswerState();

        if (data.isSecret() && !isMyQuestion) {
            setSecret();
        } else if (data.isSecret() && isMyQuestion) {
            setMySecret();
        } else {
            setNormal();
        }

        //handleEditButton(isMyQuestion);
    }

    private void setSecret() {
        binding.imgSecret.setVisibility(View.VISIBLE);
        binding.layoutContentsPanel.setVisibility(View.GONE);
        binding.txtSecret.setVisibility(View.VISIBLE);
        binding.txtEdit.setVisibility(View.GONE);
    }

    private void setMySecret() {
        binding.imgSecret.setVisibility(View.VISIBLE);
        binding.layoutContentsPanel.setVisibility(View.VISIBLE);
        binding.txtSecret.setVisibility(View.GONE);
        setContents();
    }

    private void setNormal() {
        binding.imgSecret.setVisibility(View.GONE);
        binding.layoutContentsPanel.setVisibility(View.VISIBLE);
        binding.txtSecret.setVisibility(View.GONE);
        setContents();
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

    private void setNick(boolean isMyQuestion) {
        if (isMyQuestion) {
            binding.txtNick.setText(resources.getString(str_my_question));
            binding.txtNick.setTextColor(resources.getColor(color_FF6200EA));
        } else {
            binding.txtNick.setText(data.getNick());
            binding.txtNick.setTextColor(resources.getColor(color_FF616161));
        }
    }

    private void setContents() {
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
    }

    private void handleEditButton(boolean isMyQuestion) {
        if (isMyQuestion && !data.isAnswer()) {
            binding.txtEdit.setVisibility(View.VISIBLE);
        } else {
            binding.txtEdit.setVisibility(View.GONE);
        }
    }
}
