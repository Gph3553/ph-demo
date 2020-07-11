package com.example.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CategoryItemDto implements Serializable {
    private static final long serialVersionUID = 5611986324111157175L;

    private Long id;
    private String name;
    private String dataType;
    private String dataLength;
    private String shareAttr;
    private String shareReason;
    private String dbFiledName;
    private String dbFiledType;
    private String dbFiledLength;
    private String isPush;
    private Boolean isPublish;
    private Integer standardfieldStatus;
    private Integer isOpenSociety;
    private String openSocietyConditions;
    private List<DictionaryDto> dictionaryList ;
    private Long resourcesItemId;
}
