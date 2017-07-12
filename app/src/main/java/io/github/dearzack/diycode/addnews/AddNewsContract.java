package io.github.dearzack.diycode.addnews;

import com.gcssloop.diycode_sdk.api.news.event.CreateNewsEvent;
import com.gcssloop.diycode_sdk.api.news.event.GetNewsNodesListEvent;

import io.github.dearzack.diycode.base.BasePresenter;
import io.github.dearzack.diycode.base.BaseView;

/**
 * Created by Zack on 2017/6/21.
 */

public interface AddNewsContract {

    interface View extends BaseView<Presenter> {
        void onGetNewsNodesList(GetNewsNodesListEvent event);
        void onCreateNewsCallBack(CreateNewsEvent event);
    }

    interface Presenter extends BasePresenter {
        void getNewsNodesList();
        void createNews(String title, String address, int nodeId);
    }
}
