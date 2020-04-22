package Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Zhining
 * @description Common Utils methods
 * @create 2020-04-23-03-02
 **/
public class UtilsImpl{

    /**
    * @author Zhining
    * @description parsing date type(mysql) to String
    * @param date
    * @return String
    * @create 2020/4/23 3:09 上午
    **/
    public static String dateParsingToString(Date date) {
        String str = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            str = sdf.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }

        return str;
    }

    //
}
