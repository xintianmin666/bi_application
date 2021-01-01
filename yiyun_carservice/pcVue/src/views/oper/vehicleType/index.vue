<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="最大承载人数" prop="capacityMax">
        <el-input
          v-model="queryParams.capacityMax"
          placeholder="请输入最大承载人数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车辆类型名称" prop="typeName">
        <el-input
          v-model="queryParams.typeName"
          placeholder="请输入车辆类型名称"
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
          v-hasPermi="['oper:vehicleType:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['oper:vehicleType:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['oper:vehicleType:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['oper:vehicleType:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="vehicleTypeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="车辆类型" align="center" prop="typeName" />
      <el-table-column label="最大承载人数" align="center" prop="capacityMax" />
      <el-table-column label="可乘坐旅客数" align="center" prop="passengerNum" />
      <el-table-column label="无座儿童数" align="center" prop="noSeatChildNum" />
      <el-table-column label="行李箱备注" align="center" prop="suitcaseNumRemark" />
      <el-table-column label="车型备注" align="center" prop="remark" />
      <el-table-column
        label="车型图片"
        width="180">
        <template slot-scope="scope">
          <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.carPicUrl"
            :fit="fit"></el-image>
          <i class="el-icon-time"></i>
        </template>
      </el-table-column>
      <el-table-column label="拼车最少人数" align="center" prop="pincheMin" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['oper:vehicleType:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['oper:vehicleType:remove']"
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

    <!-- 添加或修改车辆类型对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="车辆类型名称" prop="typeName">
          <el-select v-model="form.typeName" placeholder="请选择">
            <el-option
              v-for="dict in carTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="最大承载人数" prop="capacityMax">
          <el-input type="number" min="0" v-model="form.capacityMax" placeholder="请输入最大承载人数" />
        </el-form-item>
        <el-form-item label="可乘坐旅客数" prop="passengerNum">
          <el-input type="number" min="0"  v-model="form.passengerNum" placeholder="请输入可乘坐旅客数" />
        </el-form-item>
        <el-form-item label="无座儿童数" prop="noSeatChildNum">
          <el-input type="number" min="0"  v-model="form.noSeatChildNum" placeholder="请输入可携带没有座位数的儿童" />
        </el-form-item>
        <el-form-item label="空间备注" prop="suitcaseNumRemark">
          <el-input v-model="form.suitcaseNumRemark" placeholder="请输入可携带行李箱备注" />
        </el-form-item>
        <el-form-item label="车型图片地址" prop="carPicUrl">
          <el-select v-model="form.carPicUrl" placeholder="请选择">
            <el-option
              label="四座小车图片"
              value="https://image.chebada.com/image/merchant-resources-man/upload/cartype/2019/06/06/e691fc0d863b2d6d4a726d732002e4f5.png">
            </el-option>
            <el-option
              label="商务车图片"
              value="https://image.chebada.com/image/merchant-resources-man/upload/cartype/2019/06/06/22198c8862c46b7f364bb5f1cc4ce67e.png">
            </el-option>
            <el-option
              label="中巴车图片"
              value="https://image.chebada.com/image/merchant-resources-man/upload/cartype/2019/12/23/b11d1fe68690e320b2f4fd82d3721652.png">
            </el-option>
            <el-option
              label="33座大巴车图片"
              value="https://image.chebada.com/image/merchant-resources-man/upload/cartype/2019/12/23/911f4227bd6616651a3617ee5923f56a.png">
            </el-option>
            <el-option
              label="45座大巴车图片"
              value="https://image.chebada.com/image/merchant-resources-man/upload/cartype/2019/06/06/8cefc807ae31ad66763bb376a860c94f.png">
            </el-option>
          </el-select>
          <!--<el-input v-model="form.carPicUrl" placeholder="请输入车辆类型图片" />-->
        </el-form-item>
        <el-form-item label="拼车最少人数" prop="pincheMin">
          <el-input v-model="form.pincheMin" placeholder="请输入拼车最少人数" />
        </el-form-item>
        <el-form-item label="车型备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入车型说明" />
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
import { listVehicleType, getVehicleType, delVehicleType, addVehicleType, updateVehicleType, exportVehicleType } from "@/api/oper/vehicleType";

export default {
  name: "VehicleType",
  data() {
    return {
      carTypeOptions:[],
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
      // 车辆类型表格数据
      vehicleTypeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        capacityMax: undefined,
        passengerNum: undefined,
        noSeatChildNum: undefined,
        suitcaseNumRemark: undefined,
        typeName: undefined,
        carPicUrl: undefined,
        pincheMin: undefined,
        createUserId: undefined,
        modifyTime: undefined,
        modifyUserId: undefined,
        isvaliable: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        typeName: [
          {required: true, message: "不可为空", trigger: "blur"},
        ],capacityMax: [
          {required: true, message: "不可为空", trigger: "blur"},
        ],passengerNum: [
          {required: true, message: "不可为空", trigger: "blur"},
        ],carPicUrl: [
          {required: true, message: "不可为空", trigger: "blur"},
        ],pincheMin: [
          {required: true, message: "不可为空", trigger: "blur"},
        ],
        suitcaseNumRemark: [
          {required: true, message: "不可为空", trigger: "blur"},
        ],
        remark: [
          {required: true, message: "不可为空", trigger: "blur"},
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("car_type").then(response => {
      this.carTypeOptions = response.data;
    });
  },
  methods: {
    /** 查询车辆类型列表 */
    getList() {
      this.loading = true;
      listVehicleType(this.queryParams).then(response => {
        this.vehicleTypeList = response.rows;
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
        vcehicleTypeId: undefined,
        capacityMax: undefined,
        passengerNum: undefined,
        noSeatChildNum: undefined,
        suitcaseNumRemark: undefined,
        remark: undefined,
        typeName: undefined,
        carPicUrl: undefined,
        pincheMin: undefined,
        createTime: undefined,
        createUserId: undefined,
        modifyTime: undefined,
        modifyUserId: undefined,
        isvaliable: undefined
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
      this.ids = selection.map(item => item.vcehicleTypeId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加车辆类型";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const vcehicleTypeId = row.vcehicleTypeId || this.ids
      getVehicleType(vcehicleTypeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改车辆类型";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.vcehicleTypeId != undefined) {
            updateVehicleType(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addVehicleType(this.form).then(response => {
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
      const vcehicleTypeIds = row.vcehicleTypeId || this.ids;
      this.$confirm('是否确认删除车辆类型编号为"' + vcehicleTypeIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delVehicleType(vcehicleTypeIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有车辆类型数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportVehicleType(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
