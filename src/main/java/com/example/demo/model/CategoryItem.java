package com.example.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("data_access_item")
public class CategoryItem implements Serializable {
    private static final long serialVersionUID = -1977288012952493393L;

    @TableId(value = "id",type = IdType.INPUT)
    private Long id;
    private String name;
    private String dataType;
    private String dataLength;
    private String shareAttr;
    private String shareReason;//'共享条件（只有为有条件共享时才有此字段）',
    private String dbFiledName;
    private String dbFiledType;
    private String dbFiledLength;
    private String isPush;
    private Boolean isPublish;
    private Integer standardfieldStatus;
    private Integer isOpenSociety;//是否向社会开放
    private String openSocietyConditions;//开放条件
  
    private Long tenantId;
  
    private Long creator;
  
    private Long modifier;
  
    private LocalDateTime modifyTm;
  
    private LocalDateTime createTm;
  
    private Long dataAccessId;//数据接入副标id
    private Long resourcesItemId;//信息项主表id
}
