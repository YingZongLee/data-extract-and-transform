package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contactjobinfo {
    private String guid;
    private String companyname;
    private String companypronunce;
    private String department;
    private String jobtitle;
    private String recognizesource;
    private Integer fieldorder;
}
