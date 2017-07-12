package io.github.dearzack.diycode.topics;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zack on 2017/6/23.
 */

@Module
public class TopicsPresenterModule {
    private final TopicsContract.View view;

    public TopicsPresenterModule(TopicsContract.View view) {
        this.view = view;
    }

    @Provides
    TopicsContract.View provideTopicsContractView() {
        return view;
    }
}
