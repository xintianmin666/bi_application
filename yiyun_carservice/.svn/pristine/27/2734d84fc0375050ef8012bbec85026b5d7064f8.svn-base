<template>
	<view class="padding-bottom-xl">
		<!-- 地图显示 -->
		<view class="cu-bar bg-white ">
			<view class="action">
				<text class="cuIcon-titles text-orange"></text>
				<text class="text-bold text-lg">当前位置</text>
			</view>
		</view>
		<view class="cu-bar bg-white " @tap="choseLoaction">
			<view class="action">
				<text class="cuIcon-title text-orange"></text>
				<text :class="rescueForm.address?'text-blue':'cu-btn bg-blue radius'">{{rescueForm.address?rescueForm.address:'定位当前位置'}}</text>
			</view>
		</view>
		<view class="page-section page-section-gap" v-if="rescueForm.address!=''">
			<map style="width: 100%; height: 300px;" :latitude="rescueForm.latitude" :markers="curLocation" :longitude="rescueForm.longitude">
			</map>
		</view>
		<view class="cu-bar bg-white ">
			<view class="action">
				<text class="cuIcon-titles text-orange"></text>
				<text class="text-bold text-lg">救援信息</text>
			</view>
		</view>
		<!-- 个人信息：  -->
		<view class="cu-form-group ">
			<view class="title">联系人姓名</view>
			<input placeholder="请输入姓名" v-model="rescueForm.userName" name="input" type="text"></input>
		</view>
		<view class="cu-form-group ">
			<view class="title"><text class="text-red">*</text>联系人电话</view>
			<input placeholder="请输入电话" v-model="rescueForm.userPhone" name="tel" type="number"></input>
		</view>
		<view class="cu-form-group ">
			<view class="title">报修车牌号</view>
			<input placeholder="例: 皖B-12345" v-model="rescueForm.carNo" name="plate" type="text"></input>
		</view>
		<view class="cu-form-group ">
			<view class="title">邀请码</view>
			<input placeholder="请输入邀请码" v-model="rescueForm.inviteCode" name="input" :disabled="hasQRcode" type="text"></input>
		</view>
		<view class="cu-bar bg-white ">
			<view class="action">
				<text class="cuIcon-titles text-orange"></text>
				<text class="text-bold text-lg">救援备注</text>
			</view>
		</view>
		<view class="cu-form-group  radius">
			<textarea maxlength="-1" @input="textareaInput" placeholder="请输入救援备注"></textarea>
		</view>
		<!-- 费用说明 -->
		<view class="bg-white solid-top mar-tb-sm padding-sm text-blue" @tap="modalDisp = true">
			 <text class="cuIcon-repairfill "></text>具体费用说明
		</view>
		<!-- 提交订单按钮 -->
		<view class="padding flex flex-direction margin-top margin-bottom-xl" @tap="submitOrder">
			<button class="cu-btn bg-blue lg shadow">提交</button>
		</view>
		<view class="bottomButton flex text-center">
			<button  class="flex-sub bg-orange "@tap="goRecords"><text class="cuIcon-repairfill "></text>我的救援</button>
			<button class="flex-sub bg-blue "@tap="callService"><text class="cuIcon-service "></text>呼叫救援</button>
		</view>
		
		<!-- modal框 提示说明 -->
		<!-- 儿童票 创建选单 -->
		<view class="cu-modal" :class="modalDisp==true?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white justify-end">
					<view class="content">一键救援服务说明</view>
					<view class="action" @tap="modalDisp=false">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="padding text-left">
					<!-- tips -->
					<view class="">
						<view class="margin-bottom-xs text-red">最终服务收费视当时实际情况而定：</view>
						<view>
							<view class="margin-bottom-xs">1.服务对象：7座（含）以下小型车辆。</view>
							<view class="margin-bottom-xs">2.救援时间：24小时。</view>
							<view class="margin-bottom-xs">3.服务区域：芜湖市区（不含高速公路）。</view>
							<view class="margin-bottom-xs">4.施救方式：现场救援、托行或拖行。</view>
							<!-- <view class="margin-bottom-xs">5.服务项目及收费标准：。</view> -->
							<view class="margin-bottom-xs">5.服务过程中产生的高速公路费、过桥费、通行费、停车费等需用户自行承担。</view>
							<view class="margin-bottom-xs">6.车主应如实向客服人员说明现场情况，否则造成的后果由车主承担。</view>
							<view class="margin-bottom-xs">7.我司可提供事故处理、车辆维修及相关理赔服务。</view>
							<view class="margin-bottom-xs">8.我公司是一家专业从事汽车清障业务的运输企业，拥有多台清障车辆，每车都安装了北斗定位系统，全年无休。</view>
							<view>9. 24小时救援电话：19955320212。</view>
						</view>
					</view>
				</view>
				<view class="cu-bar bg-white ">
						
					</view>
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
				hasQRcode:false,
				rescueForm: {
					userPhone: '',
					userId:'',
					orderType:'7',
					shopId:'9',
					orderAmount:'',
					collectAmount:'',
					isUsePoints:'2',
					inviteCode: '',
					name: '',
					goodsCode: '',
					goodsName: '',
					userName: '',
					carNo: '',
					address: '',
					remark: '',
					longitude: '',
					latitude: ''
				},
				curLocation: [{
					latitude: 0,
					longitude: 0,
					iconPath: '../../../static/image/location.png',
					width:32,
					height:32
				}],
				modalDisp:false,
				params:{},
				userInfo:{}
			}
		},
		onLoad(options) {
			this.timeNow = this.$u.timeFormat(this.timestamp, 'yyyy年mm月dd日');
			this.userInfo = uni.getStorageSync('storage_session');
			this.userInfo.carNo?this.rescueForm.carNo = this.userInfo.carNo:''
			this.userInfo.userPhone?this.rescueForm.userPhone = this.userInfo.userPhone:''
			console.log('用户信息：',this.userInfo)
		},
		methods: {
			// 记录输入备注
			textareaInput(e) {
				this.rescueForm.remark = e.detail.value
			},
			// 选择位置
			choseLoaction() {
				let that = this;
				uni.chooseLocation({
					success: function(res) {
						that.rescueForm.latitude = res.latitude;
						that.rescueForm.longitude = res.longitude;
						that.rescueForm.address = res.address;
						// 坐标图标
						that.curLocation[0].latitude = res.latitude;
						that.curLocation[0].longitude = res.longitude;
					}
				});
			},
			// 电话呼叫救援
			callService(){
				uni.showModal({
				    title: '拨打电话',
				    content: '24小时救援热线：199553230212',
				    success: function (res) {
				        if (res.confirm) {
				           uni.makePhoneCall({
				               phoneNumber: '19955320212' //
				           });
				        }
				    }
				});
			},
			// 跳转至救援记录列表页
			goRecords(){
				uni.navigateTo({
					url:'./rescueRecords/rescueRecords'
				})
			},
			// 提交救援订单
			submitOrder(){
				
				 if(this.$u.test.mobile(this.rescueForm.userPhone)==false||this.rescueForm.userPhone.trim()==''){
					uni.showToast({
						title:'手机号错误！',
						icon:'none'
					})
					return
				}
				
				if(!this.rescueForm.address){
					uni.showToast({
						title:'请选择地址！',
						icon:'none'
					})
					return
				}
				
				this.rescueForm.userId = this.userInfo.id
				const isLogin =  this.userInfo.userPhone;
				if(!this.rescueForm.userId||!isLogin){
					uni.showToast({
						title:'请先登录后再进行操作',
						icon:'none'
					})
					setTimeout(()=>{
						uni.navigateTo({
							url:'../../login/login'
						})
					},1500)
					return
				}
				console.log(this.rescueForm);
				// return
				this.$http.post(GD.webUrl,
				'/order/createOrder', this.rescueForm).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						uni.showToast({
							title:res.message,
							icon:'none',
							duration:1500
						})
						setTimeout(()=>{
							uni.redirectTo({
								url:'./rescueRecords/rescueRecords'
							})
						},1500)
					}
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					uni.showToast({
						title:err.message,
						icon:'none'
					})
				});
			}
		}
	}
</script>

<style>
	.bottomButton {
		position: fixed;
		bottom: 0rpx;
		left: 0;
		right: 0;
		z-index: 999;
	}
	.bottomButton button{
		border-radius: 0;
	}
	.bottomButton button:after{
		border-radius: 0;
	}
	.title{
		min-width: 100px;
	}
</style>
