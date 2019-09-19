package com.yuyang.stickyheaders.demo;

import android.content.Context;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yuyang.stickyheaders.StickyLinearLayoutManager;
import com.yuyang.stickyheaders.demo.model.HeaderItem;
import com.yuyang.stickyheaders.demo.model.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        adapter = new RecyclerAdapter();
        adapter.setDataList(genDataList(0));
        StickyLinearLayoutManager layoutManager = new StickyLinearLayoutManager(this, adapter) {
            @Override
            public boolean isAutoMeasureEnabled() {
                return true;
            }

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                RecyclerView.SmoothScroller smoothScroller = new TopSmoothScroller(recyclerView.getContext());
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }

            class TopSmoothScroller extends LinearSmoothScroller {

                TopSmoothScroller(Context context) {
                    super(context);
                }

                @Override
                public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
                    return boxStart - viewStart;
                }
            }
        };
        layoutManager.elevateHeaders(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        layoutManager.setStickyHeaderListener(new StickyLinearLayoutManager.StickyHeaderListener() {
            @Override
            public void headerAttached(View headerView, int adapterPosition) {
                Log.d("Listener", "Header Attached : " + adapterPosition);
            }

            @Override
            public void headerDetached(View headerView, int adapterPosition) {
                Log.d("Listener", "Header Detached : " + adapterPosition);
            }
        });

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.addDataList(genDataList(adapter.getItemCount()));
            }
        }, 5000);
    }

    public static List<Object> genDataList(int start) {
        List<Object> items = new ArrayList<>();
        for (int i = start; i < 100 + start; i++) {
            if (i % 10 == 0) {
                items.add(new HeaderItem("Header " + i));
            } else {
                items.add(new Item("Item " + i, "description " + i));
            }
        }
        return items;
    }
}
