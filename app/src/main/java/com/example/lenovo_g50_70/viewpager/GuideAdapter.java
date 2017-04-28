package com.example.lenovo_g50_70.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo-G50-70 on 2017/4/26.
 */

public class GuideAdapter extends PagerAdapter {

    private List<View> mViews=new ArrayList<>();
    private Context mContext;

    public GuideAdapter(List<View> views,Context context){
        mViews=views;
        mContext=context;
    }

    //添加ListItem
    public Object instantiateItem(View container,int position){
        ((ViewPager) container).addView(mViews.get(position));
        return mViews.get(position);
    }

    //删除ListItem
    public void destroyItem(View container,int position,Object object){
        ((ViewPager) container).removeView(mViews.get(position));
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==object);
    }
}
