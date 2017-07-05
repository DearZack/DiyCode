package io.github.dearzack.diycode.personal;

import dagger.Component;

/**
 * Created by Zack on 2017/6/22.
 */

@Component(modules = PersonalDetailPresenterModule.class)
public interface PersonalDetailComponent {
    void inject(PersonalActivity activity);
}
