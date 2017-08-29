package com.xiaoma.animatorrecyclerhelper.animator;

import android.view.View;

/**
 * author: mxc
 * date: 2017/8/29.
 */

public class AlphaAnimatorListener implements IAnimatorListener {
    @Override
    public void setAnimator(float distance, float percent, View view) {
        view.setAlpha(1 - percent);
    }

    @Override
    public void resetViewToNormal(View view) {
        view.setAlpha(1);
    }
}
