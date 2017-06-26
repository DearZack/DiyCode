package io.github.dearzack.diycode.sites;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gcssloop.diycode_sdk.api.sites.bean.Sites;

import io.github.dearzack.diycode.R;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Zack on 2017/6/26.
 */

public class SitesViewBinder extends ItemViewBinder<Sites, SitesViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_sites_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Sites item) {
        holder.name.setText(item.getName());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final TextView name;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.sites);
        }
    }
}
