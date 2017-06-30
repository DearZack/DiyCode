package io.github.dearzack.diycode.login;

import android.support.annotation.NonNull;

import com.gcssloop.diycode_sdk.api.Diycode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/21.
 */

public class LoginPresenter implements LoginContract.Presenter {

    LoginContract.View view;

    @Inject
    public LoginPresenter(LoginContract.View view) {
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
        Diycode.getSingleInstance().login(account, password);
    }
}
