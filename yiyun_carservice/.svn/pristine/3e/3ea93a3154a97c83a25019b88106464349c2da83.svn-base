<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="手机号码" prop="userPhone">
        <el-input
          v-model="queryParams.userPhone"
          placeholder="请输入用户手机号码"
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
        <el-select v-model="queryParams.orderType" placeholder="请选择订单类型" clearable size="small">
          <el-option
            v-for="dict in orderTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="订单状态" prop="orderStatus">
        <el-select v-model="queryParams.orderStatus" placeholder="请选择订单状态" clearable size="small">
          <el-option
            v-for="dict in orderStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="客户姓名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入客户姓名"
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
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"

        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="corderList"  @selection-change="handleSelectionChange" height="420px">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="邀请码" align="center" prop="inviteCode" />
      <el-table-column label="用户手机号码" align="center" prop="userPhone" min-width="120px"/>
      <el-table-column label="订单号" align="center" prop="orderCode" min-width="120px"/>
      <el-table-column label="订单类型" align="center" prop="orderType" :formatter="orderTypeFormat" />
      <el-table-column label="订单状态" align="center" prop="orderStatus" :formatter="orderStatusFormat" />
      <el-table-column label="订单金额" align="center" prop="orderAmount" />
      <el-table-column label="使用积分" align="center" prop="usePoints" />
      <el-table-column label="赠送积分" align="center" prop="addPoints" />
      <el-table-column label="支付类型" align="center" prop="payType" />
      <el-table-column label="折扣金额" align="center" prop="discountAmount" />
      <el-table-column label="优惠券金额" align="center" prop="couponAmount" />
      <el-table-column label="积分抵扣金额" align="center" prop="pointsAmount" />
      <el-table-column label="实收金额" align="center" prop="collectAmount" />
      <el-table-column label="用户id" align="center" prop="userId" />
      <el-table-column label="订单取消时间" align="center" prop="cancelTime" />
      <el-table-column label="预约到店时间" align="center" prop="bookTime" />
      <el-table-column label="支付时间" align="center" prop="payTime" />
      <el-table-column label="退款时间" align="center" prop="refundTime" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="商家id" align="center" prop="shopId" />
      <el-table-column label="客户姓名" align="center" prop="userName" />
      <el-table-column label="车牌号" align="center" prop="carNo" />
      <el-table-column label="地址" align="center" prop="address" min-width="150px"/>
      <el-table-column label="一键救援人员姓名" align="center" prop="rescueName" />
      <el-table-column label="一键救援人员电话号码" align="center" prop="rescueMobile" min-width="120px"/>
      <el-table-column label="救援车辆车牌号码" align="center" prop="rescueCarNo" width="120px"/>
      <el-table-column label="客户所在位置经度" align="center" prop="longitude" min-width="100px"/>
      <el-table-column label="客户所在位置纬度" align="center" prop="latitude" min-width="100px"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" min-width="200px">
        <template slot-scope="scope">
          <el-button v-if="scope.row.orderStatus == 1 && scope.row.orderType != 1 && scope.row.orderType != 4"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="jieDan(scope.row)"

          >接单</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-goods"
            @click="handleUpdate(scope.row)"

          >产品详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-more"
            @click="getShopEvaluate(scope.row)"

          >评价</el-button>
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

    <!-- 添加或修改订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
      <el-table v-loading="loading" :data="goodsList" height="500px">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="主键id" align="center" prop="id" />
        <el-table-column label="订单号" align="center" prop="orderCode" />
        <el-table-column label="产品编号" align="center" prop="productCode" />
        <el-table-column label="产品名称" align="center" prop="productName" />
<!--        <el-table-column label="产品备注" align="center" prop="productDescribe" />-->
        <el-table-column  label="产品备注">
          <template slot-scope="scope">
            <el-image v-for="(item, index) in scope.row.productDescribeArr" :key='index' style="width: 30px; height: 30px" :src="item" :preview-src-list="[item]"></el-image>
          </template>
<!--          <template slot-scope="scope">-->
<!--            <el-image style="width: 30px; height: 30px" :src="scope.row.productDescribe" :preview-src-list="[scope.row.productDescribe]">-->
<!--              <div slot="error" class="image-slot">-->
<!--                <i class="el-icon-picture-outline"></i>-->
<!--              </div>-->
<!--            </el-image>-->
<!--          </template>-->

        </el-table-column>
        <el-table-column label="折扣金额" align="center" prop="discountAmount" />
        <el-table-column label="优惠券金额" align="center" prop="couponAmount" />
        <el-table-column label="积分抵扣金额" align="center" prop="pointsAmount" />
        <el-table-column label="实收金额" align="center" prop="collectAmount" />
        <el-table-column label="订单类型" align="center" prop="orderType" :formatter="orderTypeFormat" />
        <el-table-column label="订单状态" align="center" prop="payStatus" :formatter="orderStatusFormat" />
        <el-table-column label="支付时间" align="center" prop="payTime" />
        <el-table-column label="退款时间" align="center" prop="refundTime" />
        <el-table-column label="产品金额" align="center" prop="productAmount" />
        <el-table-column label="订单验证码" align="center" prop="verifyCode" />

      </el-table>

    </el-dialog>
  </div>
</template>
<style>
  .el-table--scrollable-y .el-table__body-wrapper {
    overflow-y: auto;
    padding: 0 0 0 0;
    margin: 0 0 0 0;

  }
  .pagination-container {
    padding: 2px 5px !important;
  }
  .app-container {
    padding: 1px;
  }


</style>
<script>
import { listCorder, getCorder, delCorder, addCorder, updateCorder, exportCorder,jieDan } from "@/api/business/corder";

export default {
  name: "Corder",
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
      // 订单表格数据
      corderList: [],
      goodsList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 订单类型(1.洗车 2.养护 3.维修 4.检测 5.驾培 6.保险 7.救援)字典
      orderTypeOptions: [],
      // 订单状态(1已下单 2已取消 3已接单 4.服务中 5.已完成 6已退款)字典
      orderStatusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userPhone: undefined,
        orderCode: undefined,
        orderType: undefined,
        orderStatus: undefined,
        userName: undefined,
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
    this.getDicts("shop_type").then(response => {
      this.orderTypeOptions = response.data;
    });
    this.getDicts("order_status").then(response => {
      this.orderStatusOptions = response.data;
    });
  },
  methods: {
    /** 查询订单列表 */
    getList() {
      this.loading = true;
      listCorder(this.queryParams).then(response => {
        this.corderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 订单类型(1.洗车 2.养护 3.维修 4.检测 5.驾培 6.保险 7.救援)字典翻译
    orderTypeFormat(row, column) {
      return this.selectDictLabel(this.orderTypeOptions, row.orderType);
    },
    // 订单状态(1已下单 2已取消 3已接单 4.服务中 5.已完成 6已退款)字典翻译
    orderStatusFormat(row, column) {
      return this.selectDictLabel(this.orderStatusOptions, row.orderStatus);
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
        inviteCode: undefined,
        userPhone: undefined,
        orderCode: undefined,
        orderType: undefined,
        orderStatus: "0",
        orderAmount: undefined,
        usePoints: undefined,
        addPoints: undefined,
        payType: undefined,
        discountAmount: undefined,
        couponAmount: undefined,
        pointsAmount: undefined,
        collectAmount: undefined,
        userId: undefined,
        createTime: undefined,
        cancelTime: undefined,
        bookTime: undefined,
        payTime: undefined,
        refundTime: undefined,
        remark: undefined,
        isDelete: undefined,
        shopId: undefined,
        userName: undefined,
        carNo: undefined,
        address: undefined,
        rescueName: undefined,
        rescueMobile: undefined,
        rescueCarNo: undefined,
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
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const orderCode = row.orderCode;

      getCorder(orderCode).then(response => {
        this.goodsList = response.data;
        this.open = true;
        this.title = "商品信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateCorder(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addCorder(this.form).then(response => {
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
      this.$confirm('是否确认删除订单编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCorder(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有订单数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportCorder(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    getShopEvaluate(row){
      this.$router.push({
        path: '/merchants/evaluate',
        query: {
          orderCode: row.orderCode,
        }
      })
    },
    jieDan(row){
      this.$confirm('是否确认接单?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        var obj = {};
        obj.id = row.id;
        obj.orderCode = row.orderCode;
        return jieDan(obj);
      }).then(() => {
        this.getList();
        this.msgSuccess("接单成功");
      }).catch(function() {});
    }
  }
};
</script>
