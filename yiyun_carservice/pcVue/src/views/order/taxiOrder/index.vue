<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px">
      <el-form-item label="上车点" prop="beginStation">
        <el-input
          v-model="queryParams.beginStation"
          placeholder="请输入上车点"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下车点" prop="endStation">
        <el-input
          v-model="queryParams.endStation"
          placeholder="请输入下车点"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用车时间" prop="useTime">
        <el-date-picker
          value-format="yyyy-MM-dd"
          v-model="queryParams.useTime"
          type="date"
          placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="预定人" prop="reserveName">
        <el-input
          v-model="queryParams.reserveName"
          placeholder="请输入预定人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预定人手机号" prop="reserveMobile">
        <el-input
          v-model="queryParams.reserveMobile"
          placeholder="请输入预定人手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单号" prop="orderCode">
        <el-input
          v-model="queryParams.orderCode"
          placeholder="请输入订单号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单类型" prop="orderType">
        <el-select v-model="queryParams.orderType" placeholder="请选择" clearable size="small">
          <el-option label="实时单" value="1">实时单</el-option>
          <el-option label="预约单" value="2">预约单</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="订单状态" prop="orderStatus">
        <el-select v-model="queryParams.orderStatus" placeholder="请选择" clearable size="small">
          <el-option label="已下单" value="1">已下单</el-option>
          <el-option label="已取消" value="2">已取消</el-option>
          <el-option label="已接单" value="3">已接单</el-option>
          <el-option label="已完成" value="4">已完成</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="司机姓名" prop="driverName">
        <el-input
          v-model="queryParams.driverName"
          placeholder="请输入司机姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="司机联系方式" prop="driverPhone">
        <el-input
          v-model="queryParams.driverPhone"
          placeholder="请输入司机联系方式"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车牌号" prop="driverCarNo">
        <el-input
          v-model="queryParams.driverCarNo"
          placeholder="请输入车牌号"
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
      <!--<el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['order:taxiOrder:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['order:taxiOrder:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['order:taxiOrder:remove']"
        >删除</el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['order:taxiOrder:export']"
        >导出
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="taxiOrderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="主键" align="center" prop="id"/>
      <el-table-column label="上车点" align="center" prop="beginStation"/>
      <el-table-column label="下车点" align="center" prop="endStation"/>
      <el-table-column label="用车时间" align="center" prop="useTime"/>
      <el-table-column label="预定人" align="center" prop="reserveName"/>
      <el-table-column label="预定人手机号" align="center" prop="reserveMobile"/>
      <el-table-column label="完成订单时间" align="center" prop="finishTime"/>
      <el-table-column label="订单号" align="center" prop="orderCode"/>
      <el-table-column label="订单类型" align="center" prop="orderType" :formatter="orderTypeFormatter"/>
      <el-table-column label="订单状态" align="center" prop="orderStatus" :formatter="orderStatusFormatter"/>
      <el-table-column label="订单金额" align="center" prop="orderAmount"/>
      <el-table-column label="实收金额" align="center" prop="collectAmount"/>
      <el-table-column label="司机姓名" align="center" prop="driverName"/>
      <el-table-column label="司机联系方式" align="center" prop="driverPhone"/>
      <el-table-column label="车牌号" align="center" prop="driverCarNo"/>
      <!--<el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['order:taxiOrder:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['order:taxiOrder:remove']"
          >删除</el-button>
        </template>
      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改出租车订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="上车点" prop="beginStation">
          <el-input v-model="form.beginStation" placeholder="请输入上车点"/>
        </el-form-item>
        <el-form-item label="下车点" prop="endStation">
          <el-input v-model="form.endStation" placeholder="请输入下车点"/>
        </el-form-item>
        <el-form-item label="用车时间" prop="useTime">
          <el-input v-model="form.useTime" placeholder="请输入用车时间"/>
        </el-form-item>
        <el-form-item label="预定人" prop="reserveName">
          <el-input v-model="form.reserveName" placeholder="请输入预定人"/>
        </el-form-item>
        <el-form-item label="预定人Id" prop="reserveId">
          <el-input v-model="form.reserveId" placeholder="请输入预定人Id"/>
        </el-form-item>
        <el-form-item label="预定人手机号" prop="reserveMobile">
          <el-input v-model="form.reserveMobile" placeholder="请输入预定人手机号"/>
        </el-form-item>
        <el-form-item label="接单或取消订单时间" prop="appectTime">
          <el-input v-model="form.appectTime" placeholder="请输入接单或取消订单时间"/>
        </el-form-item>
        <el-form-item label="完成订单时间" prop="finishTime">
          <el-input v-model="form.finishTime" placeholder="请输入完成订单时间"/>
        </el-form-item>
        <el-form-item label="订单号" prop="orderCode">
          <el-input v-model="form.orderCode" placeholder="请输入订单号"/>
        </el-form-item>
        <el-form-item label="出租车订单类型:1.实时单 2.预约单">
          <el-select v-model="form.orderType" placeholder="请选择出租车订单类型:1.实时单 2.预约单">
            <el-option label="请选择字典生成" value=""/>
          </el-select>
        </el-form-item>
        <el-form-item label="订单状态:1、已下单 2、已取消 3、已接单 4、已完成">
          <el-radio-group v-model="form.orderStatus">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="订单金额" prop="orderAmount">
          <el-input v-model="form.orderAmount" placeholder="请输入订单金额"/>
        </el-form-item>
        <el-form-item label="实收金额" prop="collectAmount">
          <el-input v-model="form.collectAmount" placeholder="请输入实收金额"/>
        </el-form-item>
        <el-form-item label="司机Id" prop="driverId">
          <el-input v-model="form.driverId" placeholder="请输入司机Id"/>
        </el-form-item>
        <el-form-item label="司机姓名" prop="driverName">
          <el-input v-model="form.driverName" placeholder="请输入司机姓名"/>
        </el-form-item>
        <el-form-item label="司机联系方式" prop="driverPhone">
          <el-input v-model="form.driverPhone" placeholder="请输入司机联系方式"/>
        </el-form-item>
        <el-form-item label="
车牌号" prop="driverCarNo">
          <el-input v-model="form.driverCarNo" placeholder="请输入
车牌号"/>
        </el-form-item>
        <el-form-item label="客户所在位置经度" prop="longitude">
          <el-input v-model="form.longitude" placeholder="请输入客户所在位置经度"/>
        </el-form-item>
        <el-form-item label="客户所在位置纬度" prop="latitude">
          <el-input v-model="form.latitude" placeholder="请输入客户所在位置纬度"/>
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
  import {
    addTaxiOrder,
    delTaxiOrder,
    exportTaxiOrder,
    getTaxiOrder,
    listTaxiOrder,
    updateTaxiOrder
  } from "@/api/order/taxiOrder";

  export default {
    name: "TaxiOrder",
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
        // 出租车订单表格数据
        taxiOrderList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          beginStation: undefined,
          endStation: undefined,
          useTime: undefined,
          reserveName: undefined,
          reserveId: undefined,
          reserveMobile: undefined,
          appectTime: undefined,
          finishTime: undefined,
          orderCode: undefined,
          orderType: undefined,
          orderStatus: undefined,
          orderAmount: undefined,
          collectAmount: undefined,
          driverId: undefined,
          driverName: undefined,
          driverPhone: undefined,
          driverCarNo: undefined,
          longitude: undefined,
          latitude: undefined
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询出租车订单列表 */
      getList() {
        this.loading = true;
        listTaxiOrder(this.queryParams).then(response => {
          this.taxiOrderList = response.rows;
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
          beginStation: undefined,
          endStation: undefined,
          useTime: undefined,
          reserveName: undefined,
          reserveId: undefined,
          reserveMobile: undefined,
          createTime: undefined,
          appectTime: undefined,
          finishTime: undefined,
          orderCode: undefined,
          orderType: undefined,
          orderStatus: "0",
          orderAmount: undefined,
          collectAmount: undefined,
          driverId: undefined,
          driverName: undefined,
          driverPhone: undefined,
          driverCarNo: undefined,
          longitude: undefined,
          latitude: undefined
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
        this.single = selection.length != 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加出租车订单";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getTaxiOrder(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改出租车订单";
        });
      },
      /** 提交按钮 */
      submitForm: function () {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != undefined) {
              updateTaxiOrder(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {
              addTaxiOrder(this.form).then(response => {
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
        this.$confirm('是否确认删除出租车订单编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delTaxiOrder(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有出租车订单数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportTaxiOrder(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function () {
        });
      },
      orderTypeFormatter(row) {
        return row.orderType == 1 ? "实时单" : "预约单"
      },
      orderStatusFormatter(row) {
        if (row.orderStatus == 1) {
          return "已下单"
        } else if (row.orderStatus == 2) {
          return "已取消"
        } else if (row.orderStatus == 3) {
          return "已接单"
        } else if (row.orderStatus == 4) {
          return "已完成"
        }
      }
    }
  };
</script>
