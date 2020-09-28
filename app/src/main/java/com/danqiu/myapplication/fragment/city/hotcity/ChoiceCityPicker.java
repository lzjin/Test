package com.danqiu.myapplication.fragment.city.hotcity;

import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 城市选择 修改
 */
public class ChoiceCityPicker {
    private static final String TAG = "ChoiceCityPicker";

    private WeakReference<FragmentActivity> mContext;
    private WeakReference<Fragment> mFragment;
    private WeakReference<FragmentManager> mFragmentManager;

    private boolean enableAnim;
    private int mAnimStyle;
    private LocatedCity mLocation;
    private List<HotCity> mHotCities;
    private OnPickListener mOnPickListener;
    private boolean isHttpSearch;

    private ChoiceCityPicker(){

    }

    private ChoiceCityPicker(Fragment fragment){
        this(fragment.getActivity(), fragment);
        mFragmentManager = new WeakReference<>(fragment.getChildFragmentManager());
    }

    private ChoiceCityPicker(FragmentActivity activity){
        this(activity, null);
        mFragmentManager = new WeakReference<>(activity.getSupportFragmentManager());
    }

    private ChoiceCityPicker(FragmentActivity activity, Fragment fragment){
        mContext = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
    }

    public static ChoiceCityPicker from(Fragment fragment){
        return new ChoiceCityPicker(fragment);
    }

    public static ChoiceCityPicker from(FragmentActivity activity){
        return new ChoiceCityPicker(activity);
    }

    /**
     * 设置动画效果
     * @param animStyle
     * @return
     */
    public ChoiceCityPicker setAnimationStyle(@StyleRes int animStyle) {
        this.mAnimStyle = animStyle;
        return this;
    }

    /**
     * 设置当前已经定位的城市
     * @param location
     * @return
     */
    public ChoiceCityPicker setLocatedCity(LocatedCity location) {
        this.mLocation = location;
        return this;
    }

    public ChoiceCityPicker setHotCities(List<HotCity> data){
        this.mHotCities = data;
        return this;
    }

    /**
     * 启用动画效果，默认为false
     * @param enable
     * @return
     */
    public ChoiceCityPicker enableAnimation(boolean enable){
        this.enableAnim = enable;
        return this;
    }

    /**
     * 是否网络请求
     * @param isHttpSearch
     * @return
     */
    public ChoiceCityPicker isHttpSearch(boolean isHttpSearch){
        this.isHttpSearch = isHttpSearch;
        return this;
    }

    /**
     * 设置选择结果的监听器
     * @param listener
     * @return
     */
    public ChoiceCityPicker setOnPickListener(OnPickListener listener){
        this.mOnPickListener = listener;
        return this;
    }

    public void show(){
        FragmentTransaction ft = mFragmentManager.get().beginTransaction();
        final Fragment prev = mFragmentManager.get().findFragmentByTag(TAG);
        if (prev != null){
            ft.remove(prev).commit();
            ft = mFragmentManager.get().beginTransaction();
        }
        ft.addToBackStack(null);

        ChoiceCityPickerDialogFragment cityPickerFragment = null;
        if(isHttpSearch){
            cityPickerFragment = ChoiceCityPickerDialogFragment.newInstance(enableAnim,true);
        }else {
            cityPickerFragment = ChoiceCityPickerDialogFragment.newInstance(enableAnim);
        }
        cityPickerFragment.setLocatedCity(mLocation);
        cityPickerFragment.setHotCities(mHotCities);
        cityPickerFragment.setAnimationStyle(mAnimStyle);
        cityPickerFragment.setOnPickListener(mOnPickListener);
        cityPickerFragment.show(ft, TAG);
    }

    /**
     * 定位完成
     * @param location
     * @param state
     */
    public void locateComplete(LocatedCity location, @LocateState.State int state){
        ChoiceCityPickerDialogFragment fragment = (ChoiceCityPickerDialogFragment) mFragmentManager.get().findFragmentByTag(TAG);
        if (fragment != null){
            Log.e("testz","---------------设置定位1-----");
            fragment.locationChanged(location, state);
        }
    }
}
