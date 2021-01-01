npm<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px">
      <el-form-item label="推广码" prop="promoteCode">
        <el-input
          v-model="queryParams.promoteCode"
          placeholder="请输入推广码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="推广人手机号" prop="promotePhone">
        <el-input
          v-model="queryParams.promotePhone"
          placeholder="请输入推广人手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="推广人姓名" prop="promoteName">
        <el-input
          v-model="queryParams.promoteName"
          placeholder="请输入推广人姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="推广人身份证" prop="promoteIdcard">
        <el-input
          v-model="queryParams.promoteIdcard"
          placeholder="请输入推广人身份证"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="推广人职位" prop="promotePosition">
        <el-select v-model="queryParams.promotePosition"  placeholder="请选择服务类型">
          <el-option
            v-for="dict in companyPositionOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="公司/个人" prop="isCompany">
        <el-select v-model="queryParams.isCompany" placeholder="请选择" clearable size="small">
          <el-option label="公司" value="1" />
          <el-option label="个人" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="公司名称" prop="company">
        <el-select v-model="queryParams.company" filterable  placeholder="请选择服务类型">
          <el-option
            v-for="dict in companyOptions"
            :key="dict.promoteName"
            :label="dict.promoteName"
            :value="dict.promoteName"
          ></el-option>
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
          v-hasPermi="['customer:promote:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['customer:promote:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['customer:promote:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['customer:promote:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="promoteList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="推广码" align="center" prop="promoteCode" />
      <el-table-column label="推广人手机号" align="center" prop="promotePhone" />
      <el-table-column label="推广人姓名" align="center" prop="promoteName" />
      <el-table-column label="推广人身份证" align="center" prop="promoteIdcard" />
      <el-table-column label="推广人职位" align="center" prop="promotePosition" />
      <el-table-column label="公司/个人" align="center" prop="isCompany" :formatter="isCompanyFormatter"/>
      <!--<el-table-column label="来源" align="center" prop="source" />-->
      <el-table-column label="公司名称" align="center" prop="company" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['customer:promote:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['customer:promote:remove']"
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

    <!-- 添加或修改推广人员对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="推广码" prop="promoteCode">
              <el-input v-model="form.promoteCode" placeholder="请输入推广码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="推广人手机号" prop="promotePhone">
              <el-input v-model="form.promotePhone" placeholder="请输入推广人手机号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="推广人姓名" prop="promoteName">
              <el-input v-model="form.promoteName" placeholder="请输入推广人姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="推广人身份证" prop="promoteIdcard">
              <el-input v-model="form.promoteIdcard" placeholder="请输入推广人身份证" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="推广人职位" prop="promotePosition">
              <el-select v-model="form.promotePosition" @change="onSelectedCompany($event)" value-key="promoteName" filterable  placeholder="请选择服务类型">
                <el-option
                  v-for="dict in companyPositionOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公司/个人" prop="isCompany">
              <el-select v-model="form.isCompany" placeholder="请选择" disabled >
                <el-option label="公司" :value="1" />
                <el-option label="个人" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属公司" prop="company">
              <el-select v-model="form.company" @change="onSelectedCompany($event)" value-key="id" filterable >
                <el-option
                  v-for="dict in companyOptions"
                  :key="dict.id"
                  :label="dict.promoteName"
                  :value="dict"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" style="display: none;">
            <el-form-item label="来源" prop="promotePosition">
              <el-input v-model="form.source" />
            </el-form-item>
          </el-col>
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
import { listPromote, getPromote, delPromote, addPromote, updatePromote, exportPromote } from "@/api/customer/promote";
import {validatePhone} from '@/utils/validate';
export default {
  name: "Promote",
  data() {
    return {
      companyPositionOptions:[],
      companyOptions:[],
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
      // 推广人员表格数据
      promoteList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        promoteCode: undefined,
        promotePhone: undefined,
        promoteName: undefined,
        promoteIdcard: undefined,
        promotePosition: undefined,
        isCompany: undefined,
        source: undefined,
        company: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        promoteCode: [
          { required: true, message: "推广码不能为空", trigger: "blur" },
        ],
        promotePhone: [
          { required: true,validator: validatePhone, trigger: 'blur' }
        ],
        promoteName:[
          { required: true, message: "推广人姓名不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getCompanyList();
    this.getDicts("company_position").then(response => {
      this.companyPositionOptions = response.data;
    });
  },
  methods: {
    /** 查询推广人员列表 */
    getList() {
      this.loading = true;
      listPromote(this.queryParams).then(response => {
        this.promoteList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getCompanyList() {
      let queryParams={'isCompany':'1','pageNum':'1','pageSize':'1000'}
      listPromote(queryParams).then(response => {
        this.companyOptions = response.rows;
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
        promoteCode: undefined,
        promotePhone: undefined,
        promoteName: undefined,
        promoteIdcard: undefined,
        promotePosition: undefined,
        isCompany: 2,
        source: undefined,
        company: undefined
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
      this.title = "添加推广人员";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPromote(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改推广人员";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updatePromote(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addPromote(this.form).then(response => {
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
      this.$confirm('是否确认删除推广人员编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delPromote(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有推广人员数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportPromote(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    isCompanyFormatter(row){
      if (row.isCompany=='1'){
        return '公司'
      } else{
        return '个人'
      }
    },
    onSelectedCompany(val){
      this.form.source = val.source;
      this.form.company = this.form.company.promoteName;
    },
  }
};
</script>
