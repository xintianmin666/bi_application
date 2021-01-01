<template>
	<view>
		<u-toast ref="uToast" />
		<view class="padding-sm bg-white">
			<view class="inputView">
				<text>车主姓名</text>
				<input type="text" v-model="userName" placeholder="请与行驶证保持一致" />
			</view>
			<view class="inputView">
				<text>联系电话</text>
				<input type="text" v-model="userPhone" placeholder="填写手机号码" />
			</view>
			<view class="inputView">
				<text>车牌号牌</text>
				<input type="text" v-model="carNo" placeholder="例:皖B12345" />
			</view>
			<view class="inputView">
				<text>邀请码</text>
				<input type="text" v-model="inviteCode" placeholder="请输入邀请码(非必填)" />
			</view>

			<view class="inputView">
				<text>预约时间</text>
				<u-calendar v-model="show" :mode="mode" @change="change" :min-date="mindate" :max-date="maxdate" range-color="#2979ff"></u-calendar>
				<text @tap="showDate">{{bookTime}}</text>
			</view>

		</view>

		<view class="bottomButton text-center margin-top padding">
			<button class="bg-yellow text-white" @click="creatReserveTimeOrder">提交预约</button>
		</view>
	</view>
</template>

<script>
	const app = getApp();
	const GD = app.globalData;
	export default {
		data() {
			return {
				userName: "",
				userPhone: "",
				carNo: "",
				inviteCode: "",
				orderType: "",
				shopId: "",
				show: false,
				mode: 'date',
				mindate: "",
				maxdate: "",
				bookTime: ""
			}
		},
		onLoad(options) {
			console.log(options)
			this.shopId = options.shopId;
			this.orderType = options.orderType;
			
			this.inviteCode = uni.getStorageSync('storage_session').inviteCode?uni.getStorageSync('storage_session').inviteCode:"";

			var date = new Date()
			this.mindate = this.$u.timeFormat(date, 'yyyy-mm-dd');
			this.bookTime = this.mindate;
			this.maxdate = this.$u.timeFormat(date.setMonth(date.getMonth() + 1), 'yyyy-mm-dd');

		},
		methods: {

			showDate() {
				this.show = true;
			},
			change(e) {
				console.log(e);
				this.bookTime = e.result;
			},

			//维修保养预约订单
			creatReserveTimeOrder() {
				if (this.userName == "") {
					this.$refs.uToast.show({
						title: '请输入车主姓名',
						type: 'none',
					})
					return;
				}
				if (this.userPhone == "") {
					this.$refs.uToast.show({
						title: '请输入手机号码',
						type: 'none',
					})
					return;
				}
				if (this.carNo == "") {
					this.$refs.uToast.show({
						title: '请输入车牌号',
						type: 'none',
					})
					return;
				}

				this.$http.post(GD.goodsUrl,
					'/order/createOrder', {
						userPhone: this.userPhone,
						userId: uni.getStorageSync('storage_session').id,
						orderType: this.orderType,
						inviteCode: this.inviteCode,
						shopId: this.shopId,
						userName: this.userName,
						carNo: this.carNo,
						bookTime: this.bookTime
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						this.$refs.uToast.show({
							title: '提交预约成功，请等待店铺联系',
							type: 'success',
						})
						
						uni.navigateBack();
					}
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					this.$refs.uToast.show({
						title: res.data.msg,
						type: 'err',
					})
				});

			},
		}
	}
</script>

<style>
	.inputView {
		display: flex;
		align-items: center;
		height: 48px;
		line-height: 48px;
		border-bottom: 1px solid #EEEEEE;
	}

	.inputView text {
		min-width: 100px;
		padding-left: 5px;
	}
</style>
