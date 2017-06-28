package io.github.dearzack.diycode.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseFragment;
import io.github.dearzack.diycode.topics.TopicsContract;
import io.github.dearzack.diycode.topics.TopicsPresenter;
import io.github.dearzack.diycode.util.ClickEvent;


public class MyFragment extends BaseFragment implements TopicsContract.View {


    @Inject
    TopicsPresenter presenter;

    public MyFragment() {
        // Required empty public constructor
    }

    public static MyFragment newInstance(String param1) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setPresenter(TopicsContract.Presenter presenter) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetList(ClickEvent<String> event) {
    }
}
