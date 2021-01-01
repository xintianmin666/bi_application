<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px">
      <el-form-item label="驾驶员姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入驾驶员姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="驾驶员电话" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入驾驶员电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="驾驶员驾照号" prop="driverLicenseNo">
        <el-input
          v-model="queryParams.driverLicenseNo"
          placeholder="请输入驾驶员驾照号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="驾驶员状态 " prop="state">
        <el-select v-model="queryParams.state" placeholder="请选择" clearable size="small">
          <el-option label="可用" value="1" />
          <el-option label="不可用" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态" prop="checkStatus">
        <el-select v-model="queryParams.checkStatus" placeholder="请选择" clearable size="small">
          <el-option label="已审核" value="1" />
          <el-option label="未审核" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['oper:driver:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['oper:driver:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['oper:driver:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['oper:driver:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="driverList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="驾驶员ID" align="center" prop="driverId" />
      <el-table-column label="驾驶员姓名" align="center" prop="name" />
      <el-table-column label="驾驶员电话" align="center" prop="phone" />
      <el-table-column label="驾驶员驾照号" align="center" prop="driverLicenseNo" />
      <el-table-column label="驾驶员状态 " align="center" prop="state" :formatter="stateFormat"/>
      <el-table-column label="所在公司" align="center" prop="deptName" />
      <el-table-column label="审核状态" align="center" prop="checkStatus" :formatter="checkStatusFormat"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['oper:driver:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="checkOkHandleUpdate(scope.row)"
            v-hasPermi="['oper:driver:edit']"
          >审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['oper:driver:remove']"
          >删除</el-button>
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

    <!-- 添加或修改驾驶员信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="选择库中驾驶员"  >
          <el-select v-model="erpDriver" @change="selectErpDriver"
                     filterable value-key="fDriverid" :clearable="true"
            remote
            reserve-keyword
            placeholder="请输入关键词"
            :remote-method="remoteMethod"
            :loading="loading1">
            <el-option
              v-for="item in erpDriverOptions"
              :key="item.fDriverid"
              :label="item.fDrivername"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="驾驶员姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入驾驶员姓名" />
        </el-form-item>
        <el-form-item label="驾驶员电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入驾驶员电话" />
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="驾驶员状态 " prop="state">
            <el-select v-model="form.state" placeholder="请选择" >
              <el-option value="1" label="可用" >可用</el-option>
              <el-option value="0" label="不可用">不可用</el-option>
            </el-select>
          </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="截止此时间后可用 " prop="disableEndTime" label-width="150px" v-if="form.state==0?true:false">
              <el-date-picker
                value-format="yyyy-MM-dd HH:mm:ss"
                v-model="form.disableEndTime"
                type="datetime"
                placeholder="不选默认永久不可用" >
              </el-date-picker>
          </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="所在公司" prop="deptId">
          <treeselect v-model="form.deptId" placeholder="请输入所在公司" :options="deptOptions" size="small" />
        </el-form-item>
        <el-form-item label="从业资格证号" prop="jobLicenseNo">
          <el-input v-model="form.jobLicenseNo" placeholder="请输入从业资格证号" />
        </el-form-item>
        <el-form-item label="内部人员" prop="belong">
          <el-select v-model="form.belong" placeholder="请选择" >
            <el-option value="1" label="内部" >内部</el-option>
            <el-option value="0" label="外部">外部</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="驾驶证号" prop="driverLicenseNo">
          <el-input v-model="form.driverLicenseNo" placeholder="请输入驾驶证号" />
        </el-form-item>
        <el-form-item label="审核状态" prop="checkStatus">
          <el-select v-model="form.checkStatus" placeholder="请选择" >
            <el-option value="1" label="已审核" >已审核</el-option>
            <el-option value="0" label="未审核">未审核</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="库中驾驶员id" prop="erpDriverId">
          <el-input v-model="form.erpDriverId" :readonly="true"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 审核驾驶员信息对话框 -->
    <el-dialog :title="title" :visible.sync="open1" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="驾驶员姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入驾驶员姓名" :disabled="true"/>
        </el-form-item>
        <el-form-item label="驾驶员电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入驾驶员电话"  :disabled="true" />
        </el-form-item>
        <el-form-item label="从业资格证号" prop="jobLicenseNo">
          <el-input v-model="form.jobLicenseNo" placeholder="请输入从业资格证号" :disabled="true"/>
        </el-form-item>
        <el-form-item label="从业资格证图片" prop="jobLicenseUrl">
          <el-image
            style="width: 100px; height: 100px"
            :src="form.jobLicenseUrl"
            :preview-src-list="jobLicenseUrlList">
          </el-image>
        </el-form-item>
        <!--<el-form-item label="驾驶证年审期限" prop="driverLicenseTerm">
          <el-input v-model="form.driverLicenseTerm" placeholder="请输入驾驶证年审期限" />
        </el-form-item>
        <el-form-item label="从业资格证年限" prop="jobLicenseTerm">
          <el-input v-model="form.jobLicenseTerm" placeholder="请输入从业资格证年限" />
        </el-form-item>-->
        <el-form-item label="内部人员" prop="belong">
          <el-input v-model="form.belong==1?'内部':'外部'" :disabled="true"/>
        </el-form-item>
        <!-- <el-form-item label="登录密码" prop="password">
           <el-input v-model="form.password" placeholder="请输入登录密码" />
         </el-form-item>
         <el-form-item label="车辆Id" prop="vehicleId">
           <el-input v-model="form.vehicleId" placeholder="请输入车辆Id" />
         </el-form-item>-->

        <el-form-item label="驾驶证号" prop="driverLicenseNo">
          <el-input v-model="form.driverLicenseNo" placeholder="请输入驾驶证号" :disabled="true" />
        </el-form-item>
        <el-form-item label="驾驶证图片" prop="driverLicenseUrl">
          <el-image
            style="width: 100px; height: 100px"
            :src="form.driverLicenseUrl"
            :preview-src-list="driverLicenseUrlList">
          </el-image>
        </el-form-item>
        <el-form-item label="身份证正面" prop="idCardFront">
          <el-image
            style="width: 100px; height: 100px"
            :src="form.idCardFront"
            :preview-src-list="idCardFrontList">
          </el-image>
        </el-form-item>
        <el-form-item label="身份证背面" prop="idCardBack">
          <el-image
            style="width: 100px; height: 100px"
            :src="form.idCardBack"
            :preview-src-list="idCardBackList">
          </el-image>
        </el-form-item>
        <el-form-item label="车牌号" prop="licenseTagno" >
          <el-input v-model="form.licenseTagno" placeholder="请输入车牌号" :disabled="true" />
        </el-form-item>
        <el-form-item label="车辆营运证号" prop="taxiLicenseNo">
          <el-input v-model="form.taxiLicenseNo" placeholder="请输入车辆营运证号" :disabled="true" />
        </el-form-item>
        <el-form-item label="营运证图片" prop="taxiLicenseUrl">
          <el-image
            style="width: 100px; height: 100px"
            :src="form.taxiLicenseUrl"
            :preview-src-list="taxiLicenseUrlList">
          </el-image>
        </el-form-item>

        <el-form-item label="审核状态" prop="checkStatus">
          <el-input v-model="form.checkStatus==1?'已审核':'未审核'" :disabled="true"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="checkOkSubmitForm">审核通过</el-button>
        <el-button  @click="checkNoSubmitForm">不通过</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listDriver, getDriver, delDriver, addDriver, updateDriver, exportDriver } from "@/api/oper/driver";
  import { treeselect } from "@/api/system/dept";
  import {listErpDriver} from "@/api/oper/erpDriver";
  import Treeselect from "@riophae/vue-treeselect";
  export default {
    name: "Driver",
    components: { Treeselect },
    data() {
      return {
        erpDriver:{},
        erpDriverOptions:[],
        taxiLicenseUrlList: [
        ],
        jobLicenseUrlList: [
        ],
        driverLicenseUrlList: [
        ],
        idCardFrontList: [
        ],
        idCardBackList: [
        ],
        deptOptions:[],
        // 遮罩层
        loading: true,
        loading1: false,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 总条数
        total: 0,
        // 驾驶员信息表格数据
        driverList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        open1:false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          name: undefined,
          phone: undefined,
          driverLicenseNo: undefined,
          state: undefined,
          createUserId: undefined,
          modifyTime: undefined,
          modifyUserId: undefined,
          deptId: undefined,
          jobLicenseNo: undefined,
          driverLicenseTerm: undefined,
          jobLicenseTerm: undefined,
          belong: undefined,
          password: undefined,
          vehicleId: undefined,
          driverLicenseUrl: undefined,
          jobLicenseUrl: undefined,
          idCardFront: undefined,
          idCardBack: undefined,
          checkStatus: undefined
        },
        // 表单参数
        form: {
          vehicleInfo:{
          },
          state:1,
        },
        // 表单校验
        rules: {
          name: [
            { required: true, message: "驾驶员名称不能为空", trigger: "blur" }
          ],
          phone: [
            { required: true, message: "驾驶员电话不能为空", trigger: "blur" }
          ],
          driverLicenseNo: [
            { required: true, message: "驾驶员驾照号不能为空", trigger: "blur" }
          ],
          state: [
            { required: true, message: "驾驶员状态 不能为空", trigger: "blur" }
          ],
          deptId:[
            { required: true, message: "驾驶员所在公司 不能为空", trigger: "blur" }
          ]
        }
      };
    },
    created() {
      this.getList();
      this.getTreeselect();
    },
    methods: {
      remoteMethod(query) {
        if (query !== '') {
          this.loading1 = true;
          setTimeout(() => {
            this.loading1 = false;
            this.getErpDriverList(query);
          }, 200);
        } else {
          this.erpDriverOptions = [];
        }
      },
      selectErpDriver(val){
        this.form.name = this.erpDriver.fDrivername
        this.form.phone = this.erpDriver.fPhonecode
        if (this.erpDriver.fDriverstate==1) {
          this.form.state = '1'
          this.form.checkStatus = '1'
        }else{
          this.form.state = '0'
        }
        this.form.deptId = this.erpDriver.fUnitid
        this.form.driverLicenseNo = this.erpDriver.fIdcard
        this.form.erpDriverId = this.erpDriver.fDriverid
      },
      getErpDriverList(driverName){
        this.queryParams.pageSize = 100000;
        this.queryParams.fDrivername = driverName;
        listErpDriver(this.queryParams).then(response =>{
            this.erpDriverOptions = response.rows
        });
        this.queryParams.pageSize = 10;
      },
      /** 查询部门下拉树结构 */
      getTreeselect() {
        treeselect().then(response => {
          this.deptOptions = response.data;
        });
      },
      /** 查询驾驶员信息列表 */
      getList() {
        this.loading = true;
        listDriver(this.queryParams).then(response => {
          this.driverList = response.rows;
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
          driverId: undefined,
          name: undefined,
          phone: undefined,
          driverLicenseNo: undefined,
          state: undefined,
          remark: undefined,
          createTime: undefined,
          createUserId: undefined,
          modifyTime: undefined,
          modifyUserId: undefined,
          deptId: undefined,
          jobLicenseNo: undefined,
          driverLicenseTerm: undefined,
          jobLicenseTerm: undefined,
          belong: undefined,
          password: undefined,
          vehicleId: undefined,
          driverLicenseUrl: undefined,
          jobLicenseUrl: undefined,
          idCardFront: undefined,
          idCardBack: undefined,
          checkStatus: undefined,
          licenseTagno:undefined,
          taxiLicenseNo:undefined,
          taxiLicenseUrl:undefined
        };
        this.resetForm("form");
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
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.driverId)
        this.single = selection.length!=1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加驾驶员信息";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const driverId = row.driverId || this.ids
        getDriver(driverId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改驾驶员信息";
        });
      },
      /** 审核按钮操作 */
      checkOkHandleUpdate(row) {
        this.reset();
        const driverId = row.driverId || this.ids
        getDriver(driverId).then(response => {
          this.form = response.data;
          if(this.form.vehicleInfo){
            this.form.licenseTagno = this.form.vehicleInfo.licenseTagno
            this.form.taxiLicenseNo = this.form.vehicleInfo.taxiLicenseNo
            this.form.taxiLicenseUrl = this.form.vehicleInfo.taxiLicenseUrl
            this.taxiLicenseUrlList.push(this.form.vehicleInfo.taxiLicenseUrl)
          }
          this.jobLicenseUrlList = []
          this.jobLicenseUrlList.push(this.form.jobLicenseUrl)
          this.driverLicenseUrlList = []
          this.driverLicenseUrlList.push(this.form.driverLicenseUrl)
          this.idCardFrontList = []
          this.idCardFrontList.push(this.form.idCardFront)
          this.idCardBackList = []
          this.idCardBackList.push(this.form.idCardBack)
          this.open1 = true;
          this.title = "审核驾驶员信息";
        });
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.driverId != undefined) {
              updateDriver(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {
              addDriver(this.form).then(response => {
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
      //审核通过
      checkOkSubmitForm:function(){
        if (this.form.driverId != undefined) {
          this.form.checkStatus = "1"
          this.form.state = "1"
          updateDriver(this.form).then(response => {
            if (response.code === 200) {
              this.msgSuccess("审核成功");
              this.open1 = false;
              this.getList();
            } else {
              this.msgError(response.msg);
            }
          });
        }
      },
      //审核不通过
      checkNoSubmitForm(){
        if (this.form.driverId != undefined) {
          this.form.checkStatus = "0"
          this.form.state = "0"
          updateDriver(this.form).then(response => {
            if (response.code === 200) {
              this.msgSuccess("不通过");
              this.open1 = false;
              this.getList();
            } else {
              this.msgError(response.msg);
            }
          });
        }
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const driverIds = row.driverId || this.ids;
        this.$confirm('是否确认删除驾驶员信息编号为"' + driverIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDriver(driverIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有驾驶员信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportDriver(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      },
      stateFormat(row, column) {
        return row.state == 1 ? '可用' : '不可用'
      },
      checkStatusFormat(row, column) {
        return row.checkStatus == 1 ? '已审核' : '未审核'
      },
    }
  };
</script>
