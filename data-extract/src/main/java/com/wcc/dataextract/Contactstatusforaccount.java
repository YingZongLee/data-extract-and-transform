package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contactstatusforaccount {
    private String guid;
    private String categorysha1;
    private String categoryguids;
    private Boolean isdeletedfromaccount;
    private String salesforceaccount;
    private String exporttosalesforceid;
    private String previousModifiedTimeForSalesforceContact;
    private String exporttosalesforceleadid;
    private String previousModifiedTimeForSalesforceLead;
    private String sugarcrmaccount;
    private String exporttosugarcrmid;
    private String statusupdatetime;
    private String modifyTimeForCrmSync;
    private String exchangeaccount;
    private String exporttoexchangeid;
    private String previousModifiedTimeForExchange;
    private String modifyTimeForContactServerSync;
    private String office365account;
    private String exporttooffice365id;
    private String previousModifiedTimeForOffice365;
    private String previousModifiedTimeForQContactz;
    private String crmexportfailedreason;
    private String contactguid;
    private String accountguid;
}
