package io.github.dearzack.diycode.normal;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zack on 2017/6/23.
 */

@Module
public class NormalPresenterModule {
    private final NormalContract.View view;

    public NormalPresenterModule(NormalContract.View view) {
        this.view = view;
    }

    @Provides
    NormalContract.View provideNormalContractView() {
        return view;
    }
}
