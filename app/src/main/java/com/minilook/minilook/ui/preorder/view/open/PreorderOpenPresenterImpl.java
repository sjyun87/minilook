package com.minilook.minilook.ui.preorder.view.open;

import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.lookbook.LookBookPresenterImpl;
import com.minilook.minilook.ui.preorder.PreorderPresenterImpl;
import com.minilook.minilook.ui.preorder.view.open.di.PreorderOpenArguments;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class PreorderOpenPresenterImpl extends BasePresenterImpl implements PreorderOpenPresenter {

    private final View view;
    private final BaseAdapterDataModel<PreorderDataModel> adapter;

    public PreorderOpenPresenterImpl(PreorderOpenArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
        //reqPreorderOpen();
        setupData();
    }

    private void reqPreorderOpen() {

    }

    private void setupData() {
        List<PreorderDataModel> items = new ArrayList<>();

        PreorderDataModel model1 = new PreorderDataModel();
        model1.setFlag(0);
        model1.setId(0);
        model1.setBrand("아델리");
        model1.setTitle("아이 체형에 딱 맞춘 기능성 잠옷");
        model1.setDesc("아이가 잠옷이 불편해서 잠을 설치는 일은 더이상 없도록 통기성과 신축성이 뛰어난 원단을 활용한 기능성 잠옷");
        model1.setDate_end("12시간 20분 55초");
        items.add(model1);

        PreorderDataModel model2 = new PreorderDataModel();
        model2.setFlag(0);
        model2.setId(1);
        model2.setBrand("퓨어오가닉");
        model2.setTitle("코디 걱정 없는 인기만점 데일리 등원룩 완성");
        model2.setDesc("아이가 잠옷이 불편해서 잠을 설치는 일은 더이상 없도록 통기성과 신축성이 뛰어난 원단을 활용한 기능성 잠옷");
        model2.setDate_end("1시간 9분 2초");
        items.add(model2);

        adapter.set(items);
        view.refresh();
    }
}