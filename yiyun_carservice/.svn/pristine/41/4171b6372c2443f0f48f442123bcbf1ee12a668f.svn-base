<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px">
      <el-form-item label="用户姓名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户手机号" prop="userPhone">
        <el-input
          v-model="queryParams.userPhone"
          placeholder="请输入用户手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="queryParams.sex" placeholder="请选择" clearable size="small">
          <el-option label="未知" value="0" />
          <el-option label="男" value="1" />
          <el-option label="女" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="身份证号码" prop="idcard">
        <el-input
          v-model="queryParams.idcard"
          placeholder="请输入身份证号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="会员等级" prop="vipLevel">
        <el-select v-model="queryParams.vipLevel" placeholder="请选择" clearable size="small">
          <el-option label="普通会员" value="1" />
          <el-option label="车会员" value="4" />
          <el-option label="VIP会员" value="3" />
          <el-option label="Plus会员" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="推广码" prop="promoteCode">
        <el-input
          v-model="queryParams.promoteCode"
          placeholder="请输入推广码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="邀请码" prop="inviteCode">
        <el-input
          v-model="queryParams.inviteCode"
          placeholder="请输入邀请码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户来源" prop="source">
        <el-select v-model="queryParams.source" placeholder="请选择" clearable size="small">
          <el-option label="普通用户" value="1" />
          <el-option label="推广用户" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="车牌号" prop="carNo">
        <el-input
          v-model="queryParams.carNo"
          placeholder="请输入车牌号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入昵称"
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

    <!--<el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['customer:cUserInfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['customer:cUserInfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['customer:cUserInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['customer:cUserInfo:export']"
        >导出</el-button>
      </el-col>
    </el-row>-->

    <el-table v-loading="loading" :data="cUserInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="昵称" align="center" prop="nickName" />
      <el-table-column label="用户姓名" align="center" prop="userName" />
      <el-table-column label="用户手机号" align="center" prop="userPhone" />
      <el-table-column label="车牌号" align="center" prop="carNo" />
      <el-table-column label="性别" align="center" prop="sex" :formatter="sexFormatter"/>
      <el-table-column label="身份证号码" align="center" prop="idcard" />
      <el-table-column label="头像" align="center" prop="headImg" width="100">
        <template slot-scope="scope">
          <el-image
            style="width: 80px; height: 80px"
            :src="scope.row.headImg"
            fit="contain"></el-image>
        </template>
      </el-table-column>
      <el-table-column label="地址" align="center" prop="address" />
      <el-table-column label="会员等级" align="center" prop="vipLevel" :formatter="vipLevelFormatter" />
      <el-table-column label="推广码" align="center" prop="promoteCode" />
      <el-table-column label="邀请码" align="center" prop="inviteCode" />
      <el-table-column label="用户来源" align="center" prop="source" :formatter="sourceFormatter"/>
      <el-table-column label="积分余额" align="center" prop="points" />
      <el-table-column label="备注" align="center" prop="remark" />
      <!--<el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['customer:cUserInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['customer:cUserInfo:remove']"
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

    <!-- 添加或修改C端用户信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户姓名" />
        </el-form-item>
        <el-form-item label="用户手机号" prop="userPhone">
          <el-input v-model="form.userPhone" placeholder="请输入用户手机号" />
        </el-form-item>
        <el-form-item label="0未知 1男性 2女性">
          <el-select v-model="form.sex" placeholder="请选择0未知 1男性 2女性">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="身份证号码" prop="idcard">
          <el-input v-model="form.idcard" placeholder="请输入身份证号码" />
        </el-form-item>
        <el-form-item label="头像" prop="headImg">
          <el-input v-model="form.headImg" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="会员等级(1.普通会员 2.Plus会员 3.VIP会员 4.车会员)" prop="vipLevel">
          <el-input v-model="form.vipLevel" placeholder="请输入会员等级(1.普通会员 2.Plus会员 3.VIP会员 4.车会员)" />
        </el-form-item>
        <el-form-item label="推广码(推广用户字段不为空)" prop="promoteCode">
          <el-input v-model="form.promoteCode" placeholder="请输入推广码(推广用户字段不为空)" />
        </el-form-item>
        <el-form-item label="邀请码" prop="inviteCode">
          <el-input v-model="form.inviteCode" placeholder="请输入邀请码" />
        </el-form-item>
        <el-form-item label="邀请码" prop="sessionKey">
          <el-input v-model="form.sessionKey" placeholder="请输入邀请码" />
        </el-form-item>
        <el-form-item label="用户来源(1.普通用户 2.推广用户)" prop="source">
          <el-input v-model="form.source" placeholder="请输入用户来源(1.普通用户 2.推广用户)" />
        </el-form-item>
        <el-form-item label="积分余额" prop="points">
          <el-input v-model="form.points" placeholder="请输入积分余额" />
        </el-form-item>
        <el-form-item label="登录密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入登录密码" />
        </el-form-item>
        <el-form-item label="支付密码" prop="payPassword">
          <el-input v-model="form.payPassword" placeholder="请输入支付密码" />
        </el-form-item>
        <el-form-item label="默认为0" prop="isDelete">
          <el-input v-model="form.isDelete" placeholder="请输入默认为0" />
        </el-form-item>
        <el-form-item label="车牌号" prop="carNo">
          <el-input v-model="form.carNo" placeholder="请输入车牌号" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="昵称" prop="openid">
          <el-input v-model="form.openid" placeholder="请输入昵称" />
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
import { listCUserInfo, getCUserInfo, delCUserInfo, addCUserInfo, updateCUserInfo, exportCUserInfo } from "@/api/customer/cUserInfo";

export default {
  name: "CUserInfo",
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
      // C端用户信息表格数据
      cUserInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        userPhone: undefined,
        sex: undefined,
        idcard: undefined,
        headImg: undefined,
        address: undefined,
        vipLevel: undefined,
        promoteCode: undefined,
        inviteCode: undefined,
        sessionKey: undefined,
        source: undefined,
        points: undefined,
        password: undefined,
        payPassword: undefined,
        isDelete: undefined,
        carNo: undefined,
        nickName: undefined,
        openid: undefined
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
    /** 查询C端用户信息列表 */
    getList() {
      this.loading = true;
      listCUserInfo(this.queryParams).then(response => {
        this.cUserInfoList = response.rows;
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
        userName: undefined,
        userPhone: undefined,
        sex: undefined,
        idcard: undefined,
        headImg: undefined,
        address: undefined,
        vipLevel: undefined,
        promoteCode: undefined,
        inviteCode: undefined,
        sessionKey: undefined,
        source: undefined,
        points: undefined,
        password: undefined,
        payPassword: undefined,
        isDelete: undefined,
        remark: undefined,
        carNo: undefined,
        nickName: undefined,
        openid: undefined
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
      this.title = "添加C端用户信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCUserInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改C端用户信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateCUserInfo(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addCUserInfo(this.form).then(response => {
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
      this.$confirm('是否确认删除C端用户信息编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCUserInfo(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有C端用户信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportCUserInfo(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    sexFormatter(row){
      if(row.sex=='1'){
        return '男';
      }else if(row.sex=='2'){
        return '女';
      }else{
        return '未知';
      }
    },
    vipLevelFormatter(row){
      if(row.vipLevel=='1'){
        return '普通会员';
      }else if(row.vipLevel=='2'){
        return 'Plus会员';
      }else if(row.vipLevel=='3'){
        return 'VIP会员';
      }else if(row.vipLevel=='4'){
        return '车会员';
      }else{
        return '未知';
      }
    },
    sourceFormatter(row){
      if(row.vipLevel=='1'){
        return '普通用户';
      }else if(row.vipLevel=='2'){
        return '推广用户';
      }else{
        return '未知';
      }
    },
  }
};
</script>
