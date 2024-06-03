var editorD;

$(function () {
    //エラー表示欄の隠す
    $('.alert-danger').css("display", "none");

    //テキストエディター
    const E = window.wangEditor;
    editorD = new E('#wangEditor')
    // エディターパラメータの設定
    editorD.config.height = 400
    editorD.config.uploadImgServer = 'images/upload'
    editorD.config.uploadFileName = 'file'
    editorD.config.uploadImgMaxSize = 2 * 1024 * 1024
    editorD.config.uploadImgMaxLength = 1
    editorD.config.showLinkImg = false
    editorD.config.uploadImgHooks = {
        // アップロード成功
        success: function (xhr) {
            console.log('success', xhr)
        },
        // アップロード失敗
        fail: function (xhr, editor, resData) {
            console.log('fail', resData)
        },
        // アップロードエラー
        error: function (xhr, editor, resData) {
            console.log('error', xhr, resData)
        },
        // タイムアウト処理
        timeout: function (xhr) {
            console.log('timeout')
        },
        customInsert: function (insertImgFn, result) {
            if (result != null && result.resultCode == 200) {
                insertImgFn(result.data)
            } else {
                alert("error");
            }
        }
    }
    editorD.create();

    $('#attendanceModal').modal('hide');

    $("#jqGrid").jqGrid({
        url: 'attendancelist/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: 'タイトル', name: 'attendanceTitle', index: 'articleTitle', width: 240},
            {label: '作成者', name: 'addName', index: 'addName', width: 120},
            {label: '登録時間', name: 'createTime', index: 'createTime', width: 120},
            {label: '更新時間', name: 'updateTime', index: 'updateTime', width: 120}
        ],
        height: 560,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '取込中。。。。',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
});

$('#saveButton').click(function () {
    //バリエーション
    if (validObject()) {
        //リクエスト送信
        var id = $("#attendanceId").val();
        var title = $("#attendanceName").val();
        var addName = $("#attendanceAuthor").val();
        var content = editorD.txt.html();
        var data = {"articleTitle": title, "articleContent": content, "addName": addName};
        var url = 'attendancelist/save';
        var method = 'POST';
        if (id > 0) {
            data = {"id": id, "articleTitle": title, "articleContent": content, "addName": addName};
            url = 'attendancelist/update';
            method = 'PUT';
        }
        $.ajax({
            type: method,
            dataType: "json",
            url: url,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("token", getCookie("token"));
            },
            data: JSON.stringify(data),
            success: function (result) {
                checkResultCode(result.resultCode);
                if (result.resultCode == 200) {
                    $('#attendanceModal').modal('hide');
                    swal("保存成功", {
                        icon: "success",
                    });
                    reload();
                }
                else {
                    $('#attendanceModal').modal('hide');
                    swal("保存失敗", {
                        icon: "error",
                    });
                }
                ;
            },
            error: function () {
                swal("保存失敗", {
                    icon: "error",
                });
            }
        });

    }
});

function attendanceAdd() {
    reset();
    $('.modal-title').html('追加');
    $('#attendanceModal').modal('show');
}

function attendanceEdit() {
    reset();
    $('.modal-title').html('編集');

    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    //情報取得
    $.get("attendancelist/info/" + id, function (r) {
        if (r.resultCode == 200 && r.data != null) {
            $('#attendanceId').val(r.data.id);
            $('#attendanceName').val(r.data.articleTitle);
            $('#attendanceAuthor').val(r.data.addName);
            editorD.txt.html(r.data.articleContent);
        }
    });
    $('#attendanceModal').modal('show');
}


function validObject() {
    var attendanceName = $('#attendanceName').val();
    if (isNull(attendanceName)) {
        showErrorInfo("タイトルを入力してください。");
        return false;
    }
    if (!validLength(attendanceName, 120)) {
        showErrorInfo("タイトルは120文字制限があります。");
        return false;
    }
    var attendanceAuthor = $('#attendanceAuthor').val();
    if (isNull(attendanceAuthor)) {
        showErrorInfo("作成者を入力してください。");
        return false;
    }
    if (!validLength(attendanceAuthor, 10)) {
        showErrorInfo("作成者は10文字制限があります。");
        return false;
    }
    var ariticleContent = editorD.txt.html();
    if (isNull(ariticleContent) || ariticleContent == '入力してください...') {
        showErrorInfo("内容を入力してください。");
        return false;
    }
    if (!validLength(ariticleContent, 8000)) {
        showErrorInfo("内容は8000文字制限があります。");
        return false;
    }
    return true;
}


function reset() {
    $('.alert-danger').css("display", "none");
    $('#attendanceId').val(0);
    $('#attendanceName').val('');
    $('#attendanceAuthor').val('');
    editorD.txt.html('');
}

function deleteArticle() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "確認ダイアログ",
        text: "データを削除するか",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.ajax({
                type: "DELETE",
                url: "attendancelist/delete",
                contentType: "application/json",
                beforeSend: function (request) {
                    request.setRequestHeader("token", getCookie("token"));
                },
                data: JSON.stringify(ids),
                success: function (r) {
                    checkResultCode(r.resultCode);
                    if (r.resultCode == 200) {
                        swal("処理成功", {
                            icon: "success",
                        });
                        $("#jqGrid").trigger("reloadGrid");
                    } else {
                        swal(r.message, {
                            icon: "error",
                        });
                    }
                }
            });
        }
    });
}


function reload() {
    reset();
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}