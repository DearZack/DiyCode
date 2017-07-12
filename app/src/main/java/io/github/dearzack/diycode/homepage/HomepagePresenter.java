package io.github.dearzack.diycode.homepage;

import android.content.Context;
import android.content.Intent;

import com.gcssloop.diycode_sdk.api.Diycode;

import javax.inject.Inject;

import io.github.dearzack.diycode.addnews.AddNewsActivity;
import io.github.dearzack.diycode.login.LoginActivity;

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
    public void add(int pageIndex, Context context) {
        if (!Diycode.getSingleInstance().isLogin()) {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            return;
        }
        switch (pageIndex) {
            case HomepageFragment.PAGE_TOPIC:
                break;
            case HomepageFragment.PAGE_NEW:
                Intent intent = new Intent(context, AddNewsActivity.class);
                context.startActivity(intent);
                break;
        }
    }
}
