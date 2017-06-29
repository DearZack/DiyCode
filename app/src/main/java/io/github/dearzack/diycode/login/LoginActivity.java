package io.github.dearzack.diycode.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.gcssloop.diycode_sdk.api.login.event.LoginEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseActivity;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @Inject
    LoginPresenter presenter;
    @BindView(R.id.login)
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        DaggerLoginComponent.builder()
                .loginPresenterModule(new LoginPresenterModule(this))
                .build()
                .inject(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogin(LoginEvent event) {
        if (event.isOk()) {
            Intent intent = getIntent();
            intent.putExtra("Token", event.getBean());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }


    @OnClick(R.id.login)
    public void onViewClicked() {
        presenter.login(account.getText().toString(), password.getText().toString());
    }
}
