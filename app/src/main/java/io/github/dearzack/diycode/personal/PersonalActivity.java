package io.github.dearzack.diycode.personal;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ImageUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;
import com.gcssloop.diycode_sdk.api.user.event.GetUserCreateTopicListEvent;
import com.gcssloop.diycode_sdk.api.user.event.GetUserEvent;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseActivity;
import io.github.dearzack.diycode.topics.TopicsRecyclerViewAdapter;
import io.github.dearzack.diycode.util.ConstantUtils;

public class PersonalActivity extends BaseActivity implements PersonalDetailContract.View {

    public static final String LOGIN_NAME = "LOGIN_NAME";
    @Inject
    PersonalDetailPresenter presenter;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.head_layout)
    RelativeLayout headLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.list)
    LRecyclerView list;
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    @BindView(R.id.other)
    TextView other;

    private UserDetail userDetail;
    TopicsRecyclerViewAdapter adapter;
    LRecyclerViewAdapter lRecyclerViewAdapter;
    List<Topic> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        DaggerPersonalDetailComponent.builder()
                .personalDetailPresenterModule(new PersonalDetailPresenterModule(this))
                .build()
                .inject(this);
        collapsingToolbarLayout.setTitle(" ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter.getUser(getIntent().getStringExtra(LOGIN_NAME));
        presenter.getTopics(getIntent().getStringExtra(LOGIN_NAME), 0, ConstantUtils.REQUEST_COUNT);
        initViews();
    }

    private void initViews() {
        data = new ArrayList<>();
        adapter = new TopicsRecyclerViewAdapter(this, data);
        list.setLayoutManager(new LinearLayoutManager(this));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        list.setAdapter(lRecyclerViewAdapter);
        list.setPullRefreshEnabled(false);
        list.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.getTopics(getIntent().getStringExtra(LOGIN_NAME), data.size(), ConstantUtils.REQUEST_COUNT);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void setPresenter(PersonalDetailContract.Presenter presenter) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetUser(GetUserEvent event) {
        if (event.isOk() && event.getBean() != null) {
            userDetail = event.getBean();
            Glide.with(this).load(userDetail.getAvatar_url()).into(new GlideDrawableImageViewTarget(avatar) {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                    super.onResourceReady(resource, animation);
                    avatar.setBackground(resource);
                    avatar.setDrawingCacheEnabled(true);
                    appBarLayout.setBackground(convertViewToDrawable(avatar));
                    avatar.setDrawingCacheEnabled(false);
                }
            });
            collapsingToolbarLayout.setTitle(userDetail.getName());
            other.setText(String.format(getString(R.string.personal_other),
                    userDetail.getTopics_count(),
                    userDetail.getFavorites_count(),
                    userDetail.getFollowing_count()));
        }
    }

    @Override
    public void onGetTopics(GetUserCreateTopicListEvent event) {
        if (event.isOk() && event.getBean() != null) {
            list.refreshComplete(ConstantUtils.REQUEST_COUNT);
            for (Topic topic : event.getBean()) {
                data.add(topic);
            }
            adapter.notifyDataSetChanged();
        }
    }

    public Drawable convertViewToDrawable(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return ImageUtils.bitmap2Drawable(ImageUtils.fastBlur(bitmap, 1, 25));
    }
}
