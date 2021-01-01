<template>
	<view>
		<view class="mBtn padding flex flex-direction margin-lr">
			<button class="cu-btn bg-green lg shadow" @tap="scanCode">扫码核验</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		onLoad() {
		// this.scanCode();	
		},
		methods: {
			// 扫一扫 功能
			scanCode() {
				let that = this;
				// 允许从相机和相册扫码
				uni.scanCode({
					success: function(res) {
						console.log('条码类型：' + res.scanType);
						console.log('条码内容：' + res.result);
						// 根据扫码调出该订单，根据订单然后显示确认核验按钮
						let codeInfo = res.result.split(',');
						let params = {
							token: that.$store.state.token,
							orderCode: codeInfo[0],
							verifyCode: codeInfo[1],
						};
						console.log('haha')
						return
						that.$http.post(GD.testURL,
							'business/corder/getCheckOrderGoods', params).then(res => {
							//成功回调
							console.log("[接口]:", res);
			
						}).catch(err => {
							//异常回调
							console.log('请求失败', err);
							uni.showToast({
								title: err.msg,
								icon: 'none'
							})
							uni.switchTab({
								url:'../home'
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
		}
	}
</script>

<style>
.mBtn{
	margin-top: 30vh;
}
</style>
