<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="退单规则名称" prop="refundRuleName">
        <el-input
          v-model="queryParams.refundRuleName"
          placeholder="请输入退单规则名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="退单规则名称" prop="createUserId">
        <el-input
          v-model="queryParams.createUserId"
          placeholder="请输入退单规则名称"
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['oper:refundRule:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['oper:refundRule:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['oper:refundRule:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['oper:refundRule:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="refundRuleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="退单规则名称" align="center" prop="refundRuleId" />
      <el-table-column label="退单规则名称" align="center" prop="refundRuleName" />
      <el-table-column label="退单规则名称" align="center" prop="createUserId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['oper:refundRule:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['oper:refundRule:remove']"
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

    <!-- 添加或修改退单规则对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="180px">
        <el-form-item label="退单规则名称" prop="refundRuleName">
          <el-input v-model="form.refundRuleName" placeholder="请输入退单规则名称" />
        </el-form-item>
        <div style="text-align: right">
          <el-button type="primary" @click.native.prevent="addRefundRuleRow(refundRuleData)" style="margin-bottom: 20px">添加一行</el-button>
        </div>
        <el-table
          :data="refundRuleData"
          border
          style="width: 100%">
          <el-table-column
            prop="startAndEndDate"
            label="退单日期"
            width="400">
            <template slot-scope="scope">
              <el-date-picker
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                v-model="scope.row.startAndEndDate"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期(不含)"
                end-placeholder="结束日期(包含)">
              </el-date-picker>
            </template>
          </el-table-column>
          <el-table-column
            prop="refundCharge"
            label="退单手续费(%)"
            width="250">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.refundCharge" :precision="2" :step="5" :max="100"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作">
            <template slot-scope="scope">
              <el-button
                @click.native.prevent="deleteRow(scope.$index, refundRuleData)"
                type="text"
                size="small">
                移除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRefundRule, getRefundRule, delRefundRule, addRefundRule, updateRefundRule, exportRefundRule } from "@/api/oper/refundRule";

export default {
  name: "RefundRule",
  data() {
    return {
      refundRuleData:[],
      startAndEndDate:'',
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
      // 退单规则表格数据
      refundRuleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        refundRuleName: undefined,
        createUserId: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    deleteRow(index, rows) {
      rows.splice(index, 1);
    },
    addRefundRuleRow(rows){
      let item = {
        startAndEndDate: ['2020-06-06','2020-06-08'],
        refundCharge: "10"}
      rows.splice(rows.length, 0,item);
    },
    /** 查询退单规则列表 */
    getList() {
      this.loading = true;
      listRefundRule(this.queryParams).then(response => {
        this.refundRuleList = response.rows;
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
        refundRuleId: undefined,
        refundRuleName: undefined,
        createTime: undefined,
        createUserId: undefined
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
      this.ids = selection.map(item => item.refundRuleId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加退单规则";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const refundRuleId = row.refundRuleId || this.ids
      getRefundRule(refundRuleId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改退单规则";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      console.log(this.refundRuleData,this.form.refundRuleName)
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.refundRuleId != undefined) {
            updateRefundRule(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addRefundRule(this.form).then(response => {
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
      const refundRuleIds = row.refundRuleId || this.ids;
      this.$confirm('是否确认删除退单规则编号为"' + refundRuleIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delRefundRule(refundRuleIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有退单规则数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportRefundRule(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  },

};
</script>
