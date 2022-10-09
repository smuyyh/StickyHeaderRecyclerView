# StickyHeaderRecyclerView  ![](https://img.shields.io/github/v/release/smuyyh/StickyHeaderRecyclerView.svg) [![GitHub license](https://img.shields.io/github/license/smuyyh/StickyHeaderRecyclerView)](https://github.com/smuyyh/StickyHeaderRecyclerView/blob/master/LICENSE)

RecyclerView 悬浮吸顶 Header，支持点击事件与状态绑定

<img src="https://github.com/smuyyh/StickyHeaderRecyclerView/blob/master/art/screenshot.gif" width="216" height="400" ></img>

## 依赖

```
buildscript {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
    dependencies {
        ...
    }
}
```

```
dependencies {
    implementation 'com.github.smuyyh:StickyHeaderRecyclerView:1.1.0'
}
```

## 用法

#### 1. Header Model

Header Model 需要实现 ```StickyHeaderModel``` 接口

```java
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

public class Item {

    public final String title;
    public final String message;

    public Item(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
```

#### 2. Adapter

RecyclerView Adapter 需要实现 ```AdapterDataProvider``` 接口，并在 ```getAdapterData()``` 返回 model 数据，用于判断对应 position 是否为 Header

```java
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
```

#### 3. Setup

```java
RecyclerView recyclerView = findViewById(R.id.recycler_view);
recyclerView.setLayoutManager(new StickyLinearLayoutManager(this, adapter)); // StickyLinearLayoutManager 替代 LinearLayoutManager

RecyclerAdapter adapter = new RecyclerAdapter();
adapter.setDataList(genDataList(0));
recyclerView.setAdapter(adapter);
```

#### 4. Features

**(4.1) Header Attach Listener**

```java
stickyLinearLayoutManager.setStickyHeaderListener(new StickyLinearLayoutManager.StickyHeaderListener() {
    @Override
    public void headerAttached(View headerView, int adapterPosition) {
        Log.d("StickyHeaderRecyclerView", "Header Attached : " + adapterPosition);
    }

    @Override
    public void headerDetached(View headerView, int adapterPosition) {
        Log.d("StickyHeaderRecyclerView", "Header Detached : " + adapterPosition);
    }
});
```

**(4.2) Elevation**

```java
layoutManager.elevateHeaders(true); // default value : 5dp

// or
layoutManager.elevateHeaders(dpValue);
```

Thanks: [StickyHeaders](https://github.com/bgogetap/StickyHeaders)

## LICENSE

```
  Copyright (c) 2019 smuyyh. All right reserved.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```
