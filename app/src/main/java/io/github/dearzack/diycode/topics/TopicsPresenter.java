package io.github.dearzack.diycode.topics;

import com.gcssloop.diycode_sdk.api.Diycode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/23.
 */

public class TopicsPresenter implements TopicsContract.Presenter {
    TopicsContract.View view;

    @Inject
    public TopicsPresenter(TopicsContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void getTopicsList(String type) {
        Diycode.getSingleInstance().getTopicsList(null, null, 0, 10);
    }
}
