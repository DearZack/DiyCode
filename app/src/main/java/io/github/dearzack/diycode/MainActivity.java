package io.github.dearzack.diycode;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import io.github.dearzack.diycode.base.BaseActivity;
import io.github.dearzack.diycode.homepage.HomepageFragment;
import io.github.dearzack.diycode.my.MyFragment;
import io.github.dearzack.diycode.notice.NoticeFragment;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private FragmentTabHost mTabHost;
    private int mImages[] = {  //Tab图片
            R.string.main_homepage, R.string.main_notice, R.string.main_myself};
    private String mFragmentTags[] = {  //标记
            "首页", "通知", "我的"};
    private Class mFragment[] = { //加载的Fragment
            HomepageFragment.class, NoticeFragment.class, MyFragment.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
//        Diycode.getSingleInstance().login("diycode888", "password");
    }

    private void initViews() {
        initTabHost();
    }

    private void initTabHost() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        //去掉分割线
        mTabHost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < mImages.length; i++) {
            //对Tab按钮添加标记和图片
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mFragmentTags[i]).setIndicator(getTabView(i));
            //添加Fragment
            mTabHost.addTab(tabSpec, mFragment[i], null);
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.white);
        }
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                updateTabs();
            }
        });
    }

    private void updateTabs() {
        for (int i = 0; i < mTabHost.getTabWidget().getTabCount(); i++) {
            TextView textView = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.tv_tab_icon);
            if (i == mTabHost.getCurrentTab()) {
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            } else {
                textView.setTextColor(getResources().getColor(R.color.gray));
            }
        }
    }

    private View getTabView(int index) {
        View view = getLayoutInflater().inflate(R.layout.tabs, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_tab_icon);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        textView.setTypeface(typeface);
        // 此处有一个坑，如果直接textView.setText("&#xe605;");
        // 会直接显示字符"&#xe605;"而不是ttf里的图标
        textView.setText(getString(mImages[index]));
        if (index == 0) {
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        return view;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }
}
