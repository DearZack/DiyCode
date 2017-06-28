package io.github.dearzack.diycode.news;

import com.gcssloop.diycode_sdk.api.Diycode;

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

    }

    @Override
    public void getNewsList(String type, int offset, int limit) {
        Diycode.getSingleInstance().getNewsList(null, offset, limit);
    }
}
