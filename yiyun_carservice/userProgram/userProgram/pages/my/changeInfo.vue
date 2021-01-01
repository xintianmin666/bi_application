<template>
	<view class="page">
		<view class="content" v-if="type==1">
			<view class="title">修改手机号</view>
			<input class="title_input user_mobile" type="text" @input="getInputPhone" placeholder="请输入新手机号"  />
		</view>
		<view class="content" v-if="type==2">
			<view class="title">修改密码</view>
			<u-input class="title_input user_password" type="password" @input="getInputPassword" :password-icon="true" placeholder="请输入新密码" />
			<view class="line-gray"></view>
		</view>
		<view class="content" v-if="type==3">
			<view class="title">支付密码</view>
			<u-input class="title_input user_password" type="password" @input="getInputPaypassword" :password-icon="true" placeholder="请输入支付密码" />
			<view class="line-gray"></view>
		</view>
		<view class="sub_change">
			<button @tap="sub_change" class="submit_in">提交修改</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				type: null,
				phone:'',
				password:'',
				payPassword:''
			}
		},
		onLoad(e) {
			this.type = e.type;
		},
		methods: {
			//监控手机号输入框
			getInputPhone(e){
				console.log(e)
				this.phone = e.detail.value;
			},
			//监控密码输入框
			getInputPassword(e){
				this.password = e;
				console.log(this.password)
			},
			//监控支付密码输入框
			getInputPaypassword(e){
				this.payPassword = e;
			},
			sub_change(){
				var type = this.type, that = this;
				console.log(type)
				switch(type){
					case "1":
						that.phone == '' ? that.$util.msg('输入内容不能为空') : that.updateInfo({
							userId: uni.getStorageSync('storage_session').id,
							userPhone: that.phone
						});
						break;
					case "2":
						that.password == '' ? that.$util.msg('输入内容不能为空') : that.updateInfo({
							userId: uni.getStorageSync('storage_session').id,
							password: that.password
						});
						break;
					case "3":
						that.payPassword == '' ? that.$util.msg('输入内容不能为空') : that.updateInfo({
							userId: uni.getStorageSync('storage_session').id,
							payPassword: that.payPassword
						});
						break;
				}
			},
			//更新信息请求接口
			updateInfo(e){
				var that = this;
				var updateInfoCallback = ()=>{
					this.$u.api.updateUserInfo(e).then(res => {
						console.log(res)
						uni.showModal({
							content: res.msg
						});
						const  _userInfo = uni.getStorageSync('storage_session');
						that.updateStroge(_userInfo);
					}).catch(res => {
						console.log(res)
					})
				}
				this.$u.throttle(updateInfoCallback,500);
			},
			//更新本地缓存
			updateStroge(e){
				var type = this.type;
				switch(type){
					case "1":
						e.userPhone = this.phone
						uni.setStorageSync('storage_session', e);
						uni.navigateBack({
						    delta: 1
						});
						break;
					case "2":
						e.password = this.password
						uni.setStorageSync('storage_session', e);
						uni.navigateBack({
						    delta: 1
						});
						break;
					case "3":
						e.payPassword = this.payPassword
						uni.setStorageSync('storage_session', e);
						uni.navigateBack({
						    delta: 1
						});
						break;
				}
			}
		}
	}
</script>

<style>
	.content{
		padding: 100rpx 60rpx;
		background-color: #fff;
	}
	.content>.title{
		font-size: 40rpx;
		color: #555555;
		height: 80rpx;
		line-height: 80rpx;
	}
	.title_input{
		border-bottom: 1px solid #C1C1C1;
		height: 84rpx;
		line-height: 84rpx;
		text-align: left;
		margin-bottom: 10rpx;
		padding-bottom: 6rpx;
	}
	.sub_change{
		width: 100%;
		padding: 0 15px;
		position: absolute;
		bottom: 100rpx;
		left: 0;
		box-sizing: border-box;
	}
	.sub_change>.submit_in{
		background: #fff;
		color: black;
	}
	.line-gray{
		background-color: #aaa;
		height: 1px;
	}
</style>
