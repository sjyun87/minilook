package com.minilook.minilook.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.common.CommonRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseDialog;
import com.minilook.minilook.util.KeyboardUtil;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class RegistCouponDialog extends BaseDialog {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final CommonRequest commonRequest = new CommonRequest();

    @BindView(R.id.edit_code) EditText codeEditText;
    @BindView(R.id.txt_error) TextView errorTextView;

    @BindString(R.string.dialog_regist_coupon_invalid) String str_invalid;
    @BindString(R.string.dialog_regist_coupon_duplicate) String str_duplicate;

    public RegistCouponDialog(@NonNull Context context) {
        super(context);
    }

    @Override protected int getLayoutID() {
        return R.layout.dialog_regist_coupon;
    }

    @Override public void onDetachedFromWindow() {
        disposable.clear();
        super.onDetachedFromWindow();
    }

    private void registCoupon(String couponCode) {
        disposable.add(commonRequest.registCoupon(couponCode)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.OVERLAP_DATA)) {
                    errorTextView.setText(str_duplicate);
                    errorTextView.setVisibility(View.VISIBLE);
                } else if (code.equals(HttpCode.BAD_REQUEST)) {
                    errorTextView.setText(str_invalid);
                    errorTextView.setVisibility(View.VISIBLE);
                }
                return code.equals(HttpCode.OK);
            })
            .subscribe(this::onResRegistCoupon, Timber::e)
        );
    }

    private void onResRegistCoupon(BaseDataModel model) {
        RxBus.send(new RxEventCouponRegistCompleted());
        this.dismiss();
    }

    @OnClick(R.id.txt_cancel)
    void onCancelClick() {
        this.dismiss();
    }

    @OnClick(R.id.txt_regist)
    void onRegistClick() {
        KeyboardUtil.hide(codeEditText);
        String couponCode = codeEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(couponCode)) {
            registCoupon(couponCode);
        }
    }

    @AllArgsConstructor @Getter public final static class RxEventCouponRegistCompleted {
    }
}
