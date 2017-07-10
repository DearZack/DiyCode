package io.github.dearzack.diycode.notice;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ConvertUtils;
import com.gcssloop.diycode_sdk.api.notifications.bean.Notification;
import com.gcssloop.diycode_sdk.api.notifications.event.GetNotificationsListEvent;
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
import io.github.dearzack.diycode.util.ConstantUtils;


public class NoticeFragment extends BaseFragment implements NoticeContract.View {


    @Inject
    NoticePresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list)
    LRecyclerView list;
    private View rootView;

    private NoticeRecyclerViewAdapter adapter;
    LRecyclerViewAdapter lRecyclerViewAdapter;
    List<Notification> data;
    boolean isRefresh = true;


    public NoticeFragment() {
        // Required empty public constructor
    }

    public static NoticeFragment newInstance(String param1) {
        NoticeFragment fragment = new NoticeFragment();
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
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_notice, container, false);
            ButterKnife.bind(this, rootView);
            DaggerNoticeComponent.builder()
                    .noticePresenterModule(new NoticePresenterModule(this))
                    .build()
                    .inject(this);
            initView(rootView);
        }

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
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

    private void initView(View view) {
        data = new ArrayList<>();
        adapter = new NoticeRecyclerViewAdapter(getActivity(), data);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        list.setAdapter(lRecyclerViewAdapter);
        list.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ConvertUtils.dp2px(6);
            }
        });
        list.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                presenter.getNotice(0, ConstantUtils.REQUEST_COUNT);
            }
        });
        list.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                presenter.getNotice(data.size(), ConstantUtils.REQUEST_COUNT);
            }
        });
        presenter.getNotice(0, ConstantUtils.REQUEST_COUNT);
        toolbar.setTitle("通知");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setPresenter(NoticeContract.Presenter presenter) {

    }

    @Override
    public void onGetNotice(GetNotificationsListEvent event) {
        if (event.isOk() && event.getBean() != null) {
            if (isRefresh) {
                data.clear();
            }
            list.refreshComplete(ConstantUtils.REQUEST_COUNT);
            if (event.getBean().size() < ConstantUtils.REQUEST_COUNT) {
                list.setLoadMoreEnabled(false);
            }
            for (Notification notification : event.getBean()) {
                data.add(notification);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
