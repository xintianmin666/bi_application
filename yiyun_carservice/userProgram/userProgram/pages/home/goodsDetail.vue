<template>
	<view>
		<u-toast ref="uToast" />
		
		<u-swiper :list="topPiclist" height="400"></u-swiper>

		<view class="padding bg-white u-border-bottom">
			<view class="u-font-18 text-black">{{goodsDtatil.goodsName}}</view>
			<view class="flex align-end margin-top">
				<view class="text-red text-xxl">¥{{goodsDtatil.payPrice}}</view>
				<view class="text-sm" style="margin-left: 20rpx; text-decoration: line-through;">¥{{goodsDtatil.price}}</view>
			</view>
		</view>

		<view class="bg-white u-border-bottom padding">无需预约，如遇消费高峰时段您可能需要排队等候</view>

		<view class="padding bg-white flex justify-between">
			<view>
				<view class="text-xl text-black">{{goodsDtatil.shopName}}</view>
				<view>{{address}}</view>
			</view>
			<u-icon name="phone" size="40"></u-icon>
		</view>

		<view class="margin-top bg-white">
			<view class="u-border-bottom padding">服务流程</view>
			<image src="../../static/image/serve_step.png" mode=""></image>
		</view>

		<view class="margin-top bg-white">
			<view class="u-border-bottom padding">商品详情</view>
			<view v-for="(item,index) in goodsImage" :key="index">
				<image :src="item" mode="widthFix" style="width: 100%;"></image>
			</view>
		</view>

		<view class="bottomButton">
			<button class="flex-sub bg-yellow text-white" @click="gotoPayPage">立即购买</button>
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
				goodsId: "",
				address: "",
				topPiclist: [],
				goodsImage: [],
				goodsDtatil: {},
				orderType: ""
			}
		},
		onLoad(options) {
			this.goodsId = options.goodsId;
			this.address = options.address;
			this.orderType = options.orderType;
			this.requestDoodsDetail();
		},
		methods: {

			//获取商铺列表
			requestDoodsDetail() {

				this.$http.get(this.baseUrl,
					'/clinet/shopgoods/getById', {
						id: this.goodsId
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {

						this.goodsDtatil = res.data;

						var tempList = [];
						var list = res.data.topPicUrl.split(",");
						for (var i = 0; i < list.length; i++) {
							tempList.push(this.baseUrl + list[i])
						}

						this.goodsImage = tempList;

						if (this.goodsImage.length > 3) {
							this.topPiclist = this.goodsImage.slice(0, 2);
						} else {
							this.topPiclist = this.goodsImage;
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

			//跳转支付页面
			gotoPayPage() {
				uni.navigateTo({
					url: './pay?orderType=' + this.orderType + '&address=' + this.address + '&goods=' + JSON.stringify(this.goodsDtatil),
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
