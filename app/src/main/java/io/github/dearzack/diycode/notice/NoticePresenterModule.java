package io.github.dearzack.diycode.notice;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zack on 2017/6/23.
 */

@Module
public class NoticePresenterModule {
    private final NoticeContract.View view;

    public NoticePresenterModule(NoticeContract.View view) {
        this.view = view;
    }

    @Provides
    NoticeContract.View provideNormalContractView() {
        return view;
    }
}
