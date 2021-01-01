<template>
	<view>
		<view class="cu-bar bg-white solid-bottom margin-top">
			<view class="action">
				<text class="cuIcon-title text-orange"></text> 密码更改
			</view>
			<view class="action">
				<button class="cu-btn bg-green shadow" @tap="passwordModal = true" >设置</button>
			</view>
		</view>
		
		<!-- 接收订单modal框 -->
		<view class="cu-modal" :class="passwordModal==true?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white justify-end">
					<view class="content">订单修改</view>
					<view class="action" @tap="pwCancel">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="padding">
					<view class="cu-form-group">
						<view class="title">原密码：</view>
						<input placeholder="请输入原密码" v-model="params.oldPassword" name="input" type="text"></input>
					</view>
					<view class="cu-form-group margin-top">
						<view class="title">新密码：</view>
						<input placeholder="请输入新密码" v-model="params.newPassword" name="input" type="text"></input>
					</view>
				</view>
				<view class="cu-bar bg-white">
					<view class="action margin-0 flex-sub text-red solid-left" @tap="pwCancel">取消</view>
					<view class="action margin-0 flex-sub  solid-left" @tap="pwConfirm">确定</view>
				</view>
			</view>
		</view>
		
		<view class="cu-list  menu sm-border  margin-top" >
			<view class="cu-item " >
				<view class="content">
					<text class="cuIcon-group text-green"></text>
					<text class="text-grey">账号所属</text>
				</view>
				<view class="action">
					<text class="text-grey ">运泰自营</text>
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
				passwordModal:false,
				params:{
				oldPassword:'',
				newPassword:'',
				token:''
				},
			};
		},
		onLoad(options) {
			
		},
		methods: {
			pwCancel(){
				this.oldPassword = '';
				this.newPassword = '';
				this.passwordModal = false;
			},
			pwConfirm(){
				// 判断旧密码是否正确
				
				// 判断输入新密码是否有效
				if(this.params.newPassword.trim()==''){
					uni.showToast({
						title:'请填写有效密码！',
						icon:'none'
					})
					return
				}
				this.params.token = this.$store.state.token
				// console.log('this.params',this.params)
				this.$http.post(GD.webRoot,
				'/system/user/profile/updatePassword', this.params).then(res => {
					// '/system/user/profile/updatePassword', this.params,header).then(res => {
					// 成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						uni.showToast({
							title:'密码修改成功！',
						})
						this.oldPassword = '';
						this.newPassword = '';
						this.passwordModal = false;
					}
				}).catch(err => {
					// 异常回调
					this.oldPassword = '';
					this.newPassword = '';
					this.passwordModal = false;
					uni.showToast({
						title:'网络请求失败！',
						icon:'none'
					})
				});
				
				// 测试接口 带 Authorization
				// uni.request({
				// 	url: GD.webRoot + '/system/user/profile/updatePassword', //仅为示例，并非真实接口地址。本地模拟
				// 	data: this.params,
				// 	header: {
				// 		Authorization:this.$store.state.token,
				// 	},
				// 	method: 'POST',
				// 	success: (res) => {
				// 		console.log(res.data)
				// 	}
				// });
				
			}
		}
	};
</script>

<style>
	page {
		background: #F1F1F1;
	}
</style>
