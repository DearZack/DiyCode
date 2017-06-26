package io.github.dearzack.diycode.sites;

import dagger.Component;

/**
 * Created by Zack on 2017/6/23.
 */

@Component(modules = SitesPresenterModule.class)
public interface SitesComponent {
    void inject(SitesFragment fragment);
}
