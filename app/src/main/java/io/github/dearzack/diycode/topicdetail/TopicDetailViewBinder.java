package io.github.dearzack.diycode.topicdetail;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.topic.bean.TopicContent;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.login.LoginActivity;
import io.github.dearzack.diycode.util.ConstantUtils;
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
        setTextCount(item, holder.likeCount);
        setTextColor(holder.like, item.getLiked());
        setTextColor(holder.favorite, item.getFavorited());
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLikeChanged(item, holder.like, holder.likeCount);
            }
        });
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFavoriteChanged(item, holder.favorite);
            }
        });
    }

    private void onLikeChanged(TopicContent content, TextView like, TextView likeCount) {
        if (!Diycode.getSingleInstance().isLogin()) {
            Intent intent = new Intent(like.getContext(), LoginActivity.class);
            if (like.getContext() instanceof TopicDetailActivity) {
                ((TopicDetailActivity) like.getContext()).startActivityForResult(intent, ConstantUtils.TOPIC_DETAIL_LOGIN);
            } else {
                like.getContext().startActivity(intent);
            }
            return;
        }
        if (content.getLiked()) {
            content.setLiked(false);
            content.setLikes_count(content.getLikes_count() - 1);
        } else {
            content.setLiked(true);
            content.setLikes_count(content.getLikes_count() + 1);
        }
        setTextCount(content, likeCount);
        setTextColor(like, content.getLiked());
        likeCount.setText(content.getLikes_count() + "");
    }

    private void onFavoriteChanged(TopicContent content, TextView favorite) {
        if (!Diycode.getSingleInstance().isLogin()) {
            Intent intent = new Intent(favorite.getContext(), LoginActivity.class);
            if (favorite.getContext() instanceof TopicDetailActivity) {
                ((TopicDetailActivity) favorite.getContext()).startActivityForResult(intent, ConstantUtils.TOPIC_DETAIL_LOGIN);
            } else {
                favorite.getContext().startActivity(intent);
            }
            favorite.getContext().startActivity(intent);
            return;
        }
        content.setFavorited(!content.getFavorited());
        setTextColor(favorite, content.getFavorited());
    }

    private void setTextColor(TextView textView, Boolean isBlue) {
        if (isBlue == null) {
            return;
        }
        if (isBlue) {
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorPrimary));
        } else {
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.gray));
        }
    }

    private void setTextCount(TopicContent content, TextView likeCount) {
        if (content.getLikes_count() == 0) {
            likeCount.setVisibility(View.INVISIBLE);
        } else {
            likeCount.setText(content.getLikes_count() + " ");
            likeCount.setVisibility(View.VISIBLE);
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
