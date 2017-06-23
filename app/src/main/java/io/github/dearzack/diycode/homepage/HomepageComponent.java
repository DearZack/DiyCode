package io.github.dearzack.diycode.homepage;

import dagger.Component;

/**
 * Created by Zack on 2017/6/23.
 */

@Component(modules = HomepagePresenterModule.class)
public interface HomepageComponent {
    void inject(HomepageFragment fragment);
}
