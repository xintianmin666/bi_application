<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
            clearable
          />
        </div>
      </el-col>
      <!--车辆数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px">
          <el-form-item label="最大承载人数" prop="carryMax">
            <el-input
              v-model="queryParams.carryMax"
              placeholder="请输入最大承载人数"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="车牌号" prop="licenseTagno">
            <el-input
              v-model="queryParams.licenseTagno"
              placeholder="请输入车牌号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="车辆状态" prop="vehicleState">
            <el-select v-model="queryParams.vehicleState" placeholder="请选择车辆状态" clearable size="small">
              <el-option
                v-for="dict in vehicleStateOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="品牌" prop="productionCompany">
            <el-input
              v-model="queryParams.productionCompany"
              placeholder="请输入品牌"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
           <!-- <el-select v-model="queryParams.productionCompany"
                       placeholder="请选择品牌"
                       clearable
                       size="small"
            >
              <el-option
                v-for="dict in productionCompanyOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>-->
          </el-form-item>
          <el-form-item label="车辆用途" prop="purpose">
            <el-select v-model="queryParams.purpose" placeholder="请选择车辆用途" clearable size="small">
              <el-option
                v-for="dict in carUseTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="车辆性质" prop="type">
            <el-select v-model="queryParams.type" placeholder="请选择车辆性质" clearable size="small">
              <el-option
                v-for="dict in carTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否为内部车辆" prop="belong">
            <el-select v-model="queryParams.belong" placeholder="请选择" clearable size="small">
              <el-option label="是" value="1" />
              <el-option label=否" value="0" />
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
              v-hasPermi="['oper:vehicle:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['oper:vehicle:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['oper:vehicle:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['oper:vehicle:export']"
            >导出</el-button>
          </el-col>
        </el-row>

        <el-table v-loading="loading" :data="vehicleList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="主键" align="center" prop="vehicleId"/>
          <el-table-column label="部门" align="center" prop="dept.deptName" />
          <el-table-column label="最大承载人数" align="center" prop="vehicleType.capacityMax" />
          <el-table-column label="车牌号" align="center" prop="licenseTagno" />
          <el-table-column label="车辆状态" align="center" prop="vehicleState" :formatter="vehicleStateFormat"/>
          <el-table-column label="品牌" align="center" prop="productionCompany"  />
          <el-table-column label="车辆用途" align="center" prop="purpose" :formatter="carUseTypeFormat"/>
          <!--<el-table-column label="车辆规格" align="center" prop="specifications" />
          <el-table-column label="配套设备" align="center" prop="supportEquipment" />-->
          <el-table-column label="是否为内部车辆" align="center" prop="belong"  :formatter="belongFormat"/>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['oper:vehicle:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['oper:vehicle:remove']"
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
      </el-col>

    </el-row>

    <!-- 添加或修改车辆信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="660px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-form-item label="选择库中车辆"  >
            <el-select v-model="erpCar" @change="selectErpCar"
                       filterable value-key="fId" :clearable="true"
                       remote
                       reserve-keyword
                       placeholder="请输入关键词"
                       :remote-method="remoteMethod"
                       :loading="loading1">
              <el-option
                v-for="item in erpCarOptions"
                :key="item.fId"
                :label="item.fPlateno"
                :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-col :span="12">
            <el-form-item label="最大承载人数" prop="carryMax">
              <el-input v-model="form.carryMax" placeholder="请输入最大承载人数" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="归属部门" prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" placeholder="请选择归属部门" size="small"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否为内部车辆" prop="belong">
              <el-select v-model="form.belong" placeholder="请选择" >
                <el-option value="1" label="是" >是</el-option>
                <el-option value="0" label="否">否</el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车牌号" prop="licenseTagno">
              <el-input v-model="form.licenseTagno" placeholder="请输入车牌号" />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="车辆状态" prop="vehicleState">
              <el-select v-model="form.vehicleState" placeholder="请选择">
                <el-option
                  v-for="dict in vehicleStateOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue+''"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="品牌" prop="productionCompany">
              <el-input v-model="form.productionCompany" placeholder="请输入品牌" />
              <!--<el-select v-model="form.productionCompany" placeholder="请选择">
                <el-option
                  v-for="dict in productionCompanyOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>-->
            </el-form-item>
            </el-col>
          <el-col :span="12">
            <el-form-item label="车辆用途" prop="purpose">
              <el-select v-model="form.purpose" placeholder="请选择车辆用途">
                <el-option
                  v-for="dict in carUseTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车辆类型" prop="type">
              <el-select v-model="form.type" placeholder="请选择车辆类型">
                <el-option
                  v-for="dict in carTypeOptions"
                  :key="dict.vcehicleTypeId"
                  :label="dict.typeName +'_'+dict.passengerNum+'座'+'('+dict.remark+')'"
                  :value="dict.vcehicleTypeId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="默认驾驶员" prop="driverId">
              <el-select v-model="form.driverId" filterable placeholder="请选择默认驾驶员">
                <el-option
                  v-for="dict in driverList"
                  :key="dict.driverId"
                  :label="dict.name +'_'+dict.deptName"
                  :value="dict.driverId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!--<el-col :span="12">
            <el-form-item label="车辆规格" prop="specifications">
              <el-input v-model="form.specifications" placeholder="请输入车辆规格" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="配套设备" prop="supportEquipment">
              <el-input v-model="form.supportEquipment" placeholder="请输入配套设备" />
            </el-form-item>
          </el-col>-->
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listVehicle, getVehicle, delVehicle, addVehicle, updateVehicle, exportVehicle, getCarType } from "@/api/oper/vehicle";
import { treeselect } from "@/api/system/dept";
import {listDriver} from "@/api/oper/driver"
import {listErpCar} from "@/api/oper/erpCar"
import Treeselect from "@riophae/vue-treeselect";
import {isInteger,} from"../../../utils/validate";
export default {
  name: "Vehicle",
  components: { Treeselect },
  data() {
    return {
      erpCar:{},
      erpCarOptions:[],
      loading1:false,
      // 遮罩层
      loading: true,
      // 部门树选项
      deptOptions: undefined,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 车辆信息表格数据
      vehicleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 品牌字典
      productionCompanyOptions: [],
      // 车辆用途
      carUseTypeOptions: [],
      // 车辆类型
      carTypeOptions: [],
      //车辆状态
      vehicleStateOptions:[],
      //默认驾驶员
      driverList:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        carryMax: undefined,
        licenseTagno: undefined,
        vehicleState: undefined,
        productionCompany: undefined,
        purpose: undefined,
        type: undefined,
        deptId: undefined,
        specifications: undefined,
        supportEquipment: undefined,
        belong: undefined
      },
      // 部门名称
      deptName: undefined,
      areaName:undefined,
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },

      // 表单校验
      rules: {
        carryMax: [
          { required: true, message: "最大承载人数不能为空", trigger: "blur" },
          {
            validator:isInteger,
            message: "请输入正确数字",
            trigger: "blur"
          }
        ],
        deptId: [
          { required: true, message: "归属部门不能为空", trigger: "blur" },
        ],
        belong: [
          { required: true, message: "不能为空", trigger: "blur" },
        ],
        licenseTagno: [
          { required: true, message: "车牌号不能为空", trigger: "blur" },
        ],
        vehicleState: [
          { required: true, message: "车辆状态不能为空", trigger: "blur" },
        ],
        /*productionCompany: [
          { required: true, message: "品牌不能为空", trigger: "blur" },
        ],*/
        purpose: [
          { required: true, message: "车辆用途不能为空", trigger: "blur" },
        ],
        type: [
          { required: true, message: "车辆类型不能为空", trigger: "blur" },
        ],
        driverId: [
          { required: true, message: "默认驾驶员不能为空", trigger: "blur" },
        ],
      }
    };
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    },
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getDriverList();
    this.getDicts("car_brand").then(response => {
      this.productionCompanyOptions = response.data;
    });
    this.getDicts("car_use_type").then(response => {
      this.carUseTypeOptions = response.data;
    });
    this.getDicts("vehicle_state").then(response => {
      this.vehicleStateOptions = response.data;
    });
    this.getCarType();
  },
  methods: {
    selectErpCar(val){
      this.form.deptId = this.erpCar.fUnitid
      this.form.licenseTagno = this.erpCar.fPlateno
      this.form.carryMax = this.erpCar.fSeatno
      if (this.erpCar.fState=='0') {
        this.form.vehicleState = '1'
      }else{
        this.form.vehicleState = '2'
      }
      this.form.productionCompany = this.erpCar.fBrandname
      this.form.purpose = '1'
      for(let i=0;i<this.carTypeOptions.length;i++){
        if (this.erpCar.fSeatno==this.carTypeOptions[i].capacityMax) {
          this.form.type = this.carTypeOptions[i].vcehicleTypeId
          break
        }
      }
    },
    remoteMethod(query) {
      if (query !== '') {
        this.loading1 = true;
        setTimeout(() => {
          this.loading1 = false;
          this.getErpCarList(query);
        }, 200);
      } else {
        this.erpCarOptions = [];
      }
    },
    getErpCarList(qurey){
      let queryParams ={}
      queryParams.pageSize = 10000;
      queryParams.fPlateno=qurey;
      listErpCar(queryParams).then(response =>{
        this.erpCarOptions = response.rows
      })
      //this.queryParams.pageSize = 10;
    },
    /** 查询车辆信息列表 */
    getList() {
      this.loading = true;
      listVehicle(this.queryParams).then(response => {
        this.vehicleList = response.rows;
        console.log(this.vehicleList)
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询车辆信息类型列表 */
    getCarType() {
      getCarType().then(response => {
        console.log('车辆信息:',response.data)
        this.carTypeOptions = response.data;
      });
    },
    //获取驾驶员信息
    getDriverList(){
      let param = {'pageNum':'1','pageSize':'10000','state':'1'}
      listDriver(param).then(response => {
        console.log('驾驶员信息:',response.rows)
        this.driverList = response.rows;
      });
    },
    // 品牌字典翻译
    productionCompanyFormat(row, column) {
      return this.selectDictLabel(this.productionCompanyOptions, row.productionCompany);
    },
    carUseTypeFormat(row, column) {
      return this.selectDictLabel(this.carUseTypeOptions, row.purpose);
    },

    // 车辆状态
    vehicleStateFormat(row, column) {
      return this.selectDictLabel(this.vehicleStateOptions, row.vehicleState);
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        vehicleId: undefined,
        carryMax: undefined,
        licenseTagno: undefined,
        vehicleState: undefined,
        remark: undefined,
        createTime: undefined,
        createUserId: undefined,
        modifyTime: undefined,
        modifyUserId: undefined,
        productionCompany: undefined,
        purpose: undefined,
        type: undefined,
        deptId: undefined,
        specifications: undefined,
        supportEquipment: undefined,
        belong: undefined
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
      this.ids = selection.map(item => item.vehicleId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加车辆信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const vehicleId = row.vehicleId || this.ids
      getVehicle(vehicleId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改车辆信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.vehicleId != undefined) {
            updateVehicle(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addVehicle(this.form).then(response => {
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
      const vehicleIds = row.vehicleId || this.ids;
      this.$confirm('是否确认删除车辆信息编号为"' + vehicleIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delVehicle(vehicleIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有车辆信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportVehicle(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },

    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },

    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.getList();
    },

    // 是否
    belongFormat(row, column) {
      return row.belong == 1 ? '是' : '否'
    },
  }
};
</script>
