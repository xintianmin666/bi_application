<template>
	<view class="solid-top solid-green tbox">
		<view class="flex user-box padding-lr-lg padding-bottom-lg padding-top">
			<view class="u-m-r-10">
				<u-avatar src="../../static/image/waitPay.png" size="140"></u-avatar>
			</view>
			<view class="u-flex-1 padding-left">
				<view class="u-font-18 u-p-b-20">管理员A1</view>
				<view class="u-font-14 u-tips-color">所属商家：运泰自营</view>
			</view>
			<view class="u-m-l-10 u-p-10" @tap="goSetting">
				<u-icon name="setting-fill" color="#0081ff" size="44"></u-icon>
			</view>
		</view>
		
		<!-- <view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item icon="order" title="全部订单" @tap="goOrdeaAll('all')"></u-cell-item>
				<u-cell-item icon="rmb-circle" title="已结算" @tap="goOrdeaAll('pay')"></u-cell-item>
			</u-cell-group>
		</view> -->
		
		<view class="cu-list menu sm-border  margin-top" >
			<!-- <view class="cu-item arrow" @tap="goOrdeaAll('all')">
				<view class="content">
					<text class="cuIcon-list text-green"></text>
					<text class="text-grey">全部订单</text>
				</view>
			</view>
			<view class="cu-item arrow" @tap="goOrdeaAll('pay')">
				<view class="content">
					<text class="cuIcon-rechargefill text-yellow"></text>
					<text class="text-grey">已结算订单</text>
				</view>
			</view>
			<view class="cu-item " @tap="toQRcode">
				<button class="cu-btn content" >
					<text class="cuIcon-scan text-blue"></text>
					<text class="text-grey">我的推广码</text>
				</button>
			</view> -->
			<view class="cu-item arrow" @tap="goPurchasePage">
				<view class="content">
					<text class="cuIcon-cartfill text-red"></text>
					<text class="text-grey">集中采购</text>
				</view>
			</view>
			<view class="cu-item " >
				<view class="content">
					<text class="cuIcon-tagfill text-red  margin-right-xs"></text>
					<text class="text-grey">我的店铺标签</text>
				</view>
				<view class="action">
					<view class="cu-tag round bg-orange light">洗车</view>
					<view class="cu-tag round bg-olive light">保养</view>
					<view class="cu-tag round bg-blue light">维修</view>
				</view>
			</view>
		</view>


		<view class="footer text-center">
			<text class="text-blue" @tap="callService">客服电话：0553-3911111</text>
			<view class="text-gray text-sm">安徽宜运信息技术服务有限公司提供技术支持</view>
		</view>

	</view>
</template>

<script>
	const app = getApp();
	const GD = app.globalData;
	export default {
		data() {
			return {
				type:'all'
			}
		},
		onLoad() {
			
		},
		onReady() {
			
		},
		methods: {
			// 拨打客服电话
			callService(){
				uni.makePhoneCall({
				    phoneNumber: '0553-3911111' //
				});
			},
			// 跳转至所有订单页面
			goOrdeaAll(type){
				uni.navigateTo({
					url:'./orderAll/orderAll?type='+type
				})
			},
			// 跳转至设置界面
			goSetting(){
				uni.navigateTo({
					url:'./setting/setting'
				})
			},
			// 跳转至二维码界面
			toQRcode(){
				uni.navigateTo({
					url:'./myQRcode'
				})
			},
			// 跳转至设置界面
			goPurchasePage(){
				uni.navigateTo({
					url:'../purchaseCenter/purchaseCenter'
				})
			},
		}
	}
</script>

<style>
	page {
		background-color: #ededed;
	}

	.user-box {
		background-color: #fff;
	}

	.footer {
		margin-top: 8vh;
	}
	.img_icon{
		width: 68rpx;
		height: 68rpx;
	}
	
</style>
