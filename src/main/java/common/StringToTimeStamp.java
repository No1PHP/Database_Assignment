package common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class StringToTimeStamp {
    //把一个yyyy-MM-dd kk:mm:ss格式的String转化为 timestamp类型
    public final static java.sql.Timestamp string2Time(String dateString) throws java.text.ParseException {
        DateFormat dateFormat;
        //dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        dateFormat.setLenient(false);
        java.util.Date timeDate = dateFormat.parse(dateString);//util类型
        java.sql.Timestamp dateTime = new java.sql.Timestamp(timeDate.getTime());//Timestamp类型,timeDate.getTime()返回一个long型
        return dateTime;
    }

}
