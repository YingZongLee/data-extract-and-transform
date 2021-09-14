package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contactemail {
    private String guid;
    private String emailValue;
    private String emailtype;
    private String recognizesource;
    private Integer fieldorder;
}
