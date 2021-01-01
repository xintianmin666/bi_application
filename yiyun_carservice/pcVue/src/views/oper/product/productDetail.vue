<template>
  <div class="app-container">
    <el-container>
      <el-header>
        <el-row>
          <el-col :span="24">
            <span style="font-size: 26px">{{productDetail.productName}}</span>
            <span style="font-size: 16px">{{productDetail.productCode}}</span>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <div class="top">
          <el-row type="flex"style="border-bottom:1px solid rgba(83,83,83,0.2);border-top:1px solid rgba(83,83,83,0.2);padding: 10px 0 " :gutter="20">
            <el-col :span="6" >
              <div class="topPicB">
                <div class="block" >
                  <el-carousel height="250px">
                    <el-carousel-item v-for="item in picB" :key="item">
                      <el-image style="width: 100%;height: 100%"  :src=baseAPI+item></el-image>
                    </el-carousel-item>
                  </el-carousel>
                </div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="topPicC">
                <div class="block">
                  <el-carousel height="250px">
                    <el-carousel-item v-for="item in picC" :key="item">
                      <el-image  style="width: 100%;height: 100%" :src=baseAPI+item></el-image>
                    </el-carousel-item>
                  </el-carousel>
                </div>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="topPrice" ref="priceTableHeight" style="height: 100%">
                <el-table
                  height="250"
                  :data="productPriceList"
                  border
                  style="width: 100%">
                  <el-table-column label="线路运力价格表" align="center">
                    <el-table-column
                      prop="vehicleType.typeName"
                      label="车型"
                      width="80">
                    </el-table-column>
                    <el-table-column
                      prop="vehicleType.passengerNum"
                      label="座位数"
                      width="80">
                    </el-table-column>
                    <el-table-column
                      prop="highSpeedSetting"
                      label="高速设置"
                      width="120" :formatter="highSpeedSettingFormat">
                    </el-table-column>
                    <el-table-column
                      prop="pchePermission"
                      label="可拼" width="100" :formatter="pchePermissionFormat">
                    </el-table-column>
                    <el-table-column
                      prop="pcheTocPrice"
                      label="散客拼车（元/座）" width="100">
                    </el-table-column>
                    <el-table-column
                      prop="pcheTobPrice"
                      label="B端拼车（元/座）" width="80">
                    </el-table-column>
                    <el-table-column
                      prop="bchePermission"
                      label="可包" width="100" :formatter="bchePermissionFormat">
                    </el-table-column>
                    <el-table-column
                      prop="bcheTocPrice"
                      label="散客包车（元/座）" width="100">
                    </el-table-column>
                    <el-table-column
                      prop="bcheTobPrice"
                      label="B端包车（元/座）" >
                    </el-table-column>
                  </el-table-column>
                </el-table>
              </div>
            </el-col>
          </el-row>

          <div style="margin-top: 30px">
            <span style="font-size: 20px;">产品详情</span>
            <Map ref="map" style="margin: 20px 0px"></Map>
            <div style="border:1px solid rgba(83,83,83,0.2);padding: 10px 5px;margin-top: 20px "  v-html="productDetail.productDetail"></div>
          </div>


          <div style="margin-top: 30px">
            <span style="font-size: 20px;">预订须知</span>
            <div style="border:1px solid rgba(83,83,83,0.2);padding: 10px 5px;margin-top: 20px "
                 v-html="productDetail.bookingGuide"></div>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>



<script>
  import {getProduct} from "@/api/oper/product";
  import Map from '@/components/Map/index';
    export default {
      name: "productDetail",
      components: {
        Map
      },
      data() {
        return {
          highSpeedSettingOptions:[],
          height:'',
          // 上传的图片服务器地址
          baseAPI: process.env.VUE_APP_BASE_API,
          productId: '',
          productDetail:'',
          productPriceList:[],
          productSiteList:[],
          picB:[],
          picC:[],
        }
      },
      watch: {
        '$route' (to, from) { // 监听路由是否变化
          if (to.query.productId !== from.query.productId) {
            this.productId = to.query.productId
            if (this.productId) {
              this.getproductDetail() // 重新加载数据
            }
          }
        }
      },
       mounted(){
         /*this.$refs.map.init()
         this.getMap()*/
         setTimeout(() =>{
           this.getMap()
                },1000)
      },
      created(){
        this.productId = this.$route.query.productId;
        this.getproductDetail()
        this.getDicts("high_speed_setting").then(response => {
          this.highSpeedSettingOptions = response.data;
        });
      },
      methods:{
        getMap(){
            this.$refs.map.isShow=false
            let path =[]
            for(let i = 0;i<this.productSiteList.length;i++){
              let siteInfo = this.productSiteList[i].siteInfo
              let lngLat =[]
              lngLat.push(siteInfo.lng)
              lngLat.push(siteInfo.lat)
              console.log(lngLat)
              this.$refs.map.addSiteMark(lngLat ,siteInfo.siteName)
              if(siteInfo.siteFence){
                this.$refs.map.drawPolygon1(siteInfo.siteFence)
              }
              path.push(lngLat)
            }
            this.$refs.map.drivingLine(path)
        },
        /** 查询产品详情 */
        getproductDetail() {
          getProduct(this.productId).then(response => {
            this.productDetail = response.data
            this.productPriceList = response.data.productPriceList
            this.productSiteList = response.data.productSiteList
            if (this.productDetail.productPicTob) {
              this.picB = this.productDetail.productPicTob.split(",")
            }
            if (this.productDetail.productPicToc) {
              this.picC = this.productDetail.productPicToc.split(",")
            }
            console.log(this.productDetail);
          });
        },
        highSpeedSettingFormat(row, column) {
          return this.selectDictLabel(this.highSpeedSettingOptions, row.highSpeedSetting);
        },
        pchePermissionFormat(row, column){
          return row.pchePermission=='1'?'是':'否'
        },
        bchePermissionFormat(row, column){
          return row.bchePermission=='1'?'是':'否'
        }

      }
    }
</script>

<style>
  .ql-align-center {
    text-align: center;
  }
  th.el-table_1_column_1.is-center {
    line-height: 40px;
    font-size: 20px;
  }

</style>
