package com.example.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 资源目录分类表
 *
 * @author chensong
 * @date 2020-03-03 14:57:40
 */
@TableName("resources_classification")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Data
public class ResourcesClassification implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId(value = "id", type = IdType.INPUT)
	private Long id;
	/**
	 * 资源分类名称
	 */
	private String name;
	/**
	 * 父级id(没有父级则为0)
	 */
	private Long parentId;
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
	 * 是否能够修改
	 */
	private Boolean isCanModify;
	/**
	 * 类型 类型 基础信息 BASIC_CLASS, 部门信息 ORGAN_CLASS, 区县信息 DISTRICT, 国家资源信息 COUNTRY, 主题分类 THEME
	 */
	private String type;
	/**
	 * 是否删除
	 */
	private Boolean delStatus;
	/**
	 * 对应的部门id（只有为部门资源、国家资源和区县资源时需要次字段，否则为0）
	 */
	private Long organizationId;

	/**
	 * 根据此字段排序，小的在前
	 */
	private Long superOrg;
	/**
	 * 显示
	 */
	private Long orgIndex;

	/**
	 * 部门对应图标code
	 */
	private String imageCode;



}
