package io.github.dearzack.diycode.homepage;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import io.github.dearzack.diycode.news.NewsFragment;
import io.github.dearzack.diycode.sites.SitesFragment;
import io.github.dearzack.diycode.topics.TopicsFragment;

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
        TopicsFragment topicsFragment = TopicsFragment.newInstance(titles[0]);
        fragments.add(topicsFragment);
        NewsFragment newsFragment = NewsFragment.newInstance("");
        fragments.add(newsFragment);
        SitesFragment sitesFragment = SitesFragment.newInstance("");
        fragments.add(sitesFragment);
        return fragments;
    }
}
