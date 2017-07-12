package io.github.dearzack.diycode.my;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zack on 2017/6/23.
 */

@Module
public class MyPresenterModule {
    private final MyContract.View view;

    public MyPresenterModule(MyContract.View view) {
        this.view = view;
    }

    @Provides
    MyContract.View provideMyContractView() {
        return view;
    }
}
