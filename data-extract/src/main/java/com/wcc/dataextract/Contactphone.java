package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contactphone {
    private String guid;
    private String phonevalue;
    private String phonetype;
    private String recognizesource;
    private Integer fieldorder;
}
