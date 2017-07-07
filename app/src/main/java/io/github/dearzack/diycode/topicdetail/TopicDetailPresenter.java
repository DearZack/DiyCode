package io.github.dearzack.diycode.topicdetail;

import android.util.Log;

import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.topic.bean.TopicContent;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicEvent;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicRepliesListEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by Zack on 2017/6/21.
 */

public class TopicDetailPresenter implements TopicDetailContract.Presenter {

    TopicDetailContract.View view;
    private TopicContent topicContent;
    private boolean liked;
    private boolean favorite;

    @Inject
    public TopicDetailPresenter(TopicDetailContract.View view) {
        this.view = view;
    }

//    @Inject
//    void setupListeners() {
//        view.setPresenter(this);
//    }

    @Override
    public void start() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
        if (topicContent == null) {
            return;
        }
        //SDK目前有问题，先这样，unLike,collectionTopic,unCollectionTopic暂时都不行
        try {
            if (topicContent.getLiked() == null) {
                return;
                //说明没有登录
            }
            if (topicContent.getLiked() != liked) {
                Log.e("zack", "liked不相等");
                if (topicContent.getLiked()) {
                    Diycode.getSingleInstance().like("topic", topicContent.getId());
                } else {
                    Diycode.getSingleInstance().unLike("topic", topicContent.getId());
                }
            }
            if (topicContent.getFavorited() == null) {
                return;
                //说明没有登录
            }
            if (topicContent.getFavorited() != favorite) {
                Log.e("zack", "favorite不相等");
                if (topicContent.getFavorited()) {
                    Diycode.getSingleInstance().collectionTopic(topicContent.getId());
                } else {
                    Diycode.getSingleInstance().unCollectionTopic(topicContent.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getTopic(int id) {
        Diycode.getSingleInstance().getTopic(id);
    }

    @Override
    public void getTopicRepliesList(int id, int offset, int limit) {
        Diycode.getSingleInstance().getTopicRepliesList(id, offset, limit);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetTopic(GetTopicEvent event) {
        view.showTopic(event);
        topicContent = event.getBean();
        liked = topicContent.getLiked();
        favorite = topicContent.getFavorited();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetTopicRepliesList(GetTopicRepliesListEvent event) {
        view.showTopicReplies(event);
    }
}
