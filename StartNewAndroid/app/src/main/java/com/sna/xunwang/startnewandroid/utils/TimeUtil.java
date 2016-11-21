package com.sna.xunwang.startnewandroid.utils;

import com.sna.xunwang.startnewandroid.config.Constants;
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
        XLog.d(Constants.TAG, "hour --> " + hour + " min --> " + min + " sec --> " + sec);
        if (hour <= 7 || hour > 19) {
            if (hour > 19) {
                nextRefreshCountDownTimerView.setTime(31 - hour, 59 - min, 59 - sec);
            } else {
                nextRefreshCountDownTimerView.setTime(7 - hour, 59 - min, 59 - sec);
            }
        } else if (hour <= 11 && hour > 7) {
            nextRefreshCountDownTimerView.setTime(11 - hour, 59 - min, 59 - sec);
        } else if (hour <= 19 && hour > 11) {
            nextRefreshCountDownTimerView.setTime(19 - hour, 59 - min, 59 - sec);
        }
        nextRefreshCountDownTimerView.start();
    }
}
