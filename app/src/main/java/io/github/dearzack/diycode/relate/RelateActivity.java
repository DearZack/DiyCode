package io.github.dearzack.diycode.relate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gcssloop.diycode_sdk.api.notifications.bean.Reply;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.user.event.GetUserCollectionTopicListEvent;
import com.gcssloop.diycode_sdk.api.user.event.GetUserCreateTopicListEvent;
import com.gcssloop.diycode_sdk.api.user.event.GetUserReplyTopicListEvent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.APP;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseActivity;
import io.github.dearzack.diycode.util.ConstantUtils;
import io.github.dearzack.diycode.util.LoadMoreDelegate;
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
    private boolean isLoading = false;

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

    private void initViews() {
        items = new Items();
        adapter = new MultiTypeAdapter();
        adapter.register(Topic.class, new TopicViewBinder());
        adapter.register(Reply.class, new ReplyViewBinder());
        adapter.setItems(items);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        LoadMoreDelegate loadMoreDelegate = new LoadMoreDelegate(new LoadMoreDelegate.LoadMoreSubject() {
            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public void onLoadMore() {
                isLoading = true;
                getDataByType(type, items.size());
            }
        });
        loadMoreDelegate.attach(list);
        Intent intent = getIntent();
        type = intent.getStringExtra(TYPE);
        toolbar.setTitle(type);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getDataByType(type, 0);
    }

    private void getDataByType(String type, int offset) {
        switch (type) {
            case ConstantUtils.TOPIC:
                presenter.getMyTopics(APP.userDetail.getLogin(), offset, ConstantUtils.REQUEST_COUNT);
                break;
            case ConstantUtils.FAVORITE:
                presenter.getMyFavorites(APP.userDetail.getLogin(), offset, ConstantUtils.REQUEST_COUNT);
                break;
            case ConstantUtils.REPLY:
                presenter.getMyReplies(APP.userDetail.getLogin(), offset, ConstantUtils.REQUEST_COUNT);
                break;
            default:
                break;
        }
    }


    @Override
    public void setPresenter(RelateContract.Presenter presenter) {

    }

    @Override
    public void onGetMyTopics(GetUserCreateTopicListEvent event) {
        isLoading = false;
        if (!type.equals(ConstantUtils.TOPIC)) {
            return;
        }
        if (event.isOk() && event.getBean() != null) {
//            items.clear();
            List<Topic> myTopics = event.getBean();
            for (Topic topic : myTopics) {
                items.add(topic);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGetMyFavorites(GetUserCollectionTopicListEvent event) {
        isLoading = false;
        if (!type.equals(ConstantUtils.FAVORITE)) {
            return;
        }
        if (event.isOk() && event.getBean() != null) {
//            items.clear();
            List<Topic> myTopics = event.getBean();
            for (Topic topic : myTopics) {
                items.add(topic);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGetMyReplies(GetUserReplyTopicListEvent event) {
        isLoading = false;
        if (!type.equals(ConstantUtils.REPLY)) {
            return;
        }
        if (event.isOk() && event.getBean() != null) {
//            items.clear();
            List<Reply> myReplies = event.getBean();
            for (Reply reply : myReplies) {
                items.add(reply);
            }
            adapter.notifyDataSetChanged();
        }
    }

}
