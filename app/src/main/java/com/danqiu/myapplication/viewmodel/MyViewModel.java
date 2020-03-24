package com.danqiu.myapplication.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.danqiu.myapplication.bean.ListBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lzj on 2020/3/19
 * Describe ：注释
 * MutableLiveData表示可变数据
 */
public class MyViewModel extends ViewModel {
    private MutableLiveData<List<ListBean>> users;
    private MutableLiveData<Integer> number;
    private int index;

    public MutableLiveData<List<ListBean>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<List<ListBean>>();
            loadUsers();
        }
        return users;
    }

    public MutableLiveData<Integer>  getNumber(){
        if(number==null){
            number=new MutableLiveData<>();
        }
         number.setValue(index++);
        return number;
    }

    private void loadUsers() {
       //模拟 网络请求数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);

                    List<ListBean> list=new ArrayList<>();
                    for(int i=1;i<5;i++){
                        list.add(new ListBean("张三"+i,i*10,"男"));
                    }

                    //users.setValue(list);
                    users.postValue(list);//改变数据后通知ui更新
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.e("test", "--------onCleared: ");
    }

}
