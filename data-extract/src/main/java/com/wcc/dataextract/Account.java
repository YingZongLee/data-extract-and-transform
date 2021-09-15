package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
public class Account {
    private String guid;
    private String email;
    private String password;
    private String displayname;
    private OffsetDateTime createtime;
    private String status;
    private String role;
    private int exportability;
    private String boss;
    private String company_guid;
    private OffsetDateTime categoryorderupdatetime;
    private OffsetDateTime resigndate;
    private OffsetDateTime inheritdate;
    private String inheritedaccount;
    private int secretary;
    private int printability;
    private String inheritancestatus;
    private int contactcountinprivate;
    private String accounttype;
    private String accountsubscriptionstatus;
    private OffsetDateTime lastpasswordchangetime;
    private String lockedissue;
    private String lockcase;
    private String assistantguid;
}
