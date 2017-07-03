package io.github.dearzack.diycode.web;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/21.
 */

public class WebPresenter implements WebContract.Presenter {

    WebContract.View view;

    @Inject
    public WebPresenter(WebContract.View view) {
        this.view = view;
    }

//    @Inject
//    void setupListeners() {
//        view.setPresenter(this);
//    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

}
