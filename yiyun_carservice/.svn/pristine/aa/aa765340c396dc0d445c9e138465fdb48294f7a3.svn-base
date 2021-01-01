<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item>
        <el-form-item label="订单类型" prop="orderType">
          <el-select v-model="queryParams.orderType" placeholder="">
            <el-option label="拼座订单" :value="1" >拼座订单</el-option>
            <el-option label="包车订单" :value="2" >包车订单</el-option>
          </el-select>
        </el-form-item>
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
          v-hasPermi="['oper:orderRefundRules:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['oper:orderRefundRules:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['oper:orderRefundRules:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['oper:orderRefundRules:export']"
        >导出</el-button>
      </el-col>
    </el-row>
    <el-table v-loading="loading" :data="orderRefundRulesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="规则名称" align="center" prop="name" />
      <el-table-column label="订单类型" align="center" prop="orderType" :formatter="orderTypeFormatter"/>
      <el-table-column label="退款率" align="center" prop="refundRate" />
      <el-table-column label="发车前多少分钟内" align="center" prop="timeBefore" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['oper:orderRefundRules:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['oper:orderRefundRules:remove']"
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

    <!-- 添加或修改订单退单规则对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="180px">
        <el-form-item label="规则名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入规则名称" />
        </el-form-item>
        <el-form-item label="订单类型" prop="orderType">
          <el-select v-model="form.orderType" placeholder="">
            <el-option label="机场巴士拼座订单" :value="1" >拼座订单</el-option>
            <el-option label="机场巴士包车订单" :value="2" >包车订单</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="退款费率" prop="refundRate">
          <el-input-number v-model="form.refundRate" :precision="1" :step="0.1" :max="1" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="发车前多少分钟内" prop="timeBefore">
          <el-input v-model="form.timeBefore" placeholder="请输入发车前多少分钟内" type="number" min="0"/>
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
  import { listOrderRefundRules, getOrderRefundRules, delOrderRefundRules, addOrderRefundRules, updateOrderRefundRules, exportOrderRefundRules } from "@/api/oper/orderRefundRules";

  export default {
    name: "OrderRefundRules",
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
        // 订单退单规则表格数据
        orderRefundRulesList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          orderType: undefined,
          refundRate: undefined,
          timeBefore: undefined,
          name: undefined
        },
        // 表单参数
        form: {
        },
        // 表单校验
        rules: {
          name: [
            {required: true, message: "名称不可为空", trigger: "blur"},
          ],
          orderType: [
            {required: true, message: "不可为空", trigger: "blur"},
          ],
          refundRate: [
            {required: true, message: "不可为空", trigger: "blur"},
          ],
          timeBefore: [
            {required: true, message: "不可为空", trigger: "blur"},
          ],
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询订单退单规则列表 */
      getList() {
        this.loading = true;
        listOrderRefundRules(this.queryParams).then(response => {
          this.orderRefundRulesList = response.rows;
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
          orderType: undefined,
          refundRate: undefined,
          timeBefore: undefined,
          name: undefined
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
        this.title = "添加订单退单规则";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getOrderRefundRules(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改订单退单规则";
        });
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != undefined) {
              updateOrderRefundRules(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {
              addOrderRefundRules(this.form).then(response => {
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
        this.$confirm('是否确认删除订单退单规则编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delOrderRefundRules(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有订单退单规则数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportOrderRefundRules(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      },
      orderTypeFormatter(row, column){
        if (row.orderType == '1') {
          return '拼座订单'
        }else if(row.orderType == '2'){
          return '包车订单'
        }
      }
    }
  };
</script>
