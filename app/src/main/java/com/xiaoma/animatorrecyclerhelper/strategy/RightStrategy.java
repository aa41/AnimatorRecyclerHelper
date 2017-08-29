package com.xiaoma.animatorrecyclerhelper.strategy;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.xiaoma.animatorrecyclerhelper.animator.IAnimatorListener;

/**
 * author: mxc
 * date: 2017/8/29.
 */

public class RightStrategy extends SimpleStrategy {
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy, LinearLayoutManager manager, IAnimatorListener listener, boolean isTouched) {
        super.onScrolled(recyclerView, dx, dy, manager, listener, isTouched);
        int parentWidth = recyclerView.getMeasuredWidth();
        firstShowPosition = manager.findLastVisibleItemPosition();
        vh = recyclerView.findViewHolderForAdapterPosition(firstShowPosition);
        if (vh != null && vh.itemView != null) {
            int width = vh.itemView.getMeasuredWidth();
            int right = Math.abs(vh.itemView.getRight() - parentWidth);
            float percent = right / (float) width;
            Log.e("right:", "b-->" + right);
            mListener.setAnimator(right, percent, vh.itemView);
        }

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState, LinearLayoutManager manager, float ratio) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE && scrolled) {
            int parentWidth = recyclerView.getMeasuredWidth();
            scrolled = false;
            firstShowPosition = manager.findLastVisibleItemPosition();
            vh = recyclerView.findViewHolderForAdapterPosition(firstShowPosition);
            if (vh != null && vh.itemView != null) {
                int width = vh.itemView.getMeasuredWidth();
                int right = Math.abs(width - vh.itemView.getRight() + parentWidth);
                float percent = right / (float) width;
                if (percent < ratio) {
                    recyclerView.smoothScrollBy( -right,0);
                } else {
                    recyclerView.smoothScrollBy(width - right,0);
                }
            }
        }

    }

    @Override
    public int getFirstShowPosition() {
        return firstShowPosition;
    }

}
