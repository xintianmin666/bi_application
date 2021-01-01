<template>
	<view>
		<view class="margin padding-sm bg-white radius">
			<view class="cu-form-group ">
				<text>车牌号：</text>
				<text class="text-blue text-bold">{{detail.carNo}}</text>
			</view>
			<view class="cu-form-group ">
				<text>救援时间：</text>
				<text class=" text-bold">{{detail.createTime}}</text>
			</view>
			<view class="cu-form-group ">
				<text>服务项目：</text>
				<text class=" text-bold">{{detail.orderTypeName}}</text>
			</view>
			<view class="cu-form-group ">
				<text>服务价格：</text>
				<text>¥<text class="text-bold text-red text-lg margin-lr-xs">{{detail.orderAmount}}</text>元</text>
			</view>

			<!-- <radio-group class="block" @change="payWayChange">
				<view class="cu-form-group">
					<view class="title">付款方式：</view>
				</view>
				<view class=" align-center padding-sm">
					<view class="margin-bottom-sm flex justify-between align-center">
						<view>
							<view class="cu-avatar radius bg-white">
								<image src="../../../../static/image/qianbao.png" mode="aspectFit"></image>
							</view>
							<text class="margin-left-sm">钱包支付</text>
						</view>
						<radio class="green" :class="payWay=='wallet'?'checked':''" :checked="payWay=='wallet'?true:false" value="wallet"></radio>
					</view>
					<view class="margin-bottom-sm flex justify-between align-center">
						<view>
							<view class="cu-avatar radius bg-white">
								<image src="../../../../static/image/yinlian.png" mode="aspectFit"></image>
							</view>
							<text class="margin-left-sm">银联支付</text>
						</view>
						<radio class="green" :class="payWay=='yinlian'?'checked':''" :checked="payWay=='yinlian'?true:false" value="yinlian"></radio>
					</view>
					<view class=" flex justify-between align-center">
						<view>
							<view class="cu-avatar radius bg-white">
								<image src="../../../../static/image/wxPay.png" mode="aspectFit"></image>
							</view>
							<text class="margin-left-sm">微信支付</text>
						</view>
						<radio class="green" :class="payWay=='wxPay'?'checked':''" :checked="payWay=='wxPay'?true:false" value="wxPay"></radio>
					</view>
				</view>
			</radio-group> -->
			<!-- 价格 -->
			<view class="solid-top">
				<!-- 是否·使用积分 -->
				<view class="cu-form-group  align-center" v-if="userPoints>0">
					<view>使用积分<text class="margin-left-xs text-sm text-gray">(拥有积分：{{userPoints}})</text></view>
					<!-- <switch class='orange radius' @change="usePointsChange" :class="usePoints?'checked':''" :checked="usePoints?true:false"></switch> -->
					<switch class='blue' @change="usePointsChange" :class="usePoints?'checked':''" :checked="usePoints?true:false"></switch>
				</view>
				<view class="cu-form-group " v-if="usePoints==true">
					<text>积分抵扣金额：</text>
					<text>-<text class="text-bold text-red text-lg margin-lr-xs">{{userPointsCount}}</text>元</text>
				</view>
				<view class="cu-form-group ">
					<text>实付金额：</text>
					<text>¥<text class="text-bold text-red text-lg margin-lr-xs">{{collectAmount}}</text>元</text>
				</view>
			</view>


			<!-- 支付码modal框 -->
			<view class="cu-modal" :class="iptCode==true?'show':''">
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
							<input placeholder="请输入" v-model="payPassWord" maxlength="6" name="input" type="password"></input>
						</view>

						<!-- 新密码输入 -->
						<!-- <u-message-input :focus="true" :value="payPassWord" mode="box" :maxlength="maxlength"></u-message-input> -->
						<view class="cu-form-group margin-top">
							<view class="title">确认支付：</view>
							<input placeholder="请输入" v-model="capplyCode" name="input" type="password"></input>
						</view>
					</view>
					<view class="cu-bar bg-white">
						<view class="action margin-0 flex-sub text-red solid-left" @tap="iptCodeCancel">取消</view>
						<view class="action margin-0 flex-sub  solid-left" @tap="iptCodeConfirm">确定</view>
					</view>
				</view>
			</view>

		</view>
		<!-- 支付按钮 -->
		<view class="padding flex flex-direction margin-top">
			<button class="cu-btn bg-green lg shadow" @tap="payConfirm">确认支付</button>
		</view>
	</view>
</template>

<script>
	const app = getApp();
	const GD = app.globalData;
	export default {
		data() {
			return {
				payWay: '',
				usePoints: false,
				detail: {},
				imgSrc: null,
				// 支付码开关
				iptCode: false,
				// 用户积分
				userPoints: 0,
				userPointsCount: 0,
				payPassWord: '',
				capplyCode: null,
				isNeedMorePay: false,
				collectAmount: 0,
				orderId: "",
				userInfo:{},
				maxlength: 6,
			}
		},
		onLoad(options) {
			if (options.orderId) {
				this.getOrderInfo(options.orderId);
			}
		},
		onShow() {
			// 模拟获取用户信息
			this.getUserInfo();
		},
		methods: {
			// 获取救援订单详情
			getOrderInfo(orderId) {
				this.orderId = orderId;
				this.$http.post(GD.webUrl,
					'/order/getOrderInfo', {
						orderCode: orderId
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					this.detail = res.data;
					this.imgSrc = res.data.orderGoodsList[0].verifyCode;
					this.collectAmount = res.data.orderAmount
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
				});
			},
			// 支付方式改变
			payWayChange(e) {
				this.payWay = e.detail.value
			},
			// 是否使用积分
			usePointsChange(e) {
				// 1.计算抵扣金额
				// 2.实付价格 - 积分抵扣金额  = 本次交易实付
				// 3.积分不够支付，走微信支付时，password清空。
				this.usePoints = e.detail.value
				if (e.detail.value == true) {
					// 模拟使用积分并拿到了用户积分余额，然后与订单做计算
					let points = this.userPoints * 0.01;

					if (points - this.detail.orderAmount >= 0) {
						this.userPointsCount = this.detail.orderAmount;
						this.collectAmount = 0;
						this.isNeedMorePay = false
					} else {
						this.userPointsCount = Number(points.toFixed(2))
						this.collectAmount = this.detail.orderAmount - points;
						this.isNeedMorePay = true
					}
				} else {
					this.collectAmount = this.detail.orderAmount
				}
				// 如果有小数，则对其进行处理
				if(this.collectAmount.toString().indexOf('.'>=0)){
					this.collectAmount = Number(this.collectAmount.toFixed(2))
				}
			},
			// 支付确认按钮
			payConfirm() {
				//跳转设置支付密码路径"/pages/my/changeInfo?type=3"
				if (!this.userInfo.payPassword && this.usePoints == true) {
					uni.showToast({
						title:'您还未设置支付密码！',
						icon:'none'
					})
					setTimeout(()=>{
						uni.navigateTo({
							url: '../../../my/changeInfo?type=3'
						})
					},1500)
					return
				}
				var that = this;
				// 是否使用积分？ 当前积分是否满足该商品价格？
				if (this.usePoints == true && this.collectAmount == 0) {
					// 1.使用积分 积分足够 需要输入积分支付密码
					this.iptCode = true;
				} else {
					console.log('积分不够或未使用积分')
					// 走支付通道
					this.payPassWord = '';
					this.iptCodeConfirm();
				}
			},
			iptCodeCancel() {
				this.iptCode = false;
				this.payPassWord = '';
				this.capplyCode = '';
			},
			iptCodeConfirm() {
				const that = this;
				// 判断密码输入是否正确
				if(this.usePoints == true&&this.payPassWord!=''&&this.payPassWord != this.capplyCode){
					uni.showToast({
						title:'支付码输入错误！',
						icon:'none'
					})
					return
				}

				let params = {
					isUsePoints: this.usePoints == true ? '1' : '2',
					payPassword: this.payPassWord,
					orderCode: this.detail.orderCode
				}
				this.$http.post(GD.webUrl,
					'/order/payOrder', params).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						
						// 如果不是积分支付，走的是微信支付
						if (!this.payPassWord) {
							// 调取支付接口
							// 是否是因为使用了积分且积分不够才走的微信支付
							that.usePoints == true ? that.$util.payWx(res, "../rescueRecords/rescueRecords", that.detail.orderCode, 'rtPoints') : that.$util.payWx(res, "../rescueRecords/rescueRecords", that.detail.orderCode,'default')
						
							setTimeout(() => {
								// 成功回调请求订单状态
								this.$http.post(GD.webUrl,
									'/order/queryWeChatOrder', {
										orderCode: that.detail.orderCode
									}).then(res => {
									//成功回调
								}).catch(err => {
									//异常回调
									// console.log('请求失败', err.message);
								});
							}, 5000)
						} else {
							uni.showToast({
								title:'使用积分支付成功！'
							})
							setTimeout(() => {
								uni.redirectTo({
									url: '../rescueRecords/rescueRecords',
								})
							}, 1500)
						}

					} else {
						uni.showToast({
							title: res.mssage,
							icon: 'none',
							duration: 1500
						})
					}
					this.iptCodeCancel();
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					this.iptCodeCancel();
				});
			},
			// 获取用户信息
			getUserInfo() {
				this.userInfo = uni.getStorageSync('storage_session')
				if (!this.userInfo.id) {
					uni.showToast({
						title: '请先登录后再进行操作',
						icon: 'none'
					})
					return
				}
				this.$http.post(GD.webUrl,
					'/user/getUserInfo', {
						userId: this.userInfo.id
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						this.userInfo = res.data
						this.userPoints = res.data.points
						this.payPW = res.data.payPassword
					} else {
						uni.showToast({
							title: res.msg,
							icon: 'none'
						})
					}

				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
				});
			},

		}
	}
</script>

<style>

</style>
