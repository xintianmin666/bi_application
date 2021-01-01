<template>
	<view class="solid-top solid-green tbox">
		<view class="flex user-box padding-lr-lg padding-bottom-lg padding-top">
			<view class="u-m-r-10">
				<u-avatar src="../../static/image/waitPay.png" size="140"></u-avatar>
			</view>
			<view class="u-flex-1 padding-left">
				<view class="u-font-18 u-p-b-20">系统自分配A1</view>
				<view class="u-font-14 u-tips-color">所属商家：<text class="text-blue">运泰自营</text></view>
			</view>
			<view class="u-m-l-10 u-p-10" @tap="navTo('userInfo')">
				<u-icon name="arrow-right" color="#dcdcdc" size="44"></u-icon>
			</view>
		</view>

		<view class="cu-list menu sm-border  margin-top">
			<!-- <view class="cu-item arrow" @tap="goOrdeaAll('all')">
				<view class="content">
					<text class="cuIcon-list text-green"></text>
					<text class="text-grey">全部订单</text>
				</view>
			</view> -->
			<view class="cu-item arrow" v-for="(fncItem,fncIndex) in fncList" :key="fncIndex" @tap="navTo(fncItem.fncName)">
				<view class="content">
					<text :class="'cuIcon-'+fncItem.icon+' text-'+colorList[fncIndex]"></text>
					<text class="text-grey">{{fncItem.label}}</text>
				</view>
			</view>
		</view>


		<view class="footer text-center">
			<text class="text-blue" @tap="callService">客服电话：0553-3911111</text>
			<view class="text-gray text-sm">安徽宜运信息技术服务有限公司提供技术支持</view>
		</view>

	</view>
</template>

<script>
	const app = getApp();
	const GD = app.globalData;
	export default {
		data() {
			return {
				colorList:['blue','orange','yellow','olive','green','cyan','red','pruple','mauve'],
				type: 'all',
				fncList: [{
						label: '我的供货商',
						fncName: 'mySuppliers',
						icon: 'deliver_fill'
					},
					{
						label: '发布需求单',
						fncName: 'issueDemand',
						icon: 'writefill'
					},
					{
						label: '我的订单',
						fncName: 'myOrders',
						icon: 'form'
					},
					{
						label: '我的地址',
						fncName: 'myAddress',
						icon: 'activity'
					},
					{
						label: '我的优惠券',
						fncName: 'myCoupon',
						icon: 'ticket'
					},
					{
						label: '我的购物车',
						fncName: 'myCart',
						icon: 'cart'
					},
					{
						label: '我的拍卖',
						fncName: 'myAUandBI',
						icon: 'squarecheckfill'
					},
					{
						label: '我的收藏',
						fncName: 'myFavor',
						icon: 'favor'
					}
				]

			}
		},
		onLoad() {

		},
		onReady() {

		},
		methods: {
			// 拨打客服电话
			callService() {
				uni.makePhoneCall({
					phoneNumber: '0553-3911111' //
				});
			},
			// 跳转至设置界面
			goSetting() {
				uni.navigateTo({
					url: './setting/setting'
				})
			},
			// 跳转
			navTo(fncName) {
				// this.createOrder();
				let nvUrl = '';
				switch (fncName) {
					case 'userInfo':
						nvUrl = "../myInfo/myInfo";
						break;
					case 'mySuppliers':
						nvUrl = "../mySuppliers/mySuppliers";
						break;
					case 'issueDemand':
						nvUrl = "../issueDemand/issueDemand";
						break;
					case 'myOrders':
						nvUrl = "../myOrders/myOrders";
						break;
					case 'myAddress':
						nvUrl = "../myAddress/myAddress";
						break;
					case 'myCoupon':
						nvUrl = "../myCoupon/myCoupon";
						break;
					case 'myCart':
						nvUrl = "../myCart/myCart";
						break;
						
				}
				uni.navigateTo({
					url:nvUrl
				})
			},
			// 动态下单假数据
			createOrder() {
				this.$http.post('http://192.168.2.106:8001',
					'/order/createOrder', {
						userPhone: 18226714510,
						userId: "666",
						orderType: 3,
						shopId: '2',
						userName: '强袭高达',
						carNo: '皖B-00002'
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);

				}).catch(err => {
					//异常回调
					console.log('请求失败', err);

				});
			}

		}
	}
</script>

<style>
	page {
		background-color: #ededed;
	}

	.user-box {
		background-color: #fff;
	}

	.footer {
		margin-top: 8vh;
	}

	.img_icon {
		width: 68rpx;
		height: 68rpx;
	}
</style>
