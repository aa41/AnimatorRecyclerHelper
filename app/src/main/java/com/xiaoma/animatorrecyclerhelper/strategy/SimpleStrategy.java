package com.xiaoma.animatorrecyclerhelper.strategy;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xiaoma.animatorrecyclerhelper.animator.IAnimatorListener;

/**
 * author: mxc
 * date: 2017/8/29.
 */

public class SimpleStrategy implements IStrategy {
    protected int firstShowPosition;
    protected RecyclerView.ViewHolder vh;
    protected IAnimatorListener mListener;
    protected boolean scrolled = false;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy, LinearLayoutManager manager, IAnimatorListener listener, boolean isTouched) {
        scrolled = true;
        mListener = listener;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState, LinearLayoutManager manager, float ratio) {

    }

    @Override
    public int getFirstShowPosition() {
        return 0;
    }

    @Override
    public void resetViewToNormal(RecyclerView recyclerView, int position) {
        for (int i = 0; i < recyclerView.getAdapter().getItemCount(); i++) {
            if (i != position) {
                RecyclerView.ViewHolder vh = recyclerView.findViewHolderForAdapterPosition(i);
                if (vh != null && vh.itemView != null) {
                    mListener.resetViewToNormal(vh.itemView);
                }

            }
        }
    }
}
