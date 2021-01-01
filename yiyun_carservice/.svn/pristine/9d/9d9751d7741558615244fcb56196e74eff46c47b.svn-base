<template>
	<view>
		<!-- default类订单 只展示 洗车 检测 -->
		<!-- 订单类型与订单设置 -->
		<view class="cu-bar bg-white solid-top solid-bottom margin-top-xs">
			<view class="action text-xl">
				<text class="cuIcon-title text-orange "></text> 订单类型：<text class="text-orange text-bold">{{orderInfo.orderType=='1'?'洗车':'检测'}}</text>
			</view>
		</view>
		<!-- 订单信息 -->
		<view class="margin padding-sm bg-white radius" >
			<view class="cu-form-group ">
				<text>车牌号：</text>
				<text class="text-blue text-bold">{{orderInfo.carNo?orderInfo.carNo:'无'}}</text>
			</view>
			<view class="cu-form-group ">
				<text>下单时间：</text>
				<text class=" text-bold">{{orderInfo.createTime}}</text>
			</view>
			<view class="cu-form-group ">
				<text>用户电话：</text>
				<text class="text-blue text-bold" @tap="callUser(orderInfo.userPhone)">{{orderInfo.userPhone}}</text>
			</view>
			<view class="cu-form-group ">
				<text>服务产品：</text>
				<text class=" text-bold">{{orderInfo.productName}}</text>
			</view>
			<view class="cu-form-group ">
				<text>服务价格：</text>
				<text>¥<text class="text-bold text-red text-lg margin-lr-xs">{{orderInfo.productAmount}}</text>元</text>
			</view>
			<view class="cu-form-group ">
				<text>付款方式：</text>
				<text class=" text-bold">在线支付</text>
			</view>
		</view>
		
		<!-- 请输入核验码 -->
		<view class="margin" v-if="isScan==true&&orderInfo.payStatus!='5'">
			<view class="padding flex flex-direction margin-top">
				<button class="cu-btn bg-red lg shadow" @tap="orderConfirm">订单确认</button>
			</view>
		</view>
		
		<!-- 是否取消订单 -->
		<!-- <view class="padding flex flex-direction margin-top">
			<button class="cu-btn bg-red lg shadow" @tap="orderCancelBtn=true">取消订单</button>
		</view> -->

		<!-- <view class="cu-modal" :class="orderCancelBtn==true?'show':''">
			<view class="cu-dialog ">
				<view class="cu-bar bg-white justify-end">
					<view class="content">提示</view>
					<view class="action" @tap="orderCancelBtn=false">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="padding">
					是否取消该订单？
				</view>
				<view class="cu-bar bg-white">
					<view class="action margin-0 flex-sub text-red solid-left" @tap="orderCancelBtn=false">取消</view>
					<view class="action margin-0 flex-sub  solid-left" @tap="orderCancel">确定</view>
				</view>
			</view>
		</view> -->

	</view>
</template>

<script>
	const app = getApp();
	const GD = app.globalData;
	export default {
		data() {
			return {
				orderCancelBtn: false,
				orderInfo: {},
				vCode:null,
				isScan:false,
			}
		},
		onLoad(options) {
			// 接收订单参数
			if (options.order) {
				console.log(options)
				this.orderInfo = JSON.parse(options.order);
				console.log('[页面传值]：', this.orderInfo)
				this.isScan = options.scan?true:false;
			}
		
			// this.appointDate = this.$u.timeFormat(this.timestamp, 'yyyy-mm-dd');
		},
		methods: {
			// 联系用户
			callUser(tel) {
				uni.makePhoneCall({
					phoneNumber: tel //仅为示例
				});
			},
			// 是否取消订单
			orderCancel() {
				this.orderCancelBtn = false;
				uni.showToast({
					title: '该订单已被取消',
					icon: 'none',
					duration: 1500
				})
			},
			// 订单确认
			orderConfirm(){
				let params = {
					token: this.$store.state.token,
					id:this.orderInfo.id,
					orderCode: this.orderInfo.orderCode,
					verifyCode:  this.orderInfo.verifyCode,
				};
				this.$http.post(GD.testURL,
					'/business/corder/checkOrderGoods', params).then(res => {
					//成功回调
					console.log("[接口]:", res);
					uni.showToast({
						title:res.msg,
						icon:'none'
					})
					uni.$emit('changeOrderStatus', {});
					setTimeout(()=>{
						uni.switchTab({
							url:'../home'
						})
					},2000)
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					uni.showToast({
						title:err.msg,
						icon:'none'
					})
				});
			},
		},
		onBackPress() {
			// console.log('取消-返回')
			uni.$off('changeOrderStatus');
		}
	}
</script>

<style>
	page {
		background: #F1F1F1;
	}
</style>
