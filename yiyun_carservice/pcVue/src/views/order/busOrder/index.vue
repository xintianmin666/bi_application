<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px">
      <el-form-item label="车牌号" prop="vehiclePlateNo" style="float:left;width: 480px">
        <el-input
          v-model="queryParams.vehiclePlateNo"
          placeholder="请输入车牌号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="司机姓名" prop="driverName" style="float:left;width: 480px">
        <el-input
          v-model="queryParams.driverName"
          placeholder="请输入司机姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="司机联系方式" prop="driverPhone" style="float:left;width: 480px">
        <el-input
          v-model="queryParams.driverPhone"
          placeholder="请输入司机联系方式"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="班次开始时间" prop="taskStartTime" style="float:left;width: 480px">
        <el-date-picker clearable size="small"
                        v-model="queryParams.beginTime"
                        type="datetime"
                        placeholder="班次开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="班次结束时间" prop="taskEndTime" style="float:left;width: 480px">
        <el-date-picker clearable size="small"
                        v-model="queryParams.endTime"
                        type="datetime"
                        placeholder="选择任务结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="班次类型" prop="taskType" style="float:left;width: 480px">
        <el-select v-model="queryParams.taskType" placeholder="请选择" clearable size="small">
          <el-option label="机场巴士订座单" value="3">机场巴士订座单</el-option>
          <!--<el-option label="拼车单" value="4">订车单</el-option>-->
          <!--<el-option label="机场巴士包车单" value="5">机场巴士包车单</el-option>-->
          <el-option label="定制客运订座单" value="6">定制客运订座单</el-option>
          <!--<el-option label="定制客运包车单" value="7">定制客运包车单</el-option>-->
          <!--<el-option label="定制客运包车单" value="8">自由包车单</el-option>-->
        </el-select>
      </el-form-item>
      <el-form-item label="班次状态" prop="taskStatus" style="float:left;width: 480px">
        <el-select v-model="queryParams.taskStatus" placeholder="请选择" clearable
                   size="small">
          <el-option label="待确认" value="1">待确认</el-option>
          <el-option label="已确认" value="2">已确认</el-option>
          <el-option label="已发班" value="3">已发班</el-option>
          <el-option label="已完成" value="4">已完成</el-option>
          <el-option label="已取消" value="5">已取消</el-option>
        </el-select>
      </el-form-item>
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
      <el-form-item label="剩余座位数" prop="remainingSeatNum">
        <el-input
          v-model="queryParams.remainingSeatNum"
          placeholder="请输入剩余座位数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--<el-col :span="1.5">-->
      <!--<el-button-->
      <!--type="primary"-->
      <!--icon="el-icon-plus"-->
      <!--size="mini"-->
      <!--@click="handleAdd"-->
      <!--v-hasPermi="['order:busOrder:add']"-->
      <!--&gt;新增-->
      <!--</el-button>-->
      <!--</el-col>-->
      <!--<el-col :span="1.5">-->
      <!--<el-button-->
      <!--type="success"-->
      <!--icon="el-icon-edit"-->
      <!--size="mini"-->
      <!--:disabled="single"-->
      <!--@click="handleUpdate"-->
      <!--v-hasPermi="['order:busOrder:edit']"-->
      <!--&gt;修改-->
      <!--</el-button>-->
      <!--</el-col>-->
      <!--<el-col :span="1.5">-->
      <!--<el-button-->
      <!--type="danger"-->
      <!--icon="el-icon-delete"-->
      <!--size="mini"-->
      <!--:disabled="multiple"-->
      <!--@click="handleDelete"-->
      <!--v-hasPermi="['order:busOrder:remove']"-->
      <!--&gt;删除-->
      <!--</el-button>-->
      <!--</el-col>-->
      <!--<el-col :span="1.5">-->
      <!--<el-button-->
      <!--type="warning"-->
      <!--icon="el-icon-download"-->
      <!--size="mini"-->
      <!--@click="handleExport"-->
      <!--v-hasPermi="['order:busOrder:export']"-->
      <!--&gt;导出-->
      <!--</el-button>-->
      <!--</el-col>-->
    </el-row>

    <el-table v-loading="loading" :data="busOrderList">
      <!--<el-table-column type="selection" width="55" align="center"/>-->
      <el-table-column label="调度单号" align="center" prop="dispatchOrdercode"/>
      <el-table-column label="车牌号" align="center" prop="vehiclePlateNo"/>
      <el-table-column label="司机姓名" align="center" prop="driverName"/>
      <el-table-column label="司机联系方式" align="center" prop="driverPhone"/>
      <el-table-column label="班次开始时间" align="center" prop="taskStartTime" width="180"/>
      <!--<template slot-scope="scope">-->
      <!--<span>{{ parseTime(scope.row.taskStartTime, '{y}-{m}-{d}') }}</span>-->
      <!--</template>-->
      <!--</el-table-column>-->
      <el-table-column label="班次结束时间" align="center" prop="taskEndTime" width="180"/>
      <!--<template slot-scope="scope">-->
      <!--<span>{{ parseTime(scope.row.taskEndTime, '{y}-{m}-{d}') }}</span>-->
      <!--</template>-->
      <!--</el-table-column>-->
      <el-table-column label="任务开始点名称" align="center" prop="taskStartSiteName"/>
      <el-table-column label="任务结束点名称" align="center" prop="taskEndSiteName"/>
      <el-table-column label="任务类型" align="center" prop="taskType" :formatter="taskTypeFormatter"/>
      <el-table-column label="任务状态" align="center" prop="taskStatus" :formatter="taskStatusFormatter"/>
      <el-table-column label="产品名称" align="center" prop="productName"/>
      <el-table-column label="车辆乘客座位数" align="center" prop="passengerSeatNum"/>
      <el-table-column label="剩余座位数" align="center" prop="remainingSeatNum"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!--<el-button-->
          <!--size="mini"-->
          <!--type="text"-->
          <!--icon="el-icon-edit"-->
          <!--@click="handleUpdate(scope.row)"-->
          <!--v-hasPermi="['order:busOrder:edit']"-->
          <!--&gt;选择派单司机-->
          <!--</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['order:busOrder:remove']"
          >添加乘客
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 查找可用车辆弹框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="carform" :model="queryParams" :inline="true" :rules="rules" label-width="200px">
        <!--<el-form-item label="班次开始时间" prop="beginTime" style="float:left;width: 450px">-->
        <!--<el-date-picker-->
        <!--v-model="queryParams.beginTime"-->
        <!--type="datetime"-->
        <!--placeholder="选择日期"-->
        <!--value-format="yyyy-MM-dd HH:mm:ss">-->
        <!--</el-date-picker>-->
        <!--</el-form-item>-->
        <!--<el-form-item label="班次结束时间" prop="endTime" style="float:left;width: 450px">-->
        <!--<el-date-picker-->
        <!--v-model="queryParams.endTime"-->
        <!--type="datetime"-->
        <!--placeholder="选择日期"-->
        <!--value-format="yyyy-MM-dd HH:mm:ss">-->
        <!--</el-date-picker>-->
        <!--</el-form-item>-->
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
                       icon="el-icon-plus" @click="confirmNewShift(scope.row)">生成新班次
            </el-button>
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
    <!-- 查找可用订单 -->
    <el-dialog :title="title" :visible.sync="edit" width="1400px" append-to-body>
      <el-form ref="busform" :model="form" :inline="true" :rules="rules" label-width="120px">
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
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="busQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetBus">重置</el-button>
        </el-form-item>
      </el-form>
      <el-row :gutter="10" class="mb2">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="addBus"
            v-hasPermi="['order:busOrder:add']"
          >确认添加
          </el-button>
        </el-col>
      </el-row>
      <el-table v-loading="loading" :data="busList" highlight-current-row @selection-change="handleSelectionChange">
        <el-table-column type="selection" :selectable="handleDisable" width="55" align="center"/>
        <!--<el-table-column label="主键" align="center" prop="id"/>-->
        <el-table-column label="乘客姓名" align="center" prop="reserveName"/>
        <el-table-column label="乘客手机号" align="center" prop="reserveMobile"/>
        <el-table-column label="用车时间" align="center" prop="useTime"/>
        <el-table-column label="预定座位数" align="center" prop="seatNum"/>
        <el-table-column label="上车点" align="center" prop="beginStation"/>
        <el-table-column label="下车点" align="center" prop="endStation"/>
        <!--<el-table-column-->
        <!--label="操作" align="center">-->
        <!--<template slot-scope="scope">-->
        <!--<el-button type="success" @click="addBus(scope.row)">添加至此班次</el-button>-->
        <!--</template>-->
        <!--</el-table-column>-->
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
  import {
    confirmNewShift,
    deptAll,
    getbusOrderlist,
    getCanUseCarList,
    getCanUseDriverList,
    getCanUseOrderList,
    productCodeAll,
    updateTaxiOrder,
    vehicleTypeAll
  } from "@/api/order/rentalOrder";

  export default {
    name: "BusOrder",
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        passengerNums: [],
        passengerNum: '',
        remainingSeatNum: '',
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
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
        total: 0,
        // 调度班次管理表格数据
        busOrderList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open:
          false,
        edit:
          false,
        driver:
          false,
        productOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          dispatchOrdercode: undefined,
          vehicleInfoId: undefined,
          vehiclePlateNo: undefined,
          driverId: undefined,
          driverName: undefined,
          driverPhone: undefined,
          taskStartTime: undefined,
          taskEndTime: undefined,
          taskStartSiteName: undefined,
          taskStartSite: undefined,
          taskEndSiteName: undefined,
          taskEndSite: undefined,
          taskType: undefined,
          taskStatus: undefined,
          createUserId: undefined,
          modifyTime: undefined,
          modifyUserId: undefined,
          productId: undefined,
          productName: undefined,
          passengerSeatNum: undefined,
          remainingSeatNum: undefined,
          ninPinNum: undefined,
          pzNum: undefined,
          noCancel: undefined,
          busId: ''
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    created() {
      this.getList();
      productCodeAll().then(response => {
        this.productOptions = response.data;
      });
    },
    methods: {
      /** 查询调度班次管理列表 */
      getList() {
        this.loading = true;
        getbusOrderlist(this.queryParams).then(response => {
          this.busOrderList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          vehicleTaskStatusId: undefined,
          dispatchOrdercode: undefined,
          vehicleInfoId: undefined,
          vehiclePlateNo: undefined,
          driverId: undefined,
          driverName: undefined,
          driverPhone: undefined,
          taskStartTime: undefined,
          taskEndTime: undefined,
          taskStartSiteName: undefined,
          taskStartSite: undefined,
          taskEndSiteName: undefined,
          taskEndSite: undefined,
          taskType: undefined,
          taskStatus: "0",
          createTime: undefined,
          createUserId: undefined,
          modifyTime: undefined,
          modifyUserId: undefined,
          remark: undefined,
          productId: undefined,
          productName: undefined,
          passengerSeatNum: undefined,
          remainingSeatNum: undefined,
          ninPinNum: undefined,
          pzNum: undefined,
          noCancel: undefined
        };
        this.resetForm("form");
      },
      taskTypeFormatter(row) {
        return row.taskType == 3 ? "机场巴士拼座单" : row.taskType == 5 ? "机场巴士包车单" : row.taskType == 6 ? "定制客运拼座单" : row.taskType == 7 ? "定制客运包车单" : "自由包车单"
      },
      taskStatusFormatter(row) {
        if (row.taskStatus == 1) {
          return "待确认"
        } else if (row.taskStatus == 2) {
          return "已确认"
        } else if (row.taskStatus == 3) {
          return "已发班"
        } else if (row.taskStatus == 4) {
          return "已完成"
        } else if (row.taskStatus == 5) {
          return "已取消"
        }
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },

      /** 新增按钮操作 */
      handleAdd(row) {
        this.reset();
        this.queryParams.busId = row.dispatchOrdercode;
        this.remainingSeatNum = row.remainingSeatNum;
        this.edit = true;
        this.title = "添加乘客";
        this.busQuery();
      },

      busQuery() {
        this.busloading = true;
        getCanUseOrderList(this.queryParams).then(response => {
          if (response.code === 200) {
            this.busList = response.data;
            this.bustotal = response.total;
            this.busloading = false;
          } else {
            this.edit = false;
            this.resetBus();
          }
        });
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.passengerNums = selection.map(item => item.seatNum)
        this.single = selection.length != 1
        this.multiple = !selection.length
        console.log(this.orderDates);
      },
      addBus() {
        if (this.ids.length == 0) {
          this.$message({
            message: '请选择订单，再操作',
            type: 'warning'
          });
          return
        }
        this.passengerNum = 0;
        for (let i = 0; i < this.passengerNums.length; i++) {
          this.passengerNum += parseInt(this.passengerNums[0]);
        }

        if (this.passengerNum > this.remainingSeatNum) {
          this.$message({
            message: '所选订单预定座位总数大于班次剩余座位数，请重新选择',
            type: 'warning'
          });
          return
        }

        this.queryParams.ids = this.ids.toString();
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
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        if (row.driverId != '' || row.driverId != null) {
          this.$message({
            message: '次班次已选择派单司机,请选择其他班次',
            type: 'warning'
          });
          return
        }
        this.reset();
        this.getTreeselect();
        this.getVehicleTypeselect();
        this.queryParams.busId = row.dispatchOrdercode;
        this.open = true;
        // this.confirmBus = true;
        this.title = "选择派单车辆";
        this.carQuery();
      },
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
      /** 搜索可拼车按钮操作 */
      carQuery() {
        // debugger
        // this.queryParams.busId = this.ids.toString();
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
      /** 提交按钮 */
      submitForm: function () {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.vehicleTaskStatusId != undefined) {
              updateBusOrder(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {
              addBusOrder(this.form).then(response => {
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
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const vehicleTaskStatusIds = row.vehicleTaskStatusId || this.ids;
        this.$confirm('是否确认删除调度班次管理编号为"' + vehicleTaskStatusIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delBusOrder(vehicleTaskStatusIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有调度班次管理数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportBusOrder(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function () {
        });
      }
    }
  };
</script>
