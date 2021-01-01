<template>
	<view>
		<u-toast ref="uToast" />

		<view class="bg-white padding-sm">
			<u-search placeholder="输入地址、店名、服务进行搜索" shape="square" v-model="keyword" height="70" @custom="searchKeyword()"></u-search>
		</view>

		<!-- <u-swiper :list="headerlist" height="300"></u-swiper> -->

		<u-tabs-swiper ref="tabs" active-color="#f29100" :list="tabList" :current="current" @change="tabsChange" :is-scroll="false"
		 swiperWidth="750"></u-tabs-swiper>

		<swiper style="height: 100vh;" :current="swiperCurrent" @transition="transition" @animationfinish="animationfinish">
			<swiper-item class="swiper-item" v-for="(item, index) in tabList" :key="index">
				<scroll-view scroll-y style="height: 100vh;width: 100%;" @scrolltolower="onreachBottom">

					<view class="store-warter" :style="[{animation: 'show ' + ((yndex+1)*0.2+1) + 's 1'}]" v-for="(ytem, yndex) in storeList"
					 :key="yndex">
						<!-- 微信小程序需要hx2.8.11版本才支持在template中引入其他组件，比如下方的u-lazy-load组件 -->
						<view class="flex u-border-bottom padding-sm" @click="checkShopDetail(ytem)">

							<u-lazy-load border-radius="10" :image="baseUrl + ytem.coverPic" :index="yndex" imgMode="scaleToFill" height="250"
							 style="min-width: 250rpx;"></u-lazy-load>

							<view class="padding-sm">
								<view class="store-shop">{{ ytem.name }}</view>
								<text>店铺评分：{{ytem.score}}</text>
								<u-rate :count="5" :current="ytem.score" :disabled="true" active-color="#ff9900" inactive-color="#b2b2b2"
								 gutter="20"></u-rate>
								<view class="store-title">{{ ytem.address }}</view>
								<view class="store-tag">
									<view v-if="ytem.selfShop == '1'" class="store-tag-owner">自营</view>
									<view v-if="ytem.selfShop == '2'" class="store-tag-owner">加盟店</view>
								</view>
							</view>
						</view>


						<view v-if="ytem.shopGoodsList && ytem.shopGoodsList.length > 2">
							<view class="flex align-center u-border-bottom margin-lr" v-for="(ztem, zndex) in ytem.shopGoodsList.slice(0,2)"
							 :key="zndex" @click="checkGoodsDetail(ztem,ytem)">

								<view class="text-center" style="width: 250rpx;">
									<view class="u-font-18 text-red">¥{{ztem.payPrice}}</view>
									<view style="text-decoration:line-through">门市价：{{ztem.price}}</view>
								</view>
								<view class="padding-sm">
									<view class="u-font-18">{{ztem.goodsName}}</view>
									<view>已售：{{ztem.saleNum}}</view>
								</view>

							</view>

							<view class="bg-white padding" @click="checkShopDetail(ytem)">查看店铺全部产品</view>
						</view>

						<view v-if="ytem.shopGoodsList && ytem.shopGoodsList.length < 3">
							<view class="flex align-center u-border-bottom margin-lr" v-for="(ztem, zndex) in ytem.shopGoodsList" :key="zndex"
							 @click="checkGoodsDetail(ztem,ytem)">

								<view class="text-center" style="width: 250rpx;">
									<view class="u-font-18 text-red">¥{{ztem.payPrice}}</view>
									<view style="text-decoration:line-through">门市价：{{ztem.price}}</view>
								</view>
								<view class="padding-sm">
									<view class="u-font-18">{{ztem.goodsName}}</view>
									<view>已售：{{ztem.saleNum}}</view>
								</view>

							</view>
						</view>

					</view>


				</scroll-view>
			</swiper-item>
		</swiper>

		<u-loadmore bg-color="rgb(240, 240, 240)" :status="loadStatus" @loadmore="requestShopList"></u-loadmore>
	</view>


</template>

<script>
	const app = getApp();
	const GD = app.globalData;
	export default {
		data() {
			return {
				baseUrl: GD.testUrl,
				index: "",
				keyword: "",
				headerlist: ['https://cdn.uviewui.com/uview/swiper/2.jpg'],
				current: 0,
				swiperCurrent: 0,
				tabList: [{
						name: '默认排序'
					},
					{
						name: '评价最高'
					},
					{
						name: '距离最近'
					}
				],
				loadStatus: 'loadmore',
				storeList: [],
				pageNum: 1,
				lonLat: ''

			}
		},
		onLoad(options) {
			this.index = options.index;

			var title = "";
			if (this.index == 1) {
				title = "洗车";
			} else if (this.index == 2) {
				title = "保养";
			} else if (this.index == 3) {
				title = "维修";
			} else if (this.index == 4) {
				title = "检测线";
			}
			uni.setNavigationBarTitle({
				title: title
			});

			this.requestShopList();

			var that = this;
			uni.getLocation({
				type: 'wgs84',
				geocode: true, //设置该参数为true可直接获取经纬度及城市信息
				success: function(res) {
					console.log(res)
					that.lonLat = res.latitude + ',' + res.longitude;
				},
				fail: function() {
					uni.showToast({
						title: '获取地址失败，将导致部分功能不可用',
						icon: 'none'
					});
				}
			});
		},


		methods: {

			tabsChange(index) {
				this.swiperCurrent = index;
				this.pageNum = 1;
				this.requestShopList()
			},
			// swiper-item左右移动，通知tabs的滑块跟随移动
			transition(e) {
				let dx = e.detail.dx;
				this.$refs.tabs.setDx(dx);
			},
			// 由于swiper的内部机制问题，快速切换swiper不会触发dx的连续变化，需要在结束时重置状态
			// swiper滑动结束，分别设置tabs和swiper的状态
			animationfinish(e) {
				let current = e.detail.current;
				this.$refs.tabs.setFinishCurrent(current);
				this.swiperCurrent = current;
				this.current = current;
			},

			searchKeyword() {
				this.pageNum = 1;
				this.requestShopList()
			},

			//获取商铺列表
			requestShopList() {

				this.$http.get(this.baseUrl,
					'/clinet/shop/list', {
						pageNum: this.pageNum,
						pageSize: 10,
						orderBy: this.swiperCurrent + 1,
						serviceType: this.index,
						lonLat: this.lonLat,
						keyWords: this.keyword
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {

						if (this.pageNum == 1) {
							this.storeList = res.rows;
						} else {
							this.storeList = this.storeList.concat(res.rows);
						}

						if (this.pageNum * 10 < res.total) {
							this.pageNum++;
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

			//商铺详情
			checkShopDetail(ytem) {
				uni.navigateTo({
					url: './shopDetail?shopDetail=' + JSON.stringify(ytem) + '&orderType=' + this.index,
				});
			},

			//商品详情
			checkGoodsDetail(ztem, ytem) {
				uni.navigateTo({
					url: './goodsDetail?goodsId=' + ztem.id + '&address=' + ytem.address + '&orderType=' + this.index,
				});
			},


			onPullDownRefresh() {
				//监听下拉刷新动作的执行方法，每次手动下拉刷新都会执行一次
				this.pageNum = 1;
				this.requestShopList()
				setTimeout(function() {
					uni.stopPullDownRefresh(); //停止下拉刷新动画
				}, 1000);
			},
			// scroll-view到底部加载更多
			onreachBottom() {
				this.loadStatus = 'loading';
				// 模拟数据加载
				setTimeout(() => {
					this.requestShopList();
					this.loadStatus = 'loadmore';
				}, 1000);
			},

		}
	}
</script>

<style>
	.swiper-item {
		height: 100%;
	}

	.store-warter {
		border-radius: 8px;
		margin: 10px;
		background-color: #ffffff;
		position: relative;
	}

	.store-title {
		font-size: 30rpx;
		margin-top: 5px;
		color: $u-main-color;
		word-break: break-all;
	}

	.store-tag {
		display: flex;
		margin-top: 5px;
	}

	.store-tag-owner {
		background-color: #fa3534;
		color: #ffffff;
		display: flex;
		align-items: center;
		padding: 4rpx 14rpx;
		border-radius: 50rpx;
		font-size: 20rpx;
		line-height: 1;
		margin: 2px;
	}


	.store-shop {
		font-size: 18px;
		margin-top: 5px;
	}
</style>
