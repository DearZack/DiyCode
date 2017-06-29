package io.github.dearzack.diycode.my;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;

import org.greenrobot.eventbus.EventBus;

import io.github.dearzack.diycode.R;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Zack on 2017/6/29.
 */

public class MyNormalViewBinder extends ItemViewBinder<MyNormalBean, MyNormalViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_my_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final MyNormalBean item) {
        if (item.isNeedMargin()) {
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.root.getLayoutParams();
            lp.setMargins(0, ConvertUtils.dp2px(15), 0, 0);
        }
        Typeface typeface = Typeface.createFromAsset(holder.logo.getContext().getAssets(), "iconfont.ttf");
        holder.logo.setTypeface(typeface);
        holder.logo.setText(item.getLogoRes());
        holder.hint.setText(item.getHint());
        holder.count.setText(String.valueOf(item.getCount()));
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NormalItemClickEvent clickEvent = new NormalItemClickEvent();
                clickEvent.setMessage(item);
                EventBus.getDefault().post(clickEvent);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final TextView logo, hint, count;
        @NonNull
        private final View root;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.root = itemView;
            this.logo = (TextView) itemView.findViewById(R.id.logo);
            this.hint = (TextView) itemView.findViewById(R.id.hint);
            this.count = (TextView) itemView.findViewById(R.id.count);
        }
    }
}
