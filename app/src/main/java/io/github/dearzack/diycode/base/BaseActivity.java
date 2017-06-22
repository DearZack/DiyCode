package io.github.dearzack.diycode.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContent();
//        initData();
//        initView();
        EventBus.getDefault().register(this);
    }

//    protected abstract void setContent();
//    protected abstract void initData();
//    protected abstract void initView();
}
