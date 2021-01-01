<template xlang="wxml">
	<view class="container margin-top-xl">
		<view class="qrimg">
			<view class="qrimg-i">
				<tki-qrcode cid="qrcode1" ref="qrcode" val="易开车的二维码" :size='300' unit="upx" :loadMake="true" :usingComponents="true" @result="qrR" />
			</view>
		</view>
		<!-- 是否更新二维码 -->
		<view class="padding flex flex-direction margin-top">
			<button class="cu-btn bg-green lg shadow" @tap="orderCancelBtn=true">刷新二维码</button>
		</view>
	</view>
</template>
<script>
import tkiQrcode from '@/components/tki-qrcode/tki-qrcode.vue'
export default {
	data() {
		return {
			ifShow: true,
			val: '二维码', // 要生成的二维码值
			size: 200, // 二维码大小
			unit: 'upx', // 单位
			background: '#b4e9e2', // 背景色
			foreground: '#309286', // 前景色
			pdground: '#32dbc6', // 角标色
			icon: '', // 二维码图标
			iconsize: 40, // 二维码图标大小
			lv: 3, // 二维码容错级别 ， 一般不用设置，默认就行
			onval: false, // val值变化时自动重新生成二维码
			loadMake: true, // 组件加载完成后自动生成二维码
			src: '' // 二维码生成后的图片地址或base64
		}
	},
	methods: {
		creatQrcode() {
			this.$refs.qrcode._makeCode()
		},
		saveQrcode() {
			this.$refs.qrcode._saveCode()
		},
		qrR(res) {
			this.src = res
		},
	
	},
	components: {
		tkiQrcode
	},
	onLoad: function () {
		setTimeout(()=>{
			uni.showToast({
				title:'携带的信息请存放在val这个参数里面！',
				icon:'none',
				duration:2000
			})
		},1500)
	},
}
</script>

<style>
.container {
	display: flex;
	flex-direction: column;
	width: 100%;
}

.qrimg {
	display: flex;
	justify-content: center;
}
.qrimg-i{
	margin-right: 10px;
}

</style>
