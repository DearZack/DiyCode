package io.github.dearzack.diycode.my;

import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.login.event.LogoutEvent;
import com.gcssloop.diycode_sdk.api.user.event.GetMeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/23.
 */

public class MyPresenter implements MyContract.Presenter {
    MyContract.View view;

    @Inject
    public MyPresenter(MyContract.View view) {
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
    public void onGetMe(GetMeEvent event) {
        view.onGetMe(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHeadClick(HeadClickEvent event) {
        view.onHeadClick(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogout(LogoutEvent event) {
        view.onLogout(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNormalItemClick(NormalItemClickEvent event) {
        view.onNormalItemClick(event);
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
