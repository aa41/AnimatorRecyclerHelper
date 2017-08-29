package com.xiaoma.animatorrecyclerhelper.animator;

import android.view.View;

/**
 * author: mxc
 * date: 2017/8/29.
 */

public interface IAnimatorListener {
    void setAnimator(float distance, float percent, View view);

    void resetViewToNormal(View view);
}
