package pr.jason.myuipratice.util;

/**
 * Created by Jaesin on 2015-02-28.
 */
public class Utils {
    public static final char LF = '\n';
    public static final char CR = '\r';

    public static String deleteLastCharacter(String str){
        if (str == null) {
            return null;
        }
        int strLen = str.length();
        if (strLen < 2) {
            return "";
        }
        int lastIdx = strLen - 1;
        String ret = str.substring(0, lastIdx);
        char last = str.charAt(lastIdx);
        if (last == LF) {
            if (ret.charAt(lastIdx - 1) == CR) {
                return ret.substring(0, lastIdx - 1);
            }
        }
        return ret;
    }
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
