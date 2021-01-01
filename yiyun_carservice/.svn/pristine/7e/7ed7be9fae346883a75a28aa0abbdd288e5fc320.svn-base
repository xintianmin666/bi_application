<template>
	<view class="padding-tb">
		<!-- 未支付订单 -->
		<!-- <view v-if="needPayId" class="bg-white  padding-lr padding-tb-sm flex justify-between align-center">
			<view class="text-bold text-shadow"><text class="cuIcon-notice text-lg text-orange margin-right-sm"></text>您有一条<text
				 class="text-orange">未支付</text>订单待支付...</view>
			<view @tap='goPay' class="cu-btn bg-blue text-bold round shadow">去支付</view>
		</view> -->
		<!-- 间隔 -->
		<!-- <view style="height: 28rpx;"></view> -->
		<!-- 订单列表 -->
		<view class="margin-lr bg-white shadow  cu-list card-menu" v-for="(item,index) in orderList" :style="[{animation: 'show ' + ((index+1)*0.2+1) + 's 1'}]"
		 :key="index" @tap="goDetail(item)">
			<view class="flex align-center solid shadow padding-sm">
				<!-- 左 -->
				<view class=" margin-right margin-left-xs">
					<view class="cu-avatar radius bg-white">
						<image src="../../../../static/image/rescue.png" mode="aspectFit"></image>
					</view>
				</view>
				<!-- 右 -->
				<view class="">
					<view class=" text-sm margin-bottom-xs">
						<text class=" ">呼叫救援时间：<text class="">{{item.createTime}}</text></text>
					</view>
					<view>车牌号：<text class="text-blue text-bold">{{item.carNo}}</text></view>
				</view> 
				<!-- 右 -->
				<view class="margin-left-sm cu-tag radius bg-red" :class="item.orderStatus=='4'?'bg-olive':item.orderStatus=='5'?'bg-green':item.orderStatus=='1'?'bg-yellow':'bg-orange'">
					{{item.orderStatus=='1'?'已下单':item.orderStatus=='2'?'已取消':item.orderStatus=='3'?'已接单':item.orderStatus=='4'?'服务中':item.orderStatus=='5'?'已完成':'已退款'}}
				</view>
			</view>
		</view>
		<!-- 点击加载更多 -->
		<view class="cu-load text-gray text-bold" :class="isOver?'over':''" @tap="getMore">{{isOver?'':'点击加载更多'}}</view>
	</view>
</template>

<script>
	const app = getApp();
	const GD = app.globalData;
	export default {
		data() {
			return {
				orderList: [],
				isOver: true,
				pagePramas: {
					pageNo: 1,
					pageSize: 10,
					userId: '',
					orderStatus: '',
					orderType: '7',
					// 救援目前只有一家公司 目前是固定 9 号 shopID，为了和商户端那边对应
					shopId: '9'
				},
				curPage: 1,
				total: 1,
				needPayId: '',
			}
		},
		onLoad(options) {
			this.pagePramas.userId = uni.getStorageSync('storage_session').id;
			this.getList();
			if(options.rtPoints=='rtPoints'){
				console.log(options)
				// 成功回调请求订单状态
					this.$http.post(GD.webUrl,
						'/order/payFailOrder', {orderCode:options.orderId}).then(res => {
						//成功回调
					}).catch(err => {
						//异常回调
						console.log('请求失败', err);
					});
			}
		},
		methods: {
			// 提交救援订单
			getList() {
				this.$http.post(GD.webUrl,
					'/order/getOrderPage', this.pagePramas).then(res => {
					//成功回调
					console.log("[接口]:", res);
					this.orderList = this.orderList.concat(res.data.list);
					// 找出未支付的订单
					Object.entries(this.orderList)
						.filter(([index, order]) => {
							if (order.orderStatus == '服务中') {
								this.needPayId = order.orderCode
							}
						})

					if (res.data.pageNum < res.data.pages) {
						this.pagePramas.pageNo+=1;
						this.isOver = false
					} else {
						this.isOver = true
					}
			
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
				});
			},
			goDetail(item) {
				uni.navigateTo({
					url: '../rescueDetails/rescueDetails?orderId=' + item.orderCode
				})
			},
			// 点击加载更多
			getMore() {
				if (this.isOver == false) {
					this.getList();
				}
			},
			// 跳转至支付页面
			// goPay() {
			// 	uni.navigateTo({
			// 		url: '../payment/payment?orderId=' + this.needPayId
			// 	})
			// }
		},
		// 下拉刷新
		onPullDownRefresh() {
			this.pagePramas.pageNo = 1;
			this.orderList= [];
			this.getList();
		},
	}
</script>

<style>

</style>
