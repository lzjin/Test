package com.danqiu.myapplication.activity;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.danqiu.myapplication.R;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.imagepipeline.image.ImageInfo;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by Administrator on 2018/9/30.
 *  大图预览
 */

public class PreviewAct extends BaseActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_sum)
    TextView tvSum;
    private int indexImg=0;
    private List<String> urlList;
    private List<PhotoDraweeView> imageList;
    private boolean isLocal=false;

    @Override
    public void initCreate(Bundle savedInstanceState) {
        //去除title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImmersionBar.with(this).init();

        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);
        initData();



    }

    private void initData() {
        //获取当前显示第几张图片
         indexImg=0;
        //获取所有图片列表
        urlList=new ArrayList<>();
        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594877134192&di=1efc1454f02f066b23a26816ab09c02a&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fzhidao%2Fwh%3D450%2C600%2Fsign%3Da97aff14ac86c91708565a3dfc0d5cf9%2F30adcbef76094b36901f0001a8cc7cd98c109d48.jpg");
        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594877159911&di=9caefee062c83c1bfa74622aeb74893d&imgtype=0&src=http%3A%2F%2Fb.zol-img.com.cn%2Fdesk%2Fbizhi%2Fimage%2F1%2F960x600%2F1349110400149.jpg");
        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594877186625&di=5890d642e62f71f69bda9926232d53a5&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn17%2F662%2Fw640h822%2F20180806%2F7a40-hhhczfc6181567.jpg");
        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594877210679&di=0862229abe7989446436dbe2e1b039b5&imgtype=0&src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2017%2F01%2F15%2F148446031381262932.JPEG");

        imageList= new ArrayList<>();
        for(int i=0;i<urlList.size();i++){
            PhotoDraweeView image=new PhotoDraweeView(this);
            image.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
            Uri uri;
            if (isLocal) {//是否本地文件加载
                uri = new Uri.Builder().scheme(UriUtil.LOCAL_FILE_SCHEME).path("路径")
                        .build();
            }else {
                uri = Uri.parse(urlList.get(i));
            }
            setDataToPhotoView(image,uri);
            imageList.add(image);

        }
        tvSum.setText(indexImg +"/" + urlList.size());
        viewPager.setAdapter(new MyPageAdapter());
        viewPager.setCurrentItem(indexImg);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tvSum.setText((position + 1) + "/" +imageList.size());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setDataToPhotoView(final PhotoDraweeView image,Uri url) {
        final PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(url);
        controller.setOldController(image.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null || image == null) {
                    return;
                }
                image.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        image.setController(controller.build());

    }

    class MyPageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        //PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
        @Override
        public void destroyItem(View container, int position, Object object) {
          //  ((ViewPager) container).removeView((View) object);
            ((ViewPager) container).removeView(imageList.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            PhotoDraweeView t = imageList.get(position);
            ((ViewPager) container).addView(t);
            return t;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
    }
}
