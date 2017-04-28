package com.example.lenovo_g50_70.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

/**
 * Created by lenovo-G50-70 on 2017/4/26.
 */

public class MyViewPager extends ViewGroup{

    private GestureDetector mDetector;
    private Scroller mScroller;
    private int[] mInts={R.drawable.wang0,R.drawable.wang1,R.drawable.wang2,R.drawable.wang3,
            R.drawable.wang4,R.drawable.wang5,R.drawable.wang6,R.drawable.wang7,R.drawable.wang8,};

    public MyViewPager(Context context) {
        super(context);
        initView();
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){
        //当图片没设置位置时，图片不会显示
        for(int i=0;i<mInts.length;i++){
            ImageView imageView =new ImageView(getContext());
            imageView.setBackgroundResource(mInts[i]);
            //加入一个存储组中，内部有图片位置的算法，计算切换页面的下标
            //如显示在屏幕的页面下标固定为0，左边为负，右边为正,像数轴
            this.addView(imageView);
        }

        mScroller = new Scroller(getContext());
        mDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                //scrollBy：相对滑动，相对我们当前的控件多少距离，就滑动多少距离
                //distanceX是我们手滑动的距离，即我们的手相对控件滑动了多少，所以X轴滑动这个距离，Y轴滑动0
                scrollBy((int)distanceX,0);
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //捕获onTouch事件，让mDetector识别手势并处理逻辑
        mDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:


                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                //你滑动的距离加上屏幕的一半，除以屏幕宽度，如果你滑动距离超过了屏幕的一半，这个pos就加1
                int pos = (scrollX + getWidth() / 2) / getWidth();
                //滑到最后一张的时候，不能出边界
                if (pos >= mInts.length) {
                    pos = mInts.length - 1;
                }
                //绝对滑动，直接滑到指定的x值
                //scrollTo(pos * getWidth(), 0);
                //自然滑动,从手滑到的地方开始，滑动距离是页面宽度减去滑到的距离，时间由路程的大小来决定
                mScroller.startScroll(scrollX, 0, pos * getWidth() - scrollX, 0, Math.abs(pos * getWidth()));
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //图片一字排开,设置图片位置
        for (int i = 0; i < mInts.length; i++) {
            this.getChildAt(i).layout(i * getWidth(), t, (i + 1) * getWidth(), b);
        }
    }

    @Override
    public void computeScroll() {
        //Scroller的原理就是用ScrollTo来一段一段的进行，最后看上去跟自然的一样，
        //必须使用postInvalidate，这样才会一直回调computeScroll这个方法，直到滑动结束。
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), 0);
            postInvalidate();
        }
    }
}
