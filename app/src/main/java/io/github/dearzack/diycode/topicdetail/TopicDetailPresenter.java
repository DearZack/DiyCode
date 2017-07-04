package io.github.dearzack.diycode.topicdetail;

import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicEvent;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicRepliesListEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/21.
 */

public class TopicDetailPresenter implements TopicDetailContract.Presenter {

    TopicDetailContract.View view;

    @Inject
    public TopicDetailPresenter(TopicDetailContract.View view) {
        this.view = view;
    }

//    @Inject
//    void setupListeners() {
//        view.setPresenter(this);
//    }

    @Override
    public void start() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getTopic(int id) {
        Diycode.getSingleInstance().getTopic(id);
    }

    @Override
    public void getTopicRepliesList(int id, int offset, int limit) {
        Diycode.getSingleInstance().getTopicRepliesList(id, offset, limit);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetTopic(GetTopicEvent event) {
        view.showTopic(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetTopicRepliesList(GetTopicRepliesListEvent event) {
        view.showTopicReplies(event);
    }
}
