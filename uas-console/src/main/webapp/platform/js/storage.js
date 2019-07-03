/**
 * 使用sessionStorage存储按钮权限，在每一个页面中调用，以设置哪些按钮显示或者不显示
 */

function clearStorage(){
	window.sessionStorage.clear();
}

function setItem(key,value){
	window.sessionStorage.setItem(key,value);
}

function getItem(key){
	return window.sessionStorage.getItem(key);
}

function removeItem(key){
	return window.sessionStorage.removeItem(key);
}