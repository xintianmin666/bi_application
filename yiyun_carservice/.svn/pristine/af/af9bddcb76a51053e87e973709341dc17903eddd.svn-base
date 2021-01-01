<template>
	<view>
		<view class="padding-tb">
			<view class="card-menu  cu-list shadow margin-lr bg-white " v-for="(couItem,couIndex) in couponList" :key="couIndex">
				<view class="text-center" :class="couItem.from=='plat'?'bg-red':'bg-orange'">{{couItem.from=='plat'?'平台券':'商家券'}}</view>
				<view class="padding-lr">
					<view class="flex justify-between align-center margin-tb-sm">
						<view class=" text-red " style="min-width: 190rpx;">
							<text class="text-xxl margin-left-xs text-bold padding-lr-xs">{{couItem.price}}</text>元
						</view>
						<view class="text-sm text-gray padding-tb-xs">
							<view>使用条件：在运泰自营店铺购买指定商品方可使用。</view>
							<view class=" text-right text-orange">有效期至 {{couItem.date}}</view>
						</view>
					</view>
				</view>
			</view>
			
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				couponList:[
					{from:'plat',price:200,date:'2020-12-31 23:59'},
					{from:'shop',price:100,date:'2020-12-26 23:59'},
					{from:'shop',price:50,date:'2020-12-25 23:59'},
					{from:'plat',price:100,date:'2020-12-31 23:59'}
				]
			}
		},
		onLoad() {

		},
		methods: {

		}
	}
</script>

<style>
	page {
		background: #F1F1F1;
	}
</style>
