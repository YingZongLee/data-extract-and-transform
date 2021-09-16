package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
public class Contactsimpleinfo {
    private String guid;
    private String fullnameeastfirstwestfirst;
    private String fullnameeastfirstwestlast;
    private String fullnameeastlastwestfirst;
    private String fullnameeastlastwestlast;
    private String company;
    private String department;
    private String jobtitle;
    private OffsetDateTime createtime;
}
