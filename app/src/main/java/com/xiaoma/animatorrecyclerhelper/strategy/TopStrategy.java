package com.xiaoma.animatorrecyclerhelper.strategy;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.xiaoma.animatorrecyclerhelper.animator.IAnimatorListener;

/**
 * author: mxc
 * date: 2017/8/29.
 */

public class TopStrategy extends SimpleStrategy {

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy, LinearLayoutManager manager, IAnimatorListener listener,boolean isTouched) {
        if(!isTouched)return;
        super.onScrolled(recyclerView,dx,dy,manager,listener,isTouched);
        firstShowPosition = manager.findFirstVisibleItemPosition();
        vh = recyclerView.findViewHolderForAdapterPosition(firstShowPosition);
        if (vh != null && vh.itemView != null) {
            int height = vh.itemView.getMeasuredHeight();
            int top = Math.abs(vh.itemView.getTop());
            float percent = top / (float) height;
            Log.e("top:","b-->"+top);
            mListener.setAnimator(top, percent, vh.itemView);
        }

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState, LinearLayoutManager manager, float ratio) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE && scrolled) {
            firstShowPosition = manager.findFirstVisibleItemPosition();
            vh = recyclerView.findViewHolderForAdapterPosition(firstShowPosition);
            if (vh != null && vh.itemView != null) {
                int height = vh.itemView.getMeasuredHeight();
                int top = Math.abs(vh.itemView.getTop());
                float percent = top / (float) height;
                if (percent < ratio) {
                    recyclerView.smoothScrollBy(0, -top);
                } else {
                    recyclerView.smoothScrollBy(0, height - top);
                }
            }
        }

    }

    @Override
    public int getFirstShowPosition() {
        return firstShowPosition;
    }

}
