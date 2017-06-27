package io.github.dearzack.diycode.web;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zack on 2017/6/22.
 */

@Module
public class WebPresenterModule {

    private final WebContract.View view;

    public WebPresenterModule(WebContract.View view) {
        this.view = view;
    }

    @Provides
    WebContract.View provideLoginContractView() {
        return view;
    }


}
