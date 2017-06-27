package io.github.dearzack.diycode.web;

import android.support.annotation.NonNull;

import com.gcssloop.diycode_sdk.api.Diycode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/21.
 */

public class WebPresenter implements WebContract.Presenter {

    WebContract.View view;

    @Inject
    public WebPresenter(WebContract.View view) {
        this.view = view;
    }

//    @Inject
//    void setupListeners() {
//        view.setPresenter(this);
//    }

    @Override
    public void start() {

    }

    @Override
    public void login(@NonNull String account, @NonNull String password) {
//        Diycode.getSingleInstance().login("diycode888", "password");
        Diycode.getSingleInstance().login(account, password);
    }
}
