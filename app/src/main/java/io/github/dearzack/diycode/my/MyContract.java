package io.github.dearzack.diycode.my;

import com.gcssloop.diycode_sdk.api.login.event.LogoutEvent;
import com.gcssloop.diycode_sdk.api.user.event.GetMeEvent;

import io.github.dearzack.diycode.base.BasePresenter;
import io.github.dearzack.diycode.base.BaseView;

/**
 * Created by Zack on 2017/6/23.
 */

public interface MyContract {
    interface View extends BaseView<Presenter> {
        void onGetMe(GetMeEvent event);
        void onHeadClick(HeadClickEvent event);
        void onLogout(LogoutEvent event);
        void onNormalItemClick(NormalItemClickEvent event);
    }

    interface Presenter extends BasePresenter {
        void getMe();
        void logout();
    }
}
