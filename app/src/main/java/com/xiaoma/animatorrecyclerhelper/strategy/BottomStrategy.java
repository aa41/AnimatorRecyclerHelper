package com.xiaoma.animatorrecyclerhelper.strategy;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.xiaoma.animatorrecyclerhelper.animator.IAnimatorListener;

/**
 * author: mxc
 * date: 2017/8/29.
 */

public class BottomStrategy extends SimpleStrategy {


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy, LinearLayoutManager manager, IAnimatorListener listener,boolean isTouched) {
        super.onScrolled(recyclerView,dx,dy,manager,listener,isTouched);
        int parentHeight = recyclerView.getMeasuredHeight();
        firstShowPosition = manager.findLastVisibleItemPosition();
        vh = recyclerView.findViewHolderForAdapterPosition(firstShowPosition);
        if (vh != null && vh.itemView != null) {
            int height = vh.itemView.getMeasuredHeight();
            int bottom = Math.abs(vh.itemView.getBottom() - parentHeight);
            float percent = bottom / (float) height;
            Log.e("bottom:", "b-->" + bottom);
            mListener.setAnimator(bottom, percent, vh.itemView);
        }

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState, LinearLayoutManager manager, float ratio) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE && scrolled) {
            int parentHeight = recyclerView.getMeasuredHeight();
            scrolled = false;
            firstShowPosition = manager.findLastVisibleItemPosition();
            vh = recyclerView.findViewHolderForAdapterPosition(firstShowPosition);
            if (vh != null && vh.itemView != null) {
                int height = vh.itemView.getMeasuredHeight();
                int bottom = Math.abs(height-vh.itemView.getBottom()+parentHeight);
                float percent = bottom / (float) height;
                if (percent < ratio) {
                    recyclerView.smoothScrollBy(0, -bottom);
                } else {
                    recyclerView.smoothScrollBy(0, height - bottom);
                }
            }
        }

    }

    @Override
    public int getFirstShowPosition() {
        return firstShowPosition;
    }

}
