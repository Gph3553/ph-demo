package com.example.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("resource_belong_system")
public class BelongSystem implements Serializable {
    private static final long serialVersionUID = 940213210790893755L;

    @TableId(value = "id",type = IdType.INPUT)
    private Long id;
    private Long tenantId;
    private String systemName;
    private String systemDescription;

    private Long creator;
    private Long modifier;
    private LocalDateTime modifyTm;
    private LocalDateTime createTm;
    private Long resourceId;
    private Integer delStatus;
}