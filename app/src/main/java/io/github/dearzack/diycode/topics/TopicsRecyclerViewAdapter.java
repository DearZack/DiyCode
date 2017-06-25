package io.github.dearzack.diycode.topics;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.util.CommonUtils;

/**
 * Created by Zack on 2017/6/23.
 */

public class TopicsRecyclerViewAdapter extends RecyclerView.Adapter<TopicsRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Topic> data;

    public TopicsRecyclerViewAdapter(Context context, List<Topic> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_topics_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Topic topic = data.get(position);
        Glide.with(context).load(topic.getUser().getAvatar_url()).into(holder.avatar);
        holder.author.setText(topic.getUser().getName());
        holder.type.setText(topic.getNode_name());
        holder.time.setText(CommonUtils.getHowLongAgo(topic.getUpdated_at()));
        holder.title.setText(topic.getTitle());
        holder.repliesCount.setText(String.format(
                context.getString(R.string.normal_replies_count),
                topic.getReplies_count()));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
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
