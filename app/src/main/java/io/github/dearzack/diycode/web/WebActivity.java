package io.github.dearzack.diycode.web;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseActivity;
import io.github.dearzack.diycode.sites.SiteClickEvent;

public class WebActivity extends BaseActivity implements WebContract.View {

    private static final String TAG = "WebActivity";

    @Inject
    WebPresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.webView)
    WebView webView;
    WebViewClient webViewClient = new WebViewClient() {

        /**
         * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
         */
        @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "shouldOverrideUrlLoading: " + url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            toolbar.setTitle(view.getTitle());
        }
    };
    WebChromeClient webChromeClient = new WebChromeClient() {
        @Override public void onProgressChanged(WebView view, int progress) {
            if (progress < 100) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(progress);
            } else if (progress == 100) {
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            toolbar.setTitle(title);
        }
    };

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
        initViews();
    }

    private void initViews() {
        toolbar.setTitle(url);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(webChromeClient);
        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void setPresenter(WebContract.Presenter presenter) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSiteClick(SiteClickEvent event) {

    }

}
