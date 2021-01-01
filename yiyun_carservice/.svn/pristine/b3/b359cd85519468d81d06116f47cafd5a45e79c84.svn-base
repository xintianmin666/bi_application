<template>
	<view class="page">
	    <view class="page__settings">
	        <view class="cell">
	            <view class="ui-cell">
	                <view class="ui-cell-left">头像</view>
	                <view class="ui-cell-right uer-image">
	                    <open-data type="userAvatarUrl"></open-data>
	                </view>
	            </view>
	            <view class="ui-cell">
	                <view class="ui-cell-left">昵称</view>
	                <view class="ui-cell-right">
	                    <open-data type="userNickName"></open-data>
	                </view>
	            </view>
	            <view class="ui-cell" v-if="isbind">
	                <view class="ui-cell-left">手机号</view>
	                <view class="ui-cell-right">{{bindPrivacyMobileNo}}</view>
	            </view>
	        </view>
	        <view class="cell" v-if="isbind">
				<view @tap="changeMobile" class="ui-cell update_phone">
				    <view class="ui-cell-left">修改手机号</view>
				    <view class="ui-cell-arrow"></view>
				</view>
	            <view @tap="changePassword" class="ui-cell update_phone">
	                <view class="ui-cell-left">修改密码</view>
	                <view class="ui-cell-arrow"></view>
	            </view>
	            <view @tap="payPasssword" class="ui-cell cancel">
	                <view class="ui-cell-left">支付密码</view>
	                <view class="ui-cell-arrow"></view>
	            </view>
	        </view>
	    </view>
	    <view class="login_btn">
	        <!-- <button @tap="loginout" class="login_out" v-if="isbind">退出登录</button> -->
	        <button open-type="getUserInfo" @getuserinfo="login" class="login_in" v-if="!isbind">立即登录</button>
	    </view>
	</view>
</template>

<script>
	const app = getApp();
	const GD = app.globalData;
	export default {
		data() {
			return {
				isbind: false,
				bindPrivacyMobileNo: 18888888888
			}
		},
        onShow(){
            var that = this;
            const userInfo = uni.getStorageSync("storage_session") || '';
            that.$u.api.getUserInfo({
            	userId: userInfo.id
            }).then(res =>{
                console.log(res)
            	res.data.userPhone != null ? (that.isbind = true) : (that.isbind = false)
            }).catch(res =>{
            	this.$util.msg(res.message);
            })
            
        },
		onLoad(e) {
			console.log(e)
			this.isbind = e.isbind == 'false' ? false : true;
			this.bindPrivacyMobileNo = e.userphone
		},
		methods: {
			//跳转登录
			login(e){
				console.log(e);
				const getUserInfoCallback = ()=>{
					if(e.detail.errMsg == "getUserInfo:ok"){
						const UserInfo = e.detail.userInfo;
						GD.UserInfo = UserInfo;
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
                            this.$u.route("pages/login/login",{
                            	isGetUserinfo: "getUserInfo:ok"
                            })
						}).catch(res => {
							console.log(res)
						})
						
					}
				}
				this.$u.throttle(getUserInfoCallback,500);
			},
			//修改手机
			changeMobile(){
				this.$u.route("pages/my/changeInfo",{
					userId: 'userId',
					type: 1
				});
			},
			//修改密码
			changePassword(){
				this.$u.route("pages/my/changeInfo",{
					userId: 'userId',
					type: 2
				});
			},
			//支付密码
			payPasssword(){
				this.$u.route("pages/my/changeInfo",{
					userId: 'userId',
					type: 3
				});
			}
			
		}
	}
</script>

<style>
	.cell {
		margin: 10px 8px;
		border-radius: 6px;
		overflow: hidden;
		padding: 0px 15px;
		background-color: #fff;
		font-size: 14px;
	}
	.ui-cell {
		padding: 15px 0;
		position: relative;
		display: -webkit-flex;
		display: flex;
		-webkit-align-items: center;
		align-items: center;
		border-bottom: 1px solid #e5e5e5;
	}
	.ui-cell:last-child {
		border-bottom: none;
	}
	.uer-image {
		width: 35px;
		height: 35px;
		border-radius: 100%;
		overflow: hidden;
	}
	.ui-cell-arrow:before {
		content: ' ';
		width: 13px;
		height: 16px;
		display: inline-block;
		background-size: 100% 100%;
		position: absolute;
		left: 0px;
		top: 50%;
		margin-top: -8px;
	}
	.ui-cell-left {
		position: relative;
		-webkit-flex: 1;
		flex: 1;
	}
	.ui-cell-right {
		text-align: right;
		font-size: 14px;
		color: #999999;
	}
	.ui-cell-arrow:after {
		content: ' ';
		display: inline-block;
		height: 8px;
		width: 8px;
		border-width: 2px 2px 0 0;
		border-color: #c1c1c1;
		border-style: solid;
		-webkit-transform: matrix(0.71, 0.71, -0.71, 0.71, 0, 0);
		transform: matrix(0.71, 0.71, -0.71, 0.71, 0, 0);
		position: absolute;
		top: 50%;
		margin-top: -5px;
		right: 1px;
	}
	.login_btn {
		width: 100%;
		padding: 0 15px;
		position: absolute;
		bottom: 100rpx;
		left: 0;
		box-sizing: border-box;
	}
	.login_btn>.login_in {
		background: #fff;
		color: black;
	}
	.login_btn>.login_out {
		background: #fff;
		color: black;
	}
</style>
