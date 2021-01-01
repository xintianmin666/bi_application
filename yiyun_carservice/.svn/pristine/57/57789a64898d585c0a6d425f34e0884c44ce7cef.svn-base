<template>
	<view>
		<view>
			<view class="cu-bar bg-white solid-top solid-bottom margin-top-xs">
				<view class="action text-xl">
					<text class="cuIcon-title text-orange "></text> 订单类型：<text class="text-orange text-bold">普通招标</text>
				</view>
				<view class="action">
					<button class="cu-btn bg-yellow ">招标中</button>
				</view>
			</view>
			<!-- 订单信息 -->
			<view class="margin padding-sm bg-white radius">
				<view class="cu-form-group ">
					<text>招标名称：</text>
					<text class=" text-bold">成品98#汽油</text>
				</view>
				<view class="cu-form-group ">
					<text>招标数量：</text>
					<text class=" text-bold">50*吨</text>
				</view>
				<view class="cu-form-group ">
					<text>招标起始时间：</text>
					<text class=" text-bold">2020-12-23 12:12</text>
				</view>
				<view class="cu-form-group ">
					<text>招标结束时间：</text>
					<text class=" text-bold">2020-12-31 12:12</text>
				</view>
				<view class="cu-form-group ">
					<text>联系电话：</text>
					<text class="text-blue text-bold" @tap="callUser('18226714510')"><text class="cuIcon-mobilefill text-blue"></text>18226714510</text>
				</view>
				<view class="cu-form-group ">
					<text class="minWd">招标需求：</text>
					<text class="padding-tb-sm text-bold">1. 拍卖开始后也可以支付保证金；
						2. 建议在开拍一天前支付保证金报名，以免错过拍卖；
						3. 如果没有获拍，保证金将在24小时内原路返还，具体到账时间需要以银行信息为准。</text>
				</view>

			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		onLoad(options) {


		},
		methods: {
			// 联系用户
			callUser(tel) {
				uni.makePhoneCall({
					phoneNumber: tel //仅为示例
				});
			},
		}
	}
</script>

<style>
	page {
		background: #F1F1F1;
	}

	.minWd {
		min-width: 90px;
	}
</style>
