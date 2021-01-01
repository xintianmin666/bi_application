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
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
          <el-form-item label="用户类型" prop="userType">
            <el-select v-model="queryParams.userType" placeholder="请选择用户类型" clearable size="small">
              <el-option label="协议用户" value="1" />
              <el-option label="非协议用户" value="2" />
            </el-select>
          </el-form-item>
          <el-form-item label="收费类型" prop="chargeType">
            <el-select v-model="queryParams.chargeType" placeholder="请选择收费类型" clearable size="small">
              <el-option label="里程收费" value="1" />
              <el-option label="时间收费" value="2" />
            </el-select>
          </el-form-item>
          <!--<el-form-item label="车辆类型" prop="carType">
            <el-select v-model="queryParams.carType" placeholder="请选择车辆类型" clearable size="small">
              <el-option label="请选择字典生成" value="" />
            </el-select>
          </el-form-item>-->
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
              v-hasPermi="['oper:priceRules:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['oper:priceRules:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['oper:priceRules:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['oper:priceRules:export']"
            >导出</el-button>
          </el-col>
        </el-row>

        <el-table v-loading="loading" :data="priceRulesList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="id" align="center" prop="id" />
          <el-table-column label="所属部门" align="center" prop="dept.deptName" />
          <el-table-column label="用户类型" align="center" prop="userType" :formatter="userTypeFormat"/>
          <el-table-column label="收费类型" align="center" prop="chargeType" :formatter="chargeTypeFormat"/>
          <el-table-column label="车辆类型" align="center" prop="vehicleType.typeName" />
          <!--<el-table-column label="标准价(元)" align="center" prop="basePrice" />
          <el-table-column label="标定里程(公里)" align="center" prop="baseDistance" />
          <el-table-column label="每公里费用(元)" align="center" prop="oneKmPrice" />
          <el-table-column label="超出标定里程每公里费用(元)" align="center" prop="outDistancePrice" />-->
          <el-table-column label="计算公式" align="center" prop="formulaName" />
          <!--<el-table-column label="计算公式详情" align="center" prop="priceFormula" />-->
          <el-table-column label="系统参数" align="center" prop="otherPriceRemark" />
          <el-table-column label="用户参数" align="center" prop="userParam" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['oper:priceRules:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['oper:priceRules:remove']"
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


    <!-- 添加或修改计价规则对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="180px">
        <el-form-item label="归属部门" prop="deptId">
          <treeselect v-model="form.deptId" :options="deptOptions" placeholder="请选择归属部门" size="small"/>
        </el-form-item>
        <el-form-item label="用户类型"  prop="userType">
          <el-select v-model="form.userType" placeholder="请选择用户类型">
            <el-option label="非协议用户" value="2"/>
            <!--<el-option label="协议用户" value="1"/>-->
          </el-select>
        </el-form-item>
        <el-form-item label="收费类型" prop="chargeType">
          <el-select v-model="form.chargeType" placeholder="请选择收费类型" >
            <el-option label="里程收费（预约）" value="1"/>
            <el-option label="时间收费（市内）" value="2"/>
          </el-select>
        </el-form-item>
        <el-form-item label="车辆类型" prop="carType">
          <el-select v-model="form.carType" placeholder="请选择车辆类型">
            <el-option
              v-for="dict in carTypeOptions"
              :key="dict.vcehicleTypeId"
              :label="dict.typeName +'_'+dict.passengerNum+'座'+'('+dict.remark+')'"
              :value="dict.vcehicleTypeId+''"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="计价公式" prop="priceFormulaItem">
          <el-select v-model="form.priceFormulaItem" value-key="id" placeholder="请选择" @visible-change="priceFormulaSelect" @change="priceFormulaChange($event)">
            <el-option
              v-for="item in priceFormulaOptions"
              :key="item.id"
              :label="item.name"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="计价公式详情" >
          <el-input type="textarea" :value=priceFormulaDetail :readonly="true"/>
        </el-form-item>
        <el-form-item label="用户参数" >
          <el-input  :value=userParam :readonly="true"/>
        </el-form-item>
        <div  v-for="(itemInfo, index) in adminParamOpPtions">
          <!--<div v-if="form.priceFormulaItem.id==4&&index==0">
            <el-form-item label="标准价(元)" prop="basePrice">
              <el-input type="number" min="0" v-model="form.basePrice" placeholder="请输入标准价" />
            </el-form-item>
            <el-form-item label="标定里程(公里)" prop="baseDistance">
              <el-input type="number" min="0" v-model="form.baseDistance" placeholder="请输入标定里程" />
            </el-form-item>
            <el-form-item label="每公里费用(元)" prop="oneKmPrice">
              <el-input type="number" min="0" v-model="form.oneKmPrice" placeholder="请输入每公里费用" />
            </el-form-item>
            <el-form-item label="超出标定里程每公里费用(元)" prop="outDistancePrice">
              <el-input type="number" min="0" v-model="form.outDistancePrice" placeholder="请输入超出标定里程每公里费用" />
            </el-form-item>
          </div>-->
          <el-form-item :label=itemInfo :prop=itemInfo>
            <el-input v-model="form[itemInfo]" type="number"/>
          </el-form-item>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPriceRules, getPriceRules, delPriceRules, addPriceRules, updatePriceRules, exportPriceRules } from "@/api/oper/priceRules";
import { getCarType } from "@/api/oper/vehicle";
import { listPriceFormula } from "@/api/oper/priceFormula";
import { treeselect} from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
export default {
  name: "PriceRules",
  components: {
    Treeselect
  },
  data() {
    return {
      userParam:'',
      priceFormulaDetail:'',
      itemInfo:'',
      adminParamOpPtions:[],
      defaultProps: {
        children: "children",
        label: "label"
      },
      deptName: undefined,
      deptOptions:[],
      // 计价公式
      priceFormulaOptions: [],
      // 车辆类型
      carTypeOptions: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 计价规则表格数据
      priceRulesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: undefined,
        userType: undefined,
        chargeType: undefined,
        carType: undefined,
        basePrice: undefined,
        baseDistance: undefined,
        oneKmPrice: undefined,
        outDistancePrice: undefined
      },
      // 表单参数
      form: {
      },
      // 表单校验
      rules: {
        deptId: [
          { required: true, message: "归属部门不能为空", trigger: "blur" },
        ],
        userType: [
          { required: true, message: "用户类型不能为空", trigger: "blur" },
        ],
        chargeType: [
          { required: true, message: "收费类型不能为空", trigger: "blur" },
        ],
        carType: [
          { required: true, message: "车辆类型不能为空", trigger: "blur" },
        ],
        basePrice: [
          { required: true, message: "标准价不能为空", trigger: "blur" },
        ],
        baseDistance: [
          { required: true, message: "标定里程不能为空", trigger: "blur" },
        ],
        oneKmPrice: [
          { required: true, message: "每公里费用不能为空", trigger: "blur" },
        ],
        outDistancePrice: [
          { required: true, message: "超出标定里程每公里费用不能为空", trigger: "blur" },
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getCarType();
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    },
  },
  methods: {
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    priceFormulaSelect(val){
      if (val){
        let queryParams={
          userType: this.form.userType,
          chargeType: this.form.chargeType}
        this.getPriceFormula(queryParams)
      }
    },
    priceFormulaChange(val){
      this.priceFormulaDetail = val.priceFormula
      this.userParam = val.userParam
      this.adminParamOpPtions = val.adminParam.split(",")
    },
    getPriceFormula(query){
      listPriceFormula(query).then(response => {
        this.priceFormulaOptions = response.rows;
      });
    },
    getCarType() {
      getCarType().then(response => {
        console.log('车辆信息:',response.data)
        this.carTypeOptions = response.data;
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    /** 查询计价规则列表 */
    getList() {
      this.loading = true;
      listPriceRules(this.queryParams).then(response => {
        this.priceRulesList = response.rows;
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
        id: undefined,
        deptId: undefined,
        userType: undefined,
        chargeType: undefined,
        carType: undefined,
        basePrice: undefined,
        baseDistance: undefined,
        oneKmPrice: undefined,
        outDistancePrice: undefined
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.form = {
        userType: '2',
        chargeType: '1',
      };
      this.adminParamOpPtions = []
      this.priceFormulaDetail=''
      this.userParam=''
      this.open = true;
      this.title = "添加计价规则";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      this.adminParamOpPtions = []
      this.priceFormulaDetail=''
      this.userParam=''
      getPriceRules(id).then(response => {
        this.form = response.data;
        /*if (this.form.otherPriceRemark) {
          let keyValue = this.form.otherPriceRemark.split(",")
          for (let i=0;i<keyValue.length;i++){
            this.adminParamOpPtions.push(keyValue[i].split(":")[0])
            this.form[keyValue[i].split(":")[0]]=keyValue[i].split(":")[1]
          }
        }*/
        this.open = true;
        this.title = "修改计价规则";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.otherPriceRemark = '';
          if (!this.form.priceFormulaItem) {
            this.msgError("计价公式不可为空");
            return
          }
          this.form.otherPrice = this.form.priceFormulaItem.id;
          let keyArray = this.form.priceFormulaItem.adminParam.split(",");
          for(let i=0;i<keyArray.length;i++){
            if (!this.form[keyArray[i]]) {
              this.msgError(keyArray[i]+"不可为空");
              return
            }
            this.form.otherPriceRemark += keyArray[i]+':'+this.form[keyArray[i]]+','
          }
          this.form.otherPriceRemark = this.form.otherPriceRemark.substring(0,this.form.otherPriceRemark.length-1)
          if (this.form.id != undefined) {
            updatePriceRules(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addPriceRules(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除计价规则编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delPriceRules(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有计价规则数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportPriceRules(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    userTypeFormat(row, column){
      return row.userType == '1' ? '协议用户' : '非协议用户'
    },
    chargeTypeFormat(row, column){
      return row.chargeType == '1' ? '里程收费（预约）' : '时间收费（市内）'
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.getList();
    },
  },
};
</script>
