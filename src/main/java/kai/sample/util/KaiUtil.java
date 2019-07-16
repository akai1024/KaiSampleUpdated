package kai.sample.util;

public class KaiUtil {

    public static boolean isEmptyStr(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean strEquals(String str1, String str2) {
        return !isEmptyStr(str1) && !isEmptyStr(str2) && str1.equals(str2);
    }

    public static boolean isAlphanumericCharactersIncludeCapital(String str) {
        return !isEmptyStr(str) && str.matches("^[a-zA-Z0-9]*$");
    }

    public static boolean isAlphanumericCharacters(String str) {
        return !isEmptyStr(str) && str.matches("^[a-z0-9]*$");
    }

}
