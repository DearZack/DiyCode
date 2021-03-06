package io.github.dearzack.diycode.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.gcssloop.diycode_sdk.api.login.event.LoginEvent;

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
    public void onLogin(LoginEvent event) {
        if (baseDialog.isShowing()) {
            baseDialog.dismiss();
        }
        if (event.isOk() && event.getBean() != null) {
            Intent intent = getIntent();
            intent.putExtra("Token", event.getBean());
            setResult(RESULT_OK, intent);
            finish();
            toast("登录成功");
        } else {
            toast(event.getCodeDescribe());
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }


    @OnClick(R.id.login)
    public void onViewClicked() {
        if (TextUtils.isEmpty(account.getText().toString())
                || TextUtils.isEmpty(password.getText().toString())) {
            toast(getString(R.string.account_or_password_is_empty));
            return;
        }
        presenter.login(account.getText().toString(), password.getText().toString());
        baseDialog.show();
    }
}
