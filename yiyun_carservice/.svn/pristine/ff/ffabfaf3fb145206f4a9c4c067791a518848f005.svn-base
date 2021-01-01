<script>
	import Vue from 'vue'
	import $http from '@/common/yyRequest/httpRequest.js'
	export default {
		$http,
		data() {
			return {
				isNotice: false,
			}
		},
		// 此处globalData为了演示其作用，不是uView框架的一部分
		globalData: {
			username: '易车行',
			webRoot: '',
			// 订单测试接口 本地
			testUrl: 'http://192.168.2.95:8890',  
			goodsUrl: 'http://192.168.2.106:8001', 
			webUrl: 'http://192.168.2.106:8001', 
			carserviceUrl: 'http://test.dtjklive.com/carservice',
		},
		onLaunch() {
			// 1.1.0版本之前关于http拦截器代码，已平滑移动到/common/http.interceptor.js中
			// 注意，需要在/main.js中实例化Vue之后引入如下(详见文档说明)：
			// import httpInterceptor from '@/common/http.interceptor.js'
			// Vue.use(httpInterceptor, app)
			// process.env.VUE_APP_PLATFORM 为通过js判断平台名称的方法，结果分别如下：
			/**
			 * h5，app-plus(nvue下也为app-plus)，mp-weixin，mp-alipay......
			 */
			var that = this
			// 小程序检测热更新升级
			const updateManager = uni.getUpdateManager();
			updateManager.onCheckForUpdate(function(res) {
				// 请求完新版本信息的回调
				if (res.hasUpdate) {
					updateManager.onUpdateReady(function(res2) {
						uni.showModal({
							title: '更新提示',
							content: '发现新版本，是否重启应用?',
							cancelColor: '#eeeeee',
							confirmColor: '#FF0000',
							success(res2) {
								if (res2.confirm) {
									// 新的版本已经下载好，调用 applyUpdate 应用新版本并重启
									updateManager.applyUpdate();
								}
							}
						});
					});
				}
			});
			updateManager.onUpdateFailed(function(res) {
				// 新的版本下载失败
				uni.showModal({
					title: '温馨提示',
					content: '检查到有新版本，但下载失败，请检查网络设置',
					success(res) {
						if (res.confirm) {
							// 新的版本已经下载好，调用 applyUpdate 应用新版本并重启
							updateManager.applyUpdate();
						}
					}
				});
			});
			uni.getSystemInfo({
				success: function(e) {
					// #ifndef MP
					Vue.prototype.StatusBar = e.statusBarHeight;
					if (e.platform == 'android') {
						Vue.prototype.CustomBar = e.statusBarHeight + 50;
					} else {
						Vue.prototype.CustomBar = e.statusBarHeight + 45;
					};
					// #endif

					// #ifdef MP-WEIXIN
					Vue.prototype.StatusBar = e.statusBarHeight;
					let custom = wx.getMenuButtonBoundingClientRect();
					Vue.prototype.Custom = custom;
					Vue.prototype.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
					// #endif		

					// #ifdef MP-ALIPAY
					Vue.prototype.StatusBar = e.statusBarHeight;
					Vue.prototype.CustomBar = e.statusBarHeight + e.titleBarHeight;
					// #endif
				}
			});
			// 检测环境
			const isDev = process.env.NODE_ENV === 'development'
			if (isDev) {
				console.log('当前为测试环境')
			}
			// 检测有无登录
			// if (!this.$store.state.user) {
			// 	uni.reLaunch({
			// 		url: './pages/home'
			// 	})
			// 	return
			// }
			//微信小程序登录
			// #ifdef MP-WEIXIN
			uni.login({
			  provider: 'weixin',
			  success: function (loginRes) {
			    console.log(loginRes);
				//授权获取地址位置信息
				uni.authorize({
				    scope: 'scope.userLocation',
				    success() {
				        uni.getLocation()
				    }
				})
				let mpWxCode = loginRes.code
				that.$u.api.getUserWxlogin({
					code: mpWxCode
				}).then(res => {
					console.log(res);
					uni.setStorageSync('storage_session', res.data);
				}).catch(res => {
					uni.showModal({
						title: '提示',
						content: '登录失效，请退出微信重新进入小程序',
						success(res) {
							
						}
					});
				})
			  }
			});
			
			// #endif
			
		},
		onShow: function () {
			console.log('App Show')
		},
		onHide: function () {
			console.log('App Hide')
		}
	}
</script>
<style>
	// 引入ColorUi
	@import "colorui/main.css";
	@import "colorui/icon.css";
	@import "colorui/animation.css";

	page {
		background-color: #ededed;

	}

	.nav-list {
		display: flex;
		flex-wrap: wrap;
		padding: 0px 40upx 0px;
		justify-content: space-between;
	}

	.nav-li {
		padding: 30upx;
		border-radius: 12upx;
		width: 45%;
		margin: 0 2.5% 40upx;
		background-image: url(https://cdn.nlark.com/yuque/0/2019/png/280374/1552996358352-assets/web-upload/cc3b1807-c684-4b83-8f80-80e5b8a6b975.png);
		background-size: cover;
		background-position: center;
		position: relative;
		z-index: 1;
	}

	.nav-li::after {
		content: "";
		position: absolute;
		z-index: -1;
		background-color: inherit;
		width: 100%;
		height: 100%;
		left: 0;
		bottom: -10%;
		border-radius: 10upx;
		opacity: 0.2;
		transform: scale(0.9, 0.9);
	}

	.nav-li.cur {
		color: #fff;
		background: rgb(94, 185, 94);
		box-shadow: 4upx 4upx 6upx rgba(94, 185, 94, 0.4);
	}

	.nav-title {
		font-size: 32upx;
		font-weight: 300;
	}

	.nav-title::first-letter {
		font-size: 40upx;
		margin-right: 4upx;
	}

	.nav-name {
		font-size: 28upx;
		text-transform: Capitalize;
		margin-top: 20upx;
		position: relative;
	}

	.nav-name::before {
		content: "";
		position: absolute;
		display: block;
		width: 40upx;
		height: 6upx;
		background: #fff;
		bottom: 0;
		right: 0;
		opacity: 0.5;
	}

	.nav-name::after {
		content: "";
		position: absolute;
		display: block;
		width: 100upx;
		height: 1px;
		background: #fff;
		bottom: 0;
		right: 40upx;
		opacity: 0.3;
	}

	.nav-name::first-letter {
		font-weight: bold;
		font-size: 36upx;
		margin-right: 1px;
	}

	.nav-li text {
		position: absolute;
		right: 30upx;
		top: 30upx;
		font-size: 52upx;
		width: 60upx;
		height: 60upx;
		text-align: center;
		line-height: 60upx;
	}

	.text-light {
		font-weight: 300;
	}

	@keyframes show {
		0% {
			transform: translateX(-50px);
		}

		60% {
			transform: translateX(40upx);
		}

		100% {
			transform: translateX(0px);
		}
	}

	@-webkit-keyframes show {
		0% {
			transform: translateX(-50px);
		}

		60% {
			transform: translateX(40upx);
		}

		100% {
			transform: translateX(0px);
		}
	}
</style>
<style lang="scss">
	@import "uview-ui/index.scss";
</style>
