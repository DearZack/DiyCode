package io.github.dearzack.diycode.reply;

import com.gcssloop.diycode_sdk.api.topic.event.CreateTopicReplyEvent;

import io.github.dearzack.diycode.base.BasePresenter;
import io.github.dearzack.diycode.base.BaseView;

/**
 * Created by Zack on 2017/6/21.
 */

public interface ReplyContract {

    interface View extends BaseView<Presenter> {
        void onReplyCallback(CreateTopicReplyEvent event);
    }

    interface Presenter extends BasePresenter {
        void reply(int id, String content);
    }
}
