package io.github.dearzack.diycode.my;

import dagger.Component;

/**
 * Created by Zack on 2017/6/23.
 */

@Component(modules = MyPresenterModule.class)
public interface MyComponent {
    void inject(MyFragment fragment);
}
