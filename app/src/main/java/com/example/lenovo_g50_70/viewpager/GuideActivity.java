package com.example.lenovo_g50_70.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private int[] mInts={R.drawable.wang0,R.drawable.wang1,
            R.drawable.wang4,R.drawable.wang5,R.drawable.wang3};
    private List<View> mViews;
    private GuideAdapter mAdapter;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
    }

    private void initData() {
        mViews=new ArrayList<>();
        for(int i=0;i<mInts.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(mInts[i]);
            mViews.add(imageView);
        }
        mAdapter =new GuideAdapter(mViews,this);
        mViewPager.setAdapter(mAdapter);
    }

    private void initView() {
        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //下面设置两种切页时的动画方式
        //mViewPager.setPageTransformer(true,new PageTransformer());
        mViewPager.setPageTransformer(true,new ZoomOutPageTransformer());
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==mInts.length-1){
                    //最后一个，实现动画浮现
                    mButton.setVisibility(View.VISIBLE);
                    AlphaAnimation aa=new AlphaAnimation(0,1f);
                    aa.setDuration(1000);
                    mButton.startAnimation(aa);
                }else {
                    //防止在最后一页翻回上一页，Button还在
                    mButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
