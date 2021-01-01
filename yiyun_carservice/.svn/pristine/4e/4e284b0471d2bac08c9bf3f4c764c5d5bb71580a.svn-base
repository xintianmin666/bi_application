<template>
	<view>

		<view>
			<!-- 商品展示轮播图 -->
			<view class="solid-top bg-white">
				<swiper class="screen-swiper" :class="dotStyle?'square-dot':'round-dot'" :indicator-dots="true" :circular="true"
				 :autoplay="true" interval="5000" duration="500">
					<swiper-item v-for="(item,index) in swiperList" :key="index" @tap="ViewSwiperImage" :data-url="item">
						<image :src="item" mode="aspectFill"></image>
					</swiper-item>
				</swiper>
			</view>
			<!-- 商品名称与价格 -->
			<view class=" padding bg-white " style="border-radius: 0 0 20rpx 20rpx;">
				<view class="text-black text-bold text-lg">巴伐利亚工厂生产（BMW THE 4 Series）宝马4系 M Power 原装海运进口双门性能车</view>
				<!-- 倒计时 -->
				<view class="text-center text-bold  margin-top-sm">
					距离拍卖结束还有：<u-count-down autoplay :timestamp="86400" separator="colon" separator-size="32" :show-border="true" font-size="32" color="#ff0000" border-color="#ff0000" separator-color="#ff0000"></u-count-down>
				</view>
				<view class="margin-top flex justify-between align-center">
					<view class=" ">当前价格：
						<view class="text-red margin-top-xs text-xxl text-bold"><text>¥</text>820000.00</view>
					</view>
					<view class=" ">起拍价格：
						<view class="margin-top-xs text-xxl "><text>¥</text>770000.00</view>
					</view>
				</view>
			</view>
			<!-- 报名按钮 -->
			<view class="padding flex flex-direction " v-if="enableAut==false">
				<button class="cu-btn bg-gradual-blue lg shadow" @tap="eaMoneyModal">交保证金参与竞拍</button>
				<!-- @tap="acceptToAuc" -->
			</view>
			<view class="margin-lr margin-bottom-sm " v-else>
				<view class="text-bold text-lg margin-bottom flex justify-between align-center">
					<text>我的出价：</text>
					<text class="text-xl text-red">¥{{myPrice}}.00</text>
				</view>
				<view class="text-center">
					<u-number-box v-model="nBoxVal" @change="nBoxValChange" :step="1000" :min="1000" color="#fff" bg-color="#e65042"
					 size="40" input-width="200" :disabled-input="true" :long-press="false"></u-number-box>
				</view>
				<view class="padding-lr padding-top-lg flex flex-direction ">
					<button class="cu-btn bg-gradual-blue lg shadow" @tap="dispConfirmModal= true">确认报价</button>
				</view>
			</view>

			<!-- 支付保证金确认modal框 -->
			<view class="cu-modal" :class="eaMoney ==true?'show':''">
				<view class="cu-dialog">
					<view class="cu-bar bg-white justify-end">
						<view class="content">需支付保证金</view>
						<view class="action" @tap="hideModal">
							<text class="cuIcon-close text-red"></text>
						</view>
					</view>
					<view class="padding-xl">
						提交保证金后方可进行加价
					</view>
					<view class="cu-bar bg-white">
						<view class="action margin-0 flex-sub text-gray solid-left" @tap="hideModal">取消</view>
						<view class="action margin-0 flex-sub text-green solid-left" @tap="eaMoneyConfirm">确定</view>
					</view>
				</view>
			</view>
			
			<!-- 报价确认modal框 -->
			<view class="cu-modal" :class="dispConfirmModal ==true?'show':''">
				<view class="cu-dialog">
					<view class="cu-bar bg-white justify-end">
						<view class="content">报价确认</view>
						<view class="action" @tap="hideModal">
							<text class="cuIcon-close text-red"></text>
						</view>
					</view>
					<view class="padding-xl">
						请认真核定报价后再点击提交
						<view class="margin-top-sm">我的报价为：<text class="text-red text-bold text-lg"><text class="padding-right-xs">¥</text>{{myPrice}}.00</text></view>
					</view>
					<view class="cu-bar bg-white">
						<view class="action margin-0 flex-sub text-gray solid-left" @tap="hideModal">再考虑考虑</view>
						<view class="action margin-0 flex-sub text-blue solid-left" @tap="myPriceConfirm">提交报价</view>
					</view>
				</view>
			</view>

			<!-- 商品详情 -->
			<view class="cu-bar  bg-white solid-top">
				<view class="action border-title">
					<text class="text-xl text-bold text-black">拍卖说明</text>
					<text class="bg-gradual-orange" style="width:3rem"></text>
				</view>
			</view>
			<view class="margin-lr text-sm">
				<view class="margin-bottom-xs">拍卖地点：芜湖;</view>
				<view class="margin-bottom-xs">起拍价：¥ 7300.00;</view>
				<view class="margin-bottom-xs">加价幅度：¥ 1000.00;</view>
				<view class="margin-bottom-xs">保证金：¥ 200.00;</view>
				<view class="margin-bottom-xs">保留价：有;</view>
				<view class="margin-bottom-xs">延时周期：无;</view>
				<view class="margin-bottom-xs text-blue">保留价说明：保留价是该拍品由商家设置能够接受的最低成交价，当成交价低于保留价时，商品会流拍。</view>
			</view>
			<!--  -->

			<!-- 商品详情 -->
			<view class="cu-bar  bg-white solid-top" >
				<view class="action border-title">
					<text class="text-xl text-bold text-black">商品详情</text>
					<text class="bg-gradual-orange" style="width:3rem"></text>
				</view>
			</view>
			<view class="margin-lr text-sm">
				<view class="margin-bottom-xs">车型：中型车;</view>
				<view class="margin-bottom-xs">是否进口：进口;</view>
				<view class="margin-bottom-xs">驱动形式：前置后驱;</view>
				<view class="margin-bottom-xs">长*宽*高(mm)：4773*1852*1395;</view>
				<view class="margin-bottom-xs">车身结构：2门4座硬顶跑车;</view>
				<view class="margin-bottom-xs">上市时间：2020.09;</view>
			</view>
			<!--  -->

			<!-- 竞拍须知 -->
			<view class="cu-bar  bg-white solid-top" >
				<view class="action border-title">
					<text class="text-xl text-bold text-black">竞拍须知</text>
					<text class="bg-gradual-orange" style="width:3rem"></text>
				</view>
			</view>
			<view class="margin-lr text-sm">
				<view class="margin-bottom-xs">1. 拍卖开始后也可以支付保证金；</view>
				<view class="margin-bottom-xs">2. 建议在开拍一天前支付保证金报名，以免错过拍卖；</view>
				<view class="margin-bottom-xs">3. 如果没有获拍，保证金将在24小时内原路返还，具体到账时间需要以银行信息为准。</view>
			</view>
			<!--  -->

			<!-- 竞拍流程 -->
			<view >
				<view class="cu-bar  bg-white solid-top">
					<view class="action border-title">
						<text class="text-xl text-bold text-black">竞拍流程</text>
						<text class="bg-gradual-orange" style="width:3rem"></text>
					</view>
				</view>
				<view class="bg-white padding-lr padding-bottom">
					<view class="cu-steps">
						<view class="cu-item " v-for="(auitem,auindex) in auctionList" :key="auindex">
							<text class=" text-red" :class="'cuIcon-' + auitem.cuIcon"></text>
							<text class=" text-black text-sm"> {{auitem.name}}</text>
						</view>
					</view>
				</view>
			</view>
			<!--  -->

		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				auctionList: [{
					cuIcon: 'rechargefill',
					name: '交保证金'
				}, {
					cuIcon: 'vipcard',
					name: '参与竞拍'
				}, {
					cuIcon: 'appreciatefill',
					name: '竞拍成功'
				}, {
					cuIcon: 'pay',
					name: '支付尾款'
				}, {
					cuIcon: 'roundcheckfill',
					name: '确认收货'
				}, {
					cuIcon: 'sponsor',
					name: '返还保金'
				}],
				dotStyle: true,
				cardCur: 0,
				swiperList: [
					'https://www.bmw.com.cn/content/dam/bmw/marketCN/common/all-models/m-series/m4-coupe/2020/20201118/1680756/kv.jpg/_jcr_content/renditions/cq5dam.resized.img.1680.large.time1605679494077.jpg',
					'https://www.bmw.com.cn/content/dam/bmw/marketCN/common/all-models/m-series/m4-coupe/2020/20201118/inform/gallery/890501/5.jpg/_jcr_content/renditions/cq5dam.resized.img.890.medium.time1605761191769.jpg',
					'https://www.bmw.com.cn/content/dam/bmw/marketCN/common/all-models/m-series/m4-coupe/2020/20201118/inform/lunbo/3.jpg/_jcr_content/renditions/cq5dam.resized.img.1185.large.time1605761708403.jpg'
				],
				// 是否可以报价
				enableAut: false,
				// 是否支付保证金
				eaMoney: false,
				// 我的报价
				myPrice: 0,
				nBoxVal: 1000,
				dispConfirmModal:false,
			}
		},
		onLoad(options) {

			
		},
		methods: {
			// 查看图片
			ViewSwiperImage(e) {
				uni.previewImage({
					urls: this.swiperList,
					current: e.currentTarget.dataset.url
				});
			},
			// 提交保证金确认
			acceptToAuc() {

			},
			eaMoneyModal(e) {
				this.eaMoney = true
			},
			hideModal(e) {
				this.eaMoney = false
				this.dispConfirmModal =false
			},
			// 确定提交完成保证金
			eaMoneyConfirm() {
				this.enableAut = true;
				this.hideModal();
			},
			// 我的竞价变化
			nBoxValChange(e) {
				this.myPrice = 820000 + e.value
			},
			// 确认报价并提交
			myPriceConfirm(){
				this.hideModal();
				uni.showToast({
					title:'报价提交成功！'
				})
			}
		}
	}
</script>

<style>

</style>
