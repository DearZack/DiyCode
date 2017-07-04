package io.github.dearzack.diycode.topicdetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gcssloop.diycode_sdk.api.topic.bean.TopicReply;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by zhouxiong on 2017-7-4.
 */

public class TopicDetailReplyViewBinder extends ItemViewBinder<TopicReply, TopicDetailReplyViewBinder.ViewHolder>{

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_topic_detail_reply_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TopicReply item) {
        holder.content.setText(item.getBody_html());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.content)
        TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
