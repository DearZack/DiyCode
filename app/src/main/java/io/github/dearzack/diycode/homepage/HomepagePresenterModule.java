package io.github.dearzack.diycode.homepage;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import io.github.dearzack.diycode.normal.NormalFragment;

/**
 * Created by Zack on 2017/6/23.
 */

@Module
public class HomepagePresenterModule {

    private final HomepageContract.View view;

    public HomepagePresenterModule(HomepageContract.View view) {
        this.view = view;
    }

    @Provides
    HomepageContract.View provideHomepageContractView() {
        return view;
    }

    @Provides
    String[] provideTitles() {
        return new String[]{"主题", "最新", "无回答", "流行", "精品"};
    }

    @Provides
    List<Fragment> provideFragments(String[] titles) {
        List<Fragment> fragments = new ArrayList<>();
        String[] types = new String[]{"last_actived", "recent", "no_reply", "popular", "excellent"};
        for (int i = 0; i < titles.length; i++) {
            NormalFragment fragment = NormalFragment.newInstance(types[i]);
            fragments.add(fragment);
        }
        return fragments;
    }
}
