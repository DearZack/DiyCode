package io.github.dearzack.diycode.reply;

import dagger.Component;

/**
 * Created by Zack on 2017/6/22.
 */

@Component(modules = ReplyPresenterModule.class)
public interface ReplyComponent {
    void inject(ReplyActivity activity);
}
