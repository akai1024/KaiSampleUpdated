package kai.sample.controller.msg;

public class ResponseBuilder {

    public static BasicResponse newResponse(ErrorCode errorCode) {
        return newResponse(errorCode, null);
    }

    public static BasicResponse newResponse(ErrorCode errorCode, Object data) {
        if (errorCode == null) {
            errorCode = ErrorCode.SUCCESS;
        }
        BasicResponse response = new BasicResponse();
        response.setErrorCode(errorCode.getErrNum());
        response.setMsg(errorCode.getErrMsg());
        response.setData(data);
        return response;
    }

}
