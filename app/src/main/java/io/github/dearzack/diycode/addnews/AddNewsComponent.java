package io.github.dearzack.diycode.addnews;

import dagger.Component;

/**
 * Created by Zack on 2017/6/22.
 */

@Component(modules = AddNewsPresenterModule.class)
public interface AddNewsComponent {
    void inject(AddNewsActivity activity);
}
