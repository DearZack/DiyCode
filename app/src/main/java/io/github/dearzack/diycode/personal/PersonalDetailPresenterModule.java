package io.github.dearzack.diycode.personal;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zack on 2017/6/22.
 */

@Module
public class PersonalDetailPresenterModule {

    private final PersonalDetailContract.View view;

    public PersonalDetailPresenterModule(PersonalDetailContract.View view) {
        this.view = view;
    }

    @Provides
    PersonalDetailContract.View provideLoginContractView() {
        return view;
    }


}
