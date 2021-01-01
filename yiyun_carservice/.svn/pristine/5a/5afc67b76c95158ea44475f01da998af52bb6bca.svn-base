<template>
	<view>
		<view class="">
			
			<view class="padding-tb bg-white solid-top">
				<view class=" padding-lr padding-tb-sm radius solid-bottom" v-for="(res, index) in siteList" :key="index">
					<view class="flex justify-between align-center">
						<view>
							<view class="text-bold text-black margin-bottom-sm">
								<text class="padding-right">{{ res.name }}</text>
								<text>{{ res.phone }}</text>
							</view>
							<view >广东省深圳市宝安区 自由路66号</view>
						</view>
						<view>
							<text class="cuIcon-post margin-lr-xs text-gray text-bold" style="font-size: 40rpx;"></text>
						</view>
					</view>
				</view>
			</view>
			<!-- 新建收货地址 btn -->
			<view class="padding flex flex-direction margin-top" @tap="toAddSite">
				<button class="cu-btn bg-red lg shadow"><text class="cuIcon-add margin-right-xs text-bold" style="font-size: 40rpx;"></text>新建收货地址</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				siteList: []
			};
		},
		onLoad() {
			this.getData();
		},
		methods: {
			getData() {
				this.siteList = [{
						id: 1,
						name: '游X',
						phone: '183****5523',
						tag: [{
								tagText: '默认'
							},
							{
								tagText: '家'
							}
						],
						site: '广东省深圳市宝安区 自由路66号'
					},
					{
						id: 2,
						name: '李XX',
						phone: '183****5555',
						tag: [{
							tagText: '公司'
						}],
						site: '广东省深圳市宝安区 翻身路xx号'
					},
					{
						id: 3,
						name: '王YY',
						phone: '153****5555',
						tag: [],
						site: '广东省深圳市宝安区 平安路13号'
					}
				];
			},
			toAddSite() {
				uni.$once(`changeAddressLit`, data => {
					// 每次订单状态改变后需重新请求一次
					this.siteList.push(data);
				})
				uni.navigateTo({
					url: '../newAddress/newAddress'
				});
			}
		},
		onBackPress() {
			// console.log('取消-返回')
			// uni.$off('changeAddressLit');
		}
	};
</script>

<style scoped>
	page {
		background: #F1F1F1;
	}
</style>
