package io.github.dearzack.diycode.web;

import dagger.Component;

/**
 * Created by Zack on 2017/6/22.
 */

@Component(modules = WebPresenterModule.class)
public interface WebComponent {
    void inject(WebActivity activity);
}
