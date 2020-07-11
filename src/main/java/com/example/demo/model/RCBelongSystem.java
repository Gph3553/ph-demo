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

/**
 * <h3>rsp</h3>
 * <p>资源所属系统表--副表</p>
 *
 * @author : PanhuGao
 * @date : 2020-03-27 10:24
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("data_access_belong_system")
public class RCBelongSystem implements Serializable {
    private static final long serialVersionUID = 940213210790893755L;
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;
    private String systemName;
    private String systemDescription;

    private Long creator;
    private LocalDateTime createTm;
    private Long dataAccessId;

}
