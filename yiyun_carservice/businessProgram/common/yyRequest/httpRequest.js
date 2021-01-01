// const webmain = 'https://api.whyuntai.com/learun/adms/wechatdriver';
function fnc(baseUrl, url, data, method, header) {
	data = data || {}
	header = header || {
		// 'content-type': 'application/x-www-form-urlencoded'
		'content-type': 'application/json'
	}
	console.log(baseUrl + url , data)
	uni.showLoading();
	let promise = new Promise(function(resolve, reject) {
		uni.request({
			url: baseUrl + url,
			data: data,
			header: header,
			method: method,
			dataType: 'json',
			success: function(res) {
				if (typeof res.data === "object") {
					if (res.data.code && res.data.code == 200) {
						resolve(res.data);
					} else if(res.data.code == 401) {
						uni.redirectTo({
							url:'/pages/login'
						})
						res.data.msg = '登录失效，请重新登录'
						reject(res.data);
					} else{
							reject(res.data);
						}
				}
			},
			fail: function() {
				uni.showToast({
					title: '请求失败，请重试',
					icon:'none',
				})
			},
			complete: function() {
				uni.hideLoading();
				uni.stopPullDownRefresh();
			}
		})
	});
	return promise;
}

function request(baseUrl, url, data, method) {
	data = data || {}
	console.log(baseUrl + url , data)
	uni.showLoading();
	let promise = new Promise(function(resolve, reject) {
		uni.request({
			url: baseUrl + url,
			data: data,
			method: method,
			dataType: 'json',
			success: function(res) {
				if (typeof res.data === "object") {
					if (res.data.code && res.data.code == 200) {
						resolve(res.data);
					} else {
						reject(res.data);
					}
				}
			},
			fail: function() {
				uni.showToast({
					title: '请求失败，请重试',
					icon:'none',
				})
			},
			complete: function() {
				uni.hideLoading();
				uni.stopPullDownRefresh();
			}
		})
	});
	return promise;
}

module.exports = {
	'get': function(baseUrl, url, data, header) {
		return fnc(baseUrl, url, data, 'GET', header)
	},
	'post': function(baseUrl, url, data, header) {
		return fnc(baseUrl, url, data, 'POST', header)
	},
	'busGet': function(baseUrl, url, data) {
		return request(baseUrl, url, data, 'GET')
	},
	'busPost': function(baseUrl, url, data) {
		return request(baseUrl, url, data, 'POST')
	}
}
