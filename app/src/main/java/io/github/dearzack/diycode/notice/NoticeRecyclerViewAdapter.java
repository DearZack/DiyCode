package io.github.dearzack.diycode.notice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SpanUtils;
import com.bumptech.glide.Glide;
import com.gcssloop.diycode_sdk.api.notifications.bean.Notification;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.util.GlideImageGetter;
import io.github.dearzack.diycode.util.HtmlUtil;

/**
 * Created by Zack on 2017/7/9.
 */

public class NoticeRecyclerViewAdapter extends RecyclerView.Adapter<NoticeRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Notification> data;
    private static final String TYPE_TOPIC_REPLY = "TopicReply";
    private static final String TYPE_MENTION = "Mention";
    private static final String TYPE_FOLLOW = "Follow";
    private static final String TYPE_NODE_CHANGE = "NodeChanged";
    private static final String TYPE_HACKNEWS_REPLY = "HacknewsReply";
    private static final String TYPE_REPLY = "Reply";

    public NoticeRecyclerViewAdapter(Context context, List<Notification> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notice_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notification notification = data.get(position);
        String replyAction = "";
        String replyContent = "";
        SpannableStringBuilder style;

        if (notification.getActor() == null) { //系统消息
            Glide.with(context).load(R.drawable.admin).error(R.mipmap.ic_launcher).into(holder.avatar);
            replyAction = "管理员将你的帖子" + notification.getTopic().getTitle()
                    + "设置为" + notification.getTopic().getNode_name() + "中的帖子";
            replyContent = notification.getNode().getSummary();
            holder.action.setText(replyAction);
            holder.content.setText(replyContent);
            return;
        }
        Glide.with(context).load(notification.getActor().getAvatar_url())
                .error(R.mipmap.ic_launcher)
                .into(holder.avatar);
        switch (notification.getType()) {
            case TYPE_TOPIC_REPLY: //帖子被回复
                replyAction = notification.getActor().getName() + "在帖子"
                        + notification.getReply().getTopic_title() + "回复了：";
                replyContent = notification.getReply().getBody();
                break;
            case TYPE_MENTION: //被提及
                replyAction = notification.getActor().getName() + "提到了你";
                replyContent = notification.getMention().getBody_html();
                break;
            case TYPE_FOLLOW: //被跟随
                replyAction = notification.getActor().getName() + "关注了你";
                break;
            case TYPE_HACKNEWS_REPLY: //新闻被回复
                break;
            case TYPE_REPLY: //回复被回复
                break;
            case TYPE_NODE_CHANGE: //节点改变
                break;
            default:
                break;
        }
        if (!TextUtils.isEmpty(replyAction)) {
            SpanUtils spanUtils = new SpanUtils();
            holder.action.setText(replyAction);
        }
        if (!TextUtils.isEmpty(replyContent)) {
            holder.content.setText(Html.fromHtml(HtmlUtil.removeP(replyContent),
                    new GlideImageGetter(holder.content.getContext(), holder.content), null));
        }

    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.notice_action)
        TextView action;
        @BindView(R.id.notice_content)
        TextView content;
        //        @BindView(R.id.time)
//        TextView time;
//        @BindView(R.id.title)
//        TextView title;
//        @BindView(R.id.replies_count)
//        TextView repliesCount;
        View root;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

}
