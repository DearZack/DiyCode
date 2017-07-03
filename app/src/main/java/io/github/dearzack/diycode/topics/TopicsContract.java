package io.github.dearzack.diycode.topics;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;

import io.github.dearzack.diycode.base.BasePresenter;
import io.github.dearzack.diycode.base.BaseView;

/**
 * Created by Zack on 2017/6/23.
 */

public interface TopicsContract {
    interface View extends BaseView<Presenter> {
        void goToTopicDetailActivity(Topic topic);
    }

    interface Presenter extends BasePresenter {
        void getTopicsList(String type, int offset, int limit);
    }
}
