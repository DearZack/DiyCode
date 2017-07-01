package io.github.dearzack.diycode.relate;

import dagger.Component;

/**
 * Created by Zack on 2017/6/22.
 */

@Component(modules = RelatePresenterModule.class)
public interface RelateComponent {
    void inject(RelateActivity activity);
}
