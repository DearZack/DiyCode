package io.github.dearzack.diycode.relate;

import com.gcssloop.diycode_sdk.api.Diycode;

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

    }

    @Override
    public void stop() {

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
}
