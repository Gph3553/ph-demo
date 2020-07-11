package com.example.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BelongSystemDto implements Serializable {
    private static final long serialVersionUID = 3509254088809554486L;

    private Long id;
    private String systemName;
    private String systemDescription;
}
