$(function() {
	$(document).on('pageInit', '#login', function(e, id, page) {

		$('#captcha_img').attr('src', baseUrl + '/kaptcha')

		$('#submit').on('click', function(e) {

			var phone = $('#phone').val();
			if(!phone) {
				$.toast('请输入电话');
				return;
			}

			var password = $('#password').val();
			if(!password) {
				$.toast('请输入密码');
				return;
			}
			var verifyCodeActual = $('#j_captcha').val();
			if(!verifyCodeActual) {
				$.toast('请输入验证码');
				return;
			}
			console.log('phone:' + phone);
			console.log('password:' + password);
			console.log('verifyCodeActual:' + verifyCodeActual);

			var loginInfo = {};
			loginInfo.phone = phone;
			loginInfo.password = password;
			loginInfo.verifyCodeActual = verifyCodeActual;

			$.ajax({
				type: "post",
				url: baseUrl + "/login",
				async: true,
				contentType: 'application/json',
				// cache: false,
				data: JSON.stringify(loginInfo),
				success: function(data) {
					if(data.success) {
						$.toast('提交成功!');
						window.location.href = './mainPage/index.html';
					} else {
						$.toast('提交失败! ' + data.message);
					}
					$('#captcha_img').click();
					$('#j_captcha').val('');
				},
				beforeSend: function (xhr) {
					xhr.withCredentials = true
				}
			});
		});
	});

	$.init();
});