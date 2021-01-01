package com.yiyun.yiyuncarservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description
 * @Author hxx
 * @Date 2020-12-14
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "c_shop_goods")
public class CShopGoods implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private String updateTime;

    @TableField("update_by")
    private String updateBy;

    /**
     * 店铺id
     */
    @TableField("shop_id")
    private Long shopId;

    /**
     * 商品类型：字典 0:洗车，1：养护 2:检测线
     */
    @TableField("goods_type")
    private String goodsType;

    /**
     * 产品名称
     */
    @TableField("goods_name")
    private String goodsName;

    /**
     * 支付价格
     */
    @TableField("pay_price")
    private BigDecimal payPrice;

    /**
     * 门市价格
     */
    @TableField("price")
    private String price;

    /**
     * 服务标准
     */
    @TableField("service_standard")
    private String serviceStandard;

    /**
     * 0：未删除  1：已删除
     */
    @TableField("is_delete")
    private String isDelete;

    /**
     * 售卖数量
     */
    @TableField("sale_num")
    private Integer saleNum;

    /**
     * 产品头图
     */
    @TableField("top_pic_url")
    private String topPicUrl;

    /**
     * 产品图片
     */
    @TableField("pic_url")
    private String picUrl;

    /**
     * 1:上架   2：下架
     */
    @TableField("on_shelf")
    private Integer onShelf;

    /**
     * 产品排序
     */
    @TableField("goods_order")
    private Integer goodsOrder;

}
