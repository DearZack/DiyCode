package io.github.dearzack.diycode.notice;

import com.gcssloop.diycode_sdk.api.login.event.LoginEvent;
import com.gcssloop.diycode_sdk.api.login.event.LogoutEvent;
import com.gcssloop.diycode_sdk.api.notifications.event.GetNotificationsListEvent;

import io.github.dearzack.diycode.base.BasePresenter;
import io.github.dearzack.diycode.base.BaseView;

/**
 * Created by Zack on 2017/6/23.
 */

public interface NoticeContract {
    interface View extends BaseView<Presenter> {
        void onGetNotice(GetNotificationsListEvent event);
        void onLogout(LogoutEvent event);
        void onLogin(LoginEvent event);
    }

    interface Presenter extends BasePresenter {
        void getNotice(int offset, int limit);
    }
}
