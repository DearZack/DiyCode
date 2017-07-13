package io.github.dearzack.diycode.addtopic;

import com.gcssloop.diycode_sdk.api.topic.event.CreateTopicEvent;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicNodeListEvent;

import io.github.dearzack.diycode.base.BasePresenter;
import io.github.dearzack.diycode.base.BaseView;

/**
 * Created by Zack on 2017/6/21.
 */

public interface AddTopicContract {

    interface View extends BaseView<Presenter> {
        void onGetTopicNodeListCallBack(GetTopicNodeListEvent event);
        void onCreateTopicCallBack(CreateTopicEvent event);
    }

    interface Presenter extends BasePresenter {
        void createTopic(String title, String body, int nodeId);
    }
}
