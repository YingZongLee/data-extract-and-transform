package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contacturl {
    private String guid;
    private String urlvalue;
    private String urltype;
    private String recognizesource;
    private Integer fieldorder;
}
