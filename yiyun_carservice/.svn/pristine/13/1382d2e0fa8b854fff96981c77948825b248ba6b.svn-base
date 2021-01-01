<template>
	<view>
		<!-- 订单类型分类 下拉模块 -->
		<view class="solid-bottom solid-top bg-white">
			<u-dropdown ref="uDropdown" :border-bottom="true" :close-on-click-mask="false" active-color="#39b54a">
				<u-dropdown-item v-model="curType" title="订单类型" :options="orderType" @change="selectChange"></u-dropdown-item>
			</u-dropdown>
		</view>
		<!-- 订单时间选择日期选择-日历 -->
		<view class="text-center bg-white padding-tb-sm text-green text-bold text-lg "@tap='searchDateDisp=!searchDateDisp'>
			查询日期：{{searchDate}}
		</view>
		<u-calendar v-model="searchDateDisp" ref="calendar" @change="dateChange" mode="date" active-bg-color="#19be6b"
		 btn-type="success" :safe-area-inset-bottom="true">
		</u-calendar>
		<!-- 订单列表 -->
		<view v-if="orderType.length<=0">
			<view class="solid-bottom bg-white text-center text-xxl padding">
				<text class="margin-right">暂无订单</text>
				<text class=" cuIcon-roundcheckfill text-green"></text>
			</view>
		</view>
		<view class="margin bg-white shadow cu-list card-menu" v-for="(item,index) in orderType" :style="[{animation: 'show ' + ((index+1)*0.2+1) + 's 1'}]"
		 :key="index" @tap="goOrder(item)">
			<view class="flex align-center solid shadow padding-sm">
				<!-- 左 -->
				<view class=" margin-right margin-left-xs">
					<view class="cu-avatar radius bg-blue lg">
						<text>{{item.label.substr(0,1)}}</text>
					</view>
				</view>
				<!-- 右 -->
				<view class="">
					<view class=" text-sm margin-bottom-xs">
						<text class=" margin-right-sm">下单时间：<text class="text-blue">2020-12-10 15:34</text></text>
					</view>
					<view class=" text-sm margin-bottom-xs">
						<text class=" margin-right-sm">用户：18226714510</text>
					</view>
					<view>车牌号：<text class="text-blue text-bold">皖B-99999</text></view>
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
				curType: null,
				orderType: [{
						label: '洗车',
						value: 'washCar',
					},
					{
						label: '保养',
						value: 'mainTenance',
					},
					{
						label: '维修',
						value: 'repair',
					},
					{
						label: '检测线',
						value: 'inspectedLine',
					},
					{
						label: '驾培',
						value: 'driverLicense',
					},
					{
						label: '救援',
						value: 'callHelp',
					}
				],
				searchDateDisp:false,
				searchDate:''
			};
		},
		onLoad(options) {
			if(options){
				console.log('查看类型',options.type)
				let actitle = '';
				actitle = options.type == 'all'?'全部订单':'已结算订单'
				uni.setNavigationBarTitle({
				    title: actitle
				});
			}
		},
		methods: {
			// 类型选择
			selectChange(e) {
				console.log('订单类型：',e)
			},
			// 日期变更
			dateChange(e) {
				this.searchDate = e.result;
				this.$u.toast('已选择：' + this.searchDate);
			},
			// 初始化当前时间选择
			initTimes() {
				var min = new Date();
				var max = new Date(min);
				max.setDate(min.getDate() + 20);
				this.calendarMin = this.$u.timeFormat(min.getTime(), 'yyyy-mm-dd')
				this.calendarMax = this.$u.timeFormat(max.getTime(), 'yyyy-mm-dd')
			},
			// 选择订单进入查看
			goOrder(item) {
				let orderUrl = '../../orders/finishOrder';
				uni.navigateTo({
					url: orderUrl + '?type=' + item.label
				})
			},
		}
	};
</script>

<style>
	page {
		background: #F1F1F1;
	}
</style>
