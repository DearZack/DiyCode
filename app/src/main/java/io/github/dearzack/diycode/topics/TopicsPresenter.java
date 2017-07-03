package io.github.dearzack.diycode.topics;

import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicsListEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/23.
 */

public class TopicsPresenter implements TopicsContract.Presenter {
    TopicsContract.View view;

    @Inject
    public TopicsPresenter(TopicsContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTopicsItemClick(TopicsClickEvent event) {
        Topic topic = event.getMessage();
        view.goToTopicDetailActivity(topic);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetList(GetTopicsListEvent event) {
        view.onGetTopicsList(event);
    }

    @Override
    public void getTopicsList(String type, int offset, int limit) {
        Diycode.getSingleInstance().getTopicsList(null, null, offset, limit);
    }
}
