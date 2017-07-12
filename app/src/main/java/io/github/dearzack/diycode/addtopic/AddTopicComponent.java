package io.github.dearzack.diycode.addtopic;

import dagger.Component;

/**
 * Created by Zack on 2017/6/22.
 */

@Component(modules = AddTopicPresenterModule.class)
public interface AddTopicComponent {
    void inject(AddTopicActivity activity);
}
