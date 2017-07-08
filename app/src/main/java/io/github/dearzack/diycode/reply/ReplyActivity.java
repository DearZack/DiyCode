package io.github.dearzack.diycode.reply;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gcssloop.diycode_sdk.api.topic.bean.TopicReply;
import com.gcssloop.diycode_sdk.api.topic.event.CreateTopicReplyEvent;
import com.rengwuxian.materialedittext.MaterialEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseActivity;

public class ReplyActivity extends BaseActivity implements ReplyContract.View {

    public static final String REPLY_PREFIX = "REPLY_PREFIX";
    public static final String TOPIC_ID = "TOPIC_ID";
    public static final String TOPIC_TITLE = "TOPIC_TITLE";
    public static final String TOPIC_REPLY = "TOPIC_REPLY";
    @Inject
    ReplyPresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.reply_content)
    MaterialEditText replyContent;
    private String prefix;
    private String topicTitle;
    private int topicId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        ButterKnife.bind(this);
        DaggerReplyComponent.builder()
                .replyPresenterModule(new ReplyPresenterModule(this))
                .build()
                .inject(this);
        initViews();
    }

    private void initViews() {
        prefix = getIntent().getStringExtra(REPLY_PREFIX);
        topicTitle = getIntent().getStringExtra(TOPIC_TITLE);
        topicId = getIntent().getIntExtra(TOPIC_ID, 0);
        toolbar.setTitle(topicTitle);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (!TextUtils.isEmpty(prefix)) {
            replyContent.setText(prefix);
            replyContent.setSelection(prefix.length());
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reply, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                if (TextUtils.isEmpty(replyContent.getText())) {
                    toast(getString(R.string.reply_content));
                    return super.onOptionsItemSelected(item);
                }
                presenter.reply(topicId, replyContent.getText().toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(ReplyContract.Presenter presenter) {

    }


    @Override
    public void onReplyCallback(CreateTopicReplyEvent event) {
        if (event.isOk() && event.getBean() != null) {
            TopicReply topicReply = event.getBean();
            Intent intent = getIntent();
            intent.putExtra(TOPIC_REPLY, topicReply);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
