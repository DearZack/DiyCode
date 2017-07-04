package io.github.dearzack.diycode.topicdetail;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.topic.bean.TopicContent;
import com.gcssloop.diycode_sdk.api.topic.bean.TopicReply;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicEvent;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicRepliesListEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseEvent;
import io.github.dearzack.diycode.base.BaseActivity;
import io.github.dearzack.diycode.util.ConstantUtils;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class TopicDetailActivity extends BaseActivity implements TopicDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list)
    RecyclerView list;

    @Inject
    TopicDetailPresenter presenter;

    Items items;
    MultiTypeAdapter adapter;

    private Topic topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);
        ButterKnife.bind(this);
        DaggerTopicDetailComponent.builder()
                .topicDetailPresenterModule(new TopicDetailPresenterModule(this))
                .build()
                .inject(this);
        topic = (Topic) getIntent().getSerializableExtra("topic");
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
        adapter.register(TopicContent.class, new TopicDetailViewBinder());
        adapter.register(TopicReply.class, new TopicDetailReplyViewBinder());
        adapter.setItems(items);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        list.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ConvertUtils.dp2px(6);
            }
        });
        toolbar.setTitle(topic.getTitle());
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        presenter.getTopic(topic.getId());
        presenter.getTopicRepliesList(topic.getId(), 0, ConstantUtils.REQUEST_COUNT);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void test(BaseEvent<String> event) {
    }


    @Override
    public void setPresenter(TopicDetailContract.Presenter presenter) {

    }

    @Override
    public void showTopic(GetTopicEvent event) {
        if (event.isOk() && event.getBean() != null) {
            items.add(0, event.getBean());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showTopicReplies(GetTopicRepliesListEvent event) {
        if (event.isOk() && event.getBean() != null) {
            for (TopicReply topicReply : event.getBean()) {
                items.add(topicReply);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
