package com.wanghaisheng.xiaoyaprogressbar;

import android.content.Context;
import android.util.TypedValue;

/**
 * Author: sheng on 2016/9/9 21:49
 * Email: 1392100700@qq.com
 */
public class Utils {

    /**
     * dp 转px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context,int dpValue){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpValue,context.getResources().getDisplayMetrics());
    }

    /**
     * sp 转 px
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context,int spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spValue,context.getResources().getDisplayMetrics());
    }

}
