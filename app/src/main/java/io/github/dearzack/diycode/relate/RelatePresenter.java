package io.github.dearzack.diycode.relate;

import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.user.event.GetUserCollectionTopicListEvent;
import com.gcssloop.diycode_sdk.api.user.event.GetUserCreateTopicListEvent;
import com.gcssloop.diycode_sdk.api.user.event.GetUserReplyTopicListEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/21.
 */

public class RelatePresenter implements RelateContract.Presenter {

    RelateContract.View view;

    @Inject
    public RelatePresenter(RelateContract.View view) {
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
    public void getMyTopics(String loginName, int offset, int limit) {
        Diycode.getSingleInstance().getUserCreateTopicList(loginName, null, offset, limit);
    }

    @Override
    public void getMyFavorites(String loginName, int offset, int limit) {
        Diycode.getSingleInstance().getUserCollectionTopicList(loginName, offset, limit);
    }

    @Override
    public void getMyReplies(String loginName, int offset, int limit) {
        Diycode.getSingleInstance().getUserReplyTopicList(loginName, null, offset, limit);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMyTopics(GetUserCreateTopicListEvent event) {
        view.onGetMyTopics(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMyFavorites(GetUserCollectionTopicListEvent event){
        view.onGetMyFavorites(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMyReplies(GetUserReplyTopicListEvent event) {
        view.onGetMyReplies(event);
    }
}
