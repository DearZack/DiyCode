package io.github.dearzack.diycode.personal;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/21.
 */

public class PersonalDetailPresenter implements PersonalDetailContract.Presenter {

    PersonalDetailContract.View view;

    @Inject
    public PersonalDetailPresenter(PersonalDetailContract.View view) {
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
