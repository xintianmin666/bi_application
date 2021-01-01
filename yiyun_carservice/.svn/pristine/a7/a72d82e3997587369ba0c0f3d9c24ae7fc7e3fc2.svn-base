<template>
	<view>
		<view class="bg-white cu-list card-menu margin-top padding-bottom">
			<view class="cu-form-group text-right">
				<view class="title"><text class="text-red">*</text>需求标题</view>
				<input placeholder="请输入" v-model="issueForm.name" type="text"></input>
			</view>
			<view class="cu-form-group text-right">
				<view class="title"><text class="text-red">*</text>需求数量</view>
				<input placeholder="请输入" v-model="issueForm.counts" type="number"></input>
			</view>
			<!-- 招标起始日期 -->
			<view >
				<u-picker v-model="dataSOpen" mode="time" :params="timeParams" @confirm="dateSChange"></u-picker>
				<view class="cu-form-group" >
					<view class="title">起始日期</view>
					<view class="picker text-yellow text-bold" @click="dataSOpen = true">
						{{issueForm.dateStart?issueForm.dateStart:'自定时间'}}
					</view>
				</view>
			</view>
			<view >
				<u-picker v-model="dataEOpen" mode="time" :params="timeParams" @confirm="dateEChange"></u-picker>
				<view class="cu-form-group" >
					<view class="title">起始日期</view>
					<view class="picker text-yellow text-bold" @click="dataEOpen = true">
						{{issueForm.dateEnd?issueForm.dateEnd:'自定时间'}}
					</view>
				</view>
			</view>
			<!-- 招标结束日期 -->
			<view class="cu-form-group ">
				<view class="title"><text class="text-red">*</text>要求备注</view>
			</view>
			<view class="padding-lr" >
				<textarea maxlength="-1" @input="remarkInput" placeholder="请输入备注内容"></textarea>
			</view>
		</view>
		<!-- 保存新地址 -->
		<view class="padding flex flex-direction margin-top">
			<button class="cu-btn bg-yellow lg shadow" @tap="saveAddress"><text class="cuIcon-post margin-right-xs text-bold"></text>保存提交</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				issueForm: {
					name: '',
					counts: '',
					remark: '',
					dateStart:'',
					dateEnd:'',
				},
				dataSOpen: false,
				dataEOpen: false,
				timeParams: {
					year: true,
					month: true,
					day: true,
					hour: true,
					minute: true,
					second: true
				},
			}
		},
		onLoad(options) {
			
		},
		methods: {
			// 备注输入
			remarkInput(e) {
				this.issueForm.remark = e.detail.value
			},
			// 时间调整
			dateSChange(e) {
				this.issueForm.dateStart = e.year+'-'+e.month+'-'+e.day+' '+e.hour+':'+e.minute+':'+e.second
			},
			dateEChange(e) {
				this.issueForm.dateEnd = e.year+'-'+e.month+'-'+e.day+' '+e.hour+':'+e.minute+':'+e.second
			},
		},
		onBackPress() {
			// console.log('取消-返回')
			// uni.$off('changeAddressLit');
		}
	}
</script>

<style>
	.title {
		min-width: 95px;
	}
	page {
		background: #F1F1F1;
	}
</style>
