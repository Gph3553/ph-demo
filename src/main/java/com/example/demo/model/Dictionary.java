package com.example.demo.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("resources_db_dictionary")
public class Dictionary implements Serializable {
    private static final long serialVersionUID = -2614327557752905971L;

    @TableId(value = "id" ,type = IdType.INPUT)
    private Long id;
    private String dbKey;
    private String dbValue;
    @TableField(value = "categoryItem_id")
    private Long categoryItemId;
    private Long tenantId;

    private Long creator;
    private Long modifier;
    private String modifyTm;
    private String createTm;
    private Integer delStatus;

}