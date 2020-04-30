package common;

import javax.servlet.http.HttpServletRequest;

/**
 * @description common uilt class for request handling from webpage
 * @create 2020-04-30-03-06
 **/
public class HttpServletRequestUtils {
    /**
    * @author Zhining
    * @description Convert the Integer to int type, based on the key of request to extract the int value
    * @param request, key
    * @return int
    * @create 2020/4/30 3:08 上午
    **/
    public static int getInt(HttpServletRequest request, String key){
        try{
            return Integer.decode(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    public static double getDouble(HttpServletRequest request, String key){
        try{
            return Double.valueOf((request.getParameter(key)));
        }catch (Exception e){
            return -1.0;
        }
    }

    public static boolean getBoolean(HttpServletRequest request, String key){
        try{
            return Boolean.valueOf((request.getParameter(key)));
        }catch (Exception e){
            return false;
        }
    }

    /**
    * @author Zhining
    * @description cut off the double side space of a string
    * @param request,key
    * @return String
    * @create 2020/4/30 3:18 上午
    **/
    public static String getString(HttpServletRequest request, String key){
        try {
            String str = request.getParameter(key);
            if(str!=null){
                str = str.trim();
            }
            if(str.equals("")){
                str = null;
            }
            return str;
        }catch (Exception e){
            return null;
        }
    }
}
