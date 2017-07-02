package io.github.dearzack.diycode.relate;

import io.github.dearzack.diycode.base.BasePresenter;
import io.github.dearzack.diycode.base.BaseView;

/**
 * Created by Zack on 2017/6/21.
 */

public interface RelateContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void getMyTopics(String loginName, int offset, int limit);
        void getMyFavorites(String loginName, int offset, int limit);
        void getMyReplies(String loginName, int offset, int limit);
    }
}
