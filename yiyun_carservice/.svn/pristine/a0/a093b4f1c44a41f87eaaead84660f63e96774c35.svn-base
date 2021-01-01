<template>
	<view>
		<!-- nav -->
		<u-navbar title-color="#fff" back-icon-color="#ffffff" :is-fixed="true" :is-back="false" :background="{'background-image': 'linear-gradient(45deg, rgb(28, 187, 180), rgb(141, 198, 63))'}"
		 :back-text-style="{color: '#fff'}" title="宜运开发" />
		<view class="flex user-box padding-lr-lg padding-bottom-lg padding-top">
			<view class="u-m-r-10">
				<u-avatar src="../../static/image/car.png" size="140"></u-avatar>
			</view>
			<view class="u-flex-1 padding-left">
				<view class="u-font-18 u-p-b-20">游客User1</view>
				<view class="u-font-14 u-tips-color">当前号码：1888888888</view>
			</view>
			<view class="u-m-l-10 u-p-10">
				<u-icon name="setting" color="#969799" size="44"></u-icon>
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
				<u-cell-item icon="rmb-circle" title="支付"></u-cell-item>
			</u-cell-group>
		</view>

		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item icon="star" title="收藏"></u-cell-item>
				<u-cell-item icon="coupon" title="卡券"></u-cell-item>
			</u-cell-group>
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
				iconList:[
					{name:'ordersAll',title:'全部订单',url:'../../static/image/orderAll.png'},
					{name:'waitPay',title:'待付款',url:'../../static/image/waitPay.png'},
					{name:'waitTor',title:'未出行',url:'../../static/image/waitTor.png'}
				]
			}
		},
		onLoad() {
			
		},
		methods: {
			// 拨打客服电话
			callService(){
				uni.makePhoneCall({
				    phoneNumber: '0553-3911111' //
				});
			}
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
		position: fixed;
		width: 100%;
		bottom: 8vh;
	}
	.img_icon{
		width: 68rpx;
		height: 68rpx;
	}
	
</style>
