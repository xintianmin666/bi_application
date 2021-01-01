<template>
  <div>
    <div id="container"></div>
  </div>
</template>
<script>
  import AMapLoader from '@amap/amap-jsapi-loader';

  export default {
    name: "Map",
    props: {
      mapInfo: {
        type: Object
      }
    },
    watch: {},
    data() {
      return {
        isShow: true,
        city: '芜湖市',
        addSiteMarkFlag: true,
        siteName: '',
        polygon: '',
        polygonArr: [],
        mouseTool: '',
        circle: '',
        placeSearch: null,
        markers: [],
        map: null,
        marker: '',
        markerList: [],
        getIds: [],
        productCodes: [],//产品id数组
        orderDates: [],
        useTimes: [],
        orderTypes: [],//订单类型数组
        useHours: []
      };
    },
    created() {
      this.init()
      /*setTimeout(() =>{
        this.addSiteMarks([{'latlng':[116.39, 39.9],'siteName':'beijing',"id":1},{'latlng':[116.19, 39.79],'siteName':'beijing1',"id":2}])
      },3000)*/
    },
    beforeDestroy() {
      console.log('beforeDestroy 销毁前状态===============》');
      this.destroy()
    },
    mounted() {
    },
    methods: {
      init() {
        AMapLoader.load({
          "key": "1fe29e41556a14c6dd97d661e84f6061",   // 申请好的Web端开发者Key，首次调用 load 时必填
          "version": "2.0",   // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
          "plugins": ['AMap.PlaceSearch', 'AMap.AutoComplete', 'AMap.Marker', 'AMap.Geocoder', 'AMap.MouseTool', 'AMap.DragRoute']  //插件列表
        }).then((AMap) => {
          console.log("地图加载。。。")
          this.map = new AMap.Map("container", {
            resizeEnable: true,
            zoom: 13
            // expandZoomRang: true,
            // zoom: 10,
          });
        }).catch(e => {
          console.log(e);
        })
      },

      showInfoClick(e) {
        for (var i = 0; i < this.markerList.length; i++) {
          // 获取存在每个 extData 中的 id
          var id = this.markerList[i].getExtData().id;
          if (id === e.target.getExtData().id) {
            if (!e.target.getExtData().isGet) {
              let icon = new AMap.Icon({
                image: '//a.amap.com/jsapi_demos/static/demo-center/icons/dir-via-marker.png',
                size: new AMap.Size(32, 38),  //图标所处区域大小
                imageSize: new AMap.Size(32, 38) //图标大小
              })
              this.markerList[i].setIcon(icon)
              this.markerList[i].getExtData().isGet = true
              this.getIds.push(id)
              // alert(this.markerList[i].getExtData().useTime)
              this.productCodes.push(this.markerList[i].getExtData().productCode)
              this.orderDates.push(this.markerList[i].getExtData().useTime.substring(0, 10))
              this.useTimes.push(this.markerList[i].getExtData().useTime)
              this.orderTypes.push(this.markerList[i].getExtData().orderType)
              this.useHours.push(this.markerList[i].getExtData().useHour)
            } else {
              let icon = new AMap.Icon({
                image: '//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png',
                size: new AMap.Size(32, 38),  //图标所处区域大小
                imageSize: new AMap.Size(32, 38) //图标大小
              })
              this.markerList[i].setIcon(icon)
              this.markerList[i].getExtData().isGet = false
              var index = this.getIds.indexOf(id);
              if (index > -1) {
                this.getIds.splice(index, 1);
                this.productCodes.splice(index, 1);
                this.orderDates.splice(index, 1);
                this.orderTypes.splice(index, 1);
                this.useHours.splice(index, 1)
              }
            }
            break;
          }
        }
        console.log("已选中的id", this.getIds)
        console.log("已选中的productCode", this.productCodes)
      },

      destroy() {
        // 组件销毁前可销毁地图实例
        this.map && this.map.destroy()
      },

      // 创建点覆盖物
      addSiteMarks(marks) {
        if (this.markerList.length > 0) {
          this.removeMarks()
        }
        for (let i = 0; i < marks.length; i++) {
          let marker = new AMap.Marker({
            icon: new AMap.Icon({
              image: '//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png',
              size: new AMap.Size(32, 38),  //图标所处区域大小
              imageSize: new AMap.Size(32, 38) //图标大小
            }),
            //icon: '//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png',
            position: marks[i].latlng,   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
            title: marks[i].siteName,
            extData: {
              id: marks[i].id,
              productCode: marks[i].productCode,
              orderType: marks[i].orderType,
              useTime: marks[i].useTime,
              useHour: marks[i].useHour,
              isGet: false
            }
          });
          console.log("客户数量" + marks[i].seatNum);
          marker.setLabel({
            offset: new AMap.Pixel(-1, -6),  //设置文本标注偏移量
            content: "<span style='color:white;font-weight:600;background: none;border: none'>" + marks[i].seatNum + "</span>",
            direction: 'center' //设置文本标注方位
          });
          marker.on('click', this.showInfoClick);
          this.markerList.push(marker)
        }
        console.log("添加覆盖点", this.marker)
        this.map.add(this.markerList);
        this.map.setFitView();
      },
      // 创建点覆盖物
      addSiteMark(lngLat, siteName) {
        if (this.markers.length > 0) {
          this.removeMarks()
        }
        this.marker = new AMap.Marker({
          icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
          position: lngLat,   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
          title: siteName
        });
        console.log("添加覆盖点", this.marker)
        this.map.add(this.marker);
      },
      removeMarks() {
        this.map.remove(this.markerList);
        this.markerList = []
        this.getIds = []
      },
      removeMark() {
        this.map.remove(this.marker);
      },

    },
  }
</script>
<style>
  #container {
    width: 100%;
    height: 400px;
  }

  .amap-marker-label {
    border: none !important;
    background: none !important;
  }
</style>
