package io.github.dearzack.diycode.sites;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zack on 2017/6/23.
 */

@Module
public class SitesPresenterModule {
    private final SitesContract.View view;

    public SitesPresenterModule(SitesContract.View view) {
        this.view = view;
    }

    @Provides
    SitesContract.View provideSitesContractView() {
        return view;
    }
}
