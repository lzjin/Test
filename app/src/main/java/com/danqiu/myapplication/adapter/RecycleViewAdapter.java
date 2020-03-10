package com.danqiu.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binioter.guideview.Component;
import com.binioter.guideview.Guide;
import com.binioter.guideview.GuideBuilder;
import com.danqiu.myapplication.R;
import com.danqiu.myapplication.bean.RecycleBean;
import com.danqiu.myapplication.component.SimpleComponent;
import com.danqiu.myapplication.fresco.ImageLoader;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lzj on 2019/3/20
 * Describe ：实现listview效果
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<RecycleBean> list;
    private RecyclerView mRecyclerView;
    private Guide mGuide;
    private int index_x=1;

    public RecycleViewAdapter(Context mContext, List<RecycleBean> list, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.list = list;
        this.mRecyclerView = recyclerView;
    }

    public RecycleViewAdapter(Context mContext, List<RecycleBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private RecycleViewGridAdapter.OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(RecycleViewGridAdapter.OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    //创建ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_recycle, parent, false);
        return new MyViewHolder(mView);
    }
    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.name.setText(list.get(position).getName());
        holder.content.setText(list.get(position).getContext());
        ImageLoader.loadImage(holder.simpleHead, list.get(position).getHead());
        //事件
        holder.simpleHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(position);
            }
        });
        View view=holder.itemView;
        if(list!=null&&index_x==1&& null == mGuide){
            view.post(new Runnable() {
                @Override public void run() {
                    //showGuideViewList(mContext,mRecyclerView);
                    showGuideView(mRecyclerView);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //继承RecyclerView.ViewHolder抽象类的自定义ViewHolder
    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.simple_head)
        SimpleDraweeView simpleHead;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.content)
        TextView content;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void showGuideView(View targetView) {
        index_x++;
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(targetView)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override public void onShown() {
            }

            @Override public void onDismiss() {
                if (null != mGuide) {
                    mGuide.dismiss();
                    mGuide = null;
                }
            }
        });

        builder.addComponent(new SimpleComponent());
        Guide guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(true);
        guide.show((Activity) mContext);
    }

    private void showGuideViewList(Context context, View view) {
        index_x++;
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(view)
                .setAlpha(153)
                .setHighTargetGraphStyle(Component.ROUNDRECT)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
                if (null != mGuide) {
                    mGuide.dismiss();
                    mGuide = null;
                }
            }
        });
        builder.addComponent(new SimpleComponent());
        mGuide = builder.createGuide();
        mGuide.setShouldCheckLocInWindow(false);
        mGuide.show((Activity) context);
    }
}
