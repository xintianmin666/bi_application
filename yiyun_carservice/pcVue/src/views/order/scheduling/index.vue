<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px">
      <el-form-item label="产品名称" prop="productId" style="float:left;width: 480px">
        <el-select v-model="queryParams.productId" placeholder="请选择产品" clearable size="small">
          <el-option
            v-for="dict in productOptions"
            :key="dict.productId"
            :label="dict.productName"
            :value="dict.productId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="订单类型" prop="orderType" style="float:left;width: 480px">
        <el-select v-model="queryParams.orderType" placeholder="请选择" clearable size="small">
          <el-option label="拼座单" value="3">机场巴士订座单</el-option>
          <!--<el-option label="拼车单" value="4">订车单</el-option>-->
          <el-option label="包车单" value="5">机场巴士包车单</el-option>
          <el-option label="包车单" value="6">定制客运订座单</el-option>
          <el-option label="包车单" value="7">定制客运包车单</el-option>
          <el-option label="包车单" value="8">自由包车单</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="用车开始日期" style="float:left;width: 480px">
        <el-date-picker
          v-model="queryParams.beginTime"
          size="small"
          type="datetime"
          placeholder="用车开始日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="用车结束日期" style="float:left;width: 480px">
        <el-date-picker
          v-model="queryParams.endTime"
          size="small"
          type="datetime"
          placeholder="用车结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="上车点" prop="beginStation" style="float:left;width: 480px">
        <el-input
          v-model="queryParams.beginStation"
          placeholder="请输入上车点"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['order:rentalOrder:add']"
        >生成新班次
        </el-button>
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          @click="handleUpdate"
          v-hasPermi="['order:rentalOrder:edit']"
        >添加到已有班次
        </el-button>
      </el-form-item>
      <Map1 ref="map1" style="margin-bottom: 20px" @lngLatInfoFromMap="getLngLatInfo" :mapInfo='mapInfo'></Map1>
    </el-form>

    <!-- 查找可用车辆弹框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="carform" :model="queryParams" :inline="true" :rules="rules" label-width="200px">
        <el-form-item label="班次开始时间" prop="beginTime" style="float:left;width: 450px">
          <el-date-picker
            v-model="queryParams.beginTime"
            type="datetime"
            placeholder="选择日期"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="班次结束时间" prop="endTime" style="float:left;width: 450px">
          <el-date-picker
            v-model="queryParams.endTime"
            type="datetime"
            placeholder="选择日期"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="车牌号" prop="driverCarNo" style="float:left;width: 450px">
          <el-input
            v-model="queryParams.driverCarNo"
            placeholder="请输入车牌号"
          />
        </el-form-item>
        <el-form-item label="所属公司" prop="reserveName" style="float:left;width: 450px">
          <!--<el-input-->
          <!--v-model="queryParams.reserveName"-->
          <!--placeholder="请输入所属公司"-->
          <!--:options="deptOptions"-->
          <!--/>-->
          <el-select v-model="queryParams.reserveName" placeholder="请选择所属公司" clearable size="small"
          >
            <el-option
              v-for="item in deptOptions"
              :key="item.deptId"
              :label="item.deptName"
              :value="item.deptId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="车型" prop="reserveMobile" style="float:left;width: 450px">
          <el-select v-model="queryParams.reserveMobile" placeholder="请选择车型" clearable size="small">
            <el-option label="请选择" value="0">请选择</el-option>
            <el-option
              v-for="item in vehicleTypes"
              :key="item.vcehicleTypeId"
              :label="item.typeName"
              :value="item.vcehicleTypeId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="司机名称" prop="driverName" style="float:left;width: 450px">
          <el-input
            v-model="queryParams.driverName"
            placeholder="请输入司机名称"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="carQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetCar">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="carloading" :data="carList" highlight-current-row @current-change="handleCurrentChange">
        <el-table-column type="index" width="55" align="center"/>
        <!--<el-table-column label="主键" align="center" prop="id"/>-->
        <el-table-column label="车牌号" align="center" prop="licenseTagno"/>
        <el-table-column label="可乘坐旅客数" align="center" prop="vehicleType.passengerNum"/>
        <el-table-column label="车型" align="center" prop="vehicleType.typeName"/>
        <el-table-column label="车辆所属公司" align="center" prop="dept.deptName"/>
        <el-table-column label="驾驶员" align="center" prop="driverName"/>
        <el-table-column label="驾驶员手机号" align="center" prop="driverPhone"/>
        <el-table-column
          fixed="right"
          label="操作"
          width="200">
          <template slot-scope="scope">
            <el-button size="mini"
                       type="text"
                       icon="el-icon-edit" @click="updateDriver(scope.row)">修改司机
            </el-button>
            <el-button size="mini"
                       type="text"
                       icon="el-icon-plus" @click="confirmNewShift(scope.row)">确认生成班次
            </el-button>
          </template>
        </el-table-column>

      </el-table>
    </el-dialog>
    <!-- 查找可用班次弹框 -->
    <el-dialog :title="title" :visible.sync="edit" width="1400px" append-to-body>
      <el-form ref="busform" :model="queryParams" :inline="true" :rules="rules" label-width="120px">
        <el-form-item label="班次开始时间" prop="beginTime">
          <el-date-picker
            v-model="queryParams.beginTime"
            type="datetime"
            placeholder="选择日期"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <!--<el-form-item label="班次结束时间" prop="endTime">-->
        <!--<el-date-picker-->
        <!--v-model="queryParams.endTime"-->
        <!--type="datetime"-->
        <!--placeholder="选择日期"-->
        <!--value-format="yyyy-MM-dd HH:mm:ss">-->
        <!--</el-date-picker>-->
        <!--</el-form-item>-->
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="busQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetBus">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="busloading" :data="busList" highlight-current-row @current-change="handleCurrentChange">
        <el-table-column type="index" width="55" align="center"/>
        <!--<el-table-column label="主键" align="center" prop="id"/>-->
        <el-table-column label="调度单号" align="center" prop="dispatchOrdercode"/>
        <el-table-column label="车牌号" align="center" prop="vehiclePlateNo"/>
        <el-table-column label="剩余座位数" align="center" prop="remainingSeatNum"/>
        <el-table-column label="司机姓名" align="center" prop="driverName"/>
        <el-table-column label="司机联系方式" align="center" prop="driverPhone"/>
        <el-table-column label="任务开始时间" align="center" prop="taskStartTime"/>
        <el-table-column label="任务开始点名称" align="center" prop="taskStartSiteName"/>
        <el-table-column
          label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="success" @click="addBus(scope.row)">添加至此班次</el-button>
          </template>
        </el-table-column>

      </el-table>
    </el-dialog>
    <!-- 查找可用司机弹框 -->
    <el-dialog :title="title" :visible.sync="driver" width="1400px" append-to-body>
      <el-form ref="driverfrom" :model="queryParams" :inline="true" :rules="rules" label-width="120px">
        <el-form-item label="司机姓名" prop="driverName">
          <el-input
            v-model="queryParams.driverName"
            placeholder="请输入司机姓名"
          />
        </el-form-item>
        <el-form-item label="所属公司" prop="reserveName">
          <!--<el-input-->
          <!--v-model="queryParams.reserveName"-->
          <!--placeholder="请输入所属公司"-->
          <!--:options="deptOptions"-->
          <!--/>-->
          <el-select v-model="queryParams.reserveName" placeholder="请选择所属公司" clearable size="small"
          >
            <el-option
              v-for="item in deptOptions"
              :key="item.deptId"
              :label="item.deptName"
              :value="item.deptId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="driverQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetDriver">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="driverloading" :data="driverList" highlight-current-row
                @current-change="handleCurrentChange">
        <el-table-column type="index" width="55" align="center"/>
        <!--<el-table-column label="主键" align="center" prop="id"/>-->
        <el-table-column label="驾驶员名称" align="center" prop="name"/>
        <el-table-column label="驾驶员电话" align="center" prop="phone"/>
        <el-table-column label="驾驶员驾照号" align="center" prop="driverLicenseNo"/>
        <el-table-column label="所在公司" align="center" prop="deptName"/>
        <el-table-column
          label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="success" @click="editDriver(scope.row)">选择此司机</el-button>
          </template>
        </el-table-column>

      </el-table>
    </el-dialog>
    <!-- 确认调度单信息 -->
    <el-dialog :title="title" :visible.sync="confirmBus" width="1114px" append-to-body>
      <div>
        班次开始时间<span style="margin-left: 30px"><el-date-picker
        v-model="queryParams.beginTime"
        type="datetime"
        placeholder="选择日期"
        value-format="yyyy-MM-dd HH:mm:ss" @change="busTimeChange(passengerData)">
        </el-date-picker></span>
        &nbsp;&nbsp;&nbsp;&nbsp;班次结束时间<span style="margin-left: 30px"><el-date-picker
        v-model="queryParams.endTime"
        type="datetime"
        placeholder="选择日期"
        value-format="yyyy-MM-dd HH:mm:ss">
        </el-date-picker></span>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<el-button type="primary"
                                                                   @click.native.prevent="addNewShift()"
                                                                   style="margin-bottom: 20px">生成新班次
      </el-button>
      </div>
      <!--<div>-->
      <!--线路名称<span style="margin-left: 30px">{{productName}}</span>-->
      <!--</div>-->
      <!--<div>-->
      <!--班次时间<span style="margin-left: 30px">{{busTime}}</span>-->
      <!--</div>-->
      <!--<div>-->
      <!--车牌号<span style="margin-left: 30px">{{driverCarNo}}</span>-->
      <!--</div>-->
      <!--<div>-->
      <!--司机名称<span style="margin-left: 30px">{{driverName}}</span>-->
      <!--</div>-->
      <!--<div>-->
      <!--司机手机号码<span style="margin-left: 30px">{{driverMobile}}</span>-->
      <!--</div>-->
      <div style="text-align: right" v-if="isShow">
        <el-button type="primary" @click.native.prevent="recalculate(passengerData)" style="margin-bottom: 20px">重新计算
        </el-button>
      </div>
      <el-table
        :data="passengerData"
        border
        style="width: 100%">
        <el-table-column
          prop="id"
          label="主键Id"
          width="150" v-if="false">
          <template slot-scope="scope">
            <el-input size="small" v-model="scope.row.id" controls-position="right" hidden>
            </el-input>
          </template>
        </el-table-column>
        <el-table-column
          prop="longitude"
          label="主键Id"
          width="150" v-if="false">
          <template slot-scope="scope">
            <el-input size="small" v-model="scope.row.longitude" controls-position="right" hidden>
            </el-input>
          </template>
        </el-table-column>
        <el-table-column
          prop="latitude"
          label="主键Id"
          width="150" v-if="false">
          <template slot-scope="scope">
            <el-input size="small" v-model="scope.row.latitude" controls-position="right" hidden>
            </el-input>
          </template>
        </el-table-column>
        <el-table-column
          prop="receptionNum"
          label="接客序号"
          min-width="60px" align="center" v-if="isShow">
          <template slot-scope="scope">
            <el-input-number size="mini" v-model="scope.row.receptionNum" controls-position="right"
                             @change="handleChange()" :min="1" :max="100">
            </el-input-number>
          </template>
        </el-table-column>
        <el-table-column
          prop="receptionTime"
          label="接客时间"
          min-width="150px" align="center" v-if="isShow">
          <template slot-scope="scope">
            <el-date-picker
              v-model="scope.row.receptionTime"
              size="small"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="接客时间" @change="receptionTimeChange(passengerData)"
            ></el-date-picker>
          </template>
        </el-table-column>
        <el-table-column
          prop="reserveName"
          label="用车人姓名"
          min-width="90px" align="center">
          <template slot-scope="scope">
            <el-input size="small" v-model="scope.row.reserveName" controls-position="right" disabled>
            </el-input>
          </template>
        </el-table-column>
        <el-table-column
          prop="reserveMobile"
          label="用车人手机号"
          min-width="100px" align="center">
          <template slot-scope="scope">
            <el-input size="small" v-model="scope.row.reserveMobile" controls-position="right" disabled>
            </el-input>
          </template>
        </el-table-column>
        <el-table-column
          label="上车点"
          min-width="100px" align="center">
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top"><p>上车点:{{scope.row.beginStation}}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{scope.row.beginStation}}</el-tag>
              </div>
            </el-popover>
            <!--<el-input size="small" v-model="scope.row.beginStation" controls-position="right">-->
            <!--</el-input>-->
          </template>
          <!--<template slot-scope="scope">-->
          <!--<el-popover trigger="hover" placement="top"><p>上车点: {{ scope.row.beginStation }}</p></el-popover>-->
          <!--</template>-->
        </el-table-column>

        <el-table-column
          prop="endStation"
          label="下车点"
          min-width="100px" align="center">
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top"><p>下车点:{{scope.row.endStation}}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{scope.row.endStation}}</el-tag>
              </div>
            </el-popover>
            <!--<el-input size="small" v-model="scope.row.beginStation" controls-position="right">-->
            <!--</el-input>-->
          </template>
        </el-table-column>
        <el-table-column
          prop="useTime"
          label="用车时间"
          min-width="150px" align="center">
          <template slot-scope="scope">
            <el-input size="small" v-model="scope.row.useTime" controls-position="right" disabled>
            </el-input>
          </template>
        </el-table-column>

      </el-table>
      <div style="text-align: right">
        <el-button type="primary" @click.native.prevent="openMapLine(passengerData)" style="margin-bottom: 20px">
          查看规划路线
        </el-button>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<el-button type="primary"
                                                                   @click.native.prevent="confirmBusData(passengerData)"
                                                                   style="margin-bottom: 20px">
        选择派单车辆
      </el-button>
      </div>
    </el-dialog>
    <el-dialog title="查看路线" :visible.sync="openMap" width="800px" append-to-body>
      <MapX ref="mapx" style="margin-bottom: 20px"></MapX>
    </el-dialog>
  </div>
</template>
<script>
  import {listCity} from "@/api/oper/city";
  import {
    addTaxiOrder,
    confirmBusData,
    confirmNewShift,
    delBusId,
    delTaxiOrder,
    deptAll,
    exportTaxiOrder,
    getCanUseBusList,
    getCanUseCarList,
    getCanUseDriverList,
    getPassengerList,
    getTaxiOrder,
    getWaitlist,
    listTaxiOrder,
    productCodeAll,
    recalculate,
    updateTaxiOrder,
    vehicleTypeAll
  } from "@/api/order/rentalOrder";
  import {getToken} from '@/utils/auth'
  import Map from '@/components/Map/index';
  import Map1 from '@/components/Map/index1';
  import MapX from '@/components/Map/indexX';
  import vPinyin from '@/utils/vue-py.js';

  export default {
    name: "Site",
    components: {
      Map, Map1, MapX
    },
    data() {
      return {
        updateVehicleId: '',
        updatedriverId: '',
        driverName: '',
        driverCarNo: '',
        useHour: '',
        busTime: '',
        productName: '',
        driverMobile: '',
        priceTypeOptions: [],
        productOptions: [],
        priceData: [],
        // 部门树选项
        deptOptions: [],
        //车型
        vehicleTypes: [],
        openSetPrice: false,
        ids: [],
        vehicleId: '',
        productCodes: [],//产品id数组
        orderDates: [],
        passengerData: [],
        orderTypes:
          [],//订单类型数组
        headers:
          {
            Authorization: 'Bearer ' + getToken()
          }
        ,
        // 遮罩层
        loading: true,
        carloading:
          true,
        busloading:
          true,
        driverloading:
          true,
        productCode:
          '',
        // 非单个禁用
        single:
          true,
        // 非多个禁用
        multiple:
          true,
        // 总条数
        total:
          0,
        // 总条数
        cartotal:
          0,
        bustotal:
          0,
        drivertotal:
          0,
        // 出租车订单表格数据
        rentalOrderList:
          [],
        // 可选车辆表格数据
        carList:
          [],
        busList:
          [],
        driverList:
          [],
        // 弹出层标题
        title:
          "",
        // 是否显示弹出层
        open:
          false,
        edit:
          false,
        driver:
          false,
        confirmBus:
          false,
        delFlag: true,
        confirmFlag: false,
        isShow: false,
        openMap: false,
        cityList:
          [],
        lnglat:
          {
            lng: '',
            lat:
              ''
          }
        ,
        siteTypeOptions: [],
        siteLevelOptions:
          [],
        siteFence:
          '',
        drawSiteFence:
          false,
        siteRadius:
          '',
        mapInfo:
          {
            siteRadius: '',
            lnglat:
              '',
            lng:
              '',
            lat:
              '',
            siteFence:
              []
          }
        ,
        lng: '',
        lat:
          '',
        polygonArr:
          [],
        mouseTool:
          '',
        circle:
          '',
        siteName:
          '',
        placeSearch:
          null,
        markers:
          [],
        map:
          null,
        // 遮罩层
        loading:
          true,
        // 选中数组
        ids:
          [],
        // 非单个禁用
        single:
          true,
        // 非多个禁用
        multiple:
          true,
        // 总条数
        total:
          0,
        // 城市站点表格数据
        siteList:
          [],
        // 弹出层标题
        title:
          "",
        // 是否显示弹出层
        open:
          false,
        edit:
          false,
        driver:
          false,
        // 查询参数
        queryParams:
          {
            beginStation: undefined,
            endStation:
            undefined,
            useTime:
            undefined,
            reserveName:
            undefined,
            reserveId:
            undefined,
            reserveMobile:
            undefined,
            appectTime:
            undefined,
            finishTime:
            undefined,
            orderCode:
            undefined,
            orderType:
            undefined,
            orderStatus:
            undefined,
            orderAmount:
            undefined,
            collectAmount:
            undefined,
            driverId:
            undefined,
            driverName:
            undefined,
            driverPhone:
            undefined,
            driverCarNo:
            undefined,
            longitude:
            undefined,
            latitude:
            undefined,
            cityId:
            undefined,
            siteCode:
            undefined,
            siteName:
            undefined,
            sitePy:
            undefined,
            siteSp:
            undefined,
            siteType:
            undefined,
            siteLevel:
            undefined,
            siteRadius:
            undefined,
            siteFence:
            undefined,
            createId:
            undefined,
            updateId:
            undefined,
            productId:
              '',
            vehicleId:
              '',
            ids:
              '',
            beginTime:
              '',
            endTime:
              '',
            busId:
              ''
          }
        ,
        // 查询参数
        cityQueryParams: {
          pageNum: 1,
          pageSize:
            10000,
          isvaliable:
            '1'
        }
        ,
        // 表单参数
        form: {}
        ,
        // 表单校验
        rules: {
          cityId: [
            {required: true, message: "城市不能为空", trigger: "blur"},
          ],
        }
      }
        ;
    },
    watch: {
      /*form: {
        handler(newValue, oldValue) {
          console.log("站点改变",newValue)
          this.changeSiteName(newValue.siteName)
        },
        deep: true
      },*/
      'open'(newValue) {
        console.log("open改变", newValue)
        if (!newValue) {
          this.queryParams.beginTime = "";
          this.queryParams.endTime = "";
          this.queryParams.beginStation = "";
          this.queryParams.orderType = "";
          this.queryParams.productId = "";
        }
      },
      'edit'(newValue) {
        console.log("edit改变", newValue)
        if (!newValue) {
          this.queryParams.beginTime = "";
          this.queryParams.endTime = "";
          this.queryParams.beginStation = "";
          this.queryParams.orderType = "";
          this.queryParams.productId = "";
        }
      },
      'confirmBus'(newValue) {
        console.log("confirmBus", newValue)
        console.log("delFlag", this.delFlag)
        if (!newValue && this.delFlag) {
          delBusId(this.queryParams);
        }
      },
      'form.siteName'(newValue) {
        console.log("站点改变", newValue)
        this.changeSiteName(newValue)
      },
      /*open(value){
        if (!value) {
          console.log("地图组件关闭")
          this.$refs.map.removeAll()
        }
      }*/

    }
    ,
    mounted() {
    }
    ,
    created() {
      setTimeout(() => {
        this.getList()
      }, 2000);
      this.getDicts("site_type").then(response => {
        this.siteTypeOptions = response.data;
      });
      this.getDicts("site_level").then(response => {
        this.siteLevelOptions = response.data;
      });
      productCodeAll().then(response => {
        this.productOptions = response.data;
      });
    }
    ,
    methods: {
      /* onSelectedCity(val){
         this.$refs.map.city = val.operCityName
         this.form.cityId =  val.operCityId
       },*/
      //订单添加到生成的新班次
      getWaitlist() {
        if (this.queryParams.beginTime == '' && this.queryParams.endTime == '') {
          let day3 = new Date();
          day3.setTime(day3.getTime() + 24 * 60 * 60 * 1000);
          let s3 = day3.getFullYear() + "-" + (day3.getMonth() + 1) + "-" + day3.getDate();
          this.queryParams.beginTime = s3 + " 00:00";
          this.queryParams.endTime = s3 + " 24:00";
        }
        getWaitlist(this.queryParams).then(response => {
          if (response.code === 200) {
            this.rentalOrderList = response.data;
            let mapData = [];
            if (this.rentalOrderList.length > 0) {
              for (let i = 0; i < this.rentalOrderList.length; i++) {
                let latlng = [];
                latlng[0] = this.rentalOrderList[i].longitude;
                latlng[1] = this.rentalOrderList[i].latitude;
                let siteName = "";
                let seatNum = 0;
                if (this.rentalOrderList[i].orderType == "3") {
                  siteName = this.rentalOrderList[i].beginStation + "(机场巴士订座单," + this.rentalOrderList[i].seatNum + "人,用车时间" + this.rentalOrderList[i].useTime + ")";
                  seatNum = this.rentalOrderList[i].seatNum;
                } else if (this.rentalOrderList[i].orderType == "4") {
                  siteName = this.rentalOrderList[i].beginStation + "(订车单,用车人数" +
                    this.rentalOrderList[i].seatNum + ",可售座位数" +
                    this.rentalOrderList[i].sellNumber + ",用车时间" + this.rentalOrderList[i].useTime + ")";
                } else if (this.rentalOrderList[i].orderType == "5") {
                  siteName = this.rentalOrderList[i].beginStation + "(机场巴士包车单,车型为" +
                    this.rentalOrderList[i].carDes + ",用车时间" + this.rentalOrderList[i].useTime + ")";
                  seatNum = 1;
                } else if (this.rentalOrderList[i].orderType == "6") {
                  siteName = this.rentalOrderList[i].beginStation + "(定制客运订座单," + this.rentalOrderList[i].seatNum + "人,用车时间" + this.rentalOrderList[i].useTime + ")";
                  seatNum = this.rentalOrderList[i].seatNum;
                } else if (this.rentalOrderList[i].orderType == "7") {
                  siteName = this.rentalOrderList[i].beginStation + "(定制客运包车单,车型为" +
                    this.rentalOrderList[i].carDes + ",用车时间" + this.rentalOrderList[i].useTime + ")";
                  seatNum = 1;
                } else if (this.rentalOrderList[i].orderType == "8") {
                  siteName = this.rentalOrderList[i].beginStation + "(自由包车单,车型为" +
                    this.rentalOrderList[i].carDes + ",用车时间" + this.rentalOrderList[i].useTime + ")";
                  seatNum = 1;
                }
                let jsonObj = {
                  "siteName": siteName,
                  "id": this.rentalOrderList[i].id,
                  "latlng": latlng,
                  "productCode": this.rentalOrderList[i].productCode,
                  "orderType": this.rentalOrderList[i].orderType,
                  "useTime": this.rentalOrderList[i].useTime,
                  "seatNum": seatNum,
                  "useHour": this.rentalOrderList[i].useHour
                };
                console.log("jsonObj")
                mapData[i] = jsonObj;
              }
            }
            this.$refs.map1.addSiteMarks(mapData);
          }
        });
      }
      ,
      confirmNewShift(row) {
        this.queryParams.driverId = row.driverId;
        this.queryParams.vehicleId = row.vehicleId;
        confirmNewShift(this.queryParams).then(response => {
          console.log(response);
          if (response.code === 200) {
            this.msgSuccess("生成新班次成功");
            this.open = false;
          } else {
            this.msgError(response.msg);
          }
        });
      },
      // addNewShift(row) {
      //   this.queryParams.driverId = row.driverId;
      //   this.queryParams.vehicleId = row.vehicleId;
      //   addTaxiOrder(this.queryParams).then(response => {
      //     console.log(response);
      //     if (response.code === 200) {
      //       if (response.data.orderType == 5) {
      //         this.msgSuccess("生成新班次成功");
      //         // console.log("生成新班次成功")
      //         this.open = false;
      //       } else {
      //         this.open = false;
      //         // this.queryParams.beginTime = "";
      //         // this.queryParams.endTime = "";
      //         // getWaitlist();
      //         this.queryParams.busId = response.data.busId;
      //         this.driverName = response.data.driverName;
      //         this.driverCarNo = response.data.driverCarNo;
      //         this.busTime = response.data.busTime;
      //         this.productName = response.data.productName;
      //         this.driverMobile = response.data.driverMobile;
      //         this.confirmBusCode();
      //       }
      //     } else {
      //       this.msgError(response.msg);
      //     }
      //   });
      // },
      addNewShift() {
        // this.queryParams.driverId = row.driverId;
        // this.queryParams.vehicleId = row.vehicleId;
        this.queryParams.ids = this.ids.toString();
        addTaxiOrder(this.queryParams).then(response => {
          console.log(response);
          if (response.code === 200) {
            console.log("是否成功了===");
            this.queryParams.busId = response.data.busId;
            console.log(this.queryParams.busId)
            this.getPassengerList(response.data.busId);
            // if (response.data.orderType == 5) {
            //   this.msgSuccess("生成新班次成功");
            //   console.log("生成新班次成功")
            //   this.open = false;
            //   this.getPassengerList(this.queryParams.busId)
            // } else {
            //   this.open = false;
            //   this.queryParams.beginTime = "";
            //   this.queryParams.endTime = "";
            //   getWaitlist();
            //   this.queryParams.busId = response.data.busId;
            //   this.driverName = response.data.driverName;
            //   this.driverCarNo = response.data.driverCarNo;
            //   this.busTime = response.data.busTime;
            //   this.productName = response.data.productName;
            //   this.driverMobile = response.data.driverMobile;
            //   this.confirmBusCode();
            // }
          } else {
            this.msgError(response.msg);
          }
        });
      },
      handleChange(value) {
        this.confirmFlag = true;
        console.log(this.confirmFlag)
      },
      /**确认调度单**/
      confirmBusCode() {
        console.log(this.confirmFlag);
        if (this.confirmFlag) {
          console.log("为什么" + this.confirmFlag)
          this.$message({
            message: '请重新计算后再确认提交',
            type: 'warning'
          });
          return
        }
        this.getPassengerList(this.queryParams.busId)
        this.confirmBus = true;
        this.title = "确认调度单";
      },
      getPassengerList(busId) {
        console.log("进来")
        getPassengerList(busId).then(response => {
          console.log("刷新页面")
          this.passengerData = response.data;
        });
      },
      busTimeChange(data) {
        if (this.useHour != '' || this.useHour != null) {
          // let day3 = new Date(this.queryParams.beginTime);
          // day3.setTime(day3.getTime() + this.useHour * 60 * 60 * 1000);
          // let s3 = day3.getFullYear() + "-" + (day3.getMonth() + 1) + "-" + day3.getDate();
          // // this.queryParams.beginTime = s3 + " 00:00";
          // this.queryParams.endTime = s3 + " 24:00";
          console.log("变化后的开始时间" + this.queryParams.beginTime);
          var t = new Date(this.queryParams.beginTime);//你已知的时间
          var t_s = t.getTime();//转化为时间戳毫秒数
          this.queryParams.endTime = new Date();//定义一个新时间
          this.queryParams.endTime.setTime(t_s + this.useHour * 1000 * 60 * 60);
        }
        this.recalculate(data);
      },
      receptionTimeChange(data) {
        for (let i = 0; i < data.length - 1; i++) {
          if (data[i].receptionNum == 1) {
            if (data[i].receptionTime != this.queryParams.beginTime) {
              this.queryParams.beginTime = data[i].receptionTime;
              if (this.useHour != '' || this.useHour != null) {
                var t = new Date(this.queryParams.beginTime);//你已知的时间
                var t_s = t.getTime();//转化为时间戳毫秒数
                this.queryParams.endTime = new Date();//定义一个新时间
                this.queryParams.endTime.setTime(t_s + this.useHour * 1000 * 60 * 60);//设置新时间比旧时间多一小时
              }
              this.recalculate(data);
            }
          } else {
            var oDate1 = new Date(data[i].receptionTime);
            var oDate2 = new Date(this.queryParams.beginTime);
            if (oDate1.getTime() < oDate2.getTime()) {
              this.$message({
                message: '接客时间小于班次发班时间，请重新填写',
                type: 'warning'
              });
              return
            }
          }
        }
      },
      recalculate(data) {
        for (let i = 0; i < data.length - 1; i++) {
          if (data[i].receptionNum == data[i + 1].receptionNum) {
            this.$message({
              message: '接客顺序存在相同序号,请重新填写',
              type: 'warning'
            });
            return
          }
        }
        recalculate(data, this.queryParams.beginTime, this.queryParams.endTime).then(response => {
          if (response.code === 200) {
            console.log("计算是否成功")
            this.confirmFlag = false;
            this.getPassengerList(this.queryParams.busId)
          } else {
            this.msgError(response.msg);
          }
        });
      }
      ,

      openMapLine(data) {
        for (let i = 0; i < data.length - 1; i++) {
          if (data[i].receptionNum == data[i + 1].receptionNum) {
            this.$message({
              message: '接客顺序存在相同序号,请重新填写',
              type: 'warning'
            });
            return
          }
        }
        this.recalculate(data);
        setTimeout(() => {
          // this.$refs.mapx.showFnc();
          this.$refs.mapx.getMarks(mapData);
        }, 800)
        this.openMap = true;
        data.sort(function (a, b) {
          return a.receptionNum - b.receptionNum
        })
        let mapData = [];
        if (data.length > 0) {
          for (let i = 0; i < data.length; i++) {
            let latlng = [];
            latlng[0] = data[i].longitude;
            latlng[1] = data[i].latitude;
            //     // let siteName = "";
            //     // if (this.rentalOrderList[i].orderType == "3") {
            //     //   siteName = this.rentalOrderList[i].beginStation + "(机场巴士订座单," + this.rentalOrderList[i].seatNum + "人,用车时间" + this.rentalOrderList[i].useTime + ")";
            //     // } else if (this.rentalOrderList[i].orderType == "4") {
            //     //   siteName = this.rentalOrderList[i].beginStation + "(订车单,用车人数" +
            //     //     this.rentalOrderList[i].seatNum + ",可售座位数" +
            //     //     this.rentalOrderList[i].sellNumber + ",用车时间" + this.rentalOrderList[i].useTime + ")";
            //     // } else if (this.rentalOrderList[i].orderType == "5") {
            //     //   siteName = this.rentalOrderList[i].beginStation + "(机场巴士包车单,车型为" +
            //     //     this.rentalOrderList[i].carDes + ",用车时间" + this.rentalOrderList[i].useTime + ")";
            //     // } else if (this.rentalOrderList[i].orderType == "6") {
            //     //   siteName = this.rentalOrderList[i].beginStation + "(定制客运订座单," + this.rentalOrderList[i].seatNum + "人,用车时间" + this.rentalOrderList[i].useTime + ")";
            //     // } else if (this.rentalOrderList[i].orderType == "7") {
            //     //   siteName = this.rentalOrderList[i].beginStation + "(定制客运包车单,车型为" +
            //     //     this.rentalOrderList[i].carDes + ",用车时间" + this.rentalOrderList[i].useTime + ")";
            //     // }
            let jsonObj = {
              //       // "siteName": siteName,
              //       // "id": this.rentalOrderList[i].id,
              "latlng": latlng
              //       // "productCode": this.rentalOrderList[i].productCode,
              //       // "orderType": this.rentalOrderList[i].orderType,
              //       // "useTime": this.rentalOrderList[i].useTime
            };
            console.log("jsonObj")
            mapData[i] = jsonObj;
          }
        }
        console.log("看看组装的数据是什么样" + mapData);
        // this.$refs.mapx.getMarks(mapData);
      }
      ,

      //确认保存调度单
      confirmBusData(data) {
        for (let i = 0; i < data.length - 1; i++) {
          if (data[i].receptionNum == data[i + 1].receptionNum) {
            this.$message({
              message: '接客顺序存在相同序号,请重新填写',
              type: 'warning'
            });
            return
          }
          if (data[i].receptionNum > 1) {
            var oDate1 = new Date(data[i].receptionTime);
            var oDate2 = new Date(this.queryParams.beginTime);
            if (oDate1.getTime() < oDate2.getTime()) {
              this.$message({
                message: '接客时间小于班次发班时间，请重新填写',
                type: 'warning'
              });
              return
            }
          }
        }
        // console.log(this.confirmFlag);
        if (this.confirmFlag && this.isShow) {
          // console.log("为什么" + this.confirmFlag)
          this.$message({
            message: '请重新计算后再确认提交',
            type: 'warning'
          });
          return
        }
        confirmBusData(data, this.queryParams.ids, this.queryParams.driverId).then(response => {
          if (response.code === 200) {
            // console.log(response);
            // this.msgSuccess("调度单生成成功");
            this.delFlag = false;
            this.confirmBus = false;
            // this.handleQuery();
            this.getTreeselect();
            this.getVehicleTypeselect();
            this.open = true;
            // this.confirmBus = true;
            this.title = "选择派单车辆";
            this.carQuery();
          } else {
            this.msgError(response.msg);
          }
        });
      }
      ,
      addBus(row) {
        this.queryParams.busId = row.dispatchOrdercode;
        updateTaxiOrder(this.queryParams).then(response => {
          console.log(response);
          if (response.code === 200) {
            this.msgSuccess("添加至此班次成功");
            this.edit = false;
            this.getList();
          } else {
            this.msgError(response.msg);
          }
        });
      }
      ,
      editDriver(row) {
        // console.log(this.updateVehicleId);
        // console.log(this.updatedriverId);
        for (let i = 0; i < this.carList.length; i++) {
          // console.log(this.carList[i].vehicleId);
          // console.log(this.carList[i].driverId);
          if (this.carList[i].vehicleId == this.updateVehicleId && this.carList[i].driverId == this.updatedriverId) {
            this.carList[i].driverId = row.driverId;
            this.carList[i].driverName = row.name;
            this.carList[i].driverPhone = row.phone;
          }
        }
        // console.log(this.carList);
        this.driver = false;
        // this.queryParams.driverId = row.driverId;
        // updateDriver(this.queryParams).then(response => {
        //   console.log(response);
        //   if (response.code === 200) {
        //     this.msgSuccess("修改司机成功");
        //     this.driver = false;
        //     this.carQuery();
        //   } else {
        //     this.msgError(response.msg);
        //   }
        // });
      }
      ,
      /** 查询部门下拉树结构 */
      getTreeselect() {
        deptAll().then(response => {
          this.deptOptions = response.data;
        });
      }
      ,
      /** 查询车型下拉树结构 */
      getVehicleTypeselect() {
        vehicleTypeAll().then(response => {
          this.vehicleTypes = response.data;
        });
      }
      ,
      /** 搜索可拼车按钮操作 */
      carQuery() {
        // debugger
        this.queryParams.ids = this.ids.toString();
        this.queryParams.pageNum = 1;
        this.carloading = true;
        getCanUseCarList(this.queryParams).then(response => {
          if (response.code === 200) {
            this.carList = response.data;
            this.cartotal = response.total;
            this.carloading = false;
          } else {
            this.open = false;
            this.resetQuery();
          }
        });
      }
      ,
      resetCar() {
        this.resetForm("carform");
        this.carQuery();
      }
      ,
      busQuery() {
        this.queryParams.ids = this.ids.toString();
        this.busloading = true;
        getCanUseBusList(this.queryParams).then(response => {
          if (response.code === 200) {
            this.busList = response.data;
            this.bustotal = response.total;
            this.busloading = false;
          } else {
            this.edit = false;
            this.resetBus();
          }
        });
      }
      ,
      resetBus() {
        this.resetForm("busform");
        this.busQuery();
      }
      ,
      driverQuery() {
        this.driverloading = true;
        getCanUseDriverList(this.queryParams).then(response => {
          console.log(response);
          if (response.code === 200) {
            this.driverList = response.data;
            this.drivertotal = response.total;
            this.driverloading = false;
          } else {
            this.driver = false;
            this.carQuery();
          }
        });
      }
      ,
      resetDriver() {
        this.resetForm("driverform");
        this.driverQuery();
      }
      ,
      handleCurrentChange(val) {
        this.currentRow = val;
        // console.log(val);
        // console.log(this.currentRow);
        this.queryParams.vehicleId = val.vehicleId;
        this.vehicleId = val.vehicleId;
        // console.log(this.vehicleInfoId);
      }
      ,
      siteTypeFormat(row, column) {
        return this.selectDictLabel(this.siteTypeOptions, row.siteType);
      }
      ,
      siteLevelFormat(row, column) {
        return this.selectDictLabel(this.siteLevelOptions, row.siteLevel);
      }
      ,
      changeSiteName(val) {
        if (val) {
          this.form.sitePy = vPinyin.getPinyin(val)
          this.form.siteSp = vPinyin.getPinyinSx(val)
        }

      }
      ,
      drawPolygon() {
        console.log('设置自定义电子围栏')
        if (this.drawSiteFence) {
          this.$refs.map.addSiteMarkFlag = false
          this.$refs.map.drawPolygon()
        } else {
          this.$refs.map.addSiteMarkFlag = true
          this.$refs.map.removeMouseTool(true)
        }
      }
      ,
      getLngLatInfo(msg) {
        console.log("子组件传过来的值", msg)
        /*this.lng = msg.lnglat.lng
        this.lat = msg.lnglat.lat*/
        this.form.siteName = msg.siteName
        this.form.lng = msg.lnglat.lng
        this.form.lat = msg.lnglat.lat
        console.log(this.$refs.form.model)
      }
      ,
      //添加点半径
      addSiteCircle(val) {
        // 调用子组件的addSiteCircle方法
        console.log("调用子组件的addSiteCircle方法")
        this.$refs.map.addSiteCircle(this.form.lng, this.form.lat, val)
      }
      ,

      /** 查询城市站点列表 */
      getList() {
        let day3 = new Date();
        day3.setTime(day3.getTime() + 24 * 60 * 60 * 1000);
        let s3 = day3.getFullYear() + "-" + (day3.getMonth() + 1) + "-" + day3.getDate();
        this.queryParams.beginTime = s3 + " 00:00";
        this.queryParams.endTime = s3 + " 24:00";
        getWaitlist(this.queryParams).then(response => {
          if (response.code === 200) {
            this.rentalOrderList = response.data;
            let mapData = [];
            if (this.rentalOrderList.length > 0) {
              for (let i = 0; i < this.rentalOrderList.length; i++) {
                let latlng = [];
                latlng[0] = this.rentalOrderList[i].longitude;
                latlng[1] = this.rentalOrderList[i].latitude;
                let siteName = "";
                let seatNum = 0;
                if (this.rentalOrderList[i].orderType == "3") {
                  siteName = this.rentalOrderList[i].beginStation + "(机场巴士订座单," + this.rentalOrderList[i].seatNum + "人,用车时间" + this.rentalOrderList[i].useTime + ")";
                  seatNum = this.rentalOrderList[i].seatNum;
                } else if (this.rentalOrderList[i].orderType == "4") {
                  siteName = this.rentalOrderList[i].beginStation + "(订车单,用车人数" +
                    this.rentalOrderList[i].seatNum + ",可售座位数" +
                    this.rentalOrderList[i].sellNumber + ",用车时间" + this.rentalOrderList[i].useTime + ")";
                } else if (this.rentalOrderList[i].orderType == "5") {
                  siteName = this.rentalOrderList[i].beginStation + "(机场巴士包车单,车型为" +
                    this.rentalOrderList[i].carDes + ",用车时间" + this.rentalOrderList[i].useTime + ")";
                  seatNum = 1;
                } else if (this.rentalOrderList[i].orderType == "6") {
                  siteName = this.rentalOrderList[i].beginStation + "(定制客运订座单," + this.rentalOrderList[i].seatNum + "人,用车时间" + this.rentalOrderList[i].useTime + ")";
                  seatNum = this.rentalOrderList[i].seatNum;
                } else if (this.rentalOrderList[i].orderType == "7") {
                  siteName = this.rentalOrderList[i].beginStation + "(定制客运包车单,车型为" +
                    this.rentalOrderList[i].carDes + ",用车时间" + this.rentalOrderList[i].useTime + ")";
                  seatNum = 1;
                } else if (this.rentalOrderList[i].orderType == "8") {
                  siteName = this.rentalOrderList[i].beginStation + "(自由包车单,车型为" +
                    this.rentalOrderList[i].carDes + ",用车时间" + this.rentalOrderList[i].useTime + ")";
                  seatNum = 1;
                }
                let jsonObj = {
                  "siteName": siteName,
                  "id": this.rentalOrderList[i].id,
                  "latlng": latlng,
                  "productCode": this.rentalOrderList[i].productCode,
                  "orderType": this.rentalOrderList[i].orderType,
                  "useTime": this.rentalOrderList[i].useTime,
                  "seatNum": seatNum,
                  "useHour": this.rentalOrderList[i].useHour
                };
                console.log("jsonObj")
                mapData[i] = jsonObj;
              }
            }
            this.$refs.map1.addSiteMarks(mapData);
          }
        });
      }
      ,

      getCityList() {
        listCity(this.cityQueryParams).then(response => {
          this.cityList = response.rows;
        })
      }
      ,
      // 取消按钮
      cancel() {
        this.open = false;
        this.queryParams.beginTime = "";
        this.queryParams.endTime = "";
        this.reset();
      }
      ,
      // 表单重置
      reset() {
        this.form = {
          siteId: undefined,
          cityId: undefined,
          siteCode: undefined,
          siteName: undefined,
          sitePy: undefined,
          siteSp: undefined,
          siteType: undefined,
          siteLevel: undefined,
          siteRadius: undefined,
          siteFence: undefined,
          createId: undefined,
          createTime: undefined,
          updateId: undefined,
          updateTime: undefined
        };
        this.resetForm("form");
        this.siteFence = ''
        this.drawSiteFence = false
        this.lng = ''
        this.lat = ''
      }
      ,
      /** 搜索按钮操作 */
      handleQuery() {
        this.getWaitlist();
      }
      ,
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      }
      ,
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.siteId)
        this.single = selection.length != 1
        this.multiple = !selection.length
      }
      ,
      /** 新增按钮操作 */
      handleAdd() {
        // console.log(this.$refs.map1.getIds)
        if (this.$refs.map1.getIds.length == 0) {
          this.$message({
            message: '请选择订单，再操作',
            type: 'warning'
          });
          return
        } else if (Array.from(new Set(this.$refs.map1.productCodes)).length > 1) {
          this.$message({
            message: '请选择同一线路产品的订单',
            type: 'warning'
          });
          return
        } else if (Array.from(new Set(this.$refs.map1.orderDates)).length > 1) {
          this.$message({
            message: '请选择同一天的订单',
            type: 'warning'
          });
          return
        } else {
          // this.orderTypes = Array.from(new Set(this.orderTypes));
          // console.log(this.orderTypes.length);
          // console.log(this.orderTypes.toString().indexOf("4") != -1);
          if (Array.from(new Set(this.$refs.map1.orderTypes)).length > 1 || (Array.from(new Set(this.$refs.map1.orderTypes)).length == 1 && this.$refs.map1.orderTypes.length > 1 && this.$refs.map1.orderTypes.toString().indexOf("5") != -1) || (Array.from(new Set(this.$refs.map1.orderTypes)).length == 1 && this.$refs.map1.orderTypes.length > 1 && this.$refs.map1.orderTypes.toString().indexOf("4") != -1)) {
            this.$message({
              message: '请选择同一订单类型的订单或者不能同时选择两个包车或者拼车订单',
              type: 'warning'
            });
            return
          }
        }
        console.log(this.$refs.map1.orderDates[0]);
        console.log("订单类型" + this.$refs.map1.orderTypes[0])
        if (this.$refs.map1.orderTypes[0] == 3 || this.$refs.map1.orderTypes[0] == 6) {
          this.isShow = true;
        } else {
          this.isShow = false;
        }
        console.log("是否显示标志" + this.isShow);
        this.queryParams.endTime = "";
        // console.log("用车时间" + this.$refs.map1.useTimes[0])
        // console.log("用车时间截串" + this.$refs.map1.useTimes[0].substring(0, 14))
        // this.queryParams.beginTime = this.$refs.map1.orderDates[0] + " 00:00:00";
        this.queryParams.beginTime = this.$refs.map1.useTimes[0].substring(0, 14) + " 00:00";
        if (this.$refs.map1.useHours[0] != '' || this.$refs.map1.useHours[0] != null) {
          console.log("路线用时" + this.$refs.map1.useHours[0]);
          this.useHour = this.$refs.map1.useHours[0];
          var t = new Date(this.queryParams.beginTime);//你已知的时间
          var t_s = t.getTime();//转化为时间戳毫秒数
          this.queryParams.endTime = new Date();//定义一个新时间
          this.queryParams.endTime.setTime(t_s + this.useHour * 1000 * 60 * 60);//设置新时间比旧时间多一小时
        }
        // this.queryParams.endTime = this.// if (this.$refs.map1.orderTypes == 3) {

        //   this.queryParams.beginTime = this.$refs.map1.orderDates[0] + " 00:00:00";
        // } else {
        //   this.queryParams.beginTime = this.$refs.map1.useTimes[0] + ":00";
        // }
        this
          .ids = this.$refs.map1.getIds;
        // this.getTreeselect();
        // this.getVehicleTypeselect();
        this.reset();
        console.log(this.ids)
        // this.open=true;
        this.confirmBus = true;
        this.title = "生成拼包车班次";
        // this.carQuery();
      }
      ,
      /** 选择司机按钮 */
      updateDriver(row) {
        if (this.queryParams.beginTime == '' || this.queryParams.endTime == '') {
          this.$message({
            message: '请填写班次开始时间和结束时间，再操作',
            type: 'warning'
          });
          return
        }
        this.updateVehicleId = row.vehicleId;
        this.updatedriverId = row.driverId;
        this.queryParams.vehicleId = row.vehicleId;
        this.driver = true;
        getCanUseDriverList(this.queryParams).then(response => {
          console.log(response);
          if (response.code === 200) {
            this.driverList = response.data;
            this.drivertotal = response.total;
            this.driverloading = false;
          } else {
            this.driver = false;
          }
        });
        this.getTreeselect();
        this.title = "选择可用司机";
      }
      ,
      /** 添加已有班次按钮操作 */
      handleUpdate() {
        if (this.$refs.map1.getIds.length == 0) {
          this.$message({
            message: '请选择订单，再操作',
            type: 'warning'
          });
          return
        } else if (Array.from(new Set(this.$refs.map1.productCodes)).length > 1) {
          this.$message({
            message: '请选择同一线路产品的订单',
            type: 'warning'
          });
          return
        } else {
          this.orderTypes = Array.from(new Set(this.$refs.map1.orderTypes));
          if (Array.from(new Set(this.$refs.map1.orderTypes)).length > 1 || (this.$refs.map1.orderTypes.length > 0 && this.$refs.map1.orderTypes.toString().indexOf("5") != -1) || (this.$refs.map1.orderTypes.length > 0 && this.$refs.map1.orderTypes.toString().indexOf("4") != -1)) {
            this.$message({
              message: '请选择拼座订单添加到已有班次',
              type: 'warning'
            });
            return
          }
        }
        this.reset();
        this.edit = true;
        this.title = "添加拼包车班次";
      }
      ,
      /** 提交按钮 */
      submitForm: function () {
        let polygonArr = this.$refs.map.polygonArr
        for (let i = 0; i < polygonArr.length; i++) {
          this.siteFence += polygonArr[i].lng + "," + polygonArr[i].lat + ";"
        }
        this.siteFence = this.siteFence.substring(0, this.siteFence.length - 1);
        this.form.siteFence = this.siteFence
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.siteId != undefined) {
              updateSite(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {
              addSite(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              });
            }
          }
        });
        this.$refs.map.removeAll()
      }
      ,
      /** 删除按钮操作 */
      handleDelete(row) {
        const siteIds = row.siteId || this.ids;
        this.$confirm('是否确认删除城市站点编号为"' + siteIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delSite(siteIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      }
      ,
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有城市站点数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportSite(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function () {
        });
      }
      ,
      removeAll() {
        this.$refs.map1.removeAll()
      }
    }
  }
  ;
</script>

