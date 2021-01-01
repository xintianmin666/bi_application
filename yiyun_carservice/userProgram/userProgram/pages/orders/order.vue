<template>
	<view>
		<u-toast ref="uToast" />

		<view class="wrap">
			<view class="u-tabs-box">
				<u-tabs-swiper activeColor="#f29100" ref="tabs" :list="tabList" :current="current" @change="change" :is-scroll="false"
				 swiperWidth="750"></u-tabs-swiper>
			</view>
			<swiper class="swiper-box" :current="swiperCurrent" @transition="transition" @animationfinish="animationfinish">
				<swiper-item class="swiper-item" v-for="(item, index) in tabList" :key="index">
					<scroll-view v-if="orderList.length > 0" scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<view class="page-box">
							<view class="order" v-for="(ytem, yndex) in orderList" :key="yndex" :style="[{animation: 'show ' + ((yndex+1)*0.2+1) + 's 1'}]"
							 @tap="checkOrderDetail(ytem)">
								<view class="top">
									<view class="left">
										<u-icon name="home" :size="30" color="rgb(94,94,94)"></u-icon>
										<view class="store">{{ ytem.shopName?ytem.shopName:"店铺名称" }}</view>
										<u-icon name="arrow-right" color="rgb(203,203,203)" :size="26"></u-icon>
									</view>

									<view v-if="item.orderType == 1 || item.orderType == 4">
										<view class="right" v-if="ytem.orderStatus == 1">未支付</view>
										<view class="right" v-if="ytem.orderStatus == 2">已取消</view>
										<view class="right" v-if="ytem.orderStatus == 3">已支付</view>
									</view>
									<view v-if="item.orderType == 2 || item.orderType == 3">
										<view class="right" v-if="ytem.orderStatus == 1">已下单</view>
										<view class="right" v-if="ytem.orderStatus == 2">已取消</view>
										<view class="right" v-if="ytem.orderStatus == 3">已接单</view>
										<view class="right" v-if="ytem.orderStatus == 4">服务中</view>
										<view class="right" v-if="ytem.orderStatus == 5">已完成</view>
									</view>

								</view>

								<view class="item">
									<view class="left">
										<image :src="baseUrl + ytem.coverPic" mode="aspectFill"></image>
									</view>

									<view class="right">

										<view v-if="item.orderType == 1 || item.orderType == 4">
											<view>订单编号:{{ytem.orderCode }}</view>
											<view>订单项目:{{ytem.orderTypeName }}</view>
											<view class="text-red text-lg">￥{{ytem.collectAmount}}</view>
											<view>下单时间:{{ ytem.createTime }}</view>
											<view v-if="ytem.orderStatus == 1" class="evaluate btn" @tap.stop="payOrder(ytem)">去支付</view>
											<view v-if="ytem.orderStatus == 5" class="evaluate btn">已完成</view>
										</view>

										<view v-if="item.orderType == 2 || item.orderType == 3">
											<view>订单编号:{{ytem.orderCode }}</view>
											<view>订单项目:{{ytem.orderTypeName }}</view>
											<view class="text-red text-lg" v-if="ytem.orderStatus == 1 || ytem.orderStatus == 3">订单价格等待确认</view>
											<view class="text-red text-lg" v-if="ytem.orderStatus == 4 || ytem.orderStatus == 5">￥{{ytem.orderAmount}}</view>
											<view>下单时间:{{ ytem.createTime }}</view>
											<view>到店时间:{{ ytem.bookTime }}</view>
										</view>

									</view>
								</view>
							</view>
						</view>
					</scroll-view>
					
					<scroll-view v-if="orderList.length == 0" scroll-y style="height: 100%;width: 100%;">
						<view class="page-box padding">
							<view>
								<view class="centre">
									<image src="https://cdn.uviewui.com/uview/template/taobao-order.png" mode=""></image>
									<view class="explain">
										您还没有相关的订单
										<view class="tips">可以去看看有那些想买的</view>
									</view>
									<view class="btn">随便逛逛</view>
								</view>
							</view>
						</view>
					</scroll-view>
				</swiper-item>

			</swiper>
			
			<u-loadmore :status="loadStatu" bgColor="#f2f2f2" v-show="isLoadMore"></u-loadmore>
			
		</view>

		<!-- 支付码modal框 -->
		<view class="cu-modal" :class="showPwd==true?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white justify-end">
					<view class="content">输入支付密码</view>
					<view class="action" @tap="iptCodeCancel">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="padding">
					<view class="cu-form-group">
						<view class="title">支付密码：</view>
						<input placeholder="请输入" v-model="payPassWord" maxlength="6" name="input" type="password"></input>
					</view>
				</view>
				<view class="cu-bar bg-white">
					<view class="action margin-0 flex-sub text-red solid-left" @tap="iptCodeCancel">取消</view>
					<view class="action margin-0 flex-sub  solid-left" @tap="iptCodeConfirm">确定</view>
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
				baseUrl: GD.testUrl,
				pageNo: 1,
				orderList: [],
				tabList: [{
						name: '洗车服务',
						orderType: 1,
					},
					{
						name: '保养服务',
						orderType: 2,
					},
					{
						name: '维修服务',
						orderType: 3,
					},
					{
						name: '检测线',
						orderType: 4,
					}
				],
				current: 0, //当前状态页
				swiperCurrent: 0, //选中状态页
				loadStatu: 'loadmore',
				isLoadMore:false,
				showPwd: false,
				payPassword: '',
			}
		},
		onLoad() {
			this.requestOrderList();
		},

		onShow() {
			this.pageNo = 1;
			this.requestOrderList();
		},

		methods: {

			// tab栏切换
			change(index) {
				this.swiperCurrent = index;
				this.pageNo = 1;
				this.requestOrderList(index);
			},


			// swiper-item左右移动，通知tabs的滑块跟随移动
			transition(e) {
				let dx = e.detail.dx;
				this.$refs.tabs.setDx(dx);
			},
			// 由于swiper的内部机制问题，快速切换swiper不会触发dx的连续变化，需要在结束时重置状态
			// swiper滑动结束，分别设置tabs和swiper的状态
			animationfinish(e) {
				let current = e.detail.current;
				this.$refs.tabs.setFinishCurrent(current);
				this.swiperCurrent = current;
				this.current = current;
			},


			requestOrderList() {

				this.$http.post(GD.goodsUrl,
					'/order/getOrderPage', {
						pageNo: this.pageNo,
						pageSize: 10,
						userId: uni.getStorageSync('storage_session').id,
						orderStatus: '',
						orderType: this.swiperCurrent + 1
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						if(this.pageNo == 1){
							this.orderList = res.data.list;
						}else{
							this.orderList = this.orderList.concat(res.data.list);
						}
						
						if (this.pageNo < res.data.pages) {
							this.isLoadMore = false
						}else{
							this.isLoadMore = true;
							this.loadStatus='nomore'
						}
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

			//查看订单详情
			checkOrderDetail(ytem) {
				uni.navigateTo({
					url: './orderDetail?orderCode=' + ytem.orderCode + "&orderType=" + (this.swiperCurrent + 1),
				})
			},

			//洗车和检测线支付
			payOrder(item) {
				if (item.collectAmount < 0) {
					this.showPwd = true;
					this.payMoney(item.orderCode)
				} else {
					this.payMoney(item.orderCode)
				}
			},

			payMoney(orderCode) {
				var that = this;
				this.$http.post(GD.goodsUrl,
					'/order/payOrder', {
						payPassword: that.payPassword, //支付密码
						orderCode: orderCode, //订单号
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						that.$util.payWx(res, "/pages/orders/order", orderCode)
						that.queryWeChatOrder(orderCode)
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

			queryWeChatOrder(orderCode) {
				this.$http.post(GD.goodsUrl,
					'/order/queryWeChatOrder', {
						orderCode: orderCode, //订单号
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {

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

			//弹出框取消支付
			iptCodeCancel() {
				this.showPwd = false;
				this.payPassWord = '';
			},

			//加载更多
			reachBottom() {
				// 模拟数据加载
				setTimeout(() => {
					if(!this.isLoadMore){
						this.isLoadMore = true;
						this.pageNo +=1;
						this.requestOrderList();
					}	
				}, 1000);
			},

			onPullDownRefresh() {
				//监听下拉刷新动作的执行方法，每次手动下拉刷新都会执行一次
				this.pageNo = 1;
				this.requestOrderList()
				setTimeout(function() {
					uni.stopPullDownRefresh(); //停止下拉刷新动画
				}, 1000);
			},

		}
	}
</script>

<style lang="scss" scoped>
	.order {
		width: 710rpx;
		background-color: #ffffff;
		margin: 20rpx auto;
		border-radius: 20rpx;
		box-sizing: border-box;
		padding: 20rpx;
		font-size: 28rpx;

		.top {
			display: flex;
			justify-content: space-between;

			.left {
				display: flex;
				align-items: center;

				.store {
					margin: 0 10rpx;
					font-size: 32rpx;
					font-weight: bold;
				}
			}

			.right {
				color: $u-type-warning-dark;
			}
		}

		.item {
			display: flex;
			margin: 20rpx 0 0;

			.left {
				margin-right: 20rpx;

				image {
					width: 200rpx;
					height: 200rpx;
					border-radius: 10rpx;
				}
			}

			.right {
				margin-left: 10rpx;
				padding-top: 20rpx;

				.btn {
					margin-top: 10rpx;
					line-height: 52rpx;
					width: 160rpx;
					border-radius: 26rpx;
					border: 2rpx solid $u-border-color;
					font-size: 26rpx;
					text-align: center;
					color: $u-type-info-dark;
				}

				.evaluate {
					color: $u-type-warning-dark;
					border-color: $u-type-warning-dark;
				}
			}
		}

	}

	.centre {
		text-align: center;
		margin: 200rpx auto;
		font-size: 32rpx;

		image {
			width: 164rpx;
			height: 164rpx;
			border-radius: 50%;
			margin-bottom: 20rpx;
		}

		.tips {
			font-size: 24rpx;
			color: #999999;
			margin-top: 20rpx;
		}

		.btn {
			margin: 80rpx auto;
			width: 200rpx;
			border-radius: 32rpx;
			line-height: 64rpx;
			color: #ffffff;
			font-size: 26rpx;
			background: linear-gradient(270deg, rgba(249, 116, 90, 1) 0%, rgba(255, 158, 1, 1) 100%);
		}
	}

	.wrap {
		display: flex;
		flex-direction: column;
		height: calc(100vh - var(--window-top));
		width: 100%;
	}

	.swiper-box {
		flex: 1;
	}

	.swiper-item {
		height: 100%;
	}
</style>
