<template>
	<view>
		<!-- 订单状态分类 -->
		<view class="bg-white solid-top">
			<scroll-view scroll-x class="bg-white nav">
				<view class="flex text-center">
					<view class="cu-item flex-sub" :class="item.ostatus==statusCur?'text-orange cur':''" v-for="(item,index) in statusList"
					 :key="index" @tap="statusSelect" :data-id="item.ostatus">
						{{item.status}}
					</view>
				</view>
			</scroll-view>
		</view>
		
		<view class="padding-tb">
			<!-- <view >
				<view class="solid-bottom bg-white text-center text-xxl padding">
					<text class="margin-right">暂无订单</text>
					<text class=" cuIcon-roundcheckfill text-green"></text>
				</view>
			</view> -->
			
			<view class="cu-list menu-avatar card-menu "  v-for="(item,index) in 6" :key="index" :style="[{animation: 'show ' + ((index+1)*0.2+1) + 's 1'}]">
				<view class="cu-item  " style="padding-right: 10px;">
					<view class="cu-avatar radius lg" style="background-image:url(https://www.bmw.com.cn/content/dam/bmw/marketCN/common/all-models/m-series/m4-coupe/2020/20201118/1680756/kv.jpg/_jcr_content/renditions/cq5dam.resized.img.1680.large.time1605679494077.jpg);"></view>
					<view class="content">
						<view class="text-mauve">
							<view class="text-cut text-bold">宝马 M4 雷霆版</view>
						</view>
						<view class="text-gray text-sm flex">
							<view class="text-cut">iDrive 全时四驱 竞技悬挂 赛用刹车套件(碳陶瓷)</view>
						</view>
					</view>
					<view class="">
						<view class=" ">¥<text class="text-lg text-bold" style="padding: 0 2rpx;">5500</text>.00</view>
					</view>
				</view>
				<view class="flex justify-around bg-white padding-sm">
					<button class="cu-btn line-black round  shadow  margin-lr-xs" >退换/售后</button>
					<button class="cu-btn line-black round  shadow  margin-lr-xs" >订单评价</button>
					<button class="cu-btn line-green round  shadow  margin-lr-xs" >确认收货</button>
				</view>
			</view>

			<!-- 点击加载更多 -->
			<view class="cu-load text-gray text-bold" :class="isOver?'over':''" >{{isOver?'':'点击加载更多'}}</view>
		</view>

	</view>
</template>

<script>
	const app = getApp();
	const GD = app.globalData;
	export default {
		data() {
			return {
				curType: null,
				searchKeys: '',
				statusList: [{
						status: '未接单',
						ostatus: '1'
					},
					{
						status: '已接单',
						ostatus: '3'
					},
					{
						status: '服务中',
						ostatus: '4'
					},
					{
						status: '已完成',
						ostatus: '5'
					},
				],
				statusCur: 1,
				scrollLeft: 0,
				curPage: 1,
				isOver: true,
			};
		},
		onLoad() {
			// this.timeNow = this.$u.timeFormat(this.timestamp, 'yyyy年mm月dd日');
		},
		onReady() {
		
		},
		methods: {
			// 订单状态选择
			statusSelect(e) {
				this.statusCur = e.currentTarget.dataset.id;
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
				// 重新请求列表
			},
		},

	};
</script>

<style>
	page {
		background: #F1F1F1;
	}
</style>
