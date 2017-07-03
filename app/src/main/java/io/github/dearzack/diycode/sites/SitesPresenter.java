package io.github.dearzack.diycode.sites;

import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.sites.event.GetSitesEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBus.getDefault().register(this);
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetList(GetSitesEvent event) {
        view.onGetSiteList(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSiteClick(SiteClickEvent event) {
        view.onSiteClick(event);
    }

    @Override
    public void getSitesList(String type) {
        Diycode.getSingleInstance().getSites();
    }
}
