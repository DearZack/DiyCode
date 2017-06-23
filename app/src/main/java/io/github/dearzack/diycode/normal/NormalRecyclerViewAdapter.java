package io.github.dearzack.diycode.normal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;

/**
 * Created by Zack on 2017/6/23.
 */

public class NormalRecyclerViewAdapter extends RecyclerView.Adapter<NormalRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Topic> data;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.CHINA);

    public NormalRecyclerViewAdapter(Context context, List<Topic> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_normal_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Topic topic = data.get(position);
        Glide.with(context).load(topic.getUser().getAvatar_url()).into(holder.avatar);
        holder.author.setText(topic.getUser().getName());
        holder.type.setText(topic.getNode_name());
        holder.time.setText(getHowLongAgo(topic.getUpdated_at()));
        holder.title.setText(topic.getTitle());
        holder.repliesCount.setText(String.format(
                context.getString(R.string.normal_replies_count),
                topic.getReplies_count()));
    }

    private String getHowLongAgo(String timeString) {
        try {
            if (TextUtils.isEmpty(timeString)) {
                return "未知时间";
            }
            long time = sdf.parse(timeString).getTime();
            long now = System.currentTimeMillis();
            long minute = 60L * 1000L;
            long hour = 60L * 60L * 1000L;
            long day = 24L * 60L * 60L * 1000L;
            long month = 30L * 24L * 60L * 60L * 1000L;
            long interval = now - time;
            if (interval < minute) {
                return interval / 1000 + "秒前";
            } else if (interval < hour) {
                return interval / (minute) + "分前";
            } else if (interval < day) {
                return interval / (hour) + "小时前";
            } else if (interval < month) {
                return interval / (day) + "天前";
            } else {
                return interval / month + "月前";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "未知时间";
        }
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
