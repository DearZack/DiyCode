package io.github.dearzack.diycode.addtopic;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/21.
 */

public class AddTopicPresenter implements AddTopicContract.Presenter {

    AddTopicContract.View view;

    @Inject
    public AddTopicPresenter(AddTopicContract.View view) {
        this.view = view;
    }

//    @Inject
//    void setupListeners() {
//        view.setPresenter(this);
//    }

    @Override
    public void start() {
//        EventBus.getDefault().register(this);
    }

    @Override
    public void stop() {
//        EventBus.getDefault().unregister(this);
    }

}
