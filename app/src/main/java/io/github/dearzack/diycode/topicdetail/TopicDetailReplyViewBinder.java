package io.github.dearzack.diycode.topicdetail;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcssloop.diycode_sdk.api.topic.bean.TopicReply;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.util.CommonUtils;
import io.github.dearzack.diycode.util.GlideImageGetter;
import io.github.dearzack.diycode.util.HtmlUtil;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by zhouxiong on 2017-7-4.
 */

public class TopicDetailReplyViewBinder extends ItemViewBinder<TopicReply, TopicDetailReplyViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_topic_detail_reply_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TopicReply item) {
        Glide.with(holder.avatar.getContext()).load(item.getUser().getAvatar_url()).into(holder.avatar);
        holder.author.setText(item.getUser().getName());
        holder.positionAndTime.setText(String.format(holder.positionAndTime.getContext().getString(R.string.topic_detail_position_and_time),
                getPosition(holder), CommonUtils.getHowLongAgo(item.getUpdated_at())));
        Typeface typeface = Typeface.createFromAsset(holder.reply.getContext().getAssets(), "iconfont.ttf");
        holder.reply.setTypeface(typeface);
        holder.reply.setText(holder.reply.getContext().getString(R.string.topic_detail_reply));
        holder.content.setText(
                Html.fromHtml(HtmlUtil.removeP(item.getBody_html()),
                        new GlideImageGetter(holder.content.getContext(), holder.content), null));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.position_and_time)
        TextView positionAndTime;
        @BindView(R.id.reply)
        TextView reply;
        @BindView(R.id.content)
        TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
