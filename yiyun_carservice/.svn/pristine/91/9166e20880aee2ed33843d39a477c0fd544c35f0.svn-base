<template>
	<view>
		<!-- 搜索框 -->
		<view class="cu-bar bg-gradual-blue search">
			<view class="cu-avatar bg-white text-blue radius" @tap="scanCode"><text class="cuIcon-scan"></text></view>
			<view class="search-form radius bg-white" style="background-color: #FFFFFF;">
				<text class="cuIcon-search"></text>
				<input v-model="searchKeys" :adjust-position="false" type="text" placeholder="输入订单号+验证码进行搜索" confirm-type="search"></input>
			</view>
			<view class="action" @tap="getSearch">
				<button class="cu-btn line-white ">搜索</button>
			</view>
		</view>
		<!-- 订单类型分类 下拉模块 -->
		<view class="solid-bottom bg-white">
			<u-dropdown ref="uDropdown" :border-bottom="true" :close-on-click-mask="false" active-color="#39b54a">
				<u-dropdown-item v-model="curType" title="订单类型" :options="orderType" @change="selectChange"></u-dropdown-item>
			</u-dropdown>
		</view>
		<!-- 订单状态分类 -->
		<view class="bg-white">
			<scroll-view scroll-x class="bg-white nav">
				<view class="flex text-center">
					<view class="cu-item flex-sub" :class="item.ostatus==statusCur?'text-orange cur':''" v-for="(item,index) in statusList"
					 :key="index" @tap="statusSelect" :data-id="item.ostatus">
						{{item.status}}
					</view>
				</view>
			</scroll-view>
		</view>
		<!-- 订单列表 -->
		<!-- 未接单 -->
		<!-- <view v-if="statusCur==1"> -->
		<view v-if="odLit.length>0">
			<view v-if="statusCur!=1">
				<view class="margin bg-white shadow  cu-list card-menu" v-for="(item,index) in odLit" :style="[{animation: 'show ' + ((index+1)*0.2+1) + 's 1'}]"
				 :key="index" @tap="goOrder(item)">
					<view class="flex align-center solid shadow padding-sm">
						<!-- 左 -->
						<view class=" margin-right margin-left-xs">
							<view class="cu-avatar radius bg-cyan lg">
								<text>{{item.orderType=='7'?'救':'普'}}</text>
							</view>
						</view>
						<!-- 右 -->
						<view class="">
							<view class="  margin-bottom-xs" >
								<text class=" ">用户电话：{{item.userPhone}}</text>
							</view>
							<view class=" margin-bottom-xs" v-if="item.orderType == 1 || item.orderType == 4 ">服务类型：<text class="text-blue text-bold">{{item.productName}}</text></view>
							<view class=" margin-bottom-xs" v-else-if="item.orderType == 5 ">服务类型：<text class="text-blue text-bold">驾考培训</text></view>
							<view class="margin-bottom-xs " v-else>服务类型：<text class="text-blue text-bold">{{item.orderType=='2'?'保养':item.orderType=='3'?'维修':item.orderType=='5'?'驾培':item.orderType=='6'?'保险':'救援'}}</text></view>
							<view class=" text-sm ">
								<text class=" margin-right-sm">下单时间：<text class="text-blue">{{item.createTime}}</text></text>
							</view>
						</view>
					</view>
				</view>
			</view>
			<!-- 如果是未接单 -->
			<view v-else>
				<view class="margin bg-white shadow  cu-list card-menu" v-for="(item,index) in odLit" :style="[{animation: 'show ' + ((index+1)*0.2+1) + 's 1'}]"
				 :key="index" @tap="goOrder(item)">
					<view v-if="item.orderType != 1 && item.orderType != 4 ">
						<view class="flex align-center solid shadow padding-sm">
							<!-- 左 -->
							<view class=" margin-right margin-left-xs">
								<view class="cu-avatar radius bg-cyan lg">
									<text>{{item.orderType=='7'?'救':'普'}}</text>
								</view>
							</view>
							<!-- 右 -->
							<view class="">
								<view class="  margin-bottom-xs" v-if="statusCur!=1">
									<text class=" ">用户电话：{{item.userPhone}}</text>
								</view>
								<view class=" margin-bottom-xs" v-if="item.orderType == 1 || item.orderType == 4 ">服务类型：<text class="text-blue text-bold">{{item.productName}}</text></view>
								<view class=" margin-bottom-xs" v-else-if="item.orderType == 5 ">服务类型：<text class="text-blue text-bold">驾考培训</text></view>
								<view class="margin-bottom-xs " v-else>服务类型：<text class="text-blue text-bold">{{item.orderType=='2'?'保养':item.orderType=='3'?'维修':item.orderType=='5'?'驾培':item.orderType=='6'?'保险':'救援'}}</text></view>
								<view class=" text-sm ">
									<text class=" margin-right-sm">下单时间：<text class="text-blue">{{item.createTime}}</text></text>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>

			<!-- 点击加载更多 -->
			<view class="cu-load  text-bold" :class="isOver?'over text-gray':'text-cyan'" @tap="getMore">{{isOver?'':'点击加载更多'}}</view>
		</view>
		<view v-else>
			<view class="solid-bottom bg-white text-center text-xxl padding">
				<text class="margin-right">暂无订单</text>
				<text class=" cuIcon-roundcheckfill text-green"></text>
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
						value: 1,
					},
					{
						label: '保养',
						value: 2,
					},
					{
						label: '维修',
						value: 3,
					},
					{
						label: '检测线',
						value: 4,
					},
					{
						label: '驾培',
						value: 5,
					},
					{
						label: '保险',
						value: 6,
					},
					{
						label: '救援',
						value: 7,
					}
				],
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
				odType: '',
				odLit: [],
				isOver: true,
			};
		},
		onLoad() {
			// this.timeNow = this.$u.timeFormat(this.timestamp, 'yyyy年mm月dd日');
			this.getOrderList();
		},
		onReady() {
			// 根据登录身份来展示不同页面
		},
		methods: {
			// 获取订单列表
			getOrderList() {
				let params = {
					token: this.$store.state.token,
					pageNum: this.curPage,
					pageSize: 10,
					orderStatus: this.statusCur,
					orderType: this.odType,
				};
				this.$http.post(GD.testURL,
					'/business/corder/getOrderList', params).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.data.pageNum < res.data.pageCount) {
						this.curPage += 1;
						this.isOver = false
					} else {
						this.isOver = true
					}
					// this.odLit = res.data.rows;
					this.odLit = this.odLit.concat(res.data.rows);
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					uni.showToast({
						title: err.msg,
						icon: 'none'
					})
				});
			},
			// 订单类型选择
			selectChange(e) {
				this.odType = e;
				// 重新请求列表
				this.refreshList();
				this.getOrderList();
			},
			// 根据核验码搜索
			getSearch() {
				this.searchKeys = this.searchKeys.trim();
				console.log(this.searchKeys)
				// this.moniScan();
			},
			// 订单状态选择
			statusSelect(e) {
				this.statusCur = e.currentTarget.dataset.id;
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
				// 重新请求列表
				this.refreshList();
				this.getOrderList();
			},
			// 选择订单进入查看
			goOrder(item) {
				uni.$once(`changeOrderStatus`, data => {
					// 每次订单状态改变后需重新请求一次
					this.refreshList();
					this.getOrderList();
				})
				let orderUrl = '';
				switch (item.orderType) {
					case '1':
						orderUrl = './orders/defalutOrder';
						break;
					case '4':
						orderUrl = './orders/defalutOrder';
						break;
					default:
						orderUrl = './orders/appointmentOrder';
						break;
				}
				if (this.statusCur == 5) {
					orderUrl = './orders/finishOrder';
				}
				uni.navigateTo({
					url: orderUrl + '?order=' + JSON.stringify(item)
				})
			},
			// 模拟扫一扫
			// moniScan() {
			// 	uni.$once(`changeOrderStatus`, data => {
			// 		// 每次订单状态改变后需重新请求一次
			// 		this.refreshList();
			// 		this.getOrderList();
			// 	})
			// 	// 允许从相机和相册扫码
			// 	let params = {
			// 		//当前订单号和核验码为 2020-12-19  09：01 下的单 类型为：检测
			// 		token: this.$store.state.token,
			// 		orderCode: '20201219090134834850',
			// 		verifyCode: '91489289',
			// 	};
			// 	this.$http.post(GD.testURL,
			// 		'/business/corder/getCheckOrderGoods', params).then(res => {
			// 		//成功回调
			// 		console.log("[接口]:", res);
			// 		uni.showToast({
			// 			title: res.msg,
			// 			icon: 'none'
			// 		})
			// 		uni.navigateTo({
			// 			url: './orders/defalutOrder?scan=true&' + 'order=' + JSON.stringify(res.data[0])
			// 		})

			// 	}).catch(err => {
			// 		//异常回调
			// 		console.log('请求失败', err);
			// 		uni.showToast({
			// 			title: err.msg,
			// 			icon: 'none'
			// 		})
			// 	});
			// },
			// 扫一扫 功能
			scanCode() {
				// 允许从相机和相册扫码
				const token  = this.$store.state.token;
				const that = this;
				uni.scanCode({
					success: function(res) {
						console.log('条码类型：' + res.scanType);
						console.log('条码内容：' + res.result);
						// 根据扫码调出该订单，根据订单然后显示确认核验按钮
						let codeInfo = res.result.split(',');
						let params = {
							token: token,
							orderCode: codeInfo[0],
							verifyCode: codeInfo[1],
						};
						that.$http.post(GD.testURL,
							'/business/corder/getCheckOrderGoods', params).then(res => {
							//成功回调
							console.log("[接口]:", res);
							uni.showToast({
								title: res.msg,
								icon: 'none'
							})
							uni.navigateTo({
								url: './orders/defalutOrder?scan=true&' + 'order=' + JSON.stringify(res.data[0])
							})
						}).catch(err => {
							//异常回调
							console.log('请求失败', err);
							uni.showToast({
								title: err.msg,
								icon: 'none'
							})
						});
					},
					fail: function(res) {
						uni.showToast({
							title: "扫码失败，请重试",
							icon: 'none'
						})
					}
				});
			},
			refreshList() {
				this.curPage = 1;
				this.odLit = [];
			},
			getMore() {
				if (this.isOver == false) {
					this.getOrderList();
				}
			},

		},

	};
</script>

<style>
	page {
		background: #F1F1F1;
	}
</style>
