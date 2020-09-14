package com.minilook.minilook.ui.order.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.order.OrderPresenterImpl;

public class MemoItemVH extends BaseViewHolder<String> {

    @BindView(R.id.txt_memo) TextView memoTextView;

    int position;

    public MemoItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_order_memo, (ViewGroup) itemView, false));
    }

    public void bind(int position, String $data) {
        super.bind($data);
        this.position = position;

        memoTextView.setText(data);

        itemView.setOnClickListener(this::onItemClick);
    }

    private void onItemClick(View view) {
        RxBus.send(new OrderPresenterImpl.RxEventMemoSelected(position, data));
    }
}
