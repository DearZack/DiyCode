package io.github.dearzack.diycode.addtopic;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zack on 2017/6/22.
 */

@Module
public class AddTopicPresenterModule {

    private final AddTopicContract.View view;

    public AddTopicPresenterModule(AddTopicContract.View view) {
        this.view = view;
    }

    @Provides
    AddTopicContract.View provideAddTopicContractView() {
        return view;
    }


}
