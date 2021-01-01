<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px">
      <el-form-item label="营运城市名称" prop="operCityName">
        <el-input
          v-model="queryParams.operCityName"
          placeholder="请输入营运城市名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="营运城市拼音" prop="operCityPinyin">
        <el-input
          v-model="queryParams.operCityPinyin"
          placeholder="请输入营运城市拼音"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="是否生效" prop="isvaliable">
        <el-select v-model="queryParams.isvaliable" placeholder="请选择"  @keyup.enter.native="handleQuery">
          <el-option key="1" label="生效" value="1">
            生效
          </el-option>
          <el-option key="0" label="停用" value="0">
            停用
          </el-option>
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
          v-hasPermi="['oper:city:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['oper:city:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['oper:city:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['oper:city:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="cityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="营运城市ID" align="center" prop="operCityId" />
      <el-table-column label="营运城市名称" align="center" prop="operCityName" />
      <el-table-column label="营运城市代码" align="center" prop="operCityCode" />
      <el-table-column label="营运城市拼音" align="center" prop="operCityPinyin" />
      <el-table-column label="营运城市简拼" align="center" prop="operCitySp" />
      <el-table-column label="城市地区代码" align="center" prop="areaCode" />
      <el-table-column label="城市地区上级代码" align="center" prop="areaParentCode" />
      <el-table-column label="是否生效" align="center" prop="isvaliable" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isvaliable"
            @change="getIsvaliableValue(scope.row)"
            active-value="1"
            inactive-value="0"
            active-color="#13ce66"
            inactive-color="#c0c4cc">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['oper:city:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['oper:city:remove']"
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

    <!-- 添加或修改营运城市信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="营运城市名称" prop="operCityName">
          <el-select v-model="areaCode"  value-key="areaCode" @change="onSelectedArea($event)" filterable
                     placeholder="请输入营运城市名称" >
            <el-option
              v-for="item in areaOptions"
              :key="item.areaCode"
              :label="item.areaName"
              :value="item">
            </el-option>
          </el-select>

          <el-input :disabled="true" v-model="form.operCityName" />
        </el-form-item>
        <el-form-item label="营运城市代码" prop="operCityCode">
          <el-input v-model="form.operCityCode" placeholder="请输入营运城市代码" />
        </el-form-item>
        <el-form-item label="营运城市简拼" prop="operCityPinyin">
          <el-input v-model="form.operCitySp" placeholder="请输入营运城市简拼" />
        </el-form-item>
        <el-form-item label="营运城市拼音" prop="operCityPinyin">
          <el-input v-model="form.operCityPinyin" placeholder="请输入营运城市简拼" />
        </el-form-item>
        <el-form-item label="城市地区代码" prop="areaCode">
          <el-input :disabled="true" v-model="form.areaCode" placeholder="请输入城市地区代码" />
        </el-form-item>
        <el-form-item label="城市地区上级代码" prop="areaParentCode">
          <el-input :disabled="true" v-model="form.areaParentCode"  />
        </el-form-item>
        <!--<el-form-item label="是否生效" prop="isvaliable">
          <el-switch
            @change="getIsvaliableValue($event)"
            v-model="isvaliableValue"
            active-color="#13ce66"
            inactive-color="#c0c4cc">
          </el-switch>
        </el-form-item>-->
      </el-form>

      <div slot="footer" class="dialog-footer" >
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCity, getCity, delCity, addCity, updateCity, exportCity,changeIsvaliable} from "@/api/oper/city";
import { areaTreeselect,listArea } from "@/api/oper/tArea";
import vPinyin from '@/utils/vue-py.js';
export default {
  name: "City",
  data() {
    return {
      isvaliableValue:true,
      filterText:'',
      // 地区选项
      areaOptions: [],
      areaCode:'',
      areaItem:{},
      defaultProps: {
        children: "children",
        label: "label"
      },
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
      // 营运城市信息表格数据
      cityList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        operCityName: undefined,
        operCityCode: undefined,
        operCityPinyin: undefined,
        areaCode: undefined,
        createUserId: undefined,
        modifyTime: undefined,
        modifyUserId: undefined,
        isvaliable: undefined
      },
      // 表单参数
      form: {
        isvaliable:this.isvaliableValue==true?1:0,
      },
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getAreaOptionselect();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },

  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    /** 查询地区数组 */
    getAreaOptionselect(val) {
      listArea(val).then(response => {
        this.areaOptions = response.rows;
      });
    },
    getCityPinyin(val){
      return vPinyin.chineseToPinYin(val);
    },
    getPinyinSx(val){
      let SX = '';
      for (var i = 0; i < this.getCityPinyin(val).length; i++) {
        var c = this.getCityPinyin(val).charAt(i);
        if (/^[A-Z]+$/.test(c)) {
          SX += c;
        }
      }
      this.form.operCitySp = SX;
    },

    onSelectedArea(val){
      this.form.areaCode = val.areaCode;
      this.form.operCityCode = val.areaCode;
      this.form.operCityName = val.areaName;
      this.form.areaParentCode = val.parentCode;
      this.getPinyinSx(val.areaName);
      this.form.operCityPinyin = this.getCityPinyin(val.areaName);
    },

    getIsvaliableValue(row){
      let text = row.isvaliable === "1" ? "启用" : "停用";
      this.$confirm('确认要"' + text + '""' + row.operCityName + '"城市吗?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return changeIsvaliable(row.operCityId, row.isvaliable);
      }).then(() => {
        this.msgSuccess(text + "成功");
      }).catch(function() {
        row.isvaliable = row.isvaliable === "0" ? "1" : "0";
      });
    },

    /** 查询营运城市信息列表 */
    getList() {
      this.loading = true;
      listCity(this.queryParams).then(response => {
        this.cityList = response.rows;
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
        operCityId: undefined,
        operCityName: undefined,
        operCityCode: undefined,
        operCityPinyin: undefined,
        areaCode: undefined,
        remark: undefined,
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
      this.ids = selection.map(item => item.operCityId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加营运城市信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const operCityId = row.operCityId || this.ids
      getCity(operCityId).then(response => {
        this.form = response.data;
        this.isvaliableValue = this.form.isvaliable=='1'?true:false;
        this.open = true;
        this.title = "修改营运城市信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.form.isvaliable = this.isvaliableValue==true?'1':'0';
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.operCityId != undefined) {
            updateCity(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addCity(this.form).then(response => {
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
      const operCityIds = row.operCityId || this.ids;
      this.$confirm('是否确认删除营运城市信息编号为"' + operCityIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCity(operCityIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有营运城市信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportCity(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
