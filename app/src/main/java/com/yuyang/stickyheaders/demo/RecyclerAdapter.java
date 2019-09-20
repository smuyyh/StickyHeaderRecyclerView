package com.yuyang.stickyheaders.demo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuyang.stickyheaders.AdapterDataProvider;
import com.yuyang.stickyheaders.demo.model.HeaderItem;
import com.yuyang.stickyheaders.demo.model.Item;

import java.util.ArrayList;
import java.util.List;

public final class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder> implements AdapterDataProvider {

    private final List<Object> dataList = new ArrayList<>();

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false));
        } else {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.header_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        final Object item = dataList.get(position);
        if (item instanceof Item) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.titleTextView.setText(((Item) item).title);
            itemViewHolder.messageTextView.setText(((Item) item).message);
        } else if (item instanceof HeaderItem) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.titleTextView.setText(((HeaderItem) item).title);

            headerViewHolder.button.setTextColor(((HeaderItem) item).color);
            headerViewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((HeaderItem) item).color == 0xffff5050) {
                        ((HeaderItem) item).color = 0xff777777;
                    } else {
                        ((HeaderItem) item).color = 0xffff5050;
                    }

                    notifyItemChanged(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position) instanceof Item ? 0 : 1;
    }

    @Override
    public List<?> getAdapterData() {
        return dataList;
    }

    public void setDataList(List<Object> items) {
        dataList.clear();
        dataList.addAll(items);
        notifyDataSetChanged();
    }

    public void addDataList(List<Object> items) {
        if (items != null) {
            int start = dataList.size();
            dataList.addAll(items);
            notifyItemRangeInserted(start, items.size());
        }
    }

    private static final class ItemViewHolder extends BaseViewHolder {

        TextView titleTextView;
        TextView messageTextView;

        ItemViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.tv_title);
            messageTextView = itemView.findViewById(R.id.tv_message);
        }
    }

    private static final class HeaderViewHolder extends BaseViewHolder {

        TextView titleTextView;
        TextView button;

        HeaderViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.tv_title);
            button = itemView.findViewById(R.id.button);
        }
    }

    static class BaseViewHolder extends RecyclerView.ViewHolder {

        BaseViewHolder(View itemView) {
            super(itemView);
        }
    }
}
