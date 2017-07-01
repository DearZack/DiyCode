package io.github.dearzack.diycode.notice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gcssloop.diycode_sdk.api.notifications.event.GetNotificationsListEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseFragment;


public class NoticeFragment extends BaseFragment implements NoticeContract.View {


    @Inject
    NoticePresenter presenter;
    private View rootView;


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

    private void initView(View view) {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setPresenter(NoticeContract.Presenter presenter) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetNotificationsList(GetNotificationsListEvent event) {

    }
}
