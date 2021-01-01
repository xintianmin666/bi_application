<template>
	<view>
		<!-- nav -->
		<u-navbar title-color="#fff" back-icon-color="#ffffff" :is-fixed="true" :is-back="true" back-text="" :background="{'background-image': 'linear-gradient(45deg, rgb(28, 187, 180), rgb(141, 198, 63))'}"
		 :back-text-style="{color: '#fff'}" title="宜运开发" />
		<view class="wrap">
			<!-- <view class="top"></view> -->
			<view class="content">
				<view class="title">欢迎使用ESGM</view>
				<u-input v-model="account" class="u-border-bottom margin-bottom-xs" type="text" :border="false" placeholder="请输入账号" />
				<u-input v-model="password" class="u-border-bottom margin-bottom-xs" type="password" :border="false" :password-icon="true" placeholder="请输入密码" />
				<view class="padding flex flex-direction margin-top">
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
		<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	const app = getApp();
	const GD = app.globalData;
	export default {
		data() {
			return {
				account: '',
				password:'',
			}
		},
		computed: {
			inputStyle() {
				let style = {};
				if (this.tel) {
					style.color = "#fff";
					style.backgroundColor = this.$u.color['success'];
				}
				return style;
			}
		},
		methods: {
			submit() {
				if (this.$u.test.mobile(this.tel)) {
					this.$u.route({
						url: 'pages/template/login/code'
					})
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
