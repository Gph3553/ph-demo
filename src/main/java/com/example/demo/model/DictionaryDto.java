package com.example.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DictionaryDto implements Serializable {
    private static final long serialVersionUID = -510662166875729438L;

    private Long id;
    private String dbKey;
    private String dbValue;
    private Long categoryItemId;
}
