package com.ideage.ams.common;

/**
 * @description 共通定数クラス
 * @author zhen.cheng
 */
public class Constants {

    public static final int RESULT_CODE_SUCCESS = 200;  // リクエスト成功
    public static final int RESULT_CODE_BAD_REQUEST = 412;  // バッドリクエスト
    public static final int RESULT_CODE_NOT_LOGIN = 402;  // 未ログイン
    public static final int RESULT_CODE_PARAM_ERROR = 406;  // パラメーターエラー
    public static final int RESULT_CODE_SERVER_ERROR = 500;  // サーバーエラー

    public final static String FILE_UPLOAD_PATH = "/home/project/upload/";//アップロードパス

}