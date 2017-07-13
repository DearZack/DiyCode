package io.github.dearzack.diycode.addtopic;

import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.topic.event.CreateTopicEvent;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicNodeListEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/21.
 */

public class AddTopicPresenter implements AddTopicContract.Presenter {

    AddTopicContract.View view;

    @Inject
    public AddTopicPresenter(AddTopicContract.View view) {
        this.view = view;
    }

//    @Inject
//    void setupListeners() {
//        view.setPresenter(this);
//    }

    @Override
    public void start() {
        EventBus.getDefault().register(this);
        Diycode.getSingleInstance().getTopicNodesList();
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void createTopic(String title, String body, int nodeId) {
        Diycode.getSingleInstance().createTopic(title, body, nodeId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetTopicNodeListCallBack(GetTopicNodeListEvent event) {
        view.onGetTopicNodeListCallBack(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCreateTopicCallBack(CreateTopicEvent event) {
        view.onCreateTopicCallBack(event);
    }
}
