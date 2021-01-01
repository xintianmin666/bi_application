<template>
	<view>

		<view class="wrap">
			<view class="top"></view>
			<view class="content">
				<view class="title">欢迎进入用车无忧</view>
				<input class="user_mobile" type="number" maxlength="11" v-model="tel" placeholder="请输入手机号" />
				<view class="password" v-if="ispasswordLogin">
					<u-input class="password_in" type="password" :password-icon="true" @input="getInputPassword" placeholder="请输入密码" />
				</view>
				<view class="vcode" v-else>
					<input class="vcode_num" type="number" @input="getInputVcode" placeholder="请输入验证码" />
					<button @tap="getCheckNum" :style="[inputStyle]" class="getCaptcha">{{!codeTime?'获取短信验证码':codeTime+'s'}}</button>
				</view>
				
				<view class="alternative">
					<view class="password_btn" @tap="changeLogin" data-type="vcode" v-if="ispasswordLogin">验证码登录</view>
					<view class="password_btn" @tap="changeLogin" data-type="password" v-else>密码登录</view>
					<view class="issue" @tap="callService">联系客服</view>
				</view>
			</view>
			<view class="register_btn">
				<button class="register_in" @tap="login" v-if="this.isGetUserinfo=='getUserInfo:ok'">立即登录</button>
                <button class="register_in" open-type="getUserInfo" @getuserinfo="mpWxGetUserInfo" v-else>立即登录</button>
				<view class="tips">未注册的手机号登录后自动创建账户，使用短信验证码登录的用户初始密码为<text style="color: #ED1C24;">123456</text></view>
			</view>
			<view class="footer">
				<view class="hint">
					<text class="link">安徽宜运信息技术服务有限公司</text>
				</view>
			</view>
		</view>
		<!-- <u-popup v-model="isLoginModel" mode="center" width="600rpx" height="300rpx" border-radius="14" :closeable="true">
			<view class="pop_area">
				<text class="pop_title">用车无忧需要您进行授权</text>
				<button class="authorize_btn" open-type="getUserInfo" @getuserinfo="mpWxGetUserInfo">授权</button>
			</view>
		</u-popup>
		<u-toast ref="uToast"></u-toast> -->
	</view>
</template>

<script>
	import {checkStr} from '@/common/util';
	const app = getApp();
	const GD = app.globalData;
	export default {
		data() {
			return {
				tel: '',
				codeTime: 0,
				ispasswordLogin: !0,
				password: '',
				vCode: '',
				isLoginModel: false,
				isGetUserinfo:''
			}
		},
		onLoad(e) {
			console.log(e);
			const that= this, userInfo = uni.getStorageSync("storage_session") || '';
			
			// uni.getSetting({
			//    success: (res) => {
			// 	   console.log(res);
			// 		if(!res.authSetting['scope.userInfo']){
			// 			that.isLoginModel = true;
			// 		}else{
			// 			that.isGetUserinfo = 'getUserInfo:ok';
			// 		}
			//    }
			// })
			
		},
		computed: {
			inputStyle() {
				let style = {};
				if ((/^1[3456789]\d{9}$/.test(this.tel))) {
					style.color = "#fff";
					style.backgroundColor = this.$u.color['primary'];
				}
				return style;
			}
		},
		methods: {
			// 拨打客服电话
			callService(){
				uni.makePhoneCall({
				    phoneNumber: '0553-3911111' //
				});
			},
			//获取验证码
			getCheckNum(){
				var t = this;
				if((/^1[3456789]\d{9}$/.test(this.tel))){
					if(this.codeTime>0){
						uni.showToast({
							title: '不能重复获取',
							icon:"none"
						});
						return;
					}else{
						t.inputStyle.color = "#fff";
						t.inputStyle.backgroundColor = "#a0cfff";
						this.codeTime = 60;
						t.getVcode(t.tel);
						let timer = setInterval(()=>{
							this.codeTime--;
							if(this.codeTime<1){
								clearInterval(timer);
								this.codeTime = 0;
								t.inputStyle.color = "#fff";
								t.inputStyle.backgroundColor = this.$u.color['primary'];
							}
						},1000)
					}
				}else{
					uni.showToast({
						title: '请输入手机号',
						icon:"none"
					});
				}
			},
			//密码登录、验证码登录切换
			changeLogin(e){
				let type = e.target.dataset.type;
				var t = this;
				const switchLoginCallback = ()=>{
					if(type == 'password'){
						t.ispasswordLogin = 1;
						t.vCode = '';
					}else{
						t.ispasswordLogin = !1;
						t.password = '';
					}
				}
				this.$u.throttle(switchLoginCallback,500)
			},
			//获取验证码接口请求
			getVcode(e){
				this.$u.api.getSendSms({
					userPhone: e
				}).then(res => {
					console.log(res)
					uni.showToast({
						title: res.message,
						icon:"none",
						duration: 2000
					});
				}).catch(res => {
					uni.showToast({
						title: res.message,
						icon:"none",
						duration: 2000
					});
				})
			},
			//监控密码输入框
			getInputPassword(e){
				console.log(e)
				this.password = e;
			},
			//监控验证码输入框
			getInputVcode(e){
				console.log(e)
				this.vCode = e;
			},
			//点击授权获取用户信息
			mpWxGetUserInfo(e){
				console.log(e)
				this.isGetUserinfo = e.detail.errMsg;
				var that = this;
				const getUserInfoCallback = ()=>{
					if(e.detail.errMsg == "getUserInfo:ok"){
						const UserInfo = e.detail.userInfo;
						GD.UserInfo = UserInfo;
						this.isLoginModel = false;
						const  _userInfo = uni.getStorageSync('storage_session');
						_userInfo.nickName = UserInfo.nickName;
						_userInfo.headImg = UserInfo.avatarUrl;
						uni.setStorageSync('storage_session', _userInfo);
						this.$u.api.updateUserInfo({
							userId: uni.getStorageSync('storage_session').id,
							nickName: UserInfo.nickName,
							headImg: UserInfo.avatarUrl
						}).then(res => {
							console.log(res)
                            that.login();
							// if(that.tel) that.login();
						}).catch(res => {
							console.log(res)
							this.$util.msg(res.message);
						})
					}
				}
				this.$u.throttle(getUserInfoCallback,500);
			},
			//账号登录
			login(e){
				
				const that = this, userphone = this.tel, vcode = this.vCode, password = this.password;
                if(!checkStr(userphone, 'mobile')){
                	this.$util.msg('请输入正确的手机号码');
                	return;
                }
                if(this.ispasswordLogin){
                	if(password != ''){
                		this.$u.api.getLogin({
                			loginType: 2,
                			userId: uni.getStorageSync('storage_session').id,
                			userPhone: userphone,
                			password: password
                		}).then(res => {
                			console.log(res)
                			uni.setStorageSync('storage_session', res.data);
                            GD.points = res.data.points;
                			uni.navigateBack({
                			    delta: 1
                			});
                		}).catch(res => {
                			console.log(res)
                			uni.showModal({
                				content: res.message
                			});
                		})
                	}else{
                		uni.showModal({
                			content: '请输入密码'
                		});
                	}
                }else{
                	if(vcode != ''){
                		this.$u.api.getLogin({
                			loginType: 1,
                			userId: uni.getStorageSync('storage_session').id,
                			userPhone: userphone,
                			code: vcode
                		}).then(res => {
                			console.log(res)
                			uni.setStorageSync('storage_session', res.data);
                            GD.points = res.data.points;
                			uni.navigateBack({
                			    delta: 1
                			});
                		}).catch(res => {
                			console.log(res)
                			uni.showModal({
                				content: res.messages
                			});
                		})
                	}else{
                		uni.showModal({
                			content: '请输入验证码'
                		});
                	}
                }
				// if(this.isGetUserinfo == 'getUserInfo:ok'){
					
				// }else{
				// 	this.isLoginModel = true;
				// }
				
				
			}
		}
	};
</script>

<style>
	page{
		background-color: #FFFFFF;
	}
	.wrap {
		font-size: 28rpx;
	}
	.content {
		width: 600rpx;
		margin: 0 auto 0;
		padding-top: 80rpx;
	}
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
		
	
	.user_mobile{
		border-bottom: 1px solid #C1C1C1;
		height: 84rpx;
		line-height: 84rpx;
	}
	.password{
		margin-top: 20rpx;
	}
	.password_in{
		border-bottom: 1px solid #C1C1C1;
		height: 84rpx;
		line-height: 84rpx;
	}
	.vcode{
		margin-top: 20rpx;
		display: flex;
		flex-wrap: nowrap;
		justify-content: space-between;
	}
	.vcode_num{
		line-height: 84rpx;
		height: 84rpx;
		padding: 12rpx 0;
		margin: 0;
	}
	.getCaptcha {
		background-color: #a0cfff;
		color: #fff;
		border: none;
		font-size: 24rpx;
		padding: 12rpx 16rpx;
		width: 200rpx;
		margin: 0;
		
	}
		
	.alternative {
		color: #888888;
		display: flex;
		justify-content: space-between;
		margin-top: 30rpx;
	}
	.register_btn{
		width: 100%;
		padding: 0 15px;
		position: absolute;
		bottom: 180rpx;
		left: 0;
		box-sizing: border-box;
	}
	.register_btn>.register_in{
		background-color: #2979ff ;
		color: #fff;
	}
	.tips {
			color: #C1C1C1;
			margin-bottom: 40rpx;
			margin-top: 8rpx;
			font-size: 24rpx;
	}
	.buttom {
		
	}
	
	.footer {
		position: fixed;
		left: 0;
		right: 0;
		bottom: 8vh;
		text-align: center;
		font-size: 24rpx;

		
	}
	.link {
		color: #0081FF;
	}
	.pop_area{
		padding: 40rpx 60rpx;
	}
	.pop_title{
		display: block;
		margin-top: 40rpx;
		font-size: 32rpx;
		color: #333;
		text-align: center;
	}
	.authorize_btn{
		background-color: #0081FF;
		margin-top: 40rpx;
		color: #fff;
	}
</style>
