package io.github.dearzack.diycode.topics;

import dagger.Component;

/**
 * Created by Zack on 2017/6/23.
 */

@Component(modules = TopicsPresenterModule.class)
public interface TopicsComponent {
    void inject(TopicsFragment fragment);
}
