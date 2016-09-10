package com.wanghaisheng.xiaoyaprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Author: sheng on 2016/9/10 01:11
 * Email: 1392100700@qq.com
 */
public class RoundProgressBarWithProgress extends HorizontalProgressBarWithProgress {

    //半径
    private int mRoundRadius = 30;

    //paint的最大宽度，因为reachbar 和 unreachbar的宽度可能不一样，则取两者中最大值
    private int mMaxPaintStrictWidth;

    private RectF mArcRectF;

    public RoundProgressBarWithProgress(Context context) {
        this(context,null);
    }

    public RoundProgressBarWithProgress(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundProgressBarWithProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRoundRadius = Utils.dp2px(mContext,mRoundRadius);

        TypedArray typedArray = getResources().obtainAttributes(attrs,R.styleable.RoundProgressBarWithProgress);

        mRoundRadius = (int) typedArray.getDimension(R.styleable.RoundProgressBarWithProgress_progress_radius,mRoundRadius);

        typedArray.recycle();

        //设置paint的一些属性
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mArcRectF = new RectF(0,0,2*mRoundRadius,2*mRoundRadius);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //paint的width最大值为reachHeight和unreachHeight中的最大值
        mMaxPaintStrictWidth = Math.max(mReachHeight,mUnreachHeight);
        //期望大小为: mMaxPaintStrictWidth+直径+左右padding(默认左右上下padding是一样的)
        int expectSize = mMaxPaintStrictWidth + mRoundRadius*2 + getPaddingLeft() + getPaddingRight();

        int width = resolveSize(expectSize,widthMeasureSpec);
        int height = resolveSize(expectSize,heightMeasureSpec);

        //设置真正的宽度为width和height中的最小值
        int realSize = Math.min(width,height);

        mRoundRadius = (realSize - getPaddingLeft() - getPaddingRight() - mMaxPaintStrictWidth)/2;

        setMeasuredDimension(realSize,realSize);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        canvas.save();
        canvas.translate(getPaddingLeft()+mMaxPaintStrictWidth/2,getPaddingTop()+mMaxPaintStrictWidth/2);
        //比例
        float ratio = getProgress()*1.0f/getMax();

        //draw unreachbar
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mUnreachColor);
        mPaint.setStrokeWidth(mUnreachHeight);
        canvas.drawCircle(mRoundRadius,mRoundRadius,mRoundRadius,mPaint);

        //draw reachbar
        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachHeight);
        float sweepAngle = ratio * 360;
        canvas.drawArc(mArcRectF,0,sweepAngle,false,mPaint);

        //draw text
        String text = (int)(ratio*100) + "%";
        int textWidth = (int) mPaint.measureText(text);
        int textHeight = (int) (mPaint.descent()+mPaint.ascent());
        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text,mRoundRadius-textWidth/2,mRoundRadius-textHeight/2,mPaint);

        canvas.restore();

    }
}
