<template>
	<view>
		<u-toast ref="uToast" />

		<view class="padding-sm bg-white" style="padding-bottom: 100rpx;">
			<view class="inputView">
				<text>车主姓名</text>
				<input type="text" v-model="userName" placeholder="请与行驶证保持一致" />
			</view>
			<view class="inputView">
				<text>联系电话</text>
				<input type="text" v-model="userPhone" placeholder="填写手机号码" />
			</view>
			<view class="inputView">
				<text>号牌种类</text>
				<u-dropdown :close-on-click-mask="true" ref="uDropdown" class="flex">
					<u-dropdown-item @change="change" v-model="kind" :title="driverType" :options="options"></u-dropdown-item>
				</u-dropdown>

			</view>
			<view class="inputView">
				<text>投保车牌号牌</text>
				<input type="text" v-model="carNo" placeholder="例:皖B12345" />
			</view>
			<view class="inputView">
				<text>邀请码</text>
				<input type="text" v-model="inviteCode" placeholder="请输入邀请码(非必填)" />
			</view>

			<view class="inputView">
				<text>选择承保机构</text>
			</view>

			<u-radio-group v-model="value" class="margin-sm">
				<u-radio @change="radioChange" v-for="(item, index) in insuranceList" :key="index" :name="item.shopId">
					{{item.title}}
				</u-radio>
			</u-radio-group>

			<view class="padding-sm">
				<text>
					保险咨询反馈时间说明：
					周一至周五，8:30-17:00，用户提交信息后12小时内进行反馈；
					周六至周日，用户提交信息后周一进行反馈。
				</text>
			</view>


			<view class="padding-sm u-border-bottom text-xl">
				<text>承保车辆行驶证照片</text>
			</view>
			<view class="padding-sm flex justify-around">
				<view>
					<text>正本照片</text>
					<u-upload ref="uUpload1" :action="action" :auto-upload="true" max-count="1" @on-choose-complete="afterRead(1)"></u-upload>
				</view>
				<view>
					<text>副本照片</text>
					<u-upload ref="uUpload2" :action="action" :auto-upload="true" max-count="1" @on-choose-complete="afterRead(2)"></u-upload>
				</view>

			</view>
		</view>

		<view class="bottomButton flex text-center">
			<button class="flex-sub bg-yellow " @click="checkProgress"><text class="cuIcon-search padding-sm"></text>查进度</button>
			<button class="flex-sub bg-red " @click="submitInfoToInsurance"><text class="cuIcon-comment padding-sm"></text>去询价</button>
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
				insuranceList: [],
				value: '',
				kind: '1',
				driverType: '',
				options: [{
						label: '小型汽车(蓝牌)',
						value: 1,
					},
					{
						label: '大型汽车(黄牌)',
						value: 2,
					},
					{
						label: '小型新能源汽车(绿牌)',
						value: 3,
					},
					{
						label: '新车(无牌)',
						value: 4,
					}
				],
				action: GD.goodsUrl + "/order/upload",
				orderCode: "",
			}
		},
		onLoad(options) {

			if (this.driverType == '') {
				this.driverType = this.options[0].label;
			}
			
			this.inviteCode = uni.getStorageSync('storage_session').inviteCode?uni.getStorageSync('storage_session').inviteCode:"";

			this.requestInsuranceList();

		},
		methods: {

			afterRead(type) {
				var event = [];
				var fileType = "";
				if (type == 1) {
					event = this.$refs.uUpload1.lists
					fileType = "driverLicenseFront";
				} else {
					event = this.$refs.uUpload2.lists
					fileType = "driverLicenseBack";
				}
				this.upload(event, fileType)
			},


			upload(event, fileType) {
				let that = this;
				event.map(item => {
					console.log(item.url)
					if (item.file) {
						uni.uploadFile({
							url: this.action,
							filePath: item.url,
							formData: {
								fileType: fileType,
								file: item.url,
								orderCode: that.orderCode
							},
							name: "file",
							method: "POST",
							success(res) {
								const file = JSON.parse(res.data);
								console.log(file)
								if (file.code == 200) {
									that.orderCode = file.data.orderCode;
								}
								
								console.log(that.orderCode)
							}
						});
					}
				})

			},

			change(index) {
				this.driverType = this.options[index - 1].label;
			},
			// 选中某个单选框时，由radio时触发
			radioChange(e) {
				this.value = e;
				console.log(this.value);
			},

			requestInsuranceList() {
				this.$http.get(GD.testUrl,
					'/clinet/shop/list', {
						pageNum: 1,
						pageSize: 10,
						serviceType: 6,
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {

						for (var i = 0; i < res.rows.length; i++) {
							var temp = {
								title: res.rows[i].name,
								shopId: res.rows[i].id
							}

							this.insuranceList.push(temp);
						}

						if (this.value == '') {
							this.value = this.insuranceList[0].shopId;
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
			//查进度
			checkProgress() {
				uni.navigateTo({
					url: "../progress?orderType=6"
				})
			},
			//去询价
			submitInfoToInsurance() {
				
				if (this.userName == "") {
					this.$refs.uToast.show({
						title: '请输入车主姓名',
						type: 'none',
					})
					return;
				}
				if (this.userPhone == "") {
					this.$refs.uToast.show({
						title: '请输入联系电话',
						type: 'none',
					})
					return;
				}
				if (this.carNo == "") {
					this.$refs.uToast.show({
						title: '请输入投保车牌号',
						type: 'none',
					})
					return;
				}
				if (this.orderCode == "") {
					this.$refs.uToast.show({
						title: '请上传车辆行驶证照片',
						type: 'none',
					})
					return;
				}

				this.$http.post(GD.goodsUrl,
					'/order/createOrder', {
						userPhone: this.userPhone,
						userId: uni.getStorageSync('storage_session').id,
						orderType: 6,
						inviteCode: this.inviteCode,
						shopId: this.value,
						userName: this.userName,
						driverType: this.driverType,
						carNo: this.carNo,
						orderCode: this.orderCode
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						this.$refs.uToast.show({
							title: '已成功提交，请等待客服联系',
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
