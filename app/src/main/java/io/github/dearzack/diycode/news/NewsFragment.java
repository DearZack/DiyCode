package io.github.dearzack.diycode.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gcssloop.diycode_sdk.api.news.bean.New;
import com.gcssloop.diycode_sdk.api.news.event.GetNewsListEvent;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

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
import io.github.dearzack.diycode.util.ClickEvent;
import io.github.dearzack.diycode.util.ConstantUtils;
import io.github.dearzack.diycode.web.WebActivity;

public class NewsFragment extends BaseFragment implements NewsContract.View {
    private static final String NEWS = "param1";
    @BindView(R.id.news_list)
    LRecyclerView newsList;
    Unbinder unbinder;

    NewsRecyclerViewAdapter adapter;
    LRecyclerViewAdapter lRecyclerViewAdapter;
    List<New> data;
    boolean isRefresh = true;

    @Inject
    NewsPresenter presenter;

    private String news;


    public NewsFragment() {
        // Required empty public constructor
    }


    public static NewsFragment newInstance(String param1) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(NEWS, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            news = getArguments().getString(NEWS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaggerNewsComponent.builder()
                .newsPresenterModule(new NewsPresenterModule(this))
                .build()
                .inject(this);
        initView(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initView(View view) {
        data = new ArrayList<>();
        presenter.getNewsList(news, 0, ConstantUtils.REQUEST_COUNT);
        adapter = new NewsRecyclerViewAdapter(getActivity(), data);
        newsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        newsList.setAdapter(lRecyclerViewAdapter);
        newsList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                presenter.getNewsList(news, 0, ConstantUtils.REQUEST_COUNT);
            }
        });
        newsList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                presenter.getNewsList(news, data.size(), ConstantUtils.REQUEST_COUNT);
            }
        });
        newsList.setHeaderViewColor(R.color.colorPrimary, R.color.colorPrimary, R.color.gray);
        newsList.setArrowImageView(R.mipmap.ic_launcher);
        newsList.setFooterViewColor(R.color.colorPrimary, R.color.colorPrimary, R.color.gray);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetList(GetNewsListEvent event) {
        if (event.isOk()) {
            if (isRefresh) {
                data.clear();
            }
            newsList.refreshComplete(ConstantUtils.REQUEST_COUNT);
            for (New news : event.getBean()) {
                data.add(news);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onItemClick(ClickEvent<New> event) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("url", event.getMessage().getAddress());
        getActivity().startActivity(intent);
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {

    }
}
