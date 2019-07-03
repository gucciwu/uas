function alert_autoClose(title, msg, icon) {
	var interval;
	var time = 1000;
	var x = 5; // 设置时间5s
	$.messager.alert(title, msg, icon, function() {
	});
	interval = setInterval(fun, time);
	function fun() {
		--x;
		if (x == 0) {
			clearInterval(interval);
			$(".messager-body").window('close');
		}
	}
	;
}

/*密码复杂性度验证
 * 密码复杂要求：1、长度大于8; 2、密码必须是字母大写，字母小写，数字，特殊字符中任意三个组合。
 */
function checkPass(pass) {
	if (s.length < 8) {
		return 0;
	}
	var ls = 0;
	if (s.match(/([a-z])+/)) {
		ls++;
	}
	if (s.match(/([0-9])+/)) {
		ls++;
	}
	if (s.match(/([A-Z])+/)) {
		ls++;
	}
	if (s.match(/[^a-zA-Z0-9]+/)) {
		ls++;
	}
	return ls

}

var regex = new RegExp('(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}');