package com.wcc.dataextract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

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
    private OffsetDateTime previousModifiedTimeForSalesforceContact;
    private String exporttosalesforceleadid;
    private OffsetDateTime previousModifiedTimeForSalesforceLead;
    private String sugarcrmaccount;
    private String exporttosugarcrmid;
    private OffsetDateTime statusupdatetime;
    private OffsetDateTime modifyTimeForCrmSync;
    private String exchangeaccount;
    private String exporttoexchangeid;
    private OffsetDateTime previousModifiedTimeForExchange;
    private OffsetDateTime modifyTimeForContactServerSync;
    private String office365account;
    private String exporttooffice365id;
    private String previousModifiedTimeForOffice365;
    private String previousModifiedTimeForQContactz;
    private String crmexportfailedreason;
    private String contactguid;
    private String accountguid;
}
