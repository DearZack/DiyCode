package io.github.dearzack.diycode.my;

import com.gcssloop.diycode_sdk.api.Diycode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/23.
 */

public class MyPresenter implements MyContract.Presenter {
    MyContract.View view;

    @Inject
    public MyPresenter(MyContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void getMe() {
        Diycode.getSingleInstance().getMe();
    }

    @Override
    public void logout() {
        Diycode.getSingleInstance().logout();
    }
}
