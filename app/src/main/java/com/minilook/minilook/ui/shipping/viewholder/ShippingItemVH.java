package com.minilook.minilook.ui.shipping.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.shipping.ShippingDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.shipping.ShippingPresenterImpl;

public class ShippingItemVH extends _BaseViewHolder<ShippingDataModel> {

    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.txt_default) TextView defaultTextView;
    @BindView(R.id.txt_phone) TextView phoneTextView;
    @BindView(R.id.txt_address) TextView addressTextView;

    @BindString(R.string.shipping_address) String format_address;

    public ShippingItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_shipping, (ViewGroup) itemView, false));
    }

    @Override public void bind(ShippingDataModel $data) {
        super.bind($data);

        nameTextView.setText(data.getName());
        if (data.isDefault()) {
            defaultTextView.setVisibility(View.VISIBLE);
        } else {
            defaultTextView.setVisibility(View.GONE);
        }
        phoneTextView.setText(data.getPhone());
        addressTextView.setText(
            String.format(format_address, data.getZipcode(), data.getAddress(), data.getAddressDetail()));

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        RxBus.send(new ShippingPresenterImpl.RxEventShippingSelectClick(data));
    }

    @OnClick(R.id.img_delete)
    void onDeleteClick() {
        RxBus.send(new ShippingPresenterImpl.RxEventShippingDeleteClick(data));
    }

    @OnClick(R.id.txt_edit)
    void onEditClick() {
        RxBus.send(new ShippingPresenterImpl.RxEventShippingEditClick(data));
    }
}
