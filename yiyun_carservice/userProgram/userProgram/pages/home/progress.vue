<template>
	<view class="padding-tb">

		<!-- 订单列表 -->
		<view class="margin-lr bg-white shadow  cu-list card-menu" v-for="(item,index) in orderList" :style="[{animation: 'show ' + ((index+1)*0.2+1) + 's 1'}]"
		 :key="index" @tap="goDetail(item)">
			<view class="flex align-center solid shadow padding-sm">
				<!-- 左 -->
				<view class=" margin-right margin-left-xs">
					<view class="cu-avatar radius bg-white">
						<image v-if="orderType == 5" src="../../static/image/driving.png" mode="aspectFit"></image>
						<image v-if="orderType == 6" src="../../static/image/insurance1.png" mode="aspectFit"></image>
					</view>
				</view>
				<!-- 右 -->
				<view class="">
					<view class=" text-sm margin-bottom-xs">
						<text class=" ">申请时间：<text class="">{{item.createTime}}</text></text>
					</view>
					<view v-if="orderType == 5">驾校名称：<text class="text-blue text-bold">{{item.shopName}}</text></view>
					<view v-if="orderType == 6">车牌号：<text class="text-blue text-bold">{{item.carNo}}</text></view>
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
				orderType:'',
				pageNo: 1,
				orderList:[],
				userId:''
			}
		},
		
		onLoad(options) {
			this.orderType = options.orderType;
			this.userId = uni.getStorageSync('storage_session').id;
			this.getOrderList();
			
			if(this.orderType == 5){
				uni.setNavigationBarTitle({
					title:"驾校报名进度"
				})
			}else if(this.orderType == 6){
				uni.setNavigationBarTitle({
					title:"申请保险进度"
				})
			}
		},
		
		// 下拉刷新
		onPullDownRefresh() {
			this.pageNo = 1;
			this.orderList= [];
			this.getOrderList();
		},
		
		methods: {
			
			// 提交救援订单
			getOrderList() {
				this.$http.post(GD.goodsUrl,
					'/order/getOrderPage', {
						pageNo: this.pageNo,
						pageSize: 10,
						userId: this.userId,
						orderStatus: '',
						orderType: this.orderType
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					this.orderList = this.orderList.concat(res.data.list);
					
					if (res.data.pageNum < res.data.pages) {
						this.pageNo += 1;
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
				console.log(item)
				uni.navigateTo({
					url:"./progressDetail?orderCode=" + item.orderCode +"&orderType=" + this.orderType,
				})
			},
			// 点击加载更多
			getMore() {
				if (this.isOver == false) {
					this.getOrderList();
				}
			},
			
		}
		
	}
</script>

<style>

</style>
