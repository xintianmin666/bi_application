<template>
	<view>
		<view class="mainBox">
			<!-- 商品展示轮播图 -->
			<view class="solid-top bg-white">
				<swiper class="screen-swiper" :class="dotStyle?'square-dot':'round-dot'" :indicator-dots="true" :circular="true"
				 :autoplay="true" interval="5000" duration="500">
					<swiper-item v-for="(item,index) in swiperList" :key="index" @tap="ViewSwiperImage" :data-url="item">
						<image :src="item" mode="aspectFill" ></image>
					</swiper-item>
				</swiper>
			</view>
			<!-- 商品名称与价格 -->
			<view class=" padding bg-white " style="border-radius: 0 0 20rpx 20rpx;">
				<view class="flex justify-between align-center">
					<view class="text-red text-bold">¥<text class="text-xxl">184.90</text></view>
					<view class="action text-center text-lg" :class="getFavor==true?'text-red':''" @tap="favorChange">
						<view :class="getFavor==true?'cuIcon-favorfill':'cuIcon-favor'" ></view>
						<text class="text-sm">{{getFavor==true?'已收藏':'收藏'}}</text>
					</view>
				</view>
				<view class="margin-tb-sm flex justify-between align-center">
					<view>
						<view class="cu-tag bg-orange light radius text-df">竞赛级刹车套件</view>
						<view class="cu-tag bg-orange light radius text-df">进口性能车</view>
						<view class="cu-tag bg-orange light radius text-df">双门</view>
					</view>
				</view>
				<view class="text-black text-bold text-lg">巴伐利亚工厂生产（BMW THE 4 Series）宝马4系 M Power 原装海运进口双门性能车</view>
			</view>
			<!-- 商品详情 -->
			<view class="bg-white margin-tb-sm padding-bottom">
				<!-- 商品介绍 -->
				<view class="margin-bottom-sm">
					<!-- title -->
					<view class="cu-bar  bg-white">
						<view class="action border-title">
							<text class="text-xl text-bold text-black">商品介绍</text>
							<text class="bg-gradual-orange" style="width:3rem"></text>
						</view>
					</view>
					<!-- content -->
					<view class="margin-lr" style="letter-spacing: .1em;text-indent: 1.5em;">
						全新BMW M4双门轿跑车 雷霆版的外观设计更具运动气质。全新设计的无边框双肾型进气格栅，营造强烈的视觉冲击力。简洁的车身线条勾勒出灵动的轮廓，在不经意间，迸发强势本色。
					</view>
				</view>
				<!-- 规格参数 -->
				<view class="margin-bottom-sm">
					<!-- title -->
					<view class="cu-bar  bg-white">
						<view class="action border-title">
							<text class="text-xl text-bold text-black">规格参数</text>
							<text class="bg-gradual-orange" style="width:3rem"></text>
						</view>
					</view>
					<!-- content -->
					<view class="margin-lr">
						<view>全新BMW M4双门轿跑车 雷霆版 *¥ 888,900 </view>
						<view>全新BMW M4双门轿跑车 M xDrive 雷霆版 *¥ 938,900 </view>
					</view>
				</view>
				<!-- 售后保障 -->
				<view>
					<!-- title -->
					<view class="cu-bar  bg-white">
						<view class="action border-title">
							<text class="text-xl text-bold text-black">售后保障</text>
							<text class="bg-gradual-orange" style="width:3rem"></text>
						</view>
					</view>
					<!-- content -->
					<view  class="margin-lr">
						如果您有任何需要或问题，欢迎拨打BMW 客户服务中心的热线：400-800-6666，或发送邮件至servicecenter@bmw.com.cn与我们联系，BMW 客户服务中心工作时间: 7 X 24 小时。
					</view>
				</view>
			</view>
			
			<!-- 底部操作台 -->
			<view class="botBar cu-bar bg-white tabbar border shop margin-top-sm">
				<view class="action">
					<view class=" cuIcon-shop">
						<view class="cu-tag badge"></view>
					</view> 
					商家店铺
				</view>
				<!-- <view class="action" :class="getFavor==true?'text-red':''" @tap="getFavor = !getFavor">
					<view :class="getFavor==true?'cuIcon-favorfill':'cuIcon-favor'"></view>
					{{getFavor==true?'已收藏':'加入收藏'}}
				</view> -->
				<button class="action" >
					<view class="cuIcon-cart">
						<view class="cu-tag badge " v-if="carts>0">{{carts}}</view>
					</view>
					购物车
				</button>
				<view class="btn-group">
					<button class="cu-btn bg-orange  round shadow" @tap="carts+=1">加入购物车</button>
					<button class="cu-btn bg-red  round shadow">立即订购</button>
				</view>
			</view>
		
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				dotStyle: true,
				cardCur: 0,
				swiperList: [
					'https://www.bmw.com.cn/content/dam/bmw/marketCN/common/all-models/m-series/m4-coupe/2020/20201118/1680756/kv.jpg/_jcr_content/renditions/cq5dam.resized.img.1680.large.time1605679494077.jpg',
					'https://www.bmw.com.cn/content/dam/bmw/marketCN/common/all-models/m-series/m4-coupe/2020/20201118/inform/gallery/890501/5.jpg/_jcr_content/renditions/cq5dam.resized.img.890.medium.time1605761191769.jpg',
					'https://www.bmw.com.cn/content/dam/bmw/marketCN/common/all-models/m-series/m4-coupe/2020/20201118/inform/lunbo/3.jpg/_jcr_content/renditions/cq5dam.resized.img.1185.large.time1605761708403.jpg'
				],
				getFavor:false,
				carts:0,
			}
		},
		methods: {
			// 查看图片
			ViewSwiperImage(e) {
				uni.previewImage({
					urls: this.swiperList,
					current: e.currentTarget.dataset.url
				});
			},
			// 是否收藏
			favorChange(){
				this.getFavor = !this.getFavor;
				const tips = this.getFavor == true?'收藏成功':'取消收藏成功'
				uni.showToast({
					title:tips
				})
			}
		}
	}
</script>

<style>
page {
		background: #F1F1F1;
	}
	.botBar{
		position: fixed;
		left: 0;
		right: 0;
		bottom: 0;
		width: 100%;
	}
	.mainBox{
		padding-bottom: 110rpx;
	}
</style>
