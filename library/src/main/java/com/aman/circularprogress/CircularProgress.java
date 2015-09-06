package com.aman.circularprogress;

/*
 * Copyright 2015 Amandeep Anguralla
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A subclass of {@link android.view.View} class for creating a custom circular progressBar
 *
 * Created by Amandeep Anguralla on 28/08/2015.
 */
public class CircularProgress extends View {
    private static final String LOG_TAG = CircularProgress.class.getSimpleName();

    /**
     * ProgressBar's line thickness
     */
    private float foregroundStrokeWidth = 4;

    /**
     * ProgerssBar's background line thickness
     */
    private float backgroundStrokeWidth = 2;

    /**
     * Current progress
     */
    private float progress = 0;

    /**
     * Text inside circular progress bar
     */
    private int textSize = 50;

    /**
     * Minimum value of progress
     * default is 0
     */
    private int min = 0;

    /**
     * Maximum value of progress
     * default is 100
     */
    private int max = 100;

    /**
     * Color of text in progress bar
     */
    private int textColor = Color.BLACK;

    /**
     * Text of progress
     */
    private String text = "4.5";

    /**
     * Array of colors to animate while progessing
     */
    private int[] colors = null;

    /**
     * Sweep speed of progress bar
     */
    private float sweepSpeed = 1;

    /**
     * Color of line behind progress
     * default is dark gray
     */
    private int backgroundColor = Color.DKGRAY;

    /**
     * Speed of progress bar
     */
    private static final long SPEED = 1000;

    /**
     * Start angle of the progress bar
     */
    private static final int startAngle = -90;

    /**
     * Default color of progress bar
     */
    private int color = Color.DKGRAY;

    private RectF rectF;
    private RectF rectB;
    private Paint backgroundPaint;
    private Paint foregroundPaint;
    private Paint textPaint;

    public float getBackgroundStrokeWidth() {
        return this.backgroundStrokeWidth;
    }

    public float getForegroundStrokeWidth() {
        return this.foregroundStrokeWidth;
    }

    public void setBackgroundStrokeWidth(float strokeWidth) {
        this.backgroundStrokeWidth = strokeWidth;
        backgroundPaint.setStrokeWidth(backgroundStrokeWidth);
        invalidate();
        requestLayout();//Because it should recalculate its bounds
    }

    public void setForegroundStrokeWidth(float strokeWidth) {
        this.foregroundStrokeWidth = strokeWidth;
        foregroundPaint.setStrokeWidth(strokeWidth);
        invalidate();
        requestLayout();
    }

    /**
     * Set width of the text. it will automatically invalidate and redraw the view
     * @param strokeWidth is the width of the text
     */
    public void setTextStrokeWidth(int strokeWidth) {
        this.textSize = strokeWidth;
        this.textPaint.setTextSize(strokeWidth);
        invalidate();
        requestLayout();
    }

    /**
     *
     * Set color of the text. it will automatically invalidate and redraw the view
     * @param color is the the color of the text to set
     */
    public void setTextColor(int color) {
        this.textColor = color;
        this.textPaint.setColor(textColor);
        invalidate();
        requestLayout();
    }

    /**
     * Set the progress text. Automatically invalidate and redraws text.
     * @param text is the string to set as text.
     */
    public void setText(String text) {
        this.text = text;
        invalidate();
        requestLayout();
    }

    /**
     * The floating point value to set as progress.
     * Precision of floating point is set to 1 digit
     * @param text is the text to set
     */
    public void setText(float text) {
        setText(String.format(Locale.getDefault(), "%.1f", text));
    }

    /**
     * Set a string resource to text
     * @param id is the id of string resource
     */
    public void setText(int id) {
        setText(getContext().getString(id));
    }

    /**
     * @return the text of progress
     */
    public String getText() {
        return this.text;
    }

    /**
     *
     * @return the text color in integer.
     */
    public int getTextColor() {
        return this.textColor;
    }

    /**
     * @return the progress of progress bar
     */
    public float getProgress() {
        return progress;
    }

    /**
     * Function to set progress of progress bar without animation
     *
     * This is automatically called by the animation interpolator
     * from function {@link CircularProgress#setProgressWithAnimation(float)}
     *
     * @param progress to set without animation
     */
    public void setProgress(float progress) {

        if(progress > this.max)
            throw new IllegalArgumentException("Progress cannot be greater than maximum progress value which is " + this.max);

        if(progress < this.min)
            throw new IllegalArgumentException("Progress cannot be less that minimum progress value which is " + this.min);

        this.progress = progress;

        if(colors != null && colors.length > 0) {
            int index = (int) (progress / (max - min) * colors.length);
            index = (Math.min(Math.max(1, index), colors.length - 1));

            foregroundPaint.setColor(colors[index]);
        }
    }

    /**
     * @return minimum value of progress
     */
    public int getMin() {
        return min;
    }

    /**
     * Set minimum value of progress in integer
     * @param min is the min value of progress to set
     */
    public void setMin(int min) {
        if(min >= max)
            throw new IllegalArgumentException("min value must be smaller than max value");
        this.min = min;
        invalidate();
        requestLayout();
    }

    /**
     * Get maximum value of progress
     * @return max value of progress in integer
     */
    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if(max <= min)
            throw new IllegalArgumentException("max value should be greater than min value");
        this.max = max;
        invalidate();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        foregroundPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    /**
     * get color of background circle in progress bar
     * @return the background circle color of progress
     */
    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(int color) {
        this.backgroundColor = color;
        backgroundPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    public CircularProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     *
     * Initialize progress bar
     * @param context is the context
     * @param attrs are the xml attributes set
     */
    private void init(Context context, AttributeSet attrs) {
        rectF = new RectF();
        rectB = new RectF();
        int colorIds;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircularProgress,
                0, 0);
        //Reading values from the XML layout
        try {
            foregroundStrokeWidth = typedArray.getDimension(R.styleable.CircularProgress_foregroundStroke, foregroundStrokeWidth);
            backgroundStrokeWidth = typedArray.getDimension(R.styleable.CircularProgress_backgroundStroke, backgroundStrokeWidth);
            progress = typedArray.getFloat(R.styleable.CircularProgress_progress, progress);
            color = typedArray.getInt(R.styleable.CircularProgress_progressbarColor, color);
            textColor = typedArray.getInt(R.styleable.CircularProgress_textColor, textColor);
            min = typedArray.getInt(R.styleable.CircularProgress_min, min);
            max = typedArray.getInt(R.styleable.CircularProgress_max, max);
            text = typedArray.getString(R.styleable.CircularProgress_text);
            colorIds = typedArray.getResourceId(R.styleable.CircularProgress_colors, 0);
            textSize = typedArray.getDimensionPixelSize(R.styleable.CircularProgress_textSize, textSize);
            backgroundColor = typedArray.getColor(R.styleable.CircularProgress_backgroundColor, backgroundColor);
            sweepSpeed = typedArray.getFloat(R.styleable.CircularProgress_sweepSpeed, sweepSpeed);

            if(max <= min)
                throw new IllegalArgumentException("max value should be greater than min value");
        } finally {
            // recycle typed array
            typedArray.recycle();
        }

        if(colorIds != 0) {
            // getting the colors to be animated during progress
            colors = getContext().getResources().getIntArray(colorIds);
        }

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(backgroundStrokeWidth);

        foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        foregroundPaint.setColor(color);
        foregroundPaint.setStyle(Paint.Style.STROKE);
        foregroundPaint.setStrokeWidth(foregroundStrokeWidth);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the background circle below progress bar
        canvas.drawOval(rectB, backgroundPaint);
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
        // draw text inside progress bar
        canvas.drawText(text, xPos, yPos, textPaint);
        float angle = 360 * progress / max;

        // draw the progress
        canvas.drawArc(rectF, startAngle, angle, false, foregroundPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int min = Math.min(width, height);
        setMeasuredDimension(min, min);
        // rectangle for the background circle
        rectB.set(0 + backgroundStrokeWidth / 2, 0 + backgroundStrokeWidth / 2, min - backgroundStrokeWidth / 2, min - backgroundStrokeWidth / 2);
        // rectangle for progress bar
        rectF.set(0 + foregroundStrokeWidth / 2, 0 + foregroundStrokeWidth / 2, min - foregroundStrokeWidth / 2, min - foregroundStrokeWidth / 2);
    }

    /**
     * Set the progress with an animation.
     * Note that the {@link android.animation.ObjectAnimator} Class automatically set the progress
     * so don't call the {@link CircularProgress#setProgress(float)} directly within this method.
     *
     * @param progress The progress it should animate to it.
     */
    public void setProgressWithAnimation(float progress) {

        if(progress > this.max)
            throw new IllegalArgumentException("Progress cannot be greater than maximum progress value which is " + this.max);

        if(progress < this.min)
            throw new IllegalArgumentException("Progress cannot be less that minimum progress value which is " + this.min);

        ArrayList<Integer> foregroundColors;
        if(colors != null && colors.length > 0) {
            float divisions = (float)(max - min) / (float)colors.length;
            foregroundColors = new ArrayList<>();
            int maxLength = (int) Math.ceil(progress / divisions);
            maxLength = (Math.min(Math.max(1, maxLength), colors.length));

            for(int i = 0; i < maxLength; i++) {
                foregroundColors.add(colors[i]);
            }
        } else {
            foregroundColors = new ArrayList<>();
            foregroundColors.add(color);
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration((long)sweepSpeed * SPEED);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(this, "progress", progress),
                ObjectAnimator.ofFloat(this, "text", progress),
                        ObjectAnimator.ofObject(foregroundPaint, "color", new android.animation.ArgbEvaluator(), foregroundColors.toArray())

                );
        animatorSet.start();
    }
}