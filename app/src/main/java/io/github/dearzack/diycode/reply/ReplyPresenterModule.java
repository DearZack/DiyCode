package io.github.dearzack.diycode.reply;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zack on 2017/6/22.
 */

@Module
public class ReplyPresenterModule {

    private final ReplyContract.View view;

    public ReplyPresenterModule(ReplyContract.View view) {
        this.view = view;
    }

    @Provides
    ReplyContract.View provideReplyContractView() {
        return view;
    }


}
