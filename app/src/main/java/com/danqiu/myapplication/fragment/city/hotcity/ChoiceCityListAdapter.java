package com.danqiu.myapplication.fragment.city.hotcity;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.utils.MLog;
import com.danqiu.myapplication.utils.SPUtil;
import com.zaaach.citypicker.adapter.InnerListener;
import com.zaaach.citypicker.adapter.decoration.GridItemDecoration;
import com.zaaach.citypicker.db.DBConfig;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.Arrays;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 *  城市选择
 */
public class ChoiceCityListAdapter extends RecyclerView.Adapter<ChoiceCityListAdapter.BaseViewHolder> {
    private static final int VIEW_TYPE_LOCATION = 10;
    private static final int VIEW_TYPE_HOT = 11;

    private Context mContext;
    private List<City> mData;
    private List<HotCity> mHotData;
    private int locateState;
    private InnerListener mInnerListener;
    private LinearLayoutManager mLayoutManager;
    private boolean stateChanged;
    private boolean autoLocate;

    public ChoiceCityListAdapter(Context context, List<City> data, List<HotCity> hotData, int state) {
        this.mData = data;
        this.mContext = context;
        this.mHotData = hotData;
        this.locateState = state;
    }

    public void autoLocate(boolean auto) {
        autoLocate = auto;
    }

    public void setLayoutManager(LinearLayoutManager manager) {
        this.mLayoutManager = manager;
    }

    public void updateData(List<City> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void updateLocateState(LocatedCity location, int state) {
        MLog.e("z","-------------设置定位2-------");
        mData.remove(0);
        mData.add(0, location);
        stateChanged = !(locateState == state);
        locateState = state;
        refreshLocationItem();
    }

    public void refreshLocationItem() {
        //如果定位城市的item可见则进行刷新
        if (stateChanged && mLayoutManager.findFirstVisibleItemPosition() == 0) {
            stateChanged = false;
            notifyItemChanged(0);
        }
    }

    /**
     * 滚动RecyclerView到索引位置
     *
     * @param index
     */
    public void scrollToSection(String index) {
        if (mData == null || mData.isEmpty()) return;
        if (TextUtils.isEmpty(index)) return;
        int size = mData.size();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(index.substring(0, 1), mData.get(i).getSection().substring(0, 1))) {
                if (mLayoutManager != null) {
                    mLayoutManager.scrollToPositionWithOffset(i, 0);
                    if (TextUtils.equals(index.substring(0, 1), "定")) {
                        //防止滚动时进行刷新
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (stateChanged) notifyItemChanged(0);
                            }
                        }, 1000);
                    }
                    return;
                }
            }
        }
    }
    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && TextUtils.equals(DBConfig.CITY_TAG_TITLE_HISTORY, mData.get(position).getSection().substring(0, 1)))
            return VIEW_TYPE_LOCATION;
        if (position == 1 && TextUtils.equals(DBConfig.CITY_TAG_TITLE_HOT, mData.get(position).getSection().substring(0, 1)))
            return VIEW_TYPE_HOT;
        return super.getItemViewType(position);
    }
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_LOCATION:
                view = LayoutInflater.from(mContext).inflate(R.layout.cp_list_item_location_layout_s, parent, false);
                return new LocationViewHolder(view);
            case VIEW_TYPE_HOT:
                view = LayoutInflater.from(mContext).inflate(R.layout.cp_list_item_hot_layout_s, parent, false);
                return new HotViewHolder(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.cp_list_item_default_layout_s, parent, false);
                return new DefaultViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        //默认
        if (holder instanceof DefaultViewHolder) {
            final int pos = holder.getAdapterPosition();
            final City data = mData.get(pos);
            if (data == null) return;

            ((DefaultViewHolder) holder).name.setText(data.getName());
            ((DefaultViewHolder) holder).name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mInnerListener != null) {
                        mInnerListener.dismiss(pos, data);
                    }
                }
            });
        }

        //定位城市 历史城市
        if (holder instanceof LocationViewHolder) {
            final int pos = holder.getAdapterPosition();
            final City data = mData.get(pos);
            if (data == null) return;

            String record = SPUtil.getString(mContext,"SP_SEARCH_TRAINRECORD");
            if (!record.equals("")) {
                Log.e("testz","-------------------记录x="+record);
                List<String> tagStrList = Arrays.asList(record.split(","));
                //刷新数据并显示
                ((LocationViewHolder) holder).tclRecord.setVisibility(View.VISIBLE);
                ((LocationViewHolder) holder).current.setVisibility(View.GONE);
                if(tagStrList!=null&&tagStrList.size()>= 10)
                    tagStrList=tagStrList.subList(0,10);
//                final List<TagBean> tags = new ArrayList<>();
//                for (String tag : tagStrList) {
//                    tags.add(new TagBean(tag));
//                }
                ((LocationViewHolder) holder).tclRecord.setTags(tagStrList);

                ((LocationViewHolder) holder).tclRecord.setOnTagClickListener(new TagView.OnTagClickListener() {
                    @Override
                    public void onTagClick(int position, String text) {
                        if (mInnerListener != null) {
                            City data = new City(text, "", "", "");
                            mInnerListener.dismiss(pos, data);
                        }
                    }

                    @Override
                    public void onTagLongClick(int position, String text) {

                    }

                });
            } else {
                Log.e("testz","-------------------无记录x="+record);
                ((LocationViewHolder) holder).current.setVisibility(View.VISIBLE);
                ((LocationViewHolder) holder).tclRecord.setVisibility(View.GONE);
                ((LocationViewHolder) holder).current.setText("暂无记录");

                //  设置宽高
                DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
                int screenWidth = dm.widthPixels;
                TypedValue typedValue = new TypedValue();
                mContext.getTheme().resolveAttribute(R.attr.cpGridItemSpace, typedValue, true);
                int space = mContext.getResources().getDimensionPixelSize(R.dimen.cp_grid_item_space);
                int padding = mContext.getResources().getDimensionPixelSize(R.dimen.cp_default_padding);
                int indexBarWidth = mContext.getResources().getDimensionPixelSize(R.dimen.cp_index_bar_width);
                int itemWidth = (screenWidth - padding - space * (ChoiceGridListAdapter.SPAN_COUNT - 1) - indexBarWidth) / ChoiceGridListAdapter.SPAN_COUNT;
                ViewGroup.LayoutParams lp = ((LocationViewHolder) holder).container.getLayoutParams();
                lp.width = itemWidth;
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                ((LocationViewHolder) holder).container.setLayoutParams(lp);

                switch (locateState) {
                    case LocateState.LOCATING:
                        ((LocationViewHolder) holder).current.setText(R.string.cp_locating);
                        break;
                    case LocateState.SUCCESS:
                        ((LocationViewHolder) holder).current.setText(data.getName());
                        break;
                    case LocateState.FAILURE:
                        ((LocationViewHolder) holder).current.setText(R.string.cp_locate_failed);
                        break;
                }
                ((LocationViewHolder) holder).container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (locateState == LocateState.SUCCESS) {
                            if (mInnerListener != null) {
                                mInnerListener.dismiss(pos, data);
                            }
                        } else if (locateState == LocateState.FAILURE) {
                            locateState = LocateState.LOCATING;
                            notifyItemChanged(0);
                            if (mInnerListener != null) {
                                mInnerListener.locate();
                            }
                        }
                    }
                });
                //第一次弹窗，如果未定位则自动定位
                if (autoLocate && locateState == LocateState.LOCATING && mInnerListener != null) {
                    mInnerListener.locate();
                    autoLocate = false;
                }

            }
        }
        //热门城市
        if (holder instanceof HotViewHolder) {
            final int pos = holder.getAdapterPosition();
            final City data = mData.get(pos);
            if (data == null) return;
            ChoiceGridListAdapter mAdapter = new ChoiceGridListAdapter(mContext, mHotData);
            mAdapter.setInnerListener(mInnerListener);
            ((HotViewHolder) holder).mRecyclerView.setAdapter(mAdapter);
        }
    }




    public void setInnerListener(InnerListener listener) {
        this.mInnerListener = listener;
    }

    static class BaseViewHolder extends RecyclerView.ViewHolder {
        BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class DefaultViewHolder extends BaseViewHolder {
        TextView name;

        DefaultViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cp_list_item_name);
        }
    }

    public static class HotViewHolder extends BaseViewHolder {
        RecyclerView mRecyclerView;

        HotViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = itemView.findViewById(R.id.cp_hot_list);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(),
                    ChoiceGridListAdapter.SPAN_COUNT, LinearLayoutManager.VERTICAL, false));
            int space = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.cp_grid_item_space);
            mRecyclerView.addItemDecoration(new GridItemDecoration(ChoiceGridListAdapter.SPAN_COUNT,
                    space));
        }
    }

    public static class LocationViewHolder extends BaseViewHolder {
        FrameLayout container;
        TextView current;
        TagContainerLayout tclRecord;

        LocationViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.cp_list_item_location_layout);
            current = itemView.findViewById(R.id.cp_list_item_location);
            tclRecord = itemView.findViewById(R.id.tcl_record);
        }
    }
}
