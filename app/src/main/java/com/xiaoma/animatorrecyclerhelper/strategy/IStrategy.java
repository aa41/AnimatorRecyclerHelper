package com.xiaoma.animatorrecyclerhelper.strategy;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xiaoma.animatorrecyclerhelper.animator.IAnimatorListener;

/**
 * author: mxc
 * date: 2017/8/29.
 */

public interface IStrategy {
    void onScrolled(RecyclerView recyclerView, int dx, int dy, LinearLayoutManager manager, IAnimatorListener listener,boolean isTouched);

    void onScrollStateChanged(RecyclerView recyclerView, int newState, LinearLayoutManager manager,float ratio);

    int getFirstShowPosition();

    void resetViewToNormal(RecyclerView recyclerView,int position);
}
