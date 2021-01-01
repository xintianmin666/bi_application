<template>
	<view>
		<view class="cu-form-group ">
			<view class="title"><text class="text-red">*</text>联系人姓名</view>
			<input placeholder="请输入姓名" v-model="address.name" name="input" type="text"></input>
		</view>
		<view class="cu-form-group ">
			<view class="title"><text class="text-red">*</text>联系人电话</view>
			<input placeholder="请输入电话" v-model="address.phone" name="tel" type="number"></input>
		</view>
		<view class="cu-form-group ">
			<view class="title"><text class="text-red">*</text>配送地址</view>
			<view v-if="address.site==''"><button class="cu-btn bg-blue shadow" @tap="choseLoaction">获取定位</button></view>
			<view v-else><text @tap="choseLoaction">{{address.site}}</text></view>
		</view>
		<!-- 保存新地址 -->
		<view class="padding flex flex-direction margin-top">
			<button class="cu-btn bg-yellow lg shadow" @tap="saveAddress"><text class="cuIcon-post margin-right-xs text-bold"></text>保存地址</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				address: {
					name: '',
					phone: '',
					site: '',
					latitude: '',
					longitude: ''
				}
			}
		},
		onLoad(options) {
			if (options.edit) {
				console.log('修改地址')
			}
		},
		methods: {
			saveAddress() {
				for (var keys in this.address) {
					if (!this.address[keys]&&keys!='latitude'&&keys!='longitude') {
						console.log(keys)
						uni.showToast({
							title: '必填项不能为空！',
							icon: 'none',
							duration:2000
						})
						return
					}
				}
				if(this.$u.test.mobile(this.address.phone)==false){
					uni.showToast({
						title:'手机号格式错误！',
						icon:'none'
					})
					return
				}
				uni.$emit('changeAddressLit', this.address);
				uni.navigateBack({
					delta: 1
				})
			},
			// 选择位置
			choseLoaction() {
				let that = this;
				uni.chooseLocation({
					success: function(res) {
						that.address.latitude = res.latitude;
						that.address.longitude = res.longitude;
						that.address.site = res.address;
					}
				});
			},
		},
		onBackPress() {
			// console.log('取消-返回')
			uni.$off('changeAddressLit');
		}
	}
</script>

<style>
	.title {
		min-width: 95px;
	}
</style>
