<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px">
      <el-form-item label="店铺名称" prop="shopName">
        <el-input
          v-model="queryParams.shopName"
          placeholder="请输入店铺名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品类型" prop="goodsType">
        <el-select v-model="queryParams.goodsType" placeholder="请选择商品类型" clearable size="small">
          <el-option
            v-for="dict in shopTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="产品名称" prop="goodsName">
        <el-input
          v-model="queryParams.goodsName"
          placeholder="请输入产品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上架/下架" prop="onShelf">
        <el-select v-model="queryParams.onShelf" placeholder="请选择" clearable size="small">
          <el-option label="上架" value="1" />
          <el-option label="下架" value="2" />
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
          v-show="addButtonShow"
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['shop:shopGoods:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['shop:shopGoods:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['shop:shopGoods:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['shop:shopGoods:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="shopGoodsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="产品id" align="center" prop="id" />
      <el-table-column label="产品名称" align="center" prop="goodsName" />
      <el-table-column label="商品类型" align="center" prop="goodsType" :formatter="goodsTypeName"/>
      <el-table-column label="店铺名称" align="center" prop="shopName" />
      <el-table-column label="平台价格" align="center" prop="payPrice" />
      <el-table-column label="门市价格" align="center" prop="price" />
      <el-table-column label="售卖数量" align="center" prop="saleNum" />
      <el-table-column label="上架/下架" align="center" prop="onShelf" :formatter="onShelfName"/>
      <el-table-column label="产品排序" align="center" prop="goodsOrder" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['shop:shopGoods:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['shop:shopGoods:remove']"
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

    <!-- 添加或修改店铺产品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="商品类型" prop="goodsType">
              <el-select v-model="form.goodsType" placeholder="请选择商品类型">
                <el-option
                  v-for="dict in goodsTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品名称" prop="goodsName">
              <el-input v-model="form.goodsName" placeholder="请输入产品名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="平台价格" prop="payPrice">
              <el-input v-model="form.payPrice" placeholder="平台支付价格" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="门市价格" prop="price">
              <el-input v-model="form.price" placeholder="显示价格" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上架/下架" prop="onShelf">
              <el-select v-model="form.onShelf" placeholder="请选择">
                <el-option label="上架" value="1" />
                <el-option label="下架" value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品排序" prop="goodsOrder">
              <el-input v-model="form.goodsOrder" placeholder="请输入产品排序" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品图片" prop="topPicUrl">
              <el-upload
                :headers="headers"
                :multiple="true"
                class="upload-demo"
                :action="uploadImgUrl"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :on-success="handleSuccess"
                list-type="picture"
                :file-list="files"
                accept='.jpg,.jpeg,.png'>
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
              </el-upload>
              <el-dialog :visible.sync="dialogVisible">
                <img width="100%" :src="dialogImageUrl" alt="">
              </el-dialog>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="服务标准" prop="serviceStandard">
              <el-input type="textarea" v-model="form.serviceStandard" placeholder="请输入服务标准" />
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
import { listShopGoods, getShopGoods, delShopGoods, addShopGoods, updateShopGoods, exportShopGoods } from "@/api/shop/shopGoods";
import { getShop } from "@/api/shop/shop";
import { getToken } from '@/utils/auth'
export default {
  name: "ShopGoods",
  data() {
    return {
      addButtonShow:false,
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      // 上传的图片服务器地址
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      files:[],
      dialogImageUrl: '',
      dialogVisible: false,
      // 店铺类型字典
      shopTypeOptions: [],
      // 产品类型字典
      goodsTypeOptions: [],
      shopId:'',
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
      // 店铺产品表格数据
      shopGoodsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        shopName: undefined,
        goodsType: undefined,
        goodsName: undefined,
        payPrice: undefined,
        price: undefined,
        serviceStandard: undefined,
        isDelete: undefined,
        saleNum: undefined,
        topPicUrl: undefined,
        picUrl: undefined,
        onShelf: undefined,
        goodsOrder: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        goodsType: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
        goodsName: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
        payPrice: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
        price: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
        topPicUrl: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
        onShelf: [
          { required: true, message: "不能为空", trigger: "blur" }
        ]
      }
    };
  },
  watch: {
    '$route' (to, from) { // 监听路由是否变化
      if (to.query.shopId !== from.query.shopId) {
        this.shopId = to.query.shopId
        if (this.shopId) {
          this.getList(); // 重新加载数据
          this.getShopServiceType();
          this.addButtonShow = true;
        }else{
          this.addButtonShow = false;
        }
      }
    }
  },
  created() {
    this.shopId = this.$route.query.shopId;
    if (this.shopId){
      this.addButtonShow = true;
    }else{
      this.addButtonShow = false;
    }
    this.getDicts("shop_type").then(response => {
      this.shopTypeOptions = response.data;
      this.getShopServiceType();
    });
    this.getList();
  },
  methods: {
    getShopServiceType(){
      this.goodsTypeOptions = [];
      if (this.shopId) {
        getShop(this.shopId).then(response => {
          if (response.data.serviceType) {
            let serviceTypeArr = response.data.serviceType.split(',');
            for (let i=0;i<this.shopTypeOptions.length;i++){
              for (let j=0;j<serviceTypeArr.length;j++){
                if (this.shopTypeOptions[i].dictValue==serviceTypeArr[j]) {
                  this.goodsTypeOptions.push(this.shopTypeOptions[i]);
                  break;
                }
              }
            }
          }
        })
      }else{
        this.goodsTypeOptions = this.shopTypeOptions;
      }
    },
    /** 查询店铺产品列表 */
    getList() {
      this.loading = true;
      if (this.shopId) {
        this.queryParams.shopId = this.shopId;
      }
      listShopGoods(this.queryParams).then(response => {
        this.shopGoodsList = response.rows;
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
        createBy: undefined,
        createTime: undefined,
        updateTime: undefined,
        updateBy: undefined,
        shopId: undefined,
        goodsType: undefined,
        goodsName: undefined,
        payPrice: undefined,
        price: undefined,
        serviceStandard: undefined,
        isDelete: undefined,
        saleNum: undefined,
        topPicUrl: undefined,
        picUrl: undefined,
        onShelf: '1',
        goodsOrder: undefined
      };
      this.resetForm("form");
      this.files=[];
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
      this.title = "添加店铺产品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getShopGoods(id).then(response => {
        let topPicUrl = response.data.topPicUrl
        this.files=[]
        if(topPicUrl){
          for (let i=0;i<topPicUrl.split(',').length;i++) {
            this.files.push(
              { name:'文件'+(i+1),
                url:process.env.VUE_APP_BASE_API +topPicUrl.split(',')[i],
                url1:topPicUrl.split(',')[i]})
          }
        }
        this.form = response.data;
        this.open = true;
        this.title = "修改店铺产品";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      let fileUrl=''
      for (let i=0;i<this.files.length;i++) {
        if (this.files[i].response) {
          //新上传图片
          fileUrl += this.files[i].response.fileName+',';
        }else{
          //原数据图片
          fileUrl += this.files[i].url1+',';
        }
      }
      this.form.topPicUrl = fileUrl.substring(0,fileUrl.length-1);
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateShopGoods(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
              if (this.shopId){
                this.form.shopId = this.shopId;
              }
              addShopGoods(this.form).then(response => {
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
      this.$confirm('是否确认删除店铺产品编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delShopGoods(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有店铺产品数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportShopGoods(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    handleSuccess(res, file,fileList) {
      // res为图片服务器返回的数据
      // 如果上传成功
      console.log("fileList",fileList)
      if (res.code == 200) {
        this.files = fileList;
      } else {
        this.$message.error("图片插入失败");
      }
    },
    handlePreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
      console.log("预览",file);
    },
    handleRemove(file, fileList) {
      this.files = fileList;
    },
    handlePreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
      console.log("预览",file);
    },
    goodsTypeName(row){
      return this.selectDictLabel(this.shopTypeOptions, row.goodsType);
    },
    onShelfName(row){
      if (row.onShelf=='1'){
        return "上架销售"
      } else{
        return "下架停售"
      }
    },
  }
};
</script>
