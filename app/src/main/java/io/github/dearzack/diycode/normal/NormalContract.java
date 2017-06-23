package io.github.dearzack.diycode.normal;

import io.github.dearzack.diycode.base.BasePresenter;
import io.github.dearzack.diycode.base.BaseView;

/**
 * Created by Zack on 2017/6/23.
 */

public interface NormalContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void getList();
    }
}
