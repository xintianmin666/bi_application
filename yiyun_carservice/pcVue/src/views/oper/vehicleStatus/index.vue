<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="车辆排班" name="first">
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px">
          <el-form-item label="日期范围" prop="startAndEndTime">
            <el-date-picker
              value-format="yyyy-MM-dd"
              v-model="startAndEndTime"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="车牌号" prop="vehiclePlateNo">
            <el-input v-model="queryParams.vehiclePlateNo" @keyup.enter.native="handleQuery"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="20">
          <el-col :span="2">
            <div>班次状态说明</div>
          </el-col>
          <el-col :span="2">
            <div class="taskStatus1">待确认</div>
          </el-col>
          <el-col :span="2">
            <div class="taskStatus2">已确认</div>
          </el-col>
          <el-col :span="2">
            <div class="taskStatus3">已发班</div>
          </el-col>
          <el-col :span="2">
            <div class="taskStatus4">已完成</div>
          </el-col>
          <el-col :span="2">
            <div class="taskStatus5">已取消</div>
          </el-col>
        </el-row>
        <el-table
          :row-style="{height:'40px'}"
          :cell-style="{padding:'0px'}"
          :border="true"
          :data="tableData"
          style="width: 100%"
          max-height="600px"
          ref="multipleTable">
          <el-table-column
            align="center"
            :label=columnItem.label
            :prop=columnItem.prop
            fixed="left"
            v-if="columnIndex==0?true:false"
            width="180" v-for="(columnItem,columnIndex) in columnItems">
            <template slot-scope="scope">
              <div v-if="columnIndex==0" @click="addVehicleTaskStatusDetail(scope.row[columnItem.prop])">
                {{scope.row[columnItem.prop][columnItem.prop]}}
              </div>
              <div style="cursor:pointer;" v-if="columnIndex!=0" v-for="item in scope.row[columnItem.prop]"
                   @click="getVehicleTaskStatusDetail(scope.row[columnItem.prop])">
                <span :class=setTaskStatusClass(item.taskStatus) style="font-size: 16px">{{item.taskStartTime.substr(11,5)+'-'+item.taskEndTime.substr(11,5)}}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            :label=columnItem.label
            :prop=columnItem.prop
            v-if="columnIndex==0?false:true"
            width="180" v-for="(columnItem,columnIndex) in columnItems">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <div style="cursor:pointer;" v-if="columnIndex!=0" v-for="item in scope.row[columnItem.prop]">
                  <p>任务开始时间：{{item.taskStartTime}}</p>
                  <p>任务结束时间：{{item.taskEndTime}}</p>
                  <p v-if="item.lockCar==1">任务类型：{{item.lockCarRemark}}</p>
                  <p v-if="item.lockCar==0">
                    任务类型：{{item.taskType==(3||6)?'拼座':'包车'}}
                  </p>
                  <p v-if="item.lockCar==0">
                    出发地：{{item.taskStartSiteName}}
                  </p>
                  <p v-if="item.lockCar==0">
                    目的地：{{item.taskEndSiteName}}
                  </p>
                </div>
                <div slot="reference" class="name-wrapper">
                  <div v-if="columnIndex==0">
                    {{scope.row[columnItem.prop][columnItem.prop]}}
                  </div>
                  <div style="cursor:pointer;" v-if="columnIndex!=0" v-for="item in scope.row[columnItem.prop]"
                       @click="getVehicleTaskStatusDetail(scope.row[columnItem.prop])">
                    <div :class=setTaskStatusClass(item.taskStatus) style="position: relative">
                      <div style="font-size: 22px;text-align: left">
                        {{item.taskStartTime.substr(11,5)+'-'+item.taskEndTime.substr(11,5)}}
                      </div>
                      <div style="font-size: 14px;position: absolute;top:-4px;right: 0">
                        <span v-if="item.lockCar==1">{{item.lockCarRemark}}</span>
                        <span v-if="item.lockCar==0">
                        {{item.taskType==(3||6)?'拼座':'包车'}}
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              </el-popover>
            </template>
          </el-table-column>
        </el-table>
        <!--<pagination
          style="height: 30px"
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />-->
      </el-tab-pane>
      <el-tab-pane label="驾驶员排班" name="second">
        <el-form :model="queryParams1" ref="queryForm1" :inline="true" label-width="120px">
          <el-form-item label="日期范围" prop="startAndEndTime">
            <el-date-picker
              value-format="yyyy-MM-dd"
              v-model="startAndEndTime1"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
            </el-date-picker>
            <el-form-item label="驾驶员姓名" prop="driverName">
              <el-input v-model="queryParams1.driverName" @keyup.enter.native="handleQuery"></el-input>
            </el-form-item>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery1">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery1">重置</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="20">
          <el-col :span="2">
            <div>班次状态说明</div>
          </el-col>
          <el-col :span="2">
            <div class="taskStatus1">待确认</div>
          </el-col>
          <el-col :span="2">
            <div class="taskStatus2">已确认</div>
          </el-col>
          <el-col :span="2">
            <div class="taskStatus3">已发班</div>
          </el-col>
          <el-col :span="2">
            <div class="taskStatus4">已完成</div>
          </el-col>
          <el-col :span="2">
            <div class="taskStatus5">已取消</div>
          </el-col>
        </el-row>
        <el-table
          :row-style="{height:'40px'}"
          :cell-style="{padding:'0px'}"
          :border="true"
          max-height="600px"
          :data="driverTableData"
          style="width: 100%"
          ref="multipleTable">
          <el-table-column
            v-if="columnIndex==0?true:false"
            fixed="left"
            align="center"
            :label=columnItem.label
            :prop=columnItem.prop
            width="180" v-for="(columnItem,columnIndex) in driverColumnItems">
            <template slot-scope="scope">
              <div v-if="columnIndex==0">
                {{scope.row[columnItem.prop][columnItem.prop]}}
              </div>
              <div style="cursor:pointer;" v-if="columnIndex!=0" v-for="item in scope.row[columnItem.prop]"
                   @click="getVehicleTaskStatusDetail(scope.row[columnItem.prop])">
                <span :class=setTaskStatusClass(item.taskStatus) style="font-size: 16px">{{item.taskStartTime.substr(11,5)+'-'+item.taskEndTime.substr(11,5)}}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            v-if="columnIndex==0?false:true"
            align="center"
            :label=columnItem.label
            :prop=columnItem.prop
            width="180" v-for="(columnItem,columnIndex) in driverColumnItems">
            <template slot-scope="scope">
              <div v-if="columnIndex==0">
                {{scope.row[columnItem.prop][columnItem.prop]}}
              </div>
              <div style="cursor:pointer;" v-if="columnIndex!=0" v-for="item in scope.row[columnItem.prop]"
                   @click="getVehicleTaskStatusDetail(scope.row[columnItem.prop])">
                <span :class=setTaskStatusClass(item.taskStatus) style="font-size: 16px">{{item.taskStartTime.substr(11,5)+'-'+item.taskEndTime.substr(11,5)}}</span>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <!-- <pagination
           style="height: 30px"
           v-show="total1>0"
           :total="total1"
           :page.sync="queryParams1.pageNum"
           :limit.sync="queryParams1.pageSize"
           @pagination="getDriverList"
         />-->
      </el-tab-pane>
    </el-tabs>


    <el-dialog title="运力详情" :visible.sync="dialogTableVisible" width="80%">
      <div class="ylxq">
        <el-row :gutter="20">
          <el-col :span="12"><span class="vsTitle">车牌号</span><span class="vsItem">{{vehicleTaskStatusDetail[0].vehiclePlateNo}}</span>
          </el-col>
          <el-col :span="12"><span class="vsTitle">座位数</span><span class="vsItem">{{vehicleTaskStatusDetail[0].passengerSeatNum}}</span>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12"><span class="vsTitle">驾驶员</span><span class="vsItem">{{vehicleTaskStatusDetail[0].driverName}}</span>
          </el-col>
          <el-col :span="12"><span class="vsTitle">手机号</span><span class="vsItem">{{vehicleTaskStatusDetail[0].driverPhone}}</span>
          </el-col>
        </el-row>
      </div>
      <div style="font-size: 20px;margin-bottom: 10px">班次列表</div>
      <el-table :data="vehicleTaskStatusDetail">
        <el-table-column property="dispatchOrdercode" label="调度单号"></el-table-column>
        <el-table-column property="taskStartTime" label="任务开始时间" width="160"></el-table-column>
        <el-table-column property="taskEndTime" label="任务结束时间" width="160"></el-table-column>
        <el-table-column property="productName" label="线路名称" width="160"></el-table-column>
        <el-table-column property="vehiclePlateNo" label="车牌号"></el-table-column>
        <el-table-column property="passengerSeatNum" label="座位数"></el-table-column>
        <el-table-column property="remainingSeatNum" label="剩余座位数"></el-table-column>
        <el-table-column property="passengerNum" label="已有乘客数">
          <template slot-scope="scope">
            {{scope.row.passengerSeatNum-scope.row.remainingSeatNum}}
          </template>
        </el-table-column>
        <el-table-column property="driverName" label="驾驶员"></el-table-column>
        <el-table-column property="driverPhone" label="手机号"></el-table-column>
        <el-table-column property="taskType" label="类型" :formatter="taskTypeFormatter"></el-table-column>
        <el-table-column property="taskStatus" label="状态" :formatter="taskStatusFormatter"></el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <div v-if="scope.row.lockCar==0">
              <div v-if="scope.row.taskStatus==1||scope.row.taskStatus==2">
                <el-button
                  size="mini"
                  @click="handleEditCar(scope.row)">更换车辆
                </el-button>
                <el-button
                  size="mini"
                  type="primary"
                  @click="handleEditDriver(scope.row)">更换司机
                </el-button>
              </div>
            </div>
            <div v-if="scope.row.lockCar==1">
              <el-button
                size="mini"
                @click="deleteCarTask(scope.row)">删除任务
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <el-dialog title="可更换车辆列表" :visible.sync="editCarVisible" width="80%">
      <el-row :gutter="20">
        <el-col :span="20" :xs="24">
          <el-form :model="queryParams2" ref="queryForm2" :inline="true" label-width="120px">
            <el-form-item label="最大承载人数" prop="carryMax">
              <el-input
                v-model="queryParams2.carryMax"
                placeholder="请输入最大承载人数"
                clearable
                size="small"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="车牌号" prop="licenseTagno">
              <el-input
                v-model="queryParams2.licenseTagno"
                placeholder="请输入车牌号"
                clearable
                size="small"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="是否为内部车辆" prop="belong">
              <el-select v-model="queryParams2.belong" placeholder="请选择" clearable size="small">
                <el-option label="是" value="1"/>
                <el-option label="否" value="0"/>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery2">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery2">重置</el-button>
            </el-form-item>
          </el-form>

          <el-table v-loading="loading" :data="canUseCarList">
            <el-table-column label="主键" align="center" prop="vehicleId"/>
            <el-table-column label="部门" align="center" prop="dept.deptName"/>
            <el-table-column label="可载乘客数" align="center" prop="vehicleType.passengerNum"/>
            <el-table-column label="车辆类型" align="center" prop="vehicleType.typeName"/>
            <el-table-column label="车牌号" align="center" prop="licenseTagno"/>
            <el-table-column label="备注" align="center" prop="vehicleType.remark"/>
            <el-table-column label="是否为内部车辆" align="center" prop="belong" :formatter="belongFormat"/>
            <el-table-column
              fixed="right"
              label="操作"
              width="100">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="primary"
                  @click="editCar(scope.row)">确认替换
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <!--<pagination
            style="height: 30px"
            v-show="total2>0"
            :total="total2"
            :page.sync="queryParams2.pageNum"
            :limit.sync="queryParams2.pageSize"
            @pagination="getCanUseCar"
          />-->
        </el-col>
      </el-row>
    </el-dialog>
    <el-dialog title="可更换驾驶员列表" :visible.sync="editDriverVisible" width="80%">
      <el-form :model="queryParams3" ref="queryForm3" :inline="true" label-width="100px">
        <el-form-item label="驾驶员姓名" prop="name">
          <el-input
            v-model="queryParams3.name"
            placeholder="请输入驾驶员姓名"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="驾驶员电话" prop="phone">
          <el-input
            v-model="queryParams3.phone"
            placeholder="请输入驾驶员电话"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="驾驶员驾照号" prop="driverLicenseNo">
          <el-input
            v-model="queryParams3.driverLicenseNo"
            placeholder="请输入驾驶员驾照号"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery3">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery3">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="loading" :data="canUseDriverList">
        <el-table-column label="驾驶员姓名" align="center" prop="name"/>
        <el-table-column label="驾驶员电话" align="center" prop="phone"/>
        <el-table-column label="驾驶员驾照号" align="center" prop="driverLicenseNo"/>
        <el-table-column label="所在公司" align="center" prop="deptName"/>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="editDriver(scope.row)">确认替换
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--<pagination
          style="height: 30px"
          v-show="total3>0"
          :total="total3"
          :page.sync="queryParams3.pageNum"
          :limit.sync="queryParams3.pageSize"
          @pagination="getCanUseDriver"
      />-->

    </el-dialog>
    <el-dialog title="添加车辆任务" :visible.sync="addTaskVisible" width="80%">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>

          <el-col :span="12">
            <el-form-item label="车辆id" prop="vehicleInfoId">
              <el-input v-model="form.vehicleInfoId" :readonly="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车牌号" prop="vehiclePlateNo">
              <el-input v-model="form.vehiclePlateNo" :readonly="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务说明" prop="lockCarRemark">
              <el-select v-model="form.lockCarRemark" placeholder="请选择" clearable size="small">
                <el-option label="维修" value="维修"/>
                <el-option label="停运" value="停运"/>
                <el-option label="占用" value="占用"/>
                <el-option label="其他" value="其他"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="任务开始时间" prop="taskStartTime">
              <el-date-picker
                value-format="yyyy-MM-dd HH:mm:ss"
                v-model="form.taskStartTime"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务结束时间" prop="taskEndTime">
              <el-date-picker
                value-format="yyyy-MM-dd HH:mm:ss"
                v-model="form.taskEndTime"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


    <div id="menu">
      <div class="menu" v-for="(item,index) in menus" :key="index" @click.stop="infoClick(index)">{{item}}</div>
    </div>
  </div>
</template>

<script>
  import {
    addVehicleTaskStatus,
    carList,
    delVehicleTaskStatus,
    driverList,
    exportVehicleTaskStatus,
    getVehicleTaskStatus,
    listVehicleTaskStatus,
    updateVehicleTaskStatus
  } from "@/api/oper/vehicleTaskStatus";
  import {formatTime} from "@/utils/dateUtil";
  import {listCanUseDriver} from "@/api/oper/driver";
  import {listCanUseVehicle} from "@/api/oper/vehicle";

  export default {
    data() {
      return {
        attributeName: '',
        form: {},
        vehicleTaskStatusDetail: [
          {vehiclePlateNo: '',}
        ],
        queryParams: {
          vehiclePlateNo: ''
        },
        queryParams1: {
          driverName: ''
        },
        queryParams2: {},
        queryParams3: {},
        // 总条数
        total: 0,
        // 总条数
        total1: 0,
        // 总条数
        total2: 0,
        // 总条数
        total3: 0,
        startAndEndTime: [],
        startAndEndTime1: [],
        menus: ['新增', '修改', '详情', '删除'],
        dialogTableVisible: false,
        editDriverVisible: false,
        editCarVisible: false,
        addTaskVisible: false,
        vehicleStatusDetailId: '',
        tableData: [],
        driverTableData: [],
        //表头
        columnItems: [],
        //驾驶员表头
        driverColumnItems: [],
        activeName: 'first',
        formLabelWidth: '120px',
        canUseDriverList: [],
        canUseCarList: [],
        editTaskId: '',
        passengerNum: '',
        taskStartTime: '',
        taskEndTime: '',
        // 表单校验
        rules: {
          vehicleInfoId: [
            {required: true, message: "不能为空", trigger: "blur"},
          ],
          vehiclePlateNo: [
            {required: true, message: "不能为空", trigger: "blur"},
          ],
          lockCarRemark: [
            {required: true, message: "不能为空", trigger: "blur"},
          ],
          taskStartTime: [
            {required: true, message: "不能为空", trigger: "blur"},
          ],
          taskEndTime: [
            {required: true, message: "不能为空", trigger: "blur"},
          ],
        }
      }
    }, created() {
      this.startAndEndTime[0] = formatTime(new Date().getTime(), 'Y-M-D');
      this.startAndEndTime[1] = formatTime(new Date().getTime() + 3600 * 1000 * 24 * 6, 'Y-M-D');
      this.startAndEndTime1 = this.startAndEndTime
      //拼接表头prop，和表头label
      this.driverColumnItems.push({label: "驾驶员", prop: "driverName"})
      this.columnItems.push({label: "车牌号", prop: "vehiclePlateNo"})
      for (let i = 0; i <= 6; i++) {
        this.columnItems.push({
          label: formatTime(new Date().getTime() + 3600 * 1000 * 24 * i, 'Y-M-D'),
          prop: formatTime(new Date().getTime() + 3600 * 1000 * 24 * i, 'Y-M-D')
        })
        this.driverColumnItems.push({
          label: formatTime(new Date().getTime() + 3600 * 1000 * 24 * i, 'Y-M-D'),
          prop: formatTime(new Date().getTime() + 3600 * 1000 * 24 * i, 'Y-M-D')
        })
      }
      this.getList();
      this.getDriverList();
    },
    methods: {
      /** 提交按钮 */
      submitForm: function () {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.vehicleTaskStatuId != undefined) {
              updateVehicleTaskStatus(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.addTaskVisible = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {
              addVehicleTaskStatus(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.addTaskVisible = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              });
            }
          }
        });
      },
      // 取消按钮
      cancel() {
        this.addTaskVisible = false;
      },
      setTaskStatusClass(val) {
        return 'taskStatus' + val;
      },
      // 是否
      belongFormat(row, column) {
        return row.belong == 1 ? '是' : '否'
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      resetQuery1() {
        this.resetForm("queryForm1");
        this.handleQuery1();
      },
      resetQuery2() {
        this.resetForm("queryForm2");
        this.handleQuery2();
      },
      resetQuery3() {
        this.resetForm("queryForm3");
        this.handleQuery3();
      },
      /** 搜索按钮操作 */
      handleQuery() {
        //拼接表头prop，和表头label
        this.columnItems = []
        this.columnItems.push({label: "车牌号", prop: "vehiclePlateNo"})
        for (let i = 0; ; i++) {
          if (new Date(this.startAndEndTime[0]).getTime() + 3600 * 1000 * 24 * i > new Date(this.startAndEndTime[1] + ' 23:59:59').getTime()) {
            break
          }
          this.columnItems.push({
            label: formatTime(new Date(this.startAndEndTime[0]).getTime() + 3600 * 1000 * 24 * i, 'Y-M-D'),
            prop: formatTime(new Date(this.startAndEndTime[0]).getTime() + 3600 * 1000 * 24 * i, 'Y-M-D')
          })
        }
        this.getList();
      },
      /** 搜索按钮操作 */
      handleQuery1() {
        //拼接表头prop，和表头label
        this.driverColumnItems = []
        this.driverColumnItems.push({label: "驾驶员", prop: "driverName"})
        for (let i = 0; ; i++) {
          if (new Date(this.startAndEndTime1[0]).getTime() + 3600 * 1000 * 24 * i > new Date(this.startAndEndTime1[1] + ' 23:59:59').getTime()) {
            break
          }
          this.driverColumnItems.push({
            label: formatTime(new Date(this.startAndEndTime1[0]).getTime() + 3600 * 1000 * 24 * i, 'Y-M-D'),
            prop: formatTime(new Date(this.startAndEndTime1[0]).getTime() + 3600 * 1000 * 24 * i, 'Y-M-D')
          })
        }
        this.getDriverList()
      },

      handleQuery2() {
        this.queryParams2.pageNum = 1;
        this.getCanUseCar()
      },
      handleQuery3() {
        this.queryParams3.pageNum = 1;
        this.getCanUseDriver()
      },

      //驾驶员排班列表
      getDriverList() {
        this.loading = true;
        this.queryParams1.taskStartTime = this.startAndEndTime1[0];
        this.queryParams1.taskEndTime = this.startAndEndTime1[1] + ' ' + '23:59:59';
        driverList(this.queryParams1).then(response => {
          this.driverTableData = response.data
          this.loading = false;
        })
      },
      getList() {
        this.loading = true;
        this.queryParams.taskStartTime = this.startAndEndTime[0];
        this.queryParams.taskEndTime = this.startAndEndTime[1] + ' ' + '23:59:59';
        carList(this.queryParams).then(response => {
          this.tableData = response.data
          this.loading = false;
        })
      },
      handleEdit(index, row) {
        console.log(index, row);
      },
      handleEditCar(row) {
        this.editCarVisible = true
        this.editTaskId = row.vehicleTaskStatusId
        this.passengerNum = row.passengerSeatNum - row.remainingSeatNum
        this.taskStartTime = row.taskStartTime
        this.taskEndTime = row.taskEndTime
        this.getCanUseCar(row)
      },
      handleEditDriver(row) {
        this.editDriverVisible = true
        this.editTaskId = row.vehicleTaskStatusId
        this.passengerNum = row.passengerSeatNum - row.remainingSeatNum
        this.taskStartTime = row.taskStartTime
        this.taskEndTime = row.taskEndTime
        this.getCanUseDriver()
      },
      deleteCarTask(row) {
        this.$confirm('是否确认删除?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delVehicleTaskStatus(row.vehicleTaskStatusId);
        }).then(() => {
          this.getList();
          this.dialogTableVisible = false;
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
      handleDelete(index, row) {
        console.log(index, row);
      },
      getInfo(data, data1) {
        console.log(data, data1);
      },
      handleClick(tab, event) {
        console.log(tab, event);
      },
      getVehicleTaskStatusDetail(msg) {
        console.log(msg)
        this.vehicleTaskStatusDetail = msg
        this.dialogTableVisible = true
      },
      addVehicleTaskStatusDetail(msg) {
        console.log(1, msg)
        if (msg.vehicleInfoId) {
          this.form.vehicleInfoId = msg.vehicleInfoId;
        }
        if (msg.vehicleId) {
          this.form.vehicleInfoId = msg.vehicleId;
        }
        this.form.vehiclePlateNo = msg.vehiclePlateNo;
        this.form.lockCar = 1
        this.addTaskVisible = true
      },
      // table的右键点击当前行事件
      rightClick(row, column, cell, event) {

        console.log(row, column, cell)
        /*var menu = document.querySelector("#menu");
        event.preventDefault();
        //获取我们自定义的右键菜单


        // 根据事件对象中鼠标点击的位置，进行定位
        menu.style.left = event.clientX + 'px';
        menu.style.top = event.clientY + 'px';
        // 改变自定义菜单的隐藏与显示
        menu.style.display = 'block';
        console.log(row,column);
        // 获取当前右键点击table下标
        this.tableData.forEach((item,index) => {
          if(item.name === row.name) {
            this.currentRowIndex = index;
            return false;
          }
        })*/
      }, // 自定义菜单的点击事件
      infoClick(index) {
        this.$alert('当前table的下标为' + this.currentRowIndex, '你点击了自定义菜单的' + this.menus[index] + '功能', {
          confirmButtonText: '确定',
          callback: action => {
            var menu = document.querySelector("#menu");
            menu.style.display = 'none';
          }
        });
      },
      // table的左键点击当前行事件
      clickTableRow(row, column, event) {
        var menu = document.querySelector("#menu");
        menu.style.display = 'none';
        // console.log(row,column,event)
        this.tableData.forEach((item, index) => {
          if (row.name === item.name) {
            this.radio = index;
          }
        })
      },

      taskTypeFormatter(row, column) {
        if (row.lockCar == 0) {
          if (row.taskType == 3 || row.taskType == 6) {
            return "拼座"
          } else if (row.taskType == 4) {
            return "拼车"
          } else if (row.taskType == 5 || row.taskType == 7 || row.taskType == 8) {
            return "包车"
          }
        } else {
          return row.lockCarRemark
        }

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
        } else {
          return ""
        }
      },
      getCanUseCar() {
        //班次已有乘客数
        this.queryParams2.passengerNum = this.passengerNum
        this.queryParams2.useCarStartTime = this.taskStartTime
        this.queryParams2.useCarEndTime = this.taskEndTime
        listCanUseVehicle(this.queryParams2).then(response => {
          this.total2 = response.total
          this.canUseCarList = response.data
        })
      },
      getCanUseDriver() {
        this.queryParams3.taskStartTime = this.taskStartTime
        this.queryParams3.taskEndTime = this.taskEndTime
        listCanUseDriver(this.queryParams3).then(response => {
          this.total3 = response.total
          this.canUseDriverList = response.data
        })
      },
      editCar(row) {
        //最低拼团人数
        this.form.ninPinNum = row.vehicleType.pincheMin
        //车辆最大可载乘客数
        this.form.passengerSeatNum = row.vehicleType.passengerNum
        //剩余座位数
        this.form.remainingSeatNum = row.vehicleType.passengerNum - this.passengerNum
        this.form.vehicleTaskStatusId = this.editTaskId
        this.form.vehicleInfoId = row.vehicleId
        this.form.vehiclePlateNo = row.licenseTagno
        this.$confirm('是否确认更换车辆车牌号为"' + row.licenseTagno + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          updateVehicleTaskStatus(this.form)
        }).then(() => {
          this.dialogTableVisible = false
          this.editDriverVisible = false
          this.editCarVisible = false
          this.msgSuccess("更换成功")
        }).then(() => {
          setTimeout(() => {
            this.getList()
          }, 500)
        }).catch(function () {
        });
      },
      editDriver(row) {
        this.form.vehicleTaskStatusId = this.editTaskId
        this.form.driverId = row.driverId
        this.form.driverName = row.name
        this.form.driverPhone = row.phone
        this.form.taskStatus = 1
        this.$confirm('是否确认更换驾驶员为"' + row.name + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          updateVehicleTaskStatus(this.form)
        }).then(() => {
          this.dialogTableVisible = false
          this.editDriverVisible = false
          this.editCarVisible = false
          this.msgSuccess("更换成功");
        }).then(() => {
          setTimeout(() => {
            this.getDriverList()
          }, 500)
        }).catch(function () {
        });
      }
    }

  }
</script>

<style scoped>
  .taskStatus1 {
    background: red;
    color: white;
    text-align: center;
    padding: 1px;
    border-bottom: solid 1px #909399;
    border-radius: 5px 5px;
  }

  .taskStatus2 {
    background: yellowgreen;
    color: white;
    text-align: center;
    padding: 1px;
    border-bottom: solid 1px #909399;
    border-radius: 5px 5px;
  }

  .taskStatus3 {
    background: rgb(195, 147, 75);
    color: white;
    text-align: center;
    padding: 1px;
    border-bottom: solid 1px #909399;
    border-radius: 5px 5px;
  }

  .taskStatus4 {
    background: #5aa838;
    color: white;
    text-align: center;
    padding: 1px;
    border-bottom: solid 1px #909399;
    border-radius: 5px 5px;
  }

  .taskStatus5 {
    background: gray;
    color: white;
    text-align: center;
    padding: 1px;
    border-bottom: solid 1px #909399;
    border-radius: 5px 5px;
  }

  #menu {
    width: 120px;
    height: 100px;
    overflow: hidden; /*隐藏溢出的元素*/
    box-shadow: 0 1px 1px #888, 1px 0 1px #ccc;
    position: absolute;
    display: none;
    background: #ffffff;
    z-index: 10;
  }

  .menu {
    width: 125px;
    height: 25px;
    line-height: 25px;
    text-indent: 10px;
    cursor: pointer;
  }

  .menu:hover {
    color: deeppink;
    text-decoration: underline;
  }

  .carRunTime {
    background: #42b983;
    color: white;
    width: 100%;
    height: 100%;
    font-size: 20px;
    padding: 20px 0;
  }

  /* .columnItemStyle{
     margin: 10px 0;
     display: inline-block;
   }*/

  .el-row {
    margin-bottom: 20px;
  }

  .ylxq {
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    padding: 20px;
    margin-bottom: 20px;

  }

  .vsTitle {
    font-weight: bold;
    margin-right: 10px;
    font-size: 16px;
  }

  .vsItem {
    font-size: 16px;
  }

</style>
