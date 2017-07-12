package io.github.dearzack.diycode.relate;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zack on 2017/6/22.
 */

@Module
public class RelatePresenterModule {

    private final RelateContract.View view;

    public RelatePresenterModule(RelateContract.View view) {
        this.view = view;
    }

    @Provides
    RelateContract.View provideRelateContractView() {
        return view;
    }


}
