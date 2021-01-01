<template>
	<view>
		<!-- scroll tips -->
		<view class=" solid-top">
			<u-notice-bar mode="horizontal" :list="scrollTips" speed="80"></u-notice-bar>
		</view>
		<!-- Nav部分 -->
		<scroll-view scroll-x class="bg-white nav">
			<view class="flex text-center">
				<view class="cu-item text-lg flex-sub" :class="typeIndex==TabCur?'text-orange cur':''" v-for="(typeItem,typeIndex) in typePart"
				 :key="typeIndex" @tap="tabSelect" :data-id="typeIndex">
					{{typeItem.title}}
				</view>
			</view>
		</scroll-view>
		<!-- 类型分类 下拉模块 -->
		<view class="solid-bottom bg-white">
			<u-dropdown ref="uDropdown" :border-bottom="true" :close-on-click-mask="false" active-color="#39b54a">
				<u-dropdown-item v-model="curType" title="分类" :options="orderType" @change="selectChange"></u-dropdown-item>
			</u-dropdown>
		</view>
		<!-- 拍卖内容 -->
		<view v-if="TabCur==0">
			<!-- 列表 -->
			<view class="cu-list menu-avatar">
				<view class="cu-item margin-lr-sm margin-bottom-sm solid-top" v-for="(item,index) in 6" :key="index" @tap="navTo()">
					<view class="cu-avatar radius lg" style="background-image:url(https://www.bmw.com.cn/content/dam/bmw/marketCN/common/all-models/m-series/m4-coupe/2020/20201118/1680756/kv.jpg/_jcr_content/renditions/cq5dam.resized.img.1680.large.time1605679494077.jpg);"></view>
					<view class="content">
						<view class="text-mauve">
							<view class="text-cut">宝马 M4 雷霆版</view>
						</view>
						<view class="text-gray text-sm flex">
							<view class="text-cut">iDrive 全时四驱 竞技悬挂 赛用刹车套件(碳陶瓷)</view>
						</view>
					</view>
					<view class="action">
						<view class="text-grey text-xs">22:30</view>
						<view class="cu-tag radius bg-red sm">进行中</view>
					</view>
				</view>
			</view>

		</view>
		<!-- 招标内容 -->
		<view v-else>
			<!-- 列表 -->
			<view class="cu-list menu-avatar">
				<view class="cu-item margin-lr-sm margin-bottom-sm solid-top" v-for="(item,index) in 6" :key="index" @tap="navTo()">
					<view class="cu-avatar radius lg" style="background-image:url(https://www.bmw.com.cn/content/dam/bmw/marketCN/common/all-models/m-series/m4-coupe/2020/20201118/1680756/kv.jpg/_jcr_content/renditions/cq5dam.resized.img.1680.large.time1605679494077.jpg);"></view>
					<view class="content">
						<view class="text-mauve">
							<view class="text-cut">98#汽油 *50吨</view>
						</view>
						<view class="text-gray text-sm flex">
							<view class="text-cut">需要98#成品汽油50吨。</view>
						</view>
					</view>
					<view class="action">
						<view class="text-grey text-xs">22:30</view>
						<view class="cu-tag radius bg-green sm">已结束</view>
						<!-- 倒计时 -->
						<!-- <u-count-down autoplay :timestamp="86400" separator="colon" separator-size="28" separator-color="#606266"></u-count-down> -->
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
				TabCur: 0,
				scrollLeft: 0,
				typePart: [{
						title: '拍卖',
						type: 'auction'
					},
					{
						title: '招标',
						type: 'bidding'
					}
				],
				auctionList: [{
					cuIcon: 'rechargefill',
					name: '交保证金'
				}, {
					cuIcon: 'vipcard',
					name: '参与竞拍'
				}, {
					cuIcon: 'appreciatefill',
					name: '竞拍成功'
				}, {
					cuIcon: 'pay',
					name: '支付货款'
				}, {
					cuIcon: 'roundcheckfill',
					name: '交易完成'
				}],
				scrollTips: ['最新成交：2020款 BMW M4 Competition 雷霆版 以¥1800000.00的最终成交价成功售出！'],
				orderType:[{label: '进行中',value: 1},{label: '已完成',value: 2}],
				curType:null
			}
		},
		methods: {
			// 滚动列表选择（商家logo）
			tabSelect(e) {
				this.TabCur = e.currentTarget.dataset.id;
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
			},
			// 跳转至详情页
			navTo(){
				// 后期接入接口，根据传入的参数类型进行分类跳转
				let url = this.TabCur==0?'../aucAndBidDetail/auction':'../aucAndBidDetail/bidding'
				uni.navigateTo({
					url:url
				})
			},
			// 订单类型选择
			selectChange(e) {
				console.log(e)
			},
		}
	}
</script>

<style>

</style>
