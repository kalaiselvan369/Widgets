package com.example.developer.apppermissions;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by developer on 10/7/17.
 */

public class PollResultBar extends ProgressBar {

    private Paint progressPaint;
    private int barThickness;
    private int barColor;
    private int progress = 80;
    private IndicatorType indicatorType;

    public enum IndicatorType {
        Line, Circle, Square
    }

    public PollResultBar(Context context) {
        super(context);
    }

    public PollResultBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PollResultBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs) {
        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PollResultBar, 0, 0);
        try {
            setBarColor(typedArray.getColor(R.styleable.PollResultBar_barColor,
                    ContextCompat.getColor(getContext(), R.color.colorAccent)));
            setBarThickness(typedArray.getDimensionPixelOffset(R.styleable.PollResultBar_barThickness, 4));
            int index = typedArray.getInt(R.styleable.PollResultBar_indicatorType, 0);
            setIndicatorType(IndicatorType.values()[index]);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        int halfHeight = getHeight() / 2;
        int progressEndX = (int) (getWidth() * progress / 100f);
        progressPaint.setStrokeWidth(barThickness);
        int color = barColor;
        progressPaint.setColor(color);
        canvas.drawLine(0, halfHeight, progressEndX, halfHeight, progressPaint);
        progressPaint.setColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
        canvas.drawLine(progressEndX, halfHeight, getWidth(), halfHeight, progressPaint);
    }


    private void setBarColor(int color) {
        this.barColor = color;
        postInvalidate();
    }

    private void setBarThickness(int dimensionPixelOffset) {
        this.barThickness = dimensionPixelOffset;
        postInvalidate();
    }

    public void setIndicatorType(IndicatorType indicatorType) {
        this.indicatorType = indicatorType;
        postInvalidate();
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

}
