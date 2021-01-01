<template>
	<view>
		<u-toast ref="uToast" />
		
		<view class="bg-white padding" v-if="evaluateList.length >0">
			<view class="u-border-bottom" v-for="(item,index) in evaluateList.slice(0,2)" :key="index">
				<view>{{item.nickName}}</view>
				<u-rate :count="5" :current="item.starRating" :disabled="true" active-color="#FA3534" inactive-color="#b2b2b2"
				 gutter="20"></u-rate>
				<view>评论内容:{{item.evaluateContent}}</view>
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
				shopId:'',
				evaluateList: []
			}
		},
		onLoad(options) {
			this.shopId = options.shopId;
			this.requestShopEvaluate();
		},
		methods: {
			//获取店铺评价
			requestShopEvaluate() {
			
				this.$http.get(GD.testUrl,
					'/clinet/evaluate/getByShopId', {
						shopId: this.shopId
					}).then(res => {
					//成功回调
					console.log("[接口]:", res);
					if (res.code == 200) {
						this.evaluateList = res.data;
					}
				}).catch(err => {
					//异常回调
					console.log('请求失败', err);
					this.$refs.uToast.show({
						title: res.data.msg,
						type: 'err',
					})
				});
			
			},
		}
	}
</script>

<style>

</style>
