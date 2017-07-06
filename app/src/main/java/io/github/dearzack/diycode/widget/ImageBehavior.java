package io.github.dearzack.diycode.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import io.github.dearzack.diycode.R;

/**
 * Created by Zack on 2017/7/6.
 */

public class ImageBehavior extends CoordinatorLayout.Behavior<View> {

    private float threshold = 0;

    public ImageBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.app_bar_layout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (threshold == 0) {
            threshold = child.getContext().getResources().getDimension(R.dimen.personal_threshold);
        }
        float dependencyY = Math.abs(dependency.getY());
        float precent = Math.min(dependencyY / threshold, 1);
        child.setScaleX(1 - precent);
        child.setScaleY(1 - precent);
        child.setAlpha(1 - precent);
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
