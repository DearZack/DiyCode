package io.github.dearzack.diycode.notice;

import dagger.Component;

/**
 * Created by Zack on 2017/6/23.
 */

@Component(modules = NoticePresenterModule.class)
public interface NoticeComponent {
    void inject(NoticeFragment fragment);
}
