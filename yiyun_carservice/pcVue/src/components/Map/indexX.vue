<template>
  <div>
    <div id="containerX"></div>
    <div id="panelX" v-show="false"></div>
  </div>
</template>
<script>
  import AMapLoader from '@amap/amap-jsapi-loader';

  export default {
    name: "MapX",
    props: {
      mapInfo: {
        type: Object
      }
    },
    watch: {
      /*mapInfo: {
        handler(newValue, oldValue) {
          console.log("mapInfo改变",newValue,oldValue)
          setTimeout(() =>{
            console.log("父组件传来的值",this.mapInfo)
            if (this.mapInfo.lnglat) {
              this.marker = new AMap.Marker({
                position: this.mapInfo.lnglat,   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9
              });
            }
          },500)
        },
        deep: true
      },*/

    },
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
        markerList: [],
        map: null,
        marker: ''
      };
    },
    created() {
      // this.init()
    },
    beforeDestroy() {
      console.log('beforeDestroy 销毁前状态===============》');
      this.destroy()
    },
    mounted() {
      /*AMapLoader.load({
        "key": "1fe29e41556a14c6dd97d661e84f6061",   // 申请好的Web端开发者Key，首次调用 load 时必填
        "version": "2.0",   // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        "plugins": ['AMap.PlaceSearch', 'AMap.AutoComplete', 'AMap.Marker', 'AMap.Geocoder', 'AMap.MouseTool']  //插件列表
      }).then((AMap) => {
        console.log("地图加载。。。")
        this.map = new AMap.Map("container", {
          resizeEnable: true
        });
        //添加地图单击事件
        this.map.on('click', this.showInfoClick);
      }).catch(e => {
        console.log(e);
      })*/
      /*  console.log("父组件传来的值",this.mapInfo)
        if (this.mapInfo.lnglat) {
          this.addSiteMark(this.mapInfo.lnglat, '')
        }*/
    },
    methods: {
      init() {
        AMapLoader.load({
          "key": "1fe29e41556a14c6dd97d661e84f6061", // 申请好的Web端开发者Key，首次调用 load 时必填
          "version": "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
          "plugins": ['AMap.Driving'] //插件列表
        }).then((AMap) => {
          console.log("地图加载。。。")
          this.map = new AMap.Map("containerX", {
            resizeEnable: true,
            // center: [116.397428, 39.90923], //地图中心点
            zoom: 13 //地图显示的缩放级别
          });

          // 开始改造

          //构造路线导航类
          let driving = new AMap.Driving({
            map: this.map,
            panel: "panelX"
          });
          let startLngLat = '';
          let endLngLat = '';
          let ways = [];
          if (this.markerList.length > 0) {
            startLngLat = new AMap.LngLat(parseFloat(this.markerList[0].latlng[0]), parseFloat(this.markerList[0].latlng[1]));
            endLngLat = new AMap.LngLat(parseFloat(this.markerList[this.markerList.length - 1].latlng[0]), parseFloat(this.markerList[this.markerList.length - 1].latlng[1]));
            if (this.markerList.length > 2) {
              for (let i = 1; i < this.markerList.length - 1; i++) {
                ways.push(new AMap.LngLat(parseFloat(this.markerList[i].latlng[0]), parseFloat(this.markerList[i].latlng[1])))
              }
            }
          }
          // 根据起终点经纬度规划驾车导航路线
          // driving.search(new AMap.LngLat(118.400840, 31.319420), new AMap.LngLat(118.425701, 31.359596), {
          //   waypoints: [new AMap.LngLat(118.399675, 31.337896)]
          // }, function (status, result) {
          //   // result 即是对应的驾车导航信息，相关数据结构文档请参考  https://lbs.amap.com/api/javascript-api/reference/route-search#m_DrivingResult
          //   if (status === 'complete') {
          //     console.success('绘制驾车路线完成')
          //   } else {
          //     console.error('获取驾车数据失败：' + result)
          //   }
          // });
          driving.search(startLngLat, endLngLat, {
            waypoints: ways
          }, function (status, result) {
            // result 即是对应的驾车导航信息，相关数据结构文档请参考  https://lbs.amap.com/api/javascript-api/reference/route-search#m_DrivingResult
            if (status === 'complete') {
              console.log('绘制驾车路线完成')
            } else {
              console.log('获取驾车数据失败：' + result)
            }
          });

          // 改造结束


        }).catch(e => {
          console.log(e);
        })
      },
      destroy() {
        // 组件销毁前可销毁地图实例
        this.map && this.map.destroy()
      },

      // 创建点覆盖物
      getMarks(marks) {
        console.log("是否进来规划路线地图");
        console.log(marks);
        if (this.markerList.length > 0) {
          this.removeMarks()
        }
        this.markerList = marks;
        this.init();
        console.log(this.markerList);
      },

      removeMarks() {
        this.map.remove(this.markerList);
        this.markerList = []
      },
      showFnc() {
        console.log("成功调用")
      }

    },
  }
</script>
<style>
  #containerX {
    width: 100%;
    height: 400px;
  }
</style>
