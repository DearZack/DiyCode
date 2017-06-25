package io.github.dearzack.diycode.news;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gcssloop.diycode_sdk.api.news.bean.New;
import com.gcssloop.diycode_sdk.api.news.event.GetNewsListEvent;

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

public class NewsFragment extends BaseFragment implements NewsContract.View {
    private static final String NEWS = "param1";
    @BindView(R.id.news_list)
    RecyclerView newsList;
    Unbinder unbinder;

    NewsRecyclerViewAdapter adapter;
    List<New> data;

    @Inject
    NewsPresenter presenter;

    // TODO: Rename and change types of parameters
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
        presenter.getNewsList(news);
        adapter = new NewsRecyclerViewAdapter(getActivity(), data);
        newsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsList.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetList(GetNewsListEvent event) {
        if (event.isOk()) {
            for (New news : event.getBean()) {
                data.add(news);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {

    }
}
