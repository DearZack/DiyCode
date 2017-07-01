package io.github.dearzack.diycode.relate;

import android.support.annotation.NonNull;

import io.github.dearzack.diycode.base.BasePresenter;
import io.github.dearzack.diycode.base.BaseView;

/**
 * Created by Zack on 2017/6/21.
 */

public interface RelateContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void login(@NonNull String account, @NonNull String password);
    }
}
