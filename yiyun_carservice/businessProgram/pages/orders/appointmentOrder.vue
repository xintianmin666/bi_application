<template>
	<view>
		<!-- 订单类型与订单设置 -->
		<view class="cu-bar bg-white solid-top solid-bottom margin-top-xs">
			<view class="action text-xl">
				<text class="cuIcon-title text-orange "></text> 订单类型：<text class="text-orange text-bold">{{orderInfo.orderType=='2'?'保养':orderInfo.orderType=='3'?'维修':orderInfo.orderType=='5'?'驾培':orderInfo.orderType=='6'?'保险':'救援'}}</text>
			</view>
			<view class="action" v-if="orderInfo.payStatus!=1">
				<button class="cu-btn bg-yellow shadow" @tap="orderEditBtn=true">价格修改</button>
			</view>
		</view>
		<!-- 驾培和保险提示流程 -->
		<view class="margin-top-xs" v-if="orderInfo.orderType!='1'&&orderInfo.orderType!='4'">
			<!-- <view class="margin-top-xs" > -->
			<view class="cu-bar  bg-white solid-top">
				<view class="action border-title">
					<text class="text-xl text-bold text-black">操作流程</text>
					<text class="bg-gradual-orange" style="width:3rem"></text>
				</view>
			</view>
			<view class="bg-white padding-lr padding-bottom">
				<view class="cu-steps">
					<view class="cu-item " v-for="(auitem,auindex) in actionList" :key="auindex">
						<text class=" text-red" :class="'cuIcon-' + auitem.cuIcon"></text>
						<text class=" text-black"> {{auitem.name}}</text>
					</view>
				</view>
			</view>
		</view>
		<!--  -->
		<!-- 订单信息 -->
		<view class="margin padding-sm bg-white radius" v-if="orderInfo.payStatus!=1">
			<view class="cu-form-group " v-if="orderInfo.orderType!=5">
				<text>车牌号：</text>
				<text class=" text-bold">{{orderInfo.carNo?orderInfo.carNo:'无'}}</text>
			</view>
			
			<view class="cu-form-group ">
				<text>下单时间：</text>
				<text class=" text-bold">{{orderInfo.createTime}}</text>
			</view>
			<view class="cu-form-group ">
				<text>用户号码：</text>
				<text class="text-blue text-bold" @tap="callUser(orderInfo.userPhone)">{{orderInfo.userPhone}}</text>
			</view>
			<!-- 预约时间 保养 维修 才有 -->
			<view v-if="orderInfo.orderType=='2'||orderInfo.orderType=='3'">
				<u-picker v-model="dataOpen" mode="time" :params="timeParams" @confirm="apDateChange"></u-picker>
				<view class="cu-form-group" >
					<view class="title">预约日期</view>
					<!-- <picker mode="date" :value="appointDate" start="2020-12-12" end="2050-12-31" @change="DateChange">
						<view class="picker text-bold">
							{{appointDate}}
						</view>
					</picker> -->
					<view class="picker text-red text-bold" @click="dataOpen = true">
						{{appointDate?appointDate:'修改时间'}}
					</view>
				</view>
			</view>
			<view class="cu-form-group ">
				<text>服务项目：</text>
				<text class=" text-bold">{{orderInfo.orderType=='2'?'保养':orderInfo.orderType=='3'?'维修':orderInfo.orderType=='5'?'驾培':orderInfo.orderType=='6'?'保险':'救援'}}</text>
			</view>
			<view class="cu-form-group ">
				<text>服务价格：</text>
				<text v-if="orderInfo.productAmount!=null">¥<text class="text-bold text-red text-lg margin-lr-xs">{{orderInfo.productAmount}}</text>元</text>
				<text v-else class="text-bold text-red ">待用户商定</text>
			</view>
			<view class="cu-form-group ">
				<text>付款方式：</text>
				<text class=" text-bold">在线支付</text>
			</view>
			<!-- 一键救援订单 专用  选择车辆后联动修改添加驾驶员及电话 -->
			<view v-if="orderInfo.orderType=='7'">
				<view class="cu-form-group ">
					<text class="minW70">救援地址：</text>
					<text class=" ">{{orderInfo.address}}</text>
				</view>
				<view class="cu-form-group" v-if="!orderInfo.rescueCarNo">
					<view class="title">救援指派</view>
					<picker @change="sendCarChange" :value="disResIndex" :range="disResCarList">
						<view class="picker">
							{{disResIndex>-1?disResCarList[disResIndex]:'请确认指派车辆'}}
						</view>
					</picker>
				</view>
				<view class="cu-form-group text-right">
					<text class=" ">指派车辆：</text>
					<input placeholder="请选择指派车辆" v-model="resPlateNo" name="input"></input>
				</view>
				<view class="cu-form-group text-right">
					<text>指派人员：</text>
					<input placeholder="请选择联系人" v-model="resPerson" name="input"></input>
				</view>
				<view class="cu-form-group text-right">
					<text>联系号码：</text>
					<input placeholder="请选择联系号码" v-model="resPhone" name="input"></input>
				</view>
				<!-- 地图start -->
				<view class="page-section page-section-gap">
					<map style="width: 100%; height: 300px;" :latitude="orderInfo.latitude" :markers="curLocation" :longitude="orderInfo.longitude">
					</map>
				</view>
				<!-- 地图end -->
			</view>
			<view>
				<!-- 图片上传 -->
				<view class="cu-bar bg-white ">
					<view class="action">
						费用清单上传
					</view>
					<view class="action">
						{{imgList.length}}/4
					</view>
				</view>
				<view class="cu-form-group">
					<view class="grid col-4 grid-square flex-sub">
						<view class="bg-img" v-for="(item,index) in imgList" :key="index" @tap="ViewImage" :data-url="imgList[index]">
							<image :src="imgList[index]" mode="aspectFill"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="index">
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="solids" @tap="ChooseImage" v-if="imgList.length<4">
							<text class='cuIcon-cameraadd'></text>
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 订单未接时展示的信息 -->
		<view class="margin padding-sm bg-white radius" v-else>
			<view class="cu-form-group ">
				<text>车牌号：</text>
				<text class="text-blue text-bold">{{orderInfo.carNo?orderInfo.carNo:'无'}}</text>
			</view>
			<view class="cu-form-group ">
				<text>下单时间：</text>
				<text class=" text-bold">{{orderInfo.createTime}}</text>
			</view>
			<view class="cu-form-group ">
				<text>服务项目：</text>
				<text class=" text-bold">{{orderInfo.orderType=='2'?'保养':orderInfo.orderType=='3'?'维修':orderInfo.orderType=='5'?'驾培':orderInfo.orderType=='6'?'保险':'救援'}}</text>
			</view>
		</view>

		<!-- 是否取消订单 -->
		<!-- <view class="padding flex flex-direction margin-top" v-if="isGetOrder==true">
			<button class="cu-btn bg-red lg shadow" @tap="orderCancelBtn=true">取消订单</button>
		</view> -->
		<view class="padding flex flex-direction " v-if="orderInfo.payStatus==1">
			<button class="cu-btn bg-green lg shadow" @tap="orderGetBtn=true">接收订单</button>
		</view>
		<view class="padding flex flex-direction " v-if="orderInfo.payStatus==3||orderInfo.payStatus==4">
			<button class="cu-btn bg-yellow lg shadow" v-if="orderInfo.orderType==2||orderInfo.orderType==3||orderInfo.orderType==7"
			 @tap="updateOrderInfo()">更新{{orderInfo.orderType=='7'?'指派人员':'预约'}}信息</button>
		</view>

		<!-- 取消订单modal框 -->
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
		<!-- 接收订单modal框 -->
		<view class="cu-modal" :class="orderGetBtn==true?'show':''">
			<view class="cu-dialog ">
				<view class="cu-bar bg-white justify-end">
					<view class="content">提示</view>
					<view class="action" @tap="orderGetBtn=false">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="padding">
					是否接收该订单？
				</view>
				<view class="cu-bar bg-white">
					<view class="action margin-0 flex-sub text-red solid-left" @tap="orderGetBtn=false">取消</view>
					<view class="action margin-0 flex-sub  solid-left" @tap="orderGet">确定</view>
				</view>
			</view>
		</view>
		<!-- 修改订单modal框 -->
		<view class="cu-modal" :class="orderEditBtn==true?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white justify-end">
					<view class="content">订单修改</view>
					<view class="action" @tap="orderEditBtn=false">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="padding">
					<view class="cu-form-group">
						<view class="title">服务价格：</view>
						<input class="text-right" placeholder="请输入金额" v-model="customPrice" name="input" type="number"></input>
					</view>
				</view>
				<view class="cu-bar bg-white">
					<view class="action margin-0 flex-sub text-red solid-left" @tap="orderEditBtn=false">取消</view>
					<view class="action margin-0 flex-sub  solid-left" @tap="orderPriceChange">确定</view>
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
				timeParams: {
					year: true,
					month: true,
					day: true,
					hour: true,
					minute: true,
					second: true
				},
				dataOpen: false,
				isGetOrder: true,
				orderEditBtn: false,
				orderGetBtn: false,
				orderCancelBtn: false,
				customPrice: 0,
				// 本地展示的文件集合
				imgList: [],
				// 需要上传的文件集合
				fileSaves: [],
				// 救援车辆 电话 存储列表
				resCarList: [],
				// 救援车辆 展示列表
				disResCarList: [],
				disResIndex: -1,
				resPlateNo: '',
				resPhone: '',
				resPerson: '',
				appointDate: '',
				// 地图坐标
				userPosition: {
					latitude: 31.318653,
					longitude: 118.399504,
				},
				curLocation: [{
					latitude: 31.318653,
					longitude: 118.399504,
					iconPath: '../../static/image/location.png',
					width: 32,
					height: 32
				}],
				orderInfo: {},
				oType: '',
				actionList: [{
					cuIcon: 'camera',
					name: '上传清单'
				}, {
					cuIcon: 'btn',
					name: '点击修改'
				}, {
					cuIcon: 'edit',
					name: '输入价格'
				}, {
					cuIcon: 'appreciate',
					name: '确认提交'
				}, {
					cuIcon: 'roundcheckfill',
					name: '操作完成'
				}],
			}
		},
		onLoad(options) {
			// 接收订单参数
			if (options.order) {
				this.orderInfo = JSON.parse(options.order);
				console.log('[页面传值]：', this.orderInfo)
				this.customPrice = this.orderInfo.productAmount;
				// 拿到imglist后需要做处理拼接
				if (this.orderInfo.productDescribe) {
					this.fileSaves = this.orderInfo.productDescribe.split(",");
					let tmpImgList = this.orderInfo.productDescribe.split(",");
					for (let i = 0; tmpImgList.length && i < tmpImgList.length; i++) {
						tmpImgList[i] = GD.testURL + tmpImgList[i]
					}
					this.imgList = tmpImgList;
				}
				// 如果为救援订单
				if (this.orderInfo.orderType == '7') {
					this.resPlateNo = this.orderInfo.rescueCarNo
					this.resPhone = this.orderInfo.rescueMobile
					this.resPerson = this.orderInfo.rescueName
				}
				// 填充预约时间 针对 orderType = 2 / =3
				this.appointDate = this.orderInfo.bookTime;
			}
			if (this.orderInfo.payStatus != '1' && this.orderInfo.orderType == '7') {
				this.getRescCarList();
			}
			// this.appointDate = this.$u.timeFormat(this.timestamp, 'yyyy-mm-dd');
		},
		methods: {
			// 联系用户
			callUser(tel) {
				uni.makePhoneCall({
					phoneNumber: tel
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
			// 接收订单
			orderGet() {
				let params = {
					token: this.$store.state.token,
					//商品id
					id: this.orderInfo.id,
					orderCode: this.orderInfo.orderCode,
				};
				this.$http.post(GD.testURL,
					'/business/corder/getPhone', params).then(res => {
					//成功回调
					console.log("[接口]:", res);
					uni.showToast({
						title: res.msg
					})
					uni.$emit('changeOrderStatus', {});
					this.orderInfo.payStatus = 3;
					//  救援订单则获取救援车辆列表
					if (this.orderInfo.payStatus != '1' && this.orderInfo.orderType == '7') {
						this.getRescCarList();
					}
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					uni.showToast({
						title: err.msg,
						icon: 'none'
					})
				});
				// 
				this.orderGetBtn = false;
			},
			// 选择图片
			ChooseImage() {
				let that = this;
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album', 'camera'], //从相册选择
					success: (res) => {
						let imgUrl = res.tempFilePaths[0]
						that.upload(imgUrl);
						// if (this.imgList.length != 0) {
						// 	this.imgList = this.imgList.concat(res.tempFilePaths)
						// } else {
						// 	this.imgList = res.tempFilePaths
						// }
					}
				});
			},
			// 查看图片
			ViewImage(e) {
				uni.previewImage({
					urls: this.imgList,
					current: e.currentTarget.dataset.url
				});
			},
			// 删除图片
			DelImg(e) {
				const idx = e.currentTarget.dataset.index;
				uni.showModal({
					title: '删除确认',
					content: '确定要删除已上传的清单吗？',
					cancelText: '取消',
					confirmText: '确定',
					success: res => {
						if (res.confirm) {
							this.imgList.splice(idx, 1);
							this.fileSaves.splice(idx, 1);
						}
					}
				})
			},
			upload(imgUrl) {
				var that = this;
				uni.uploadFile({
					url: GD.testURL + '/common/upload', //仅为示例，并非真实接口地址。
					filePath: imgUrl,
					formData: {
						token: this.$store.state.token,
						file: imgUrl
					},
					name: "file",
					method: "POST",
					success: (uploadFileRes) => {
						const res = JSON.parse(uploadFileRes.data)
						console.log(res)
						// console.log(res.data) 返回的上传图片文件id
						// 将提交成功的文件，返回dataid存储起来，用于删除使用
						this.fileSaves.push(res.fileName)
						// 直接存储本地图片
						this.imgList.push(res.url)
						console.log('this.fileSaves', this.fileSaves)
						console.log('this.imgList', this.imgList)
					}
				})


			},
			// 选择派遣车辆后默认填充人员 号码
			sendCarChange(e) {
				this.disResIndex = e.detail.value
				this.resPlateNo = this.resCarList[this.disResIndex].plateNo
				this.resPhone = this.resCarList[this.disResIndex].phone
				this.resPerson = this.resCarList[this.disResIndex].person
			},
			// 预约时间改变
			apDateChange(e) {
				this.appointDate = e.year+'-'+e.month+'-'+e.day+' '+e.hour+':'+e.minute+':'+e.second
			},
			// 获取救援车辆列表
			getRescCarList() {
				// 地图坐标展示
				this.curLocation[0].latitude = this.orderInfo.latitude
				this.curLocation[0].longitude = this.orderInfo.longitude
				let params = {
					token: this.$store.state.token,
					dictType: 'rescue_car',
				};
				this.$http.post(GD.testURL,
					'/system/dict/data/getList', params).then(res => {
					//成功回调
					console.log("[接口]:", res);
					this.disResCarList = [];
					Object.entries(res.data)
						.filter(([id, car]) => car.dictValue)
						.forEach(([id, car]) => {
							const rescCar = {
								plateNo: car.dictValue,
								phone: car.dictLabel,
								person: car.remark
							}
							this.disResCarList.push(car.dictValue);
							this.resCarList.push(rescCar)
						})
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					uni.showToast({
						title: err.msg,
						icon: 'none'
					})
				});
			},
			// 更新救援订单信息  
			updateOrderInfo() {
				if (this.orderInfo.orderType == '2' || this.orderInfo.orderType == '3') {
					this.updateBookTime();
					return
				}
				let params = {
					token: this.$store.state.token,
					// 此处id为商品id
					id: this.orderInfo.id,
					orderCode: this.orderInfo.orderCode,
					rescueName: this.resPerson.trim(),
					rescueMobile: this.resPhone.trim(),
					rescueCarNo: this.resPlateNo.trim()
				};
				this.$http.post(GD.testURL,
					'/business/corder/editRescueInfo', params).then(res => {
					//成功回调
					console.log("[接口]:", res);
					uni.$emit('changeOrderStatus', {});
					uni.showToast({
						title: res.msg
					})
					
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					uni.showToast({
						title: err.msg,
						icon: 'none'
					})
				});
			},
			// 更新预约时间
			updateBookTime() {
				let params = {
					token: this.$store.state.token,
					orderCode: this.orderInfo.orderCode,
					bookTime:  this.appointDate,
					userPhone: this.orderInfo.userPhone
				};
				this.$http.post(GD.testURL,
					'/business/corder/modifyBookTime', params).then(res => {
					//成功回调
					console.log("[接口]:", res);
					uni.$emit('changeOrderStatus', {});
					uni.showToast({
						title: res.msg
					})
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					uni.showToast({
						title: err.msg,
						icon: 'none'
					})
				});
			},
			// 订单金额、图片路径上传
			orderPriceChange() {
				// 如果救援和驾培没有上传图片就修改金额提交
				if (this.orderInfo.orderType == '5' || this.orderInfo.orderType == '6') {
					if (this.fileSaves.length < 1) {
						uni.showToast({
							title: '请确保清单图片已上传再修改价格',
							icon: 'none',
							duration: 2000
						})
						this.orderEditBtn = false;
						return
					}
				}
				this.orderInfo.productAmount = this.customPrice;
				let imgStr = '';
				if (this.fileSaves.length) {
					// 确认上传，则对图片集合进行拼接。
					imgStr = this.fileSaves.join(",");
				}
				let params = {
					token: this.$store.state.token,
					// 此处id为商品id
					id: this.orderInfo.id,
					orderCode: this.orderInfo.orderCode,
					productAmount: this.customPrice,
					productDescribe: imgStr,
					orderType: this.orderInfo.orderType
				};
				this.$http.post(GD.testURL,
					'/business/corder/modifyOrderPriceAndDescribe', params).then(res => {
					//成功回调
					console.log("[接口]:", res);
					uni.$emit('changeOrderStatus', {});
					uni.showToast({
						title: res.msg
					})
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					uni.showToast({
						title: err.msg,
						icon: 'none'
					})
				});
				this.orderEditBtn = false;
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

	.minW70 {
		min-width: 80px;
	}
</style>
