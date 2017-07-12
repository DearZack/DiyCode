package io.github.dearzack.diycode.addnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gcssloop.diycode_sdk.api.base.bean.Node;

import java.util.ArrayList;
import java.util.List;

import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.widget.flowtaglayout.OnInitSelectedPosition;

/**
 * Created by Zack on 2017/7/12.
 */

public class TagAdapter<T> extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<T> mDataList;

    public TagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tag, null);

        TextView textView = (TextView) view.findViewById(R.id.tv_tag);
        T t = mDataList.get(position);

        if (t instanceof Node) {
            textView.setText(((Node) t).getName());
        }
        return view;
    }

    public void onlyAddAll(List<T> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<T> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }

    @Override
    public boolean isSelectedPosition(int position) {
        //初始化选中第一个
        return position == 0;
    }
}
