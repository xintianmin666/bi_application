<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="产品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入产品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--<el-form-item label="产品编号" prop="productCode">
        <el-input
          v-model="queryParams.productCode"
          placeholder="请输入产品编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->

      <el-form-item label="线路类型" prop="lineType">
          <el-select v-model="queryParams.lineType" placeholder="请选择线路类型" clearable size="small">
            <el-option
              v-for="dict in lineTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
      </el-form-item>
      <el-form-item label="发车点" prop="startSiteId">
        <el-input
          v-model="queryParams.startSiteId"
          placeholder="请输入发车点"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目的地点" prop="endSiteId">
        <el-input
          v-model="queryParams.endSiteId"
          placeholder="请输入目的地点"
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
          v-hasPermi="['oper:product:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['oper:product:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['oper:product:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['oper:product:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="productList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="产品名称" align="center" prop="productName" />
     <!-- <el-table-column label="产品编号" align="center" prop="productCode" />-->
      <el-table-column label="线路类型" align="center" prop="lineType" :formatter="lineTypeFormat" />
      <el-table-column label="发车站点" align="center" prop="startSiteId" />
      <el-table-column label="到达站点" align="center" prop="endSiteId" />
      <el-table-column label="起步价格" align="center" prop="startingPrice" />
      <el-table-column label="接送起步距离" align="center" prop="startingDistance" />
      <el-table-column label="超出价格(元/公里)" align="center" prop="outOfPrice" />
      <el-table-column label="是否上架" align="center" prop="isSale" :formatter="isSaleFormat"/>
      <el-table-column label="计价规则" align="center" prop="bcChargeType" :formatter="bcChargeTypeFormat"/>
      <!-- <el-table-column label="拼车价格(元/人)" align="center" prop="pchePrice" />-->
      <!--<el-table-column label="包车价格(元/人)" align="center" prop="bchePrice" />-->
      <el-table-column label="运行时间" align="center" prop="runTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="300">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['oper:product:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['oper:product:remove']"
          >删除</el-button>
          <el-button v-if="scope.row.bcChargeType=='1'?true:false"
            size="mini"
            type="text"
            icon="el-icon-setting"
            @click="setPrice(scope.row)"
            v-hasPermi="['oper:productPrice:add']"
          >设置一口价</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-setting"
            @click="setSite(scope.row)"
            v-hasPermi="['oper:productSite:add']"
          >设置站点</el-button>
          <!--<el-button
            size="mini"
            type="text"
            icon="el-icon-zoom-in"
            @click="goProductDetail(scope.row)"
            v-hasPermi="['oper:product:edit']"
          >预览</el-button>-->
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
    <!-- 添加或修改产品价格信息对话框 -->
    <el-dialog :title="title" :visible.sync="openSetPrice" width="1200px" append-to-body>
      <div>
        产品名称<span style="margin-left: 30px">{{productName}}</span>
      </div>
      <div style="margin-top: 20px">
        计价规则<span style="margin-left: 30px">{{bcChargeType=='1'?'一口价':'里程计费'}}</span>
      </div>
      <div style="text-align: right">
        <el-button type="primary" @click.native.prevent="addPriceRow(productPriceData)" style="margin-bottom: 20px">添加一行</el-button>
      </div>

      <el-table
      :data="productPriceData"
      border
      style="width: 100%">
        <el-table-column
          prop="carType"
          label="车型"
          width="400">
          <template slot-scope="scope">
            <el-select v-model="scope.row.carType" placeholder="请选择" style="width: 350px">
              <el-option
                v-for="dict in carTypeOptions"
                :key="dict.vcehicleTypeId+''"
                :label="dict.typeName +'_'+dict.passengerNum+'座'+'('+dict.remark+')'"
                :value="dict.vcehicleTypeId+''">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <!--<el-table-column
          prop="carSeatNo"
          label="座位数"
          width="150">
          <template slot-scope="scope">
            <el-input-number size="small" v-model="scope.row.carSeatNo" controls-position="right" @change="handleChange()" :min="1" :max="100">
            </el-input-number>
          </template>
        </el-table-column>-->
      <!--<el-table-column
        prop="highSpeedSetting"
        label="高速设置"
        width="120">
        <template slot-scope="scope">
          <el-select v-model="scope.row.highSpeedSetting" placeholder="请选择">
            <el-option
              v-for="item in highSpeedSettingOptions"
              :key="item.dictValue"
              :label="item.dictLabel"
              :value="item.dictValue">
            </el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column
        prop="pchePermission"
        label="可拼" width="100">
        <template slot-scope="scope">
          <el-select v-model="scope.row.pchePermission" placeholder="请选择">
            <el-option label="是" value="1">是</el-option>
            <el-option label="否" value="0">否</el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column
        prop="pcheTocPrice"
        label="散客拼车（元/座）" width="100">
        <template slot-scope="scope">
          <el-input v-model="scope.row.pcheTocPrice" placeholder="请输入内容"></el-input>
        </template>
      </el-table-column>
      <el-table-column
        prop="pcheTobPrice"
        label="B端拼车（元/座）" width="80">
        <template slot-scope="scope">
          <el-input v-model="scope.row.pcheTobPrice" placeholder="请输入内容"></el-input>
        </template>
      </el-table-column>
      <el-table-column
        prop="bchePermission"
        label="可包" width="100">
        <template slot-scope="scope">
          <el-select v-model="scope.row.bchePermission" placeholder="请选择">
            <el-option label="是" value="1">是</el-option>
            <el-option label="否" value="0">否</el-option>
          </el-select>
        </template>
      </el-table-column>-->

        <el-table-column
          prop="bcheTocPrice"
          label="非协议用户包车一口价" width="250">
          <template slot-scope="scope">
            <el-input v-model="scope.row.bcheTocPrice" placeholder="请输入内容"></el-input>
          </template>
        </el-table-column>
        <el-table-column
          prop="bcheTobPrice"
          label="协议用户包车一口价" >
          <template slot-scope="scope">
            <el-input v-model="scope.row.bcheTobPrice" placeholder="请输入内容"></el-input>
          </template>
        </el-table-column>
      <el-table-column
        fixed="right"
        label="操作">
        <template slot-scope="scope">
          <el-button
            @click.native.prevent="deleteRow(scope.$index, productPriceData)"
            type="text"
            size="small">
            移除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
      <div style="text-align: right">
        <el-button type="primary"  @click.native.prevent="savePriceData(productPriceData)" style="margin-bottom: 20px"> 保存</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改产品站点信息对话框 -->
    <el-dialog :title="title" :visible.sync="openSetSite" width="700px" append-to-body>
      <div>
        产品名称<span style="margin-left: 30px">{{productName}}</span>
      </div>
      <div style="text-align: right">
        <el-button type="primary" @click.native.prevent="addSiteRow(productSiteData)" style="margin-bottom: 20px">添加一行</el-button>
      </div>
      <el-table
        :data="productSiteData"
        border
        style="width: 100%">
        <el-table-column
          prop="siteInfo"
          label="站点"
          width="150">
          <template slot-scope="scope">
            <el-select value-key="siteId" @change="onSelectedSite($event,scope)" v-model="scope.row.siteInfo" filterable placeholder="请选择">
              <el-option
                v-for="item in siteOptions"
                :key="item.siteId"
                :label="item.siteName"
                :value="item">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column
          prop="orderNum"
          label="排序"
          width="150">
          <template slot-scope="scope">
            <el-input-number size="small" v-model="scope.row.orderNum" controls-position="right" @change="handleChange()" :min="1" :max="100">
            </el-input-number>
          </template>
        </el-table-column>
        <el-table-column
          prop="siteType"
          label="类型" width="140">
          <template slot-scope="scope">
            <el-select v-model="scope.row.siteType" placeholder="请选择">
              <el-option label="上车站点" :value= 1>上车站点</el-option>
              <el-option label="下车站点" :value= 2>下车站点</el-option>
              <el-option label="隐藏站点" :value= 3>隐藏站点</el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column
          prop="useSiteFence"
          label="使用站点围栏" width="120">
          <template slot-scope="scope">
            <el-select  v-model="scope.row.useSiteFence" placeholder="请选择">
              <el-option label="使用" value="1">使用</el-option>
              <el-option label="不使用" value="0">不使用</el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作">
          <template slot-scope="scope">
            <el-button
              @click.native.prevent="deleteRow(scope.$index, productSiteData)"
              type="text"
              size="small">
              移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="text-align: right">
        <el-button type="primary" @click.native.prevent="saveSiteData(productSiteData)" style="margin-bottom: 20px"> 保存</el-button>
      </div>
    </el-dialog>
    <!-- 添加或修改产品信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="产品名称" prop="productName">
              <el-input v-model="form.productName" placeholder="请输入产品名称" />
            </el-form-item>
          </el-col>
          <!--<el-col :span="12">
            <el-form-item label="产品代码" prop="productCode">
            <el-input v-model="form.productCode" placeholder="请输入产品编号" />
          </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品图片(pc)" prop="productPicTob">
              <el-upload
                :headers="headers"
                :multiple="true"
                class="upload-demo"
                :action="uploadImgUrl"
                :on-preview="handlePreview"
                :on-remove="handleRemove1"
                :on-success="handleSuccess1"
                list-type="picture"
                :file-list="files1"
                accept='.jpg,.jpeg,.png,.gif'>
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
              </el-upload>
              <el-dialog :visible.sync="dialogVisible">
                <img width="100%" :src="dialogImageUrl" alt="">
              </el-dialog>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品图片(移动端)" prop="productPicToc">
              <el-upload
                :headers="headers"
                :multiple="true"
                class="upload-demo"
                :action="uploadImgUrl"
                :on-preview="handlePreview"
                :on-remove="handleRemove2"
                :on-success="handleSuccess2"
                list-type="picture"
                :file-list="files2"
                accept='.jpg,.jpeg,.png,.gif'>
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
              </el-upload>
              <el-dialog :visible.sync="dialogVisible">
                <img width="100%" :src="dialogImageUrl" alt="">
              </el-dialog>
            </el-form-item>
          </el-col>-->
          <el-col :span="12">
            <el-form-item label="归属部门" prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" placeholder="请选择归属部门" size="small"/>
            </el-form-item>
          </el-col>
          <!--<el-col :span="24" style="margin-bottom: 60px">
            <el-form-item label="产品详情" prop="productDetail">
              <Editor v-model="form.productDetail" />
            </el-form-item>
          </el-col>-->
          <el-col :span="12">
            <el-form-item label="线路类型">
              <el-select v-model="form.lineType" placeholder="请选择线路类型">
                <el-option
                  v-for="dict in lineTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!--<el-col :span="12">
            <el-form-item label="发车点" prop="startSiteId">
              <el-input v-model="form.startSiteId" placeholder="请输入发车点" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="目的地点" prop="endSiteId">
              <el-input v-model="form.endSiteId" placeholder="请输入目的地点" />
            </el-form-item>
          </el-col>-->
          <el-col :span="12">
            <el-form-item label="运行时间(分)" prop="runTime">
              <el-input type="number" min="0" v-model="form.runTime" placeholder="请输入运行时间(分)" />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="里程(公里)" prop="mileage">
              <el-input type="number" min="0" v-model="form.mileage" placeholder="请输入里程(公里)" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="拼座标准价格(元/人)" prop="startingPrice">
              <el-input type="number" min="0" v-model="form.startingPrice" placeholder="请输入拼座价格(元)" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="拼座接送起步距离(公里)" prop="startingDistance">
              <el-input type="number" min="0" v-model="form.startingDistance" placeholder="请输入起步距离(公里)" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="拼座超出接送起步距离价格(元/公里)" prop="outOfPrice">
              <el-input type="number" min="0" v-model="form.outOfPrice" placeholder="请输入超出价格(元/公里)" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否上架" prop="isSale">
              <el-radio-group v-model="form.isSale">
                <el-radio  label="1">上架销售</el-radio>
                <el-radio  label="0">下架停售</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="包车计价规则" prop="bcChargeType">
              <el-radio-group v-model="form.bcChargeType">
                <el-radio  label="1">一口价</el-radio>
                <!--<el-radio  label="2">里程价</el-radio>-->
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="首班发车时间" prop="beginTime">
              <el-time-picker
                value-format="HH:mm"
                arrow-control
                v-model="form.beginTime"
                :picker-options="{
                  format:'HH:mm'
                }"
                placeholder="选择时间">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="末班发车时间" prop="endTime" >
              <el-time-picker
                value-format="HH:mm"
                arrow-control
                v-model="form.endTime"
                :picker-options="{
                  format:'HH:mm'
                }"
                placeholder="选择时间">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发车频率(每几个小时发一班)" prop="departureFrequency" >
              <el-input-number v-model="form.departureFrequency" :precision="0" :step="1" :max="24" :min="1"></el-input-number>
            </el-form-item>
          </el-col>



          <!--<el-col :span="12">
            <el-form-item label="拼车价格(元/人)" prop="pchePrice">
              <el-input v-model="form.pchePrice" placeholder="请输入拼车价格(元/人)" />
            </el-form-item>
          </el-col>-->
          <!--<el-col :span="12">
            <el-form-item label="包车价格(元/人)" prop="bchePrice">
              <el-input v-model="form.bchePrice" placeholder="请输入包车价格(元/人)" />
            </el-form-item>
          </el-col>-->

         <!-- <el-col :span="24" style="margin-bottom: 20px">
            <el-form-item label="预订须知" prop="bookingGuide">
              <Editor v-model="form.bookingGuide" />
            </el-form-item>
          </el-col>-->
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
import { listProduct, getProduct, delProduct, addProduct, updateProduct, exportProduct } from "@/api/oper/product";
import { listByProductId,addProductPrice} from "@/api/oper/productPrice";
import { listAllSite } from "@/api/oper/site";
import { listByProductId as listSiteByProductId,addProductSite} from "@/api/oper/productSite";
import Editor from '@/components/Editor';
import { getToken } from '@/utils/auth'
import {getCarType } from "@/api/oper/vehicle";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
export default {
  name: "Product",
  components: {
    Editor,
    Treeselect
  },
  data() {
    return {
      bcChargeType:'',
      deptOptions:[],
      siteOptions: [],
      productId:'',
      productName:'',
      highSpeedSettingOptions:[],
      carTypeOptions:[],
      num: 10,
      //产品价格
      productPriceData: [],
      //产品站点
      productSiteData:[],
      // 线路类型字典
      lineTypeOptions: [],
      //pc文件展示
      files1:[],
      //c端图片展示
      files2:[],
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      // 上传的图片服务器地址
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      dialogImageUrl: '',
      dialogVisible: false,
      //存入数据pc库图片url
      fileUrl1: '',
      //存入数据C端库图片url
      fileUrl2: '',
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
      // 产品信息表格数据
      productList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openSetPrice:false,
      openSetSite:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productName: undefined,
        productCode: undefined,
        productPicTob: undefined,
        productPicToc: undefined,
        productDetail: undefined,
        lineType: undefined,
        startSiteId: undefined,
        endSiteId: undefined,
        runTime: undefined,
        mileage: undefined,
        bookingGuide: undefined,
        startingDistance: undefined,
        startingPrice: undefined,
        outOfPrice: undefined,
        createId: undefined,
      },
      // 表单参数
      form: {
      },
      // 表单校验
      rules: {
        productName: [
          {required: true, message: "产品名称不可为空", trigger: "blur"},
        ],
        startingDistance: [
          {required: true, message: "接送起步距离不可为空", trigger: "blur"},
        ],
        startingPrice:[
          {required: true, message: "接送起步价格不可为空", trigger: "blur"},
        ],
        outOfPrice:[
          {required: true, message: "超范围价格不可为空", trigger: "blur"},
        ],
        deptId:[
          {required: true, message: "不可为空", trigger: "blur"},
        ],
        beginTime:[
          {required: true, message: "不可为空", trigger: "blur"},
        ],
        endTime:[
          {required: true, message: "不可为空", trigger: "blur"},
        ],
        bcChargeType:[
          {required: true, message: "不可为空", trigger: "blur"},
        ],
        departureFrequency:[
          {required: true, message: "不可为空", trigger: "blur"},
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getDicts("line_type").then(response => {
      this.lineTypeOptions = response.data;
    });
    this.getDicts("high_speed_setting").then(response => {
      this.highSpeedSettingOptions = response.data;
    });
    this.getDicts("car_type").then(response => {
      this.carTypeOptions = response.data;
    });


  },
  methods: {
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    getCarType() {
      getCarType().then(response => {
        console.log('车辆信息:',response.data)
        this.carTypeOptions = response.data;
      });
    },
    handleChange(value) {
      console.log(value);
    },
    deleteRow(index, rows) {
      console.log(index, rows)
      rows.splice(index, 1);
    },
    addPriceRow(rows){
      let item = {bchePermission: "1",
        bcheTobPrice: "",
        bcheTocPrice: "",
        carSeatNo: 10,
        highSpeedSetting: "1",
        pchePermission: "1",
        pcheTobPrice: "",
        pcheTocPrice: ""}
      rows.splice(rows.length, 0,item);
    },

    addSiteRow(rows){
      let item = {useSiteFence:'1'}
      rows.splice(rows.length, 0,item);
    },
    //保存价格
    savePriceData(data){
      addProductPrice(data,this.productId).then(response =>{
        if (response.code === 200) {
          this.msgSuccess("保存成功");
          this.openSetPrice = false;
        } else {
          this.msgError(response.msg);
        }
      });
    },
    //保存产品站点
    saveSiteData(data){
      for (let i=0;i<data.length;i++){
        data[i].siteInfoId = data[i].siteInfo.siteId
        if (!data[i].siteInfo.siteFence) {
          data[i].useSiteFence='0'
        }
      }
      console.log(data)
      addProductSite(data,this.productId).then(response =>{
        if (response.code === 200) {
          this.msgSuccess("保存成功");
          this.openSetSite = false;
          this.handleQuery();
        } else {
          this.msgError(response.msg);
        }
      });
    },
    // 富文本图片上传前
    quillImgBefore(file) {
      let fileType = file.type;
      if(fileType === 'image/jpeg' || fileType === 'image/png'){
        return true;
      }else {
        this.$message.error('请插入图片类型文件(jpg/jpeg/png)');
        return false;
      }
    },

    handleSuccess1(res, file,fileList) {
      // res为图片服务器返回的数据
      // 如果上传成功
      console.log("fileList",fileList)
      if (res.code == 200) {
        this.files1 = fileList;
      } else {
        this.$message.error("图片插入失败");
      }
    },
    handleSuccess2(res, file,fileList) {
      // res为图片服务器返回的数据
      // 如果上传成功
      if (res.code == 200) {
        this.files2 = fileList;
      } else {
        this.$message.error("图片插入失败");
      }
    },
    // 富文本图片上传失败
    uploadError() {
      // loading动画消失
      this.$message.error("图片插入失败");
    },
    // 覆盖默认的上传行为
    requestUpload(msg) {
      console.log(msg)
    },
    handleRemove1(file, fileList) {
      this.files1 = fileList;
      this.fileUrl1 = '';
    },
    handleRemove2(file, fileList) {
      this.files2 = fileList;
    },
    handlePreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
      console.log(file);
    },
    /** 查询产品信息列表 */
    getList() {
      this.loading = true;
      listProduct(this.queryParams).then(response => {
        this.productList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /*查询产品下的价格列表*/
    getPriceListByProductId(id){
      listByProductId(id).then(response =>{
        this.productPriceData = response.data;
      });
    },
    getSiteListByProductId(id){
      listSiteByProductId(id).then(response =>{
        this.productSiteData = response.data;
      });
    },
    getSiteAll(){
      listAllSite().then(response =>{
        this.siteOptions = response.data;
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
        productId: undefined,
        productName: undefined,
        productCode: undefined,
        productPicTob: undefined,
        productPicToc: undefined,
        productDetail: undefined,
        lineType: undefined,
        startSiteId: undefined,
        endSiteId: undefined,
        runTime: undefined,
        mileage: undefined,
        bookingGuide: undefined,
        startingDistance: undefined,
        startingPrice: undefined,
        outOfPrice: undefined,
        createId: undefined,
        createTime: undefined,
        pchePrice: undefined,
        bchePrice: undefined,
        isSale:"1",
        bcChargeType:'1'
      };
      this.files1=[],
      this.files2=[],
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
      this.ids = selection.map(item => item.productId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /**去产品详情页面*/
    goProductDetail(row){
      this.$router.push({
        path: '/productInfo/productDetail',
        name: 'productDetail',
        query: {
          productId: row.productId,
        }
      })
    },
    /**去设置价格页面**/
    setPrice(row){
      this.getCarType();
      this.productId = row.productId;
      this.productName = row.productName;
      this.bcChargeType = row.bcChargeType;
      this.getPriceListByProductId(row.productId)
      this.openSetPrice = true;
      this.title = "设置线路价格";
    },
    /**去设置站点页面**/
    setSite(row){
      this.productId = row.productId;
      this.productName = row.productName;
      this.getSiteAll()
      this.getSiteListByProductId(row.productId)
      this.openSetSite = true;
      this.title = "设置线路站点";

    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加产品信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const productId = row.productId || this.ids
      getProduct(productId).then(response => {
        this.form = response.data;
        this.productPicTob = response.data.productPicTob
        this.files1=[]
        if(this.productPicTob&&this.productPicTob.indexOf(",")!=-1){
          for (let i=0;i<this.productPicTob.split(',').length;i++) {
            this.files1.push({name:'文件'+(i+1),url:process.env.VUE_APP_BASE_API +this.productPicTob.split(',')[i]
              ,url1:this.productPicTob.split(',')[i]})
          }
        }

        this.files2=[]
        this.productPicToc = response.data.productPicToc
        if(this.productPicToc&&this.productPicToc.indexOf(",")!=-1){
          for (let i=0;i<this.productPicToc.split(',').length;i++) {
            this.files2.push({name:'文件'+(i+1),url:process.env.VUE_APP_BASE_API +this.productPicToc.split(',')[i]
              ,url1:this.productPicToc.split(',')[i]})
          }
        }
        this.open = true;
        this.title = "修改产品信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        console.log(this.files1,this.files2)
        this.fileUrl1=''
        this.fileUrl2=''
        for (let i=0;i<this.files1.length;i++) {
          if (this.files1[i].response) {
            this.fileUrl1 += this.files1[i].response.fileName+','
          }else{
            this.fileUrl1 += this.files1[i].url1+','
          }
        }
        for (let i=0;i<this.files2.length;i++) {
          if (this.files2[i].response) {
            this.fileUrl2 += this.files2[i].response.fileName+','
          }else{
            this.fileUrl2 += this.files2[i].url1+','
          }
        }

        this.form.productPicTob = this.fileUrl1.substring(0,this.fileUrl1.length-1)
        this.form.productPicToc = this.fileUrl2.substring(0,this.fileUrl2.length-1)
        if (valid) {
          if (this.form.productId != undefined) {
            updateProduct(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addProduct(this.form).then(response => {
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
      const productIds = row.productId || this.ids;
      this.$confirm('是否确认删除产品信息编号为"' + productIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delProduct(productIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有产品信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportProduct(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    lineTypeFormat(row, column) {
      return this.selectDictLabel(this.lineTypeOptions, row.lineType);
    },
    isSaleFormat(row, column) {
      if (row.isSale==1) {
        return "上架销售"
      }else{
        return "下架停售"
      }
    },
    bcChargeTypeFormat(row){
      if (row.bcChargeType=='1') {
        return "一口价"
      }else if (row.bcChargeType=='2') {
        return "里程收费"
      }else if (row.bcChargeType=='3') {
        return "时间收费"
      }
    }
  }
};
</script>
