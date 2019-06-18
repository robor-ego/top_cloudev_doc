package top.cloudev.doc.common;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 国际化工具
 * Created by Mac.Manon on 2018/04/04
 */
public class I18nUtils {
    /**
     * 根据key获得基于客户端语言的本地化信息
     * @param key
     * @return
     */
    public static String getMessage(String key){
        return getMessage(key,Locale.getDefault());
    }

    /**
     * 根据key获得基于request指定的Locale的本地化信息
     * @param key
     * @param request
     * @return
     */
    public static String getMessage(String key, HttpServletRequest request){
        if(request.getLocale() != null)
            return getMessage(key,request.getLocale());
        else
            return getMessage(key);
    }

    /**
     * 根据key和指定的locale获得本地化信息
     * @param key
     * @param locale
     * @return
     */
    public static String getMessage(String key, Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n/messages",locale);
        return resourceBundle.getString(key);
    }
}
