package io.github.dearzack.diycode.my;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.login.event.LogoutEvent;
import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;
import com.gcssloop.diycode_sdk.api.user.event.GetMeEvent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.APP;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseFragment;
import io.github.dearzack.diycode.login.LoginActivity;
import io.github.dearzack.diycode.personal.PersonalActivity;
import io.github.dearzack.diycode.relate.RelateActivity;
import io.github.dearzack.diycode.util.ConstantUtils;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;


public class MyFragment extends BaseFragment implements MyContract.View {


    @Inject
    MyPresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.my_info_list)
    RecyclerView myInfoList;
    private View rootView;

    private MultiTypeAdapter adapter;
    private Items items;

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
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_my, container, false);
            ButterKnife.bind(this, rootView);
            DaggerMyComponent.builder()
                    .myPresenterModule(new MyPresenterModule(this))
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
        items = new Items();
        adapter = new MultiTypeAdapter();
        adapter.register(UserDetail.class, new MyHeadViewBinder());
        adapter.register(MyNormalBean.class, new MyNormalViewBinder());
        adapter.setItems(items);
        myInfoList.setLayoutManager(new LinearLayoutManager(getActivity()));
        myInfoList.setAdapter(adapter);
        myInfoList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 2;
            }
        });
        addEmptyItems();
        presenter.getMe();
        toolbar.setTitle("我");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    private void addEmptyItems() {
        items.clear();
        UserDetail userDetail = new UserDetail();
        userDetail.setName("登录/注册");
        items.add(0, userDetail);
        MyNormalBean topic = new MyNormalBean();
        topic.setLogoRes(R.string.my_topic);
        topic.setHint(ConstantUtils.TOPIC);
        topic.setCount(userDetail.getTopics_count());
        topic.setNeedMargin(true);
        MyNormalBean reply = new MyNormalBean();
        reply.setLogoRes(R.string.my_reply);
        reply.setHint(ConstantUtils.REPLY);
        reply.setCount(userDetail.getReplies_count());
        reply.setNeedMargin(false);
        MyNormalBean favorite = new MyNormalBean();
        favorite.setLogoRes(R.string.my_favorite);
        favorite.setHint(ConstantUtils.FAVORITE);
        favorite.setCount(userDetail.getFavorites_count());
        favorite.setNeedMargin(false);
        items.add(topic);
        items.add(reply);
        items.add(favorite);
        if (Diycode.getSingleInstance().isLogin()) {
            MyNormalBean logout = new MyNormalBean();
            logout.setLogoRes(R.string.my_logout);
            logout.setHint(ConstantUtils.LOGOUT);
            logout.setNeedMargin(true);
            logout.setNeedHideCount(true);
            items.add(logout);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setPresenter(MyContract.Presenter presenter) {

    }

    public void onGetMe(GetMeEvent event) {
        if (event.isOk() && event.getBean() != null) {
            items.clear();
            UserDetail userDetail = event.getBean();
            APP.userDetail = event.getBean();
            items.add(0, userDetail);
            MyNormalBean topic = new MyNormalBean();
            topic.setLogoRes(R.string.my_topic);
            topic.setHint(ConstantUtils.TOPIC);
            topic.setCount(userDetail.getTopics_count());
            topic.setNeedMargin(true);
            MyNormalBean reply = new MyNormalBean();
            reply.setLogoRes(R.string.my_reply);
            reply.setHint(ConstantUtils.REPLY);
            reply.setCount(userDetail.getReplies_count());
            reply.setNeedMargin(false);
            MyNormalBean favorite = new MyNormalBean();
            favorite.setLogoRes(R.string.my_favorite);
            favorite.setHint(ConstantUtils.FAVORITE);
            favorite.setCount(userDetail.getFavorites_count());
            favorite.setNeedMargin(false);
            MyNormalBean logout = new MyNormalBean();
            logout.setLogoRes(R.string.my_logout);
            logout.setHint(ConstantUtils.LOGOUT);
            logout.setNeedMargin(true);
            logout.setNeedHideCount(true);
            items.add(topic);
            items.add(reply);
            items.add(favorite);
            items.add(logout);
            adapter.notifyDataSetChanged();
        }
    }

    public void onHeadClick(HeadClickEvent event) {
        if (Diycode.getSingleInstance().isLogin()) {
            Intent intent = new Intent(getActivity(), PersonalActivity.class);
            intent.putExtra(PersonalActivity.LOGIN_NAME, APP.userDetail.getLogin());
            startActivity(intent);
        } else {
            goToLogin();
        }
    }

    public void onLogout(LogoutEvent event) {
         addEmptyItems();
    }

    public void onNormalItemClick(NormalItemClickEvent event) {
        if (Diycode.getSingleInstance().isLogin()) {
            switch (event.getMessage().getHint()) {
                case ConstantUtils.LOGOUT:
                    presenter.logout();
                    break;
                case ConstantUtils.TOPIC:
                case ConstantUtils.FAVORITE:
                case ConstantUtils.REPLY:
                    goToRelate(event.getMessage().getHint());
                    break;
            }
        } else {
            goToLogin();
        }
    }

    private void goToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        //这里有个坑，不要调用getActivity().startActivityForResult(),这样回调是在fragment的宿主activity的onActivityResult
        //直接调用startActivityForResult（）回调的是自己的onActivityResult
        startActivityForResult(intent, ConstantUtils.MY_TO_LOGIN);
    }

    private void goToRelate(String type) {
        Intent intent = new Intent(getActivity(), RelateActivity.class);
        intent.putExtra(RelateActivity.TYPE, type);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ConstantUtils.MY_TO_LOGIN:
                if (resultCode == Activity.RESULT_OK) {
                    presenter.getMe();
                }
                break;
        }
    }
}
