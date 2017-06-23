package io.github.dearzack.diycode.normal;

import com.gcssloop.diycode_sdk.api.Diycode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/23.
 */

public class NormalPresenter implements NormalContract.Presenter {
    NormalContract.View view;

    @Inject
    public NormalPresenter(NormalContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        getList();
    }

    @Override
    public void getList() {
        Diycode.getSingleInstance().getTopicsList("last_actived", null, 0, 10);
    }
}
