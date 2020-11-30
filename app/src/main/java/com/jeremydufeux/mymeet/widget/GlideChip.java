package com.jeremydufeux.mymeet.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.chip.Chip;
import com.jeremydufeux.mymeet.R;

public class GlideChip extends Chip {

    public GlideChip(Context context) {
        super(context);

        int[][] backgroundStates = new int[][] {
                new int[] { android.R.attr.state_checked}, // checked
                new int[] { -android.R.attr.state_checked}  // unchecked
        };
        int[] backgroundColors = new int[] {
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.light_grey)
        };
        ColorStateList backgroundList = new ColorStateList(backgroundStates, backgroundColors);
        setChipBackgroundColor(backgroundList);
        
        int[][] textStates = new int[][] {
                new int[] { android.R.attr.state_checked}, // checked
                new int[] { -android.R.attr.state_checked}  // unchecked
        };
        int[] textColors = new int[] {
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.dark_grey)
        };
        ColorStateList textList = new ColorStateList(textStates, textColors);
        setTextColor(textList);
    }

    public GlideChip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Set an image from an URL for the {@link Chip} using {@link com.bumptech.glide.Glide}
     * @param url icon URL
     */
    public GlideChip setIconUrl(String url) {
        Glide.with(this)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        //setChipIcon(errDrawable);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        setChipIcon(resource);
                        return false;
                    }
                }).preload();
        return this;
    }

}
