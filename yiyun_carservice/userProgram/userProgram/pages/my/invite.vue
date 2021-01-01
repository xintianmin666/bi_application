<template>
	<view>
		<view class="content">
			<view class="title">输入邀请码</view>
			<input class="title_input user_mobile" type="number" @input="getInviteCode" placeholder="请输入邀请码" />
			<view class="inviteCode">当前邀请码：<text class="color1">{{inviteCode}}</text></view>
		</view>
		<view class="sub_change">
			<button @tap="sub_change" class="submit_in">提交</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				inviteCode:null
			}
		},
		onLoad(e) {
			var num = e.inviteCode;
			this.loadInviteCode();
		},
		methods: {
			loadInviteCode(){
				var that = this;
				this.$u.api.getUserInfo({
					userId: uni.getStorageSync('storage_session').id
				}).then(res => {
					console.log(res)
					this.inviteCode = res.data.inviteCode || "";
				}).catch(res => {
					console.log(res)
					uni.showModal({
						content: res.message
					});
				});
			},
			getInviteCode(e){
				this.inviteCode = e.detail.value;
			},
			sub_change(){
				var updateInfoCallback = ()=>{
					this.$u.api.updateUserInfo({
						userId: uni.getStorageSync('storage_session').id,
						inviteCode: this.inviteCode
					}).then(res => {
						console.log(res)
						uni.showModal({
							content: res.msg
						});
					}).catch(res => {
						console.log(res)
					})
				}
				this.$u.throttle(updateInfoCallback,500);
			}
		}
	}
</script>

<style>
	page{
		background-color: #FFFFFF;
	}
	.content{
		padding: 100rpx 60rpx 0;
		
	}
	.content>.title{
		font-size: 40rpx;
		color: #555555;
		height: 80rpx;
		line-height: 80rpx;
	}
	.inviteCode{
		font-size: 30rpx;
		color: #555555;
	}
	.color1{
		color: #E54D42;
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
</style>
