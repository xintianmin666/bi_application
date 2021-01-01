<template>
	<view>
		<!-- list -->
		<view class="bg-white padding" v-if="carList.length>0">
			<!-- 店铺 -->
			<view class="margin-bottom" v-for="(shopItem,shopIndex) in carList" :key="shopIndex">
				<!-- 商铺名称 -->
				<view class="flex  align-center ">
					<view class="" @tap="shopSelect(shopItem,shopIndex)">
						<image src="/static/image/circleCacheef.png" class="circle" v-if="shopItem.selected==true"></image>
						<image src="/static/image/circleCachee.png" class="circle" v-else></image>
					</view>
					<view class="text-lg text-bold margin-left solid-bottom">
						<text class="cuIcon-shop text-red padding-right-xs"></text>
						<text>{{shopItem.title?shopItem.title:'上海大众333车队专营店'}} <text class="cuIcon-right text-red  padding-left-xs"></text></text>
					</view>
				</view>
				<!-- 详细商品 -->
				<view class="solid-bottom" v-for="(gItem,gIndex) in shopItem.glist" :key="gIndex">
					<view class="flex margin-top margin-bottom-sm align-center">
						<view @tap="goodsSelect(shopIndex,gIndex)">
							<image src="/static/image/circleCacheef.png" class="circle" v-if="gItem.selected==true"></image>
							<image src="/static/image/circleCachee.png" class="circle" v-else></image>
						</view>
						<view class="padding-lr flex">
							<!-- 商品图片 -->
							<view class="">
								<image :src="imgUrl" class="goodsImg radius" mode="aspectFill"></image>
							</view>
							<!-- 商品详情 名称+型号 -->
							<view class="margin-left-sm">
								<view class="text-twoCut ">{{gItem.name?gItem.name:'iDrive 全时四驱 竞技悬挂 赛用刹车套件(碳陶瓷)'}}</view>
								<view class="cu-tag bg-gray text-bold margin-top-sm">{{gItem.remark?gItem.remark:'M4 Competition'}}</view>
							</view>
						</view>
					</view>
					<!-- 商品价格 单价 + 数量步进器 -->
					<view class="text-right flex align-center justify-end padding-bottom-sm">
						<view class="margin-right-sm text-red text-bold">¥<text class=" text-xl padding-left-xs">{{gItem.price}}</text>.00</view>
						<view class="">
							<u-number-box v-model="gItem.number" @change="nBoxValChange" :step="1" :min="1" color="#333333" bg-color="#f0f0f0"
							 size="28" :disabled-input="true" :long-press="false"></u-number-box>
						</view>
					</view>
				</view>

			</view>
		</view>
		<!-- 内容为空时显示 -->
		<view v-else>
			<u-empty mode="car"></u-empty>
		</view>

		<!-- 底部结算模块 -->
		<view class="footerBar flex justify-between align-center padding-lr bg-white">
			<view @tap="allGoodSelect">
				<image class="margin-right-sm" src="/static/image/circleCacheef.png" v-if="allSelect==true"></image>
				<image class="margin-right-sm" src="/static/image/circleCachee.png" v-else></image>
				<text>全选</text>
			</view>
			<view class="align-center flex">
				<view class="text-bold text-xl text-red margin-right"><text class="text-lg">￥</text>{{goodsAmount}}</view>
				<button class="cu-btn bg-gradual-red text-bold lg" style="min-width: 240rpx;">去结算</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 我的报价
				myPrice: 0,
				nBoxVal: 1,
				allSelect: false,
				// 
				selected: false,
				imgUrl: 'https://www.bmw.com.cn/content/dam/bmw/marketCN/common/all-models/m-series/m4-coupe/2020/20201118/inform/gallery/890501/5.jpg/_jcr_content/renditions/cq5dam.resized.img.890.medium.time1605761191769.jpg',
				totalPrice: 0,
				goodsAmount: 0,
				carList: [{
						shopId: 1,
						title: "上海大众333车队A队",
						total: 2,
						goodsAmount: 6051,
						selected: false,
						glist: [{
								id: 236,
								gid: 47,
								name: "iDrive 全时四驱 竞技悬挂 赛用刹车套件(碳陶瓷)",
								img: "https://www.bmw.com.cn/content/dam/bmw/marketCN/common/all-models/m-series/m4-coupe/2020/20201118/inform/gallery/890501/5.jpg/_jcr_content/renditions/cq5dam.resized.img.890.medium.time1605761191769.jpg",
								gsId: 72,
								remark: "M4 Competition",
								price: 20000,
								number: 1,
								selected: false,
							},
							{
								id: 237,
								gid: 45,
								name: "M Power专属空气套件",
								img: "https://www.bmw.com.cn/content/dam/bmw/marketCN/common/all-models/m-series/m4-coupe/2020/20201118/inform/lunbo/3.jpg/_jcr_content/renditions/cq5dam.resized.img.1185.large.time1605761708403.jpg",
								gsId: 72,
								remark: "M Performance",
								price: 13000,
								number: 1,
								selected: false,
							}
						]
					},
					{
						shopId: 2,
						title: "上海红牛888车队S队",
						total: 2,
						goodsAmount: 7777,
						selected: false,
						glist: [{
								id: 236,
								gid: 47,
								name: "梅赛德斯奔驰 AMG GT",
								img: "https://www.bmw.com.cn/content/dam/bmw/marketCN/common/all-models/m-series/m4-coupe/2020/20201118/inform/gallery/890501/5.jpg/_jcr_content/renditions/cq5dam.resized.img.890.medium.time1605761191769.jpg",
								gsId: 72,
								remark: "AMG 高端性能车",
								price: 24000,
								number: 1,
								selected: false,
							},
							{
								id: 237,
								gid: 45,
								name: "梅赛德斯奔驰 AMG GTR",
								img: "https://www.bmw.com.cn/content/dam/bmw/marketCN/common/all-models/m-series/m4-coupe/2020/20201118/inform/lunbo/3.jpg/_jcr_content/renditions/cq5dam.resized.img.1185.large.time1605761708403.jpg",
								gsId: 72,
								remark: "AMG 高端性能车",
								price: 28000,
								number: 1,
								selected: false,
							}
						]
					}
				]
			}
		},
		methods: {
			// start
			// 商品数量变化
			nBoxValChange(e) {
				// this.myPrice = 820000 + e.value
				this.getAllSelectAmount();
			},
			// 单个商铺选择
			shopSelect(item, index) {
				const isAllShopSelect = true;
				// 确认选择商铺
				this.carList[index].selected = !this.carList[index].selected
				this.carList[index].glist.forEach(good => {
					good.selected = this.carList[index].selected;
				})
				// 判断当前商铺的勾选状态改变是否影响全选的勾选
				let allShopsSelect = true;
				this.carList.forEach(shop => {
					if (shop.selected == false) {
						allShopsSelect = false
					}
				})
				allShopsSelect == true ? this.allSelect = true : this.allSelect = false
				this.getAllSelectAmount();
			},
			// 单个商品选择
			goodsSelect(sindex, gindex) {
				this.carList[sindex].glist[gindex].selected = !this.carList[sindex].glist[gindex].selected
				this.checkAllselect(sindex);
				this.getAllSelectAmount();
				
			},
			// 全选商品
			allGoodSelect() {
				this.goodsAmount = 0;
				this.allSelect = !this.allSelect;
				const isSelect = this.allSelect
				this.carList.forEach(shop => {
					shop.selected = isSelect;
					shop.glist.forEach(good => {
						good.selected = isSelect;
						this.goodsAmount += good.number * good.price;
					})
				})
				if (isSelect == false) {
					this.goodsAmount = 0
				}

			},
			// 计算所有已勾选的商品的价格
			getAllSelectAmount(list) {
				this.goodsAmount = 0
				this.carList.forEach(shop => {
					shop.glist.forEach(good => {
						// good.selected = isSelect;
						if (good.selected == true) {
							this.goodsAmount += good.number * good.price;
						}
					})
				})
			},
			// 检测是否所有商品都已勾选
			checkAllselect(index){
				// 判断当前商品的勾选状态改变是否影响商铺的勾选
				let allGoodsSelect = true;
				this.carList[index].glist.forEach(good => {
					if (good.selected == false) {
						allGoodsSelect = false
					}
				})
				allGoodsSelect== true ? this.carList[index].selected = true : this.carList[index].selected = false
				// 判断当前商铺的勾选状态改变是否影响全选的勾选
				let allShopsSelect = true;
				this.carList.forEach(shop => {
					if (shop.selected == false) {
						allShopsSelect = false
					}
				})
				allShopsSelect == true ? this.allSelect = true : this.allSelect = false
			},

			// end
			//计算总价 复制
			calculate: function() {

				let total = 0,
					num = 0,
					goods = []


				this.goodsList.forEach(e => {
					e.goods.forEach(item => {
						if (item.checked) {
							total += item.totalPrice
							num += 1

						}
					})

				})
				let data = this.goodsList
				for (let i = 0; i < data.length; i++) {
					let obj = {
						"storeId": '',
						"couponIds": "",
						"orderGoodsAndNum": [],
					}
					obj.storeId = data[i].storeId
					for (let j = 0; j < data[i].goods.length; j++) {
						if (data[i].goods[j].checked) {
							obj.orderGoodsAndNum.push({
								"goodsId": data[i].goods[j].goodsId,
								"num": data[i].goods[j].number
							})
						}
					}
					if (obj.orderGoodsAndNum.length != 0) {
						goods.push(obj)
					}
				}
				this.goodsArr = goods

				this.totalPrice = total
				this.goodsAmount = num
			},
			//选择商品  复制
			RadioChange: function(type, index, i) {
				switch (type) {
					case 'parent':
						this.$set(this.goodsList[index], 'checked', !this.goodsList[index].checked)
						if (!this.goodsList[index].checked) {
							this.goodsList[index].goods.forEach(item => {
								item.checked = false
							})
						} else {
							this.goodsList[index].goods.forEach(item => {
								item.checked = true
							})
						}
						break;
					case 'child':

						this.$set(this.goodsList[index].goods[i], 'checked', !this.goodsList[index].goods[i].checked)
						if (!this.goodsList[index].goods[i].checked) {
							this.$set(this.goodsList[index], 'checked', false)
						}
						this.goodsList.forEach(e => {
							let num = 0
							this.goodsList[index].goods.forEach(item => {
								if (item.checked) {
									num += 1
								} else {
									this.radio = true
								}
							})
							if (num == this.goodsList[index].goods.length) {
								this.$set(this.goodsList[index], 'checked', true)
							}
						})
						break;
				}
				console.log(this.goodsList)
				this.calculate()
			},
			//全选 复制
			RadioChangeAll() {
				this.radio = !this.radio
				this.goodsList.forEach((e, index) => {
					this.$set(this.goodsList[index], 'checked', !this.goodsList[index].checked)
				})
				this.calculate()
			},
			changeGoodsNumber: function(type, index, item) {

				if (type == 'add') {
					this.goodsList[index].goods[item].number += 1
					console.log('+', this.goodsList[index].goods[item].number)

				} else {
					if (this.goodsList[index].goods[item].number == 1) {
						/* 弹框提示不能小于0 */
						return
					}
					this.goodsList[index].goods[item].number -= 1
					console.log('-', this.goodsList[index].goods[item].number)
				}
				this.goodsList[index].goods[item].totalPrice = this.goodsList[index].goods[item].number * this.goodsList[index].goods[
					item].price
				//console.log(this.goodsList[index].goods[item].totalPrice)
				this.calculate()
			},
			/* 获得购物车列表 复制 */
			getCartData: function() {
				api.shoppingCartList({}, {
					header: {
						Authorization: uni.getStorageSync('user_token'),
						"content-type": 'application/x-www-form-urlencoded'
					}
				}).then(res => {
					console.log('【接口】购物车列表', res)

					if (res.data.code === 200) {
						let data = res.data.data
						data.forEach(e => {
							let goodsItem = []
							e['tbUserGoodsShoppingCarVo'].forEach(item => {
								goodsItem.push({
									cartId: item.id, //购物车id
									goodsId: item.goodsId,
									goodsName: item.goodsVo.goodsName, //商品名称
									price: item.goodsVo.price, //单价
									number: item.number, //数量
									totalPrice: item.goodsVo.price * item.number, //商品总价
									checked: false
								})
							})
							this.goodsList.push({
								storeName: e.storeName, //商店名称
								storeId: e.storeId, //商店ID
								storeImg: '', //商店头像
								goods: goodsItem, //商品列表
								checked: false

							})
						})
						console.log('当前购物车', this.goodsList)
					}
				}).catch(err => {
					console.log(err)
				})
			},
			//创建订单  复制
			creatOrder: function() {
				let data = this.goodsArr
				this.$api.createOrder({
					"orderGoods": data,
					"paymentMethod": 0,
					"userAddressId": "469001380" //"海南省五指山市毛阳镇上的擦桌子幸存者"
				}, {
					header: {
						Authorization: this.$getLocal('user_token')
					}
				}).then(res => {
					console.log('【接口】创建订单', res)
					if (res.data.code == 200) {
						uni.navigateTo({
							url: '../orderPay/orderPay'
						})
					} else {
						uni.showToast({
							title: res.data.msg,
							duration: 2000
						})
					}
				}).catch(err => console.log(err))

			},

		},
	}
</script>

<style>
	page {
		background-color: #ededed;
		padding-bottom: 100rpx;
	}

	.circle {
		width: 48rpx;
		height: 48rpx;
	}

	/* 底部样式 */
	.footerBar {
		width: 100%;
		height: 98rpx;
		position: fixed;
		bottom: 0rpx;
		left: 0rpx;
		display: flex;
		z-index: 1001;
		border: 1rpx solid #e0e0e0;
	}

	.footerBar image {
		width: 44rpx;
		height: 44rpx;
		vertical-align: middle;
		margin-top: -8rpx;
	}

	.goodsImg {
		width: 180rpx;
		height: 180rpx;
	}

	.text-twoCut {
		text-overflow: -o-ellipsis-lastline;
		overflow: hidden;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-line-clamp: 2;
		line-clamp: 2;
		-webkit-box-orient: vertical;
	}
</style>
