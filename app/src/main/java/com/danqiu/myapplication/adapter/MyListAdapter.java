package com.danqiu.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.bean.ListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/11/9.
 */

public class MyListAdapter extends BaseAdapter {
    private List<ListBean> list;
    private Context mContext;

    public MyListAdapter(List<ListBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.mylist_item, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvAge.setText(list.get(position).getAge()+"");
        viewHolder.tvName.setText(list.get(position).getName());

        viewHolder.tvSex.setText(list.get(position).getSex());


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tvAge)
        TextView tvAge;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvSex)
        TextView tvSex;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
