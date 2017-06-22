package io.github.dearzack.diycode.login;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zack on 2017/6/22.
 */

@Module
public class LoginPresenterModule {

    private final LoginContract.View view;

    public LoginPresenterModule(LoginContract.View view) {
        this.view = view;
    }

    @Provides
    LoginContract.View provideLoginContractView() {
        return view;
    }

    @Provides
    int provideAge() {
        return 500;
    }

}
