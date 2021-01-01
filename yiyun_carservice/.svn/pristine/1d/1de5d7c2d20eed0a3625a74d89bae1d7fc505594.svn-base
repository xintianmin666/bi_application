<template>
	<view>
		<u-toast ref="uToast" />

		<view class="flex align-center bg-white padding-sm">
			<image :src="goodsImage" imgMode="aspectFill" style="width: 250rpx;height: 220rpx; border-radius: 10rpx;"></image>
			<view class="margin">
				<view class="text-black text-xxl">{{goods.goodsName}}</view>
				<view>{{goods.shopName}}</view>
				<view class="text-red text-xxl">¥{{goods.payPrice}}</view>
				<view>{{address}}</view>
			</view>
		</view>

		<view class="margin-top bg-white flex justify-between padding-sm">
			<view class="text-black">购买数量</view>
			<u-number-box :min="1" :max="10" v-model="value" @change="valChange"></u-number-box>
		</view>

		<view class="margin-top bg-white">
			<view class="flex justify-between padding-sm u-border-bottom">
				<view class="text-black">产品金额</view>
				<view class="text-xl">¥{{goods.payPrice}}</view>
			</view>
			<view class="flex justify-between padding-sm">
				<view class="text-black">可用积分{{points}}</view>
				<u-switch v-model="checked" @change="changeStatus"></u-switch>
			</view>
		</view>

		<view class="bottomButton flex">
			<view class="flex-sub text-red bg-white text-xxl padding-sm text-center">实际付款 ¥{{actualPrice}}</view>
			<button class=" bg-gradual-green text-white" @click="payForGoods">提交订单</button>
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

	</view>
</template>

<script>
	const app = getApp();
	const GD = app.globalData;

	export default {
		data() {
			return {
				baseUrl: GD.testUrl,
				value: 1,
				orderType: "",
				address: "",
				goods: {},
				goodsImage: "",
				checked: true,
				points: 0,
				actualPrice: 0,
				showPwd: false,
				payPassword: '',
				pwd:'',
				isNeedMorePay: true
			}
		},
		onLoad(options) {
			console.log(options)
			this.goods = JSON.parse(options.goods);
			this.address = options.address;
			this.orderType = options.orderType;
			console.log(this.goods)

			var tempList = [];
			var list = this.goods.topPicUrl.split(",");
			for (var i = 0; i < list.length; i++) {
				tempList.push(this.baseUrl + list[i])
			}

			this.goodsImage = tempList[0];

		},
		
		onShow(){
			this.getUserInfo();
		},
		methods: {
			
			// 获取用户信息
			getUserInfo(){
				const userId = uni.getStorageSync('storage_session').id;
				if(!userId){
					uni.showToast({
						title:'请先登录后再进行操作',
						icon:'none'
					})
					return
				}
				this.$http.post(GD.goodsUrl,
					'/user/getUserInfo', {userId:userId}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						this.points = res.data.points;
						this.pwd = res.data.payPassword;
						
						if (this.checked) {
							if (this.points - this.goods.payPrice * this.value * 100 > 0) {
								this.actualPrice = 0;
								this.isNeedMorePay = false
							} else {
								this.actualPrice = (this.goods.payPrice * this.value * 100 - this.points)/100;
								this.isNeedMorePay = true
							}
						} else {
							this.actualPrice = this.goods.payPrice * this.value;
							this.isNeedMorePay = true
						}
					}else{
						uni.showToast({
							title: res.message,
							icon:'none'
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

			valChange(e) {
				this.value = e.value;
				this.checkNeedPay(this.checked);
			},

			checkNeedPay(status) {
				if (status) {
					// 模拟使用积分并拿到了用户积分余额，然后与订单做计算
					this.actualPrice = (this.goods.payPrice * this.value * 100 - this.points)/100;
					if (this.actualPrice > 0) {
						this.isNeedMorePay = true
						
					} else {
						this.isNeedMorePay = false
						this.actualPrice = 0;
					}
				} else {
					this.actualPrice = this.goods.payPrice * this.value;
					this.isNeedMorePay = true
				}
			},

			//提交订单
			payForGoods() {

				if (uni.getStorageSync('storage_session').userPhone == null) {
					uni.navigateTo({
						url: '../login/login'
					})
					return;
				}
				// 是否使用积分？ 当前积分是否满足该商品价格？
				if (this.checked == true && !this.isNeedMorePay) {
					// 1.使用积分 积分足够 需要输入积分支付密码
					console.log(this.pwd);
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

			//弹出框确认支付
			iptCodeConfirm() {
				this.$http.post(GD.goodsUrl,
					'/order/createOrder', {
						userPhone: uni.getStorageSync('storage_session').userPhone,
						userId: uni.getStorageSync('storage_session').id,
						isUsePoints: this.checked ? "1" : "2",
						orderType: this.orderType,
						shopId: this.goods.shopId,
						goodsName: this.goods.goodsName,
						orderAmount: this.goods.payPrice * this.value,
						collectAmount: this.actualPrice,
						goodsCode: this.goods.id,
						goodsNum: this.value,
						payPassword: this.payPassword
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						
						if(this.actualPrice > 0){
							this.payOrder(res.data)
						}else{
							this.showPwd = false;
							uni.switchTab({
								url:'../orders/order'
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
			
			payOrder(orderCode) {
				var that = this;
				this.$http.post(GD.goodsUrl,
					'/order/payOrder', {
						isUsePoints: this.checked, //是否使用积分
						payPassword: this.payPassword, //支付密码
						orderCode: orderCode, //订单号
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						that.$util.payWx(res, "/pages/orders/order", orderCode)
						that.queryWeChatOrder(orderCode)
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
			
			queryWeChatOrder(orderCode){
				this.$http.post(GD.goodsUrl,
					'/order/queryWeChatOrder', {
						orderCode: orderCode, //订单号
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
			}

		}
	}
</script>

<style>
	.bottomButton {
		position: fixed;
		bottom: 0rpx;
		left: 0;
		right: 0;
	}

	button {
		border-radius: 0;
	}
</style>
