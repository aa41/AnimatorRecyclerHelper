package com.xiaoma.animatorrecyclerhelper.strategy;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.xiaoma.animatorrecyclerhelper.animator.IAnimatorListener;

/**
 * author: mxc
 * date: 2017/8/29.
 */

public class LeftStrategy extends SimpleStrategy  {
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy, LinearLayoutManager manager, IAnimatorListener listener, boolean isTouched) {
        if(!isTouched)return;
        super.onScrolled(recyclerView,dx,dy,manager,listener,isTouched);
        firstShowPosition = manager.findFirstVisibleItemPosition();
        vh = recyclerView.findViewHolderForAdapterPosition(firstShowPosition);
        if (vh != null && vh.itemView != null) {
            int width = vh.itemView.getMeasuredHeight();
            int left = Math.abs(vh.itemView.getLeft());
            float percent = left / (float) width;
            Log.e("top:","b-->"+left);
            mListener.setAnimator(left, percent, vh.itemView);
        }

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState, LinearLayoutManager manager, float ratio) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE && scrolled) {
            firstShowPosition = manager.findFirstVisibleItemPosition();
            vh = recyclerView.findViewHolderForAdapterPosition(firstShowPosition);
            if (vh != null && vh.itemView != null) {
                int width = vh.itemView.getMeasuredWidth();
                int left = Math.abs(vh.itemView.getLeft());
                float percent = left / (float) width;
                if (percent < ratio) {
                    recyclerView.smoothScrollBy( -left,0);
                } else {
                    recyclerView.smoothScrollBy( width - left,0);
                }
            }
        }

    }

    @Override
    public int getFirstShowPosition() {
        return firstShowPosition;
    }
}
