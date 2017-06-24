package io.github.dearzack.diycode.normal;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicsListEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseFragment;


public class NormalFragment extends BaseFragment implements NormalContract.View {
    private static final String TYPE = "type";
    @BindView(R.id.normal_list)
    RecyclerView normalList;
    Unbinder unbinder;

    @Inject
    NormalPresenter presenter;

    NormalRecyclerViewAdapter adapter;
    List<Topic> data;

    private String type;

    public NormalFragment() {
        // Required empty public constructor
    }

    public static NormalFragment newInstance(String param1) {
        NormalFragment fragment = new NormalFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_normal, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaggerNormalComponent.builder()
                .normalPresenterModule(new NormalPresenterModule(this))
                .build()
                .inject(this);
        initView(view);
        return view;
    }

    private void initView(View view) {
        data = new ArrayList<>();
        presenter.getList(type);
        adapter = new NormalRecyclerViewAdapter(getActivity(), data);
        normalList.setLayoutManager(new LinearLayoutManager(getActivity()));
        normalList.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(NormalContract.Presenter presenter) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetList(GetTopicsListEvent event) {
        if (event.isOk()) {
            for (Topic topic : event.getBean()) {
                data.add(topic);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
