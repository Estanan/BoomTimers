package com.example.administrator.boomtimer.util;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.administrator.boomtimer.R;
import com.example.administrator.boomtimer.model.Tag;
import com.example.administrator.boomtimer.model.MyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public class SmallUtil {

//    public static String generateDurationStr (long duration) {
//        String durationStr = generatePart((int)(duration / 3600)) + ":" +
//                generatePart((int)(duration / 3600 %60)) + ":" +
//                generatePart((int)(duration % 60));
//        return durationStr;
//    }

    public static String generatePart(int time) {
        String back;
        if (time < 10) {
            back = "0" + time;
        } else {
            back = "" + time;
        }
        return back;
    }

    public static DisplayMetrics getScreenSize(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }

    public static void changeIcon(AppCompatImageView iv, Tag tag) {
        iv.setImageResource(tag.getIcon());
        int color = tag.getColor();
        iv.setColorFilter(color);
    }

    public static void changeColor(AppCompatImageView iv, Tag tag) {
        iv.setImageResource(tag.getIcon());
        int color = tag.getColor();
        iv.setColorFilter(color);
    }

    public static MyTime gainTime() {
        Calendar ca = Calendar.getInstance();
        MyTime myTime = new MyTime(ca.get(ca.YEAR),
                ca.get(ca.MONTH) + 1,
                ca.get(ca.DATE),
                ca.get(ca.HOUR_OF_DAY),
                ca.get(ca.MINUTE),
                ca.get(ca.SECOND));
        return myTime;
    }

    public static int gainIntDuration(MyTime begin, MyTime end) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date now;
        java.util.Date date;
        try {
            now = df.parse(end.toString());
            date = df.parse(begin.toString());
            int l = (int) ((now.getTime() - date.getTime()) / 1000);
            return l;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String gainSetDuration(int duration) {
        String back;
        back = generatePart(duration / 3600) + ":" +
                generatePart(duration % 3600 / 60) + ":" +
                generatePart(duration % 60);
        return back;
    }

    public static String gainHistoryDuration(int duration) {
        String back;
        String minute = generatePart(duration / 60);
        String second = generatePart(duration % 60);
        back = minute.equals("00") ?
                second + "秒" :
                minute + "分" + second + "秒";
        return back;
    }

//    public static String gainStringDuration(MyTime begin, MyTime end) {
//        int duration = gainIntDuration(begin, end);
//        String back = gainStringDuration(duration);
//        return back;
//    }
//    public static String gainStringDuration(int duration) {
//        String back;
//        if (duration < 3600) {
//            back = generatePart(duration / 60) + ":" + generatePart(duration % 60);
//        } else {
//            back = generatePart(duration / 3600) + ":" + generatePart((duration % 3600) / 60);
//        }
//        return back;
//    }

    public static String yearMouthDay(MyTime time) {
        String back = "";
        if (time != null) {
            back = time.getYear() + "." +
                    time.getMonth() + "." +
                    time.getDay();
        }
        return back;
    }

    public static String timepoint(MyTime time) {
        String back = generatePart(time.getHour()) + ":" + generatePart(time.getMinute());
        return back;
    }

    public static String monthDay(MyTime time) {
        String back = time.getHour() + ":" + time.getMinute() + ":" + time.getSecond();
        return back;
    }

    /**
     * 获取签名时间
     *
     * @return
     */
    public static String getSignatureTime() {
        long epoch = new Date().getTime();
        long value = epoch / 1000;
        //Log.d(TAG, "unix stamp is "+epoch+", the return result  is" + value );
        return String.valueOf(value);
    }

    public static String getSignature() {
        return "sign";
    }

}
