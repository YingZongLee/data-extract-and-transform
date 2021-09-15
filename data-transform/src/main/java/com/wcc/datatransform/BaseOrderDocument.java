package com.wcc.datatransform;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseOrderDocument extends BaseIdDocument {
    private String recognizeSource;
    private Integer fieldOrder;
}
