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
        map: null,
        marker: '',
        markerList: [],
        getIds: [],
        productCodes: [],//产品id数组
        orderDates: [],
        useTimes: [],
        orderTypes: [],//订单类型数组
        useHours: [],
        placeSearch:'',
        lnglat:'',
      };
    },
    created() {
      this.init()
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
          "plugins": ['AMap.PlaceSearch', 'AMap.AutoComplete', 'AMap.Marker', 'AMap.Geocoder', 'AMap.MouseTool', 'AMap.DragRoute','AMap.Autocomplete']  //插件列表
        }).then((AMap) => {
          console.log("地图加载。。。")
          this.map = new AMap.Map("container", {
            resizeEnable: true,
            zoom: 13
          });
          //添加地图单击事件
          this.map.on('click', this.showInfoClick);
          this.placeSearch = new AMap.PlaceSearch({
            // city 指定搜索所在城市，支持传入格式有：城市名、citycode和adcode
            city:'芜湖',
          })
        }).catch(e => {
          console.log(e);
        })
      },

      searchAndMarkSite(val){
        this.removeMarks();
        this.removeMark();
        this.placeSearch.search(val, (status, result)=> {
          // 查询成功时，result即对应匹配的POI信息
          console.log(result)
          var pois = result.poiList.pois;
          for(var i = 0; i < pois.length; i++){
            var poi = pois[i];
            this.markerList[i] = new AMap.Marker({
              position: poi.location,   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
              title: poi.name
            });
            this.markerList[i].on('click', (e) => {
              this.addSiteMark(e.lnglat, this.siteName)
            })
            // 将创建的点标记添加到已有的地图实例：
            this.map.add(this.markerList[i]);
          }
          this.map.setFitView();
        })
      },

      showInfoClick(e) {
        this.removeMarks();
        this.removeMark();
        this.addSiteMark(e.lnglat, this.siteName)
        this.lnglat = e.lnglat
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
            /*icon: new AMap.Icon({
              image: '//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png',
              size: new AMap.Size(32, 38),  //图标所处区域大小
              imageSize: new AMap.Size(32, 38) //图标大小
            }),*/
            icon: '//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png',
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
        this.removeMarks()
        this.removeMark()
        this.marker = new AMap.Marker({
          icon: new AMap.Icon({
            image: '//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png',
            size: new AMap.Size(28, 38),  //图标所处区域大小
            imageSize: new AMap.Size(28, 38), //图标大小
          }),
          position: lngLat,   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
          offset: new AMap.Pixel(-14, -38),
        });
        console.log("添加覆盖点", this.marker)
        //this.map.add(this.marker);
        this.marker.setMap(this.map);
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
