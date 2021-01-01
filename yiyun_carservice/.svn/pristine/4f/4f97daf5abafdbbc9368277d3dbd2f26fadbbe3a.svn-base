<template>
	<view>
		<u-toast ref="uToast" />

		<u-swiper :list="coverPic" height="400"></u-swiper>

		<view class="flex flex-direction bg-white padding u-border-bottom">
			<text class="text-black text-xxl">{{shopDetail.name}}</text>
			<text>工作时间: {{workTime}}</text>
			<text>工作内容：{{shopDetail.workContent}}</text>
			<text>店铺评分：{{shopDetail.score}}</text>
			<u-rate :count="5" :current="shopDetail.score" :disabled="true" active-color="#FA3534" inactive-color="#b2b2b2"
			 gutter="20"></u-rate>
		</view>

		<view class="bg-white padding flex justify-between">
			<text class="text-black">{{shopDetail.address}}</text>
			<u-icon name="map" size="40" @click="gotoMap"></u-icon>
		</view>

		<view class="padding bg-white flex justify-between">
			<text class="text-black">商户电话：{{shopDetail.phone}}</text>
			<u-icon name="phone" size="40" @click="phonecall(shopDetail.phone)"></u-icon>
		</view>

		<view class="margin-top bg-white">
			<view class="flex justify-between align-center padding-sm u-border-bottom" v-for="(item,index) in shopDetail.shopGoodsList"
			 :key="index" @click="checkGoodsDetail(item)">
				<view>
					<view class="text">{{item.goodsName}}</view>
					<view class="text-red text-xl margin-top">¥{{item.payPrice}}</view>
				</view>

				<view>
					<u-button type="success" @click="gotoPayPage(item)">立即购买</u-button>
				</view>

			</view>
		</view>

		<view class="margin-top bg-white" v-if="orderType=='1' || orderType=='4'">
			<view class="u-border-bottom padding">服务流程</view>

			<image src="../../static/image/serve_step.png" mode=""></image>
		</view>
		
		<view class="margin-top bg-white">
			<view class="u-border-bottom padding">商品详情</view>
			<view v-for="(item,index) in goodsImage" :key="index">
				<image :src="item" mode="widthFix" style="width: 100%;"></image>
			</view>
		</view>

		<view class="bg-white">
			<view class="u-border-bottom padding">全部评价({{evaluateList.length}})</view>

			<view class="padding" v-if="evaluateList.length >0">
				<view class="u-border-bottom" v-for="(item,index) in evaluateList.slice(0,2)" :key="index">
					<view>{{item.nickName}}</view>
					<u-rate :count="5" :current="item.starRating" :disabled="true" active-color="#FA3534" inactive-color="#b2b2b2"
					 gutter="20"></u-rate>
					<view>评论内容:{{item.evaluateContent}}</view>
				</view>
			</view>

			<view class="u-border-bottom padding" @click="gotoEvaluatePage">查看全部评价({{evaluateList.length}})</view>
		</view>

		<view class="bottomButton text-center" v-if="orderType=='2' || orderType=='3'">
			<button class="bg-yellow text-white" @click="reserveTime">预约到店</button>
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
				shopDetail: {},
				orderType: "",
				coverPic: [],
				evaluateList: [],
				goodsImage:[],
				workTime:""
			}
		},

		onLoad(options) {
			this.shopDetail = JSON.parse(options.shopDetail);
			this.orderType = options.orderType;
			this.workTime = this.shopDetail.workTime.replace(',',' - ');
			var list1 = this.shopDetail.coverPic.split(",");
			for (var i = 0; i < list1.length; i++) {
				this.coverPic.push(this.baseUrl + list1[i])
			}
			
			var tempList = [];
			var list2 = this.shopDetail.shopTopPics.split(",");
			for (var i = 0; i < list2.length; i++) {
				tempList.push(this.baseUrl + list2[i])
			}
			this.goodsImage = tempList;

			this.requestShopEvaluate();
		},

		methods: {
			//获取店铺评价
			requestShopEvaluate() {

				this.$http.get(this.baseUrl,
					'/clinet/evaluate/getByShopId', {
						shopId: this.shopDetail.id
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						this.evaluateList = res.data;
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
			//跳转店铺地址
			gotoMap() {
				var lonLat = this.shopDetail.lonLat.split(",");
				var lon = lonLat[0]
				var lat = lonLat[1]
				uni.navigateTo({
					url: './shopLocation?lon=' + lon + "&lat=" + lat,
				});
			},
			
			//拨打电话
			phonecall(phoneNumber){
				uni.makePhoneCall({
				    phoneNumber: phoneNumber 
				});
			},
			
			//跳转商品全部评价
			gotoEvaluatePage() {
				uni.navigateTo({
					url: './checkEvaluate?shopId=' + this.shopDetail.id,
				});
			},

			//商品详情
			checkGoodsDetail(item) {
				uni.navigateTo({
					url: './goodsDetail?goodsId=' + item.id + '&address=' + this.shopDetail.address + '&orderType=' + this.orderType,
				});
			},

			//跳转支付页面
			gotoPayPage(item) {
				uni.navigateTo({
					url: './pay?orderType=' + this.orderType + '&address=' + this.shopDetail.address + '&goods=' + JSON.stringify(
						item),
				});
			},

			//预约到店
			reserveTime() {
				uni.navigateTo({
					url: './reserveTime?orderType=' + this.orderType + '&shopId=' + this.shopDetail.id,
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
		z-index: 999;
	}

	button {
		border-radius: 0;
	}
</style>
