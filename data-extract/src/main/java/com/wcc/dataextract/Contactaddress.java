package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contactaddress {
    private String guid;
    private String countrycode;
    private String countryname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private Integer addressformat;
    private String addresstype;
    private String recognizesource;
    private Integer fieldorder;
}
