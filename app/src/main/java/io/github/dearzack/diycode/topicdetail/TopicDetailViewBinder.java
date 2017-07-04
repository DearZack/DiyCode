package io.github.dearzack.diycode.topicdetail;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcssloop.diycode_sdk.api.topic.bean.TopicContent;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.widget.ZWebView;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by zhouxiong on 2017-7-4.
 */

public class TopicDetailViewBinder extends ItemViewBinder<TopicContent, TopicDetailViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_topic_detail_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final TopicContent item) {
        Glide.with(holder.avatar.getContext()).load(item.getUser().getAvatar_url()).into(holder.avatar);
        holder.author.setText(item.getUser().getName());
        holder.type.setText(item.getNode_name());
        holder.webView.loadDataWithBaseURL("", ZWebView.setupWebContent(item.getBody_html(), true, true, "")
                , "text/html", "UTF-8", "");
        holder.replyCount.setText(String.format(holder.replyCount.getContext()
                .getString(R.string.topic_detail_reply_count), item.getReplies_count()));
        Typeface typeface = Typeface.createFromAsset(holder.like.getContext().getAssets(), "iconfont.ttf");
        holder.like.setTypeface(typeface);
        holder.like.setText(holder.like.getContext().getString(R.string.topic_detail_like));
        holder.favorite.setTypeface(typeface);
        holder.favorite.setText(holder.favorite.getContext().getString(R.string.topic_detail_favorite));
        if (item.getLikes_count() == 0) {
            holder.likeCount.setVisibility(View.GONE);
        } else {
            holder.likeCount.setText(item.getLikes_count() + " ");
            holder.likeCount.setVisibility(View.VISIBLE);
        }
        setTextColor(holder.like, item.getLiked());
        setTextColor(holder.favorite, item.getFavorited());
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLikeClick(item, holder.like, holder.likeCount);
            }
        });
    }

    private void onLikeClick(TopicContent content, TextView like, TextView likeCount) {
        if (content.getLiked()) {
            content.setLiked(false);
            content.setLikes_count(content.getLikes_count() - 1);
        } else {
            content.setLiked(true);
            content.setLikes_count(content.getLikes_count() + 1);
        }
        setTextColor(like, content.getLiked());
        likeCount.setText(content.getLikes_count() + "");
    }

    private void setTextColor(TextView textView, boolean isBlue) {
        if (isBlue) {
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorPrimary));
        } else {
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.gray));
        }
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.web_view)
        ZWebView webView;
        @BindView(R.id.reply_count)
        TextView replyCount;
        @BindView(R.id.favorite)
        TextView favorite;
        @BindView(R.id.like_count)
        TextView likeCount;
        @BindView(R.id.like)
        TextView like;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
