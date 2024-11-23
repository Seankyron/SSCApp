package com.example.sscapp.utils;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CarouselLayoutManager extends LinearLayoutManager {

    private final float shrinkAmount = 0.15f;
    private final float rotationAmount = 25f;

    public CarouselLayoutManager(Context context) {
        super(context);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
        float midpoint = getWidth() / 2.f;
        float d0 = 0.f;
        float d1 = midpoint * 1.5f;
        float s0 = 1.f;
        float s1 = 1.f - shrinkAmount;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            float childMidpoint = (getDecoratedRight(child) + getDecoratedLeft(child)) / 2.f;
            float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
            float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
            child.setScaleX(scale);
            child.setScaleY(scale);

            float rotation = rotationAmount * (midpoint - childMidpoint) / getWidth();
            child.setRotationY(rotation);

            // Adjust the translation to create a circular effect
            float translationZ = -Math.abs(rotation) * 10;
            child.setTranslationZ(translationZ);

            float translationX = calculateTranslationX(childMidpoint, midpoint, child.getWidth(), scale);
            child.setTranslationX(translationX);
        }
        return scrolled;
    }

    private float calculateTranslationX(float childMidpoint, float midpoint, float childWidth, float scale) {
        float distance = childMidpoint - midpoint;
        float direction = distance > 0 ? 1 : -1;
        float absDist = Math.abs(distance);
        float maxTranslation = childWidth * 0.5f;
        float normalizedDist = Math.min(absDist / (getWidth() * 0.5f), 1);
        return direction * maxTranslation * normalizedDist * (1 - scale);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        scrollHorizontallyBy(0, recycler, state);
    }
}

