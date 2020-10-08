package com.minilook.minilook.ui.question.viewholder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.question.QuestionDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class QuestionItemVH extends BaseViewHolder<QuestionDataModel> {

    @BindView(R.id.txt_type) TextView typeTextView;
    @BindView(R.id.img_my_secret) ImageView mySecretImageView;
    @BindView(R.id.img_state) ImageView stateImageView;
    @BindView(R.id.txt_state) TextView stateTextView;
    @BindView(R.id.layout_question_panel) ConstraintLayout questionPanel;
    @BindView(R.id.txt_question) TextView questionTextView;
    @BindView(R.id.txt_regist_date) TextView registDateTextView;
    @BindView(R.id.txt_nick) TextView nickTextView;
    @BindView(R.id.layout_answer_panel) ConstraintLayout answerPanel;
    @BindView(R.id.txt_answer) TextView answerTextView;
    @BindView(R.id.txt_answer_date) TextView answerDateTextView;
    @BindView(R.id.layout_secret_panel) ConstraintLayout secretPanel;

    @BindDrawable(R.drawable.dot_purple) Drawable dot_purple;
    @BindDrawable(R.drawable.dot_gray) Drawable dot_gray;

    @BindString(R.string.question_my_question) String str_my_question;
    @BindString(R.string.question_unanswered) String str_unanswered;
    @BindString(R.string.question_answer_completed) String str_answer_completed;

    @BindColor(R.color.color_FF616161) int color_FF616161;
    @BindColor(R.color.color_FF6200EA) int color_FF6200EA;

    public QuestionItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_question, (ViewGroup) itemView, false));
    }

    @Override public void bind(QuestionDataModel $data) {
        super.bind($data);

        boolean isMyQuestion = data.getMemberNo() == App.getInstance().getMemberNo();

        typeTextView.setText(data.getType());
        questionTextView.setText(data.getQuestion());
        questionPanel.setVisibility(View.VISIBLE);
        registDateTextView.setText(data.getRegistDate());

        if (data.isAnswer()) {
            stateImageView.setImageDrawable(dot_purple);
            stateTextView.setText(str_answer_completed);

            answerTextView.setText(data.getAnswer());
            answerDateTextView.setText(data.getAnswerDate());
            answerPanel.setVisibility(View.VISIBLE);
        } else {
            stateImageView.setImageDrawable(dot_gray);
            stateTextView.setText(str_unanswered);
            answerPanel.setVisibility(View.GONE);
        }

        if (isMyQuestion) {
            nickTextView.setText(str_my_question);
            nickTextView.setTextColor(color_FF6200EA);

            if (data.isSecret()) {
                mySecretImageView.setVisibility(View.VISIBLE);
            } else {
                mySecretImageView.setVisibility(View.GONE);
            }
        } else {
            nickTextView.setText(data.getNick());
            nickTextView.setTextColor(color_FF616161);

            if (data.isSecret()) {
                questionPanel.setVisibility(View.GONE);
                answerPanel.setVisibility(View.GONE);
                secretPanel.setVisibility(View.VISIBLE);
            } else {
                secretPanel.setVisibility(View.GONE);
            }
        }
    }
}
