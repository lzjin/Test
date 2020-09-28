package com.danqiu.myapplication.fragment.city;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.danqiu.myapplication.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 查询城市适配器
 */

public class AddressSearchAdapter extends RecyclerView.Adapter {


    private List<CityBean> addresses;
    private Context context;

    public AddressSearchAdapter(Context context, List<CityBean> addressList) {
        this.context = context;
        this.addresses = addressList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_address_s, parent, false);
        RecyclerView.ViewHolder viewHolder = new AddressListViewHolder(convertView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final CityBean address = addresses.get(position);
        AddressListViewHolder viewHolder = (AddressListViewHolder) holder;
        viewHolder.tvAddressee.setText(address.getName());
        //viewHolder.tvPhone.setText(address.getPhone());
        viewHolder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onItemClick(v, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return addresses == null ? 0 : addresses.size();
    }

    /**
     * 点击事件
     */
    private OnViewItemClickListener onClickListener;

    public void setOnCheckClickListener(OnViewItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnViewItemClickListener {
        void onItemClick(View v, int position);
    }


    static class AddressListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_addressee)
        TextView tvAddressee;
        @BindView(R.id.tv_phone)
        TextView tvPhone;

        @BindView(R.id.name_ll)
        LinearLayout mRootView;

        AddressListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
