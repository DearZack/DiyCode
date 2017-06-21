package io.github.dearzack.diycode.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gcssloop.diycode_sdk.api.login.event.LoginEvent;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    private EditText account, password;
    private Button login;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter();
    }

    @Override
    protected void setContent() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login(account.getText().toString(), password.getText().toString());
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogin(LoginEvent event) {
        Gson gson = new Gson();
        Log.e("TAG", gson.toJson(event));
    }
}
