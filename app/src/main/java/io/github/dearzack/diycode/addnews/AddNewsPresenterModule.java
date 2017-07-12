package io.github.dearzack.diycode.addnews;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zack on 2017/6/22.
 */

@Module
public class AddNewsPresenterModule {

    private final AddNewsContract.View view;

    public AddNewsPresenterModule(AddNewsContract.View view) {
        this.view = view;
    }

    @Provides
    AddNewsContract.View provideAddNewsContractView() {
        return view;
    }


}
