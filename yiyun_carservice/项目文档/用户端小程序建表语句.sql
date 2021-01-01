-- ----------------------------
-- Table structure for c_error_log
-- ----------------------------
DROP TABLE IF EXISTS `c_error_log`;
CREATE TABLE `c_error_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `error_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '错误类型1支付订单 2支付订单回调',
  `error_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '第三方交互错误返回信息',
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统错误日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for c_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `c_evaluate`;
CREATE TABLE `c_evaluate`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户编号',
  `create_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '添加时间',
  `is_delete` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '默认为0',
  `evaluate_content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评价内容',
  `order_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `star_rating` int(2) NULL DEFAULT 5 COMMENT '星级评分',
  `shop_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商家Id',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户评价表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for c_order
-- ----------------------------
DROP TABLE IF EXISTS `c_order`;
CREATE TABLE `c_order`  (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `invite_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邀请码',
  `user_phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户手机号码',
  `order_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `order_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单类型(1.洗车 2.养护 3.维修 4.检测 5.驾培 6.保险 7.救援)',
  `order_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '订单状态(1已下单 2已取消 3已接单 4.服务中 5.已完成 6已退款)',
  `order_amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '订单金额',
  `use_points` decimal(15, 2) NULL DEFAULT 0.00 COMMENT '使用积分',
  `add_points` decimal(15, 2) NULL DEFAULT NULL COMMENT '赠送积分',
  `pay_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '支付类型',
  `discount_amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '折扣金额',
  `coupon_amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '优惠券金额',
  `points_amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '积分抵扣金额',
  `collect_amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '实收金额',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `create_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `cancel_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单取消时间',
  `book_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预约到店时间',
  `pay_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付时间',
  `refund_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_delete` int(1) NULL DEFAULT 0,
  `shop_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商家id',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户姓名',
  `car_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车牌号',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `rescue_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一键救援人员姓名',
  `rescue_mobile` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一键救援人员电话号码',
  `rescue_car_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '救援车辆车牌号码',
  `longitude` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户所在位置经度',
  `latitude` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户所在位置纬度',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `orderindex`(`order_code`) USING BTREE,
  INDEX `ordercreattime`(`create_time`) USING BTREE,
  INDEX `ordertype`(`order_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for c_order_goods
-- ----------------------------
DROP TABLE IF EXISTS `c_order_goods`;
CREATE TABLE `c_order_goods`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `product_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品编号',
  `product_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `product_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品备注(商家端上传的图片链接)',
  `discount_amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '折扣金额',
  `coupon_amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '优惠券金额',
  `points_amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '积分抵扣金额',
  `collect_amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '实收金额',
  `order_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单类型(1.洗车 2.养护 3.维修 4.检测 5.驾培 6.保险 7.救援)',
  `pay_status` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '订单状态(1已下单 2已取消 3已接单 4.服务中 5.已支付 6已退款)',
  `pay_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付时间',
  `refund_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款时间',
  `product_amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '产品金额',
  `verify_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单验证码',
  `use_points` decimal(15, 2) NULL DEFAULT 0.00 COMMENT '使用积分',
  `add_points` decimal(15, 2) NULL DEFAULT NULL COMMENT '赠送积分',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `orderbuscode`(`order_code`) USING BTREE,
  INDEX `orderbusstatus`(`pay_status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单商品明细' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for c_points_history
-- ----------------------------
DROP TABLE IF EXISTS `c_points_history`;
CREATE TABLE `c_points_history`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `points` decimal(15, 2) NULL DEFAULT 0.00 COMMENT '积分金额',
  `product_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `chang_type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '变动类型(1订单增加 2订单抵扣 3退款返还 4评价增加 5取消返还)',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户编号',
  `change_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '变动时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '积分变动明细' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for c_refund_log
-- ----------------------------
DROP TABLE IF EXISTS `c_refund_log`;
CREATE TABLE `c_refund_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `order_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单类型(1.洗车 2.养护 3.维修 4.检测 5.驾培 6.保险 7.救援)',
  `refund_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '退款状态 1未退款 2已申请退款 3已退款 4退款失败',
  `order_amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '订单金额',
  `refund_amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '退款金额',
  `releation_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联商品表Id',
  `error_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '第三方交互错误返回信息',
  `refund_error` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '退款发生错误原因',
  `refund_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款类型:系统 人工',
  `fee_rate` decimal(10, 2) NULL DEFAULT NULL COMMENT '费率或手续费',
  `refund_order_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款订单号',
  `pay_type` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '支付类型:1.微信原生支付 2.银联支付',
  `refund_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款时间',
  `create_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '退款日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for c_relation_car
-- ----------------------------
DROP TABLE IF EXISTS `c_relation_car`;
CREATE TABLE `c_relation_car`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `user_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `car_no` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车牌号',
  `is_default` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否默认 1是 2否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户车牌号信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for c_shop
-- ----------------------------
DROP TABLE IF EXISTS `c_shop`;
CREATE TABLE `c_shop`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '登录用户id',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '所属部门id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商户名称',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地址',
  `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号码',
  `tel` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机或座机',
  `work_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '工作内容',
  `work_time` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作息时间',
  `lon_lat` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经纬度',
  `score` float NULL DEFAULT 4.8 COMMENT '评分',
  `num` bigint(20) NULL DEFAULT 0 COMMENT '评分人数',
  `points` int(11) NULL DEFAULT NULL COMMENT '信用积分',
  `is_delete` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '1:删除  0：未删除',
  `is_show` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '1：上架展示  2：下架',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `cover_pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列表缩略图',
  `shop_top_pics` varchar(1600) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '店铺轮播图',
  `self_shop` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '1:自营店 2：加盟店',
  `shop_order` int(11) NULL DEFAULT 0 COMMENT '默认排序',
  `service_type` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务类型（字典）',
  `charge_person` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `shop_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '店铺类型（字典）驾培，维修，检测，施救',
  `shop_tags` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商铺标签（字典多选），可搜索',
  `can_use_points` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '是否支持积分币抵扣 1是 2否',
  `service_range` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '服务范围',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '店铺商户' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for c_shop_goods
-- ----------------------------
DROP TABLE IF EXISTS `c_shop_goods`;
CREATE TABLE `c_shop_goods`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
  `goods_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品类型：字典 0:洗车，1：养护 2:检测线',
  `goods_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `pay_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付价格',
  `price` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门市价格',
  `service_standard` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务标准',
  `is_delete` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0：未删除  1：已删除',
  `sale_num` int(11) NULL DEFAULT 0 COMMENT '售卖数量',
  `top_pic_url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '产品头图',
  `pic_url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '产品图片',
  `on_shelf` int(1) NULL DEFAULT 1 COMMENT '1:上架   2：下架',
  `goods_order` int(4) NULL DEFAULT 0 COMMENT '产品排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '产品' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for c_user_info
-- ----------------------------
DROP TABLE IF EXISTS `c_user_info`;
CREATE TABLE `c_user_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `user_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `sex` int(1) NULL DEFAULT NULL COMMENT '0未知 1男性 2女性',
  `idcard` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `head_img` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `vip_level` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员等级(1.普通会员 2.Plus会员 3.VIP会员 4.车会员)',
  `promote_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推广码(推广用户字段不为空)',
  `invite_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邀请码',
  `session_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `source` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户来源(1.普通用户 2.推广用户)',
  `points` decimal(15, 2) NULL DEFAULT 0.00 COMMENT '积分余额',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'AAD2647F4BBF525F97FB3883DD2835B5415C6DAC524B85C10AF771B5' COMMENT '登录密码',
  `pay_password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付密码',
  `is_delete` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '默认为0',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `car_no` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车牌号',
  `nick_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `openid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'C端用户信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for c_userlevel_history
-- ----------------------------
DROP TABLE IF EXISTS `c_userlevel_history`;
CREATE TABLE `c_userlevel_history`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户编号',
  `user_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `vip_level` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员等级(1.普通会员 2.Plus会员 3.VIP会员 4.车会员)',
  `invite_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邀请码',
  `change_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '变动时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员等级变动明细' ROW_FORMAT = Compact;