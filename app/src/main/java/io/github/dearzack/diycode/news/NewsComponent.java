package io.github.dearzack.diycode.news;

import dagger.Component;

/**
 * Created by Zack on 2017/6/23.
 */

@Component(modules = NewsPresenterModule.class)
public interface NewsComponent {
    void inject(NewsFragment fragment);
}
