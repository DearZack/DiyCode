package io.github.dearzack.diycode.homepage;

import android.util.Log;

import com.gcssloop.diycode_sdk.api.Diycode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/23.
 */

public class HomepagePresenter implements HomepageContract.Presenter {

    HomepageContract.View view;

    @Inject
    public HomepagePresenter(HomepageContract.View view) {
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
    public void add() {
        Log.e("zhouxiong", "zzzzzz");
    }

    @Override
    public void getNodes() {
        Diycode.getSingleInstance().getNewsNodesList();
    }
}
