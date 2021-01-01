package com.carservice.project.customer.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

/**
 * C端用户信息对象 c_user_info
 * 
 * @author carservice
 * @date 2020-12-18
 */
public class CUserInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 用户姓名 */
    @Excel(name = "用户姓名")
    private String userName;

    /** 用户手机号 */
    @Excel(name = "用户手机号")
    private String userPhone;

    /** 0未知 1男性 2女性 */
    @Excel(name = "0未知 1男性 2女性")
    private Integer sex;

    /** 身份证号码 */
    @Excel(name = "身份证号码")
    private String idcard;

    /** 头像 */
    @Excel(name = "头像")
    private String headImg;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 会员等级(1.普通会员 2.Plus会员 3.VIP会员 4.车会员) */
    @Excel(name = "会员等级(1.普通会员 2.Plus会员 3.VIP会员 4.车会员)")
    private String vipLevel;

    /** 推广码(推广用户字段不为空) */
    @Excel(name = "推广码(推广用户字段不为空)")
    private String promoteCode;

    /** 邀请码 */
    @Excel(name = "邀请码")
    private String inviteCode;

    /** $column.columnComment */
    @Excel(name = "邀请码")
    private String sessionKey;

    /** 用户来源(1.普通用户 2.推广用户) */
    @Excel(name = "用户来源(1.普通用户 2.推广用户)")
    private String source;

    /** 积分余额 */
    @Excel(name = "积分余额")
    private Double points;

    /** 登录密码 */
    @Excel(name = "登录密码")
    private String password;

    /** 支付密码 */
    @Excel(name = "支付密码")
    private String payPassword;

    /** 默认为0 */
    @Excel(name = "默认为0")
    private Integer isDelete;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String carNo;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;

    /** $column.columnComment */
    @Excel(name = "昵称")
    private String openid;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setUserPhone(String userPhone) 
    {
        this.userPhone = userPhone;
    }

    public String getUserPhone() 
    {
        return userPhone;
    }
    public void setSex(Integer sex) 
    {
        this.sex = sex;
    }

    public Integer getSex() 
    {
        return sex;
    }
    public void setIdcard(String idcard) 
    {
        this.idcard = idcard;
    }

    public String getIdcard() 
    {
        return idcard;
    }
    public void setHeadImg(String headImg) 
    {
        this.headImg = headImg;
    }

    public String getHeadImg() 
    {
        return headImg;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setVipLevel(String vipLevel) 
    {
        this.vipLevel = vipLevel;
    }

    public String getVipLevel() 
    {
        return vipLevel;
    }
    public void setPromoteCode(String promoteCode) 
    {
        this.promoteCode = promoteCode;
    }

    public String getPromoteCode() 
    {
        return promoteCode;
    }
    public void setInviteCode(String inviteCode) 
    {
        this.inviteCode = inviteCode;
    }

    public String getInviteCode() 
    {
        return inviteCode;
    }
    public void setSessionKey(String sessionKey) 
    {
        this.sessionKey = sessionKey;
    }

    public String getSessionKey() 
    {
        return sessionKey;
    }
    public void setSource(String source) 
    {
        this.source = source;
    }

    public String getSource() 
    {
        return source;
    }
    public void setPoints(Double points) 
    {
        this.points = points;
    }

    public Double getPoints() 
    {
        return points;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }
    public void setPayPassword(String payPassword) 
    {
        this.payPassword = payPassword;
    }

    public String getPayPassword() 
    {
        return payPassword;
    }
    public void setIsDelete(Integer isDelete) 
    {
        this.isDelete = isDelete;
    }

    public Integer getIsDelete() 
    {
        return isDelete;
    }
    public void setCarNo(String carNo) 
    {
        this.carNo = carNo;
    }

    public String getCarNo() 
    {
        return carNo;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }
    public void setOpenid(String openid) 
    {
        this.openid = openid;
    }

    public String getOpenid() 
    {
        return openid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userName", getUserName())
            .append("userPhone", getUserPhone())
            .append("sex", getSex())
            .append("idcard", getIdcard())
            .append("headImg", getHeadImg())
            .append("address", getAddress())
            .append("vipLevel", getVipLevel())
            .append("promoteCode", getPromoteCode())
            .append("inviteCode", getInviteCode())
            .append("sessionKey", getSessionKey())
            .append("source", getSource())
            .append("points", getPoints())
            .append("password", getPassword())
            .append("payPassword", getPayPassword())
            .append("isDelete", getIsDelete())
            .append("remark", getRemark())
            .append("carNo", getCarNo())
            .append("nickName", getNickName())
            .append("openid", getOpenid())
            .toString();
    }
}
