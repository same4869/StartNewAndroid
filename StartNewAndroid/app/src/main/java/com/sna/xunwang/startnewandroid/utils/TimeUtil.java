package com.sna.xunwang.startnewandroid.utils;

import com.sna.xunwang.startnewandroid.view.NextRefreshCountDownTimerView;

import java.util.Calendar;

/**
 * Created by xunwang on 16/11/10.
 */

public class TimeUtil {
    //别致刷新时间是在8点,12点,20点,输入当前时间
    //计算下次刷新时间
    public static void calcNextTime(NextRefreshCountDownTimerView nextRefreshCountDownTimerView, long curTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(curTime);
        int sec = calendar.get(Calendar.SECOND);
        int min = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour <= 8 || hour > 20) {
            if (hour > 20) {
                nextRefreshCountDownTimerView.setTime(32 - hour, 59 - min, 59 - sec);
            } else {
                nextRefreshCountDownTimerView.setTime(8 - hour, 59 - min, 59 - sec);
            }
        } else if (hour <= 12 && hour > 8) {
            nextRefreshCountDownTimerView.setTime(12 - hour, 59 - min, 59 - sec);
        } else if (hour <= 20 && hour > 12) {
            nextRefreshCountDownTimerView.setTime(20 - hour, 59 - min, 59 - sec);
        }
        nextRefreshCountDownTimerView.start();
    }
}
