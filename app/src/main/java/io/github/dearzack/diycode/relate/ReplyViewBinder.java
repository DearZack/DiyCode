package io.github.dearzack.diycode.relate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcssloop.diycode_sdk.api.notifications.bean.Reply;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.topicdetail.TopicDetailActivity;
import io.github.dearzack.diycode.util.CommonUtils;
import io.github.dearzack.diycode.util.GlideImageGetter;
import io.github.dearzack.diycode.util.HtmlUtil;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by zhouxiong on 2017-7-12.
 */

public class ReplyViewBinder extends ItemViewBinder<Reply, ReplyViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_reply_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final Reply item) {
        Glide.with(holder.avatar.getContext()).load(item.getUser().getAvatar_url())
                .error(R.mipmap.ic_launcher)
                .into(holder.avatar);
        holder.time.setText(CommonUtils.getHowLongAgo(item.getUpdated_at()));
        holder.action.setText(Html.fromHtml(toNormalColor("在帖子")
                + item.getTopic_title() + toNormalColor("回复了：")));
        holder.content.setText(Html.fromHtml(HtmlUtil.removeP(item.getBody_html()),
                new GlideImageGetter(holder.content.getContext(), holder.content), null));
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TopicDetailActivity.class);
                Topic topic = new Topic();
                topic.setId(item.getTopic_id());
                topic.setTitle(item.getTopic_title());
                intent.putExtra(TopicDetailActivity.TOPIC, topic);
                v.getContext().startActivity(intent);
            }
        });
    }

    private String toNormalColor(String content) { ///*" + normalColor + "*/
        return "<font color='#666666'> " + content + " </font>";
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.action)
        TextView action;
        @BindView(R.id.content)
        TextView content;
        View root;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            root = itemView;
        }
    }
}
