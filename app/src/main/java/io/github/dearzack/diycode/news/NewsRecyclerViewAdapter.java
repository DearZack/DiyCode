package io.github.dearzack.diycode.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcssloop.diycode_sdk.api.news.bean.New;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.util.CommonUtils;

/**
 * Created by Zack on 2017/6/23.
 */

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<New> data;

    public NewsRecyclerViewAdapter(Context context, List<New> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        New news = data.get(position);
        Glide.with(context).load(news.getUser().getAvatar_url()).into(holder.avatar);
        holder.author.setText(news.getUser().getName());
        holder.type.setText(news.getNode_name());
        holder.time.setText(CommonUtils.getHowLongAgo(news.getUpdated_at()));
        holder.title.setText(news.getTitle());
        holder.address.setText(CommonUtils.getHost(news.getAddress()));
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsClickEvent clickEvent = new NewsClickEvent();
                clickEvent.setMessage(data.get(holder.getAdapterPosition()));
                EventBus.getDefault().post(clickEvent);
            }
        });
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
        @BindView(R.id.address)
        TextView address;
        View root;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            root = itemView;
        }
    }
}
