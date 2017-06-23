package io.github.dearzack.diycode.homepage;

import android.os.Bundle;
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
        return new String[]{"TOPICS", "NEWS", "SITES"};
    }

    @Provides
    List<Fragment> provideFragments(String[] titles) {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            NormalFragment fragment = new NormalFragment();
            Bundle bundle = new Bundle();
            bundle.putString("text", titles[i]);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        return fragments;
    }
}
