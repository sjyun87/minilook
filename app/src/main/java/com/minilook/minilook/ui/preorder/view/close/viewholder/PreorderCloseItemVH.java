package com.minilook.minilook.ui.preorder.view.close.viewholder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindDrawable;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.preorder.PreorderPresenterImpl;

public class PreorderCloseItemVH extends _BaseViewHolder<PreorderDataModel> {

    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_brand) TextView brandTextView;
    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_desc) TextView descTextView;

    @BindDrawable(R.drawable.ph_square) Drawable img_placeholder;

    public PreorderCloseItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_preorder_close, (ViewGroup) itemView, false));
    }

    @Override public void bind(PreorderDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getThumbUrl())
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);

        brandTextView.setText(data.getBrandName());
        titleTextView.setText(data.getTitle());
        descTextView.setText(data.getDesc());

        itemView.setOnClickListener(this::onItemClick);
    }

    private void onItemClick(View view) {
        RxBus.send(new PreorderPresenterImpl.RxBusEventPreorderClick(data.getPreorderNo()));
    }
}
