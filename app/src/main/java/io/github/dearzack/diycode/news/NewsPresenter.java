package io.github.dearzack.diycode.news;

import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.news.event.GetNewsListEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/23.
 */

public class NewsPresenter implements NewsContract.Presenter {
    NewsContract.View view;

    @Inject
    public NewsPresenter(NewsContract.View view) {
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
    public void onGetList(GetNewsListEvent event) {
        view.onGetNewsList(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onItemClick(NewsClickEvent event) {
        view.onItemClick(event);
    }

    @Override
    public void getNewsList(String type, int offset, int limit) {
        Diycode.getSingleInstance().getNewsList(null, offset, limit);
    }
}
