package io.github.dearzack.diycode.sites;

import com.gcssloop.diycode_sdk.api.sites.event.GetSitesEvent;

import io.github.dearzack.diycode.base.BasePresenter;
import io.github.dearzack.diycode.base.BaseView;

/**
 * Created by Zack on 2017/6/23.
 */

public interface SitesContract {
    interface View extends BaseView<Presenter> {
        void onGetSiteList(GetSitesEvent event);
        void onSiteClick(SiteClickEvent event);
    }

    interface Presenter extends BasePresenter {
        void getSitesList(String type);
    }
}
