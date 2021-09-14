package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Account {
    private String guid;
    private String email;
    private String password;
    private String displayname;
    private String createtime;
    private String status;
    private String role;
    private int exportability;
    private String boss;
    private String company_guid;
    private String categoryorderupdatetime;
    private String resigndate;
    private String inheritdate;
    private String inheritedaccount;
    private int secretary;
    private int printability;
    private String inheritancestatus;
    private int contactcountinprivate;
    private String accounttype;
    private String accountsubscriptionstatus;
    private String lastpasswordchangetime;
    private String lockedissue;
    private String lockcase;
    private String assistantguid;
}
