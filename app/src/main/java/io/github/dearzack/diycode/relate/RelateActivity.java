package io.github.dearzack.diycode.relate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseActivity;
import io.github.dearzack.diycode.sites.SiteClickEvent;

public class RelateActivity extends BaseActivity implements RelateContract.View {

    private static final String TAG = "RelateActivity";
    public static final String TYPE = "type";

    private String type;

    @Inject
    RelatePresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        DaggerRelateComponent.builder()
                .relatePresenterModule(new RelatePresenterModule(this))
                .build()
                .inject(this);
        initViews();
    }

    private void initViews() {
        Intent intent = getIntent();
        type = intent.getStringExtra(TYPE);
        toolbar.setTitle(type);
        setSupportActionBar(toolbar);
    }




    @Override
    public void setPresenter(RelateContract.Presenter presenter) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSiteClick(SiteClickEvent event) {

    }

}
