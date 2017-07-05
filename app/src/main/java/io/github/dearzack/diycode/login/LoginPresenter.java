package io.github.dearzack.diycode.login;

import android.support.annotation.NonNull;

import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.login.event.LoginEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBus.getDefault().register(this);
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void login(@NonNull String account, @NonNull String password) {
        Diycode.getSingleInstance().login(account, password);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogin(LoginEvent event) {
        view.onLogin(event);
    }
}
