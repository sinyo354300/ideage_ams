package com.ideage.ams.common;
import org.springframework.util.StringUtils;

/**
 * @description リスポンス作成ツール
 * @author zhen.cheng
 */
public class ResultGenerator {


    public static Result getSuccessResult() {
        Result result = new Result();
        result.setResultCode(Constants.RESULT_CODE_SUCCESS);
        result.setMessage(MsgConstants.DEFAULT_MESSAGE_0001);
        return result;
    }

    public static Result getSuccessResult(String message) {
        Result result = new Result();
        result.setResultCode(Constants.RESULT_CODE_SUCCESS);
        result.setMessage(message);
        return result;
    }

    public static Result getSuccessResult(Object data) {
        Result result = new Result();
        result.setResultCode(Constants.RESULT_CODE_SUCCESS);
        result.setMessage(MsgConstants.DEFAULT_MESSAGE_0001);
        result.setData(data);
        return result;
    }

    public static Result getFailResult(String message) {
        Result result = new Result();
        result.setResultCode(Constants.RESULT_CODE_SERVER_ERROR);
        if (StringUtils.isEmpty(message)) {
            result.setMessage(MsgConstants.DEFAULT_MESSAGE_0002);
        } else {
            result.setMessage(message);
        }
        return result;
    }

    public static Result getNullResult(String message) {
        Result result = new Result();
        result.setResultCode(Constants.RESULT_CODE_BAD_REQUEST);
        result.setMessage(message);
        return result;
    }

    public static Result getErrorResult(int code, String message) {
        Result result = new Result();
        result.setResultCode(code);
        result.setMessage(message);
        return result;
    }
}
