package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contactim {
    private String guid;
    private String imvalue;
    private String imtype;
    private String recognizesource;
    private Integer fieldorder;
}
