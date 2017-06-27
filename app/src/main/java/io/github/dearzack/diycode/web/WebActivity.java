package io.github.dearzack.diycode.web;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseActivity;
import io.github.dearzack.diycode.util.ClickEvent;

public class WebActivity extends BaseActivity implements WebContract.View {

    @Inject
    WebPresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.webview)
    WebView webview;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        DaggerWebComponent.builder()
                .webPresenterModule(new WebPresenterModule(this))
                .build()
                .inject(this);
        url = getIntent().getStringExtra("url");
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setPresenter(WebContract.Presenter presenter) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSiteClick(ClickEvent<String> event) {

    }

}
