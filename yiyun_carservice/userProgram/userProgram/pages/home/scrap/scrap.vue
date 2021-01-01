<template>
	<view>
		<u-toast ref="uToast" />
		
		<u-swiper :list="list" mode="none"></u-swiper>

		<view class="padding-lr-lg bg-white">
			<view class="inputView">
				<text>联系人</text>
				<input type="text" value="" placeholder="姓名" />
			</view>
			<view class="inputView">
				<text>电话</text>
				<input type="text" value="" placeholder="请输入电话号码" />
			</view>
			<view class="inputView">
				<text>车牌号</text>
				<input type="text" value="" placeholder="例如:皖B88888" />
			</view>
			<view class="inputView">
				<text>地址</text>
				<text>{{curLocation.address}}</text>
			</view>
		</view>
		<view>
			<map style="width: 100%; height: 300px;" :markers="curLocation" :latitude="curLocation.latitude" :longitude="curLocation.longitude">
			</map>
		</view>
		
		<view class="padding flex flex-direction margin-top">
			<button class="cu-btn bg-orange lg" >预约报废</button>
		</view>
		
		
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: ["https://cdn.uviewui.com/uview/swiper/1.jpg"],
				checked: false,
				curLocation: [{
					latitude: 0,
					longitude: 0,
					address: "",
					iconPath: '../../../static/image/location.png',
					width: 32,
					height: 32
				}],
			}
		},
		onLoad() {

			uni.getLocation({
				type: 'wgs84',
				geocode: true, //设置该参数为true可直接获取经纬度及城市信息
				success: function(res) {
					console.log(res)
					that.curLocation = res;
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
