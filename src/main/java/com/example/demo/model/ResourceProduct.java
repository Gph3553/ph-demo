package com.example.demo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResourceProduct {

    /**
     * 主键id
     */
    private Long id;

    private String name = "";

    private String resourcesName = "";
    /**
     * 部门ids
     */
    private Long organizationId = -1L;

    /**
     * 是否是资源(分为资源和目录)
     */
    private Boolean isResource = true;

    /**
     * 父节点id，顶级节点的父节点id为0
     */
    private Long parentId = -1L;

    /**
     * 是否
     */
    private Boolean isShowNum = false;

    /**
     * 接入数量/总数量
     */
    private String numInfo = "";

    /**
     * 类型
     */
    private NrdCatalogueType type;

    /**
     * 显示
     */
    private Long superOrg;

    /**
     * 根据此字段排序，
     */
    private Long orgIndex;

    /**
     * 租户id
     */
    private Long tenantId;
    /**
     * 父级名称集合
     */
    private List<String> parentNames = new ArrayList<>();

    /**
     * 子集名称集合
     */
    private List<String> childNames = new ArrayList<>();

    private List<Long> parentIds = new ArrayList<>();

    private List<Long> childIds = new ArrayList<>();

    /**
     * 接入状态
     */
    private Boolean accessStatus = false;

    /**
     * 共享类型
     */
    private String shareType = "";

    /**
     * 更新周期
     */
    private String renewCycle = "";

    /**
     * 共享格式类型
     */
    private String infoSourceSort = "";
    private Integer accessType = -1;

    /**
     * 资源简介
     */
    private String resourcesDesc = "";

    private String shareSort = "";

    /**
     * 是否在资源目录显示
     */
    private Boolean isShowResources = true;

    public String toJson(){
        try {
            String s = new ObjectMapper().writeValueAsString(this);
            return s;
        } catch (JsonProcessingException e) {
        }
        return "{}";
    }



}
