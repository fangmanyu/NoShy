// 默认服务器地址
//var host = '10.0.0.49:8080';
var host = '192.168.199.179:8080';
//var host = '127.0.0.1:8080';
// var host = '10.10.15.2:8080';

// var baseUrl = 'http://127.0.0.1:8080';
// var baseUrl = 'http://10.10.15.2:8080';
//var baseUrl = 'http://192.168.199.179:8080';
var baseUrl = 'http://' + host;
// 默认socket连接地址
// var baseSocketUrl = 'ws://127.0.0.1:8080';
// var baseSocketUrl = 'ws://10.10.15.2:8080';
//var baseSocketUrl = 'ws://192.168.199.179:8080';
var baseSocketUrl = 'ws://' + host;
// 默认图片地址
var defaultImg = '../../img/defaultPostImage.jpg';
// 默认视频播放地址
var defaultVideo = 'http://video.stxkfzx.xin/962dbcf0eb424cbe93d62f8da5ffec80/1986bd101ac746e6bf94b6c6a0b2f42a-ed004a683f748c663cab37d554dba83a-ld.m3u8';
// 默认视频地址
var baseVideoUrl = 'http://video.stxkfzx.xin';





// 更换验证码
function changeVerifyCode(img) {
	img.src = baseUrl + "/kaptcha?" + Math.floor(Math.random() * 1000);
}

// 获取参数值
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) {
		return decodeURIComponent(r[2]);
	}
	return "";
}

// 时间格式转换
Date.prototype.Format = function(fmt) {
	var o = {
		"M+": this.getMonth() + 1, // 月份
		"d+": this.getDate(), // 日
		"h+": this.getHours(), // 小时
		"m+": this.getMinutes(), // 分
		"s+": this.getSeconds(), // 秒
		"q+": Math.floor((this.getMonth() + 3) / 3), // 季度
		"S": this.getMilliseconds() // 毫秒
	};

	if(/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}

	for(var k in o) {
		if(new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}

	return fmt;

};

// 将null值转换为空字符串
function nullToEmpty(data) {
	return data === null ? '' : data;
}


function defaultImage(img) {
	return img == null ? defaultImg : img;
}

function defaultNetImage (img) {
	return img == null ? defaultImg : (baseUrl + img);
}


document.addEventListener('plusready', function() {
    var webview = plus.webview.currentWebview();
    plus.key.addEventListener('backbutton', function() {
        webview.canBack(function(e) {
            if(e.canBack) {
                webview.back();
            } else {
                //webview.close(); //hide,quit
                //plus.runtime.quit();
                //首页返回键处理
                //处理逻辑：1秒内，连续两次按返回键，则退出应用；
                var first = null;
                plus.key.addEventListener('backbutton', function() {
                    //首次按键，提示‘再按一次退出应用’
                    if (!first) {
                        first = new Date().getTime();
                        console.log('再按一次退出应用');
                        setTimeout(function() {
                            first = null;
                        }, 1000);
                    } else {
                        if (new Date().getTime() - first < 1500) {
//                      	window.localStorage.clear();
                            plus.runtime.quit();
                        }
                    }n
                }, false);
            }
        })
    });
});
