<template>
  <div>
    <div id="container"></div>
    <div id="myPageTop" style="position: absolute;right:30px;top:100px;" v-show="isShow">
      <el-input v-model="siteName" placeholder="请输入内容" @change="getSiteMark"></el-input>
      <button class="btn" @click="removeAll" style="margin-bottom: 5px">清除覆盖物</button>
    </div>
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
  watch:{
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
      isShow:true,
      city:'芜湖市',
      addSiteMarkFlag:true,
      siteName:'',
      polygon:'',
      polygonArr: [],
      mouseTool: '',
      circle: '',
      placeSearch: null,
      markers: [],
      map: null,
      marker:''
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
    init(){
      AMapLoader.load({
        "key": "1fe29e41556a14c6dd97d661e84f6061",   // 申请好的Web端开发者Key，首次调用 load 时必填
        "version": "2.0",   // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        "plugins": ['AMap.PlaceSearch', 'AMap.AutoComplete', 'AMap.Marker', 'AMap.Geocoder', 'AMap.MouseTool','AMap.DragRoute']  //插件列表
      }).then((AMap) => {
        console.log("地图加载。。。")
        this.map = new AMap.Map("container", {
          resizeEnable: true
        });
        //添加地图单击事件
        this.map.on('click', this.showInfoClick);
      }).catch(e => {
        console.log(e);
      })
    },
    destroy(){
      // 组件销毁前可销毁地图实例
      this.map && this.map.destroy()
    },
//绘制多边形
    drawPolygon() {
      this.polygonArr = []
      console.log('绘制多边形')
      this.removeMouseTool(true)
      this.mouseTool = new AMap.MouseTool(this.map)
      this.mouseTool.polygon({
        strokeColor: "#FF33FF",
        strokeOpacity: 1,
        strokeWeight: 6,
        strokeOpacity: 0.2,
        fillColor: '#1791fc',
        fillOpacity: 0.4,
        // 线样式还支持 'dashed'
        strokeStyle: "solid",
        // strokeStyle是dashed时有效
        // strokeDasharray: [30,10],
      })
      this.mouseTool.on('draw', (event) => {
        //this.polygonArr.push(event.obj);
        // event.obj 为绘制出来的覆盖物对象
        console.log('覆盖物对象绘制完成经纬度', this.polygonArr)
      })
    },
    //添加点半径
    addSiteCircle(lng, lat,radius) {
      if (this.circle) {
        console.log("删除点半径")
        this.removeMarkCircle()
      }
      this.circle = new AMap.Circle({
        center: [lng, lat],
        radius: radius, //半径
        borderWeight: 3,
        strokeColor: "#FF33FF",
        strokeOpacity: 1,
        strokeWeight: 6,
        strokeOpacity: 0.2,
        fillOpacity: 0.4,
        strokeStyle: 'dashed',
        strokeDasharray: [10, 10],
        // 线样式还支持 'dashed'
        fillColor: '#1791fc',
        zIndex: 50,
      })
      setTimeout(() =>{
        this.circle.setMap(this.map)
        console.log("添加点半径")
        // 缩放地图到合适的视野级别
        this.map.setFitView([this.circle])
      }, 500);

    },
    getSiteNameByLngLat(lnglat) {
      let geocoder = new AMap.Geocoder({
        city: "0553", //城市设为北京，默认：“全国”
        radius: 1000 //范围，默认：500
      });
      geocoder.getAddress(lnglat, (status, result) => {
        if (status === 'complete' && result.regeocode) {
          this.siteName =  result.regeocode.formattedAddress;
        } else {
          console.log('根据经纬度查询地址失败')
        }
      });
    },

    // 创建点覆盖物
    addSiteMark(lngLat, siteName) {
      if (this.marker) {
        this.removeMark()
      }
      if (this.markers.length > 0) {
        this.removeMarks()
      }
      this.marker = new AMap.Marker({
        position: lngLat,   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
        title: siteName
      });
      console.log("添加覆盖点", this.marker)
      this.map.add(this.marker);
    },
    //点击地图显示位置


     showInfoClick(e) {
      var text = '您在 [ ' + e.lnglat.getLng() + ',' + e.lnglat.getLat() + ' ] 的位置单击了地图！'
      console.log("showInfoClick", e)
      this.siteName = this.getSiteNameByLngLat(e.lnglat)
       if (this.addSiteMarkFlag) {
         this.addSiteMark(e.lnglat, this.siteName)
         setTimeout(() =>{
           console.log(this.siteName)
           this.$emit('lngLatInfoFromMap',{lnglat:e.lnglat,siteName:this.siteName})
         }, 500);
       }else{
         this.polygonArr.push(e.lnglat);
       }
    },
    removeMarks() {
      this.map.remove(this.markers);
    },
    removeMark() {
      this.map.remove(this.marker);
    },
    removeMarkCircle() {
      this.map.remove(this.circle);
    },
    removeDrawPolygon() {
      console.log("删除多边形")
      if (this.mouseTool&&this.mouseTool.polygon) {
        this.map.remove(this.mouseTool.polygon);
      }
    },
    removeMouseTool(boolean) {
      console.log("删除鼠标工具")
      if (this.mouseTool) {
        this.mouseTool.close(boolean)
      }
    },
    removeMarkPolygon(){
      this.map.remove(this.polygon)
    },
    // 清除地图上所有添加的覆盖物
    removeAll() {
      this.siteName=''
      this.addSiteMarkFlag = true
      this.removeMouseTool(true)
      this.polygonArr=[]
      this.removeMarkCircle()
      this.removeMarkPolygon()
      this.removeMark()
    },

    addAll(){
      // 创建点覆盖物
      this.marker = new AMap.Marker({
        position: new AMap.LngLat(this.mapInfo.lng, this.mapInfo.lat),
      });
      this.map.add(this.marker)

      this.circle = new AMap.Circle({
        center: [this.mapInfo.lng, this.mapInfo.lat],
        radius: this.mapInfo.siteRadius, //半径
        borderWeight: 3,
        strokeColor: "#FF33FF",
        strokeOpacity: 1,
        strokeWeight: 6,
        strokeOpacity: 0.2,
        fillOpacity: 0.4,
        strokeStyle: 'dashed',
        strokeDasharray: [10, 10],
        // 线样式还支持 'dashed'
        fillColor: '#1791fc',
        zIndex: 50,
      })
      this.circle.setMap(this.map)
      // 缩放地图到合适的视野级别
      this.map.setFitView([ this.circle ])
      var siteFence = this.mapInfo.siteFence
      var path = []
      for (let i=0;i<siteFence.length;i++) {
          path.push(siteFence[i].split(","))
      }

      this.polygon = new AMap.Polygon({
        path: path,
        strokeColor: "#FF33FF",
        strokeWeight: 6,
        strokeOpacity: 0.2,
        fillOpacity: 0.4,
        fillColor: '#1791fc',
        zIndex: 50,
      })
      this.map.add(this.polygon)
      // 缩放地图到合适的视野级别
      this.map.setFitView([ this.polygon ])
    },

    drawPolygon1(siteFence) {
      console.log(siteFence)
      siteFence = siteFence.split(";")
      var path = []
      for (let i=0;i<siteFence.length;i++) {
        //path.push(siteFence[i].split(","))
        path.push(new AMap.LngLat(siteFence[i].split(",")[0],siteFence[i].split(",")[1]))
      }
      let polygon = new AMap.Polygon({
        path: path,
        fillColor: '#fff', // 多边形填充颜色
        borderWeight: 1, // 线条宽度，默认为 1
        strokeColor: 'red', // 线条颜色
      })
      this.map.add(polygon)
    },


    //获取搜搜站点标识
    getSiteMark() {
      if (this.marker) {
        this.removeMark()
      }
      if (this.markers.length > 0) {
        this.removeMarks()
      }
      console.log(this.siteName)
      this.placeSearch = new AMap.PlaceSearch({
        // city 指定搜索所在城市，支持传入格式有：城市名、citycode和adcode
        city: this.city
      })

      //搜索城市标记点
      this.placeSearch.search(this.siteName, (status, result) => {
        // 查询成功时，result即对应匹配的POI信息
        console.log(result)
        var pois = result.poiList.pois;
        for (var i = 0; i < pois.length; i++) {
          var poi = pois[i];
          this.markers[i] = new AMap.Marker({
            position: poi.location,   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
            title: poi.name
          });
          this.markers[i].on('click', (e) => {
            var text = '您在 [ ' + e.lnglat.getLng() + ',' + e.lnglat.getLat() + ' ] 的位置点击了marker！'
            console.log(text)
            this.siteName = this.getSiteNameByLngLat(e.lnglat)
            this.addSiteMark(e.lnglat, this.siteName)
            setTimeout(() =>{
              console.log(this.siteName)
              this.$emit('lngLatInfoFromMap',{lnglat:e.lnglat,siteName:this.siteName})
            }, 500);
          })
          // 将创建的点标记添加到已有的地图实例：
          this.map.add(this.markers[i]);
        }
        this.map.setFitView();
      })
    },

    //可拖拽驾车路线规划
    drivingLine(path){
      //绘制初始路径
      this.map.plugin("AMap.DragRoute", ()=> {
        let route = new AMap.DragRoute(this.map, path, AMap.DrivingPolicy.LEAST_TIME); //构造拖拽导航类
        route.search(); //查询导航路径并开启拖拽导航
      });
    },
  },
}
</script>
<style>
  #container {width:100%; height: 400px; }
</style>
