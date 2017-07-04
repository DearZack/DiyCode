package io.github.dearzack.diycode.topicdetail;

import dagger.Component;

/**
 * Created by Zack on 2017/6/22.
 */

@Component(modules = TopicDetailPresenterModule.class)
public interface TopicDetailComponent {
    void inject(TopicDetailActivity activity);
}
