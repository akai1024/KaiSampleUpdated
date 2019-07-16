package kai.sample.controller.msg;

public enum ErrorCode {

    SUCCESS(0, "success"),
    UNKNOWN_ERROR(1, "unknown error"),
    ILLEGAL_PARAMETERS(2, "illegal parameters"),


    ILLEGAL_USER(1000, "illegal parameter: user"),
    ILLEGAL_PWD(1001, "illegal parameter: pwd"),
    USER_EXISTS(1002, "user already exists"),

    ;

    private int errNum;
    private String errMsg;

    private ErrorCode(int num, String msg) {
        errNum = num;
        errMsg = msg;
    }

    public int getErrNum() {
        return errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

}