package com.danqiu.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.bean.RecycleBean;
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

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycle, parent, false);

        MyViewHolder holder= new MyViewHolder(view);

        return holder;
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

}
