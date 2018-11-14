package com.danqiu.myapplication.views;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzj on 2018/1/31.
 * 自定义 密码支付输入框
 */

public class PayViewPass extends RelativeLayout {
    private Activity mContext;//上下文
    private GridView mGridView; //支付键盘
    private String   strPass="";//保存密码
    private TextView[] mTvPass;//密码数字控件
    private ImageView mIvClose;//关闭
    private TextView mTvForget;//忘记密码
    private TextView mTvHint;//提示 (提示:密码错误,重新输入)
    private List<Integer> listBtn;//1,2,3---0
    private View mView;//布局

    //在代码new使用
    public PayViewPass(Context context) {
        super(context);
    }
    //在布局文件中使用的时候调用,多个样式文件
    public PayViewPass(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    //在布局文件中使用的时候调用
    public PayViewPass(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = (Activity) context;

        initView();//初始化
        this.addView(mView); //将子布局添加到父容器,才显示控件
    }

    /**
     * 初始化
     */
    private void initView() {
        mView = LayoutInflater.from(mContext).inflate( R.layout.view_pay_pass, null);

        mIvClose  = (ImageView) mView.findViewById(R.id.iv_close);//关闭
        mTvForget = (TextView) mView.findViewById(R.id.tv_forget);//忘记密码
        mTvHint   = (TextView) mView.findViewById(R.id.tv_passText);//提示
        mTvPass = new TextView[6];                                  //密码控件
        mTvPass[0] = (TextView) mView.findViewById(R.id.tv_pass1);
        mTvPass[1] = (TextView) mView.findViewById(R.id.tv_pass2);
        mTvPass[2] = (TextView) mView.findViewById(R.id.tv_pass3);
        mTvPass[3] = (TextView) mView.findViewById(R.id.tv_pass4);
        mTvPass[4] = (TextView) mView.findViewById(R.id.tv_pass5);
        mTvPass[5] = (TextView) mView.findViewById(R.id.tv_pass6);

        mGridView = (GridView) mView.findViewById(R.id.gv_pass);

        //初始化数据
        listBtn = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            listBtn.add(i);
        }
        listBtn.add(10);
        listBtn.add(0);
        listBtn.add(R.mipmap.ic_pay_del);

        mGridView.setAdapter(adapter);
    }


    // GridView的适配器
    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return listBtn.size();
        }
        @Override
        public Object getItem(int position) {
            return listBtn.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.view_pay_pass_gridview_item, null);
                holder = new ViewHolder();
                holder.btnKey = (TextView) convertView.findViewById(R.id.bt);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //-----------------------------
            holder.btnKey.setText(listBtn.get(position)+"");
            if (position == 9) {
                holder.btnKey.setText("");
                holder.btnKey.setBackgroundColor(mContext.getResources().getColor(R.color.graye3));
            }
            if (position == 11) {
                holder.btnKey.setText("");
                holder.btnKey.setBackgroundResource(listBtn.get(position));
            }

         holder.btnKey.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                      //-----------点击0-9按钮---------------
                      if (position < 11 &&position!=9) {
                             if(strPass.length()==6){//已经是6位了，不添加
                                  return;
                             }
                              strPass=strPass+listBtn.get(position)+"";
                              mTvPass[strPass.length()-1].setText("*"); //内容随意，不从这里取值
                              //输入完成
                                 if(strPass.length()==6){
                                    mClickListener.onMyFinish(strPass);//请求服务器验证密码
                               }
                                LogUtil.e("test","-------------增加后长度:"+strPass.length());
                        }
                       //----------删除-------------------
                         if (position == 11) {
                              if(strPass.length()>0){
                                  mTvPass[strPass.length()-1].setText("");
                                  strPass=strPass.substring(0,strPass.length()-1);
                                  LogUtil.e("test","-------------删除后长度:"+strPass.length());
                              }
                          }
                         //----------多余-------------------
                         if(position==9){

                          }
                }
            });//-----监听end----
            return convertView;
        }
    };
    static class ViewHolder {
        public TextView btnKey;
    }
    /**
     * 按钮监听对外接口
     */
    public static interface OnMyClickListener {
        public void onMyFinish(String pass);
    }

    /**
     * 监听
     */
    private OnMyClickListener mClickListener;
    /**
     * 监听
     */
    public void setMyClickListener(OnMyClickListener listener) {
        mClickListener = listener;
    }

    /**
     * 返回取消支付的ImageView
     */
    public ImageView getClose() {
        return mIvClose;
    }
    /**
     * 忘记密码的TextView
     */
    public TextView getForgetPsw() {
        return mTvForget;
    }

    /**
     * 设置提醒的TextView
     */
    public void setTvHint(String msg) {
       mTvHint.setText(msg );
    }
    /**
     * 清楚所有密码TextView
     */
    public void cleanAllTv() {
        strPass="";
        for(int i=0;i<6;i++){
            mTvPass[i].setText("");
        }
    }


}




