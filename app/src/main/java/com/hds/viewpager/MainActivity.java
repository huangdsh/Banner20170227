package com.hds.viewpager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPagerAdapter mAdapter;//存放图片的集合
    private List<ImageView> imageViews;

    private String[] titles = new String[]{"黄垚", "吴鑫", "叫我索大人", "德芙", "我曾经很瘦"};//存放文字描述的集合
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
        dots.get(0).setBackgroundResource(R.drawable.dot_focesed);

        title = (TextView) findViewById(R.id.tv);
        title.setText(titles[0]);
        
    }
}
