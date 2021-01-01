<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="城市id" prop="cityId">
        <el-input
          v-model="queryParams.cityId"
          placeholder="请输入城市id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="站点代码" prop="siteCode">
        <el-input
          v-model="queryParams.siteCode"
          placeholder="请输入站点代码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="站点名称" prop="siteName">
        <el-input
          v-model="queryParams.siteName"
          placeholder="请输入站点名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="站点拼音" prop="sitePy">
        <el-input
          v-model="queryParams.sitePy"
          placeholder="请输入站点拼音"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="站点拼音首字母" prop="siteSp">
        <el-input
          v-model="queryParams.siteSp"
          placeholder="请输入站点拼音首字母"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="站点类别" prop="siteType">
        <el-select v-model="queryParams.siteType" placeholder="请选择站点类别" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="站点等级" prop="siteLevel">
        <el-input
          v-model="queryParams.siteLevel"
          placeholder="请输入站点等级"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="站点半径单位米" prop="siteRadius">
        <el-input
          v-model="queryParams.siteRadius"
          placeholder="请输入站点半径单位米"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="站点电子栅栏" prop="createId">
        <el-input
          v-model="queryParams.createId"
          placeholder="请输入站点电子栅栏"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="站点电子栅栏" prop="updateId">
        <el-input
          v-model="queryParams.updateId"
          placeholder="请输入站点电子栅栏"
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
          v-hasPermi="['oper:site:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['oper:site:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['oper:site:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['oper:site:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="siteList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="siteId" />
      <el-table-column label="所属城市" align="center" prop="cityName" />
      <el-table-column label="站点代码" align="center" prop="siteCode" />
      <el-table-column label="站点名称" align="center" prop="siteName" />
      <el-table-column label="站点拼音" align="center" prop="sitePy" />
      <el-table-column label="站点拼音首字母" align="center" prop="siteSp" />
      <el-table-column label="站点类别" align="center" prop="siteType" :formatter="siteTypeFormat"/>
      <el-table-column label="站点等级" align="center" prop="siteLevel" :formatter="siteLevelFormat"/>
      <el-table-column label="站点经度" align="center" prop="lng" />
      <el-table-column label="站点纬度" align="center" prop="lat" />
      <el-table-column label="站点半径(米)" align="center" prop="siteRadius" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['oper:site:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['oper:site:remove']"
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

    <!-- 添加或修改城市站点对话框 -->
    <el-dialog :title="title" :visible.sync="open" @close='removeAll' :fullscreen="true" :close-on-click-modal="false" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <Map ref="map" style="margin-bottom: 20px" @lngLatInfoFromMap="getLngLatInfo" :mapInfo='mapInfo'></Map>
        <el-row>
          <el-col :span="4">
            <el-form-item label="所属城市" prop="cityId">
              <el-select v-model="form.cityId"  filterable
                         placeholder="请输入营运城市名称" >
                <el-option
                  v-for="item in cityList"
                  :key="item.operCityId"
                  :label="item.operCityName"
                  :value="item.operCityId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="站点代码" prop="siteCode">
              <el-input v-model="form.siteCode" placeholder="请输入站点代码" />
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="站点名称" prop="siteName">
              <el-input v-model="form.siteName" placeholder="请输入站点名称" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="站点拼音" prop="sitePy">
              <el-input v-model="form.sitePy" placeholder="请输入站点拼音" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="站点简拼" prop="siteSp">
              <el-input v-model="form.siteSp" placeholder="请输入站点拼音首字母" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="站点类别">
              <el-select v-model="form.siteType" placeholder="请选择站点类别">
                <el-option
                  v-for="dict in siteTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="站点等级">
              <el-select v-model="form.siteLevel" placeholder="请选择站点等级">
                <el-option
                  v-for="dict in siteLevelOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="经度" prop="lng">
              <el-input v-model="form.lng" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="纬度" prop="lat">
              <el-input v-model="form.lat"  readonly="readonly"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="扩展半径">
              <el-input @change="addSiteCircle($event)" v-model="form.siteRadius" :mapInfo='mapInfo' placeholder="请输入站点半径单位米" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item  prop="siteFence">
              <el-checkbox v-model="drawSiteFence"  @change="drawPolygon">自定义电子围栏</el-checkbox>
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
  import { listCity } from "@/api/oper/city";
  import { listSite, getSite, delSite, addSite, updateSite, exportSite } from "@/api/oper/site";
  import Map from '@/components/Map/index';
  import vPinyin from '@/utils/vue-py.js';
  export default {
    name: "Site",
    components: {
      Map
    },
    data() {
      return {
        cityList:[],
        lnglat:{
          lng:'',
          lat:''
        },
        siteTypeOptions:[],
        siteLevelOptions:[],
        siteFence:'',
        drawSiteFence:false,
        siteRadius:'',
        mapInfo:{
          siteRadius:'',
          lnglat:'',
          lng:'',
          lat:'',
          siteFence:[]
        },
        lng:'',
        lat:'',
        polygonArr:[],
        mouseTool:'',
        circle:'',
        siteName:'',
        placeSearch:null,
        markers:[],
        map:null,
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
        // 城市站点表格数据
        siteList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          cityId: undefined,
          siteCode: undefined,
          siteName: undefined,
          sitePy: undefined,
          siteSp: undefined,
          siteType: undefined,
          siteLevel: undefined,
          siteRadius: undefined,
          siteFence: undefined,
          createId: undefined,
          updateId: undefined,
        },
        // 查询参数
        cityQueryParams: {
          pageNum: 1,
          pageSize: 10000,
          isvaliable: '1'
        },
        // 表单参数
        form: {
        },
        // 表单校验
        rules: {
          cityId: [
            { required: true, message: "城市不能为空", trigger: "blur" },
          ],
        }
      };
    },
    watch:{
      /*form: {
        handler(newValue, oldValue) {
          console.log("站点改变",newValue)
          this.changeSiteName(newValue.siteName)
        },
        deep: true
      },*/
      'form.siteName'(newValue){
        console.log("站点改变",newValue)
        this.changeSiteName(newValue)
      },
      /*open(value){
        if (!value) {
          console.log("地图组件关闭")
          this.$refs.map.removeAll()
        }
      }*/

    },
    mounted () {
    },
    created() {
      this.getCityList()
      this.getList();
      this.getDicts("site_type").then(response => {
        this.siteTypeOptions = response.data;
      });
      this.getDicts("site_level").then(response => {
        this.siteLevelOptions = response.data;
      });
    },
    methods: {
      /* onSelectedCity(val){
         this.$refs.map.city = val.operCityName
         this.form.cityId =  val.operCityId
       },*/
      siteTypeFormat(row, column) {
        return this.selectDictLabel(this.siteTypeOptions, row.siteType);
      },
      siteLevelFormat(row, column) {
        return this.selectDictLabel(this.siteLevelOptions, row.siteLevel);
      },
      changeSiteName(val){
        if (val){
          this.form.sitePy = vPinyin.getPinyin(val)
          this.form.siteSp = vPinyin.getPinyinSx(val)
        }

      },
      drawPolygon(){
        console.log('设置自定义电子围栏')
        if (this.drawSiteFence) {
          this.$refs.map.addSiteMarkFlag = false
          this.$refs.map.drawPolygon()
        }else{
          this.$refs.map.addSiteMarkFlag = true
          this.$refs.map.removeMouseTool(true)
        }
      },
      getLngLatInfo(msg){
        console.log("子组件传过来的值",msg)
        /*this.lng = msg.lnglat.lng
        this.lat = msg.lnglat.lat*/
        this.form.siteName = msg.siteName
        this.form.lng = msg.lnglat.lng
        this.form.lat = msg.lnglat.lat
        console.log(this.$refs.form.model)
      },
      //添加点半径
      addSiteCircle(val){
        // 调用子组件的addSiteCircle方法
        console.log("调用子组件的addSiteCircle方法")
        this.$refs.map.addSiteCircle(this.form.lng,this.form.lat,val)
      },

      /** 查询城市站点列表 */
      getList() {
        this.loading = true;
        listSite(this.queryParams).then(response => {
          this.siteList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },

      getCityList(){
        listCity(this.cityQueryParams).then(response =>{
          this.cityList = response.rows;
        })
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          siteId: undefined,
          cityId: undefined,
          siteCode: undefined,
          siteName: undefined,
          sitePy: undefined,
          siteSp: undefined,
          siteType: undefined,
          siteLevel: undefined,
          siteRadius: undefined,
          siteFence: undefined,
          createId: undefined,
          createTime: undefined,
          updateId: undefined,
          updateTime: undefined
        };
        this.resetForm("form");
        this.siteFence=''
        this.drawSiteFence=false
        this.lng=''
        this.lat=''
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
        this.ids = selection.map(item => item.siteId)
        this.single = selection.length!=1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加城市站点";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const siteId = row.siteId || this.ids
        getSite(siteId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改城市站点";
          this.lnglat.lng = this.form.lng
          this.lnglat.lat = this.form.lat
          this.mapInfo.siteRadius = this.form.siteRadius
          this.mapInfo.lnglat = this.lnglat
          this.mapInfo.lng = this.form.lng
          this.mapInfo.lat = this.form.lat
          this.mapInfo.siteFence = this.form.siteFence.split(";")
        });

        setTimeout(() =>{
          this.$refs.map.addAll()
        },1000)
      },
      /** 提交按钮 */
      submitForm: function() {
        let polygonArr = this.$refs.map.polygonArr
        for (let i=0;i<polygonArr.length;i++) {
          this.siteFence += polygonArr[i].lng+","+polygonArr[i].lat+";"
        }
        this.siteFence = this.siteFence.substring(0,this.siteFence.length-1);
        this.form.siteFence = this.siteFence
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.siteId != undefined) {
              updateSite(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {
              addSite(this.form).then(response => {
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
        this.$refs.map.removeAll()
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const siteIds = row.siteId || this.ids;
        this.$confirm('是否确认删除城市站点编号为"' + siteIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delSite(siteIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有城市站点数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportSite(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      },
      removeAll(){
        this.$refs.map.removeAll()
      }
    }
  };
</script>
<style>
</style>
