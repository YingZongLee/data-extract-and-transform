package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contactname {
    private String guid;
    private String firstname;
    private String firstnamepronunce;
    private String lastname;
    private String lastnamepronunce;
    private String middlename;
    private String prefix;
    private String suffix;
    private String recognizesource;
    private Integer fieldorder;
}
