package io.github.dearzack.diycode.addnews;

import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.news.event.CreateNewsEvent;
import com.gcssloop.diycode_sdk.api.news.event.GetNewsNodesListEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/21.
 */

public class AddNewsPresenter implements AddNewsContract.Presenter {

    AddNewsContract.View view;

    @Inject
    public AddNewsPresenter(AddNewsContract.View view) {
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
    public void getNewsNodesList() {
        Diycode.getSingleInstance().getNewsNodesList();
    }

    @Override
    public void createNews(String title, String address, int nodeId) {
        Diycode.getSingleInstance().createNews(title, address, nodeId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetNewsNodesList(GetNewsNodesListEvent event) {
        view.onGetNewsNodesList(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCreateNewsCallBack(CreateNewsEvent event) {
        view.onCreateNewsCallBack(event);
    }
}
