package io.github.dearzack.diycode.news;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zack on 2017/6/23.
 */

@Module
public class NewsPresenterModule {
    private final NewsContract.View view;

    public NewsPresenterModule(NewsContract.View view) {
        this.view = view;
    }

    @Provides
    NewsContract.View provideNormalContractView() {
        return view;
    }
}
