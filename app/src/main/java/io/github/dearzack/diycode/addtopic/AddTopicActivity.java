package io.github.dearzack.diycode.addtopic;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gcssloop.diycode_sdk.api.topic.bean.Node;
import com.gcssloop.diycode_sdk.api.topic.event.CreateTopicEvent;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicNodeListEvent;
import com.hamsa.twosteppickerdialog.OnStepDataRequestedListener;
import com.hamsa.twosteppickerdialog.OnStepPickListener;
import com.hamsa.twosteppickerdialog.TwoStepPickerDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseActivity;

public class AddTopicActivity extends BaseActivity implements AddTopicContract.View {

    @Inject
    AddTopicPresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.select_type)
    TextView selectType;
    @BindView(R.id.title)
    MaterialEditText title;
    @BindView(R.id.body)
    MaterialEditText body;
    Map<String, List<Node>> nodesMap;
    List<String> keys;
    Node currentNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);
        ButterKnife.bind(this);
        DaggerAddTopicComponent.builder()
                .addTopicPresenterModule(new AddTopicPresenterModule(this))
                .build()
                .inject(this);
        initViews();
    }

    private void initViews() {
        toolbar.setTitle("新建Topic");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        selectType.setText(String.format(getString(R.string.add_topic_select_type), "  ", "  "));
        nodesMap = new HashMap<>();
        keys = new ArrayList<>();
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
                if (TextUtils.isEmpty(title.getText())) {
                    toast(getString(R.string.add_news_input_title));
                    return super.onOptionsItemSelected(item);
                }
                if (TextUtils.isEmpty(body.getText())) {
                    toast(getString(R.string.add_topic_content));
                    return super.onOptionsItemSelected(item);
                }
                presenter.createTopic(title.getText().toString(),
                        body.getText().toString(),
                        currentNode.getId());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(AddTopicContract.Presenter presenter) {

    }

    @Override
    public void onGetTopicNodeListCallBack(GetTopicNodeListEvent event) {
        if (event.isOk() && event.getBean() != null) {
            for (Node node : event.getBean()) {
                if (nodesMap.get(node.getSection_name()) == null) {
                    List<Node> nodeList = new ArrayList<>();
                    nodeList.add(node);
                    nodesMap.put(node.getSection_name(), nodeList);
                    keys.add(node.getSection_name());
                } else {
                    nodesMap.get(node.getSection_name()).add(node);
                }
            }
            currentNode = nodesMap.get(keys.get(0)).get(0);
            selectType.setText(String.format(getString(R.string.add_topic_select_type),
                    currentNode.getSection_name(), currentNode.getName()));
        }
    }

    @Override
    public void onCreateTopicCallBack(CreateTopicEvent event) {
        if (event.isOk() && event.getBean() != null) {
            toast("新建成功");
            finish();
        } else {
            toast(event.getCodeDescribe());
        }
    }

    @OnClick(R.id.select_type)
    public void onSelectTypeClick() {
        final TwoStepPickerDialog pickThing = new TwoStepPickerDialog
                .Builder(this)
                .withBaseData(keys)
                .withOkButton("确定")
                .withCancelButton("取消")
                .withBaseOnLeft(true) // if you want it RTL like, set it to false
                .withInitialBaseSelected(0)
                .withInitialStepSelected(0)
                .withOnStepDataRequested(new OnStepDataRequestedListener() {
                    @Override
                    public List<String> onStepDataRequest(int baseDataPos) {
                        List<String> temp = new ArrayList<>();
                        for (Node node : nodesMap.get(keys.get(baseDataPos))) {
                            temp.add(node.getName());
                        }
                        return temp;
                    }
                })
                .withDialogListener(new OnStepPickListener() {
                    @Override
                    public void onStepPicked(int step, int pos) {
                        currentNode = nodesMap.get(keys.get(step)).get(pos);
                        selectType.setText(String.format(getString(R.string.add_topic_select_type),
                                currentNode.getSection_name(), currentNode.getName()));
                    }

                    @Override
                    public void onDismissed() {
                        toast("取消");
                    }
                })
                .build();
        pickThing.show();
    }
}
