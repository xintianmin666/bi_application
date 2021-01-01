<template>
	<view>
		<u-toast ref="uToast" />
		
		<!-- 轮播图 -->
		<u-swiper :list="headerlist" height="300"></u-swiper>
        <!-- scroll tips -->
		<view class=" solid-top">
			<u-notice-bar mode="horizontal" :list="scrollTips" speed="80" type="primary"></u-notice-bar>
		</view>
		<view class="cu-list grid col-4 no-border margin-tb-sm">
			<view class="cu-item" v-for="(item,index) in cuIconList" :key="index" @click="itemClick(index)">
				<view class="cu-avatar xl round" :style="item.sty"></view>
				<text>{{item.name}}</text>
			</view>
		</view>

		<!-- 快捷功能 -->
		<view class="cu-bar  bg-white solid-top" style="min-height: 40px;">
			<view class="action">
				<text class="cuIcon-titles text-orange"></text>
				<text class="text-xl">便捷服务</text>
			</view>
		</view>
		<view class=" margin flex justify-between">
			<view class=" fncMod solid" :style="[{animation: 'show ' + ((index+1)*0.2+1) + 's 1'}]" v-for="(item,index) in groupC"
			 :key="index">
				<image src="/static/image/servise_img.png" class="radius imgH" mode="aspectFit"></image>
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
				groupC: [{
						name: '功能mark A',
						title: 'A级功能',
						icon: 'ticket'
					},
					{
						name: '功能mark B',
						title: 'B级功能',
						icon: 'ticket'
					}
				],
				scrollTips: ['最新活动：热烈祝贺用车无忧小程序成功上线！'],
				cuIconList: [{
					cuIcon: 'flashlightopen',
					color: 'red',
					sty:'background-image:url(/static/image/icon_nav1.png);',
					name: '车辆清洗'
				}, {
					cuIcon: 'magic',
					color: 'orange',
					sty:'background-image:url(/static/image/icon_nav2.png);',
					name: '车辆保养'
				}, {
					cuIcon: 'repair',
					color: 'yellow',
					sty:'background-image:url(/static/image/icon_nav3.png);',
					name: '车辆维修'
				}, {
					cuIcon: 'text',
					color: 'olive',
					sty:'background-image:url(/static/image/icon_nav4.png);',
					name: '车辆年审'
				}, {
					cuIcon: 'taxi',
					color: 'cyan',
					sty:'background-image:url(/static/image/icon_nav5.png);',
					name: '车辆保险'
				}, {
					cuIcon: 'newsfill',
					color: 'blue',
					sty:'background-image:url(/static/image/icon_nav6.png);',
					name: '学驾驶'
				}, {
					cuIcon: 'service',
					color: 'purple',
					sty:'background-image:url(/static/image/icon_nav7.png);',
					name: '一键救援'
				}],
				headerlist: []
			}
		},
		onLoad() {
			this.getHeaderList()
		},
		methods: {

			getHeaderList() {
				this.$http.get(GD.testUrl,
					'/clinet/platformAds/getByPosition', {
						position: "homePageAds",
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						var picList = res.data[0].picurl.split(",");
						var linkList = res.data[0].linkurl.split(",");
						
						for (var i = 0; i < picList.length; i++) {
							this.headerlist.push({
								image: GD.testUrl + picList[i],
								linkurl:linkList[i]
							});
						}
						
						console.log(this.headerlist);
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

			itemClick(index) {
				if (index == 0 || index == 1 || index == 2 || index == 3) {
					uni.navigateTo({
						url: './maintenance?index=' + (index + 1),
					});
				} else if (index == 4) {
					uni.navigateTo({
						url: './insurance/insurance',
					});
				} else if (index == 5) {
					uni.navigateTo({
						url: './drivingLicense/drivingLicense',
					});
				} else if (index == 6) {
					uni.navigateTo({
						url: './oneKeyRescue/oneKeyRescue',
					});
				}
				// else if (index == 7) {

				// } else if (index == 8) {
				// 	uni.navigateTo({
				// 		url: './scrap/scrap',
				// 	});
				// }

			}
		}
	}
</script>

<style>
	.grid-text {
		font-size: 28rpx;
		margin-top: 10rpx;
		color: #909399;
	}
    .cu-avatar.xl{
        width: 80rpx;
        height: 80rpx;
        margin: 0 auto;
        background-color: #FFFFFF;
    }

	.fncMod {
		width: 45%;
	}
	.imgH{
		height: 180rpx; 
	}
</style>
