package io.github.dearzack.diycode.relate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.user.event.GetUserCreateTopicListEvent;
import com.gcssloop.diycode_sdk.api.user.event.GetUserReplyTopicListEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.APP;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseActivity;
import io.github.dearzack.diycode.util.ConstantUtils;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class RelateActivity extends BaseActivity implements RelateContract.View {

    private static final String TAG = "RelateActivity";
    public static final String TYPE = "type";

    private String type;

    @Inject
    RelatePresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list)
    RecyclerView list;
    private Items items;
    private MultiTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relate);
        ButterKnife.bind(this);
        DaggerRelateComponent.builder()
                .relatePresenterModule(new RelatePresenterModule(this))
                .build()
                .inject(this);
        initViews();
    }

    private void initViews() {
        items = new Items();
        adapter = new MultiTypeAdapter();
        adapter.register(Topic.class, new TopicViewBinder());
        adapter.setItems(items);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        Intent intent = getIntent();
        type = intent.getStringExtra(TYPE);
        toolbar.setTitle(type);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        switch (type) {
            case ConstantUtils.TOPIC:
                presenter.getMyTopics(APP.userDetail.getLogin(), 0, ConstantUtils.REQUEST_COUNT);
                break;
            case ConstantUtils.FAVORITE:
                presenter.getMyFavorites(APP.userDetail.getLogin(), 0, ConstantUtils.REQUEST_COUNT);
                break;
            case ConstantUtils.REPLY:
                presenter.getMyReplies(APP.userDetail.getLogin(), 0, ConstantUtils.REQUEST_COUNT);
                break;
        }
    }


    @Override
    public void setPresenter(RelateContract.Presenter presenter) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMyTopics(GetUserCreateTopicListEvent event) {
        if (!type.equals(ConstantUtils.TOPIC)) {
            return;
        }
        if (event.isOk() && event.getBean() != null) {
            List<Topic> myTopics = event.getBean();
            items.add(myTopics);
            adapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMyFavorites(GetUserCreateTopicListEvent event) {
        if (!type.equals(ConstantUtils.FAVORITE)) {
            return;
        }
        if (event.isOk() && event.getBean() != null) {
            List<Topic> myTopics = event.getBean();
            Log.e(TAG, myTopics.size() + "");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMyReplies(GetUserReplyTopicListEvent event) {
        if (event.isOk() && event.getBean() != null) {
            List<Topic> myTopics = event.getBean();
            Log.e(TAG, myTopics.size() + "");
        }
    }

}
