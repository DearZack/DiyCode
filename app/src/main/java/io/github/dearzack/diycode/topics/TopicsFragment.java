package io.github.dearzack.diycode.topics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicsListEvent;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseFragment;
import io.github.dearzack.diycode.topicdetail.TopicDetailActivity;
import io.github.dearzack.diycode.util.ConstantUtils;


public class TopicsFragment extends BaseFragment implements TopicsContract.View {
    private static final String TYPE = "type";
    @BindView(R.id.topics_list)
    LRecyclerView topicsList;

    @Inject
    TopicsPresenter presenter;

    TopicsRecyclerViewAdapter adapter;
    LRecyclerViewAdapter lRecyclerViewAdapter;
    List<Topic> data;
    boolean isRefresh = true;

    private String type;

    public TopicsFragment() {
        // Required empty public constructor
    }

    public static TopicsFragment newInstance(String param1) {
        TopicsFragment fragment = new TopicsFragment();
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
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_topics, container, false);
        ButterKnife.bind(this, view);
        DaggerTopicsComponent.builder()
                .topicsPresenterModule(new TopicsPresenterModule(this))
                .build()
                .inject(this);
        initView(view);
        return view;
    }

    private void initView(View view) {
        data = new ArrayList<>();
        presenter.getTopicsList(type, 0, ConstantUtils.REQUEST_COUNT);
        adapter = new TopicsRecyclerViewAdapter(getActivity(), data);
        topicsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        topicsList.setAdapter(lRecyclerViewAdapter);
        topicsList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                presenter.getTopicsList(type, 0, ConstantUtils.REQUEST_COUNT);
            }
        });
        topicsList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                presenter.getTopicsList(type, data.size(), ConstantUtils.REQUEST_COUNT);
            }
        });
        topicsList.setHeaderViewColor(R.color.colorPrimary, R.color.colorPrimary, R.color.gray);
        topicsList.setArrowImageView(R.mipmap.ic_launcher);
        topicsList.setFooterViewColor(R.color.colorPrimary, R.color.colorPrimary, R.color.gray);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setPresenter(TopicsContract.Presenter presenter) {

    }



    @Override
    public void goToTopicDetailActivity(Topic topic) {
        Intent intent = new Intent(getActivity(), TopicDetailActivity.class);
        intent.putExtra("topic", topic);
        startActivity(intent);
    }

    @Override
    public void onGetTopicsList(GetTopicsListEvent event) {
        if (event.isOk()) {
            if (isRefresh) {
                data.clear();
            }
            topicsList.refreshComplete(ConstantUtils.REQUEST_COUNT);
            for (Topic topic : event.getBean()) {
                data.add(topic);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
