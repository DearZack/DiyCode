package io.github.dearzack.diycode.news;

import com.gcssloop.diycode_sdk.api.news.event.GetNewsListEvent;

import io.github.dearzack.diycode.base.BasePresenter;
import io.github.dearzack.diycode.base.BaseView;

/**
 * Created by Zack on 2017/6/23.
 */

public interface NewsContract {
    interface View extends BaseView<Presenter> {
        void onGetNewsList(GetNewsListEvent event);
        void onItemClick(NewsClickEvent event);
    }

    interface Presenter extends BasePresenter {
        void getNewsList(String type, int offset, int limit);
    }
}
