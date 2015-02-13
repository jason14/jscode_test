package pr.jason.myuipratice.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jaesin on 2015-02-12.
 */
public class TimeConvert {

    private static final String HOUR_MINUTE_FORMAT = "%02d:%02d:%02d";
    private static final String MINUTE_FORMAT = "%02d:%02d";

    private static final String DATE_FORMAT = "MM-dd";

    public static String parseTime(long milliseconds) {

        if(milliseconds/(1000*60*60) >= 1) {
            return String.format(HOUR_MINUTE_FORMAT,
                    TimeUnit.MILLISECONDS.toHours(milliseconds),
                    TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(
                            TimeUnit.MILLISECONDS.toHours(milliseconds)), TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
        }else{
            return String.format(MINUTE_FORMAT,
                    TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(
                            TimeUnit.MILLISECONDS.toHours(milliseconds)), TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
        }
    }

    private static class TIME_MAXIMUM{
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }

    public static String formatTimeString(long milliseconds) {

        long curTime = System.currentTimeMillis();
        long regTime = milliseconds;
        long diffTime = (curTime - regTime) / 1000;

        String msg = null;
        if (diffTime < TIME_MAXIMUM.SEC) {
            // sec
            msg = "방금 전";
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            // min
            msg = diffTime + "분 전";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            // hour
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            // day
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            // day
            msg = (diffTime) + "달 전";
        } else {
            msg = (diffTime) + "년 전";
        }

        return msg;
    }


    public static String parseCompareNowTime(long milliseconds){
        String compareTime = null;
        //현재 날짜

        long nowSystem =
                System.currentTimeMillis();
        Date date = new Date(nowSystem);
        Calendar nowCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Calendar callCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        long now = date.getTime();
        callCal.setTimeInMillis(milliseconds);
        nowCal.setTimeInMillis(now);
        //날짜 비교
        int nowYear, callYear, nowMonth,callMonth,nowDate,callDate,nowMinute,callMinute,nowHour,callHour;
        nowYear = nowCal.get(Calendar.YEAR);
        callYear = callCal.get(Calendar.YEAR);
        nowMonth = nowCal.get(Calendar.MONTH);
        callMonth = callCal.get(Calendar.MONTH);
        nowDate = nowCal.get(Calendar.DAY_OF_MONTH);
        callDate = callCal.get(Calendar.DAY_OF_MONTH);
        nowHour = nowCal.get(Calendar.HOUR_OF_DAY);
        callHour = callCal.get(Calendar.HOUR_OF_DAY);
        nowMinute = nowCal.get(Calendar.MINUTE);
        callMinute = callCal.get(Calendar.MINUTE);

            nowCal.set(Calendar.YEAR,nowYear);
            nowCal.set(Calendar.MONTH,nowMonth);
            nowCal.set(Calendar.DAY_OF_MONTH,nowDate);

            nowCal.set(Calendar.HOUR,0);
            nowCal.set(Calendar.MINUTE,0);
            nowCal.set(Calendar.SECOND,0);
            nowCal.set(Calendar.MILLISECOND,0);
            callCal.set(Calendar.YEAR,callYear);
            callCal.set(Calendar.MONTH,callMonth);
            callCal.set(Calendar.DAY_OF_MONTH,callDate);
            callCal.set(Calendar.HOUR,0);
            callCal.set(Calendar.MINUTE,0);
            callCal.set(Calendar.SECOND,0);
            callCal.set(Calendar.MILLISECOND,0);
            long comparedMilliTime = nowCal.getTimeInMillis() - callCal.getTimeInMillis();
            if(comparedMilliTime <= 24*60*60*1000){//어제
                int hour = (int)(now - milliseconds)/60*60*1000;
                compareTime = hour + "시간 전";
            }else if(comparedMilliTime > 24*60*60*1000 && comparedMilliTime <= 7*24*60*60*1000){
                int day = (int)(comparedMilliTime/24*60*60*1000);
                compareTime = day + "일 전";
            }else{
                int tmpMonth = callMonth + 1;
                compareTime = tmpMonth +"월 " + callDate+"일";
            }

        //오늘 아니고 일주일 이내이면 몇일 전으로 표시

        //일주일 이내가 아니면 날짜 형식으로 표시

        return compareTime;
    }
}
