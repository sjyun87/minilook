package com.minilook.minilook.ui.main.fragment.lookbook.view.preview.viewholder;

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
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseViewHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class LookBookImageModuleVH extends BaseViewHolder<LookBookModuleDataModel> {

    @BindView(R.id.img_lookbook) ImageView bgImageView;
    @BindView(R.id.layout_content_panel) LinearLayout contentPanel;
    @BindView(R.id.txt_label) TextView labelTextView;
    @BindView(R.id.txt_title) TextView titleTextView;

    public LookBookImageModuleVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_lookbook_image, (ViewGroup) itemView, false));
    }

    @Override public void bind(LookBookModuleDataModel $data) {
        super.bind($data);
        Timber.e(data.toString());

        Glide.with(itemView)
            .load(data.getBg_url())
            .into(bgImageView);

        contentPanel.setGravity(Gravity.BOTTOM);
        labelTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        titleTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

        itemView.setOnClickListener(v -> RxBus.send(new RxEventPreviewClick()));
    }

    @AllArgsConstructor @Getter public final static class RxEventPreviewClick {
    }
}
