<template>
	<view>
	
		<view class="flex user-box padding-lr-lg padding-bottom-lg padding-top">
			<view class="u-m-r-10">
				<image class="headimg" :src="headImg"></image>
			</view>
			<view class="u-flex-1 padding-left">
				<view class="u-font-18 u-p-b-20">{{userName}}</view>
				<view class="u-font-14 u-type-warning">积分：{{points}}</view>
			</view>
			<view class="u-m-l-10 u-p-10">
				<u-icon name="setting" color="#969799" size="44" @tap="goSetting"></u-icon>
			</view>
		</view>
		
		<view class="margin-top-sm padding-sm bg-white flex justify-around text-center">
			<view class="text-center" v-for="(item,index) in iconList" :key="index" :style="[{animation: 'show ' + ((index+1)*0.2+1) + 's 1'}]" >
				<image :src="item.url" class="img_icon margin-bottom-xs" mode="aspectFill"></image>
				<view class="text-lg">{{item.title}}</view>
			</view>
		</view>
		
		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item icon="car" title="车牌号" @tap="carNum"></u-cell-item>
			</u-cell-group>
		</view>

		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item icon="man-add" title="受邀码" @tap="invite"></u-cell-item>
			</u-cell-group>
		</view>


		<view class="footer text-center">
			<text class="text-blue" @tap="callService">客服电话：0553-3911111</text>
			<view class="text-gray text-sm">安徽宜运信息技术服务有限公司提供技术支持</view>
		</view>
		<u-popup v-model="isLoginModel" mode="center" width="600rpx" height="300rpx" border-radius="14" :closeable="false" :mask-close-able="false">
			<view class="pop_area">
				<text class="pop_title">用户信息登录失效</text>
				<button class="authorize_btn" open-type="getUserInfo" @getuserinfo="mpWxGetUserInfo">登录</button>
			</view>
		</u-popup>
		<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	const app = getApp();
	const GD = app.globalData;
	export default {
		data() {
			return {
				iconList:[
					{name:'ordersAll',title:'全部订单',url:'../../static/image/icon_me-allorder@2x.png'},
					{name:'waitPay',title:'待付款',url:'../../static/image/icon_me_pay.png'},
					{name:'waitTor',title:'未使用',url:'../../static/image/icon_me_service.png'}
				],
				headImg: '../../static/image/icon_userheadimg.png',
				userName: '游客',
				points: 0,
				isbind: false,
                isLoginModel: false,
			}
		},
		onShow(){
			const userInfo = uni.getStorageSync("storage_session") || '';
            var that = this;
            that.headImg = userInfo.headImg || '../../static/image/icon_userheadimg.png';
            that.userName = userInfo.nickName || '游客';
            if(userInfo.userPhone != null){
                this.isbind = true;
                uni.getSetting({
                   success: (res) => {
                	   console.log(res);
                		if(!res.authSetting['scope.userInfo']){
                			that.isLoginModel = true;
                		}
                   }
                })
                this.$u.api.getUserInfo({
                	userId: userInfo.id
                }).then(res =>{
                    console.log(res)
                	that.points = res.data.points;
                }).catch(res =>{
                	this.$util.msg(res.message);
                })
            }
            
			
		},
		onLoad() {
			const that= this, userInfo = uni.getStorageSync("storage_session") || '';
			if(userInfo.userPhone == null){
				//that.isLoginModel = true;
				this.$u.route('pages/login/login');
			}else{
				this.isbind = true;
				console.log(userInfo)
				
			}
			
		},
		methods: {
            //点击授权获取用户信息
            mpWxGetUserInfo(e){
            	console.log(e)
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
            				that.headImg = res.data.headImg;
            				that.userName = res.data.nickName;
            			}).catch(res => {
            				console.log(res)
            				this.$util.msg(res.message);
            			})
            		}
            	}
            	this.$u.throttle(getUserInfoCallback,500);
            },
			// 拨打客服电话
			callService(){
				uni.makePhoneCall({
				    phoneNumber: '0553-3911111' //
				});
			},
			//跳转设置界面
			goSetting(){
				this.$u.route('pages/my/setting',{
					isbind: this.isbind,
					userphone: uni.getStorageSync("storage_session").userPhone
				})
			},
			//设置车牌号
			carNum(){
				this.$u.route('pages/my/carNum')
			},
			//受邀码
			invite(){
				this.$u.route('pages/my/invite')
			},
			
			
		}
	}
</script>

<style>
	page {
		background-color: #ededed;
	}
	.headimg{
		width: 140rpx;
		height: 140rpx;
		border-radius: 50%;
	}
	.user-box {
		background-color: #fff;
	}

	.footer {
		position: fixed;
		width: 100%;
		bottom: 8vh;
	}
	.img_icon{
		width: 68rpx;
		height: 68rpx;
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
