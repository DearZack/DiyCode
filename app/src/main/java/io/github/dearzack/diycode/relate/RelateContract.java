package io.github.dearzack.diycode.relate;

import com.gcssloop.diycode_sdk.api.user.event.GetUserCollectionTopicListEvent;
import com.gcssloop.diycode_sdk.api.user.event.GetUserCreateTopicListEvent;
import com.gcssloop.diycode_sdk.api.user.event.GetUserReplyTopicListEvent;

import io.github.dearzack.diycode.base.BasePresenter;
import io.github.dearzack.diycode.base.BaseView;

/**
 * Created by Zack on 2017/6/21.
 */

public interface RelateContract {

    interface View extends BaseView<Presenter> {
        void onGetMyTopics(GetUserCreateTopicListEvent event);
        void onGetMyFavorites(GetUserCollectionTopicListEvent event);
        void onGetMyReplies(GetUserReplyTopicListEvent event);
    }

    interface Presenter extends BasePresenter {
        void getMyTopics(String loginName, int offset, int limit);
        void getMyFavorites(String loginName, int offset, int limit);
        void getMyReplies(String loginName, int offset, int limit);
    }
}
