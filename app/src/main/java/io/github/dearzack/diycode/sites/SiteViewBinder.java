package io.github.dearzack.diycode.sites;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcssloop.diycode_sdk.api.sites.bean.Sites;

import org.greenrobot.eventbus.EventBus;

import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.util.ClickEvent;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Zack on 2017/6/26.
 */

public class SiteViewBinder extends ItemViewBinder<Sites.Site, SiteViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_site_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final Sites.Site item) {
        Glide.with(holder.logo.getContext()).load(item.getAvatar_url()).into(holder.logo);
        holder.name.setText(item.getName());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickEvent<Sites.Site> clickEvent = new ClickEvent<>();
                clickEvent.setMessage(item);
                EventBus.getDefault().post(clickEvent);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final TextView name;
        @NonNull
        private final ImageView logo;
        @NonNull
        private final View root;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.root = itemView;
            this.name = (TextView) itemView.findViewById(R.id.site);
            this.logo = (ImageView) itemView.findViewById(R.id.logo);
        }
    }

}
