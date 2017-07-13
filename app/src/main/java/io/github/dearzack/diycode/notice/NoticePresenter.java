package io.github.dearzack.diycode.notice;

import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.login.event.LoginEvent;
import com.gcssloop.diycode_sdk.api.login.event.LogoutEvent;
import com.gcssloop.diycode_sdk.api.notifications.event.GetNotificationsListEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBus.getDefault().register(this);
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getNotice(int offset, int limit) {
        Diycode.getSingleInstance().getNotificationsList(offset, limit);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetNotice(GetNotificationsListEvent event) {
        view.onGetNotice(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogout(LogoutEvent event) {
        view.onLogout(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogin(LoginEvent event) {
        view.onLogin(event);
    }
}
