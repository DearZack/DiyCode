package io.github.dearzack.diycode.addnews;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gcssloop.diycode_sdk.api.base.bean.Node;
import com.gcssloop.diycode_sdk.api.news.event.CreateNewsEvent;
import com.gcssloop.diycode_sdk.api.news.event.GetNewsNodesListEvent;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseActivity;
import io.github.dearzack.diycode.widget.flowtaglayout.FlowTagLayout;
import io.github.dearzack.diycode.widget.flowtaglayout.OnTagSelectListener;

public class AddNewsActivity extends BaseActivity implements AddNewsContract.View{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    MaterialEditText title;
    @BindView(R.id.link)
    MaterialEditText link;
    @BindView(R.id.nodes)
    FlowTagLayout nodes;
    @Inject
    AddNewsPresenter presenter;
    private TagAdapter<Node> nodeTagAdapter;
    private List<Node> nodesData;
    private Node currentNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        ButterKnife.bind(this);
        DaggerAddNewsComponent.builder()
                .addNewsPresenterModule(new AddNewsPresenterModule(this))
                .build()
                .inject(this);
        initViews();
    }

    private void initViews() {
        toolbar.setTitle("分享新链接");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nodeTagAdapter = new TagAdapter<>(this);
        nodesData = new ArrayList<>();
        nodes.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        nodes.setAdapter(nodeTagAdapter);
        nodes.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                currentNode = nodesData.get(selectedList.get(0));
                toast(currentNode.getName());
            }
        });
        presenter.getNewsNodesList();
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
                if (TextUtils.isEmpty(title.getText())) {
                    toast(getString(R.string.add_news_input_title));
                    return super.onOptionsItemSelected(item);
                }
                if (TextUtils.isEmpty(link.getText())) {
                    toast(getString(R.string.add_news_input_link));
                    return super.onOptionsItemSelected(item);
                }
                presenter.createNews(title.getText().toString(),
                        link.getText().toString(),
                        currentNode.getId());
                break;
        }
        return super.onOptionsItemSelected(item);
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
    public void setPresenter(AddNewsContract.Presenter presenter) {

    }

    @Override
    public void onGetNewsNodesList(GetNewsNodesListEvent event) {
        if (event.isOk() && event.getBean() != null) {
            currentNode = event.getBean().get(0);
            for (Node node : event.getBean()) {
                nodesData.add(node);
            }
            nodeTagAdapter.clearAndAddAll(nodesData);
        }
    }

    @Override
    public void onCreateNewsCallBack(CreateNewsEvent event) {
        if (event.isOk() && event.getBean() != null) {
            toast(getString(R.string.add_news_share_success));
            finish();
        } else {
            toast(event.getCodeDescribe());
        }
    }
}
