package io.github.dearzack.diycode.my;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;

import org.greenrobot.eventbus.EventBus;

import io.github.dearzack.diycode.R;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Zack on 2017/6/29.
 */

public class MyHeadViewBinder extends ItemViewBinder<UserDetail, MyHeadViewBinder.ViewHolder>{

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_my_head_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final UserDetail item) {
        Glide.with(holder.head.getContext())
                .load(item.getAvatar_url())
                .error(R.mipmap.ic_launcher)
                .into(holder.head);
        holder.name.setText(item.getName());
        Typeface typeface = Typeface.createFromAsset(holder.icon.getContext().getAssets(), "iconfont.ttf");
        holder.icon.setTypeface(typeface);
        holder.icon.setText(R.string.my_right);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeadClickEvent clickEvent = new HeadClickEvent();
                clickEvent.setMessage(item);
                EventBus.getDefault().post(clickEvent);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final TextView name, icon;
        @NonNull
        private final ImageView head;
        @NonNull
        private final View root;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.root = itemView;
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.icon = (TextView) itemView.findViewById(R.id.icon);
            this.head = (ImageView) itemView.findViewById(R.id.head);
        }
    }
}
