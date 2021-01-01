<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="计算公式名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入计算公式名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户类型" prop="userType">
        <el-select v-model="queryParams.userType" placeholder="请选择" clearable size="small">
          <el-option label="协议用户" value="1" />
          <el-option label="非协议用户" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="收费类型" prop="chargeType">
        <el-select v-model="queryParams.chargeType" placeholder="请选择" clearable size="small">
          <el-option label="里程收费" value="1" />
          <el-option label="时间收费" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="全包/半包" prop="qBao">
        <el-select v-model="queryParams.qBao" placeholder="请选择" clearable size="small">
          <el-option label="全包价格" value="1" />
          <el-option label="半包价格" value="2" />
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
          v-hasPermi="['oper:priceFormula:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['oper:priceFormula:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['oper:priceFormula:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['oper:priceFormula:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="priceFormulaList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="计算公式名称" align="center" prop="name" />
      <el-table-column label="用户类型" align="center" prop="userType" :formatter="userTypeFormatter"/>
      <el-table-column label="收费类型" align="center" prop="chargeType" :formatter="chargeTypeFormatter" />
      <el-table-column label="计算公式" align="center" prop="priceFormula" width="300px"/>
      <el-table-column label="系统参数" align="center" prop="adminParam" />
      <el-table-column label="用户参数" align="center" prop="userParam" />
      <!--<el-table-column label="1:可用  0：不可用" align="center" prop="isUse" />-->
      <el-table-column label="全包/半包" align="center" prop="qBao" :formatter="qBaoFormatter"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['oper:priceFormula:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['oper:priceFormula:remove']"
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

    <!-- 添加或修改计算公式对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="公式名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入计算公式名称" />
        </el-form-item>
        <el-form-item label="用户类型" prop="userType">
          <el-select v-model="form.userType" placeholder="请选择用户类型">
            <el-option label="协议用户" value="1" />
            <el-option label="非协议用户" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="收费种类" prop="chargeType">
          <el-select v-model="form.chargeType" placeholder="请选择收费种类">
            <el-option label="里程收费" value="1" />
            <el-option label="时间收费" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="计算公式" prop="priceFormula">
          <el-input v-model="form.priceFormula" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="管理员设定参数" prop="adminParam">
          <el-input v-model="form.adminParam" placeholder="请输入管理员设定参数" />
        </el-form-item>
        <el-form-item label="用户参数" prop="userParam">
          <el-input v-model="form.userParam" placeholder="请输入用户参数" />
        </el-form-item>
        <!--<el-form-item label="是否可用">
          <el-select v-model="form.chargeType" >
            <el-option label="可用" value="1" />
            <el-option label="不可用" value="0" />
          </el-select>
        </el-form-item>-->
        <el-form-item label="全包/半包" prop="qBao">
          <el-select v-model="form.qBao" placeholder="请选择">
            <el-option label="全包价格" value="1" />
            <el-option label="半包价格" value="2" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPriceFormula, getPriceFormula, delPriceFormula, addPriceFormula, updatePriceFormula, exportPriceFormula } from "@/api/oper/priceFormula";

export default {
  name: "PriceFormula",
  data() {
    return {
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
      // 计算公式表格数据
      priceFormulaList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        userType: undefined,
        chargeType: undefined,
        priceFormula: undefined,
        adminParam: undefined,
        userParam: undefined,
        isUse: undefined,
        qBao: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "不能为空", trigger: "blur" },
        ],
        userType: [
          { required: true, message: "不能为空", trigger: "blur" },
        ],
        chargeType: [
          { required: true, message: "不能为空", trigger: "blur" },
        ],
        priceFormula: [
          { required: true, message: "不能为空", trigger: "blur" },
        ],
        adminParam: [
          { required: true, message: "不能为空", trigger: "blur" },
        ],
        userParam: [
          { required: true, message: "不能为空", trigger: "blur" },
        ],
        /*qBao: [
          { required: true, message: "不能为空", trigger: "blur" },
        ],*/
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询计算公式列表 */
    getList() {
      this.loading = true;
      listPriceFormula(this.queryParams).then(response => {
        this.priceFormulaList = response.rows;
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
        name: undefined,
        userType: undefined,
        chargeType: undefined,
        priceFormula: undefined,
        adminParam: undefined,
        userParam: undefined,
        isUse: undefined,
        qBao: undefined
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
      this.open = true;
      this.title = "添加计算公式";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPriceFormula(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改计算公式";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updatePriceFormula(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addPriceFormula(this.form).then(response => {
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
      this.$confirm('是否确认删除计算公式编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delPriceFormula(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有计算公式数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportPriceFormula(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },userTypeFormatter(row, column){
      return row.userType == '1'?'协议用户' : '非协议用户'
    },chargeTypeFormatter(row, column){
      return row.chargeType == '1'? '里程收费' : '时间收费'
    },qBaoFormatter(row, column){
      return row.qBao == '1' ? '全包' : '半包'
    }
  }
};
</script>
