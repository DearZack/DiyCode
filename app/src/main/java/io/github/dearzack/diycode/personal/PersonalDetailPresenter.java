package io.github.dearzack.diycode.personal;

import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.user.event.GetUserCreateTopicListEvent;
import com.gcssloop.diycode_sdk.api.user.event.GetUserEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/21.
 */

public class PersonalDetailPresenter implements PersonalDetailContract.Presenter {

    PersonalDetailContract.View view;

    @Inject
    public PersonalDetailPresenter(PersonalDetailContract.View view) {
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
    public void getUser(String loginName) {
        Diycode.getSingleInstance().getUser(loginName);
    }

    @Override
    public void getTopics(String loginName, int offset, int limit) {
        Diycode.getSingleInstance().getUserCreateTopicList(loginName, null, offset, limit);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetUser(GetUserEvent event) {
        view.onGetUser(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetTopics(GetUserCreateTopicListEvent event) {
        view.onGetTopics(event);
    }

}
