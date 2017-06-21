package io.github.dearzack.diycode.login;

import android.support.annotation.NonNull;

import com.gcssloop.diycode_sdk.api.Diycode;

/**
 * Created by Zack on 2017/6/21.
 */

public class LoginPresenter implements LoginContract.Presenter {

    @Override
    public void start() {

    }

    @Override
    public void login(@NonNull String account, @NonNull String password) {
//        Diycode.getSingleInstance().login("diycode888", "password");
        Diycode.getSingleInstance().login(account, password);
    }
}
