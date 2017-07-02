package io.github.dearzack.diycode.relate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.util.CommonUtils;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Zack on 2017/7/2.
 */

public class TopicViewBinder extends ItemViewBinder<Topic, TopicViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_topics_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Topic item) {
        Glide.with(holder.avatar.getContext()).load(item.getUser().getAvatar_url()).into(holder.avatar);
        holder.author.setText(item.getUser().getName());
        holder.type.setText(item.getNode_name());
        holder.time.setText(CommonUtils.getHowLongAgo(item.getUpdated_at()));
        holder.title.setText(item.getTitle());
        holder.repliesCount.setText(String.format(
                holder.repliesCount.getContext().getString(R.string.normal_replies_count),
                item.getReplies_count()));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.replies_count)
        TextView repliesCount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
