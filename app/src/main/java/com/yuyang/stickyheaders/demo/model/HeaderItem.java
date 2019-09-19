package com.yuyang.stickyheaders.demo.model;

import com.yuyang.stickyheaders.StickyHeaderModel;

public class HeaderItem implements StickyHeaderModel {

    public final String title;

    /**
     * 状态保存示例，如果header存在一些交互性行为，在onBindViewHolder里面需要绑定悬浮header的状态
     */
    public int color = 0xff777777;

    public HeaderItem(String title) {
        this.title = title;
    }
}
