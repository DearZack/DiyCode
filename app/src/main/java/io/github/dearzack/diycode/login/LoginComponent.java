package io.github.dearzack.diycode.login;

import dagger.Component;

/**
 * Created by Zack on 2017/6/22.
 */

@Component(modules = LoginPresenterModule.class)
public interface LoginComponent {
    void inject(LoginActivity activity);
}
