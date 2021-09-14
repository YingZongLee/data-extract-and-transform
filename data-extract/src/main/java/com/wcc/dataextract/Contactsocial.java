package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contactsocial {
    private String guid;
    private String socialvalue;
    private String socialtype;
    private String recognizesource;
    private Integer fieldorder;
}
