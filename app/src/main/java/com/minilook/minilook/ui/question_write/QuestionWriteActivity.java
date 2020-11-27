package com.minilook.minilook.ui.question_write;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindArray;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.question_write.adapter.QuestionTypeAdapter;
import com.minilook.minilook.ui.question_write.di.QuestionWriteArguments;
import com.minilook.minilook.util.DimenUtil;
import java.util.Arrays;

public class QuestionWriteActivity extends _BaseActivity implements QuestionWritePresenter.View {

    public static void start(Context context, int productNo) {
        Intent intent = new Intent(context, QuestionWriteActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("productNo", productNo);
        context.startActivity(intent);
    }

    @BindView(R.id.txt_selected_type) TextView selectedTypeTextView;
    @BindView(R.id.img_type_arrow) ImageView arrowImageView;
    @BindView(R.id.rcv_type) RecyclerView recyclerView;
    @BindView(R.id.edit_question) EditText questionEditText;
    @BindView(R.id.img_secret_check) ImageView secretCheckBoxImageView;
    @BindView(R.id.txt_apply) TextView applyTextView;

    @BindArray(R.array.question_type) String[] types;

    @BindColor(R.color.color_FFA9A9A9) int color_FFA9A9A9;
    @BindColor(R.color.color_FFF5F5F5) int color_FFF5F5F5;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;

    @BindDrawable(R.drawable.ic_arrow_down_black) Drawable img_arrow_down;
    @BindDrawable(R.drawable.ic_arrow_up_black) Drawable img_arrow_up;
    @BindDrawable(R.drawable.ic_checkbox1_off) Drawable img_checkbox_off;
    @BindDrawable(R.drawable.ic_checkbox1_on) Drawable img_checkbox_on;

    @BindString(R.string.toast_question_write) String str_question_write;

    private QuestionWritePresenter presenter;
    private QuestionTypeAdapter adapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_question_write;
    }

    @Override protected void createPresenter() {
        presenter = new QuestionWritePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private QuestionWriteArguments provideArguments() {
        return QuestionWriteArguments.builder()
            .view(this)
            .productNo(getIntent().getIntExtra("productNo", -1))
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuestionTypeAdapter();
        adapter.setOnTypeSelectedListener(data -> presenter.onTypeSelected(data));
        recyclerView.setAdapter(adapter);
        DividerDecoration.builder(this)
            .size(DimenUtil.dpToPx(this, 1))
            .color(color_FFA9A9A9)
            .showLastDivider()
            .build()
            .addTo(recyclerView);

        adapter.set(Arrays.asList(types));
        adapter.refresh();
        presenter.onTypeSelected(adapter.get(0));
    }

    @Override public void showTypeBox() {
        recyclerView.setVisibility(View.VISIBLE);
        arrowImageView.setImageDrawable(img_arrow_up);
    }

    @Override public void hideTypeBox() {
        recyclerView.setVisibility(View.GONE);
        arrowImageView.setImageDrawable(img_arrow_down);
    }

    @Override public void setSelectedType(String type) {
        selectedTypeTextView.setText(type);
    }

    @Override public void setupQuestionEditText() {
        questionEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onTextChanged(s.toString());
            }

            @Override public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override public void checkSecretCheckBox() {
        secretCheckBoxImageView.setImageDrawable(img_checkbox_on);
    }

    @Override public void uncheckSecretCheckBox() {
        secretCheckBoxImageView.setImageDrawable(img_checkbox_off);
    }

    @Override public void enableApplyButton() {
        applyTextView.setEnabled(true);
        applyTextView.setBackgroundColor(color_FF8140E5);
    }

    @Override public void disableApplyButton() {
        applyTextView.setEnabled(false);
        applyTextView.setBackgroundColor(color_FFF5F5F5);
    }

    @Override public void showQuestionWriteToast() {
        Toast.makeText(this, str_question_write, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.layout_type_box_panel)
    void onTypeBoxClick() {
        presenter.onTypeBoxClick();
    }

    @OnClick(R.id.layout_secret_panel)
    void onSecretClick() {
        presenter.onSecretClick();
    }

    @OnClick(R.id.txt_apply)
    void onApplyClick() {
        presenter.onApplyClick();
    }
}
