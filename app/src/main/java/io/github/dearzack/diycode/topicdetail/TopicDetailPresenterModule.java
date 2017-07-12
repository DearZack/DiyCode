package io.github.dearzack.diycode.topicdetail;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zack on 2017/6/22.
 */

@Module
public class TopicDetailPresenterModule {

    private final TopicDetailContract.View view;

    public TopicDetailPresenterModule(TopicDetailContract.View view) {
        this.view = view;
    }

    @Provides
    TopicDetailContract.View provideTopicDetailContractView() {
        return view;
    }


}
