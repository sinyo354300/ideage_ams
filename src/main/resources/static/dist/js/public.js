/**
 * @param obj
 * @returns {boolean}
 */
function isNull(obj) {
    if (obj == null || obj == undefined || obj.trim() == "") {
        return true;
    }
    return false;
}

/**
 *
 * @param obj
 * @param length
 * @returns {boolean}
 */
function validLength(obj, length) {
    if (obj.trim().length < length) {
        return true;
    }
    return false;
}

/**
 * ユーザー名の検証
 *
 * @param userName
 * @returns {boolean}
 */
function validUserName(userName) {
    var pattern = /^[a-zA-Z0-9_-]{4,16}$/;
    if (pattern.test(userName.trim())) {
        return (true);
    } else {
        return (false);
    }
}

/**
 * パスワードの検証
 *
 * @param password
 * @returns {boolean}
 */
function validPassword(password) {
    var pattern = /^[a-zA-Z0-9]{6,20}$/;
    if (pattern.test(password.trim())) {
        return (true);
    } else {
        return (false);
    }
}

function login() {
    var userName = $("#userName").val();
    var password = $("#password").val();
    if (isNull(userName)) {
        showErrorInfo("ユーザー名を入力してください。");
        return;
    }
    if (!validUserName(userName)) {
        showErrorInfo("入力したユーザー名は正しくない。");
        return;
    }
    if (isNull(password)) {
        showErrorInfo("パスワードを入力してください。");
        return;
    }
    if (!validPassword(password)) {
        showErrorInfo("入力したパスワードは正しくない");
        return;
    }
    var data = {"userName": userName, "password": password}
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "users/login",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                $('.alert-danger').css("display", "none");
                setCookie("token", result.data.userToken);
                window.location.href = "/";
            }
            ;
            if (result.resultCode == 500) {
                showErrorInfo("ログイン失敗！");
                return;
            }
        },
        error: function () {
            $('.alert-danger').css("display", "none");
            showErrorInfo("異常エラーのため、管理者に問い合わせしてください。");
            return;
        }
    });
}


/**
 * クッキーの作成
 *
 * @param name
 * @param value
 */
function setCookie(name, value) {
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString() + ";path=/";

}

/**
 * クッキーの取込
 * @param name
 * @returns {null}
 */
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}

/**
 * クッキーの削除
 * @param name
 */
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null)
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}

/**
 * クッキーの検証
 */
function checkCookie() {
    if (getCookie("token") == null) {
        swal("未ログイン", {
            icon: "error",
        });
        window.location.href = "login.html";
    }
}

/**
 * クッキーの検証
 */
function checkResultCode(code) {
    if (code == 402) {
        window.location.href = "login.html";
    }
}


function showErrorInfo(info) {
    $('.alert-danger').css("display", "block");
    $('.alert-danger').html(info);
}


/**
 * レコードの選択
 * @returns {*}
 */
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        swal("1件明細を選択してください。", {
            icon: "error",
        });
        return;
    }
    var selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        swal("1件明細しか選択できない。", {
            icon: "error",
        });
        return;
    }
    return selectedIDs[0];
}

/**
 * 複数レコードの選択
 * @returns {*}
 */
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        swal("1件明細を選択してください。", {
            icon: "error",
        });
        return;
    }
    return grid.getGridParam("selarrrow");
}