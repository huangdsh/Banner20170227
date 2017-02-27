package com.hds.viewpager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPagerAdapter mAdapter;//存放图片的集合
    private List<ImageView> imageViews;

    private String[] titles = new String[]{"近来可好", "阿道夫", "叫我索大人", "德芙", "我曾经很瘦"};//存放文字描述的集合
    private List<View> dots;//存放小点点的集合
    private TextView title;//图片的标题
    //存放图片id的集合
    private int[] imageIds = new int[]{
            R.drawable.aaa,
            R.drawable.bb,
            R.drawable.cc,
            R.drawable.dd,
            R.drawable.ee
    };
    private int oldPosition = 0 ;//记录上一个点的位置
    private ScheduledExecutorService scheduledExecutorService;//线程池，用来定时轮播
    private int currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.vp);
        //显示图片的集合
        imageViews = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            imageViews.add(imageView);
        }

        //显示小点点的集合
        dots = new ArrayList<>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));
        dots.get(0).setBackgroundResource(R.drawable.dot_focesed);//刚进来默认显示第一张图

        title = (TextView) findViewById(R.id.tv);//显示图片标题
        title.setText(titles[0]);

        mAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(mAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //标题的改变
                title.setText(titles[position]);
                //小点点的改变
                dots.get(position).setBackgroundResource(R.drawable.dot_focesed);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                oldPosition = position;//再次改变时，当前的position即为老的position
                currentItem = position;//用于当用户手动滑动轮播图处理

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //自动轮播
    }

    protected void onStart(){
        super.onStart();
        //开启一个单独的后台线程
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        //给线程添加一个定时的调度任务
//        Runnable command,
//        long initialDelay,
//        long delay,
//        TimeUnit unit
        //参数说明：延迟initialDelay时间后，开始执行command。
        //并且按照delay时间周期性重复调用
        //TimeUnit用来定义时间单位
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPagerTask(),2,2, TimeUnit.SECONDS
        );
    }

    private class ViewPagerTask implements Runnable{

        @Override
        public void run() {
            //确定ViewPager跳转到哪个页面
            //使用取余的方式来确定
            currentItem = (currentItem + 1) % imageIds.length;
            mhandler.sendEmptyMessage(0);//只是为了调用UI更新，发一个空消息
        }
    }
    private Handler mhandler = new Handler(){
      @Override
        public void handleMessage(Message msg){
          viewPager.setCurrentItem(currentItem);
      }

    };


    private  class ViewPagerAdapter extends PagerAdapter {
        //获取当前窗体界面数量
        @Override
        public int getCount() {
            return imageViews.size();
        }

        //用于判断是否有对象生成的界面
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        //return一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放入当前的ViewPager中
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            return imageViews.get(position);
        }

        //从Viewgroup中移除当前的view
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position));
        }
    }
}
