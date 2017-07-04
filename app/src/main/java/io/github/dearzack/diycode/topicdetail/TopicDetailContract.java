package io.github.dearzack.diycode.topicdetail;

import com.gcssloop.diycode_sdk.api.topic.event.GetTopicEvent;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicRepliesListEvent;

import io.github.dearzack.diycode.base.BasePresenter;
import io.github.dearzack.diycode.base.BaseView;

/**
 * Created by Zack on 2017/6/21.
 */

public interface TopicDetailContract {

    interface View extends BaseView<Presenter> {
        void showTopic(GetTopicEvent event);
        void showTopicReplies(GetTopicRepliesListEvent event);
    }

    interface Presenter extends BasePresenter {
        void getTopic(int id);
        void getTopicRepliesList(int id, int offset, int limit);
    }
}
