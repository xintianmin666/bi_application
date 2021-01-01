<template>
	<view>
		<u-toast ref="uToast" />

		<u-swiper :list="list" mode="none"></u-swiper>

		<view class="padding-sm bg-white">
			<view class="inputView">
				<text>学员姓名</text>
				<input type="text" v-model="userName" placeholder="请输入姓名" />
			</view>
			<view class="inputView">
				<text>联系电话</text>
				<input type="number" v-model="userPhone" placeholder="请输入手机号码" />
			</view>
			<view class="inputView">
				<text>邀请码</text>
				<input type="text" v-model="inviteCode" placeholder="请输入邀请码" />
			</view>

			<view class="inputView">
				<text>驾培类型</text>
				<u-select v-model="show1" mode="single-column" :list="driverTypeList" @confirm="change1"></u-select>
				<view >
					<text @click="show1 = true">{{driverType.label}}</text>
					<u-icon name="arrow-right"></u-icon>
				</view>
			</view>

			<view class="inputView">
				<text>驾校名称</text>
				<u-select v-model="show2" mode="single-column" :list="driverNameList" @confirm="change2"></u-select>
				<view>
					<text @click="show2 = true"> {{driverName.label}}</text>
					<u-icon name="arrow-right"></u-icon>
				</view>
			</view>

		</view>

		<view class="bottomButton flex text-center">
			<button class="flex-sub bg-yellow " @tap="checkProgress"><text class="cuIcon-search padding-sm "></text>查进度</button>
			<button class="flex-sub bg-blue " @click="submitInfoToDriving"><text class="cuIcon-message padding-sm "></text>提交报名</button>
		</view>

	</view>
</template>

<script>
	const app = getApp();
	const GD = app.globalData;
	export default {
		data() {
			return {
				list: ["https://cdn.uviewui.com/uview/swiper/3.jpg"],
				userName: "",
				userPhone: "",
				inviteCode: "",

				driverTypeList: [{
						label: 'C1',
						value: 1
					},
					{
						label: 'C2',
						value: 2
					},
					{
						label: 'B1',
						value: 3
					},
					{
						label: '从业资格证',
						value: 4
					}
				],
				driverType: {
					label: "C1",
					value: "1"
				},
				show1: false,

				driverNameList: [{
					label: '请选择驾校',
					value: "",

				}],
				driverName: {
					label: "请选择驾校",
					value: "",
				},
				show2: false,

			}
		},

		onLoad(options) {

			if (this.typeTitle == '') {
				this.typeTitle = this.options1[0].label;
				this.driverType = this.options1[0].value;
			}

			if (this.drivingName == '') {
				this.drivingName = this.options2[0].label;
			}
			
			this.inviteCode = uni.getStorageSync('storage_session').inviteCode?uni.getStorageSync('storage_session').inviteCode:"";

			this.requestDrivingList();
		},

		methods: {

			requestDrivingList() {
				this.$http.get(GD.testUrl,
					'/clinet/shop/list', {
						pageNum: 1,
						pageSize: 10,
						serviceType: 5,
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {

						for (var i = 0; i < res.rows.length; i++) {
							var temp = {
								label: res.rows[i].address,
								value: res.rows[i].id
							}

							this.driverNameList.push(temp);
						}
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

			change1(e) {
				console.log(e)
				this.driverType = e[0];
			},


			change2(e) {
				console.log(e)
				this.driverName = e[0];
			},

			//查进度
			checkProgress() {
				uni.navigateTo({
					url: "../progress?orderType=5"
				})
			},

			//提交报名
			submitInfoToDriving() {
				if (this.userName == "") {
					this.$refs.uToast.show({
						title: '请输入学员姓名',
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
				if (this.driverName.value == "") {
					this.$refs.uToast.show({
						title: '请选择驾校',
						type: 'none',
					})
					return;
				}
				this.$http.post(GD.goodsUrl,
					'/order/createOrder', {
						userPhone: this.userPhone,
						userId: uni.getStorageSync('storage_session').id,
						orderType: 5,
						inviteCode: this.inviteCode,
						shopId: this.driverName.value,
						userName: this.userName,
						driverType: this.driverType.label,
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						this.$refs.uToast.show({
							title: '提交报名成功，请等待客服联系',
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
			}
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

	.inputView input {
		flex: 1;
		border: 1px solid #2979ff;
		height: 35px;
		padding-left: 20rpx;
	}

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
