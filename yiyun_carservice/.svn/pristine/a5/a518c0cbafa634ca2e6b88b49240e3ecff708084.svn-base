<template>
	<view>
		<!-- 救援详情 -->
		<view class="margin bg-white shadow cu-list card-menu">
			<view class="cu-form-group ">
				<view class="title">联系人姓名</view>
				<view>{{detail.userName?detail.userName:'无'}}</view>
			</view>
			<view class="cu-form-group ">
				<view class="title">联系人号码</view>
				<view>{{detail.userPhone?detail.userPhone:'无'}}</view>
			</view>
			<view class="cu-form-group ">
				<view class="title">报修车牌号</view>
				<view>{{detail.carNo?detail.carNo:'无'}}</view>
			</view>
			<view class="cu-form-group ">
				<view class="title">救援备注</view>
				<view>{{detail.remark?detail.remark:'无'}}</view>
			</view>
			<view class="cu-form-group ">
				<view class="title">救援地址</view>
				<view>{{detail.address?detail.address:'无'}}</view>
			</view>
			<view class="cu-form-group ">
				<view class="title">救援人员</view>
				<view>{{detail.rescueName?detail.rescueName:'无'}}</view>
			</view>
			<view class="cu-form-group ">
				<view class="title">联系号码</view>
				<view>{{detail.rescueMobile?detail.rescueMobile:'无'}}</view>
			</view>
			<view class="cu-form-group ">
				<view class="title">救援车辆</view>
				<view>{{detail.rescueCarNo?detail.rescueCarNo:'无'}}</view>
			</view>
			<view class="cu-form-group ">
				<view class="title">救援进度</view>
				<view class="cu-tag radius" :class="detail.orderGoodsList[0].payStatus=='4'?'bg-olive':detail.orderGoodsList[0].payStatus=='5'?'bg-green':detail.orderGoodsList[0].payStatus=='1'?'bg-yellow':'bg-orange'">
					{{detail.orderGoodsList[0].payStatus=='1'?'已下单':detail.orderGoodsList[0].payStatus=='2'?'已取消':detail.orderGoodsList[0].payStatus=='3'?'已接单':detail.orderGoodsList[0].payStatus=='4'?'服务中':detail.orderGoodsList[0].payStatus=='5'?'已完成':'已退款'}}
				</view>
				<!-- <view class="cu-tag radius " :class="detail.orderGoodsList&&detail.orderGoodsList[0].payStatus=='已支付'?'bg-green':'bg-yellow'">{{detail.orderGoodsList[0].payStatus}}</view> -->
			</view>
			<view class="cu-form-group " v-if="detail.orderGoodsList&&detail.orderGoodsList[0].payStatus=='5'">
				<view class="title">支付时间</view>
				<view class="" >{{detail.orderGoodsList[0].payTime?detail.orderGoodsList[0].payTime:'暂无'}}</view>
			</view>
			<view>
				<!-- 图片上传 -->
				<view class="cu-bar bg-white ">
					<view class="action">
						费用清单
					</view>
					<view class="action">
						{{imgList.length}}/{{imgList.length}}
					</view>
				</view>
				<view class="cu-form-group" v-if="imgList.length>0">
					<view class="grid col-4 grid-square flex-sub">
						<view class="bg-img" v-for="(item,index) in imgList" :key="index" @tap="ViewImage" :data-url="imgList[index]">
							<image :src="imgList[index]" mode="aspectFill"></image>
						</view>
					</view>
				</view>
			</view>
			<view class="cu-form-group " v-if="detail.orderStatus=='2'">
				<view class="title">取消时间</view>
				<view>{{detail.cancelTime?detail.cancelTime:'暂无'}}</view>
			</view>
		</view>
		<!-- 取消订单按钮 -->
		<view class="padding flex flex-direction margin-top" v-if="detail.orderStatus=='1'">
			<button class="cu-btn bg-red lg shadow" @tap="orderCancel">取消订单</button>
		</view>
		<!-- 展示支付按钮 -->
		<view class="padding flex flex-direction margin-tb" v-if="detail.orderStatus=='4'">
			<button class="cu-btn bg-blue lg shadow" @tap="goPay">支付订单</button>
		</view>
		<!-- 展示二维码按钮 -->
		<view class="padding flex flex-direction margin-top" v-if="imgSrc!=null">
			<button class="cu-btn bg-blue lg shadow" @tap="showQRcode= !showQRcode">出示二维码</button>
		</view>
		<view class="flex justify-center padding-bottom" v-if="showQRcode&&imgSrc!=null">
			<image :src="imgSrc" mode="aspectFit"></image>
		</view>
		<!-- 展示评价按钮 -->
		<view class="padding flex flex-direction margin-top" v-if="detail.orderStatus=='5'&&canEvaluate==true">
			<button class="cu-btn bg-orange lg shadow" @tap="contentModal = true">立即评价</button>
		</view>
		
		<!-- 评价modal框 -->
		<view class="cu-modal" :class="contentModal==true?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white justify-end">
					<view class="content">服务评价</view>
					<view class="action" @tap="contentCancel">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="padding text-left">
					<view class="cu-form-group">
						<text>服务评分</text>
						<u-rate :count="5" v-model="startRating" :min-count="1" active-icon="heart-fill" inactive-icon="heart"></u-rate>
					</view>
					<view class="cu-form-group" v-if="contentModal==true">
						<textarea maxlength="-1" v-model="content" placeholder="请输入您的评价"></textarea>
					</view>
				</view>
				<view class="cu-bar bg-white">
					<view class="action margin-0 flex-sub text-red solid-left" @tap="contentCancel">取消</view>
					<view class="action margin-0 flex-sub  solid-left" @tap="contentConfirm">确定</view>
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
				imgSrc: null,
				showQRcode: false,
				detail: {},
				content:"",
				contentModal:false,
				startRating:5,
				canEvaluate:false,
				// 图片展示集合
				imgList: [],
			}
		},
		onLoad(options) {
			if (options.orderId) {
				this.getOrderInfo(options.orderId);
			} else {
				uni.navigateBack({
					delta: 1
				})
			}
		},
		methods: {
			// 获取救援订单详情
			getOrderInfo(orderId) {
				console.log(GD.testUrl)
				this.$http.post(GD.webUrl,
					'/order/getOrderInfo', {
						orderCode: orderId
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);

					this.detail = res.data;
					this.imgSrc = res.data.orderGoodsList[0].verifyCode;
					this.canEvaluate = res.data.isEvaluate==0?true:false
					
					if(res.data.orderGoodsList[0].productDescribe){
						let tmpImgList = res.data.orderGoodsList[0].productDescribe.split(",");
						for(let i =0;tmpImgList.length&&i<tmpImgList.length;i++){
							// 这里域名暂时接季然的本地
							tmpImgList[i]=GD.testUrl + tmpImgList[i]
						}
						this.imgList =tmpImgList;
					}
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
				});
			},
			// 取消订单
			orderCancel() {
				let that = this;
				uni.showModal({
				    title: '取消订单',
				    content: '是否取消该条订单？',
				    success: function (res) {
				        if (res.confirm) {
				           that.$http.post(GD.webUrl,
				           	'/order/cancelOrder', {
				           		orderCode: that.detail.orderCode
				           	}).then(res => {
				           	//成功回调
				           	console.log("[接口]:", res);
				           	if (res.code == 200) {
				           		uni.showToast({
				           			title: '订单取消成功!'
				           		})
				           		uni.redirectTo({
				           			url: '../rescueRecords/rescueRecords'
				           		})
				           	} else {
				           		uni.showToast({
				           			title: '订单取消失败',
				           			icon: 'none'
				           		})
				           	}
				           }).catch(err => {
				           	//异常回调
				           	console.log('请求失败', err);
				           });
				        } else if (res.cancel) {
				            console.log('用户点击取消');
				        }
				    }
				});
				
			},
			// 评价取消
			contentCancel(){
				this.contentModal = false;
				this.content = ""
			},
			// 提交评价
			contentConfirm(){
				const userId = uni.getStorageSync('storage_session').id;
				let params = {
					evaluateContent:this.content,
					orderCode:this.detail.orderCode,
					startRating:this.startRating,
					userId:userId,
					shopId:'9'
				};
				this.$http.post(GD.webUrl,
					'/order/createEvaluate', params).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						uni.showToast({
							title:res.message
						})
						setTimeout(()=>{
							uni.redirectTo({
								url: '../rescueRecords/rescueRecords'
							})
						},1500)
					}
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					uni.showToast({
						title: err.message,
						icon: 'none'
					})
				});
				this.contentCancel();
			},
			// 查看图片
			ViewImage(e) {
				uni.previewImage({
					urls: this.imgList,
					current: e.currentTarget.dataset.url
				});
			},
			// 前往支付
			goPay(){
				uni.navigateTo({
						url:'../payment/payment?orderId='+ this.detail.orderCode
					})
			}
		}
	}
</script>

<style>
	.title {
		min-width: 100px;
	}
</style>
