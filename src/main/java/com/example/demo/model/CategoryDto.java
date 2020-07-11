package com.example.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CategoryDto implements Serializable {
    private static final long serialVersionUID = 4764500934852532955L;
    private Long id;
    private Long organizationId;
    private String organizationName;
    private String resourceName;
    private String resourceCode;
    private String resourceDesc;
    private String interdepartName;
    private String provideCode;
    private String categoryName;
    private String renewCycle;
    private String infoSourceSort;
    private String infoSourceType;
    private String otherDescription;
    private String shareSort;
    private String shareType;
    private Integer accessType;
    private Integer standardStatus;
    private String remark;
    private Long classificationId;
    private Integer agreeStatus;
    private Long resourcesId;
    //文件更新是否走流程
    private Boolean fileUpdate;
    //    所属系统
    private List<BelongSystemDto> belongSystemList ;
    //    信息项信息
    private List<CategoryItemDto> categoryItemList ;


}
