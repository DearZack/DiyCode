package io.github.dearzack.diycode.personal;

import android.os.Bundle;

import javax.inject.Inject;

import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseActivity;

public class PersonalActivity extends BaseActivity implements PersonalDetailContract.View {

    public static final String LOGIN_NAME = "LOGIN_NAME";
    @Inject
    PersonalDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        DaggerPersonalDetailComponent.builder()
                .personalDetailPresenterModule(new PersonalDetailPresenterModule(this))
                .build()
                .inject(this);
        toast(getIntent().getStringExtra(LOGIN_NAME));
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
}
