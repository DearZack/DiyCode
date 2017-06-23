package io.github.dearzack.diycode.normal;

import dagger.Component;

/**
 * Created by Zack on 2017/6/23.
 */

@Component(modules = NormalPresenterModule.class)
public interface NormalComponent {
    void inject(NormalFragment fragment);
}
