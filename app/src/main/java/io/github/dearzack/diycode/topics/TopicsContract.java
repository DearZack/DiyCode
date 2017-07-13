package io.github.dearzack.diycode.topics;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.topic.event.CreateTopicEvent;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicsListEvent;

import io.github.dearzack.diycode.base.BasePresenter;
import io.github.dearzack.diycode.base.BaseView;

/**
 * Created by Zack on 2017/6/23.
 */

public interface TopicsContract {
    interface View extends BaseView<Presenter> {
        void goToTopicDetailActivity(Topic topic);
        void onGetTopicsList(GetTopicsListEvent event);
        void onCreateTopicCallBack(CreateTopicEvent event);
    }

    interface Presenter extends BasePresenter {
        void getTopicsList(String type, int offset, int limit);
    }
}
