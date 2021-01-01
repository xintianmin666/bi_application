<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px">
      <el-form-item label="店铺名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入店铺名称"
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
      <el-form-item label="联系号码" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入联系号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上架/下架" prop="isShow">
        <el-select v-model="queryParams.isShow" placeholder="请选择" clearable size="small">
          <el-option label="上架" value="1" />
          <el-option label="下架" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="店铺来源" prop="selfShop">
        <el-select v-model="queryParams.selfShop" placeholder="请选择" clearable size="small">
          <el-option label="自营店" value="1" />
          <el-option label="加盟店" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="店铺类型" prop="shopType">
        <el-select v-model="queryParams.shopType" placeholder="请选择店铺类型" clearable size="small">
          <el-option
            v-for="dict in shopTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="服务类型" prop="shopType">
        <el-select v-model="queryParams.serviceType" placeholder="请选择服务类型" clearable size="small">
          <el-option
            v-for="dict in shopTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
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
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['shop:shop:edit']"
        >审核</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="shopList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="店铺名称" align="center" prop="name" />
      <el-table-column label="店铺来源" align="center" prop="shopmaintype" :formatter="shopmaintypeFormatter" />
      <el-table-column label="地址" align="center" prop="address" />
      <el-table-column label="联系号码" align="center" prop="phone" />
      <el-table-column label="工作内容" align="center" prop="workContent" />
      <el-table-column label="负责人" align="center" prop="chargePerson" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['shop:shop:edit']"
          >审核</el-button>
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

    <!-- 添加或修改店铺店铺对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="店铺名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入店铺名称" :readonly="form.id != undefined"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="归属部门" prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" placeholder="请选择归属部门"  :disabled ="form.id != undefined" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系号码" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入手机号码" :readonly="form.id != undefined"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="工作内容" prop="workContent">
              <el-input type="textarea" v-model="form.workContent" placeholder="请输入工作内容" :readonly="form.id != undefined"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="服务时间">
              <el-time-select
                placeholder="起始时间"
                v-model="startTime"
                :picker-options="{
                start: '06:30',
                step: '00:30',
                end: '17:30'
              }">
              </el-time-select>
              <el-time-select prop="endTime"
                placeholder="结束时间"
                v-model="endTime"
                :picker-options="{
                start: '12:30',
                step: '00:30',
                end: '23:30',
                minTime: startTime
              }">
              </el-time-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="店铺地址" prop="address">
              <el-input v-model="form.address" placeholder="请输入地址" icon="el-icon-map-location" :readonly="form.id != undefined"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="经纬度" prop="lonLat">
              <el-input v-model="form.lonLat" :readonly="form.id != undefined"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上架/下架" prop="isShow">
              <el-select v-model="form.isShow" placeholder="请选择">
                <el-option label="上架" value="1" />
                <el-option label="下架" value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审核结果" prop="isShow">
              <el-select v-model="form.checkreult" placeholder="请选择">
                <el-option label="待审核" value="1" />
                <el-option label="通过" value="2" />
                <el-option label="不通过" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="店铺来源" prop="selfShop">
              <el-select v-model="form.selfShop" placeholder="请选择">
                <el-option label="自营店" value="1" />
                <el-option label="加盟店" value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="this.form.shopmaintype=='2'">
            <el-form-item label="店铺等级" prop="shoprank">
              <el-select v-model="form.shoprank" placeholder="请选择">
                <el-option label="一级采购商" value="1" />
                <el-option label="二级采购商" value="2" />
                <el-option label="三级采购商" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="店铺标签" prop="shoprank">
              <el-select v-model="form.shopTags" multiple placeholder="请选择">
                <el-option
                  v-for="dict in shoptagsOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="this.form.shopmaintype=='3'">
            <el-form-item label="店铺类型" prop="shopType">
              <el-select v-model="form.shopType" placeholder="请选择店铺类型">
                <el-option
                  v-for="dict in shopTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="this.form.shopmaintype=='3'||this.form.shopmaintype=='2'">
            <el-form-item label="产品类型" prop="serviceTypeArr">
              <el-select v-model="form.serviceTypeArr" multiple @change="$forceUpdate()" placeholder="请选择服务类型">
                <el-option
                  v-for="dict in shopTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="负责人" prop="chargePerson">
              <el-input v-model="form.chargePerson" placeholder="请输入负责人" :readonly="form.id != undefined"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="店铺图片" prop="shopTopPics">
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
              </el-upload>
              <el-dialog :visible.sync="dialogVisible">
                <img width="100%" :src="dialogImageUrl" alt="">
              </el-dialog>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="默认排序" prop="shopOrder">
              <el-input v-model="form.shopOrder" placeholder="请输入默认排序" />
            </el-form-item>
          </el-col>
        </el-row>
       <!-- <el-form-item label="店铺列表缩略图" prop="coverPic">
          <el-input v-model="form.coverPic" placeholder="请输入列表缩略图" />
        </el-form-item>
        <el-form-item label="服务类型">
          <el-select v-model="form.serviceType" placeholder="请选择服务类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="商铺标签" prop="shopTags">
          <el-input v-model="form.shopTags" placeholder="请输入商铺标签" />
        </el-form-item>
        <el-form-item label="是否支持积分币抵扣 1是 2否" prop="canUsePoints">
          <el-input v-model="form.canUsePoints" placeholder="请输入是否支持积分币抵扣 1是 2否" />
        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listShop, getShop, delShop, addShop, updateShop, exportShop } from "@/api/shop/shop";
import Map from '@/components/Map/index2';
import { getToken } from '@/utils/auth'
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "Shop",
  components: { Map,Treeselect },
  data() {
    return {
      shopTag1:[],//供应商店铺标签
      shopTag2:[],//采购商店铺标签
      shopTag3:[],//普通店铺标签
      shoptagsOptions:[],
      shopmaintypeOptions:[],
      // 部门名称
      deptName: undefined,
      startTime:'',
      endTime:'',
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      files:[],
      dialogImageUrl: '',
      dialogVisible: false,
      // 上传的图片服务器地址
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      // 店铺类型字典
      shopTypeOptions: [],
      address:'',
      drawer: false,
      direction: 'btt',
      // 部门树选项
      deptOptions: undefined,
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
      // 店铺表格数据
      shopList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: undefined,
        name: undefined,
        address: undefined,
        phone: undefined,
        tel: undefined,
        workContent: undefined,
        workTime: undefined,
        lonLat: undefined,
        score: undefined,
        num: undefined,
        points: undefined,
        isDelete: undefined,
        isShow: undefined,
        coverPic: undefined,
        shopTopPics: undefined,
        selfShop: undefined,
        shopOrder: undefined,
        serviceType: undefined,
        chargePerson: undefined,
        shopType: undefined,
        shopTags: undefined,
        canUsePoints: undefined,
        serviceRange: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userName: [
          { required: true, message: "登录账号不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        address: [
          { required: true, message: "地址不能为空", trigger: "blur" }
        ],
        phone: [
          { required: true, message: "手机号码不能为空", trigger: "blur" }
        ],
        workContent: [
          { required: true, message: "工作内容不能为空", trigger: "blur" }
        ],
        startTime:[
          { required: true, message: "不能为空", trigger: "blur" }
        ],
        lonLat: [
          { required: true, message: "经纬度不能为空", trigger: "blur" }
        ],
        isShow: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
        coverPic: [
          { required: true, message: "列表缩略图不能为空", trigger: "blur" }
        ],
        shopTopPics: [
          { required: true, message: "店铺轮播图不能为空", trigger: "blur" }
        ],
        selfShop: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
        serviceType: [
          { required: true, message: "服务类型（字典）不能为空", trigger: "blur" }
        ],
        shopType: [
          { required: true, message: "店铺类型不能为空", trigger: "blur" }
        ],
        deptId :[
          { required: true, message: "不能为空", trigger: "blur" }
        ],
        serviceTypeArr:[ { required: true, message: "不能为空", trigger: "blur" }]

      }
    };
  },
  created() {
    this.getList();
    this.getDicts("shop_type").then(response => {
      this.shopTypeOptions = response.data;
    });
    this.getDicts("usertype").then(response => {
      this.shopmaintypeOptions = response.data;
    });
    this.getDicts("shop_tag").then(response => {
      this.shopTag3 = response.data;
    });
    this.getDicts("cg_shop_tags").then(response => {
      this.shopTag2 = response.data;
    });
    this.getDicts("gy_shop_tags").then(response => {
      this.shopTag1 = response.data;
    });
    this.getTreeselect();
  },
  computed: {
    newAddress() {
      return this.form.address;
    }
  },
  /*watch: {
    newAddress: 'getLonLat' // 值可以为methods的方法名
  },*/
  methods: {
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
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
    handleRemove(file, fileList) {
      this.files = fileList;
    },
    handlePreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
      console.log("预览",file);
    },
    getLonLat(){
        this.drawer = true
        setTimeout(()=>{this.$refs.map.searchAndMarkSite(this.form.address)},1000);

      /*debugger
      if(this.form.lonlat){
        let lonlatArr = [];
        lonlatArr.push(this.form.address.split(',')[0])
        lonlatArr.push(this.form.address.split(',')[1])
        setTimeout(()=>{this.$refs.map.addSiteMark(lonlatArr,'')},1000);
      }else if (!this.form.lonlat && this.form.address) {
        setTimeout(()=>{this.$refs.map.searchAndMarkSite(this.form.address)},1000);
      }*/
    },
    handleClose(done) {
      let lnglatObj = this.$refs.map.lnglat;
      if (lnglatObj) {
        this.form.lonLat = lnglatObj.lng +','+lnglatObj.lat;
      }
      done();
    },
    /** 查询店铺列表 */
    getList() {
      this.loading = true;
      this.queryParams.checkreult = '1'
      listShop(this.queryParams).then(response => {
        this.shopList = response.rows;
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
        deptId: undefined,
        name: undefined,
        address: undefined,
        phone: undefined,
        tel: undefined,
        workContent: undefined,
        workTime: undefined,
        lonLat: undefined,
        score: undefined,
        num: undefined,
        points: undefined,
        isDelete: undefined,
        isShow: '1',
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        coverPic: undefined,
        shopTopPics: undefined,
        selfShop: undefined,
        shopOrder: undefined,
        serviceType: undefined,
        chargePerson: undefined,
        shopType: undefined,
        shopTags: undefined,
        canUsePoints: undefined,
        serviceRange: undefined,
        serviceTypeArr:[]
      };
      this.resetForm("form");
      this.files=[];
      this.startTime='';
      this.endTime=''
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
      this.title = "添加店铺";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const id = row.id || this.ids
      getShop(id).then(response => {
        this.form = response.data;

        if (this.form.shopmaintype=='1') {
          this.shoptagsOptions = this.shopTag1
        }else if(this.form.shopmaintype=='2'){
          this.shoptagsOptions = this.shopTag2
        }else if(this.form.shopmaintype=='3'){
          this.shoptagsOptions = this.shopTag3
        }

        this.startTime  = this.form.workTime.split(',')[0];
        this.endTime  = this.form.workTime.split(',')[1]
        let shopTopPics = response.data.shopTopPics
        this.files=[]
        if(shopTopPics){
          for (let i=0;i<shopTopPics.split(',').length;i++) {
            this.files.push({name:'文件'+(i+1),url:process.env.VUE_APP_BASE_API +shopTopPics.split(',')[i]
              ,url1:shopTopPics.split(',')[i]})
          }
        }
        if (this.form.serviceType){
          this.form.serviceTypeArr = this.form.serviceType.split(',');
        }
        if (this.form.shopTags){
          this.form.shopTags = this.form.shopTags.split(',');
        }
        this.open = true;
        this.title = "审核店铺";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.form.workTime = this.startTime +','+this.endTime;
      let fileUrl=''
      for (let i=0;i<this.files.length;i++) {
        if (this.files[i].response) {
          //新上传图片
          fileUrl += this.files[i].response.fileName+','
        }else{
          //原数据图片
          fileUrl += this.files[i].url1+','
        }
      }
      this.form.shopTopPics = fileUrl.substring(0,fileUrl.length-1)
      this.form.coverPic = fileUrl.split(",")[0]
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.shopTags) {
            this.form.shopTags = this.form.shopTags.toString();
          }
          if (this.form.serviceTypeArr) {
            this.form.serviceType = this.form.serviceTypeArr.toString();
          }
          if (this.form.id != undefined) {
            updateShop(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addShop(this.form).then(response => {
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
      this.$confirm('是否确认删除店铺编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delShop(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有店铺数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportShop(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    shopTypeFormat(row){
      return this.selectDictLabel(this.shopTypeOptions, row.shopType);
    },
    serviceTypeFormat(row){
      let serviceType = '';
      if (row.serviceType) {
        let serviceTypeArr = row.serviceType.split(',');
        for (let i=0;i<serviceTypeArr.length;i++) {
          serviceType += this.selectDictLabel(this.shopTypeOptions, serviceTypeArr[i])+' ';
        }
      }
      return serviceType;
    },
    shopmaintypeFormatter(row){
      return this.selectDictLabel(this.shopmaintypeOptions, row.shopmaintype);
    },
    selfShopFormat(row){
      if (row.selfShop=='1'){
        return "自营店"
      } else if (row.selfShop=='2'){
        return "加盟店"
      }
    },
    checkreultFormat(row){
      if (row.checkreult=='1'){
        return "待审核"
      } else if (row.checkreult=='2'){
        return "通过"
      }else if (row.checkreult=='3'){
        return "不通过"
      }else{return "待审核"}
    },
    /**新建产品页面*/
    getShopGoods(row){
      this.$router.push({
        path: '/merchants/shopGoods',
        query: {
          shopId: row.id,
        }
      })
    },
    /**商铺的评价列表页面*/
    getShopEvaluate(row){
      this.$router.push({
        path: '/merchants/evaluate',
        query: {
          shopId: row.id,
        }
      })
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.getList();
    },
  }
};
</script>
