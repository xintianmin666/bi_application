package com.yiyun.yiyuncarservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description
 * @Author  hxx
 * @Date 2020-10-29
 */

@Data
@EqualsAndHashCode(callSuper=false)
@Table ( name ="ahyt_promote" )
public class AhytPromote  implements Serializable {


   @TableId(value = "id",type = IdType.AUTO)
	private Integer id;

   	@TableField("promote_code")
	private String promoteCode;

   	@TableField("promote_name")
	private String promoteName;

   	@TableField("promote_idcard")
	private String promoteIdcard;

   	@TableField("promote_position")
	private String promotePosition;

   	@TableField("code_url")
	private String codeUrl;

   	@TableField("ticket")
	private String ticket;

	/**
	 * 1:是公司；2：个人
	 */
   	@TableField("is_company")
	private Integer isCompany;

	/**
	 * 来源
	 */
   	@TableField("source")
	private Integer source;

	/**
	 * 公司名称
	 */
   	@TableField("company")
	private String company;

	/**
	 * 1:是代理人   2：不是代理人
	 */
   	@TableField("is_agent")
	private Integer isAgent;

}
