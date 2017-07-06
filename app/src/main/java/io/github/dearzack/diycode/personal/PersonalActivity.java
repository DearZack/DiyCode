package io.github.dearzack.diycode.personal;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseActivity;

public class PersonalActivity extends BaseActivity implements PersonalDetailContract.View {

    public static final String LOGIN_NAME = "LOGIN_NAME";
    @Inject
    PersonalDetailPresenter presenter;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.head_layout)
    RelativeLayout headLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.nsv)
    NestedScrollView nsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        DaggerPersonalDetailComponent.builder()
                .personalDetailPresenterModule(new PersonalDetailPresenterModule(this))
                .build()
                .inject(this);
        toast(getIntent().getStringExtra(LOGIN_NAME));
        collapsingToolbarLayout.setTitle("周雄");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void setPresenter(PersonalDetailContract.Presenter presenter) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
