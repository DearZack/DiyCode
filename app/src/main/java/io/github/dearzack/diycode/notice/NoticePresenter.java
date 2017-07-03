package io.github.dearzack.diycode.notice;

import com.gcssloop.diycode_sdk.api.Diycode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/23.
 */

public class NoticePresenter implements NoticeContract.Presenter {
    NoticeContract.View view;

    @Inject
    public NoticePresenter(NoticeContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
//        EventBus.getDefault().register(this);
    }

    @Override
    public void stop() {
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getMe() {
        Diycode.getSingleInstance().getMe();
    }

    @Override
    public void logout() {
        Diycode.getSingleInstance().logout();
    }
}
