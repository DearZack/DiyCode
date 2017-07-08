package io.github.dearzack.diycode.personal;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zack on 2017/6/22.
 */

@Module
public class PersonalPresenterModule {

    private final PersonalContract.View view;

    public PersonalPresenterModule(PersonalContract.View view) {
        this.view = view;
    }

    @Provides
    PersonalContract.View provideLoginContractView() {
        return view;
    }


}
