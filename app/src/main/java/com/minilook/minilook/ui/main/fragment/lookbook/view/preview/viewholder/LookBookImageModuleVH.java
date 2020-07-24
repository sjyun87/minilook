package com.minilook.minilook.ui.main.fragment.lookbook.view.preview.viewholder;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.lookbook.LookBookPreviewDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.main.fragment.lookbook.LookBookPresenterImpl;

public class LookBookImageModuleVH extends BaseViewHolder<LookBookPreviewDataModel> {

    @BindView(R.id.img_lookbook) ImageView bgImageView;
    @BindView(R.id.layout_content_panel) LinearLayout contentPanel;
    @BindView(R.id.txt_label) TextView labelTextView;
    @BindView(R.id.txt_title) TextView titleTextView;

    public LookBookImageModuleVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_lookbook_image, (ViewGroup) itemView, false));
    }

    @Override public void bind(LookBookPreviewDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getUrl_image())
            .into(bgImageView);

        switch (data.getPosition()) {
            case 0:
                contentPanel.setGravity(Gravity.TOP);
                labelTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                titleTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                break;

            case 1:
                contentPanel.setGravity(Gravity.TOP);
                labelTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                titleTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                break;

            case 2:
                contentPanel.setGravity(Gravity.BOTTOM);
                labelTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                titleTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                break;

            case 3:
                contentPanel.setGravity(Gravity.BOTTOM);
                labelTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                titleTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                break;
        }

        titleTextView.setTextColor(Color.parseColor(data.getColor()));
        labelTextView.setTextColor(Color.parseColor(data.getColor()));

        titleTextView.setText(data.getTitle());
        labelTextView.setText(data.getLabel());

        itemView.setOnClickListener(v -> RxBus.send(new LookBookPresenterImpl.RxEventNavigateToDetail(true)));
    }
}
