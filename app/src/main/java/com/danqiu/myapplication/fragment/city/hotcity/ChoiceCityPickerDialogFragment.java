package com.danqiu.myapplication.fragment.city.hotcity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.fragment.city.AddressSearchAdapter;
import com.danqiu.myapplication.fragment.city.CityBean;
import com.danqiu.myapplication.utils.SPUtil;
import com.zaaach.citypicker.adapter.InnerListener;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.adapter.decoration.DividerItemDecoration;
import com.zaaach.citypicker.adapter.decoration.SectionItemDecoration;
import com.zaaach.citypicker.db.DBManager;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;
import com.zaaach.citypicker.util.ScreenUtil;
import com.zaaach.citypicker.view.SideIndexBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市选择 弹框 DialogFragment
 */
public class ChoiceCityPickerDialogFragment extends DialogFragment implements TextWatcher,
        View.OnClickListener, SideIndexBar.OnIndexTouchedChangedListener, InnerListener {
    private View mContentView;
    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private TextView mOverlayTextView;
    private SideIndexBar mIndexBar;
    private EditText mSearchBox;
    private TextView mCancelBtn;
    private ImageView mClearAllBtn;

    private LinearLayoutManager mLayoutManager;
    private ChoiceCityListAdapter mAdapter;
    private List<City> mAllCities;
    private List<HotCity> mHotCities;
    private List<City> mResults;

    private DBManager dbManager;

    private int height;
    private int width;

    private boolean enableAnim = false;
    private int mAnimStyle = R.style.DefaultCityPickerAnimation;
    private LocatedCity mLocatedCity;
    private int locateState;
    private OnPickListener mOnPickListener;

    /**
     * 自定义  请求搜索
     */
    //private OugoAddressApi mAddressApi ;//地址管理接口
    //private OugoProDialog loadingDialog;//加载弹窗
    private PopupWindow popupWindow;
    private boolean isHttpSearch;
    private View mTopView;


    /**
     * 获取实例
     */
    public static ChoiceCityPickerDialogFragment newInstance(boolean enable, boolean isHttpSearch) {
        final ChoiceCityPickerDialogFragment fragment = new ChoiceCityPickerDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean("cp_enable_anim", enable);
        args.putBoolean("cp_isHttpSearch", isHttpSearch);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 获取实例
     *
     * @param enable 是否启用动画效果
     * @return
     */
    public static ChoiceCityPickerDialogFragment newInstance(boolean enable) {
        final ChoiceCityPickerDialogFragment fragment = new ChoiceCityPickerDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean("cp_enable_anim", enable);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.CityPickerStyle);
    }

    public void setLocatedCity(LocatedCity location) {
        mLocatedCity = location;
    }

    public void setHotCities(List<HotCity> data) {
        if (data != null && !data.isEmpty()) {
            this.mHotCities = data;
        }
    }

    @SuppressLint("ResourceType")
    public void setAnimationStyle(@StyleRes int resId) {
        this.mAnimStyle = resId <= 0 ? mAnimStyle : resId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                                       // cp_dialog_city_picker   cp_dialog_city_picker_s
        mContentView = inflater.inflate(R.layout.cp_dialog_city_picker_s, container, false);
        return mContentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initViews();
    }

    private void initViews() {
        popupWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //loadingDialog = new OugoProDialog(getActivity(), R.style.CustomProgressDialog);//,


        mRecyclerView = mContentView.findViewById(R.id.cp_city_recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SectionItemDecoration(getActivity(), mAllCities), 0);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()), 1);
        mAdapter = new ChoiceCityListAdapter(getActivity(), mAllCities, mHotCities, locateState);
        mAdapter.autoLocate(true);
        mAdapter.setInnerListener(this);
        mAdapter.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //确保定位城市能正常刷新
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mAdapter.refreshLocationItem();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });

        mTopView = mContentView.findViewById(R.id.top_title_view);
        mEmptyView = mContentView.findViewById(R.id.cp_empty_view);//空数据
        mOverlayTextView = mContentView.findViewById(R.id.cp_overlay);

        mIndexBar = mContentView.findViewById(R.id.cp_side_index_bar);
        mIndexBar.setNavigationBarHeight(ScreenUtil.getNavigationBarHeight(getActivity()));
        mIndexBar.setOverlayTextView(mOverlayTextView)
                .setOnIndexChangedListener(this);

        mSearchBox = mContentView.findViewById(R.id.edt_cp_search_box);
        mSearchBox.addTextChangedListener(this);

        mCancelBtn = mContentView.findViewById(R.id.cp_cancel);
        mClearAllBtn = mContentView.findViewById(R.id.cp_clear_all);
        mCancelBtn.setOnClickListener(this);
        mClearAllBtn.setOnClickListener(this);
    }

    private void initData() {
        Bundle args = getArguments();
        if (args != null) {
            enableAnim = args.getBoolean("cp_enable_anim");
            isHttpSearch = args.getBoolean("cp_isHttpSearch");
        }
        //初始化热门城市
        if (mHotCities == null || mHotCities.isEmpty()) {
            mHotCities = new ArrayList<>();
            mHotCities.add(new HotCity("北京", "北京", "101010100"));
            mHotCities.add(new HotCity("上海", "上海", "101020100"));
            mHotCities.add(new HotCity("广州", "广东", "101280101"));
            mHotCities.add(new HotCity("深圳", "广东", "101280601"));
            mHotCities.add(new HotCity("天津", "天津", "101030100"));
            mHotCities.add(new HotCity("杭州", "浙江", "101210101"));
            mHotCities.add(new HotCity("南京", "江苏", "101190101"));
            mHotCities.add(new HotCity("成都", "四川", "101270101"));
            mHotCities.add(new HotCity("武汉", "湖北", "101200101"));
        }
        //初始化定位城市，默认为空时会自动回调定位
        if (mLocatedCity == null) {
            mLocatedCity = new LocatedCity(getString(R.string.cp_locating), "未知", "0");
            locateState = LocateState.LOCATING;
        } else {
            locateState = LocateState.SUCCESS;
        }

        dbManager = new DBManager(getActivity());
        mAllCities = dbManager.getAllCities();
        mAllCities.add(0, mLocatedCity);
        mAllCities.add(1, new HotCity("热门城市", "未知", "0"));
        mResults = mAllCities;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mOnPickListener != null) {
                        mOnPickListener.onCancel();
                    }
                }
                return false;
            }
        });

        measure();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            window.setGravity(Gravity.TOP);
           // window.setLayout(width, height - ScreenUtil.getStatusBarHeight(getActivity()));
            if (enableAnim) {
                window.setWindowAnimations(mAnimStyle);
            }
        }
    }

    //测量宽高
    private void measure() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(dm);
            height = dm.heightPixels;
            width = dm.widthPixels;
        } else {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            height = dm.heightPixels;
            width = dm.widthPixels;
        }
    }

    /**
     * 搜索框监听
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String keyword = s.toString();
        if(isHttpSearch){//网络搜索
            mEmptyView.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(keyword)) {
                requestHttpSearchCityList(keyword);
            } else {
                if (null != popupWindow && popupWindow.isShowing())
                    popupWindow.dismiss();
            }
        }else {//本地
            if (TextUtils.isEmpty(keyword)) {
                mClearAllBtn.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.GONE);
                mResults = mAllCities;
                ((SectionItemDecoration) (mRecyclerView.getItemDecorationAt(0))).setData(mResults);
                mAdapter.updateData(mResults);
            } else {
                mClearAllBtn.setVisibility(View.VISIBLE);
                //开始数据库查找
                mResults = dbManager.searchCity(keyword);
                ((SectionItemDecoration) (mRecyclerView.getItemDecorationAt(0))).setData(mResults);
                if (mResults == null || mResults.isEmpty()) {
                    mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                    mAdapter.updateData(mResults);
                }
            }
            mRecyclerView.scrollToPosition(0);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.cp_clear_all://关闭 返回
                dismiss();
                if (mOnPickListener != null) {
                    mOnPickListener.onCancel();
                }
                break;
            case R.id.cp_cancel://清除
                mSearchBox.setText("");
                if (null != popupWindow){
                    popupWindow.dismiss();
                }
                break;
        }
    }


    @Override
    public void onIndexChanged(String index, int position) {
        //滚动RecyclerView到索引位置
        mAdapter.scrollToSection(index);
    }

    public void locationChanged(LocatedCity location, int state) {
        mAdapter.updateLocateState(location, state);
    }

    @Override
    public void dismiss(int position, City data) {
        dismiss();
        if (mOnPickListener != null) {
            mOnPickListener.onPick(position, data);
        }
    }

    @Override
    public void locate() {
        if (mOnPickListener != null) {
            mOnPickListener.onLocate();
        }
    }

    public void setOnPickListener(OnPickListener listener) {
        this.mOnPickListener = listener;
    }


    /**
     * 显示加载弹窗
     */
    protected void showLoadingDialog() {
        if (null != getActivity() && !getActivity().isFinishing()) {
//            if (loadingDialog != null) {
//                loadingDialog.show();
//            }
        }
    }

    /**
     * 关闭加载弹窗
     */
    protected void closeLoadingDialog() {
        if (null != getActivity() && !getActivity().isFinishing()) {
//            if (loadingDialog != null && loadingDialog.isShowing()) {
//                loadingDialog.dismiss();
//            }
        }
    }

    /**
     * 搜索 网络请求
     * @param keyWord
     */
    public void requestHttpSearchCityList(String keyWord) {
        //定位接口，需要APP自身实现，这里模拟一下定位
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<CityBean> innerList =new ArrayList<>();
                for(int i=0;i<6;i++){
                    innerList.add(new CityBean("北京车站","1234501"+i,keyWord+"_"+i));
                }
                showPopupWindow(innerList);
            }
        }, 2000);

//        showLoadingDialog();
//        closeLoadingDialog();
    }

    private void showPopupWindow(final List<CityBean> innerList) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_search_popwindow, null);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.select);

        recyclerView.setLayoutManager(layoutManager);
        AddressSearchAdapter scoreTeamAdapter = new AddressSearchAdapter(getActivity(), innerList);
        recyclerView.setAdapter(scoreTeamAdapter);
        scoreTeamAdapter.setOnCheckClickListener(new AddressSearchAdapter.OnViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (null != innerList && innerList.size() > position) {
                    City city = new City(innerList.get(position).getName(), "", "", "");
                    //保存搜索记录
                    String searchKey = (innerList.get(position).getName());
                    String records = SPUtil.getString(getActivity(), "SP_SEARCH_TRAINRECORD");
                    if (TextUtils.isEmpty(records)) {
                        SPUtil.put(getActivity(), "SP_SEARCH_TRAINRECORD", searchKey);
                    } else {
                        String[] split = records.split(",");
                        for (String record : split) {
                            if (record.equals(searchKey)) {
                                if (mOnPickListener != null) {
                                    mOnPickListener.onPick(position, city);
                                    dismiss();
                                }
                                return;
                            }
                        }
                        SPUtil.put(getActivity(), "SP_SEARCH_TRAINRECORD", searchKey + "," + records);
                    }
                    if (mOnPickListener != null) {
                        mOnPickListener.onPick(position, city);
                        dismiss();
                    }

                }
            }
        });

        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.trsan)));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // backgroundAlpha(getActivity(), 1f);
            }
        });
        popupWindow.showAsDropDown(mTopView);
        // backgroundAlpha(getActivity(), 0.6f);
        //popupWindow.setAnimationStyle(R.style.anim_photo_select);
    }


}
