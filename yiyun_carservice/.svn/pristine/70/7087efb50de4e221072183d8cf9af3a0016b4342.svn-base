<template>
	<view>
		<view class="wrap">
			<view class="content">
				<view class="title">欢迎使用·车服务</view>
				<u-input v-model="params.username" class="u-border-bottom margin-bottom-xs" type="text" :border="false" placeholder="请输入账号" />
				<u-input v-model="params.password" class="u-border-bottom margin-bottom-xs" type="password" :border="false" :password-icon="true" placeholder="请输入密码" />
				<view class="padding flex flex-direction margin-top"@tap="userLogin">
					<button class="cu-btn bg-green lg shadow">登录</button>
				</view>
				<view class="tips margin-top-xl text-gray text-sm">若遇到登陆问题，请与技术人员联系</view>
				
			</view>
			<view class="footer">
				<view class="hint">
					<text class="link">安徽宜运信息技术服务有限公司</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	const app = getApp();
	const GD = app.globalData;
	export default {
		data() {
			return {
				params:{
					username: '',
					password:'',
				}
			}
		},
		onLoad() {
				this.checkCache();
		},
		computed: {
			
		},
		methods: {
			// 登录
			userLogin() {
				let that = this;
				this.$http.post(GD.testURL,
					'/login', this.params).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						this.$store.commit('token', res.token)
						
						//  存 uni.setStorageSync('userInfo', userInfo);
						//  取 uni.getStorageSync('userInfo');
						
						// 缓存账号和密码
						this.getCache();
						// 车服务B端 集采C端
						uni.reLaunch({
							url:'./home'
						})
						// 集采B端 待做
					}
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
				});
			},
			// 查看是否有缓存账号和密码
			checkCache(){
			try {
				var loginInfo = uni.getStorageSync('loginData');
				if (loginInfo) {
					this.params.username = loginInfo.username
					this.params.password = loginInfo.password
				}
			} catch (e) {
				// error
			}
			},
			// 登录成功即做本地缓存 账号 密码 token
			getCache(){
				// 缓存账号 密码
				try {
					uni.setStorageSync('loginData', this.params);
				} catch (e) {
					// error
				}
			}
		}
	};
</script>
<style>
	.tips {
		color: $u-type-info;
		margin-bottom: 60rpx;
	}
</style>
<style lang="scss" scoped>
	.wrap {
		font-size: 28rpx;

		.content {
			width: 600rpx;
			margin: 0 auto 0;
			padding-top: 80rpx;

			.title {
				text-align: left;
				font-size: 60rpx;
				font-weight: 500;
				margin-bottom: 100rpx;
			}

			input {
				text-align: left;
				margin-bottom: 10rpx;
				padding-bottom: 6rpx;
			}

			

			.getCaptcha {
				background-color: rgb(219, 241, 225);
				color: $u-tips-color;
				border: none;
				font-size: 30rpx;
				padding: 12rpx 0;

				&::after {
					border: none;
				}
			}

			.alternative {
				color: $u-tips-color;
				display: flex;
				justify-content: space-between;
				margin-top: 30rpx;
			}
		}

		.buttom {
			.loginType {
				display: flex;
				padding: 350rpx 150rpx 150rpx 150rpx;
				justify-content: space-between;

				.item {
					display: flex;
					flex-direction: column;
					align-items: center;
					color: $u-content-color;
					font-size: 28rpx;
				}
			}

		}
	}

	.footer {
		position: fixed;
		left: 0;
		right: 0;
		bottom: 8vh;
		text-align: center;
		font-size: 20rpx;

		.link {
			color: $u-type-success;
		}
	}
</style>
