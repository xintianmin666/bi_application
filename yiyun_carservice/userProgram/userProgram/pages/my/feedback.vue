<template>
	<view>
		<view class="cu-bar bg-white solid-bottom">
			<view class="action">
				<text class="cuIcon-title text-blue"></text>
				<text>提出意见与反馈</text>
			</view>
		</view>
		<view class="cu-form-group  radius">
			<textarea maxlength="-1" @input="textareaInput" placeholder="请输入您的意见与提议"></textarea>
		</view>
		<!-- 是否更新二维码 -->
		<view class="padding flex flex-direction margin-top">
			<button class="cu-btn bg-blue lg shadow" @tap="adSubmit">提交</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				advice:"",
			}
		},
		methods: {
			textareaInput(e) {
				this.advice = e.detail.value
			},
			adSubmit(){
				console.log('提交成功！')
			}
		}
	}
</script>

<style>

</style>
