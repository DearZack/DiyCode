package io.github.dearzack.diycode.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContent();
//        initData();
//        initView();
    }

//    protected abstract void setContent();
//    protected abstract void initData();
//    protected abstract void initView();

    protected void toast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
