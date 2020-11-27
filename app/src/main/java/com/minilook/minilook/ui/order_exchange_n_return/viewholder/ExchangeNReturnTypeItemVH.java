package com.minilook.minilook.ui.order_exchange_n_return.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.order_exchange_n_return.OrderExchangeNReturnPresenterImpl;

public class ExchangeNReturnTypeItemVH extends _BaseViewHolder<CodeDataModel> {

    @BindView(R.id.txt_name) TextView nameTextView;

    public ExchangeNReturnTypeItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_exchage_n_reason_selectbox, (ViewGroup) itemView, false));
    }

    @Override public void bind(CodeDataModel $data) {
        super.bind($data);

        nameTextView.setText(data.getName());

        itemView.setOnClickListener(this::onItemClick);
    }

    private void onItemClick(View view) {
        RxBus.send(new OrderExchangeNReturnPresenterImpl.RxEventSelectedType(data));
    }
}
