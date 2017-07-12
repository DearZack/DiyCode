package io.github.dearzack.diycode.homepage;

import android.content.Context;

import io.github.dearzack.diycode.base.BasePresenter;
import io.github.dearzack.diycode.base.BaseView;

/**
 * Created by Zack on 2017/6/23.
 */

public interface HomepageContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void add(int pageIndex, Context context);
    }
}
