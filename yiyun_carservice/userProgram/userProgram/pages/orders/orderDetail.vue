<template>
	<view>
		<u-toast ref="uToast" />

		<u-swiper :list="coverPic" height="400"></u-swiper>

		<view class="bg-white padding flex justify-between">
			<text class="text-black">{{order.shopAddress}}</text>
			<u-icon name="map" size="40" @click="gotoMap"></u-icon>
		</view>

		<view class="padding bg-white flex justify-between">
			<text class="text-black">商户电话：{{order.shopPhone}}</text>
			<u-icon name="phone" size="40" @click="phonecall(order.shopPhone)"></u-icon>
		</view>

		<view class="margin-top bg-white" v-if="orderType == 1 || orderType == 4">

			<view class="padding-sm u-border-bottom" v-for="(item,index) in order.orderGoodsList" :key="index">
				<view v-if="item.payStatus == 3 || item.payStatus == 5 || item.payStatus == 6">
					<view>
						<view class="text">{{item.orderType}}</view>
						<view class="text-red text-xl">¥{{item.collectAmount}}</view>
						<view class="text">创建订单时间:{{order.createTime}}</view>
					</view>

					<u-modal v-model="show">
						<image :src="QRcode" mode=""></image>
					</u-modal>

					<view class="padding-sm flex justify-between" v-if="item.payStatus == 3 ">
						<u-button type="warning" @click="refundOrder(item)">订单退款</u-button>
						<u-button type="success" @click="open(item)">去消费</u-button>
					</view>

					<view class="padding-sm" v-if="item.payStatus == 5">
						<u-button type="success">已消费</u-button>
					</view>

					<view class="padding-sm" v-if="item.payStatus == 6">
						<u-button type="success">退款成功</u-button>
					</view>
				</view>

				<view class="padding-sm" v-if="item.payStatus == 1 || item.payStatus == 2">
					<view>
						<view class="text">订单项目:{{order.orderTypeName}}</view>
						<view class="text-red text-xl">订单金额:¥{{order.collectAmount}}</view>
						<view class="text">创建订单时间:{{order.createTime}}</view>
					</view>
					<view class="margin-top flex justify-between" v-if="item.payStatus == 1">
						<u-button type="error" @click="cancelOrder">取消订单</u-button>
						<u-button type="success" @click="payOrder(order.orderCode)">去支付</u-button>
					</view>
				</view>

			</view>
			
			<view class="padding flex flex-direction margin-top" v-if="order.orderStatus=='3'&&canEvaluate==true">
				<button class="cu-btn bg-orange lg shadow" @tap="contentModal = true">立即评价</button>
			</view>

		</view>

		<view class="margin-top bg-white padding-sm" v-if="orderType == 2 || orderType == 3">
			<view>
				<view class="text">订单项目:{{order.orderTypeName}}</view>

				<view class="text-red text-lg" v-if="order.orderStatus == 1 || order.orderStatus == 3">订单价格等待确认</view>
				<view class="text-red text-lg" v-if="order.orderStatus == 4 || order.orderStatus == 5">订单金额:￥{{order.orderAmount}}</view>

				<view class="text">创建订单时间:{{order.createTime}}</view>

				<view class="text-red text-lg" v-if="order.orderStatus == 4 ">实付金额:￥{{actualPrice}}</view>
				<view class="text-red text-lg" v-if="order.orderStatus == 5 ">实付金额:￥{{order.collectAmount}}</view>
				<view class="flex justify-between" v-if="order.orderStatus == 4">
					<view class="text-black">可用积分{{points}}</view>
					<u-switch v-model="checked" @change="changeStatus"></u-switch>
				</view>

			</view>

			<view class="bottomButton" v-if="order.orderStatus == 4">
				<u-button type="success" @click="payOrder()">去支付</u-button>
			</view>
			<view class="bottomButton" v-if="order.orderStatus == 1 || order.orderStatus == 3">
				<u-button type="error" @click="cancelOrder">取消订单</u-button>
			</view>

			<view class="padding flex flex-direction margin-top" v-if="order.orderStatus=='5'&&canEvaluate==true">
				<button class="cu-btn bg-orange lg shadow" @tap="contentModal = true">立即评价</button>
			</view>

		</view>

		<!-- 支付码modal框 -->
		<view class="cu-modal" :class="showPwd==true?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white justify-end">
					<view class="content">输入支付密码</view>
					<view class="action" @tap="iptCodeCancel">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="padding">
					<view class="cu-form-group">
						<view class="title">支付密码：</view>
						<input placeholder="请输入" v-model="payPassword" maxlength="6" name="input" type="password"></input>
					</view>
				</view>
				<view class="cu-bar bg-white">
					<view class="action margin-0 flex-sub text-red solid-left" @tap="iptCodeCancel">取消</view>
					<view class="action margin-0 flex-sub  solid-left" @tap="iptCodeConfirm">确定</view>
				</view>
			</view>
		</view>


		<!-- 评价modal框 -->
		<view class="cu-modal" :class="contentModal==true?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white justify-end">
					<view class="content">服务评价</view>
					<view class="action" @tap="contentCancel">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="padding text-left">
					<view class="cu-form-group">
						<text>服务评分</text>
						<u-rate :count="5" v-model="startRating" :min-count="1" active-icon="heart-fill" inactive-icon="heart"></u-rate>
					</view>
					<view class="cu-form-group" v-if="contentModal==true">
						<textarea maxlength="-1" v-model="content" placeholder="请输入您的评价"></textarea>
					</view>
				</view>
				<view class="cu-bar bg-white">
					<view class="action margin-0 flex-sub text-red solid-left" @tap="contentCancel">取消</view>
					<view class="action margin-0 flex-sub  solid-left" @tap="contentConfirm">确定</view>
				</view>
			</view>
		</view>

	</view>
</template>

<script>
	const app = getApp();
	const GD = app.globalData;
	export default {
		data() {
			return {
				orderCode: "",
				orderType: "",
				order: {},
				coverPic: [],
				show: false,
				QRcode: '',
				checked: true,
				points: 0,
				actualPrice: 0,
				showPwd: false,
				payPassword: '',
				pwd: '',
				isNeedMorePay: true,
				canEvaluate: false,
				contentModal: false,
				content: "",
			}
		},

		onLoad(options) {
			this.orderCode = options.orderCode;
			this.orderType = options.orderType;
			this.getOrderInfo();
		},
		onShow() {
			this.getUserInfo();
		},
		methods: {

			// 获取用户信息
			getUserInfo() {
				const userId = uni.getStorageSync('storage_session').id;
				if (!userId) {
					uni.showToast({
						title: '请先登录后再进行操作',
						icon: 'none'
					})
					return
				}
				this.$http.post(GD.goodsUrl,
					'/user/getUserInfo', {
						userId: userId
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						this.points = res.data.points;
						this.pwd = res.data.payPassword;

						if (this.checked) {
							if (this.points - this.order.orderAmount * 100 > 0) {
								this.actualPrice = 0;
								this.isNeedMorePay = false
							} else {
								this.actualPrice = (this.order.orderAmount * 100 - this.points) / 100;
								this.isNeedMorePay = true
							}
						} else {
							this.actualPrice = this.order.orderAmount;
							this.isNeedMorePay = true
						}
					} else {
						uni.showToast({
							title: res.message,
							icon: 'none'
						})
					}

				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
				});
			},

			changeStatus(status) {
				this.checkNeedPay(status);
			},

			checkNeedPay(status) {
				if (status) {
					// 模拟使用积分并拿到了用户积分余额，然后与订单做计算
					this.actualPrice = (this.order.orderAmount * 100 - this.points) / 100;
					if (this.actualPrice > 0) {
						this.isNeedMorePay = true

					} else {
						this.isNeedMorePay = false
						this.actualPrice = 0;
					}
				} else {
					this.actualPrice = this.order.orderAmount;
					this.isNeedMorePay = true
				}
			},

			getOrderInfo() {
				 var that = this;
				this.$http.post(GD.goodsUrl,
					'/order/getOrderInfo', {
						orderCode: this.orderCode
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						this.order = res.data;
						if(that.orderType == 1 || that.orderType == 4){

							for (var i = 0; i < res.data.orderGoodsList.length; i++) {
								
								if(res.data.orderGoodsList[i].payStatus == '5' && res.data.isEvaluate == 0){
									that.canEvaluate = true
								}
							}

						}else{
							that.canEvaluate = res.data.isEvaluate == 0 ? true : false
						}
							

						var list = this.order.coverPic.split(",");
						for (var i = 0; i < list.length; i++) {
							this.coverPic.push(GD.testUrl + list[i])
						}

						uni.setNavigationBarTitle({
							title: this.order.shopName
						})
					}
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					this.$refs.uToast.show({
						title: err.message,
						type: 'err',
					})
				});
			},

			//跳转店铺地址
			gotoMap() {
				console.log(11)
				var lonLat = this.order.lonLat.split(",");
				var lon = lonLat[0]
				var lat = lonLat[1]
				uni.navigateTo({
					url: '../home/shopLocation?lon=' + lon + "&lat=" + lat,
				});
			},

			//拨打电话
			phonecall(phoneNumber) {
				uni.makePhoneCall({
					phoneNumber: phoneNumber
				});
			},

			//提交订单
			payOrder() {

				if (uni.getStorageSync('storage_session').userPhone == null) {
					uni.navigateTo({
						url: '../login/login'
					})
					return;
				}
				// 是否使用积分？ 当前积分是否满足该商品价格？
				if (this.checked == true && !this.isNeedMorePay) {
					// 1.使用积分 积分足够 需要输入积分支付密码
					//跳转设置支付密码路径"/pages/my/changeInfo?type=3"
					if (!this.pwd) {
						uni.navigateTo({
							url: '../my/changeInfo?type=3'
						})
						return
					}
					this.showPwd = true;

				} else {
					console.log('积分不够或未使用积分')
					// 走支付通道
					this.iptCodeConfirm();
				}
			},

			//弹出框取消支付
			iptCodeCancel() {
				this.showPwd = false;
				this.payPassword = '';
			},
			//弹出框确定支付
			iptCodeConfirm() {
				var that = this;
				this.$http.post(GD.goodsUrl,
					'/order/payOrder', {
						isUsePoints: this.checked ? "1" : "2",
						payPassword: this.payPassword, //支付密码
						orderCode: this.orderCode, //订单号
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						if (that.actualPrice > 0) {
							that.$util.payWx(res, "/pages/orders/order", that.orderCode)
							that.queryWeChatOrder()
						} else {
							uni.switchTab({
								url: '../orders/order'
							})
						}
					}
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					this.$refs.uToast.show({
						title: err.message,
						type: 'err',
					})
				});
			},

			queryWeChatOrder() {
				this.$http.post(GD.goodsUrl,
					'/order/queryWeChatOrder', {
						orderCode: this.orderCode, //订单号
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {

					}
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					this.$refs.uToast.show({
						title: err.message,
						type: 'err',
					})
				});
			},
			//打开核销码
			open(item) {
				this.show = true;
				this.QRcode = item.verifyCode
			},

			//取消订单
			cancelOrder() {
				this.$http.post(GD.goodsUrl,
					'/order/cancelOrder', {
						orderCode: this.orderCode
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						this.$refs.uToast.show({
							title: res.message,
							type: 'success',
						})
						this.getOrderInfo();
					}
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					this.$refs.uToast.show({
						title: err.message,
						type: 'err',
					})
				});
			},
			//订单退款
			refundOrder(item) {

				this.$http.post(GD.goodsUrl,
					'/order/refundOrder', {
						refundId: item.refundId,
						orderCode: this.orderCode,
						userId: uni.getStorageSync('storage_session').id
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						this.getOrderInfo();
						this.$refs.uToast.show({
							title: res.message,
							type: 'success',
						})

					}
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					this.$refs.uToast.show({
						title: err.message,
						type: 'err',
					})
				});
			},

			// 评价取消
			contentCancel() {
				this.contentModal = false;
				this.content = ""
			},
			// 提交评价
			contentConfirm() {
				const userId = uni.getStorageSync('storage_session').id;
				let params = {
					evaluateContent: this.content,
					orderCode: this.orderCode,
					startRating: this.startRating,
					userId: userId,
					shopId: this.order.shopId
				};
				this.$http.post(GD.goodsUrl,
					'/order/createEvaluate', params).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						uni.showToast({
							title: res.message
						})
						setTimeout(() => {
							uni.navigateBack();
						}, 1000)
					}
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					uni.showToast({
						title: err.message,
						icon: 'none'
					})
				});
				this.contentCancel();
			},

		}
	}
</script>

<style>
	.bottomButton {
		position: fixed;
		bottom: 0rpx;
		left: 0;
		right: 0;
		z-index: 999;
	}

	button {
		border-radius: 0;
	}
</style>
