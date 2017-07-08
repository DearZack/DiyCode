package io.github.dearzack.diycode.reply;

import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.topic.event.CreateTopicReplyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/21.
 */

public class ReplyPresenter implements ReplyContract.Presenter {

    ReplyContract.View view;

    @Inject
    public ReplyPresenter(ReplyContract.View view) {
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
    public void reply(int id, String content) {
        Diycode.getSingleInstance().createTopicReply(id, content);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReplyCallback(CreateTopicReplyEvent event) {
        view.onReplyCallback(event);
    }
}
