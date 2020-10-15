package com.minilook.minilook.ui.preorder.view.open.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.preorder.PreorderPresenterImpl;

public class PreorderOpenHeaderVH extends BaseViewHolder<PreorderDataModel> {

    public PreorderOpenHeaderVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_preorder_open_header, (ViewGroup) itemView, false));
    }

    @OnClick(R.id.img_info)
    void onInfoClick() {
        RxBus.send(new PreorderPresenterImpl.RxBusEventPreorderInfoClick());
    }
}
