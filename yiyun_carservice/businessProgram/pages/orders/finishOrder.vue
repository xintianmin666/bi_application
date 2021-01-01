<template>
	<view>
		<!-- 订单类型与订单设置 -->
		<view class="cu-bar bg-white solid-top solid-bottom margin-top-xs">
			<view class="action text-xl">
				<text class="cuIcon-title text-orange "></text> 订单类型：<text class="text-orange text-bold">{{orderInfo.orderType=='1'?'洗车':orderInfo.orderType=='2'?'保养':orderInfo.orderType=='3'?'维修':orderInfo.orderType=='4'?'检测':orderInfo.orderType=='5'?'驾培':orderInfo.orderType=='6'?'保险':'救援'}}</text>
			</view>
			<view class="action">
				<view class="cu-btn bg-red  radius text-bold" >已完成</view>
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
				<text>用户号码：</text>
				<text class="text-blue text-bold" >{{orderInfo.userPhone}}</text>
			</view>
			<!-- 预约时间 保养 维修 才有 -->
			<view class="cu-form-group" >
				<view class="title">预约时间</view>
				<text class="text-bold">{{orderInfo.bookTime?orderInfo.bookTime:'无'}}</text>
			</view>
			<view class="cu-form-group ">
				<text>服务项目：</text>
				<text class=" text-bold" v-if="orderInfo.orderType=='1'||orderInfo.orderType=='4'">{{orderInfo.productName}}</text>
				<text v-else class=" text-bold">{{orderInfo.orderType=='2'?'保养':orderInfo.orderType=='3'?'维修':orderInfo.orderType=='5'?'驾培':orderInfo.orderType=='6'?'保险':'救援'}}</text>
			</view>
			<view class="cu-form-group ">
				<text>服务价格：</text>
				<text>¥<text class="text-bold text-red text-lg margin-lr-xs">{{orderInfo.productAmount}}</text>元</text>
			</view>
			<view class="cu-form-group ">
				<text>积分抵扣：</text>
				<text>-<text class="text-bold text-red text-lg margin-lr-xs">{{orderInfo.pointsAmount?orderInfo.pointsAmount:0}}</text>元</text>
			</view>
			<view class="cu-form-group ">
				<text>实收金额：</text>
				<text>¥<text class="text-bold text-red text-lg margin-lr-xs">{{orderInfo.collectAmount}}</text>元</text>
			</view>
			<view class="cu-form-group ">
				<text>支付时间：</text>
				<text class=" text-bold">{{orderInfo.payTime}}</text>
			</view>
			<!-- 一键救援订单 专用  选择车辆后联动修改添加驾驶员及电话 -->
			<view class="solid-top" v-if="orderInfo.orderType=='7'">
				<view class="cu-form-group ">
					<text class="minW70">救援地址：</text>
					<text class=" ">{{orderInfo.address}}</text>
				</view>
				<view class="cu-form-group text-right">
					<text class=" ">指派车辆：</text>
					<input placeholder="请选择指派车辆" v-model="resPlateNo" name="input" :disabled="true"></input>
				</view>
				<view class="cu-form-group text-right">
					<text>指派人员：</text>
					<input placeholder="请选择联系人" v-model="resPerson" name="input" :disabled="true"></input>
				</view>
				<view class="cu-form-group text-right">
					<text>指派号码：</text>
					<input placeholder="请选择联系号码" v-model="resPhone" name="input" :disabled="true"></input>
				</view>
				<!-- 地图start -->
				<view class="page-section page-section-gap">
					<map style="width: 100%; height: 300px;" :latitude="orderInfo.latitude" :markers="curLocation" :longitude="orderInfo.longitude">
					</map>
				</view>
				<!-- 地图end -->
			</view>
			<view  v-if="orderInfo.orderType!='1'&&orderInfo.orderType!='4'">
				<!-- 图片上传 -->
				<view class="cu-bar bg-white ">
					<view class="action">
						费用清单
					</view>
					<view class="action">
						{{imgList.length}}/{{imgList.length}}
					</view>
				</view>
				<view class="cu-form-group">
					<view class="grid col-4 grid-square flex-sub">
						<view class="bg-img" v-for="(item,index) in imgList" :key="index" @tap="ViewImage" :data-url="imgList[index]">
							<image :src="imgList[index]" mode="aspectFill"></image>
						</view>
					</view>
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
				appointDate: '待用户商定',
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
				oType: ''
			}
		},
		onLoad(options) {
			// 接收订单参数
			if (options.order) {
				this.orderInfo = JSON.parse(options.order);
				console.log('[页面传值]：', this.orderInfo)
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
					this.curLocation[0].latitude = this.orderInfo.latitude
					this.curLocation[0].longitude = this.orderInfo.longitude
				}
			}
			// this.appointDate = this.$u.timeFormat(this.timestamp, 'yyyy-mm-dd');
		},
		methods: {
			// 查看图片
			ViewImage(e) {
				uni.previewImage({
					urls: this.imgList,
					current: e.currentTarget.dataset.url
				});
			},

		},
		onBackPress() {
			// console.log('取消-返回')
			// uni.$off('changeOrderStatus');
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
