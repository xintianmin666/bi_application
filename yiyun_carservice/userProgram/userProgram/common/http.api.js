// 如果没有通过拦截器配置域名的话，可以在这里写上完整的URL(加上域名部分)
let hotSearchUrl = '/ebapi/store_api/hot_search';
let indexUrl = '/ebapi/public_api/index';

// let baseUrl = "http://test.dtjklive.com/yiyuncarservice";
let baseUrl = "http://192.168.2.106:8001"
let WxloginUrl = "/user/wxlogin",
SendSmsUrl = "/user/sendSms",
LoginUrl = "/user/login",
updateUrl = "/user/updateUserInfo",
UserInfoUrl = "/user/getUserInfo",
PayOrderUrl = "/order/payOrder"
;

// 此处第二个参数vm，就是我们在页面使用的this，你可以通过vm获取vuex等操作，更多内容详见uView对拦截器的介绍部分：
// https://uviewui.com/js/http.html#%E4%BD%95%E8%B0%93%E8%AF%B7%E6%B1%82%E6%8B%A6%E6%88%AA%EF%BC%9F
const install = (Vue, vm) => {
	// 此处没有使用传入的params参数----示例
	let getSearch = (params = {}) => vm.$u.get(hotSearchUrl, {
		id: 2
	});
	// 此处使用了传入的params参数，一切自定义即可----示例
	let getInfo = (params = {}) => vm.$u.post(indexUrl, params);
	
	//微信登录授权
	let getUserWxlogin = (params = {}) => vm.$u.post(baseUrl+WxloginUrl, params);
	//获取验证码
	let getSendSms = (params = {}) => vm.$u.post(baseUrl+SendSmsUrl, params);
	//账号密码登录
	let getLogin = (params = {}) => vm.$u.post(baseUrl+LoginUrl, params);
	//更新用户信息
	let updateUserInfo = (params = {}) => vm.$u.post(baseUrl+updateUrl, params);
	//获取用户信息
	let getUserInfo = (params = {}) => vm.$u.post(baseUrl+UserInfoUrl, params);
	//调起支付回调
	let getPayOrder = (params = {}) => vm.$u.post(baseUrl+PayOrderUrl, params);
	
	// 将各个定义的接口名称，统一放进对象挂载到vm.$u.api(因为vm就是this，也即this.$u.api)下
	vm.$u.api = {getSearch, getInfo, getUserWxlogin, getSendSms, getLogin, updateUserInfo, getUserInfo, getPayOrder};
}

export default {
	install
}