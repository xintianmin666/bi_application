<template>
	<view>
		<view class="cu-bar bg-gradual-blue search">
			<view class="cu-avatar bg-white text-blue radius" @tap="simTabbar" data-target="tabbar"><text class="cuIcon-sort"></text></view>
			<view class="search-form radius ">
				<text class="cuIcon-search"></text>
				<input v-model="searchKeys" :adjust-position="false" type="text" placeholder="搜索商品" confirm-type="search"></input>
			</view>
			<view class="action" @tap="getSearch">
				<button class="cu-btn line-white ">搜索</button>
			</view>
		</view>
		<!-- 搜索框结束 -->
		<!-- 侧边模拟Tabbar -->
		<view class="cu-modal drawer-modal justify-start" :class="showKeys=='tabbar'?'show':''" @tap="hideSimTabbar">
			<!-- <view class="cu-dialog basis-lg" @tap.stop="" :style="[{top:CustomBar+'px',height:'calc(100vh - ' + CustomBar + 'px)'}]"> -->
			<view class="cu-dialog basis-lg" @tap.stop="">
				<view class="cu-list menu card-menu text-left margin-top-sm">
					<view class="cu-item margin-tb-sm radius arrow" v-for="(tbItem,tbIndex) in simTbList" :key="tbIndex" @tap="navTo(tbItem.url)">
						<view class="content">
							<view><text class=" padding-right-xs text-blue" :class="'cuIcon-'+tbItem.icon"></text>{{tbItem.label}}</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 战术分隔 嗝----------------------------------------------------------------------------------------------------------------------------------------------嗝  -->
		<view>
			<!-- 供应商展示 -->
			<!-- <view class="padding-tb-sm bg-white">
				<scroll-view scroll-x class="bg-white nav" style="white-space:nowrap" scroll-with-animation :scroll-left="scrollLeft">
					<view class="cu-item margin-bottom-lg text-center" style="height: auto;" :class="index==TabCur?'text-blue cur':''"
					 v-for="(item,index) in 10" :key="index" @tap="tabSelect"  :data-id="index">
						<view class="flex flex-direction">
							<image class="radius" src="https://img14.360buyimg.com/n0/jfs/t1/103681/20/4247/169984/5de5dc77E97e38335/1337fbb33bcf66ed.jpg" style="width:128rpx;height: 128rpx;"></image>
							<text>RAYS{{index+1}}店</text>
						</view>
					</view>
				</scroll-view>
			</view> -->
			<!-- 商品类型分类 下拉模块 -->
			<view class="solid-bottom bg-white">
				<u-dropdown ref="uDropdown" :border-bottom="true" :close-on-click-mask="false" active-color="#39b54a">
					<u-dropdown-item v-model="curType" title="商品分类" :options="goodsType" @change="selectChange"></u-dropdown-item>
					<u-dropdown-item v-model="curType" title="销量" :options="saleCounts" @change="selectChange"></u-dropdown-item>
					<u-dropdown-item v-model="curType" title="价格" :options="priceType" @change="selectChange"></u-dropdown-item>
					<u-dropdown-item v-model="curType" title="好评" :options="comentType" @change="selectChange"></u-dropdown-item>
				</u-dropdown>
			</view>
			<!-- 列表 -->
			<view class="padding-lr-xs padding-bottom padding-top-xs flex flex-wrap">
				<view class="shopItem padding-tb-sm bg-white margin-xs card-menu  radius shadow" v-for="(tbItem,tbIndex) in 10"
				 :key="tbIndex" @tap="navTo('goods')">
					<!-- <view class="">
						<text class=" padding-right-xs text-blue" :class="'cuIcon-'+tbItem.icon"></text>{{tbItem.label}}
					</view>
					<view> -->
					<view class="flex-direction padding-xs">
						<view class="text-center">
							<image class="radius" src="https://img14.360buyimg.com/n0/jfs/t1/103681/20/4247/169984/5de5dc77E97e38335/1337fbb33bcf66ed.jpg"
							 style="width:300rpx;height: 300rpx;"></image>
							<!-- <image class="" src="../../static/uview/common/logo.png" style="width:68px;height: 68px;"></image> -->
						</view>
						<view class="padding-top margin-left-sm">
							<view class="margin-bottom-xs">RAYS TE37轮毂/个</view>
							<view class="margin-bottom-xs">
								<text class="cu-tag line-red sm">包邮</text>
								<text class="cu-tag bg-red sm radius">运泰自营</text>
							</view>
							<view class="text-red align-center">¥<text class="padding-left-xs text-bold text-xxl">5500.00</text></view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 战术分隔 嗝 ----------------------------------------------------------------------------------------------------------------------------------------------嗝  -->
		<view class="cu-avatar lg round margin-left iconCart" @tap="navToCart">
			<view class="cu-tag badge">
				<block>4</block>
			</view>
			<text class="cuIcon-cart text-black"></text>
		</view>

	</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				CustomBar: this.CustomBar,
				showKeys: null,
				searchKeys: '',
				simTbList: [{
						label: '首页',
						url: '',
						icon: 'circle'
					},
					{
						label: '拍卖与招标',
						url: 'auctionAndBidding/auctionAndBidding',
						icon: 'rank'
					},
					{
						label: '个人中心',
						url: 'purchaseCmy/purchaseCmy',
						icon: 'settings'
					}
				],
				curType: null,
				goodsType: [{
					label: '汽车配件',
					value: 1
				}, {
					label: '汽车耗材',
					value: 2
				}, {
					label: '其他分类',
					value: 2
				}],
				saleCounts: [{
					label: '从低到高',
					value: 1
				}, {
					label: '从高到低',
					value: 2
				}],
				priceType: [{
					label: '从低到高',
					value: 1
				}, {
					label: '从高到低',
					value: 2
				}],
				comentType: [{
					label: '好评最多',
					value: 1
				}, {
					label: '好评最少',
					value: 2
				}],
				TabCur: 0,
				scrollLeft: 0
			}
		},
		onLoad() {
			this.checkAddress();
		},
		onReady() {

		},
		methods: {
			// 展开侧边模拟 tabbar 栏
			simTabbar(e) {
				this.showKeys = e.currentTarget.dataset.target
			},
			// 关闭侧边模拟 tabbar 栏
			hideSimTabbar(e) {
				this.showKeys = null
			},
			// 关键字搜索商品
			getSearch() {
				this.searchKeys.trim();
				if (this.searchKeys != '') {
					console.log('憨憨敬礼')
				} else {
					uni.showToast({
						title: '请输入有效的关键字',
						icon: 'none'
					})
				}
			},
			// tabbar路由跳转
			navTo(url) {
				this.hideSimTabbar();
				if (url == '') {
					return
				}
				let navToUrl = url == 'goods' ? './goodsDetail/goodsDetail' : url
				uni.navigateTo({
					url: navToUrl
				})
			},
			// 订单类型选择
			selectChange(e) {
				this.odType = e;

			},
			// 滚动列表选择（商家logo）
			tabSelect(e) {
				this.TabCur = e.currentTarget.dataset.id;
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
			},
			// 检查登录号是否有至少一个收货地址
			checkAddress() {
				uni.showModal({
					title: '您还未设置收货地址！',
					content: '是否前往设置？',
					confirmText: '立即前往',
					cancelText: '稍后设置',
					cancelColor: '#AAAAAA',
					success: function(res) {
						if (res.confirm) {
							uni.navigateTo({
								url: './newAddress/newAddress'
							})
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				})
			},
			// 跳转至 购物车页面
			navToCart(){
				uni.navigateTo({
					url:'./myCart/myCart'
				})
			}
		}
	}
</script>

<style scoped>
	page {
		background: #F1F1F1;
	}

	.search-form {
		background-color: #FFFFFF;
	}

	.shopItem {
		width: 46vw;
	}

	.iconCart {
		position: fixed;
		right: 5vw;
		top: 55vh;
		border: 1px solid #dcdcdc;
		background-color: rgba(255,255,255,.8);
		z-index: 999;
	}
</style>
