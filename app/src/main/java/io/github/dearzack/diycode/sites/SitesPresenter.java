package io.github.dearzack.diycode.sites;

import com.gcssloop.diycode_sdk.api.Diycode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/23.
 */

public class SitesPresenter implements SitesContract.Presenter {
    SitesContract.View view;

    @Inject
    public SitesPresenter(SitesContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void getSitesList(String type) {
        Diycode.getSingleInstance().getSites();
    }
}
