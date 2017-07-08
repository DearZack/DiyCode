package io.github.dearzack.diycode.topicdetail;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.topic.bean.TopicReply;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.login.LoginActivity;
import io.github.dearzack.diycode.personal.PersonalActivity;
import io.github.dearzack.diycode.reply.ReplyActivity;
import io.github.dearzack.diycode.util.CommonUtils;
import io.github.dearzack.diycode.util.ConstantUtils;
import io.github.dearzack.diycode.util.GlideImageGetter;
import io.github.dearzack.diycode.util.HtmlUtil;
import io.github.dearzack.diycode.util.LinkMovementMethodExt;
import io.github.dearzack.diycode.util.SpanClickListener;
import io.github.dearzack.diycode.web.WebActivity;
import me.drakeet.multitype.ItemViewBinder;


/**
 * Created by zhouxiong on 2017-7-4.
 */

public class TopicDetailReplyViewBinder extends ItemViewBinder<TopicReply, TopicDetailReplyViewBinder.ViewHolder> {

    private static final String TAG = "TopicDetailReplyViewBin";
    private String topicTitle;

    public TopicDetailReplyViewBinder(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_topic_detail_reply_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(final @NonNull ViewHolder holder, @NonNull final TopicReply item) {
        Glide.with(holder.avatar.getContext()).load(item.getUser().getAvatar_url()).error(R.mipmap.ic_launcher).into(holder.avatar);
        holder.author.setText(item.getUser().getName());
        holder.positionAndTime.setText(String.format(holder.positionAndTime.getContext().getString(R.string.topic_detail_position_and_time),
                getPosition(holder), CommonUtils.getHowLongAgo(item.getUpdated_at())));
        Typeface typeface = Typeface.createFromAsset(holder.reply.getContext().getAssets(), "iconfont.ttf");
        holder.reply.setTypeface(typeface);
        holder.reply.setText(holder.reply.getContext().getString(R.string.topic_detail_reply));
        holder.content.setText(
                Html.fromHtml(HtmlUtil.removeP(item.getBody_html()),
                        new GlideImageGetter(holder.content.getContext(), holder.content), null));
        holder.content.setMovementMethod(new LinkMovementMethodExt(new SpanClickListener() {
            @Override public void onClick(int type, String url) {
                Log.d(TAG, "url: " + url);
                if (url.startsWith("/")) {
                    Log.d(TAG, "username: " + url.substring(1));
                    Intent intent = new Intent(holder.content.getContext(), PersonalActivity.class);
                    intent.putExtra(PersonalActivity.LOGIN_NAME, url.substring(1));
                    holder.content.getContext().startActivity(intent);
                } else if (url.startsWith("#")) {
                    // url: "#reply1"
                    Log.d(TAG, "楼");
                } else {
                    Intent intent = new Intent(holder.content.getContext(), WebActivity.class);
                    intent.putExtra("url", url);
                    holder.content.getContext().startActivity(intent);
                }
            }
        }));
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PersonalActivity.class);
                intent.putExtra(PersonalActivity.LOGIN_NAME, item.getUser().getLogin());
                v.getContext().startActivity(intent);
            }
        });
        holder.author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PersonalActivity.class);
                intent.putExtra(PersonalActivity.LOGIN_NAME, item.getUser().getLogin());
                v.getContext().startActivity(intent);
            }
        });
        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prefix = "#" + getPosition(holder) + "楼 @" + item.getUser().getLogin() + " ";
                int id = item.getTopic_id();
                reply(v, prefix, id);
            }
        });
    }

    private void reply(View view, String prefix, int id) {
        if (!Diycode.getSingleInstance().isLogin()) {
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            view.getContext().startActivity(intent);
            return;
        }
        Intent intent = new Intent(view.getContext(), ReplyActivity.class);
        intent.putExtra(ReplyActivity.REPLY_PREFIX, prefix);
        intent.putExtra(ReplyActivity.TOPIC_ID, id);
        intent.putExtra(ReplyActivity.TOPIC_TITLE, topicTitle);
        if (view.getContext() instanceof TopicDetailActivity) {
            ((TopicDetailActivity)view.getContext()).startActivityForResult(intent, ConstantUtils.TOPIC_DETAIL_TO_REPLY);
        } else {
            view.getContext().startActivity(intent);
        }
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
