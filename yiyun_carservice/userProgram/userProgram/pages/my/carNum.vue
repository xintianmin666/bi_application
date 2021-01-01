<template>
	<view>
		<view class="item" v-for="(res, index) in siteList" :key="res.id">
			<view class="top">
				<view class="carNumber">{{ res.carNo }}</view>
				<view class="tag">
					<text v-if="res.isDefault==1" class="red">默认</text>
				</view>
				<view :data-carnum="res.carNo" @tap="delCarnum"><u-icon class="edit_pen" name="close-circle" :size="40" color="#ff5100"></u-icon></view>
			</view>
		</view>
		<view class="addSite" @tap="toAddSite">
			<view class="add">
				<u-icon name="plus" color="#ffffff" class="icon" :size="30"></u-icon>添加车牌
			</view>
		</view>
		<u-popup v-model="show" mode="center" width="600rpx" height="500rpx" border-radius="14" :closeable="true">
			<view class="input-area">
				<view class="input-wrap">
					<input class="input" disabled type="text" :value="input" placeholder="请输入车牌号" @tap="keyboardshow=true" />
					<u-button :custom-style="{height: '32px'}" class="clear-btn" @click="clearCarnum">清空</u-button>
				</view>
				<u-keyboard ref="uKeyboard" safe-area-inset-bottom @confirm="confirm" mode="car"
				:confirmBtn="true" :cancelBtn="true" :tooltip="true" v-model="keyboardshow" tips="请输入车牌号" 
				@change="change" @backspace="backspace"></u-keyboard>
				<view class="btn_wrap">
					<view class="default">
						<view class="left">
							<view class="set">设置默认地址</view>
						</view>
						<view class="right"><switch @change="setDefault" /></view>
					</view>
					<button class="add_btn" @tap="addCarnum">确定</button>
				</view>
			</view>
		</u-popup>
	</view>
</template>

<script>
export default {
	data() {
		return {
			siteList: [],
			show: false,
			input: '', 
			keyboardshow: false,
			isDefault: 2
		};
	},
	onLoad() {
		this.getData();
	},
	methods: {
		getData() {
			var that = this;
			this.$u.api.getUserInfo({
				userId: uni.getStorageSync('storage_session').id
			}).then(res => {
				console.log(res)
				that.siteList = res.data.carList;
			}).catch(res => {
				console.log(res)
				uni.showModal({
					content: res.message
				});
			});
			
		},
		toAddSite(){
			this.show = true;
			this.keyboardshow = true;
		},
		// 点击退格键
		backspace() {
			if(this.input.length) this.input = this.input.substr(0, this.input.length - 1);
		},
		// 键盘按键发生变化
		change(detail) {
			this.input += detail;
		},
		// 点击完成
		confirm(e) {
			
		},
		//清空
		clearCarnum(){
			this.input = '';
		},
		// 设置默认
		setDefault(e){
			console.log(e)
			e.detail.value == true ? this.isDefault = 1 : this.isDefault = 2;
		},
		// 接口添加车牌
		addCarnum(){
			var that = this;
			uni.showLoading();
			if(this.input != ''){
				this.$u.api.updateUserInfo({
					userId: uni.getStorageSync('storage_session').id,
					carNo: this.input,
					changeType: 'add',
					isDefault: this.isDefault
				}).then(res =>{
					console.log(res)
					that.siteList = res.data.carList
					that.input = '';
					uni.hideLoading();
				}).catch(res =>{
					console.log(res)
					uni.hideLoading();
					uni.showModal({
						content: res.message
					});
					that.input = '';
				})
			}
			
			//初始化键盘、模态窗
			this.show = false;
			this.keyboardshow = false;
		},
		//接口删除车牌
		delCarnum(e){
			console.log(e)
			var carnum = e.currentTarget.dataset.carnum;
			var that = this;
			uni.showLoading();
			this.$u.api.updateUserInfo({
				userId: uni.getStorageSync('storage_session').id,
				carNo: carnum,
				changeType: 'del',
				isDefault: this.isDefault
			}).then(res =>{
				console.log(res)
				that.siteList = res.data.carList
				that.input = '';
				uni.hideLoading();
			}).catch(res =>{
				console.log(res)
				uni.hideLoading();
				uni.showModal({
					content: res.message
				});
				that.input = '';
			})
		}
	}
};
</script>

<style lang="scss" scoped>
page {
	background-color: #ededed;
}
.item {
	padding: 20rpx;
	.top {
		display: flex;
		font-weight: bold;
		font-size: 34rpx;
		border: 1px solid #fff;
		background-color: #fff;
		padding: 40rpx;
		border-radius: 8px;
		position: relative;
		.tag {
			display: flex;
			font-weight: normal;
			align-items: center;
			text {
				display: block;
				width: 60rpx;
				height: 34rpx;
				line-height: 34rpx;
				color: #ffffff;
				font-size: 20rpx;
				border-radius: 6rpx;
				text-align: center;
				margin-left: 30rpx;
				background-color:rgb(49, 145, 253);
			}
			.red{
				background-color:red
			}
		}
	}
	.edit_pen{
		position: absolute;
		right: 40rpx;
		bottom: 40rpx;
	}
	.bottom {
		display: flex;
		margin-top: 20rpx;
		font-size: 28rpx;
		justify-content: space-between;
		color: #999999;
	}
}
.addSite {
	display: flex;
	justify-content: space-around;
	width: 600rpx;
	line-height: 100rpx;
	position: absolute;
	bottom: 30rpx;
	left: 80rpx;
	background-color: red;
	border-radius: 60rpx;
	font-size: 30rpx;
	.add{
		display: flex;
		align-items: center;
		color: #ffffff;
		.icon{
			margin-right: 10rpx;
		}
	}
}
	.input {
		border: 1px solid $u-light-color;
		border-radius: 4px;
		margin-bottom: 20px;
		height: 32px;
		font-size: 26rpx;
		flex: 1;
		box-sizing: border-box;
		padding-left: 20rpx;
	}
	.input-area{
		padding: 40rpx 60rpx;
		.input-wrap {
			display: flex;
			margin-top: 80rpx;
		}
		.clear-btn {
			margin-left: 10px;
			font-size: 28rpx;
		}
		.btn_wrap{
			.add_btn{
				margin-top: 40rpx;
			}
			.default {
				display: flex;
				justify-content: space-between;
				line-height: 64rpx;
				.default>switch::before{
					content: '';
				}
			}
		}
	}
	
	
</style>
