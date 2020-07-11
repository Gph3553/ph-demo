package com.example.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 资源目录表
 *
 * @author chensong
 * @date 2020-03-03 14:57:40
 */
@TableName("resources_catalogue")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Data
public class ResourcesCatalogue implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.INPUT)
	private Long id;
	/**
	 * 租户id
	 */
	private Long tenantId;
	/**
	 * 创建者
	 */
	private Long creator;
	/**
	 * 修改者
	 */
	private Long modifier;
	/**
	 * 修改时间
	 */
	private LocalDateTime modifyTm;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTm;
	/**
	 * 资源提供方部门id
	 */
	private Long organizationId;
	/**
	 * 资源提供方部门名称
	 */
	private String organizationName;
	/**
	 * 资源名称
	 */
	private String resourceName;
	/**
	 * 资源代码
	 */
	private String resourceCode;
	/**
	 * 来源
	 */
	private String sources;
	/**
	 * 资源简介
	 */
	private String resourceDesc;
	/**
	 * 信息资源格式类型
	 */
	private String infoSourceType;
	/**
	 * 资源拼音
	 */
	private String resourcePinyin;
	/**
	 * 信息资源格式分类
	 */
	private String infoSourceSort;
	/**
	 * 其他类型资源格式描述
	 */
	private String otherDescription;
	/**
	 * 是否纳入考核
	 */
	private Boolean isAssessment;
	/**
	 * 资源分类表id
	 */
	private Long classificationId;
	/**
	 * 主题分类id(主题分类存储在资源分类表)
	 */
	private Long themeId;
	/**
	 * 是否是草稿状态
	 */
	private Boolean draftStatus;
	/**
	 * 更新周期
	 */
	private String renewCycle;
	/**
	 * 共享方式分类
	 */
	private String shareSort;
	/**
	 * 共享属性
	 */
	private String shareAttr;
	/**
	 * 共享方式类型(只有资源共享才有此属性) 0为数据库 1为服务 2为文件
	 */
	private String shareType;
	/**
	 * 数据来源 1为目录编制 2为资源共享 3为资源目录管理
	 */
	private String dataSources;
	/**
	 * 是否删除
	 */
	private Boolean delStatus;
	/**
	 * 关联类目及名称
	 */
	private String categoryName;
	/**
	 * 提供方内部部门
	 */
	private String interdepartName;
	/**
	 * 是否标准资源 0-否 1-是
	 */
	private Boolean standardStatus;

	/**
	 * 是否要进行发布
	 */
	private Boolean operateType;
	/**
	 * 发布日期
	 */
	private LocalDateTime pushDate;
	/**
	 * 资源提供方代码
	 */
	private String provideCode;
	/**
	 * 资源是否可以修改共享方式
	 */
	private Boolean accessModifyStatus;
	/**
	 * 是否在资源目录展示，默认为1
	 */
	private Boolean isShowResources;

	/**
	 * 共享条件
	 */
	private String shareReason;
	/**
	 * 接入方式 0-数据库 1-服务 2-文件
	 */
	private Integer accessType;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 是否接入 0-否 1-是
	 */
	private Boolean accessStatus;

	/**
	 * 接入时间
	 */
	private LocalDateTime accessDate;
	/**
	 * 开放属性
	 */
	private String openType;
	/**
	 * 审核状态(目前用于资源接入)
	 */
	private String reviewStatus;

	/**
	 * 是否发布
	 */
	private Integer isPush;

	/**
	 * 资源编码
	 * @param model
	 * @return
	 */
     private String resourcesEncode;

   public String toJson(){
	   try {
		   String s = new ObjectMapper().writeValueAsString(this);
		   return s;
	   } catch (JsonProcessingException e) {
	   }
	   return "{}";
   }

   public static Optional<ResourcesCatalogue> readValue(String json){
   	   if(StringUtils.isEmpty(json)){
   	   	return Optional.empty();
	   }
	   try {
		   ResourcesCatalogue resourcesCatalogue = new ObjectMapper().readValue(json, ResourcesCatalogue.class);
		   return Optional.of(resourcesCatalogue);
	   } catch (JsonProcessingException e) {
		   e.printStackTrace();
	   }
	   return Optional.empty();
   }

}
