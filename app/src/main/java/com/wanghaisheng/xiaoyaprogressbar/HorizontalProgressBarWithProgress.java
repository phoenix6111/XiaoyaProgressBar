package com.wanghaisheng.xiaoyaprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;

/**
 * Author: sheng on 2016/9/9 20:26
 * Email: 1392100700@qq.com
 */
public class HorizontalProgressBarWithProgress extends ProgressBar {
    private static final String TAG = "HorizontalProgress";

    //默认的属性值
    public static final int DEFAULT_TEXT_COLOR = 0xfffc00d1;
    public static final int DEFAULT_TEXT_SIZE = 10;//sp
    public static final int DEFAULT_TEXT_OFFSET = 10;//dp
    public static final int DEFAULT_REACH_COLOR = DEFAULT_TEXT_COLOR;
    public static final int DEFAULT_REACH_HEIGHT = 2;//dp
    public static final int DEFAULT_UNREACH_COLOR = 0xffd3d6da;
    public static final int DEFAULT_UNREACH_HEIGHT = 2;//dp

    protected Context mContext;

    protected int mTextColor = DEFAULT_TEXT_COLOR;
    protected int mTextSize ;
    protected int mTextOffset ;
    protected int mReachColor = DEFAULT_REACH_COLOR;
    protected int mReachHeight ;
    protected int mUnreachColor = DEFAULT_UNREACH_COLOR;
    protected int mUnreachHeight;

    //实际绘制的宽度（减去padding）
    protected int mRealWidth;
    protected Paint mPaint = new Paint();

    public HorizontalProgressBarWithProgress(Context context) {
        this(context,null);
    }

    public HorizontalProgressBarWithProgress(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalProgressBarWithProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        initData();

        //获取自定义属性值
        obtainAttrValue(attrs);

        //设置字体大小，以在测量时确定height
        mPaint.setTextSize(mTextSize);
    }

    private void initData() {
        mTextSize = Utils.sp2px(mContext,DEFAULT_TEXT_SIZE);
        mTextOffset = Utils.dp2px(mContext,DEFAULT_TEXT_OFFSET);
        mReachHeight  = Utils.dp2px(mContext,DEFAULT_REACH_HEIGHT);
        mUnreachHeight = Utils.dp2px(mContext,DEFAULT_UNREACH_HEIGHT);
    }

    /**
     * 获取自定义属性值
     * @param attrs
     */
    private void obtainAttrValue(AttributeSet attrs) {

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,R.styleable.HorizontalProgressBarWithProgress);

        mTextColor = typedArray.getColor(R.styleable.HorizontalProgressBarWithProgress_progress_text_color,mTextColor);
        mTextSize = (int) typedArray.getDimension(R.styleable.HorizontalProgressBarWithProgress_progress_text_size,mTextSize);
        mTextOffset = (int) typedArray.getDimension(R.styleable.HorizontalProgressBarWithProgress_progress_text_offset,mTextOffset);
        mReachColor = typedArray.getColor(R.styleable.HorizontalProgressBarWithProgress_progress_reach_color,mReachColor);
        mReachHeight = (int) typedArray.getDimension(R.styleable.HorizontalProgressBarWithProgress_progress_reach_height,mReachHeight);
        mUnreachColor = typedArray.getColor(R.styleable.HorizontalProgressBarWithProgress_progress_unreach_color,mUnreachColor);
        mUnreachHeight = (int) typedArray.getDimension(R.styleable.HorizontalProgressBarWithProgress_progress_unreach_height,mUnreachHeight);

        typedArray.recycle();

    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //测量宽度，要求用户必须给出明确的宽度值
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        //测量高度
        int heightSize = measureHeight(heightMeasureSpec);

        //设置宽度和高度
        setMeasuredDimension(widthSize,heightSize);

        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    /**
     * 测量高度
     * @param heightMeasureSpec
     * @return
     */
    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        //如果是exactly，即给出明确的值，
        if(heightMode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            //否则取text的高度和reachHeight,unReachHeight的高度中的最大值加上padding
            //获取text的高度
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());
            result = getPaddingTop() + getPaddingBottom() + Math.max(Math.max(mReachHeight,mUnreachHeight),Math.abs(textHeight));

            //如果是at_most，则不能超过给定的高度
            if(heightMode == MeasureSpec.AT_MOST) {
                result = Math.min(result,size);
            }
        }

        return result;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        canvas.save();
        //将canvas移动到padding处
        canvas.translate(getPaddingLeft(),getMeasuredHeight()/2);
        //是否需要绘制 unreachbar
        boolean needUnreachBar = true;

        //draw reachbar
        float ratio = getProgress()*1.0f/getMax();
        Log.d(TAG,"progress "+ratio);
        String text = (int)(ratio * 100) + "%";
        //获取字体的宽度
        int textWidth = (int) mPaint.measureText(text);
        // reachbar end x
        int progressEndX = (int) ((mRealWidth - textWidth - mTextOffset) * ratio);
        if(progressEndX + textWidth + mTextOffset > mRealWidth) {
            progressEndX = mRealWidth - textWidth - mTextOffset;
            needUnreachBar = false;
        } else {
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            canvas.drawLine(0,0,progressEndX,0,mPaint);
        }

        // draw text
        mPaint.setColor(mTextColor);
        int y = (int) (-(mPaint.descent() + mPaint.ascent())/2);
        canvas.drawText(text,progressEndX + mTextOffset/2,y,mPaint);

        // draw unreachbar
        if(needUnreachBar) {
            mPaint.setColor(mUnreachColor);
            mPaint.setStrokeWidth(mUnreachHeight);
            canvas.drawLine(progressEndX + mTextOffset + textWidth,0,mRealWidth,0,mPaint);
        }


        canvas.restore();
    }
}
