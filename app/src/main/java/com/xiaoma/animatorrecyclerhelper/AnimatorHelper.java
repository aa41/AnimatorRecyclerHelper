package com.xiaoma.animatorrecyclerhelper;

import android.support.annotation.IntDef;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.xiaoma.animatorrecyclerhelper.animator.AlphaAnimatorListener;
import com.xiaoma.animatorrecyclerhelper.animator.IAnimatorListener;
import com.xiaoma.animatorrecyclerhelper.strategy.BottomStrategy;
import com.xiaoma.animatorrecyclerhelper.strategy.IStrategy;
import com.xiaoma.animatorrecyclerhelper.strategy.LeftStrategy;
import com.xiaoma.animatorrecyclerhelper.strategy.RightStrategy;
import com.xiaoma.animatorrecyclerhelper.strategy.TopStrategy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * author: mxc
 * date: 2017/8/29.
 */

public class AnimatorHelper {
    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager manager;
    private IAnimatorListener listener;


    private int oldPosition = 0;
    private int firstShowPosition;

    @IntDef({TOP, BOTTOM, LEFT, RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private boolean scrollOnePage;
    private boolean autoScroll;
    private float ratio = 0.5f;
    private
    @AnimatorHelper.Type
    int type = TOP;

    private IStrategy strategy;

    private boolean isTouched = false;


    public void attachToRecyclerView(RecyclerView recyclerView) {
        if (recyclerView == null)
            throw new NullPointerException("recyclerView is NULL???");
        if (recyclerView.getAdapter() == null)
            throw new NullPointerException("adapter is NULL? pls set first");
        if (recyclerView.getLayoutManager() == null && !(recyclerView.getLayoutManager() instanceof LinearLayoutManager))
            throw new IllegalArgumentException("ur not setLayoutManger or not instance of LinearLayoutManager");
        adapter = recyclerView.getAdapter();
        manager = (LinearLayoutManager) recyclerView.getLayoutManager();

        if (strategy == null) {
            strategy = new TopStrategy();
        }

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isTouched = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        //isTouched=false;
                        break;

                }
                return false;
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(autoScroll){
                    strategy.onScrollStateChanged(recyclerView, newState, manager, ratio);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (listener == null) {
                    listener = new AlphaAnimatorListener();
                }
                if (isTouched) {
                    strategy.onScrolled(recyclerView, dx, dy, manager, listener, isTouched);
                    firstShowPosition = strategy.getFirstShowPosition();
                    if (scrollOnePage) {
                        if (oldPosition == firstShowPosition) {

                        } else {
                            oldPosition = firstShowPosition;
                            stopScroll(recyclerView);
                        }
                    }
                    strategy.resetViewToNormal(recyclerView, oldPosition);
                }


            }
        });

    }

    private void stopScroll(RecyclerView recyclerView) {
        Class<? extends RecyclerView> cls = recyclerView.getClass();
        try {
            Method method = cls.getDeclaredMethod("setScrollState", int.class);
            method.setAccessible(true);
            method.invoke(recyclerView, 0);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public IAnimatorListener getListener() {
        return listener;
    }

    public AnimatorHelper setListener(IAnimatorListener listener) {
        this.listener = listener;
        return this;
    }

    public boolean isScrollOnePage() {
        return scrollOnePage;
    }

    public AnimatorHelper setScrollOnePage(boolean scrollOnePage) {
        this.scrollOnePage = scrollOnePage;
        return this;
    }

    public boolean isAutoScroll() {
        return autoScroll;
    }

    public AnimatorHelper setAutoScroll(boolean autoScroll) {
        this.autoScroll = autoScroll;
        return this;
    }

    public float getRatio() {
        return ratio;
    }

    public AnimatorHelper setRatio(float ratio) {
        this.ratio = ratio;
        return this;
    }

    public int getType() {
        return type;
    }

    public AnimatorHelper setType(@Type int type) {
        this.type = type;
        if (type == TOP) {
            strategy = new TopStrategy();
        } else if (type == BOTTOM) {
            strategy = new BottomStrategy();
        } else if (type == LEFT) {
            strategy = new LeftStrategy();
        } else if (type == RIGHT) {
            strategy = new RightStrategy();
        }
        return this;
    }

    public IStrategy getStrategy() {
        return strategy;
    }

    public AnimatorHelper setStrategy(IStrategy strategy) {
        this.strategy = strategy;
        return this;
    }
}
