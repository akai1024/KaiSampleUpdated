package kai.sample.controller.msg;

public class BasicResponse {

    public static final int ERRORCODE_SUCCESS = 0;

    public static final int ERRORCODE_CREATE_USER_FAIL = 1000;

    private int errorCode = ERRORCODE_SUCCESS;
    private String msg;
    private Object data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
