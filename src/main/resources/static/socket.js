var userID = "888";
var websocket = null;

$(function () {
    $('#connect').click(function () {
        userID = $('#id').val();

        connectWebSocket();
    })
});

window.onunload = function () {
    closeWebSocket();
};

function connectWebSocket() {
    console.log("开始....");

    // 建立连接
    websocket = new WebSocket("ws://192.168.199.179:8080/post/chat/postId=" + userID);

    websocket.onopen = function () {
        console.log("open");
    };

    websocket.onclose = function () {
        console.log("onclose");
    };

    websocket.onmessage = function (msg) {
        console.log(msg.data);
        var info = JSON.parse(msg.data);
        console.log(info.success);
        console.log(info.message);
        if (info.success) {
        $('#message').append(info.data.userId + '说: ' + info.data.message + '<br>');

        }
    };
}

function send() {
    let postValue = {};
    postValue.id = userID;
    postValue.message = $("#text").val();
    websocket.send(JSON.stringify(postValue));
}

function closeWebSocket() {
    if (websocket !== null) {
        websocket.close();
    }
}
